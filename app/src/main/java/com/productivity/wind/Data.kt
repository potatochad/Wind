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
/*! NEVER move bar and lists to another FOLDER, or other file
aka....got some functions in datatools, that though a bit tantrum...
yea....i cant figure out how fix it or rewire it...(kinda lazy--i made it long ago dont remember what did)
*/

fun onNewDay() {  
     Bar.apps.forEach{app ->
          Bar.apps.edit(app){
				done = no
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
    
    


    
    //region COPY PASTE THING Disipline

    var FirstEditText by m(no)
    var targetText by m("ALWAYS BE KIND")
    var LetterToTime by m(1)
    var DoneRetype_to_time by m(60)
    var HowManyDoneRetypes_InDay by m(0)
    var currentInput by m("")
    var highestCorrect by m(0)

    //endregion COPY PASTE Disipline


    var lastDate by m("")

    

    //region ACHIEVEMENTS

    var TotalTypedLetters by m(0)

    //endregion


    // LISTS

	// LISTS DONT BACKUP OR RESTORE!!!!!
    
    var copyTasks = mutableStateListOf<CopyTsk>()
	var apps = mutableStateListOf<AppTsk>()
	var badWords = mutableStateListOf<WebWord>().apply {
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

	var Newlogs by m("")
	var Oldlogs by m("")
	var logsTag by m("")
}

@Serializable
data class CopyTsk(
    val id: Str = Id(),
    var title: Str = "",
    var onMax: Bool = no,
    var MaxTimes: Int = 5,
    var Done_Worth: Int = 10,
    var Letter_Worth: Int = 1,
)

@Serializable
data class AppTsk(
    val id: Str = Id(),
    var name: Str = "",
    var done: Bool = no,
    var pkg: Str = "",
    var NowTime: Int = 0,
    var DoneTime: Int = 0,
    var Worth: Int = 0,
)

@Serializable
data class WebWord(
    val id: Str = Id(),
    var word: Str = "",
)





//region OnAppStart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set navigation bar black with white icons
        WindowCompat.setDecorFitsSystemWindows(window, true)

        // Set navigation bar black with white icons
        window.navigationBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false
            show(WindowInsetsCompat.Type.systemBars()) // Force visible
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }

        AppStart_beforeUI(applicationContext)
        setContent {
            LazyTheme {
                Surface(Modifier.maxS()) {
					AppStart()
                }
            }

        }

    }

    override fun onResume() {
        super.onResume()
        // re-apply nav bar color to prevent flashing
        window.navigationBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false

        OnResume()
    }
}




@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(ctx: Context) {
    
    App.ctx = ctx
    SettingsSaved.init()
    SettingsSaved.Bsave()

    //Background thing! Disabled
    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { context.startForegroundService(Intent(context, WatchdogService::class.java))} else { context.startService(Intent(context, WatchdogService::class.java)) }
}


@Composable
fun MAINStart() {
    RunOnce {
        delay(1_000L)
        App.restoringFromFile = no
    }
}

fun OnResume(){
        refreshApps()  
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
	
	var screenHeight by m(0.dp)
	var screenWidth by m(0.dp)
	var LazyScreenContentHeight by m(0.dp)
	
	var Menu by m(no)
	var restoringFromFile by m(no)
	

}
