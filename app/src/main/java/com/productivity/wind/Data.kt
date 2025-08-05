package com.productivity.wind

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
    //Disipline points
    var Dpoints by m(0)
    var lastDate by m("")
    var restoringFromFile by m(false)

    


    
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
    var G_targetText by m("Let's get ready to work. Start by choosing one task that is the most important. Try to focus on that task only.  When the task is finished, you can take a short break to rest. If you finish more tasks after that, great job — keep going one step at a time. If permissions turned on (menu->settings) you can configure apps (Top bar, right side icon) to be blocked if do not have enough points (1 point = 1 second).")
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


    //endregion MISALANIOUS


    
    //region PERMISSIONS

    var NotificationPermission by m(false)
    var NotificationP_Description by m(
        "Keeps the app running in the background so you don’t miss any tracking or point updates."
    )

    var OptimizationExclusionPermission by m(false)
    var OptimizationExclusionP_Description by m(
        "Stops your phone’s battery saver from shutting down the background service. This ensures the app runs smoothly."
    )

    var UsageStatsPermission by m(false)
    var UsageStatsP_Description by m(
        "Lets the app see which apps you open. Used only to track apps you selected, to help manage your focus."
    )
    
    //endregion


    

    //region ACHIEVEMENTS

    var TotalTypedLetters by m(0)
    var showUsageIcon by m(false)

    //endregion


    //region LISTS
    
    var myList by m("")
    var myList2 by m("")

    //endregion LISTS
    
}
//m-mutable state, ml- mutablelistof

data class TestData(
    var id: Str = Id(),
    var name: Str = "",
)
var Tests = ml(TestData())
var Tests2 = ml(TestData())



object ListStorage {
    
    val all = listOf(
        StoreList(Bar::myList, Tests),
        StoreList(Bar::myList2, Tests2),
    )
    //RUNS ON start and restore
    inline fun <reified T> initONCE(json: String, list: SnapshotStateList<T>) {
        if (json.isNotBlank() && list.isEmpty()) {
            val type = getListType(list)
            val loaded = gson.fromJson<MutableList<T>>(json, type)
            list.clear()
            list.addAll(loaded)
        }
    }

    @Composable
    fun <T> OnAppStartONCE(jsonRef: KMutableProperty0<String>, list: SnapshotStateList<T>) {
        LaunchedEffect(Unit) {
            while (true) {
                jsonRef.set(gson.toJson(list))
                delay(1_000L)
            }
        }
    }

    //runs on restore and init
    fun initAll2() {
        all.forEach { item ->
            @Suppress("UNCHECKED_CAST")
            initONCE(
                item.json(),
                item.list as SnapshotStateList<Any>
            )
        }
    }
    fun initAll() {
    all.forEach { item ->
        if (item.json().isNotBlank() && item.list.isEmpty()) {
            val loaded = gson.fromJson<MutableList<Any>>(item.json(), item.type)
            @Suppress("UNCHECKED_CAST")
            (item.list as MutableList<Any>).addAll(loaded)
        }
    }
}


    @Composable
    fun OnAppsStartAll(){
        all.forEach { item ->
            @Suppress("UNCHECKED_CAST")
            OnAppStartONCE(
                item.json as KMutableProperty0<String>,
                item.list as SnapshotStateList<Any>
            )
        }
    }


    
}






data class DataApps(
    var id: Str = Id(),
    var name: Str = "",
    var done: Bool = false,
    var packageName: Str = "",
    var Block : Bool = false,
    var TimeSpent : Int = 0,
)
data class DataCopyPastes(
    var id: Str = Id(),
    var text: Str = "Copy paste text",
    var done: Bool = false,
    var HowManyDones : Int = 0,
)
var apps = mutableStateListOf<DataApps>()
var CopyPastes = mutableStateListOf<DataCopyPastes>()














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







//region BACKGROUND TASK

class WatchdogService : Service() {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private var OneJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind: $intent")
        return null
    }

    fun GOtowindAPP(){
        val intent = Intent(Global1.context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Global1.context.startActivity(intent)
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        NotificationHelper(this).createNotificationChannel()
        startForeground(1, NotificationHelper(this).buildNotification(),)
        Global1.context = this
        if (Bar.BlockingEnabled) {
            if (OneJob == null || OneJob?.isActive == false) {
                OneJob = serviceScope.launch {
                    while (true) {

                        //region SAFETY PURPOSES

                        delay(1000L)
                        Bar.COUNT +=1

                        //endregion


                        //region CURRENT APP

                        val usageStatsManager = Global1.context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                        val NowTime = System.currentTimeMillis()

                        /*
                        * THIS IS NOT SUPER ACCURATE
                        ? If you want better precision, you’ll need an Accessibility Service.
                        !THIS REQUIRES NAVIGATING USER TO IT*/
                        val AppsUsed = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, NowTime - 20_000, NowTime)
                        val currentApp = AppsUsed?.maxByOrNull { it.lastTimeUsed }?.packageName

                        //LOG WHEN WANT TOOO
                        if (currentApp == Global1.context.packageName || currentApp == null) { } else { log("BACKGROUND — CURRENT APP: $currentApp", "bad") }

                        //endregion CURRENT APP

                        val blocked = apps.any { it.packageName == currentApp && it.Block }

                        if (blocked) {
                            if (Bar.funTime > 0) {
                                Bar.funTime -= 1
                                log("BACKGROUND---Spending Time??:::${Bar.funTime};", "bad")
                            } else {
                                GOtowindAPP()
                                log("BACKGROUND---Blocking APP:::${currentApp}; ${Bar.COUNT}", "bad")
                            }
                        }


                    if (currentApp == "com.seekrtech.waterapp") {
                        log("NEW DAY??; ${Bar.NewDay}", "bad")
                        log("waterdo time; ${Bar.WaterDOtime_spent}", "bad")
                        if (Bar.NewDay) {
                            if (Bar.WaterDOtime_spent > 50) {
                                Bar.funTime += 900
                                Bar.NewDay = false
                            } else {
                                Bar.WaterDOtime_spent += 1
                            }
                        }
                    }
                    }
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() { super.onDestroy(); serviceScope.cancel() }
}


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


    //DELETE!!!!!!LATER
    if (Tests.isEmpty()){
                Tests.add(TestData(name = "Test"))
    }



    
    PopUps()
    val halfWidth = LocalConfiguration.current.screenWidthDp.dp/2+30.dp; Bar.halfWidth = halfWidth
    val halfHeight = LocalConfiguration.current.screenHeightDp.dp/2; Bar.halfHeight = halfHeight
    LaunchedEffect(Unit) {
        DayChecker.start()
    }
    ListStorage.OnAppsStartAll()
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



//endregion

































/*?WHEN TESTING IS PAINFUL
* !AUTO DISABLED EACH TIME*/
/* IT AUTO CALLS ITSELF
YEA, WEIRD ONLY FEW FUNCTIONS IT CALLS
? YOU MUST WORK WITH IT WANT AND SET UP
! onAccessibilityEvent- IS A MUST AND ONLY ON CHANGE CALLED
* */
class WatchdogAccessibilityService  : AccessibilityService() {

    /* ON CHANGE DO X
    ! this is very important
    ? must be names onAccessibilityEvent

    * */
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        CurrentApp(event)
        //GETEVERYTHING(event)
    }
    fun CurrentApp(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Bar.currentApp = event.packageName.toString()
            log("CURRENT APP: ${Bar.currentApp}", "bad")
        }
    }
    private fun exploreNodeTree(node: AccessibilityNodeInfo?, depth: Int) {
        if (node == null) return

        val indent = "  ".repeat(depth)

        val text = node.text
        val id = node.viewIdResourceName
        val desc = node.contentDescription
        val className = node.className
        val clickable = node.isClickable
        val enabled = node.isEnabled
        val focused = node.isFocused
        val focusable = node.isFocusable
        val visible = node.isVisibleToUser
        val bounds = Rect().apply { node.getBoundsInScreen(this) }

        // Header
        log("$indent Node:", "bad")

        // Properties
        if (!text.isNullOrEmpty()) log("$indent   Text: $text", "bad")
        if (!desc.isNullOrEmpty()) log("$indent   Description: $desc", "bad")
        if (!id.isNullOrEmpty()) log("$indent   ID: $id", "bad")
        log("$indent   Class: $className", "bad")
        log("$indent   Clickable: $clickable", "bad")
        log("$indent   Enabled: $enabled", "bad")
        log("$indent   Focused: $focused", "bad")
        log("$indent   Focusable: $focusable", "bad")
        log("$indent   Visible: $visible", "bad")
        log("$indent   Bounds: $bounds", "bad")

        // Children
        for (i in 0 until node.childCount) {
            exploreNodeTree(node.getChild(i), depth + 1)
        }
    }

    fun GETEVERYTHING(event: AccessibilityEvent?) {
        val root = rootInActiveWindow
        if (root != null) {
            exploreNodeTree(root, 0)
        }
    }
    override fun onInterrupt() {
        log("ACESABILITY Service interrupted.", "bad")
    }
}

class NotificationHelper(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "WatchdogServiceChannel"
    }
    fun createNotificationChannel() {
        Timber.d("createNotificationChannel() called")
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_channel_name_watchdog_service),
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(channel)
    }

    fun buildNotification(): Notification {
        Timber.d("buildNotification() called")

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntentFlags =
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        val pendingIntent =
            PendingIntent.getActivity(context, 0, notificationIntent, pendingIntentFlags)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notification_title_app_watchdog))
            .setContentText(context.getString(R.string.notification_content_monitoring_apps))
            .setSmallIcon(R.drawable.baseline_radar_24)
            .setContentIntent(pendingIntent)
            // Low priority for ongoing background service notification
            .setPriority(NotificationCompat.PRIORITY_LOW)
            // Makes the notification persistent
            .setOngoing(true)
            .build()
    }
}
