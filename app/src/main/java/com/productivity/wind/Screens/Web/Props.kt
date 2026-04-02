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
import com.productivity.wind.Imports.Utils.Browser.*


object WebProps {
	@Composable
	fun BtnAllow(
		action1: WebAction,
		Do: Do = {}
	){
		Ctext(
			"Allow", Mod.space(5),
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
			"Block", Mod.space(5),
			selected = action1 == WebAction.Block,
		) {
			Do()				
		}
	}

	@Composable
	fun BtnsTypes(webAction: WebAction, type: WebType, Do: Do_<WebType>){
		Ctext(
			"Url", Mod.space(5),
			selected = type == WebType.Url,
		) {
			Do(WebType.Url)				
		}
		Ctext(
			"Blot", Mod.space(5),
			selected = type == WebType.Blot,
		) {
			Do(WebType.Blot)			
		}
		Ctext(
			"KeyWord", Mod.space(5),
			selected = type == WebType.KeyWord,
		) {
			Do(WebType.KeyWord)				
		}
	}


	@Composable
	fun TopBarInput(
		web: WebController
	) {
		var Input1 by r(Bar.Url)
		TinyInput(
			Input1, 
			Mod.h(40).w(AppW - 180.dp), 
			isInt = no, 
			maxLetters = 400,
			onAction = {
				Bar.Url = Input1
				web.url(Bar.Url)
			},
		){       
			Input1 = it
		}
	}

	object Popup {
		@Composable
		fun WebWordLock(
			locked: mBool,
		){
			var show = r(locked.it)

			var oldLocked by r(locked.it)
			RunOnce(locked.it){
				if (locked.it != oldLocked){
					if (locked.it) show.it = yes
				}
				oldLocked = locked.it
			}
			
			LazyPopup(
				show, 
				"Are you sure?",
				"Locked keywords can NOT be editted, deleted, can NOT reverse choice, ONLY the global settings page option restore can REVERSE it",    
				onCancel = {
					if (locked.it) locked.it = no
				},
			)
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


object WebUtils {
	fun FindBadWord(txt: Str): Str {
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
	fun EachFoundBadWord(
		txt: Str,
		Do: Do_<WebWord>,
	){
        for (y in Bar.webWord) {
            if (txt.contains(y.word, ignoreCase = true) && y.action == WebAction.Block) {
				Do(y)
            }
        }
	}
	fun HasBadWord(
		txt: Str,
		extraCheck: (WebWord) -> Bool = { yes }
	): Bool {
		for (y in Bar.webWord) {
			if (
				txt.contains(y.word, ignoreCase = true) &&
					y.action == WebAction.Block &&
					extraCheck(y)
			) {
				return true
			}
		}
		return false
	}

	fun HasGoodWord(
		txt: Str,
		extraCheck: (WebWord) -> Bool = { yes },
	): Bool {
		for (y in Bar.webWord) {
			if (
				txt.contains(y.word, ignoreCase = true) &&
					y.action == WebAction.Allow && extraCheck(y)
			) {
				return yes
			}
		}
		return no
	}
	fun EachFoundGoodWord(
		txt: Str,
		Do: Do_<WebWord>,
	){
        for (y in Bar.webWord) {
            if (txt.contains(y.word, ignoreCase = true) && y.action == WebAction.Allow) {
				Do(y)
            }
        }
	}

	fun FindGoodWord(txt: Str): Str {
		for (y in Bar.webWord) {
			if (
				txt.contains(y.word, ignoreCase = true) &&
					y.action == WebAction.Allow
			) {
				return y.word
			}
		}
		return ""
	}

	fun IsGood(ask: WebResourceRequest): WebAction {
		fun Block(){
			return@IsGood WebAction.Block
		}
		fun Allow(){
			return@IsGood WebAction.Allow
		}
		fun Blot(){
			return@IsGood WebAction.Blot
		}
		
		var Blot = WebType.Blot
		var KeyWord = WebType.KeyWord

		val url = toStr(ask.url))


		
		val badWords = mList<WebWord>()
		var goodWords by mList<WebWord>()

		fun AddBadWord(type1: WebType){
			badWords.add{ type = type1 }
		}
		fun AddGoodWord(type1: WebType){
			goodWords.add{ type = type1 }
		}
		
		
		
		EachFoundBadWord(url) {
			if (it.locked) Block()
			when (it.type) {
				Blot -> AddBadWord(Blot)
				KeyWord -> AddBadWord(KeyWord)
			}
		}

		if (badWords.empty) Allow()

		

		WebUtils.EachFoundGoodWord(url){ x ->
			when (x.type) {
				Blot -> AddGoodWord(Blot)
				KeyWord -> AddGoodWord(KeyWord)
			}
		}

		

		if (badWords.any { x ->
			x.type == WebType.Blot && 
			goodWords.empty &&
			if (badWords.none { it.type == WebType.KeyWord })
		}){
			Blot()
		}
		if (goodWords.any { 
			it.type == WebType.KeyWord &&
			
		}){
			Allow()
		} else
		
						
		Allow()
	}

	
	
}
















