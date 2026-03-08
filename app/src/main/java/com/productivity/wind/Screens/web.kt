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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*


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
		onBackClick = { goTo("Main") }
    ) {
        WebXml(web)
    }
}




@Composable
fun WebKeywords() {
	var filterAction by r(WebAction.Block)
	var Tag = r("")

	
	/*
	TinyInput(Tag, Mod.h(40).weight(1f), isInt = no, maxLetters = 400)
				
				move(30)
				BackHandler { stopSearch() }
			}
		}, 
		showBack = no
	) {


	it.name.contains(Tag.it)
	
	
	*/
	
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
				BtnAllow(filterAction) {
					filterAction = WebAction.Allow
				}
				BtnBlock(filterAction) {
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
private fun BtnAllow(
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
private fun BtnBlock(
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


@Composable
fun WebWordConfigure(id: Str = "") {
    var word1 = r(UrlShort(Bar.Url))
	var action1 by r(WebAction.Block)
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
					goTo("Main")
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
				LazzyRow {
					BtnAllow(action1) {
						action1 = WebAction.Allow
					}
					BtnBlock(action1) {
						action1 = WebAction.Block					
					}
				}
				LazzyRow {
					TinyInput(word1, Mod.weight(1f), isInt = no, maxLetters = 800)      
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

