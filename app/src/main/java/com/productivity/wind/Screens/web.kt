package com.productivity.wind.Screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import android.os.*
import android.webkit.*
import androidx.activity.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.*
import com.productivity.wind.Imports.*

@Composable
fun Web() {
    var url = r { m("https://www.google.com") }
        
    LazyScreen(
        title = {
            UI.Cinput(what=url, WidthMax=150)
        },
    ) {
        LazyBrowser(url)
    }
}



@Composable
fun LazyBrowser(url: MutableState<String>) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            WebView.setWebContentsDebuggingEnabled(true)
            settings.BrowserSettings()

            loadUrl(url.value)
        }
    }, update = { webView ->
        if (webView.url != url.value) webView.loadUrl(url.value)
    }, modifier = Modifier.fillMaxSize())
}
