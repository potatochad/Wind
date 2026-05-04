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



class WebController(
    ctx: Context //!NEEDS ONLY LOCAL CONTEXT
) {
    val rootView: View =
        LayoutInflater.from(ctx).inflate(R.layout.web, null, false)

    val webView: WebView =
        rootView.findViewById(R.id.myWebView)

    val swipeRefresh: SwipeRefreshLayout =
        rootView.findViewById(R.id.swipeRefreshContainer)



    
    private var shouldOverrideUrlLoading = mutableListOf<(String?) -> Boolean>()
    private var onPageFinished = mutableListOf<(String?) -> Unit>()
    private var onLoadResource = mutableListOf<(String?) -> Unit>()
    private val doUpdateVisitedHistory = mutableListOf<(String?, Boolean) -> Unit>()
    private val onPageStarted = mutableListOf<(String?) -> Unit>()
    private val shouldInterceptRequest = mutableListOf<(WebResourceRequest) -> WebResourceResponse?>()

    private var onProgressChanged = mutableListOf<(Int) -> Unit>()
    private var onReceivedTitle = mutableListOf<(String?) -> Unit>()

    
    init {
     swipeRefresh.setOnRefreshListener {
            webView.reload()
        }

        swipeRefresh.setOnChildScrollUpCallback { _, _ ->
            webView.scrollY > 0
        }
        // swipe.setDistanceToTriggerSync(250)
        
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return shouldOverrideUrlLoading.any { it(url) } // return true if any handler wants to override
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                view?.gray(90f)
                swipeRefresh.isRefreshing = false

                view?.hideYouTubeShorts()
				showMovingRedBox()
                
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
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
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
            override fun onReceivedTitle(view: WebView?, title: String?) {
                onReceivedTitle.forEach { it(title) }
            }
        }
    }

    fun shouldOverrideUrlLoading(Do: (String?) -> Boolean) {
        shouldOverrideUrlLoading.add(Do)
    }
    fun onPageFinished(Do: (String?) -> Unit) {
        onPageFinished.add(Do)
    }
    fun onLoadResource(Do: (String?) -> Unit) {
        onLoadResource.add(Do)
    }
    fun doUpdateVisitedHistory(handler: (String?, Boolean) -> Unit) {
        doUpdateVisitedHistory.add(handler)
    }
    fun onPageStarted(handler: (String?) -> Unit) {
        onPageStarted.add(handler)
    }
    fun shouldInterceptRequest(handler: (WebResourceRequest) -> WebResourceResponse?) {
        shouldInterceptRequest.add(handler)
    }
    

    fun onProgressChanged(handler: (Int) -> Unit) {
        onProgressChanged.add(handler)
    }
    fun onReceivedTitle(handler: (String?) -> Unit) {
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
	fun showMovingRedBox() {

	val js = """
		(function() {

			if (document.getElementById("moving-box")) return;

			const box = document.createElement("div");
			box.id = "moving-box";

			box.style.position = "fixed";
			box.style.left = "20px";
			box.style.top = "100px";
			box.style.width = "120px";
			box.style.height = "120px";
			box.style.background = "red";
			box.style.zIndex = "999999";
			box.style.borderRadius = "12px";

			document.body.appendChild(box);

			let direction = 1;

			setInterval(() => {
				let top = parseInt(box.style.top);

				if (top > window.innerHeight - 150) direction = -1;
				if (top < 50) direction = 1;

				box.style.top = (top + direction * 5) + "px";
			}, 16);

		})();
	""".trimIndent()

	webView?.evaluateJavascript(js, null)
	}
    fun foundYoutubeChannel(channel: Str = "") {
		val cleanStyle = """
			(function() {
				const style = document.createElement('style');
				style.innerHTML = `
					* {
						color: red !important;
						background: rgba(255, 0, 0, 0.05) !important;
						border-color: red !important;
					}
				`;
				document.head.appendChild(style);
			})();
		""".trimIndent()

		webView?.evaluateJavascript(cleanStyle, null)
    }
}

@SuppressLint("SetJavaScriptEnabled")
fun WebController.applyFancySettings(){
    this.settings {
        javaScriptEnabled = yes
        domStorageEnabled = yes
        useWideViewPort = yes
        loadWithOverviewMode = yes
    }
    this.onPageFinished { url ->
        this.zoomOut()
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











