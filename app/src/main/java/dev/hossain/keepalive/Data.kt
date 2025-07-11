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


class Settings {
    var funTime by mutableStateOf(0)

    //region COPY PASTE THING
    var targetText by mutableStateOf("I am doing this project to regain freedom in my life. It is most important project ever, but that doesn't mean i need to take it soop seriously. I need to only focus on it,and how I programm, all logic MUST BE WRITTEN BY ME, IT MUST BEEEE, otherwise will spend many hours and thus resulting a catastrophic outcome, of nothing achieved, like those 5 months!!! I need to keep with it, AND GET IT TO BEAR FRUIT AS FAST AS possible. Skip all the nonesense, of logicall app making, just get it done as dirty as possible, to the genshin part. And make it work, stack upon thing after thing. Can improve the thing later, get money to a person, etc... All i must do is stick with the idea: type stuff and get time to have fun. Done, I MUST FOCUS ON ONE IDEA, ONE ONLYYY. Goal is consistency, nothing else, nothing else!!")
    var LetterToTime by mutableStateOf(1)
    var DoneRetype_to_time by mutableStateOf(60)
    var currentInput by mutableStateOf("")
    var highestCorrect by mutableStateOf(0)
    var GenshinApk by mutableStateOf("com.miHoYo.GenshinImpact")
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
                    /* ...
                    || currentApp == "com.sec.android.app.clockpackage"*/
                    if (currentApp == Bar.GenshinApk ) {
                        if (Bar.funTime >100) {
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
    lateinit var context: Context
    lateinit var navController: NavHostController
}


//endregion


