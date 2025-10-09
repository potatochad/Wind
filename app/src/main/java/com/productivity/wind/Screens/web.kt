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

fun BlockKeywords(
    webView: m_<WebView?>,
    keywords: List<Str>,
    Do: Do = { Vlog("Blocked keyword") },
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
                    return@evaluateJavascript
                }
            }
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
            val url = UrlShort(webView.value?.url ?: "https://google.com")
            val scrollState = rememberScrollState()
            Row(Modifier.scroll(vertical=no).width(App.screenWidth/3)) {
                Text("$url")
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
        WebXml(
            webViewState = webView,
            onUrlChanged = {
                BlockKeywords(webView, "mrbeast")
            },
            onProgressChanged = {
                BlockKeywords(webView, "mrbeast")
            },
            onPageStarted = {
                BlockKeywords(webView, "mrbeast")
            },
            onPageFinished = { 
                BlockKeywords(webView, "mrbeast")
            }
        )
    }
}


