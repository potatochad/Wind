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
This is a special class
*more info in dataTools.kt

it looks for all vars.
ONLY stores their data:
IF (mutable state)
IF (mutable list)

SKIPS: any private info

also...forgot how works.


it only work with the defaults data....

and yes MUST USE THE 
var NAME by m(x)
FORMAT!!!!!!!!

for lists
same thing...very important!!!!




*/
class Settings {
    var funTime by m(0)
    var Dpoints by m(0)
	var lastDate by m("")
    
    


    
    //COPY PASTE
    var FirstEditText by m(no)
    var targetText by m("ALWAYS BE KIND")
    var LetterToTime by m(1)
    var DoneRetype_to_time by m(60)
    var HowManyDoneRetypes_InDay by m(0)
    var currentInput by m("")
    var highestCorrect by m(0)    
    

    //ACHIEVEMENTS
    var TotalTypedLetters by m(0)


    // LISTS

	// LISTS DONT BACKUP OR RESTORE!!!!!
    
    var copyTsk = mList<CopyTsk>()
	var waits = mList<Waits>()
	var apps = mList<AppTsk>()// mutablestatelistof
	var badWords = mList<WebWord>().apply {
		addAll(
			listOf(
				WebWord(word = "anime"),
				WebWord(word = "youtube.com"),
				WebWord(word = "facebook.com"),
				WebWord(word = "instagram.com"),
				WebWord(word = "x.com"),
				WebWord(word = "tiktok.com"),
			)
		)
	}

	//USED in banguage-better language
	var logs by m("")
}


@Serializable
data class CopyTsk(
    val id: Str = Id(),
    var txt: Str = "",
	var input: Str = "",
    var done: Bool = no,
    var DailyMax: Int = 5,
	var DailyDone: Int = 0,
    var Done_Worth: Int = 10,
    var Letter_Worth: Int = 1,
	var goodStr: Int = 0,
) 

fun runDo(cmd: Str) {
    val name = cmd.substringBefore("(")
    val args = cmd.substringAfter("(").substringBefore(")")

    when (name) {
        "scrollToProgress" -> scrollToProgress(args.toFloat())
        "scroll" -> scroll(args.toInt())
        "shake" -> shake()
    }
}


@Serializable
data class Waits(
    val id: Str = Id(),
    var whenStart: Bool = no,
    var whenDo: Bool = no,
    var DoStr: Str = "",
)

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
data class WebWord(
    val id: Str = Id(),
    var word: Str = ""
)







@Composable
fun AppContent() {
	LazyTheme {
        Surface(Modifier.maxS()) {
			SelectionContainer {
				AppStart()			
			}
		}
	}
}





@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(ctx: Context) {
    
    App.ctx = ctx
	App.pkg = ctx.packageName
	
    SettingsSaved.init()
    SettingsSaved.Bsave()

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
	App.screenHeight = LocalConfiguration.current.screenHeightDp.dp
    App.screenWidth = LocalConfiguration.current.screenWidthDp.dp
	App.LazyScreenContentHeight = App.screenHeight-100.dp- bottomSystemHeight()
	
    LazyMenu { Menu() }
    
    Popup.Init()
	
    RunOnce {
        DayChecker.start()
    }
	
    
    App.navHost = rememberNavController()
    MyNavGraph(navController = App.navHost)
}



//endregion

//region GLOBAL
//* CONTEXT from anywhere!!!
object App {
    /* APP CONTEXT
    Context is weirt:
    there is application, ok for most things
    ?and local
    !which used for popup etc...
    * */
    lateinit var ctx: Context
    lateinit var navHost: NavHostController
	lateinit var pkg: Str

	
	var screenHeight by m(0.dp)
	var screenWidth by m(0.dp)
	var LazyScreenContentHeight by m(0.dp)
	
	var Menu by m(no)
	

}
