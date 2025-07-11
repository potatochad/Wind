package dev.hossain.keepalive

import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
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


class Settings {
    var funTime by mutableStateOf(0)

    //region COPY PASTE THING
    var targetText by mutableStateOf("I am doing this project to regain freedom in my life. It is most important project ever, but that doesn't mean i need to take it soop seriously. I need to only focus on it,and how I programm, all logic MUST BE WRITTEN BY ME, IT MUST BEEEE, otherwise will spend many hours and thus resulting a catastrophic outcome, of nothing achieved, like those 5 months!!! I need to keep with it, AND GET IT TO BEAR FRUIT AS FAST AS possible. Skip all the nonesense, of logicall app making, just get it done as dirty as possible, to the genshin part. And make it work, stack upon thing after thing. Can improve the thing later, get money to a person, etc... All i must do is stick with the idea: type stuff and get time to have fun. Done, I MUST FOCUS ON ONE IDEA, ONE ONLYYY. Goal is consistency, nothing else, nothing else!!")
    var LetterToTime by mutableStateOf(3)
    var DoneRetype_to_time by mutableStateOf(60)
    var currentInput by mutableStateOf("")
    var highestCorrect by mutableStateOf(0)
    var GenshinApk by mutableStateOf("com.miHoYo.GenshinImpact")
    //endregion


    //region MISALANIOUS
    var ShowMenu by mutableStateOf(false)
    var CheckInstalledApps by mutableStateOf(true)
    var AppList = mutableStateListOf<Apps>()


    var COUNT by mutableStateOf(0)
    var ServiceId by mutableStateOf("null")
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
    private val serviceScope = CoroutineScope(Dispatchers.Default + serviceJob)
    private var OneJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind: $intent")
        return null
    }

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
                    log("BACKGROUND---CURRENT APP:::${currentApp}; ${Bar.COUNT}", "bad")
                    if (currentApp == Bar.GenshinApk) {
                        log("APP IS GENSHINNNNN:::${currentApp}", "bad")
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


