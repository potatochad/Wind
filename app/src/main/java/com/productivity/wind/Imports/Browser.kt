package com.productivity.wind.Imports

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

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebXml(
    webViewState: m_<WebView?>,
    url: Str = "",
    isDesktopSite: Bool = no,
    onUrlChanged: DoStr = {},
    onProgressChanged: DoInt = {},
    onPageStarted: DoStr = {},
    onPageFinished: DoStr = {},
    loadPage: (url: Str) -> Bool = { yes },
) {
    BackHandler {
        webViewState.it?.goBack()
    }

    AndroidView(
        factory = { ctx ->
            val rootView = LayoutInflater.from(ctx).inflate(R.layout.web, null, no)
            val myWebView = rootView.findViewById<WebView>(R.id.myWebView)

            myWebView.settings.apply {
                javaScriptEnabled = yes
                domStorageEnabled = yes
                useWideViewPort = yes
                loadWithOverviewMode = yes
            }

            myWebView.webViewClient = object : WebViewClient() {
                override fun onLoadResource(view: WebView?, url: Str?) {
                    super.onLoadResource(view, url)
                    if (isDesktopSite) {
                        view?.evaluateJavascript(
                            "document.querySelector('meta[name=\"viewport\"]').setAttribute('content'," +
                                    "'width=1024px, initial-scale=' + (document.documentElement.clientWidth / 1024));",
                            null
                        )
                    }
                }

                override fun doUpdateVisitedHistory(view: WebView?, url: Str?, isReload: Bool) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    url?.let { 
                        onUrlChanged(it) 
                    }
                }

                override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
                    val raw = "${request.url}"

                    val stop = loadPage(raw)

                    if (!stop) {
                        goBackWeb(view)
                        return WebResourceResponse("text/plain", "utf-8", null)
                    }
                    return super.shouldInterceptRequest(view, request)
                }



                override fun onPageStarted(view: WebView?, url: Str?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    url?.let { onPageStarted(it) }
                }

                override fun onPageFinished(view: WebView?, url: Str?) {
                    super.onPageFinished(view, url)
                    url?.let { onPageFinished(it) }
                    view?.zoomOut()
                }
            }

            myWebView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    onProgressChanged(newProgress)
                }
            }

            myWebView.url("https://www.google.com/search?q=$url")
            webViewState.it = myWebView

            rootView
        },
        modifier = Modifier.maxS(),
        update = { view ->
            val myWebView = view.findViewById<WebView>(R.id.myWebView)
        }
    )
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
fun UrlShort(input: Str): Str {
    return remember(input) {
        input.removePrefix("https://")
            .removePrefix("http://")
            .removePrefix("www.")
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






