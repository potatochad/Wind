package com.productivity.wind.Imports.Utils.Browser
 
import android.annotation.SuppressLint
import android.content.*
import androidx.compose.runtime.*
import androidx.activity.compose.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.ui.viewinterop.*
import android.view.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import com.productivity.wind.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.productivity.wind.R
import com.productivity.wind.Imports.*
import android.webkit.*
import android.graphics.*
import kotlinx.coroutines.*
import com.productivity.wind.Imports.Utils.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.productivity.wind.Imports.Utils.String.*
import java.io.ByteArrayInputStream
import android.util.Log


class WebController(
    ctx: Context //!NEEDS ONLY LOCAL CONTEXT
) {
    val rootView: View =
        LayoutInflater.from(ctx).inflate(R.layout.web, null, false)

    val webView: WebView =
        rootView.findViewById(R.id.myWebView)

    val swipeRefresh: SwipeRefreshLayout =
        rootView.findViewById(R.id.swipeRefreshContainer)



    private var shouldOverrideUrlLoading = mList<(Str?) -> Bool>()
    private var onPageFinished = mList<(Str?) -> Unit>()
    private var onLoadResource = mList<(Str?) -> Unit>()
    private val doUpdateVisitedHistory = mList<(Str?, Bool) -> Unit>()
    private val onPageStarted = mList<(Str?) -> Unit>()
    private val shouldInterceptRequest = mList<(WebResourceRequest) -> WebResourceResponse?>()

    private var onProgressChanged = mList<(Int) -> Unit>()
    private var onReceivedTitle = mList<(Str?) -> Unit>()

    
    init {
		webView.enable()
		
		swipeRefresh.setOnRefreshListener {
            webView.reload()
        }

        swipeRefresh.setOnChildScrollUpCallback { _, _ ->
            webView.scrollY > 0
        }
        // swipe.setDistanceToTriggerSync(250)
        
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: Str?): Bool {
                return shouldOverrideUrlLoading.any { it(url) } // return true if any handler wants to override
            }
			override fun onPageCommitVisible(view: WebView?, url: Str?) {
				super.onPageCommitVisible(view, url)

				view?.importsJS()
			}

			private var lastFinishedUrl: Str? = null
            override fun onPageFinished(view: WebView?, url: Str?) {
                super.onPageFinished(view, url)
				if (url == lastFinishedUrl) return

				//this.zoomOut()
                view?.gray(90f)
                swipeRefresh.isRefreshing = no

                view?.hideYoutubeChannel(
					listOf("MrBeast", "McYum", "Technoblade", "Skeppy", "Grian", "Spifey", "Minecraft", "Speedrun", "Mr Bean", "POV", "m views", "redstone", "command blocks", "00 IQ", "poly bridge", "iswho", "rageplaysgames", "leowook", "shalz")         
				)
				
                
                onPageFinished.forEach { it(url) }
            }
            override fun onLoadResource(view: WebView?, url: Str?) {
                super.onLoadResource(view, url)
                onLoadResource.forEach { it(url) }
            }
            override fun doUpdateVisitedHistory(view: WebView?, url: Str?, isReload: Bool) {
                super.doUpdateVisitedHistory(view, url, isReload)
                doUpdateVisitedHistory.forEach { it(url, isReload) } // call your handlers
            }
            override fun onPageStarted(view: WebView?, url: Str?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                onPageStarted.forEach { it(url) }
            }
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
                shouldInterceptRequest.forEach { 
                    val result = it(request)
                    if (result != null) return result
                }
                return super.shouldInterceptRequest(view, request)
            }
        }
        
        
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                onProgressChanged.forEach { it(newProgress) }
            }
            override fun onReceivedTitle(view: WebView?, title: Str?) {
                onReceivedTitle.forEach { it(title) }
            }
			override fun onConsoleMessage(message: ConsoleMessage): Bool {
				val msg = message.message()

				if (msg.has("[WINDWEB_LOG]")) {
					Log.e("badWEB", msg)
				}
				return yes
			}
        }
    }

    fun shouldOverrideUrlLoading(Do: (Str?) -> Bool) {
        shouldOverrideUrlLoading.add(Do)
    }
    fun onPageFinished(Do: (Str?) -> Unit) {
        onPageFinished.add(Do)
    }
    fun onLoadResource(Do: (Str?) -> Unit) {
        onLoadResource.add(Do)
    }
    fun doUpdateVisitedHistory(handler: (Str?, Bool) -> Unit) {
        doUpdateVisitedHistory.add(handler)
    }
    fun onPageStarted(handler: (Str?) -> Unit) {
        onPageStarted.add(handler)
    }
    fun shouldInterceptRequest(handler: (WebResourceRequest) -> WebResourceResponse?) {
        shouldInterceptRequest.add(handler)
    }
    

    fun onProgressChanged(handler: (Int) -> Unit) {
        onProgressChanged.add(handler)
    }
    fun onReceivedTitle(handler: (Str?) -> Unit) {
        onReceivedTitle.add(handler)
    }    


    fun settings(configure: WebSettings.() -> Unit) {
        webView.settings.apply {
            configure()
        }
    }
    fun back() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }
    fun canGoBack(): Bool {
        return webView.canGoBack()
    }
    val url: Str?
        get() = webView.url
    fun url(u: Str) {
        val url = u.trim()

        val finalUrl =
        if (
            url.startsWith("http://") ||
            url.startsWith("https://") ||
            url.startsWith("file://") ||
            url.startsWith("about:") ||
            url.startsWith("data:") ||
            url.startsWith("javascript:") ||
            url.startsWith("//")
        ) url
        else "https://$url"

        webView.loadUrl(finalUrl)
    }
    val web: WebView?
        get() = webView
    fun zoomOut(){
        webView.zoomOut()
    }
    fun reload(){
        webView.reload()
    }

    fun blockImages(Do: () -> Bool = { yes }) {
        shouldInterceptRequest { request ->
            val url = request.url.toString().lowercase()

            val host = request.url.host?.lowercase()

            if (host?.contains("ytimg") == true) {

                return@shouldInterceptRequest WebResource.emptyImage()
            }
            
            if (!url.image && Do()) return@shouldInterceptRequest null
            else WebResource.emptyImage()
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
fun WebView.enable(){
	this.settings.apply {
        javaScriptEnabled = yes
		domStorageEnabled = yes
        useWideViewPort = yes
        loadWithOverviewMode = yes
	}
}




@SuppressLint("SetJavaScriptEnabled")
fun WebView.clearWebData() {
    clearMatches()
    clearHistory()
    clearFormData()
    clearSslPreferences()
    clearCache(yes)

    CookieManager.getInstance().removeAllCookies(null)
    WebStorage.getInstance().deleteAllData()
}

@SuppressLint("SetJavaScriptEnabled")
fun WebSettings.applyDefaultConfig() {
    javaScriptEnabled = yes
    setSupportZoom(yes)
    builtInZoomControls = yes
    displayZoomControls = no
}

@Composable
fun UrlLong(input: Str): Str {
    return remember(input) {
        if (input.startsWith("http://") || input.startsWith("https://")) {
            input
        } else {
            "https://$input"
        }
    }
}
@Composable
fun UrlShort(x: String): String {
    return remember(x) {
        x.remove("https://", "http://", "www.")
    }
}

fun BlockKeywords(
    web: WebController,
    keywords: List<Str>,
    Do: Do = {},
) {
    web.html {
        if (it != null) {
            val lowerHtml = it.lowercase()
            for (word in keywords) {
                if (lowerHtml.contains(word.lowercase())) {
                    web.back()
                    Do()
                    Vlog("Blocked $word") 
                    return@html
                }
            }
        }
    }
}

val WebResourceRequest.image: Bool
        get() = url.toString().lowercase().image


class WebResource {
    companion object {
        fun empty(): WebResourceResponse {
            return WebResourceResponse(
                "text/plain",
                "UTF-8",
                ByteArrayInputStream(ByteArray(0))
            )
        }

        fun emptyImage(): WebResourceResponse {
            return WebResourceResponse(
                "image/png",
                null,
                ByteArrayInputStream(ByteArray(0))
            )
        }
    }
}











