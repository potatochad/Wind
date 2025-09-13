package com.productivity.wind

import com.productivity.wind.Screens.*
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.productivity.wind.ui.theme.*
import androidx.compose.ui.platform.LocalConfiguration
import com.productivity.wind.Imports.*
import androidx.compose.runtime.snapshots.*
import androidx.core.*
import androidx.compose.ui.graphics.*
import androidx.core.view.*
import android.graphics.*
import androidx.compose.foundation.layout.*
import com.productivity.wind.Imports.*
import androidx.compose.foundation.*

/*! NEVER move bar and lists to another FOLDER, or other file
aka....got some functions in datatools, that though a bit tantrum...
yea....i cant figure out how fix it or rewire it...(kinda lazy--i made it long ago dont remember what did)
*/

fun onNewDay() {  
     apps.forEach{app ->
          apps.edit(app){
				done = false
          }
     }   
}


class Settings {
    var funTime by m(0)
    var Dpoints by m(0)
    
    


    
    //region COPY PASTE THING Disipline

    var FirstEditText by m(true)
    var targetText by m("ALWAYS BE KIND")
    var LetterToTime by m(1)
    var DoneRetype_to_time by m(60)
    var HowManyDoneRetypes_InDay by m(0)
    var currentInput by m("")
    var highestCorrect by m(0)

    //endregion COPY PASTE Disipline


    
    //region MISALANIOUS

    var halfHeight by m(0.dp)
    var halfWidth by m(0.dp)
    var ShowMenu by m(false)


    var lastDate by m("")
    var restoringFromFile by m(false)

    //endregion MISALANIOUS

    

    //region ACHIEVEMENTS

    var TotalTypedLetters by m(0)

    //endregion


    //region LISTS
    
    var ListApps by m("")
	var CopyPasteTasks by m("")
    
}
//m-mutable state, ml- mutablelistof



val trackedLists = listOf(
        Dset("Bar.ListApps", "apps"),
	    Dset("Bar.CopyPasteTasks", "copyTasks"),
        
    )

var copyTasks = ml(CopyTasks())

data class CopyTasks(
    override val id: Str= Id(),
    var title: Str = "",
    var onMax: Bool = false,
	var MaxTimes: Int = 5,
    var Done_Worth: Int = 10,
    var Letter_Worth: Int = 1,
) : Identifiable, Copyable<CopyTasks> {
    override fun copySelf() = this.copy()
}


var apps = ml(DataApps())

data class DataApps(
    override val id: Str = Id(),
    var name: Str = "",
    var done: Bool = false,
    var pkg: Str = "",
    var NowTime: Int = 0,
    var DoneTime: Int = 0,
    var Worth: Int = 0,
) : Identifiable, Copyable<DataApps> {
    override fun copySelf() = this.copy()
}











fun show(state: MutableState<Boolean>){ state.value = true }

object Popup {
    var Edit = m(false)
    var NeedMorePoints = m(false)
    var AskUsagePermission = m(false)
    var EnableBlocking = m(false)
    
    var AppSelect = m(false)

}





//region OnAppStart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set navigation bar black with white icons
        window.navigationBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false

        AppStart_beforeUI(applicationContext)
        setContent {
            WindTheme {
                Surface(modifier = Modifier
						.fillMaxSize()
					   ) {
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
fun AppStart_beforeUI(context: Context) {
    
    App.ctx = context
    SettingsSaved.init()
    SettingsSaved.Bsave()

    //Background thing! Disabled
    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { context.startForegroundService(Intent(context, WatchdogService::class.java))} else { context.startService(Intent(context, WatchdogService::class.java)) }
}


@Composable
fun MAINStart() {
    LaunchedEffect(Unit) {
        delay(1_000L)
        Bar.restoringFromFile = false
    }
}

fun OnResume(){
        refreshApps()  
}
@Composable
fun AppStart() {
    LazyMenu { Menu() }
    
    PopUps()
    val halfWidth = LocalConfiguration.current.screenWidthDp.dp/2+30.dp; Bar.halfWidth = halfWidth
    val halfHeight = LocalConfiguration.current.screenHeightDp.dp/2; Bar.halfHeight = halfHeight
    LaunchedEffect(Unit) {
        DayChecker.start()
    }
  
    
    ListStorage.SynchAll()
    
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


    /*POPUPS CRASHING??
    * USE LOCAL CONTEXT
    * ? YEA GOT NO BETTER SOLUTION SORRRY*/

}
