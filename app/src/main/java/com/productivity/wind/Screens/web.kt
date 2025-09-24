package com.productivity.wind.Screens
//
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import android.os.*
import timber.log.Timber
import android.app.usage.UsageStatsManager
import androidx.compose.foundation.interaction.*
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.isAccessible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import android.content.ClipData
import android.content.ClipboardManager
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import android.content.Intent
import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import android.graphics.drawable.Drawable
import android.content.pm.*
import java.util.*
import com.productivity.wind.R
import android.webkit.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.graphics.painter.*
import com.google.accompanist.drawablepainter.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.*
import androidx.compose.runtime.*
import android.webkit.*
import androidx.activity.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.*
import com.productivity.wind.Imports.*
import com.productivity.wind.*
import org.mozilla.geckoview.*
import android.content.*
import androidx.compose.ui.platform.*
import androidx.compose.foundation.lazy.*
import org.mozilla.geckoview.AllowOrDeny
import org.mozilla.geckoview.*
import org.mozilla.geckoview.GeckoSession.NavigationDelegate.*
import org.mozilla.geckoview.GeckoSession.*
import androidx.activity.compose.*
import java.util.concurrent.*
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.*
import android.net.Uri
import android.os.*
import android.print.*
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.util.Base64
import android.view.*
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.GsonBuilder
import java.io.ByteArrayOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.*


fun WebView.WebDefaults() {
    settings.apply {
        javaScriptEnabled = true
        domStorageEnabled = true
        useWideViewPort = true
        loadWithOverviewMode = true
        builtInZoomControls = true
        displayZoomControls = false
    }
    webChromeClient = WebChromeClient()
}
fun WebView.onLoadedPage(action: (pageUrl: String?, view: WebView?) -> Unit) {
    webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, urlLoaded: String?) {
            super.onPageFinished(view, urlLoaded)
            action(urlLoaded, view)
        }
    }
}


fun WebView.updateWeb(url: String) {
    if (this.url != url) {
        this.loadUrl(url)
    }
}

fun WebView.injectFixedSizeYouTube() {
    val widthPx = (500 * resources.displayMetrics.density).toInt()
    val heightPx = (1000 * resources.displayMetrics.density).toInt()
    
    val js = """
    javascript:(function() {
        var iframes = document.getElementsByTagName('iframe');
        for(var i = 0; i < iframes.length; i++) {
            if(iframes[i].src.includes("youtube.com/embed")) {
                iframes[i].style.setProperty('width', '${widthPx}px', 'important');
                iframes[i].style.setProperty('height', '${heightPx}px', 'important');
                iframes[i].style.setProperty('border', 'none', 'important');
                iframes[i].style.setProperty('position', 'relative', 'important');
            }
        }
    })();
""".trimIndent()


    this.evaluateJavascript(js, null)
}







@Composable
fun Web() {
    var url = r_m("https://www.google.com") // default URL
    val webView = r { mutableStateOf<WebView?>(null) }

    Item.WebPointTimer()

    LaunchedEffect(webView.value) {
    val wv = webView.value ?: return@LaunchedEffect
    while (true) {
        val currentUrl = wv.url
        if (currentUrl != url.value) {
            url.value = currentUrl
        }
        delay(100) // 100ms is smoother, safer
    }
}



    LazyScreen(
        title = { 
            Text(" Points ${Bar.funTime}: ") 
            val scrollState = rememberScrollState()

            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                Text("url= ${url.value}")
            }

        },
        Scrollable = false,
        DividerPadding = false,
    ) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    this.WebDefaults()
                    
                    
                

                        webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val newUrl = request?.url.toString()
                    url.value = newUrl
                    return false // allow WebView to load
                }

                override fun onPageFinished(view: WebView?, urlLoaded: String?) {
                    super.onPageFinished(view, urlLoaded)
                    if (!urlLoaded.isNullOrEmpty()) {
                        url.value = urlLoaded
                    }
                }
                        }



                    webView.value = this
                    loadUrl(url.value)
                }
            },
            update = { webView ->
                webView.updateWeb(url.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(App.LazyScreenContentHeight)
        )
    }
}


