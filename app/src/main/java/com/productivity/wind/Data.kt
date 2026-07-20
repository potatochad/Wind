package com.productivity.wind

import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Screens.Settings.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.String.*
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
import com.productivity.wind.Screens.Task.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.gms.location.*
import android.util.Log
import android.app.Activity
import android.os.Process
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import com.productivity.wind.Screens.Settings.LogScreen.*    
import com.productivity.wind.Screens.Settings.*
import com.productivity.wind.Screens.Web.*


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
			 input = ""
		 }
	 }
	 Bar.doTsk.each{
		 it.edit {
			 didTime = 0
		 }
	 }


}

//!! Global var that shouldnt be saved
var defaultScreen by m("Main")
var SettingsItemCardColor = Color(0xFF121212)


object Bar {
	//BASIC
    var funTime by s(0)
    var Dpoints by s(0)
	
	var lastDate by s("")
	var leftApp by s(no)
	var encryptedBackup by s(yes) 
	var noUninstall by s(no)
	var logs = sList<Str>("logs")
	
	var Url by s("https://google.com")
	

	//LOCATION
	var userLatLng by s("51.5074,-0.1278")
	var userLocation by synch(toLatLng(userLatLng)) {
		userLatLng = toStr(it)
	}
	var savedMapType by s(MapType.NORMAL.name)
	var privacyLocation by s(no)
	var privacyGeo = sList<GeoCircle>("privacyGeo")


    
    //ACHIEVEMENTS
    var LettersTyped by s(0)
	

	// LISTS
	
    var copyTsk = sList<CopyTsk>("copyTsk")
	var apps = sList<AppTsk>("apps")
	var doTsk = sList<DoTsk>("doTsk")

	var webWord = sList<WebWord>("WebWord", listOf("anime", "youtube.com", "facebook.com", "instagram.com", "x.com", "tiktok.com").map { WebWord(word = it) })

	var waits = sList<Waits>("waits")
}

@Serializable
data class CopyTsk(
    val id: Str = Id(),
    var txt: Str = "Be always kind",
    var input: Str = "",
	var name: Str = "Copy Paste",
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
    var start: Long = now(),
    var wait: Long,//ml
    var DoJs: Str = "",
){
	val end: Long
        get() = start + wait
}

@Serializable
data class AppTsk(
    val id: Str = Id(),
    var name: Str = "",
    var done: Bool = no,
    var pkg: Str = "",
    var nowTime: Int = 0,
    var doneTime: Int = 0,
    var worth: Int = 0
)

@Serializable
data class DoTsk(
    val id: Str = Id(),
    var name: Str = "",
	var description: Str = "",
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
enum class WebAction {
    Allow,
    Block,
	AI,
	Blot,
	BlotImages,
	BlotVideos,
}
@Serializable
enum class WebType {
    Url,
    Blot,
	KeyWord,
}

@Serializable
data class WebWord(
    val id: Str = Id(),
	var word: Str = "",
	var schedule: Schedule = Schedule(),
	var locked: Bool = no,
	var type: WebType = WebType.Url,
	var action: WebAction = WebAction.Block,
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




fun NavGraphBuilder.ScreenNav() {
    //Main—StartDestination    
    url("Main") { Main() }

    url("Achievements") { Achievements() }
    url("Challenge") { Challenge() }
    url("AppUsage/{appId}") {
		var x: Str = it.url("appId") ?: ""
		AppUsage(x) 
	}

    url("CopyPaste/{appId}") { 
		var x: Str = it.url("appId") ?: ""
		CopyPaste(x) 
	}
	url("ToDo/{toDoId}") { 
		var x: Str = it.url("toDoId") ?: ""
		ToDo(x) 
	}

    url("Web") { Web() }
    url("WebKeywords") { WebKeywords() }
	url("WebHome") { WebHome() }
	url("WebWordConfigure/{WordId}") { 
		var x: Str = it.url("WordId") ?: ""
		WebWordConfigure(x) 
	}
	
	url("filterExtraWeb") { filterExtraWeb() }
    

    url("SettingsScreen") { SettingsScreen() }
    url("ExtensionsScreen") { ExtensionsScreen() }
	url("PrivacyScreen") { PrivacyScreen() }
    url("SettingsOtherScreen") { SettingsOtherScreen() }
	url("LogsScreen") { LogsScreen() }

	// popups ‼️Nav back on close
	popup("GetPoints") { GetPoints() }
	popup("AllowAppUsage") { AllowAppUsage() }
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

//Dont use APP context
suspend fun AppBackground(notif: LazyNotifi){
	while (yes) {
		wait(1000)
		notif.text = "running..."
		wait(1000)
		notif.text = "running.."
		wait(1000)
		notif.text = "running."
		wait(1000)
		notif.text = "running"
		wait(1000)
		notif.text = "running."
		wait(1000)
		notif.text = "running.."
	}
}

fun HandleIntent(intent: Intent){
	val uri = intent.data ?: return
	
	if (uri.scheme == "wind") {
		when (uri.host) {
			"web" -> {
				defaultScreen = "Web"
			}
		}
	}
}


@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI() {
	

	
}

fun MAINStart() {
    TskProp.UpdateAppTsk()

}

fun OnLeaveApp(){
	Bar.leftApp = yes
	
}

fun OnResume(){
	DeviceGray(no)
	Bar.leftApp = no
	TskProp.UpdateAppTsk()

	if (isNewDay()) newDay()

	
}

@Composable
fun AppStart() {
	App.start(AppBackground::class.java)
	
	Keyboard.track()
	AppFocus.track()
	
	val LocationClient = r { LocationServices.getFusedLocationProviderClient(App) }
    
	RunOnce {
		
		Bar.waits.each{
			it.end
			// DoJs = "var x = 5; x = 6;"
		}
		
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



