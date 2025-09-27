package com.productivity.wind.Screens


import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.util.Base64
import android.view.ContextMenu
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import kotlinx.coroutines.*


class MainApp : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Surface(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            startActivity(Intent(this@MainApp, WebClass::class.java))
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Open WebClass", fontSize = 20.sp)
                    }
                }
            }
        }

    }
}




var tabsList: ArrayList<Tab> = ArrayList()
private var isFullscreen: Bool = true
var isDesktopSite: Bool = false
var bookmarkList: ArrayList<Bookmark> = ArrayList()
var bookmarkIndex: Int = -1
lateinit var myPager: ViewPager2
lateinit var tabsBtn: TextView

class WebClass : AppCompatActivity() {

    lateinit var find: ActivityMainBinding
    private var printJob: PrintJob? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        find = ActivityMainBinding.inflate(layoutInflater)
        setContentView(find.root)

        getAllBookmarks()

        tabsList.add(Tab("Home", HomeFragment()))
        find.myPager.adapter = TabsAdapter(supportFragmentManager, lifecycle)
        find.myPager.isUserInputEnabled = false
        myPager = find.myPager
        tabsBtn = find.tabsBtn

        initializeView()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBackPressed() {
        var frag: BrowseFragment? = null
        try {
            frag = tabsList[find.myPager.currentItem].fragment as BrowseFragment
        } catch (_: Exception) {
        }

        when {
            frag?.find?.webView?.canGoBack() == true -> frag.find.webView.goBack()
            find.myPager.currentItem != 0 -> {
                tabsList.removeAt(find.myPager.currentItem)
                find.myPager.adapter?.notifyDataSetChanged()
                find.myPager.currentItem = tabsList.size - 1

            }

            else -> super.onBackPressed()
        }
    }


    private inner class TabsAdapter(fa: FragmentManager, lc: Lifecycle) :
        FragmentStateAdapter(fa, lc) {
        override fun getItemCount(): Int = tabsList.size

        override fun createFragment(position: Int): Fragment = tabsList[position].fragment
    }


    private fun initializeView() {

        find.tabsBtn.setOnClickListener {
            val viewTabs = layoutInflater.inflate(R.layout.tabs_view, find.root, false)
            val bindingTabs = TabsViewBinding.bind(viewTabs)

            val dialogTabs =
                MaterialAlertDialogBuilder(this, R.style.roundCornerDialog).setView(viewTabs)
                    .setTitle("Select Tab")
                    .setPositiveButton("Home") { self, _ ->
                        changeTab("Home", HomeFragment())
                        self.dismiss()
                    }
                    .setNeutralButton("Google") { self, _ ->
                        changeTab("Google", BrowseFragment(urlNew = "www.google.com"))
                        self.dismiss()
                    }
                    .create()

            bindingTabs.tabsRV.setHasFixedSize(true)
            bindingTabs.tabsRV.layoutManager = LinearLayoutManager(this)
            bindingTabs.tabsRV.adapter = TabAdapter(this, dialogTabs)

            dialogTabs.show()

            val pBtn = dialogTabs.getButton(AlertDialog.BUTTON_POSITIVE)
            val nBtn = dialogTabs.getButton(AlertDialog.BUTTON_NEUTRAL)

            pBtn.isAllCaps = false
            nBtn.isAllCaps = false

            pBtn.setTextColor(Color.BLACK)
            nBtn.setTextColor(Color.BLACK)

            pBtn.setCompoundDrawablesWithIntrinsicBounds(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_home, theme), null, null, null
            )
            nBtn.setCompoundDrawablesWithIntrinsicBounds(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_add, theme), null, null, null
            )
        }

        find.settingBtn.setOnClickListener {

            var frag: BrowseFragment? = null
            try {
                frag = tabsList[find.myPager.currentItem].fragment as BrowseFragment
            } catch (_: Exception) {
            }

            val view = layoutInflater.inflate(R.layout.more_features, find.root, false)
            val dialogBinding = MoreFeaturesBinding.bind(view)

            val dialog = MaterialAlertDialogBuilder(this).setView(view).create()

            dialog.window?.apply {
                attributes.gravity = Gravity.BOTTOM
                attributes.y = 50
                setBackgroundDrawable(ColorDrawable(0xFFFFFFFF.toInt()))
            }
            dialog.show()

            if (isFullscreen) {
                dialogBinding.fullscreenBtn.apply {
                    setIconTintResource(R.color.cool_blue)
                    setTextColor(ContextCompat.getColor(this@WebClass, R.color.cool_blue))
                }
            }

            frag?.let {
                bookmarkIndex = isBookmarked(it.find.webView.url!!)
                if (bookmarkIndex != -1) {

                    dialogBinding.bookmarkBtn.apply {
                        setIconTintResource(R.color.cool_blue)
                        setTextColor(ContextCompat.getColor(this@WebClass, R.color.cool_blue))
                    }
                }
            }

            if (isDesktopSite) {
                dialogBinding.desktopBtn.apply {
                    setIconTintResource(R.color.cool_blue)
                    setTextColor(ContextCompat.getColor(this@WebClass, R.color.cool_blue))
                }
            }



            dialogBinding.backBtn.setOnClickListener {
                onBackPressed()
            }

            dialogBinding.forwardBtn.setOnClickListener {
                frag?.apply {
                    if (find.webView.canGoForward())
                        find.webView.goForward()
                }
            }

            dialogBinding.saveBtn.setOnClickListener {
                dialog.dismiss()
                if (frag != null)
                    saveAsPdf(web = frag.find.webView)
                else Snackbar.make(find.root, "First Open A WebPage\uD83D\uDE03", 3000).show()
            }

            dialogBinding.desktopBtn.setOnClickListener {
                it as MaterialButton

                frag?.find?.webView?.apply {
                    isDesktopSite = if (isDesktopSite) {
                        settings.userAgentString = null
                        it.setIconTintResource(R.color.black)
                        it.setTextColor(ContextCompat.getColor(this@WebClass, R.color.black))
                        false
                    } else {
                        settings.userAgentString =
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0"
                        settings.useWideViewPort = true
                        evaluateJavascript(
                            "document.querySelector('meta[name=\"viewport\"]').setAttribute('content'," +
                                    " 'width=1024px, initial-scale=' + (document.documentElement.clientWidth / 1024));",
                            null
                        )
                        it.setIconTintResource(R.color.cool_blue)
                        it.setTextColor(
                            ContextCompat.getColor(
                                this@WebClass,
                                R.color.cool_blue
                            )
                        )
                        true
                    }
                    reload()
                    dialog.dismiss()
                }

            }

            dialogBinding.bookmarkBtn.setOnClickListener {
                frag?.let {
                    if (bookmarkIndex == -1) {
                        val viewB =
                            layoutInflater.inflate(R.layout.bookmark_dialog, find.root, false)
                        val bBinding = BookmarkDialogBinding.bind(viewB)
                        val dialogB = MaterialAlertDialogBuilder(this)
                            .setTitle("Add Bookmark")
                            .setMessage("Url:${it.find.webView.url}")
                            .setPositiveButton("Add") { self, _ ->
                                try {
                                    val array = ByteArrayOutputStream()
                                    it.webIcon?.compress(Bitmap.CompressFormat.PNG, 100, array)
                                    bookmarkList.add(
                                        Bookmark(
                                            name = bBinding.bookmarkTitle.text.toString(),
                                            url = it.find.webView.url!!,
                                            array.toByteArray()
                                        )
                                    )
                                } catch (e: Exception) {
                                    bookmarkList.add(
                                        Bookmark(
                                            name = bBinding.bookmarkTitle.text.toString(),
                                            url = it.find.webView.url!!
                                        )
                                    )
                                }
                                self.dismiss()
                            }
                            .setNegativeButton("Cancel") { self, _ -> self.dismiss() }
                            .setView(viewB).create()
                        dialogB.show()
                        bBinding.bookmarkTitle.setText(it.find.webView.title)
                    } else {
                        val dialogB = MaterialAlertDialogBuilder(this)
                            .setTitle("Remove Bookmark")
                            .setMessage("Url:${it.find.webView.url}")
                            .setPositiveButton("Remove") { self, _ ->
                                bookmarkList.removeAt(bookmarkIndex)
                                self.dismiss()
                            }
                            .setNegativeButton("Cancel") { self, _ -> self.dismiss() }
                            .create()
                        dialogB.show()
                    }
                }

                dialog.dismiss()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        printJob?.let {
            when {
                it.isCompleted -> Snackbar.make(
                    find.root,
                    "Successful -> ${it.info.label}",
                    4000
                ).show()

                it.isFailed -> Snackbar.make(find.root, "Failed -> ${it.info.label}", 4000)
                    .show()
            }
        }
    }

    private fun saveAsPdf(web: WebView) {
        val pm = getSystemService(Context.PRINT_SERVICE) as PrintManager

        val jobName = "${URL(web.url).host}_${
            SimpleDateFormat("HH:mm d_MMM_yy", Locale.ENGLISH)
                .format(Calendar.getInstance().time)
        }"
        val printAdapter = web.createPrintDocumentAdapter(jobName)
        val printAttributes = PrintAttributes.Builder()
        printJob = pm.print(jobName, printAdapter, printAttributes.build())
    }

    fun isBookmarked(url: Str): Int {
        bookmarkList.forEachIndexed { index, bookmark ->
            if (bookmark.url == url) return index
        }
        return -1
    }

    fun saveBookmarks() {
        //for storing bookmarks data using shared preferences
        val editor = getSharedPreferences("BOOKMARKS", MODE_PRIVATE).edit()

        val data = GsonBuilder().create().toJson(bookmarkList)
        editor.putString("bookmarkList", data)

        editor.apply()
    }

    private fun getAllBookmarks() {
        //for getting bookmarks data using shared preferences from storage
        bookmarkList = ArrayList()
        val editor = getSharedPreferences("BOOKMARKS", MODE_PRIVATE)
        val data = editor.getString("bookmarkList", null)

        if (data != null) {
            val list: ArrayList<Bookmark> = GsonBuilder().create()
                .fromJson(data, object : TypeToken<ArrayList<Bookmark>>() {}.type)
            bookmarkList.addAll(list)
        } else {
            // add default bookmarks
            bookmarkList.add(
                Bookmark(
                    "Google",
                    "https://www.google.com",
                    null,
                    R.drawable.ic_d_google
                )
            )
            bookmarkList.add(
                Bookmark(
                    "Youtube",
                    "https://youtube.com",
                    null,
                    R.drawable.ic_d_youtube
                )
            )
        }
    }

}


@SuppressLint("NotifyDataSetChanged")
fun changeTab(url: Str, fragment: Fragment, isBackground: Bool = false) {
    tabsList.add(Tab(name = url, fragment = fragment))
    myPager.adapter?.notifyDataSetChanged()
    tabsBtn.text = tabsList.size.toString()

    if (!isBackground) myPager.currentItem = tabsList.size - 1
}

class TabAdapter(private val context: Context, private val dialog: AlertDialog): RecyclerView.Adapter<TabAdapter.MyHolder>() {

    class MyHolder(binding: TabBinding) :RecyclerView.ViewHolder(binding.root) {
        val cancelBtn = binding.cancelBtn
        val name = binding.tabName
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(TabBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = tabsList[position].name
        holder.root.setOnClickListener {
            myPager.currentItem = position
            dialog.dismiss()
        }

        holder.cancelBtn.setOnClickListener {
            if(tabsList.size == 1 || position == myPager.currentItem)
                Snackbar.make(myPager, "Can't Remove this tab", 3000).show()
            else{
                tabsList.removeAt(position)
                notifyDataSetChanged()
                myPager.adapter?.notifyItemRemoved(position)
            }


        }
    }

    override fun getItemCount(): Int {
        return tabsList.size
    }
}


class BookmarkAdapter(private val context: Context, private val isActivity: Boolean = false) :
    RecyclerView.Adapter<BookmarkAdapter.MyHolder>() {

    private val colors = context.resources.getIntArray(R.array.myColors)

    class MyHolder(
        binding: BookmarkViewBinding? = null,
        bindingL: LongBookmarkViewBinding? = null
    ) : RecyclerView.ViewHolder((binding?.root ?: bindingL?.root)!!) {
        val image = (binding?.bookmarkIcon ?: bindingL?.bookmarkIcon)!!
        val name = (binding?.bookmarkName ?: bindingL?.bookmarkName)!!
        val root = (binding?.root ?: bindingL?.root)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        if (isActivity)
            return MyHolder(
                bindingL = LongBookmarkViewBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        return MyHolder(
            binding = BookmarkViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            //default bookmark image
            if (bookmarkList[position].imagePath != null) {
                holder.image.background = ResourcesCompat.getDrawable(context.resources, bookmarkList[position].imagePath!!, context.theme)
            } else {
                val icon = BitmapFactory.decodeByteArray(
                    bookmarkList[position].image, 0,
                    bookmarkList[position].image!!.size
                )
                holder.image.background = icon.toDrawable(context.resources)
            }
        } catch (e: Exception) {
            holder.image.setBackgroundColor(colors[(colors.indices).random()])
            holder.image.text = bookmarkList[position].name[0].toString()
        }

        holder.name.text = bookmarkList[position].name


        holder.root.setOnClickListener {
            when {
                checkForInternet(context) -> {
                    changeTab(
                        bookmarkList[position].name,
                        BrowseFragment(urlNew = bookmarkList[position].url)
                    )
                    if (isActivity) (context as Activity).finish()
                }

                else -> Snackbar.make(holder.root, "Internet Not Connected\uD83D\uDE03", 3000)
                    .show()
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
            when{
                URLUtil.isValidUrl(urlNew) -> loadUrl(urlNew)
                urlNew.contains(".com", ignoreCase = true) -> loadUrl(urlNew)
                else -> loadUrl("https://www.google.com/search?q=$urlNew")
            }
        }

        return view
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()
        tabsList[myPager.currentItem].name = find.webView.url.toString()
        tabsBtn.text = tabsList.size.toString()

        //for downloading file using external download manager
        find.webView.setDownloadListener { url, _, _, _, _ -> startActivity(
            Intent(Intent.ACTION_VIEW).setData(
            Uri.parse(url))) }

        val mainRef = requireActivity() as WebClass

        mainRef.find.refreshBtn.visibility = View.VISIBLE
        mainRef.find.refreshBtn.setOnClickListener {
            find.webView.reload()
        }

        find.webView.apply {
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
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
                    try{
                        mainRef.find.webIcon.setImageBitmap(icon)
                        webIcon = icon
                        bookmarkIndex = mainRef.isBookmarked(view?.url!!)
                        if(bookmarkIndex != -1){
                            val array = ByteArrayOutputStream()
                            icon!!.compress(Bitmap.CompressFormat.PNG, 100, array)
                            bookmarkList[bookmarkIndex].image = array.toByteArray()
                        }
                    }catch (e: Exception){}
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

    override fun onPause() {
        super.onPause()
        (requireActivity() as WebClass).saveBookmarks()
        //for clearing all webview data
        find.webView.apply {
            clearMatches()
            clearHistory()
            clearFormData()
            clearSslPreferences()
            clearCache(true)

            CookieManager.getInstance().removeAllCookies(null)
            WebStorage.getInstance().deleteAllData()
        }

    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val result = find.webView.hitTestResult
        when(result.type){
            WebView.HitTestResult.IMAGE_TYPE -> {
                menu.add("View Image")
                menu.add("Save Image")
                menu.add("Share")
                menu.add("Close")
            }
            WebView.HitTestResult.SRC_ANCHOR_TYPE, WebView.HitTestResult.ANCHOR_TYPE-> {
                menu.add("Open in New Tab")
                menu.add("Open Tab in Background")
                menu.add("Share")
                menu.add("Close")
            }
            WebView.HitTestResult.EDIT_TEXT_TYPE, WebView.HitTestResult.UNKNOWN_TYPE -> {}
            else ->{
                menu.add("Open in New Tab")
                menu.add("Open Tab in Background")
                menu.add("Share")
                menu.add("Close")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val message = Handler().obtainMessage()
        find.webView.requestFocusNodeHref(message)
        val url = message.data.getString("url")
        val imgUrl = message.data.getString("src")

        when(item.title){
            "Open in New Tab" -> {
                changeTab(url.toString(), BrowseFragment(url.toString()))
            }
            "Open Tab in Background" ->{
                changeTab(url.toString(), BrowseFragment(url.toString()), isBackground = true)
            }
            "View Image" ->{
                if(imgUrl != null) {
                    if (imgUrl.contains("base64")) {
                        val pureBytes = imgUrl.substring(imgUrl.indexOf(",") + 1)
                        val decodedBytes = Base64.decode(pureBytes, Base64.DEFAULT)
                        val finalImg =
                            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

                        val imgView = ShapeableImageView(requireContext())
                        imgView.setImageBitmap(finalImg)

                        val imgDialog = MaterialAlertDialogBuilder(requireContext()).setView(imgView).create()
                        imgDialog.show()

                        imgView.layoutParams.width = Resources.getSystem().displayMetrics.widthPixels
                        imgView.layoutParams.height = (Resources.getSystem().displayMetrics.heightPixels * .75).toInt()
                        imgView.requestLayout()

                        imgDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    }
                    else changeTab(imgUrl, BrowseFragment(imgUrl))
                }
            }

            "Save Image" ->{
                if(imgUrl != null) {
                    if (imgUrl.contains("base64")) {
                        val pureBytes = imgUrl.substring(imgUrl.indexOf(",") + 1)
                        val decodedBytes = Base64.decode(pureBytes, Base64.DEFAULT)
                        val finalImg =
                            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

                        MediaStore.Images.Media.insertImage(
                            requireActivity().contentResolver,
                            finalImg, "Image", null
                        )
                        Snackbar.make(find.root, "Image Saved Successfully", 3000).show()
                    }
                    else startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(imgUrl)))
                }
            }

            "Share" -> {
                val tempUrl = url ?: imgUrl
                if(tempUrl != null){
                    if(tempUrl.contains("base64")){

                        val pureBytes = tempUrl.substring(tempUrl.indexOf(",") + 1)
                        val decodedBytes = Base64.decode(pureBytes, Base64.DEFAULT)
                        val finalImg = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

                        val path = MediaStore.Images.Media.insertImage(requireActivity().contentResolver,
                            finalImg, "Image", null)

                        ShareCompat.IntentBuilder(requireContext()).setChooserTitle("Sharing Url!")
                            .setType("image/*")
                            .setStream(Uri.parse(path))
                            .startChooser()
                    }
                    else{
                        ShareCompat.IntentBuilder(requireContext()).setChooserTitle("Sharing Url!")
                            .setType("text/plain").setText(tempUrl)
                            .startChooser()
                    }
                }
                else Snackbar.make(find.root, "Not a Valid Link!", 3000).show()
            }
            "Close" -> {}
        }

        return super.onContextItemSelected(item)
    }
}

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        return view
    }

    override fun onResume() {
        super.onResume()

        val mainActivityRef = requireActivity() as WebClass

        tabsBtn.text = tabsList.size.toString()
        tabsList[myPager.currentItem].name = "Home"

        mainActivityRef.find.topSearchBar.setText("")
        binding.searchView.setQuery("",false)
        mainActivityRef.find.webIcon.setImageResource(R.drawable.ic_search)

        mainActivityRef.find.refreshBtn.visibility = View.GONE

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(result: String?): Boolean {
                if(checkForInternet(requireContext()))
                    changeTab(result!!, BrowseFragment(result))
                else
                    Snackbar.make(binding.root, "Internet Not Connected\uD83D\uDE03", 3000).show()
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean = false
        })
        mainActivityRef.find.goBtn.setOnClickListener {
            if(checkForInternet(requireContext()))
                changeTab(mainActivityRef.find.topSearchBar.text.toString(),
                    BrowseFragment(mainActivityRef.find.topSearchBar.text.toString())
                )
            else
                Snackbar.make(binding.root, "Internet Not Connected\uD83D\uDE03", 3000).show()
        }

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setItemViewCacheSize(5)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 5)
        binding.recyclerView.adapter = BookmarkAdapter(requireContext())

        if(bookmarkList.size < 1)
            binding.viewAllBtn.visibility = View.GONE
        binding.viewAllBtn.setOnClickListener {
            startActivity(Intent(requireContext(), BookmarkActivity::class.java))
        }
    }
}



data class Tab(var name: Str, val fragment: Fragment)
data class Bookmark(val name: Str, val url: Str, var image: ByteArray? = null, var imagePath: Int? = null)

class BookmarkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val find = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(find.root)
        find.rvBookmarks.setItemViewCacheSize(5)
        find.rvBookmarks.hasFixedSize()
        find.rvBookmarks.layoutManager = LinearLayoutManager(this)
        find.rvBookmarks.adapter = BookmarkAdapter(this, isActivity = true)
    }
}