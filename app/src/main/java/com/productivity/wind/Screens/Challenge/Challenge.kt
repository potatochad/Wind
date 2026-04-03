package com.productivity.wind.Screens.Challenge
  
import android.annotation.SuppressLint
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.*
import com.productivity.wind.R
import com.productivity.wind.Imports.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import androidx.compose.foundation.text.selection.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.geometry.*
import com.productivity.wind.Imports.UI_visible.*
import com.productivity.wind.Screens.*
 


@Composable
fun Challenge() {
  LazyScreen("Challenge") {
    LazyItem(
            BigIcon = Icons.Filled.AccessTime,
            BigIconColor = darkBlue,
            title = "App Usage",
            onClick = { 
              goTo("AppUsage/_")
            },
        )
    LazyItem(
            BigIcon = Icons.Filled.ContentPaste,
            BigIconColor = Color(0xFF1E88E5), 
            title = "Copy Paste",
            onClick = { 
              goTo("CopyPaste/_")
            },
        )
	LazyItem(
            BigIcon = Icons.Filled.ContentPaste,
            BigIconColor = gold, 
            title = "To do",
            onClick = { 
              goTo("ToDo/_")
            },
        )
    
   }
}


@Composable
fun CopyPaste(id: Str ="") {
    var txt = r("Be always kind")
    var maxDone = r(5)
    var donePts = r(10)
    var letterPts = r(1)

	val inputScroll = Scroll()
    
    LoadItemFromId(id, Bar.copyTsk) { t ->
		txt.it = t.txt
		maxDone.it = t.maxDone
		donePts.it = t.donePts
		letterPts.it = t.letterPts

		wait {
			inputScroll.goTo(t.goodStr() * 2)
		}
	}

    LazyScreen(top = { 
        Header.CopyPaste(txt, maxDone, donePts, letterPts, id) 
    }) {
        RuleCard("If") {
            LazzyRow {
                Text("Letter typed correctly: ")
                TinyInput(letterPts)
                Text(" points")
            }
            LazzyRow {
                Text("Text typed correctly: ")
                TinyInput(donePts)
                Text(" points")
            }
        }

        RuleCard("Other") {
            LazzyRow {
                Text("DailyMax: ")
                TinyInput(maxDone)
            }
			ScrollInput(txt, scroll = inputScroll)
        }
    }
}






@Composable
fun CopyTskUI(tsk: CopyTsk) {
    val txtScroll = LazyList() 
	var txtScrollSize by m(txtScroll.size)
	val inputScroll = Scroll()
	var TxtLines by r(listOf<Str>())
	
	var goodStr by r(tsk.goodStr())
	var goodStr2 by r(0)

    RunOnce(goodStr) {
		if (goodStr > 30) {
			wait(100) {
				MeasureWaitLag("centering Txt"){
					txtScroll.goToLineCentered(
						TxtLines.lineIndexByChar(goodStr)
					)
				}
			}
		}
	}
	RunOnce {
		wait {
			txtScrollSize = txtScroll.size
		}
		inputScroll.toBottom()
	}

    
    LazzyRow {
        Text("Done: ${tsk.doneTimes}/${tsk.maxDone}")
        End { 
			Item.Edit{
                Item.enoughPoints {
					goTo("CopyPaste/${tsk.id}")
                }
            }

			Item.Delete{ 
				Bar.copyTsk.remove(tsk)
			}
		}
    }
	


	BoxWithConstraints {
		val maxWidthPx = constraints.maxWidth.toFloat() // pixels
		val lines = tsk.txt.toLines(maxWidthPx)
		TxtLines = toListStr(lines)

		fun MakeProcessedLines(): List<UIStr> {
			var sum = 0
		
			return lines.change { txt ->
				val lineStart = sum
				sum += txt.size
				val lineEnd = sum

				val greenChar = goodStr - lineStart
				fun uiStrGreen() = txt.fromTo(0, greenChar).green()
				fun uiStrNormal() = txt.fromTo(greenChar, txt.size)

				return@change when {
					(goodStr <= lineStart) -> UIStr(txt)
					(goodStr >= lineEnd) -> txt.green()
				    else -> UIStr(uiStrGreen(), uiStrNormal())
				}
			}
		}

		val processedLines = remember(tsk.txt, goodStr) {
			MeasureLag("making processedLines"){
				MakeProcessedLines()
			}
		}
		
		


		
		LazyColumn(
			modifier = Mod.space(bottom = 15, start = 15).h(0, 100).maxW(),      
			state = txtScroll
		) {
			items(
				items = processedLines,
				key = { it.hashCode() }
			) { txtUI ->
				Text(txtUI)
			}
		}
	}

	
	
	
	
			
	
	var txt = r(tsk.input)
    ScrollInput(txt, scroll = inputScroll) {
		if (it.size - txt.it.size < 2) {
			txt.it=it
			
			tsk.edit{ input = it }

			if (goodStr < tsk.goodStr()) {
				Bar.LettersTyped++
				Bar.funTime += tsk.letterPts
			}

			if (tsk.input == tsk.txt) {
				Vlog("done")
				
				tsk.edit{
					doneTimes +=1
					input = ""
				}
				
				Bar.funTime += tsk.donePts
				txtScroll.goTo(0)
				txt.it=""
			}
        }
		goodStr = tsk.goodStr()
    }
}





@Composable
fun AppUsage(id: Str = "") {
    var time1 = r(60)
    var worth1 = r(10)
    var name1 by r("app")
	var app1 by r<AppTsk?>(null)

	var show = r(no)
	
	selectApp(show){
		name1 = it
	}
  
    LoadItemFromId(id, Bar.apps) { a ->
		time1.it = a.DoneTime
		worth1.it = a.Worth
		name1 = a.name
		app1 = a
	}

    LazyScreen(top = {
		Text("AppUsage")
        
        End {
			Item.ItemDelete(Bar.apps, app1){
				goTo("Main")
			}

			Item.FancyAdd(
				list = Bar.apps,
				item = app1,
				stop = { 
					when {
						isUsageP_Enabled() == no -> { 
							goTo("AllowAppUsage")
							yes
						}
						time1.it < 1 -> {
							Vlog("Add time")
							yes
						}
						name1 == "app" -> {
							Vlog("Select app")
							yes
						}
						else -> no
					}
				},
				newItem = { AppTsk() },
			){ x ->
				x.edit {
					pkg = getAppPkg(name1)
                    name = name1
                    DoneTime = time1.it
                    Worth = worth1.it
				}  
				goTo("Main")
			}
        }
    }) {
		RuleCard("If"){
		  LazzyRow{
			  Text("Spend ")
			  TinyInput(time1)
			  Text(" seconds")
          
			  Text(" on ")
			  Ctext(name1) {
				  show.it = yes
			  }
		  }
      }
      RuleCard("Do"){
		  LazzyRow{
			  Text("Add ")
			  TinyInput(worth1)
			  Text(" points")
		  }
      }
	  

    }
}

@Composable
fun ToDo(id: Str = "") {
    var time1 = r(60)
    var points1 = r(10)
    var name1 = r("TaskName")
	var description1 = r("")
	var schedule1 by r(Schedule(
	    	type = "WEEKLY",
	    	every = 1,
	    )
	)
	var todo by r<DoTsk?>(null)
	
	
  
    LoadItemFromId(id, Bar.doTsk) { t ->
		description1.it = t.description
		time1.it = t.doneTime
		points1.it = t.worth
		name1.it = t.name
		schedule1 = t.schedule
		todo = t
	}

    LazyScreen(top = {
        Text("ToDo")
        
        End {
			Item.ItemDelete(Bar.doTsk, todo){
				goTo("Main")
			}

			Item.FancyAdd(
				list = Bar.doTsk,
				item = todo,
				stop = { 
					when {
						time1.it == 0 -> {
							Vlog("Add time")
							yes
						}
						name1.it == "" -> {
							Vlog("Add name")
							yes
						}
						else -> no
					}
				},
				newItem = { DoTsk() },
			){ x ->
				var gotPerm = Permission.ignoreOptimizations()
				if (!gotPerm) Vlog("recommended permission")

				x.edit {
					name = name1.it
					doneTime = time1.it
					worth = points1.it
					schedule = schedule1
					description = description1.it
				}  
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
fun DoTskUI(tsk: DoTsk) = LazzyRow {

	//compose friendly
	var tskOn by r(tsk.on)
	var timeWorked by r(tsk.didTime)

	RunOnce(tskOn, timeWorked){
		tsk.edit {
			on = tskOn
			didTime = timeWorked
		}
	}

	
	RunOnce {
		tskOn = no

        while (yes) {
			wait(1000)
			
			if (tskOn && !Bar.leftApp){
				log("${tsk.name}: ${Time(tsk.timeLeft)}")
		
				timeWorked++

				if (tsk.didTime > timeWorked) timeWorked = tsk.didTime

				tsk.edit {
					didTime = timeWorked
				}
				
				Bar.funTime++	

				start(ForEverService::class.java)

			}
			if (tsk.done()){
				log("task is done")
				tskOn = no
			}
		}
		
	}

	Icon.Timer(tskOn) {
		tskOn = !it
	}
	move(5)
	Column(Mod.space(top = 5, bottom = 2)) {
		Text("${tsk.name}: ${Time(tsk.timeLeft)}")
		
		if (tsk.description != ""){
			LazzyRow {
				Text(tsk.description.take(100).size(11).gray())
			}
		}
	}
	
	log("WHAT USER SEES| ${tsk.name}: ${Time(tsk.timeLeft)}")
	
}




