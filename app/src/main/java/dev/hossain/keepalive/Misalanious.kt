package dev.hossain.keepalive

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.hossain.keepalive.data.logging.AppActivityLogger
import dev.hossain.keepalive.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber




//region BACKGROUND TASK

class WatchdogService : Service() {
    companion object { private const val NOTIFICATION_ID = 1 }
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private val notificationHelper = NotificationHelper(this)
    private lateinit var activityLogger: AppActivityLogger
    private var currentServiceInstanceId: Int = 0
    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind: $intent")
        return null
    }

    //* FUNCTION RESPONSIBLE FOR WHAT HAPPENS WHEN BACKGROUND TASK RUNS
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        Timber.d("onStartCommand() called with: intent = $intent, flags = $flags, startId = $startId")
        currentServiceInstanceId = startId
        notificationHelper.createNotificationChannel()
        activityLogger = AppActivityLogger(applicationContext)
        startForeground(NOTIFICATION_ID, notificationHelper.buildNotification(),)


        serviceScope.launch {


        }
        return START_STICKY
    }

    override fun onDestroy() { super.onDestroy(); serviceScope.cancel() }
}


//endregion


//region NavController


fun NavGraphBuilder.OtherScreens(){
    composable("TrueMain") {
        val navController = rememberNavController()
        TrueMain(navController)
    }
    composable("FunScreen") {
        val navController = rememberNavController()
        FunScreen(navController)
    }

}

//endregion

//region MICALANIOUS UI
@Composable
fun MICALANIOUS_Ui() {

    GetToWorkAlert()

}
@Composable
fun GetToWorkAlert(){
    if (S_Data.bool("showWorkAlert")) {
        LaunchedEffect(Unit) {
            delay(3000)
            S_manager.update(S_Data.id, mapOf("showWorkAlert" to false))
        }

        Snackbar(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("NO TIME! GET TO WORK.")
        }
    }
}

//endregion


//* DATA
val S_manager = ItemManager("Settings")
var S_Data = S_manager.createOrUpdate(id = "SettingsId", defaults = mapOf(
    "targetText" to "I am doing this project to regain freedom in my life. It is most important project ever, but that doesn't mean i need to take it soop seriously. I need to only focus on it, do the pomo. And spend half time improving, half time using the product. Done. I need to keep with it for 100 days for it to bear fruit. Right now it won't work/ the initial mvp is terrible. But that's ok. I will improve it slowly, one tiny feature at a time. All i must do is stick with the idea: type stuff and get time to have fun. Done. That is it!!!!. Goal is consistency, nothing else, nothing else!!",
    "LetterToTime" to 5,
    "DoneRetype_to_time" to 60,
    "funTime" to 0,
    "currentInput" to "",
    "highestCorrect" to 0,

    /*? MISALANIOUS LOGIC MANAGEMENT */
    "showWorkAlert" to false,
))

