package com.productivity.wind.Imports.Utils
 
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


class WebController(val webView: WebView) {
    private var shouldOverrideUrlLoading = mutableListOf<(String?) -> Boolean>()
    private var onPageFinished = mutableListOf<(String?) -> Unit>()
    private var onLoadResource = mutableListOf<(String?) -> Unit>()
    private val doUpdateVisitedHistory = mutableListOf<(String?, Boolean) -> Unit>()
    private val onPageStarted = mutableListOf<(String?) -> Unit>()
    private val shouldInterceptRequest = mutableListOf<(WebResourceRequest) -> WebResourceResponse?>()

    private var onProgressChanged = mutableListOf<(Int) -> Unit>()
    private var onReceivedTitle = mutableListOf<(String?) -> Unit>()

    init {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return shouldOverrideUrlLoading.any { it(url) } // return true if any handler wants to override
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
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
    val url: Str?
        get() = webView.url
    fun url(url: Str) {
        webView.loadUrl(url)
    }
    val web: WebView?
        get() = webView
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
fun UrlShort(x: Str): Str {
    return remember(x) {
        x.remove("https://").remove("http://").remove("www.")
    }
}

fun BlockKeywords(
    webView: m_<Web?>,
    keywords: List<Str>,
    Do: Do = {},
) {
    webView.it?.evaluateJavascript(
        "(function() { return document.body.innerText.toLowerCase(); })();"
    ) { html ->
        if (html != null) {
            val lowerHtml = html.lowercase()
            for (word in keywords) {
                if (lowerHtml.contains(word.lowercase())) {
                    goBackWeb(webView.it)
                    Do()
                    Vlog("Blocked $word") 
                    return@evaluateJavascript
                }
            }
        }
    }
}
