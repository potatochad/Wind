package com.productivity.wind

import kotlin.reflect.full.*
import kotlin.reflect.*
import androidx.compose.runtime.snapshots.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.heightIn
import com.productivity.wind.Screens.DayChecker
import com.productivity.wind.Screens.MyNavGraph
import androidx.compose.runtime.mutableStateOf
import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import android.content.Intent
import android.accessibilityservice.AccessibilityService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.graphics.Rect
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.navigation.compose.rememberNavController
import com.productivity.wind.ui.theme.KeepAliveTheme
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.unit.sp
import com.productivity.wind.*
import java.lang.reflect.*




class Settings {
    var funTime by m(0)
    var Dpoints by m(0)
    
    


    
    //region COPY PASTE THING Disipline

    var FirstEditText by m(true)
    var targetText by m("Let's get ready to work. Start by choosing one task that is the most important. Try to focus on that task only.  When the task is finished, you can take a short break to rest. If you finish more tasks after that, great job — keep going one step at a time. If permissions turned on (menu->settings) you can configure apps (Top bar, right side icon) to be blocked if do not have enough points (1 point = 1 second).")
    var LetterToTime by m(1)
    var DoneRetype_to_time by m(60)
    var HowManyDoneRetypes_InDay by m(0)
    var currentInput by m("")
    var highestCorrect by m(0)

    //endregion COPY PASTE Disipline


    
    //region COPY PASTE THING GERMAN

    var G_FirstEditText by m(true)
    var G_targetText by m("Let's get ready to work. Start by choosing one task that is the most important. Try to focus on that task only.  When the task is finished, you can take a short break to rest. If you finish more tasks after that, great job — keep going one step at a time. If permissions turned on (menu->settigs) you can configure apps (Top bar, right side icon) to be blocked if do not have enough points (1 point = 1 second).")
    var G_LetterToTime by m(2)
    var G_DoneRetype_to_time by m(60)
    var G_currentInput by m("")
    var G_highestCorrect by m(0)

    //endregion COPY PASTE GERMAN



    //region BLOCKING

    var BlockingEnabled by m(false)
    var currentApp by m("")
    var COUNT by m(0)
    var NewDay by m(true)

    //refreshs to 0 daily// if more than 50 seconds, get 600 time
    var WaterDOtime_spent by m(0)
    var secondsLeft by m(10)
    //endregion BLOCKING


    
    //region MISALANIOUS

    var halfHeight by m(0.dp)
    var halfWidth by m(0.dp)
    var ShowMenu by m(false)

    var showLogsIcon by m(false)

    var lastDate by m("")
    var restoringFromFile by m(false)

    //endregion MISALANIOUS


    
    //region PERMISSIONS

    var NotificationPermission by m(false)

    var OptimizationExclusionPermission by m(false)

    var UsageStatsPermission by m(false)
    
    //endregion


    

    //region ACHIEVEMENTS

    var TotalTypedLetters by m(0)
    var showUsageIcon by m(false)

    //endregion


    //region LISTS
    
    var ListApps by m("")
    
}
//m-mutable state, ml- mutablelistof

var apps = ml(DataApps())

val trackedLists = listOf(
        Dset("Bar.ListApps", "apps"),
        
    )





data class DataApps(
    var id: Str = Id(),
    var name: Str = "",
    var done: Bool = false,
    var packageName: Str = "",
    var Block : Bool = false,
    var TimeSpent : Int = 0,
)













//region POPUP CONTROLLER

object Popup {
    var Edit = m(false)
    var G_Edit = m(false)
    var NeedMorePoints = m(false)
    var EnablePermissions = m(false)
    var EnableBlocking = m(false)
}
//!Just call this on app start
@Composable
fun PopUps(){
   G_EditPopUp(Popup.G_Edit)
   EnablePermissionsPopup(Popup.EnablePermissions)
   EditPopUp(Popup.Edit)
   NeedMorePointsPopuo(Popup.NeedMorePoints)
   EnableBlockingPopup(Popup.EnableBlocking)
}


// All other things
@Composable
fun NeedMorePointsPopuo(show: MutableState<Boolean>){
    LazyPopup(
        show = Popup.NeedMorePoints, 
        title = "Not EnoughPoints", 
        message = "Need have ${Bar.Dpoints} points to do this. Only have ${Bar.funTime}"
    )
}
@Composable
fun EnableBlockingPopup(show: MutableState<Boolean>){
    LazyPopup(
            show = show,
            title = "Enable?",
            message = "If you enable blocking, an overlay screen will appear over the selected apps when you run out of points. (1 point = 1 second)\n\nTo disable blocking, you’ll need more than the selected points in the unlock Threashold",
            showCancel = true,
            showConfirm = true,
            onConfirm = {
                Bar.BlockingEnabled = true
            },
        )
}

@Composable
fun EditPopUp(show: MutableState<Boolean>) {
    var TemporaryTargetText by remember { mutableStateOf("") }
    TemporaryTargetText = Bar.targetText
    LazyPopup(
        show = show,
        onDismiss = { TemporaryTargetText = Bar.targetText },
        title = "Edit Text",
        message = "",
        content = {
            OutlinedTextField(
                value = TemporaryTargetText,
                onValueChange = { TemporaryTargetText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        showCancel = true,
        onConfirm = { Bar.targetText = TemporaryTargetText; Bar.FirstEditText = false },
        onCancel = { TemporaryTargetText = Bar.targetText }
    )
}

@Composable
fun G_EditPopUp(show: MutableState<Boolean>) {
    var TemporaryTargetText by remember { mutableStateOf("") }
    TemporaryTargetText = Bar.G_targetText
    LazyPopup(
        show = show,
        onDismiss = { TemporaryTargetText = Bar.G_targetText },
        title = "Edit Text",
        message = "",
        content = {
            OutlinedTextField(
                value = TemporaryTargetText,
                onValueChange = { TemporaryTargetText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        showCancel = true,
        onConfirm = { Bar.G_targetText = TemporaryTargetText; Bar.G_FirstEditText = false },
        onCancel = { TemporaryTargetText = Bar.G_targetText }
    )
}

@Composable
fun EnablePermissionsPopup(show: MutableState<Boolean>) {
    LazyPopup(show = show,
              title = "Need Permissions", 
              message = "Please enable all permissions first.", 
              onConfirm = { Global1.navController.navigate("SettingsP_Screen")}
              )
}

//endregion POPUP CONTROLLER



//region ADD CHALLANGE POPUPS

@Composable
fun AddReminder(show: MutableState<Boolean>) {
    var InputText by remember { m("") }

    fun DONE() { show.value = false }
    LazyPopup(show = show,
              title = "Add Challenge", 
              content = { 
                  UI.InputField(
                      value = InputText,
                      onValueChange = { InputText = it },
                      onDone = { DONE() } ,
                      textSize = 16.sp,           
                      boxHeight = 40.dp,                
                      InputWidth = 200.dp,
                      MaxLetters = 100,
                  )
              }, 
              onConfirm = { DONE() },
              message="",
              )
}

//region ADD CHALLANGE POPUPS


                
//endregion POPUP CONTROLLER







//endregion










//region OnAppStart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppStart_beforeUI(applicationContext)
        setContent {
            KeepAliveTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppStart()

                    Global1.navController = rememberNavController()
                    MyNavGraph(navController = Global1.navController)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
    SettingsSaved.init()
    SettingsSaved.Bsave()

    //Background thing
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { context.startForegroundService(Intent(context, WatchdogService::class.java))} else { context.startService(Intent(context, WatchdogService::class.java)) }
}


@Composable
fun MAINStart() {
    LaunchedEffect(Unit) {
        delay(1_000L)
        Bar.restoringFromFile = false
    }
}
@Composable
fun AppStart() {
    
    PopUps()
    val halfWidth = LocalConfiguration.current.screenWidthDp.dp/2+30.dp; Bar.halfWidth = halfWidth
    val halfHeight = LocalConfiguration.current.screenHeightDp.dp/2; Bar.halfHeight = halfHeight
    LaunchedEffect(Unit) {
        DayChecker.start()
    }
    
    
    ListStorage.SynchAll()
    
    
}



//endregion

//region GLOBAL
//* CONTEXT from anywhere!!!
object Global1 {
    /* APP CONTEXT
    Context is weirt:
    there is application, ok for most things
    ?and local
    !which used for popup etc...
    * */
    lateinit var context: Context
    lateinit var navController: NavHostController


    /*POPUPS CRASHING??
    * USE LOCAL CONTEXT
    * ? YEA GOT NO BETTER SOLUTION SORRRY*/

}




