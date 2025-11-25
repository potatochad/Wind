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
import com.productivity.wind.Imports.Data.*


@Suppress("DEPRECATION")
fun checkForInternet(): Bool {
    val context = App.ctx
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val activeNetwork = network?.let { connectivityManager.getNetworkCapabilities(it) }
        activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == yes ||
                activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == yes
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected == yes
    }

    if (!isConnected) Vlog("No internet")
    return isConnected
}
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebXml(
    webViewState: m_<WebView?>,
    url: String = "",
    isDesktopSite: Bool = no,
    onUrlChanged: DoStr = {},
    onProgressChanged: DoInt = {},
    onPageStarted: DoStr = {},
    onPageFinished: DoStr = {},
    loadPage: (view: WebView?, url: Str) -> Bool = { _, _ -> no },
) {
    BackHandler {
        webViewState.it?.goBack()
    }

    RunOnce(webViewState.it) {
        while (true) {
            Bar.Url = webViewState.it?.url ?: ""
            log("Bar.Url = ${Bar.Url}")
            delay(1000)
        }
    }
    AndroidView(
        factory = { context ->
            val rootView = LayoutInflater.from(context).inflate(R.layout.web, null, no)
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
                    url?.let { onUrlChanged(it) }
                }

                override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
                    val raw = "${request.url}"
                    // log("raw = $raw")
                    
                    // YOUR CUSTOM CODE decides
                    val stop = loadPage(view, raw)

                    if (url.endsWith(".jpg", yes) ||
                            url.endsWith(".jpeg", yes) ||
                            url.endsWith(".png", yes) ||
                            url.endsWith(".gif", yes) ||
                            url.contains("i.ytimg.com")
                       ) {
                        return WebResourceResponse("text/plain", "utf-8", null)
                    }

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

            myWebView.loadUrl("https://www.google.com/search?q=$url")
            webViewState.it = myWebView

            rootView
        },
        modifier = Modifier.maxS(),
        update = { view ->
            val myWebView = view.findViewById<WebView>(R.id.myWebView)
            if (myWebView.url.isNullOrEmpty()) {
                myWebView.loadUrl("https://www.google.com/search?q=$url")
            }
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

fun goBackWeb(webView: WebView?) {
    webView?.post {
        if (webView?.canGoBack()==yes) {
            webView.goBack()
        }
    }
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
    webView: m_<WebView?>,
    keywords: List<Str>,
    Do: Do = { },
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






