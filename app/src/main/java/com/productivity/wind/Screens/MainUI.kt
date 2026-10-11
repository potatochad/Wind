package com.productivity.wind.Screens

import com.productivity.wind.Imports.Utils.NavControl.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.productivity.wind.MAINStart
import com.productivity.wind.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.*
import androidx.compose.runtime.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.activity.compose.*
import com.productivity.wind.Screens.Task.*


@Composable
fun Main() {
	RunOnce { MAINStart() }
	
	var searching by r(no)
	var Tag = r("")

	BtnFloating { goTo("Challenge") }

	fun stopSearch(){
		Tag.it = ""
		searching = no
	}
	
	LazyScreen(
		top = { 
			if (!searching) {
				AppItem.Menu()
				
				Icon.Chill { goTo("Web") }

				Icon.Reload{
					// showOrderNotification(11)
				}
				move(12)
				Text("Points ${Bar.funTime}")
        
				End { 
					Icon.Search {
						searching = yes
					}
				}
			} else {
				Icon.Back { stopSearch() }
				TinyInput(Tag, Mod.h(40).weight(1f), isInt = no, maxLetters = 400)
				move(30)
				BackHandler { stopSearch() }
			}
		}, 
		backIcon = no
	) {
		
		Bar.copyTsk.findUI({ 
			if (searching) it.input.contains(Tag.it) else !it.done()
		}) { 
			LazyCard { 
				CopyTskUI(it) 
			} 
		}

		Bar.doTsk.findUI({ 
			if (searching) { it.name.contains(Tag.it) || it.description.contains(Tag.it) } else { !it.done() && taskDueToday(it.schedule) }					
		}) { 						
			LazyCard(
				modUI = Mod.space(start = 8),
				modCard = Mod.space(8, 10).maxW().click {    
					goTo("ToDo/${it.id}")
				},
			) { 
				DoTskUI(it)
			}
		}
		
		Bar.apps.findUI({ 
				if (searching) it.name.contains(Tag.it) else !it.done
		}) { 
			AppTaskUI(it) 
		}

			

	}
}







				
