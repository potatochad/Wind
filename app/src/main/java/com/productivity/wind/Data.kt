package com.productivity.wind

import com.productivity.wind.Screens.*
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalConfiguration
import com.productivity.wind.Imports.*
import androidx.core.view.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.productivity.wind.Imports.Data.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.system.*
import dalvik.system.*
import java.io.File
import kotlinx.coroutines.*


/*

import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

fun runJs(jsCode: String) {
    val cx = Context.enter()
    try {
        val scope: Scriptable = cx.initStandardObjects()
        cx.evaluateString(scope, jsCode, "JS", 1, null)
    } finally {
        Context.exit()
    }
}
*/

fun RunJs(){
	
	
}

/*! NEVER move bar and lists to another FOLDER, or other file
aka....got some functions in datatools, that though a bit tantrum...
yea....i cant figure out how fix it or rewire it...(kinda lazy--i made it long ago dont remember what did)
*/

fun onNewDay() {  
     Bar.apps.each{
          Bar.apps.edit(it){
				done = no
          }
     }
	 Bar.copyTsk.each{
		 Bar.copyTsk.edit(it){
			 done = no
			 DailyDone = 0
		 }
	 }
}


/*
‼️ NEVER PUT IT ONE LINEEE, s—save, it gets its id by getting line and file of S()
*/

object Bar {
	//BASIC
    var funTime by s(0)
    var Dpoints by s(0)
	var lastDate by s("") 
	var Url by s("")
	var logs by s("")
    
    //ACHIEVEMENTS
    var LettersTyped by s(0)
	

	// LISTS
    var copyTsk = sList<CopyTsk>("copyTsk")
	var waits = sList<Waits>("waits")

	var badWords = sList<WebWord>("badWords").apply {
		addAll(
			listOf(
				WebWord().apply { word = "anime" },
				WebWord().apply { word = "youtube.com" },
				WebWord().apply { word = "facebook.com" },
				WebWord().apply { word = "instagram.com" },
				WebWord().apply { word = "x.com" },
				WebWord().apply { word = "tiktok.com" },
			)
		)
	}


	//NOT SAVED
	var apps = mList<AppTsk>()


}


@Serializable
data class CopyTsk(
    val id: Str = Id(),
    var txt: Str = "Be always kind",
    var input: Str = "",
    var done: Bool = no,
    var DailyMax: Int = 5,
    var DailyDone: Int = 0,
    var Done_Worth: Int = 10,
    var Letter_Worth: Int = 1,
    var goodStr: Int = 0,
) {
    var txtState by m(txt)
        set(value) {
            field = value
            txt = value
        }

    var inputState by m(input)
        set(value) {
            field = value
            input = value
        }

    var doneState by m(done)
        set(value) {
            field = value
            done = value
        }
}


@Serializable
class Waits {
    val id = Id()
    var whenStart by m(no)
    var whenDo by m(no)
    var DoStr by m("")
}

@Serializable
data class AppTsk(
    val id: Str = Id(),
    var name: Str = "",
    var done: Bool = no,
    var pkg: Str = "",
    var NowTime: Int = 0,
    var DoneTime: Int = 0,
    var Worth: Int = 0
)

@Serializable
class WebWord{
    val id = Id()
	var word by m("")
}







@Composable
fun AppContent() {
	LazyTheme {
        Surface(Mod.maxS()) {
			SelectionContainer {
				AppStart()			
			}
		}
	}
}


@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI() {

    //Background thing! Disabled
    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { context.startForegroundService(Intent(context, WatchdogService::class.java))} else { context.startService(Intent(context, WatchdogService::class.java)) }
}

fun MAINStart() {
    Item.UpdateAppTsk()
}

fun OnResume(){
	Item.UpdateAppTsk()

	
}

@Composable
fun AppStart() {
	RunOnce {
        DayChecker.start()
	}
	
    LazyMenu { Menu() }
	
    MyNavGraph(navController = AppNav)
}



