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
import com.productivity.wind.Imports.Utils.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.graphics.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.compose.ui.platform.*

import androidx.compose.material3.pulltorefresh.*
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator

@Composable
fun Web(){
    //returns class: WebController
    var ctx = LocalContext.current
    val web = r { WebController(ctx) }
    
    web.applyFancySettings()
    
    web.url(Bar.Url)
        
    Item.WebPointTimer()

    web.doUpdateVisitedHistory { url, isReload ->
        web.allVisibleText { txt ->
            Vlog("history updated")
            log("full html: [$txt]", 2000)

            var blocked = false

            for (y in Bar.webWord) {
                if (txt.contains(y.word, ignoreCase = true)) {
                    blocked = true
                    web.back()
                    Vlog("blocking, bad word detected: $y")
                    break
                }
            }

            if (!blocked) {
                Bar.Url = url ?: "https://www.google.com"
            }
        }
    }

    LazyScreen(
        top = {
            Text(" Points ${Bar.funTime}")
    
            End {
                Row{
                    Icon.Reload{ 
                        web.reload()
                    } 
                    Icon.Delete {
                        Bar.Url = "https://google.com"
                    }
                    Item.Add {
                        goTo("WebKeywords")
                    }
                }
            }
        },
        scroll = no,
        DividerPadding = no,
    ) {
        WebXml(web)
    }
}




@Composable
fun WebKeywords() {
    LazyScreen(
        top = {
			
            }
        },
    ) {
        Bar.webWord.findUI({yes}) {
            LazyCard(
				modUI = Mod.space(start = 8),
				modCard = Mod.space(h=8, w=10).maxW()
			) { 
                LazzyRow {
                    Text(text = it.word)

                    Item.Edit {
                        Item.enoughPoints {
                            Bar.webWord.edit(it) {
                                word = word1.it
                            }
                        }
                    }

                    Icon.Delete {
                        Item.enoughPoints {
                            Bar.webWord.remove(it)
                        }
                    }
                }
            }
        }


		
    }
}


@Composable
fun WebWordConfigure() {
	var url by r(UrlShort(Bar.Url))
    var word1 = r(url)
	var action1 by r(WebAction.Block)
	
    LazyScreen(
		top = {
			Text("Configure")
            End {
                Icon.MoreMenu{
                    goTo("filterExtraWeb")
                }
				Item.Add {
                    Bar.webWord.add {
                        word = word1.it
						action = action1
					}
				}
			}
		}
	) {
        Card {
			Column{
				LazzyRow {
					Ctext(
						"Allow",
						mod = Mod.space(5),
						animate = yes,
						selected = action1 == WebAction.Allow,
					) {
					
					}
					Ctext(
						"Block",
						mod = Mod.space(5),
						animate = yes,
						selected = action1 == WebAction.Block,
					) {
					
					}
				}
				TinyInput(word1, Mod.weight(1f), isInt = no, maxLetters = 800)   
			}
		}
    }
}



@Composable
fun filterExtraWeb() {
    LazyScreen("fancy blocking") {
        
    }
}

