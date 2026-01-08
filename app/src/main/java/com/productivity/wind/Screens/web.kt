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


@Composable
fun Web(){
    val web = r { mutableStateOf<Web?>(null) }
    
    web.url(Bar.Url)
        
    Item.WebPointTimer()

    LazyScreen(
        top = {
            Text(" Points ${Bar.funTime}")
    
            UI.End {
                Row{
                    Icon.Reload{ 
                        web.it?.reload()
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
    var url by r(Bar.Url)
    var BadWord = r_m("${Bar.Url.take(10)+"..."}")

    LazyScreen(
        top = {
            Row(Mod.Hscroll().w(AppW / 2)) {
                Text(Bar.Url, maxLines = 1)
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
                Column(Mod.Vscroll()){


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
    LazyScreen("fancy blocking") {
        
    }
}

