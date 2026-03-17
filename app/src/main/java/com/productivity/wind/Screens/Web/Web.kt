package com.productivity.wind.Screens.Web

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import com.productivity.wind.Screens.*


@Composable
fun WebHome(){
    LazyScreen(
        top = {
            End {
                Item.Add {
                    goTo("WebKeywords")
                }
            }
        },
        scroll = no,
		onBackClick = { goTo("Main") }
    ) {
		
        LazzyRow(Mod.centerH.space(5)) {
			Text("Home".size(28.sp))
		}
		WebProps.Home.Input()

        move(h = 16)

        // Quick access cards (like Google shortcuts)
		Text("Recent".size(12.sp).gray(), modifier = Mod.space(start = 8))
		LazzyRow(Mod.centerH.space(5)) {
			LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
				item {
					LazyCard(
						modCard = Mod.s(80).click{},
					) {
						Text("Gmail")
					}
				}
				item {
					LazyCard(
						modCard = Mod.s(80).click{
							Bar.Url = "youtube.com"
							goTo("Web")
						},
					) {
						Text("Youtube")
					}
				}
				item {
					LazyCard(
						modCard = Mod.s(80).click{},
					) {
						Text("Maps")
					}
				}
			}
		}
    }
}


@Composable
fun Web(){
    //returns class: WebController
    var ctx = LocalContext.current
    val web = r { WebController(ctx) }

	RunOnce {
		web.applyFancySettings()
		web.url(Bar.Url)
	}
	if (Bar.Url == "") goTo("WebHome")
    
    Item.WebPointTimer()

	BlockingLogic(web)

    LazyScreen(
        top = {
            
			TinyInput(Bar.Url, Mod.h(40).w(AppW - 180.dp), isInt = no, maxLetters = 400){       
				Bar.Url = it
			}
				
            End {
                Row{
                    Icon.Reload{ 
                        web.reload()
                    } 
                    Item.Add {
                        goTo("WebKeywords")
                    }
                }
            }
        },
        scroll = no,
        DividerPadding = no,
		onBackClick = { goTo("Main") }
    ) {
        WebXml(web)
    }
}








