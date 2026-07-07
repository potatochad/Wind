//‼️CAREFULL WITH APP CONTEXT


package com.productivity.wind.Imports

import com.productivity.wind.Imports.Utils.String.*
import timber.log.Timber
import android.app.usage.UsageStatsManager
import androidx.compose.foundation.interaction.*
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.isAccessible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import android.content.ClipData
import android.content.ClipboardManager
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import android.content.Intent
import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import android.graphics.drawable.Drawable
import android.content.pm.*
import java.util.*
import com.productivity.wind.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.core.content.ContextCompat

//region LATER USE

fun Context.start(service: Class<out Service>) {
    val intent = Intent(this, service)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}

fun Context.stop(service: Class<out Service>) {
    stopService(Intent(this, service))
}


/*
class AppBackground : Service() {
	
	val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    var job: Job? = null
	var timeRan by m(1)

	
	override fun onCreate() {
		super.onCreate()

		try {
   		   val notif = Notifi("Background Tasks: [testing]", "running...", 1)

           Vlog("service create")
           notif.startForeground(this)
		} catch (e: Exception) {
            LogCrash(e)
		}
	}

	
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {  
		try {
           if (job == null || job?.isActive == no) {
               job = serviceScope.launch {
				   val notif = Notifi("Background Tasks: [testing]", "running...", 1)
				   AppBackground(notif)
               }
           }
		} catch (e: Exception) {
            LogCrash(e)
		}

        return START_STICKY
    }

    override fun onDestroy() { 
		serviceScope.cancel() 
		super.onDestroy()
	}
	override fun onBind(intent: Intent?) = null
}
*/


class AppBackground : Service() {

    companion object {
        private const val CHANNEL_ID = "background_tasks"
        private const val NOTIFICATION_ID = 1
    }

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var job: Job? = null

    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(myAppRes) // REQUIRED
            .setContentTitle("Background Tasks")
            .setContentText("Running...")
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun updateNotification(text: String) {
        val updated = notificationBuilder
            .setContentText(text)
            .setOnlyAlertOnce(true)
            .build()

        NotificationManagerCompat.from(this)
            .notify(NOTIFICATION_ID, updated)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (job?.isActive != true) {
            job = serviceScope.launch {
				while (true){
					//for better accuracy need accesibility permission
					var lastUsed = LastUsedApp()

					log("lastUsed: $lastUsed")




					
					
					updateNotification("Running...$lastUsed")
					delay(1000)
					
					updateNotification("Running.. $lastUsed")
					delay(1000)

					updateNotification("Running.  $lastUsed")
					delay(1000)

					updateNotification("Running   $lastUsed")
					delay(1000)

					updateNotification("Running.  $lastUsed")
					delay(1000)

					updateNotification("Running.. $lastUsed")
					delay(1000)
				}
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        job?.cancel()
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Background Tasks",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background service"
            }

            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }
}



class BootReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		try {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Intent(context, AppBackground::class.java).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    log("Starting the service in >=26 Mode from a BroadcastReceiver")
                    context.startForegroundService(it)
                    return
                }
                log("Starting the service in < 26 Mode from a BroadcastReceiver")
                context.startService(it)
            }
        }
		} catch (e: Exception) {
            LogCrash(e)
		}
	}
}






/*
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

                        /*

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

					}*/   }}
            }
        }

        return START_STICKY
    }

    override fun onDestroy() { super.onDestroy(); serviceScope.cancel() }
}

*/







