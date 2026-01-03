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
	var resetInput = r_m(yes)
	val inputScroll = r_Scroll()
    
    if (!id.isEmpty()) {
      val tsk = Bar.copyTsk.find { it.id == id }

      if (tsk != null) {
		  txt.it = tsk.txt
		  DailyMax.it = tsk.DailyMax
		  Done_Worth.it = tsk.Done_Worth
		  Letter_Worth.it = tsk.Letter_Worth
		  
		  wait {
			  inputScroll.goTo(tsk.goodStr() * 2)
		  }
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
			Item.BigTskInput(txt, inputScroll)
        }
		/*
		LazyRuleCard("Extra") {
            UI.CheckRow("reset input every day", resetInput)
		}
		*/
    }
}

@Composable
fun CopyTskUI(tsk: CopyTsk) {
    val txtScroll = r_Scroll()
	val inputScroll = r_Scroll()
	var scrollBy by r_m(toF(AppW)/180f)
	
	var bigText by r_m(UIStr(tsk.txt))
	var goodStr by r_m(tsk.goodStr())

	var done by r_m(no)

    RunOnce(goodStr) {
		if (goodStr > 30) {
			txtScroll.scroll(scrollBy)
			log("scrollBy: $scrollBy")
		}
		RunOnce {
			wait {
				if (goodStr > 30) {
					val done = toF(tsk.goodStr)*(scrollBy)
					txtScroll.goTo(done)
				}
				inputScroll.toBottom()
			}
		}

		if (done) {
			Vlog("done")
			Bar.copyTsk.edit(tsk){
				tsk.DailyDone +=1
				tsk.input = ""
				tsk.goodStr = 0
			}
			Bar.funTime += tsk.Done_Worth
			txtScroll.goTo(0)
		}
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
	Text(
		text = bigText,
		modifier = Mod.space(h = 15).space(bottom = 15).h(100).maxW().Vscroll(txtScroll)
	)

	var txt = r_m(tsk.input)
    Item.BigTskInput(txt, inputScroll) {
		if (it.size - txt.it.size < 2) {
			txt.it=it
			
			Bar.copyTsk.edit(tsk) { tsk.input = it }
			
			if (goodStr < tsk.goodStr()) {
				Bar.LettersTyped++
				Bar.funTime += tsk.Letter_Worth
				goodStr = tsk.goodStr()
			}
			
			if (tsk.input == tsk.txt) {
				done = yes
				txt.it=""
			}
        }
    }
}





@Composable
fun AppUsage(id: Str = "") {
    var Time = r_m(60)
    var Points = r_m(10)
    var WhichIf = r_m(0)
	
	var show = r_m(no)
    var appName by r_m("app")
	
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



