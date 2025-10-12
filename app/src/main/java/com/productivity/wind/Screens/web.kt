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
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.*


var WebUrl by m("")
@Composable
fun Web(){
    val webView = r { mutableStateOf<WebView?>(null) }
    val badWords = listOf("mrbeast", "tiktok", "instagram", "clickbait")

    WebUrl = "${UrlShort(webView.value?.url ?: "https://google.com")}"
       


    Item.WebPointTimer()

    LazyScreen(
        title = {
            Text(" Points ${Bar.funTime}")
    
            UI.End {
                Row{
                    Icon.Reload(webView)
                    Icon.Add {
                        goTo("BlockKeyword")
                    }
                }
            }
        },
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
fun BlockKeyword() {
    var BadWord = r_m("Youtube")

    LazyScreen(
        title = {
            Row(
                Modifier
                    .scroll(vertical = no)
                    .width(App.screenWidth / 2),
            ) {
                Text("$WebUrl")
            }
            UI.End {
                Icon.Add {
                    Bar.badWords.new { word = BadWord.it }
                }
            }
        },
    ) {
        LazyRuleCard("If") {
            LazzyRow {
                Text("Detect ")
                UI.Cinput(BadWord)
            }
        }
        LazyRuleCard("Do") {
            LazzyRow {
                Text("Go back")
            }
        }

        LazyCard {
            LazzyList(Bar.badWords, Modifier.maxWidth()) {it, index ->

                LazzyRow {
                    Text(text = it.word)

                    Icon.Edit {
                        Bar.badWords.edit(it) { it.word }
                    } //

                    Icon.Delete {
                        Bar.badWords.remove(it)
                    }
                }
            }
        }
    }
}









