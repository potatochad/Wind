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
import androidx.compose.foundation.shape.*
import androidx.compose.ui.text.style.*





var WebUrl by m("")
@Composable
fun Web(){
    val webView = r { mutableStateOf<WebView?>(null) }
    val badWords = mutableListOf<Str>()
    var on = r_m(yes)

    RunOnce {
        webView.it?.loadUrl("https://youtube.com")
        Bar.badWords.forEach {
            badWords += it.word
        }
    }
    RunOnce(webView.it?.url){
        BlockKeywords(webView, badWords)
    }

    
    WebUrl = "${webView.it?.url ?: "https://youtube.com"}"
       


    Item.WebPointTimer(on)

    LazyScreen(
        title = {
            Text(" Points ${Bar.funTime}")
    
            UI.End {
                Row{
                    Icon.Reload{ 
                        webView.it?.reload()
                    } 
                    Icon.Add {
                        goTo("BlockKeyword")
                    }
                }
            }
        },
        DividerPadding = no,
    ) {
        WebXml(
            webViewState = webView,
            loadPage={ web, url ->
                Bar.badWords.each {
                    if (url.contains(it.word)) {
                        web?.loadUrl("https://www.google.com")
                        no
                    } else yes
                }
            },
        )
    }
}




@Composable
fun BlockKeyword() {
    var BadWord = r_m("someWord")

    RunOnce(Bar.badWords) {
        BadWord.it = "${Bar.badWords.size}"
    }

    LazyScreen(
        title = {
            Row(
                Modifier
                    .scroll(h = yes)
                    .w(App.screenWidth / 2),
            ) {
                Text(WebUrl, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            UI.End {
                Icon.MoreMenu{
                    goTo("filterExtraWeb")
                }
                Icon.Add {
                    Bar.badWords.add {
                        word = BadWord.it
                    }
                }
            }
        },
    ) {
        LazyRuleCard("If") {
            LazzyRow {
                Text("Detect ")
                Item.TskInput(BadWord, maxLetters = 12, w=120, isInt=no)
            }
        }

        LazyRuleCard("Do") {
            LazzyRow {
                Text("Go back")
            }
        }

        LazyCard {
            Bar.badWords.forEach {
                Column(Modifier.Vscroll()){


                LazzyRow {
                    Text(text = it.word)

                    Icon.Edit {
                        Item.enoughPoints{
                            Bar.badWords.edit(it) {
                                word = BadWord.it
                            }
                        }

                    }

                    Icon.Delete {
                        Item.enoughPoints {
                            Bar.badWords.remove(it)
                        }
                    }
                }}
            }
        }
    }
}



@Composable
fun filterExtraWeb() {
    LazyScreen(
        title = {
            Text("fancy blocking")
        },
    ) {
        
    }
}

