package com.productivity.wind.Screens
  
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.heightIn
import com.productivity.wind.MAINStart
import com.productivity.wind.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.*
import kotlin.system.*
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import androidx.compose.foundation.*
import androidx.compose.ui.focus.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.activity.compose.*


@Composable
fun Main() {

	RunOnce{
		MAINStart()
	}
	var searching by r(no)
	var Tag = r("")

	BtnFloating {
		goTo("Challenge")
	}
	
	LazyScreen(
	top = { 
		if (!searching) {
			Icon.Menu {
				menu = yes
			}

			// Icon.Chill { goTo("Web") }

			Icon.Reload{
				showOrderNotification(11)
			}
        
			move(w = 12)
        
			Text("Points ${Bar.funTime}")
        
			End {
				Icon.Search {
					searching = yes
				}
			}
		} else {
			Icon.Back {
				searching = no
			}
			LazyInput(Tag, modifier = Mod.h(40).w(250).Hscroll())
			
			BackHandler {
				searching = no
			}
		}
	}, 
	showBack = no
) {
	Column(Mod.Vscroll()){

		if (Tag == "") {
			
		Bar.copyTsk.each {
			if (!it.done()){
				LazyCard { 
					CopyTskUI(it)
				}
			}
		}

		Bar.doTsk.each {
			if (!it.done() && taskDueToday(it.schedule)){
					
				LazyCard(
					modUI = Mod.space(start = 8),
					modCard = Mod.space(h=8, w=10).maxW().click {    
						goTo("ToDo/${it.id}")
					},
				) { 
					DoTskUI(it)
				}
			}
		}


		Bar.apps.each {
			if (!it.done) {
				if (it.NowTime > it.DoneTime - 1 && !it.done) {
					Bar.funTime += it.Worth
					Bar.apps.edit(it) { done = yes }
					Vlog("${it.name} completed")
				}
                    
				Item.AppTaskUI(it)
			}
		}
		} else {
			input
			name
			description
			


			
		}


		
	}
}}

		



















				
