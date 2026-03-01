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

    private var urlHandlers = mutableListOf<(String?) -> Boolean>()
    private var pageFinishedHandlers = mutableListOf<(String?) -> Unit>()

    private var progressHandlers = mutableListOf<(Int) -> Unit>()
    private var titleHandlers = mutableListOf<(String?) -> Unit>()

    init {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return urlHandlers.any { it(url) } // return true if any handler wants to override
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                pageFinishedHandlers.forEach { it(url) }
            }
        }
        
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressHandlers.forEach { it(newProgress) }
            }
            override fun onReceivedTitle(view: WebView?, title: String?) {
                titleHandlers.forEach { it(title) }
            }
        }
    }

    fun overrideUrl(Do: (String?) -> Boolean) {
        urlHandlers.add(Do)
    }
    fun onPageFinished(Do: (String?) -> Unit) {
        pageFinishedHandlers.add(Do)
    }

    fun onProgressChanged(handler: (Int) -> Unit) {
        progressHandlers.add(handler)
    }
    fun onReceivedTitle(handler: (String?) -> Unit) {
        titleHandlers.add(handler)
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
