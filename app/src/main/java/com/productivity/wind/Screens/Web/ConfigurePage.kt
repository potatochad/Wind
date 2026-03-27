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
fun WebKeywords() {
	var filterAction by r(WebAction.Block)
	
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
			LazzyRow(Mod.centerH) {
				WebProps.BtnAllow(filterAction) {
					filterAction = WebAction.Allow
				}
				WebProps.BtnBlock(filterAction) {
					filterAction = WebAction.Block					
				}
			}
		}
		
	
        Bar.webWord.findUI({
			it.action == filterAction
		}) {
            LazyCard(
				modUI = Mod.space(10),
				modCard = Mod.space(10).maxW().click {    
					goTo("WebWordConfigure/${it.id}")
				},
			) { 
                LazzyRow {
                    Text(text = it.word)
                }
            }
        }

		
    }
}


@Composable
fun WebWordConfigure(id: Str = "") {
    var word1 = r(UrlShort(Bar.Url))
	var action1 by r(WebAction.Block)
	var type1 by r(WebAction.Block)
	var schedule1 by r(Schedule(
	    	type = "WEEKLY",
	    	every = 1,
	    )
	)
	var webWord by r<WebWord?>(null)

	/*
	WebWord(
    val id: Str = Id(),
	var word: Str = "",
	var schedule: Schedule = Schedule(),
	var action: WebAction = WebAction.Block,
)
	*/
	
    if (!id.isEmpty()) {
      webWord = Bar.webWord.find { it.id == id }

      if (webWord != null) {
		  val t = webWord!!
		  word1.it = t.word
		  action1 = t.action
		  schedule1 = t.schedule
	   }
    }

    LazyScreen(top = {
        Text("Configure")

        End {
			
			if (webWord != null) {
				Item.Delete { 
					webWord!!.remove()
					goTo("WebKeywords")
				}
			}
			

			Icon(
				if (webWord != null) Icons.Default.Edit
				else Icons.Default.Add
			) {
				log("clicked icon ")
                if (word1.it== "") {
					Vlog("Add time")
					return@Icon
				}
				log("passed the check")


                if (!id.isEmpty()) {
                    val wordFound = Bar.webWord.find { it.id == id }
					log("found something")
					
                    if (wordFound!=null){
						log("tsk not null found")
						
                        wordFound.edit {
							action = action1
							word = word1.it
							schedule = schedule1
                        }  
						log("going to WebKeywords")
                        goTo("WebKeywords")
                    }
                    return@Icon
                }

				Vlog("adding task")

                Bar.webWord.add {
                    action = action1
					word = word1.it
					schedule = schedule1
                }
				Vlog("going to WebKeywords")
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
					WebProps.BtnsTypes(type1) {
						type1 = it
					}
				}
				
			}
		}
		RuleCard("Schedule"){
			ScheduleUI(schedule1) {
				schedule1 = it
			}
		}
	  

    }
}



@Composable
fun filterExtraWeb() {
    LazyScreen("fancy blocking") {
        
    }
}
