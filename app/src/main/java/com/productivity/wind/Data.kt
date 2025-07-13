package com.productivity.wind

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import com.productivity.wind.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import android.provider.Settings
import android.view.LayoutInflater
import android.view.WindowManager
import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.graphics.PixelFormat
import android.widget.Button
import android.accessibilityservice.AccessibilityService
import android.graphics.Rect
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.isActive
import kotlin.reflect.KMutableProperty1
import androidx.compose.runtime.MutableState

class Settings {
    var funTime by mutableStateOf(0)

    var BlockYoutube by mutableStateOf(false)
    var BlockSettings by mutableStateOf(false)
    var currentApp by mutableStateOf("")


    //region COPY PASTE THING
    var targetText by mutableStateOf("I can do good things, I am amazing, I make mistakes, I try my best. I want to do x")
    var LetterToTime by mutableStateOf(1)
    var DoneRetype_to_time by mutableStateOf(60)
    var currentInput by mutableStateOf("")
    var highestCorrect by mutableStateOf(0)
    //endregion


    //region MISALANIOUS
    var ShowMenu by mutableStateOf(false)
    var CheckInstalledApps by mutableStateOf(true)
    var AppList = mutableStateListOf<Apps>()
    var TotalTypedLetters by mutableStateOf(0)

    var COUNT by mutableStateOf(0)
    var NewDay by mutableStateOf(true)

    //refreshs to 0 daily// if more than 50 seconds, get 600 time
    var WaterDOtime_spent by mutableStateOf(0)
    //endregion

    //region PERMISSIONS
    var App_Description by mutableStateOf("A simple gamified app, that encourages, actions you want to take: Just retype your goals, words you want to learn and earn points to Spend time on your favorite apps")

    var NotificationPermission by mutableStateOf(false);
    var NotificationP_Description by mutableStateOf("Necesary permission for background service to work, stay active: Without it, the app is unable to function properly")
    var DrawOnTopPermission by mutableStateOf(false);
    var DrawOnTopP_Description by mutableStateOf("This is used to block apps, that need points to use (each point = 1s, of usage)")
    var OptimizationExclusionPermission by mutableStateOf(false);
    var OptimizationExclusionP_Description by mutableStateOf("Necesary permission for background service to work.")
    var UsageStatsPermission by mutableStateOf(false);
    var UsageStatsP_Description by mutableStateOf("Necesary so the app can detect other apps, and the amount spent on them")
    var DeviceAdminPermission by mutableStateOf(false);
    var DeviceAdminP_Description by mutableStateOf("Optional: use when want a little disipline boost")

    //endegion


}


data class Apps(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var packageName: String
) {
    var Block by mutableStateOf(false)
    var Exists by mutableStateOf(true)
    var TimeSpent by mutableStateOf(0f)
}


data class FancyItem(
    var name: MutableState<String> = mutableStateOf(""),
    var done: MutableState<Boolean> = mutableStateOf(false)
)
class Lists {
    val SimpleList = mutableStateListOf<FancyItem>()
    val SimpleList1 = mutableStateListOf<FancyItem>()
}
val Blis = Lists();
inline fun <reified T : Any> MutableList_ToSimple(list: List<T>): List<Map<String, Any?>> {
    return list.map { item ->
        val map = mutableMapOf<String, Any?>()

        T::class.memberProperties.forEach { blar ->
            blar.isAccessible = true
            val value = blar.get(item)

            // If it's a MutableState, get its .value
            if (value is MutableState<*>) {
                map[blar.name] = value.value
            } else {
                map[blar.name] = value
            }
        }

        map
    }
}


fun ListsSaveding() {
    val gson = Gson()

    //region SETUP

    val context = Global1.context
    var WhereSave = "MyPrefs"
    val data = context.getSharedPreferences(WhereSave, Context.MODE_PRIVATE)
    val editor = data.edit()

    //endregion SETUP


    fun saveAllLists(context: Context) {
        val gson = Gson()
        val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Loop through all properties inside Blis (like SimpleList, SimpleList1, etc.)
        Lists::class.memberProperties.forEach { prop ->
            prop.isAccessible = true
            val list = prop.get(Blis)

            if (list is List<*>) {
                // Make sure the list holds FancyItem objects
                val fancyList = list.filterIsInstance<FancyItem>()
                val simpleList = MutableList_ToSimple(fancyList)
                val jsonString = gson.toJson(simpleList)

                // Save using the property name as the key
                editor.putString(prop.name, jsonString)
            }
        }

        editor.apply()
    }



    editor.putString("name", "John")
    editor.apply()

    val name = data.getString("name", "default")


}

object ListsSaved {
    private var Dosave: Job? = null
    fun Bsave() {
        if (Dosave?.isActive == true) return
        Dosave = GlobalScope.launch {
            while (isActive) {
                val data = Global1.context.getSharedPreferences("lists", Context.MODE_PRIVATE)
                val edit = data.edit()

                var CPU = 0
                com.productivity.wind.Settings::class.memberProperties.forEach { bar ->
                    /*CPU usage, forget this ok*/CPU+=20; if (CPU>2000) {log("SettingsManager: Bsave is taking up to many resourcesss. Shorter delay, better synch, like skipping things, and maing sure only one runs, can greatly decrease THE CPU USAGE", "Bad") }//ADD SUPER UNIVERSAL STUFFF
                    bar.isAccessible = true
                    val value = bar.get(Bar)
                    if (value is SnapshotStateList<*>) return@forEach

                    when (value) {
                        is Boolean -> edit.putBoolean(bar.name, value)
                        is String -> edit.putString(bar.name, value)
                        is Int -> edit.putInt(bar.name, value)
                        is Float -> edit.putFloat(bar.name, value)
                        is Long -> edit.putLong(bar.name, value)
                    }
                }
                edit.apply()
                delay(1_000L) // save every 5 seconds
            }
        }
    }
    fun init() {
        val prefs = Global1.context.getSharedPreferences("lists", Context.MODE_PRIVATE)
        if (prefs.all.isEmpty() || initOnce) return
        initOnce= true //MUST USE, ALL ARE ZERO OR NULL

        com.productivity.wind.Settings::class.memberProperties.forEach { barIDK ->
            //best variable is variable//JUST MAKING SURE
            if (barIDK is KMutableProperty1<com.productivity.wind.Settings, *>) {
                @Suppress("UNCHECKED_CAST")
                val bar = barIDK as KMutableProperty1<com.productivity.wind.Settings, Any?>
                bar.isAccessible = true
                val name = bar.name
                val type = bar.returnType.classifier

                val stateProp = bar.getDelegate(Bar)
                when {
                    stateProp is MutableState<*> && type == Boolean::class -> (stateProp as MutableState<Boolean>).value = prefs.getBoolean(name, false)
                    stateProp is MutableState<*> && type == String::class -> (stateProp as MutableState<String>).value = prefs.getString(name, "") ?: ""
                    stateProp is MutableState<*> && type == Int::class -> (stateProp as MutableState<Int>).value = prefs.getInt(name, 0)
                    stateProp is MutableState<*> && type == Float::class -> (stateProp as MutableState<Float>).value = prefs.getFloat(name, 0f)
                    stateProp is MutableState<*> && type == Long::class -> (stateProp as MutableState<Long>).value = prefs.getLong(name, 0L)
                }
            }
            else { log("SettingsManager: Property '${barIDK.name}' is not a var! Make it mutable if you want to sync it.", "Bad") }
        }
    }
}






























//region BACKGROUND TASK

class WatchdogService : Service() {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private var OneJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind: $intent")
        return null
    }

    //region Boring
    private var isOverlayShowing = false

    fun BlockScreen() {
        if (!Settings.canDrawOverlays(this)) { log("canDrawOverlays----PERMISSSION NOT GRANTED", "bad"); return}

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val XmlBuilder = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val XmlView = XmlBuilder.inflate(R.layout.overlay_layout, null)

        //region SCREEN PRAMS-BORING

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        //endregion

        if (!isOverlayShowing) { log("Showing BlockScreen:::isOverlayShowing---${isOverlayShowing}", "bad"); windowManager.addView(XmlView, layoutParams); isOverlayShowing = true }
        if (XmlView?.isAttachedToWindow == true) { } else { isOverlayShowing = false;log("NOW SHOWING BlockScreen:::isOverlayShowing-----${isOverlayShowing}", "bad") }


        //region WAIT 10 SECONS TO LEAVE

        var secondsLeft = 10
        val leaveButton = XmlView.findViewById<Button>(R.id.leaveButton)
        leaveButton.isEnabled = false
        leaveButton.text = secondsLeft.toString()

        val handler = Handler(Looper.getMainLooper())
        val countdown = object : Runnable {
            override fun run() {
                log("BLOCK SCREEN: Counting down---secondsLeft---${secondsLeft}", "bad")
                secondsLeft--

                if (secondsLeft > 0) {
                    leaveButton.text = secondsLeft.toString()
                    handler.postDelayed(this, 1000) // wait 1 second, then run again
                } else {
                    leaveButton.text = "Leave"
                    leaveButton.isEnabled = true // now they can click it
                }
            }
        }
        handler.postDelayed(countdown, 1000) // start after 1 second


        //endregion

        leaveButton.setOnClickListener {
            log("BLOCK SCREEN: Clicked leave button", "bad")
            val intent = Intent(Global1.context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Global1.context.startActivity(intent)

            // Also remove the overlay from screen

            log("Removing Overlay Screen+ isOverlayShowing = false", "bad")
            isOverlayShowing = false
            windowManager.removeView(XmlView)
        }

    }

    //endregion

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        NotificationHelper(this).createNotificationChannel()
        startForeground(1, NotificationHelper(this).buildNotification(),)
        Global1.context = this
        if (OneJob == null || OneJob?.isActive == false) {
            OneJob = serviceScope.launch {
                while (true) {

                    //region SAFETY PURPOSES

                    delay(1000L)
                    Bar.COUNT +=1

                    //endregion


                    //region BLOCK APP

                    val usageStatsManager = Global1.context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                    val NowTime = System.currentTimeMillis()

                    /*
                    * THIS IS NOT SUPER ACCURATE
                    ? If you want better precision, you’ll need an Accessibility Service.
                    !THIS REQUIRES NAVIGATING USER TO IT*/
                    val AppsUsed = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, NowTime - 20_000, NowTime)
                    val currentApp = AppsUsed?.maxByOrNull { it.lastTimeUsed }?.packageName

                    log("BACKGROUND---CURRENT APP:::${currentApp};", "bad")
                    if (currentApp == "net.pluckeye.tober" ) {
                        if (Bar.funTime >0) {
                            Bar.funTime -=1
                            log("BACKGROUND---Spending Time??:::${Bar.funTime};", "bad")
                        }
                        else {
                            BlockScreen()
                            log("BACKGROUND---Blocking APP:::${currentApp}; ${Bar.COUNT}", "bad")
                        }
                    }
                    if (currentApp == "com.android.settings") {

                        if (Bar.funTime >1_000) {
                            log("BACKGROUND---Spending Time??:::${Bar.funTime};", "bad")
                        }
                        else {
                            if (gotAdmin) {
                                BlockScreen()
                                log("BACKGROUND---Blocking APP:::${currentApp}; ${Bar.COUNT}", "bad")
                            }
                        }
                    }
                    if (currentApp == "com.seekrtech.waterapp") {
                        log("NEW DAY??; ${Bar.NewDay}", "bad")
                        log("waterdo time; ${Bar.WaterDOtime_spent}", "bad")
                        if (Bar.NewDay) {
                            if (Bar.WaterDOtime_spent > 50) {
                                Bar.funTime += 600
                                Bar.NewDay = false
                            } else {
                                Bar.WaterDOtime_spent += 1
                            }
                        }
                    }

                    //endregion BLOCK APP


                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() { super.onDestroy(); serviceScope.cancel() }
}


//endregion










//region OnAppStart

@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
    SettingsSaved.init()
    SettingsSaved.Bsave()
    //SettingsSaved.initialize(Global1.context, Bar)
    //SettingsSaved.startAutoSave(Global1.context, Bar)


    //Background thing

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { context.startForegroundService(Intent(context, WatchdogService::class.java))} else { context.startService(Intent(context, WatchdogService::class.java)) }
}


@Composable
fun AppStart() {
    LaunchedEffect(Unit) {
        DayChecker.start()
        Bar.CheckInstalledApps= true
    }
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