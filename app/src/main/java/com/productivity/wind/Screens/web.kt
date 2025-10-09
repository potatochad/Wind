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
import com.productivity.wind.Imports.Data.*



var WebUrl by m("")
@Composable
fun Web(){
    val webView = r { mutableStateOf<WebView?>(null) }
    val badWords = listOf("mrbeast", "tiktok", "instagram", "clickbait")


    Item.WebPointTimer()

    LazyScreen(
        title = {
            Text(" Points ${Bar.funTime}: ")
    
            UI.End {
                Icon.Reload(webView)
                UI.move(10)
                Icon.Add {
                    goTo("BlockKeyword")
                }
            }
        },
        Scrollable = false,
        DividerPadding = false,
    ) {
        WebXml(
            webViewState = webView,
            onUrlChanged = {
                BlockKeywords(webView, badWords)
            },
            onProgressChanged = {
                BlockKeywords(webView, badWords)
            },
            onPageStarted = {
                BlockKeywords(webView, badWords)
            },
            onPageFinished = { 
                BlockKeywords(webView, badWords)
            }
        )
    }
}


@Composable
fun BlockKeyword(){
    LazyScreen(
        title = {
            WebUrl = "${UrlShort(webView.value?.url ?: "https://google.com")}"
            val scrollState = rememberScrollState()
            Row(Modifier.scroll(vertical=no).width(App.screenWidth/3)) {           
                Text("$WebUrl")
            }
            UI.End {
                UI.move(10)
                Icon.Add {
                    
                }
            }
        },
    ) {
        
    }
}









