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
            UI.Ctext(url)
        },
    ) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()   // keeps navigation inside
                    settings.apply {
                        javaScriptEnabled = true          // needed for most sites
                        domStorageEnabled = true          // enables localStorage/sessionStorage
                        databaseEnabled = true            // allows HTML5 DB APIs
                        cacheMode = WebSettings.LOAD_DEFAULT  // use cached resources when possible
                        builtInZoomControls = true
                        displayZoomControls = false
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        allowFileAccess = true            // allow file:// access
                        allowContentAccess = true         // allow content:// access
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW // https + http
                    }
                    // Optional: enable debugging for dev
                    WebView.setWebContentsDebuggingEnabled(true)

                    loadUrl(url.value)
                }
            },
            update = { webView ->
                if (webView.url != url.value) {
                    webView.loadUrl(url.value)
                }
            }
        )
    }
}



