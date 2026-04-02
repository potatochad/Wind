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

import com.productivity.wind.Imports.Utils.Browser.*


@Composable
fun WebKeywords() {
	var filterAction by r(WebAction.Block)
	var locked1 = r(no)
	
    LazyScreen(
		top = {
			Text("List")
			End {
				Item.Add {
					goTo("WebWordConfigure/_")
				}
			}
		},
		onBackClick = { goTo("Web") }
	) {
		LazyCard {
			LazzyRow(Mod.centerV){
				Weight {
					LazzyRow(Mod.centerH){
						WebProps.BtnAllow(filterAction) {
							filterAction = WebAction.Allow
						}
						WebProps.BtnBlock(filterAction) {
							filterAction = WebAction.Block					
						}
					}
				}
				Icon.Lock(locked1)
			}
		}
		
	
        Bar.webWord.findUI({
			it.action == filterAction &&
			if (locked1.it) it.locked else !it.locked
		}) {
            LazyCard(
				modUI = Mod.space(10),
				modCard = Mod.space(10).maxW().click {    
					if (!it.locked) goTo("WebWordConfigure/${it.id}")
				},
			) { 
                LazzyRow {
                    Text(text = it.word)
					End {
						if (it.locked) Icon.Lock(color = gold)
					}
                }
            }
        }

		
    }
}


@Composable
fun WebWordConfigure(id: Str = "") {
    var word1 = r(UrlShort(Bar.Url))
	var action1 by r(WebAction.Block)
	var type1 by r(WebType.Url)
	var locked1 = r(no)
	var schedule1 by r(Schedule(
	    	type = "WEEKLY",
	    	every = 1,
	    )
	)
	var webWord by r<WebWord?>(null)


	WebProps.Popup.WebWordLock(locked1)
	
	
    LoadItemFromId(id, Bar.webWord) { t ->
		word1.it = t.word
		action1 = t.action
		schedule1 = t.schedule
		locked1.it = t.locked
		type1 = t.type
		webWord = t
	}
	
    LazyScreen(top = {
        Text("Configure")

        End {
			Item.ItemDelete(Bar.webWord, webWord){
				goTo("WebKeywords")
			}

			Item.FancyAdd(
				list = Bar.webWord,
				item = webWord,
				stop = { 
					var stop = word1.it.empty
					if (stop) Vlog("Add time")
					stop
				},
				newItem = { WebWord() },
			){ x ->
				x.edit {
					action = action1
					word = word1.it
					schedule = schedule1
					locked = locked1.it
					type = type1
				}  
				goTo("WebKeywords")
			}
		}
	}) {
		LazyCard {
			Column{
				LazzyRow(Mod.centerH) {
					WebProps.BtnAllow(action1) {
						action1 = WebAction.Allow
					}
					WebProps.BtnBlock(action1) {
						action1 = WebAction.Block					
					}
				}
				LazzyRow {
					TinyInput(word1, Mod.weight(1f), isInt = no, maxLetters = 800)      
				}
				move(10)
				LazzyRow {
					Text("Type:")
					WebProps.BtnsTypes(action1, type1) {
						type1 = it
					}
				}
				LazzyRow {
					Text("Lock:")
					Icon.Lock(locked1)
				}
				
			}
		}
		LazyCard{
			LazyMore("Schedule"){
			ScheduleUI(schedule1) {
				schedule1 = it
			}
			}
		}
	  

    }
}



@Composable
fun filterExtraWeb() {
    LazyScreen("fancy blocking") {
        
    }
}
