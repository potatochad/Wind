package dev.hossain.keepalive

import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.graphics.drawable.Drawable
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
import dev.hossain.keepalive.data.logging.AppActivityLogger
import dev.hossain.keepalive.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import android.provider.Settings
import android.view.LayoutInflater
import android.view.WindowManager
import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.view.View
import android.graphics.PixelFormat
import android.widget.Button
import androidx.compose.material3.AlertDialog
import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


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

    var AccesabilityPermission by mutableStateOf(false)


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

        if (OneJob == null || OneJob?.isActive == false) {
            OneJob = serviceScope.launch {
                while (true) {

                    //region SAFETY PURPOSES

                    delay(1000L)
                    Bar.COUNT +=1

                    //endregion

                    if (Bar.BlockYoutube) {
                        if (Bar.currentApp == "com.google.android.youtube" ) {
                            log("CURRENT APP,blocking: ${Bar.currentApp}", "bad")

                            if (Bar.funTime >100) {
                                Bar.funTime -=1
                                log("BACKGROUND---Spending Time??:::${Bar.funTime};", "bad")
                            }
                            else {
                                BlockScreen()
                                log("BACKGROUND---Blocking APP:::${Bar.currentApp}; ${Bar.COUNT}", "bad")
                            }
                        }
                    }
                    if (Bar.BlockSettings) {
                        if (Bar.currentApp == "com.android.settings") {
                            log("CURRENT APP: ${Bar.currentApp}", "bad")
                            if (Bar.funTime > 1_000) {
                                log("BACKGROUND---Spending Time??:::${Bar.funTime};", "bad")
                            } else {
                                if (gotAdmin) {
                                    BlockScreen()
                                    log(
                                        "BACKGROUND---Blocking APP:::${Bar.currentApp}; ${Bar.COUNT}",
                                        "bad"
                                    )
                                }
                            }
                        }
                    }
                    if (Bar.currentApp == "com.seekrtech.waterapp") {
                        log("CURRENT APP: ${Bar.currentApp}", "bad")
                        if (Bar.NewDay) {
                            log("NEW DAY ----${Bar.NewDay}", "bad")
                            if (Bar.WaterDOtime_spent > 50) {
                                Bar.funTime += 600
                                Bar.NewDay = false
                            } else {
                                Bar.WaterDOtime_spent += 1
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
        AccessibilityPermission()
    }
}



//endregion

//region GLOBAL
//* CONTEXT from anywhere!!!
object Global1 {
    lateinit var context: Context
    lateinit var navController: NavHostController
}


//endregion


































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

        Log.d("UI-LOG", "$indent Node:")
        if (!text.isNullOrEmpty()) Log.d("UI-LOG", "$indent   Text: $text")
        if (!desc.isNullOrEmpty()) Log.d("UI-LOG", "$indent   Description: $desc")
        if (!id.isNullOrEmpty()) Log.d("UI-LOG", "$indent   ID: $id")
        Log.d("UI-LOG", "$indent   Class: $className")

        for (i in 0 until node.childCount) {
            exploreNodeTree(node.getChild(i), depth + 1)
        }
    }
    fun GETEVERYTHING(event: AccessibilityEvent?) {
        val root = rootInActiveWindow
        if (root != null) {
            Log.d("UI-LOG", "---- NEW SCREEN EVENT ----")
            exploreNodeTree(root, 0)
        }
    }
    override fun onInterrupt() {
        log("ACESABILITY Service interrupted.", "bad")
    }
}