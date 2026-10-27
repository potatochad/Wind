package com.productivity.wind.Imports.Utils.AppsAndDevice

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import android.app.*
import androidx.core.app.*
import android.os.*
import android.content.*
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import android.content.Intent
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.UI_visible.*


fun Context.start(service: Class<out Service>) {
    Permission.ignoreOptimizations()
    startService(Intent(this, service))
}

fun Context.startForeground(service: Class<out Service>) {
    Permission.ignoreOptimizations()

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

		Permission.notification()

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
				var appearOnTop = Permission.appearOnTop()
				while (appearOnTop){
					//for better accuracy need accesibility permission
					var lastUsed = LastUsedApp()

					// log("lastUsed: $lastUsed, appearOnTop: $appearOnTop")

					
					if (!Permission.hasAccessibility("com.teqtic.lockmeout")) {
						DeviceGray()
					} else {
						DeviceGray(no)
					}

					
					
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
	}
}










