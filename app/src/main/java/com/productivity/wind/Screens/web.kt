package com.productivity.wind.Screens

import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import com.productivity.wind.*
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.productivity.wind.R
import com.productivity.wind.Imports.*
import android.annotation.SuppressLint
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.graphics.Bitmap
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.compose.*
import com.productivity.wind.Imports.Data.Bar
import com.productivity.wind.Imports.Data.Bool
import com.productivity.wind.Imports.Data.Do
import com.productivity.wind.Imports.Data.Str
import com.productivity.wind.Imports.Data.it
import com.productivity.wind.Imports.Data.m_
import com.productivity.wind.Imports.Data.no
import com.productivity.wind.Imports.Data.r


fun goBackWeb(webView: WebView?) {
    if (webView?.canGoBack() == true) {
        webView.goBack()
    }
}

fun BlockKeyword(
    webView: m_<WebView?>,
    keyword: Str,
    Do: Do = { Vlog("Blocked keyword") },
) {
    webView.it?.evaluateJavascript(
        "(function() { return document.body.innerText; })();"
    ) { html ->
        if (html.contains(keyword, ignoreCase = true)) {
            goBackWeb(webView.it)
            Do()
        }
    }
}


@Composable
fun Web(){
    val webView = r { mutableStateOf<WebView?>(null) }

    Item.WebPointTimer()

    LazyScreen(
        title = {
            Text(" Points ${Bar.funTime}: ")
            var Url = UrlShort("${webView.value?.url ?: https://google.com}")
            val scrollState = rememberScrollState()
            Row(Modifier.scroll(vertical=no).width(App.screenWidth/3)) {
                Text("$Url")
            }
            UI.End {
                Icon.Reload(webView)
                UI.move(10)
                Icon.Add {
                    
                }
            }
        },
        Scrollable = false,
        DividerPadding = false,
    ) {
        BrowseScreenXml(
            webViewState = webView,
            onUrlChanged = {
            
            },
            onProgressChanged = {},
            onPageStarted = {},
            onPageFinished = { 
                BlockKeyword(webView, "mrbeast")
            }
        )
    }
}








@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BrowseScreenXml(
    webViewState: m_<WebView?>,
    url: String = "",
    isDesktopSite: Bool = false,
    onUrlChanged: (String) -> Unit = {},
    onProgressChanged: (Int) -> Unit = {},
    onPageStarted: (String) -> Unit = {},
    onPageFinished: (String) -> Unit = {},
) {
    BackHandler {
        webViewState.value?.goBack()
    }

    AndroidView(
        factory = { context ->
            val rootView = LayoutInflater.from(context).inflate(R.layout.web, null, false)
            val myWebView = rootView.findViewById<WebView>(R.id.myWebView)

            myWebView.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
            }

            myWebView.webViewClient = object : WebViewClient() {
                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                    if (isDesktopSite) {
                        view?.evaluateJavascript(
                            "document.querySelector('meta[name=\"viewport\"]').setAttribute('content'," +
                                    "'width=1024px, initial-scale=' + (document.documentElement.clientWidth / 1024));",
                            null
                        )
                    }
                }

                override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    url?.let { onUrlChanged(it) }
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    url?.let { onPageStarted(it) }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
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
            webViewState.value = myWebView

            rootView
        },
        modifier = Modifier.fillMaxSize(),
        update = { view ->
            val myWebView = view.findViewById<WebView>(R.id.myWebView)
            if (myWebView.url.isNullOrEmpty()) {
                myWebView.loadUrl("https://www.google.com/search?q=$url")
            }
        }
    )
}
