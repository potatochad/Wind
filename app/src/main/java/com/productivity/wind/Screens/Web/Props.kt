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
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape


object WebProps {


	@Composable
	fun TopBarInput(
		str: str = ""
		onDone: Do = {}
	){
		TinyInput(str, Mod.h(40).w(AppW - 180.dp), isInt = no, maxLetters = 400){       
			Bar.Url = it
		}
	}
	
	object Home {
		@Composable
		fun Input(web: WebController){
			LazyCard(
				modUI = Mod.space(start = 8),
				modCard = Mod.space(h=8, w=10).maxW().click {
					Bar.Url = "google.com"
					goTo("Web")
				},
			) {
				LazzyRow(Mod.centerV) {
					Icon.Search()
					move(8)
					Text("Search Google or type URL".gray().size(14.sp))
				}
			}
		}
		
		@Composable
		fun RecentCard(
			label: Str,
			Do: Do = {},
		){
			LazzyColumn {
				LazyCard(
					modUI = Mod.space(2),
					modCard = Mod
						.clip(CircleShape)
						.click{ 
							Do() 
						},
				) {
					LazyImage(
						Icons.Default.Add,
						Mod.clip(CircleShape).s(80),
					)
				}
					//Icon round
				Text(label.overFlow(10))
			}
		}

		
	}

	
}

@Composable
fun BtnAllow(
	action1: WebAction,
	Do: Do = {}
){
	Ctext(
		"Allow",
		mod = Mod.space(5),
		selected = action1 == WebAction.Allow,
	) {
		Do()
	}
}

@Composable
fun BtnBlock(
	action1: WebAction,
	Do: Do = {}
){
	Ctext(
		"Block",
		mod = Mod.space(5),
		selected = action1 == WebAction.Block,
	) {
		Do()				
	}
}



fun containsBadWord(txt: Str): Bool {
    for (y in Bar.webWord) {
        if (
            txt.contains(y.word, ignoreCase = true) &&
            y.action == WebAction.Block
        ) {
            return true
        }
    }
    return false
}

fun containsGoodWord(txt: Str): Bool {
    for (y in Bar.webWord) {
        if (
            txt.contains(y.word, ignoreCase = true) &&
            y.action == WebAction.Allow
        ) {
            return true
        }
    }
    return false
}

fun foundBadWord(txt: Str): Str {
    for (y in Bar.webWord) {
        if (
            txt.contains(y.word, ignoreCase = true) &&
            y.action == WebAction.Block
        ) {
            return y.word
        }
    }
    return ""
}


