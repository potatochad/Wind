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
import com.productivity.wind.Imports.Utils.*
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
import kotlin.properties.*
import com.google.android.gms.location.*
import com.productivity.wind.Imports.UI_visible.*


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

fun newDay() {  
     Bar.apps.each{
          Bar.apps.edit(it){
				done = no
          }
     }
	 Bar.copyTsk.each{
		 it.edit {
			 doneTimes = 0
		 }
	 }
	 Bar.doTsk.each{
		 it.edit {
			 didTime = 0
		 }
	 }
}


/*
‼️ NEVER PUT IT ONE LINEEE, s—save, it gets its id by getting line and file of S()
*/

object Bar {
	//BASIC
    var funTime by s(0, "funTime")
    var Dpoints by s(0, "Dpoints")
	var lastDate by s("", "lastDate")
	var leftApp by s(no, "leftApp")
	var encryptedBackup by s(yes, "encryptedBackup") 
	var Url by s("https://google.com", "Url")
	var logs = sList<Str>("logs")

	//LOCATION
	var userLatLng by s("51.5074,-0.1278", "userLatLng")
	var userLocation by synch(toLatLng(userLatLng)) {
		userLatLng = toStr(it)
	}
	var privacyLocation by s(no, "privacyLocation")
	var privacyGeo = sList<GeoCircle>("privacyGeo")


    
    //ACHIEVEMENTS
    var LettersTyped by s(0, "LettersTyped")
	

	// LISTS
    var copyTsk = sList<CopyTsk>("copyTsk")
	var waits = sList<Waits>("waits")
	var apps = sList<AppTsk>("apps")
	var doTsk = sList<DoTsk>("doTsk")

	var badWords = sList<WebWord>("badWords", listOf("anime", "youtube.com", "facebook.com", "instagram.com", "x.com", "tiktok.com").map { WebWord(word = it) })



}


@Serializable
data class CopyTsk(
    val id: Str = Id(),
    var txt: Str = "Be always kind",
    var input: Str = "",
    var maxDone: Int = 5,
    var doneTimes: Int = 0,
    var donePts: Int = 10,
    var letterPts: Int = 1,
) {
	//For recompose
    var txtState by synch(txt){ txt = it }
	var inputState by synch(input){ input = it }
}


@Serializable
data class Waits (
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
data class DoTsk(
    val id: Str = Id(),
    var name: Str = "",
    var didTime: Int = 0,
    var doneTime: Int = 0,
    var worth: Int = 0,
	var timerOn: Bool = no,
	var schedule: Schedule = Schedule(),
	var showToday: Bool = no,
) {
	var on by synch(timerOn){ timerOn = it }

}

@Serializable
data class WebWord(
    val id: Str = Id(),
	var word: Str = ""
)
//top saves, end does not
@Serializable
data class GeoCircle(
    val id: Str = Id(),
    var Lat: Double = 0.0,
    var Lng: Double = 0.0,
    var radius: Float =0f,
)
@Serializable
data class txtLine(
    val id: Str = Id(),
    var line: Int = 0,
	var size: Int = 0,
)








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

fun OnLeaveApp(){
	Bar.leftApp = yes
	
}

fun OnResume(){
	Bar.leftApp = no
	Item.UpdateAppTsk()

	if (isNewDay()) newDay()

	
}

@Composable
fun AppStart() {
	val LocationClient = r { LocationServices.getFusedLocationProviderClient(App) }
    
	RunOnce {
		captureAppCrashes()
		getMyAppLogs() 

		if (Bar.privacyLocation) {
			location {
				getUserLocation(LocationClient, 3000L){
					Bar.userLocation = it
				}
			}
		}
		
	}
	
    LazyMenu { Menu() }
	
    MyNavGraph(navController = AppNav)
}



