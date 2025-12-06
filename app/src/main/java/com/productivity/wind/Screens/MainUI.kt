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
import com.productivity.wind.Imports.Data.Bar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.heightIn
import com.productivity.wind.MAINStart
import com.productivity.wind.*
import com.productivity.wind.Imports.Data.*
import com.productivity.wind.Imports.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.*
import kotlin.system.*
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import androidx.compose.foundation.*
import androidx.compose.ui.focus.*



@Composable
fun Main() {
	RunOnce{
		MAINStart()
	}

    
    LazyScreen(top = { Header.Main() }, showBack = no) {
        Column(Mod.Vscroll()){

            Bar.copyTsk.each {
				LazyCard { 
					CopyTskUI(it)
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
			val done = tsk.goodStr.toFloat() / tsk.txt.length.toFloat()

			scrollToProgress(done, txtScroll)
		}
		inputScroll.toBottom()
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



	val coloredTarget = fullCorrectStr(tsk.txt, tsk.input)

    
    LazzyRow {
        Text("Done: ${tsk.DailyDone}/${tsk.DailyMax}")
        UI.End { 
			var delete = r_m(no)

            isSure(delete){
                Bar.copyTsk.remove(tsk)
            }
            
			Icon.Edit{
                Item.enoughPoints {
					goTo("CopyPaste/${tsk.id}")
                }
            }
            
			Icon.Delete{
                delete.it = yes
			}
		}
    }
    move(8)
    Text(
        text = coloredTarget,
        modifier = Mod.h(0, 100).w(0, 300).Vscroll(txtScroll)
    )
    move(h = 20)

    OutlinedTextField(
        value = tsk.input,
        onValueChange = {
			if (it.length - tsk.input.length <= 2) {
				
                if (it.length > tsk.input.length) Bar.LettersTyped += 1
				
				Bar.copyTsk.edit(tsk){ tsk.input = it }

                val correctInput = CopyTskCorrectInput(tsk)

                val newlyEarned = correctInput.length - tsk.goodStr
                if (newlyEarned > 0) {
                    var oldFunTime = Bar.funTime
                    Bar.funTime += newlyEarned * tsk.Letter_Worth
					
					Bar.copyTsk.edit(tsk){
						tsk.goodStr = correctInput.length
					}
                }

                if (correctInput == tsk.txt) Done()
            }
        },
        modifier = Mod.maxW().h(150).Vscroll(inputScroll).onFocusChanged { inputScroll.toBottom() },
		placeholder = { Text("Start typing...") }
    )
}






















				
