package com.productivity.wind

import kotlin.reflect.full.*
import kotlin.reflect.*
import androidx.compose.runtime.snapshots.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.heightIn
import com.productivity.wind.Screens.*
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
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.*
import androidx.compose.ui.graphics.Color
import android.content.pm.*
import com.productivity.wind.Imports.*
import android.app.*

fun onNewDay() {  
        
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
    
}
//m-mutable state, ml- mutablelistof



val trackedLists = listOf(
        Dset("Bar.ListApps", "apps"),
        
    )



var apps = ml(DataApps())

data class DataApps(
    var id: Str = Id(),
    var name: Str = "",
    var done: Bool = false,
    var packageName: Str = "",
    var NowTime: Int = 0,
    var DoneTime: Int = 0,
    var Worth: Int = 0,
)
fun MutableList<DataApps>.edit(id: Str, block: DataApps.() -> Unit) {
    val index = indexOfFirst { it.id == id }
    if (index != -1) {
        val old = this[index]
        old.block()              
        this[index] = old.copy() 
    }
}


fun getApps(): List<ResolveInfo> {
    val context = Global1.context
    val pm = context.packageManager
    val launchIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    return pm.queryIntentActivities(launchIntent, 0)
}

fun getTodayAppUsage(packageName: String): Int {
    val context = Global1.context
    // Today window
    val end = System.currentTimeMillis()
    val cal = java.util.Calendar.getInstance().apply {
        set(java.util.Calendar.HOUR_OF_DAY, 0)
        set(java.util.Calendar.MINUTE, 0)
        set(java.util.Calendar.SECOND, 0)
        set(java.util.Calendar.MILLISECOND, 0)
    }
    val start = cal.timeInMillis

    val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as android.app.usage.UsageStatsManager
    val stats = usm.queryUsageStats(android.app.usage.UsageStatsManager.INTERVAL_DAILY, start, end)

    // Find the usage for the specific package
    val appStats = stats.find { it.packageName == packageName }
    return ((appStats?.totalTimeInForeground ?: 0) / 1000L).toInt().coerceAtLeast(0)
}


fun refreshApps(target: MutableList<DataApps> = apps) {
    val context = Global1.context

    // 1) Get all launchable apps
    val launchables: List<ResolveInfo> = getApps()

    // 2) Check usage permission
    if (!UI.isUsageP_Enabled()) {
        return
    }

    // 3) Preserve old IDs and done flags
    val oldByPkg = target.associateBy { it.packageName }

    // 4) Build fresh list using helper function for today's usage
    val fresh = launchables.map { ri ->
        val pkg = ri.activityInfo.packageName
        val label = ri.loadLabel(context.packageManager)?.toString() ?: pkg
        val old = oldByPkg[pkg]

        DataApps(
            id = old?.id ?: Id(),
            name = label,
            packageName = pkg,
            NowTime = getTodayAppUsage(pkg), // ✅ calls your helper
            done = old?.done ?: false,
            DoneTime = old?.DoneTime ?: 0,
            Worth = old?.Worth ?: 0
        )
    }.sortedByDescending { it.NowTime }

    // 5) Replace content in-place
    target.clear()
    target.addAll(fresh)
}







fun show(state: MutableState<Boolean>) = state.value = true

object Popup {
    var Edit = m(false)
    var G_Edit = m(false)
    var NeedMorePoints = m(false)
    var AskUsagePermission = m(false)
    var EnableBlocking = m(false)
    
    var AppSelect = m(false)

}





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
@Composable
fun AppStart() {
    LaunchedEffect(Unit) {
        refreshApps()
    }
    LazyMenu { Menu() }
    
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
