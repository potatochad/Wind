package com.productivity.wind.Screens

import android.annotation.SuppressLint
import android.content.*
import android.app.Activity
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.compose.material3.*
import android.os.Handler
import android.print.PrintAttributes
import android.print.*
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.util.Base64
import android.view.*
import android.webkit.*
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.widget.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import com.productivity.wind.*
import androidx.compose.ui.Modifier
import com.productivity.wind.Imports.*
import androidx.compose.foundation.background
import androidx.compose.ui.draw.*
import androidx.compose.animation.core.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.productivity.wind.R
import com.productivity.wind.databinding.*
import kotlinx.coroutines.*
import com.google.android.material.imageview.*
import android.graphics.*
import android.graphics.drawable.*




var tabsList: ArrayList<Tab> = ArrayList()
var isDesktopSite: Bool = false
var bookmarkList: ArrayList<Bookmark> = ArrayList()
var bookmarkIndex: Int = -1
lateinit var myPager: ViewPager2
lateinit var tabsBtn: TextView


class WebClass : AppCompatActivity() {

    lateinit var find: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        find = setUpXML(this)
        setContentView(find.root)

        tabsList.add(Tab("Home", HomeFragment()))
        find.myPager.adapter = TabsAdapter(supportFragmentManager, lifecycle)
        find.myPager.isUserInputEnabled = false
        myPager = find.myPager
        tabsBtn = find.tabsBtn
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBackPressed() {
        handleBackPressed(this)
    }


    private inner class TabsAdapter(fa: FragmentManager, lc: Lifecycle) :
        FragmentStateAdapter(fa, lc) {
        override fun getItemCount(): Int = tabsList.size

        override fun createFragment(position: Int): Fragment = tabsList[position].fragment
    }

}


@SuppressLint("NotifyDataSetChanged")
fun changeTab(url: Str, fragment: Fragment, isBackground: Bool = false) {
    tabsList.add(Tab(name = url, fragment = fragment))
    myPager.adapter?.notifyDataSetChanged()
    tabsBtn.text = tabsList.size.toString()

    if (!isBackground) myPager.currentItem = tabsList.size - 1
}

class TabAdapter(private val dialog: AlertDialog): RecyclerView.Adapter<TabAdapter.MyHolder>() {

    class MyHolder(find: TabBinding) :RecyclerView.ViewHolder(find.root) {
        val cancelBtn = find.cancelBtn
        val name = find.tabName
        val root = find.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(TabBinding.inflate(LayoutInflater.from(App.ctx), parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = tabsList[position].name
        holder.root.setOnClickListener {
            myPager.currentItem = position
            dialog.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return tabsList.size
    }
}


class BookmarkAdapter(private val isActivity: Boolean = false) :
    RecyclerView.Adapter<BookmarkAdapter.MyHolder>() {

    private val colors = App.ctx.resources.getIntArray(R.array.myColors)

    class MyHolder(
        find: BookmarkViewBinding? = null,
        findL: LongBookmarkViewBinding? = null
    ) : RecyclerView.ViewHolder((find?.root ?: findL?.root)!!) {
        val image = (find?.bookmarkIcon ?: findL?.bookmarkIcon)!!
        val name = (find?.bookmarkName ?: findL?.bookmarkName)!!
        val root = (find?.root ?: findL?.root)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        if (isActivity)
            return MyHolder(
                findL = LongBookmarkViewBinding.inflate(
                    LayoutInflater.from(App.ctx),
                    parent,
                    false
                )
            )
        return MyHolder(
            find = BookmarkViewBinding.inflate(
                LayoutInflater.from(App.ctx),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            //default bookmark image
            if (bookmarkList[position].imagePath != null) {
                holder.image.background = ResourcesCompat.getDrawable(App.ctx.resources, bookmarkList[position].imagePath!!, App.ctx.theme)
            } else {
                val icon = BitmapFactory.decodeByteArray(
                    bookmarkList[position].image, 0,
                    bookmarkList[position].image!!.size
                )
                holder.image.background = icon.toDrawable(App.ctx.resources)
            }
        } catch (e: Exception) {
            holder.image.setBackgroundColor(colors[(colors.indices).random()])
            holder.image.text = bookmarkList[position].name[0].toString()
        }

        holder.name.text = bookmarkList[position].name


        holder.root.setOnClickListener {
            when {
                checkForInternet() -> {
                    changeTab(
                        bookmarkList[position].name,
                        BrowseFragment(urlNew = bookmarkList[position].url)
                    )
                    if (isActivity) (App.ctx as Activity).finish()
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }
}

class BrowseFragment(private var urlNew: Str = "https://www.google.com") : Fragment() {

    lateinit var find: FragmentBrowseBinding
    var webIcon: Bitmap? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_browse, container, false)
        find = FragmentBrowseBinding.bind(view)
        registerForContextMenu(find.webView)

        find.webView.apply {
            loadUrl("https://www.google.com/search?q=$urlNew")
        }

        return view
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()
        tabsList[myPager.currentItem].name = find.webView.url.toString()
        tabsBtn.text = tabsList.size.toString()

        val mainRef = requireActivity() as WebClass

        find.webView.apply {
            settings.applyDefaultConfig()
            webViewClient = object: WebViewClient(){

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                    if(isDesktopSite)
                        view?.evaluateJavascript("document.querySelector('meta[name=\"viewport\"]').setAttribute('content'," +
                                " 'width=1024px, initial-scale=' + (document.documentElement.clientWidth / 1024));", null)
                }

                override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    mainRef.find.topSearchBar.text = SpannableStringBuilder(url)
                    tabsList[myPager.currentItem].name = url.toString()
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    mainRef.find.progressBar.progress = 0
                    mainRef.find.progressBar.visibility = View.VISIBLE
                    if(url!!.contains("you", ignoreCase = false)) mainRef.find.root.transitionToEnd()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    mainRef.find.progressBar.visibility = View.GONE
                    find.webView.zoomOut()
                }
            }
            webChromeClient = object: WebChromeClient(){
                //for setting icon to our search bar
                override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                    super.onReceivedIcon(view, icon)
                    
                }

                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    super.onShowCustomView(view, callback)
                    find.webView.visibility = View.GONE
                    find.customView.visibility = View.VISIBLE
                    find.customView.addView(view)
                    mainRef.find.root.transitionToEnd()
                }

                override fun onHideCustomView() {
                    super.onHideCustomView()
                    find.webView.visibility = View.VISIBLE
                    find.customView.visibility = View.GONE

                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    mainRef.find.progressBar.progress = newProgress
                }
            }

            find.webView.setOnTouchListener { _, motionEvent ->
                mainRef.find.root.onTouchEvent(motionEvent)
                return@setOnTouchListener false
            }

            find.webView.reload()
        }
    }
}

class HomeFragment : Fragment() {

    private lateinit var find: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        find = FragmentHomeBinding.bind(view)

        return view
    }

    override fun onResume() {
        super.onResume()

        val mainActivityRef = requireActivity() as WebClass

        tabsBtn.text = tabsList.size.toString()
        tabsList[myPager.currentItem].name = "Home"

        mainActivityRef.find.topSearchBar.setText("")
        find.searchView.setQuery("",false)
        mainActivityRef.find.webIcon.setImageResource(R.drawable.ic_search)

        mainActivityRef.find.refreshBtn.visibility = View.GONE

        find.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(result: Str?): Bool {
                if(checkForInternet())
                    changeTab(result!!, BrowseFragment(result))
                return true
            }
            override fun onQueryTextChange(p0: Str?): Bool = false
        })
        mainActivityRef.find.goBtn.setOnClickListener {
            if(checkForInternet())
                changeTab(mainActivityRef.find.topSearchBar.text.toString(),
                    BrowseFragment(mainActivityRef.find.topSearchBar.text.toString())
                )
        }

        find.recyclerView.setHasFixedSize(true)
        find.recyclerView.setItemViewCacheSize(5)
        find.recyclerView.layoutManager = GridLayoutManager(requireContext(), 5)
        find.recyclerView.adapter = BookmarkAdapter()

        if(bookmarkList.size < 1)
            find.viewAllBtn.visibility = View.GONE
        find.viewAllBtn.setOnClickListener {
            startActivity(Intent(requireContext(), BookmarkActivity::class.java))
        }
    }
}



data class Tab(
    var name: Str, 
    val fragment: Fragment
)

data class Bookmark(
    val name: Str, 
    val url: Str, 
    var image: ByteArray? = null, 
    var imagePath: Int? = null
)

class BookmarkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val find = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(find.root)
        find.rvBookmarks.setItemViewCacheSize(5)
        find.rvBookmarks.hasFixedSize()
        find.rvBookmarks.layoutManager = LinearLayoutManager(this)
        find.rvBookmarks.adapter = BookmarkAdapter(isActivity = true)
    }
}
