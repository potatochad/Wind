package com.productivity.wind.Screens
  
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
import com.productivity.wind.Imports.Data.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.geometry.*
import com.productivity.wind.Imports.UI_visible.*

 


@Composable
fun Challenge() {
  LazyScreen("Challenge") {
    LazyItem(
            BigIcon = Icons.Filled.AccessTime,
            BigIconColor = DarkBlue,
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
            BigIconColor = Gold, 
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
    
    if (!id.isEmpty()) {
      val tsk = Bar.copyTsk.find { it.id == id }

      if (tsk != null) {
		  txt.it = tsk.txt
		  maxDone.it = tsk.maxDone
		  donePts.it = tsk.donePts
		  letterPts.it = tsk.letterPts
		  
		  wait {
			  inputScroll.goTo(tsk.goodStr() * 2)
		  }
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
			BigInput(txt, inputScroll)
        }
    }
}






@Composable
fun CopyTskUI(tsk: CopyTsk) {
    val txtScroll = LazyList() 
	val inputScroll = Scroll()
	
	var goodStr by r(tsk.goodStr())
	var goodStr2 by r(0)

    RunOnce(goodStr) {
		if (goodStr > 30) {
			wait {
				var scrollBy = toF(txtScroll.size)/toF(tsk.txt.size)
				val done = toF(goodStr)*scrollBy - scrollBy*50
				txtScroll.goTo(done) 
			}
		}
	}
	RunOnce {
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

			Icon.Delete{ 
				Bar.copyTsk.remove(tsk)
			}
		}
    }


	/*
	LazyText(
		bigText = tsk.txt, 
		mod = Mod.space(bottom = 15, start = 15).h(0, 100).maxW(),
		scroll = txtScroll,
	) { index, char ->
		if (index <= goodStr) {
			char.green()
		} else {
			char
		}
	}*/


	val lines = tsk.txt.toLines()

	val linesSize = remember(lines) {
		var sum = 0
		lines.map {
			sum += it.size
			sum
		}
	}
	
	LazyColumn(
		modifier = Mod.space(bottom = 15, start = 15).h(0, 100).maxW(),
		state = txtScroll
	) {
		itemsIndexed(lines) { index, txt ->
			val lineStart = linesSize[index] - txt.size
			val lineEnd = linesSize[index]

			val txtUI = when {
					goodStr <= lineStart -> txt
					goodStr >= lineEnd -> txt.green()
					else -> {
						val greenChar = goodStr - lineStart

						UIStr(
							txt.fromTo(0, greenChar).green(),
							txt.fromTo(greenChar, txt.size)
						)
					}
				}
			
			Text(txtUI)
		}
	}
			
	
	var txt = r(tsk.input)
    BigInput(txt, inputScroll) {
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
    var Time = r(60)
    var Points = r(10)
    var WhichIf = r(0)
	
	var show = r(no)
    var appName by r("app")
	
	selectApp(show){
		appName = it
	}
  
    if (!id.isEmpty()) {
      val app = Bar.apps.find { it.id == id }

      if (app != null) {
        Time.it = app.DoneTime
        Points.it = app.Worth
        appName = app.name
      }
    }

    LazyScreen(top = {
        Header.AppUsage(Time, Points, appName)
       }
    ) {
		RuleCard("If"){
		  LazzyRow{
			  CheckCircle(1, WhichIf)
			  Text("Spend ")
			  TinyInput(Time)
			  Text(" seconds")
          
			  Text(" on ")
			  Ctext(appName) {
				  show.it = yes
			  }
		  }
      }
      RuleCard("Do"){
		  LazzyRow{
			  Text("Add ")
			  TinyInput(Points)
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
	var schedule1 by r(Schedule(
	    	type = "WEEKLY",
	    	every = 1,
	    )
	)
	
	
  
    if (!id.isEmpty()) {
      val todo = Bar.doTsk.find { it.id == id }

      if (todo != null) {
        time1.it = todo.doneTime
        points1.it = todo.worth
        name1.it = todo.name
		schedule1 = todo.schedule
      }
    }

    LazyScreen(top = {
        Text("ToDo")
        
        End {
            Item.Add {
				log("clicked icon ")
                if (time1.it==0) {
					Vlog("Add time")
					return@Add
				}
				if (name1.it=="") {
					Vlog("Add name")
					return@Add
				}
				log("passed the check")

				

				var gotPerm = Permission.ignoreOptimizations()
				if (!gotPerm) Vlog("recommended permission")


                if (!id.isEmpty()) {
                    val tsk = Bar.doTsk.find { it.id == id }
					log("found something")
					
                    if (tsk!=null){
						log("tsk not null found")
						
                        tsk.edit {
							name = name1.it
							doneTime = time1.it
							worth = points1.it
							schedule = schedule1
                        }  
						log("going to main")
                        goTo("Main")
                    }
                    return@Add
                }

				Vlog("adding task")

                Bar.doTsk.add {
                    name = name1.it
					doneTime = time1.it
					worth = points1.it
					schedule = schedule1
                }
				Vlog("going to main")
                goTo("Main")
            }
		}
	}) {
		RuleCard("Info"){
			LazzyRow{
				TinyInput(name1, w=180, isInt = no, maxLetters = 135)
			}
			LazzyRow{
				Text(", time")
				
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

	LaunchedEffect(tskOn, timeWorked){
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
				log("[TaskActive] Tsk_DIDTIME: ${tsk.didTime}, timeWorked: $timeWorked , name: ${tsk.name}")
			
				timeWorked++
				
				Bar.funTime++	
				
				Notification("Timer", Time(tsk.timeLeft), id = 2){ builder, manager ->  
					
					while (yes){
						wait(1000)
						log("just in while loop")
						
						if (tskOn && Bar.leftApp){
						Vlog("NOTIFICATION TICKING")
						
						timeWorked++

						tsk.edit{
							didTime = timeWorked
						}
				
						Bar.funTime++	
						val notifi = builder.title("Timer").text(Time(tsk.timeLeft)).build()
						manager.notify(2, notifi)
					}}
					
				}
			}
			if (tsk.done()){
				log("task is done")
				tskOn = no
			}
		}
		
	}


	
	Icon.Timer(tskOn) {
		if (!it == yes) {
			var found = Bar.doTsk.find { it.on == yes }
			if (found != null) {
				found.edit { on = no }
				log("Disabling , found ${found.name}, foundOn ${found.on}, !it ${!it}")
			}
		}
		tskOn = !it
		
		log("name ${tsk.name}, tskOn $tskOn, !it ${!it}")
	}
	move(5)
	Text(
		"${tsk.name}: ${Time(tsk.timeLeft)}",
		modifier = Mod.w(toF(AppW)*0.57)
	)
	
    End { 
		Item.Edit(mod = Mod.space(0)){
            Item.enoughPoints {
				goTo("ToDo/${tsk.id}")
            }
        }

		Item.Delete(Mod.space(0)){ 
			tsk.remove()
		}
	}
}






