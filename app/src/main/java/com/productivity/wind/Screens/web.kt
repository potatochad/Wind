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
		  word1 = t.word
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
							name = name1.it
							doneTime = time1.it
							worth = points1.it
							schedule = schedule1
							description = description1.it
                        }  
						log("going to main")
                        goTo("Main")
                    }
                    return@Icon
                }

				Vlog("adding task")

                Bar.doTsk.add {
                    name = name1.it
					doneTime = time1.it
					worth = points1.it
					schedule = schedule1
					description = description1.it
                }
				Vlog("going to main")
                goTo("Main")
            }
		}
	}) {
		RuleCard("Info"){
			LazzyRow(Mod.space(bottom = 5)){
				TinyInput(name1, Mod.weight(1f), isInt = no, maxLetters = 800)           
			}
			BigInput(description1, Mod.wrapContentHeight()) {
				description1.it = it
			}
			LazzyRow{
				Text("Time")
				
				TinyInput(time1)
				Text(" seconds")
			}
			LazzyRow(Mod.space(w=5)){
			    Text("On done")
			    TinyInput(points1)
			    Text(" points")
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

