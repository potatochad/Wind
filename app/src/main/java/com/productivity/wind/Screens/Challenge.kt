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
    var txt = r_m("Be always kind")
    var DailyMax = r_m(5)
    var Done_Worth = r_m(10)
    var Letter_Worth = r_m(1)
    
    if (!id.isEmpty()) {
      val tsk = Bar.copyTsk.find { it.id == id }

      if (tsk != null) {
        txt.it = tsk.txt
        DailyMax.it = tsk.DailyMax
        Done_Worth.it = tsk.Done_Worth
        Letter_Worth.it = tsk.Letter_Worth
      }
    }

    LazyScreen(top = { 
        Header.CopyPaste(txt, DailyMax, Done_Worth, Letter_Worth, id) 
    }) {
        LazyRuleCard("If") {
            LazzyRow {
                Text("Letter typed correctly: ")
                Item.TskInput(Letter_Worth)
                Text(" points")
            }
            LazzyRow {
                Text("Text typed correctly: ")
                Item.TskInput(Done_Worth)
                Text(" points")
            }
        }

        LazyRuleCard("Other") {
            LazzyRow {
                Text("DailyMax: ")
                Item.TskInput(DailyMax)
            }
            LazzyRow {
				Column {
					Text("Text: ")
					Item.BigTskInput(txt)
				}
			}
        }

		LazyMore("Extra"){
			Text("hello")
		}

		
    }
}

@Composable
fun CopyTskUI(tsk: CopyTsk) {
	if (tsk.DailyDone == tsk.DailyMax) return

    val txtScroll = r_Scroll()
	val inputScroll = r_Scroll()

    RunOnce {
		if (tsk.goodStr > 20) {
			val done = toF(tsk.goodStr) / toF(tsk.txt.size)

			scrollToProgress(done, txtScroll)
		}
	}
	
	fun Done() {
		Bar.copyTsk.edit(tsk){
			tsk.DailyDone +=1
			tsk.input = ""
			tsk.goodStr = 0
		}
        Bar.funTime += tsk.Done_Worth
		txtScroll.goTo(0)
	}

	RunOnce(tsk.goodStr) {
		if (tsk.goodStr > 20) {
			txtScroll.scroll(2)
		}
		inputScroll.toBottom()
	}

    
    LazzyRow {
        Text("Done: ${tsk.DailyDone}/${tsk.DailyMax}")
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
    move(8)
    Text(
        text = correctStr(tsk.txt, tsk.input),
        modifier = Mod.h(0, 100).w(0, 300).Vscroll(txtScroll)
    )
    move(h = 20)

    OutlinedTextField(
        value = tsk.input,
        onValueChange = {
			if (it.size - tsk.input.size <= 2) {
				CopyTskInput(tsk, it) { Done() }
				
				CopyTskSimpleAutoCorrect(tsk)
				
            }
        },
        modifier = Mod.maxW().h(150).Vscroll(inputScroll).onFocusChanged { inputScroll.toBottom() },
		placeholder = { Text("Start typing...") }
    )
}






@Composable
fun AppUsage(id: Str = "") {
    var Time = r_m(60)
    var Points = r_m(10)
    var WhichIf = r_m(0)
    selectedApp.it= ""
  
    if (!id.isEmpty()) {
      val app = Bar.apps.find { it.id == id }

      if (app != null) {
        Time.it = app.DoneTime
        Points.it = app.Worth
        selectedApp.it = app.name
      }
    }

    LazyScreen(top = {
        Header.AppUsage(Time, Points, selectedApp)
       }
    ) {
      LazyRuleCard("If"){
        LazzyRow{
          UI.CheckCircle(1, WhichIf)
          Text("Spend ")
          Item.TskInput(Time)
          Text(" seconds")
          
          Text(" on ")
          UI.Ctext(if (selectedApp.it.isEmpty()) "app" else selectedApp.it) {
            show(AppSelect)
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



