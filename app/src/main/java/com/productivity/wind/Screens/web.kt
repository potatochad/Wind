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
import androidx.compose.ui.graphics.*
import com.productivity.wind.Imports.UI_visible.*


@Composable
fun Web(){
    val web = r { mutableStateOf<Web?>(null) }
    
    web.url(Bar.Url)
        
    Item.WebPointTimer()

    LazyScreen(
        top = {
            Text(" Points ${Bar.funTime}")
    
            End {
                Row{
                    Icon.Reload{ 
                        web.it?.reload()
                    } 
                    Icon.Delete {
                        Bar.Url = "google.com"
                    }
                    Item.Add {
                        goTo("BlockKeyword")
                    }
                }
            }
        },
        DividerPadding = no,
    ) {
        WebXml(
            webViewState = web,
            onUrlChanged={
                Bar.Url = it

                web.txt{x->
                    //Vlog("htnl: [$x]")
                }

                Bar.badWords.each { y->
                    if (Bar.Url.contains(y.word, ignoreCase = yes)) {
                        goBackWeb(web)
                        return@each
                    }
                }
            },
        )
    }
}




@Composable
fun BlockKeyword() {
    var url by r(UrlShort(Bar.Url))
    var BadWord = r("${url.take(10)+"..."}")

    LazyScreen(
        top = {
            Row(
                Mod.Hscroll()
                    .w((AppW / 2)+30.dp)
                    .border(width = 1.dp, color = Color.LightGray)  
            ) {
                Text(url, maxLines = 1)
            }
            End {
                Icon.MoreMenu{
                    goTo("filterExtraWeb")
                }
                Item.Add {
                    Bar.badWords.add {
                        word = BadWord.it
                    }
                }
            }
        },
    ) {
        RuleCard("If") {
            LazzyRow {
                Text("Detect ")
                TinyInput(BadWord, maxLetters = 100, isInt=no)
            }
        }

        RuleCard("Do") {
            LazzyRow {
                Text("Go back")
            }
        }

        LazyCard {
            Bar.badWords.forEach {
                Column(Mod.Vscroll()){


                LazzyRow {
                    Text(text = it.word)

                    Item.Edit {
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
    LazyScreen("fancy blocking") {
        
    }
}

