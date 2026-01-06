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
        LazyRuleCard("If") {
            LazzyRow {
                Text("Letter typed correctly: ")
                Item.TskInput(letterPts)
                Text(" points")
            }
            LazzyRow {
                Text("Text typed correctly: ")
                Item.TskInput(donePts)
                Text(" points")
            }
        }

        LazyRuleCard("Other") {
            LazzyRow {
                Text("DailyMax: ")
                Item.TskInput(maxDone)
            }
			Item.BigTskInput(txt, inputScroll)
        }
    }
}

@Composable
fun CopyTskUI(tsk: CopyTsk) {
    val txtScroll = Scroll()
	val inputScroll = Scroll()
	var scrollBy by r(toF(AppW)/180f)
	
	var goodStr by r(tsk.goodStr())
	var goodStr2 by r(0)

    RunOnce(goodStr) {
		if (goodStr > 30) {
			// Vlog("txtScroll.size: ${txtScroll.size}, tsk.txt.size: ${tsk.txt.size}, scrollTo: ${ toF(goodStr)*toF(txtScroll.size) / toF(tsk.txt.size) }")
			val done = toF(goodStr)*(scrollBy) - 80f
			txtScroll.goTo(done)
		}
	}
	RunOnce {
		inputScroll.toBottom()
	}

    
    LazzyRow {
        Text("Done: ${tsk.doneTimes}/${tsk.maxDone}")
        UI.End { 
			Icon.Edit{
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
	val lines = tsk.txt.toLines()
	LazyColumn(
		modifier = Mod.space(h = 15).space(bottom = 15).h(0, 100).maxW(),
		state = txtScroll
	) {
		items(lines) {
			Text(it)
		}
	}
	*/

	var layout by r { mutableStateOf<TextLayoutResult?>(null) }

	Box(Mod.space(h = 15).space(bottom = 15).h(0, 100).maxW().Vscroll(txtScroll).center()) {
		Text(
			text = tsk.txt,
			onTextLayout = { layout = it }
		)

		Canvas(Mod.matchParentSize()) {
			val l = layout ?: return@Canvas

			for (i in 0 until goodStr) {
				val box = l.getBoundingBox(i)

				drawContext.canvas.nativeCanvas.apply {
					drawText(
						tsk.txt[i].toString(),
						box.left,
						box.bottom - 7.dp.toPx(),
						android.graphics.Paint().apply {
							color = android.graphics.Color.GREEN
							textSize = 50f // match your Text size
						}
					)
				}
			}
		}
	}


	var txt = r(tsk.input)
    Item.BigTskInput(txt, inputScroll) {
		if (it.size - txt.it.size < 2 && goodStr <= txt.it.size) {
			txt.it=it
			
			tsk.edit{ input = it }

			if (goodStr < tsk.goodStr()) {
				Bar.LettersTyped++
				Bar.funTime += tsk.letterPts
				goodStr = tsk.goodStr()
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
				goodStr = tsk.goodStr()
			}
        }
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
      LazyRuleCard("If"){
        LazzyRow{
          UI.CheckCircle(1, WhichIf)
          Text("Spend ")
          Item.TskInput(Time)
          Text(" seconds")
          
          Text(" on ")
          UI.Ctext(appName) {
			 show.it = yes
          }
        }
      }
      LazyRuleCard("Do"){
        LazzyRow{
          Text("Add ")
          Item.TskInput(Points)
          Text(" points")
        }
      }
	  

    }

}



