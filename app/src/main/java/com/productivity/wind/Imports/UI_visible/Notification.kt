package com.productivity.wind.Imports.UI_visible
 
import android.annotation.*
import androidx.annotation.RequiresApi
import android.app.*
import android.app.NotificationManager.*
import android.content.*
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.*
import android.os.Handler
import android.provider.*
import android.view.*
import android.widget.*
import android.support.v4.media.session.*
import androidx.activity.compose.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import androidx.core.app.*
import androidx.core.app.NotificationCompat.ProgressStyle
import androidx.core.graphics.drawable.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.*
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.permissions.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.productivity.wind.*
import com.productivity.wind.Imports.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.R
import kotlinx.coroutines.*
import kotlin.math.*
import java.util.logging.*




@RequiresApi(Build.VERSION_CODES.S)
fun startSystemTimer(context: Context, minutes: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    if (!alarmManager.canScheduleExactAlarms()) {
        startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        return
    }

    // Time from now in milliseconds
    val triggerAtMillis = System.currentTimeMillis() + minutes * 60 * 1000

    // This PendingIntent opens your app when the timer is tapped
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Build AlarmClockInfo
    val alarmClockInfo = AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent)

    // Register it
    alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
}

fun showOrderNotification(
    id: Int = 2,
    title: Str = "hi",
    text: Str = "test",                        
    Do: suspend (builder: NotificationBuilder, manager: NotificationManager) -> Unit = { _, _ -> }     
): Notification {
    Permission.notification()
    
    val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val builder = NotificationCompat.Builder(AppCtx, "WindApp_id")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("You order is being placed")
        .setContentText("Confirming with bakery...")
        .setShortCriticalText("Placing")
        .setOngoing(true)
        .setRequestPromotedOngoing(true)
        .setStyle(NotificationCompat.ProgressStyle()
                .setProgress(75)  // max=100, current=0, determinate
        )
		.setLargeIcon(toBitmap(myAppRes))
        .setUsesChronometer(true)
        .setChronometerCountDown(true) // optional, for countdown
		.setWhen(System.currentTimeMillis() + 10_000) // example 10 sec timer
        .setShortCriticalText("Arrived")
        //.setStyle(buildBaseProgressStyle(INITIALIZING).setProgressIndeterminate(true))

	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // API 34
		if (!manager.canPostPromotedNotifications()) {
			Vlog("CANT POST PROMOTED NOTIFICATIONS")
		}
	} else {
		Vlog("Promoted notifications not supported on this Android version")
	}

          
            
    val notifi = builder.build()
    manager.notify(id, notifi)
        
    return notifi
}


//setDeleteIntent() when user dismisses what doo

/*
.setStyle(
                        buildBaseProgressStyle(ORDER_COMPLETE)
                            .setProgressTrackerIcon(
                                IconCompat.createWithResource(
                                    AppCtx, myAppRes
                                )
                            )
                            .setProgress(100)
                    )
                    .setShortCriticalText("Arrived")


*/


fun Notification(
    title: Str,
    text: Str,
    id: Int = 1,                         
    Do: suspend (builder: NotificationBuilder, manager: NotificationManager) -> Unit = { _, _ -> }     
): Notification {
    val myMediaSession = MediaSessionCompat(AppCtx, "MyMedia")

    Permission.notification()
    var firstTime = if (notifMap[id]== null) yes else no
    
    val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val builder = getNotifBuilder(id)
            .setContentTitle(title)
            .setContentText(text)

            
    val notifi = builder.build()
    manager.notify(id, notifi)

    // optional dynamic updates
    if (firstTime){
        CoroutineScope(Dispatchers.Default).launch {
           Do(builder, manager)
        }
    }
        
    return notifi
}



fun Notification(
    xml: Int,
    id: Int = 1,
    Do: suspend (builder: NotificationCompat.Builder, remoteView: RemoteViews, manager: NotificationManager) -> Unit = { _, _, _ -> }
) {
    val deleteIntent = Intent(AppCtx, NotificationSwipeReceiver::class.java).apply {
        putExtra("notif_id", id)
    }
    
    val pendingIntent = PendingIntent.getBroadcast(
        AppCtx,
        id,
        deleteIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    Permission.notification()
        val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // create RemoteViews from your XML layout
        val remoteView = RemoteViews(AppCtx.packageName, xml)

        // create or reuse builder
        val builder = notifMap[id] ?: NotificationCompat.Builder(AppCtx, "WindApp_id")
            .setSmallIcon(myAppRes)         
            .setAutoCancel(false)
            .setCustomContentView(remoteView)
            .setCustomBigContentView(remoteView)
            .setDeleteIntent(pendingIntent)

        notifMap[id] = builder

        // show notification
        manager.notify(id, builder.build())

        
        CoroutineScope(Dispatchers.Default).launch {
            Do(builder, remoteView, manager)
        }
    
}
































 












@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Composable
fun LiveUpdateSample() {
    val notifiManager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    Notifi2.Init(notifiManager)

    /*

    Try("postNotification ERROR"){
       var isPostPromotionsEnabled by m(Notifi2.isPostPromotionsEnabled())
    }
    
    
    
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        isPostPromotionsEnabled = Notifi2.isPostPromotionsEnabled()
    }
    if (!isPostPromotionsEnabled) {
        Text(
            text = "string.post_promoted_permission_message",
            modifier = Mod.space(h = 10),
        )
        Button(
            onClick = {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_PROMOTION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, AppCtx.packageName)
                }
                AppCtx.startActivity(intent)
            },
        ) {
            Text("string.to_settings")
        }
    }
            
    
            Text("liveUodateSummaryText")
            move(4)
            Btn("Checkout"){
                Notifi2.start()
                Vlog("orderdd")
            }
     */
    
}




object Notifi2 {
    private lateinit var notificationManager: NotificationManager
    const val CHANNEL_ID = "live_updates_channel_id"
    private const val CHANNEL_NAME = "live_updates_channel_name"
    private const val NOTIFICATION_ID = 1234


    @RequiresApi(Build.VERSION_CODES.O)
    fun Init(notifManager: NotificationManager) {
        notificationManager = notifManager
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

    private enum class OrderState(val delay: Long) {
        INITIALIZING(5000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(INITIALIZING)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("You order is being placed")
                    .setContentText("Confirming with bakery...")
                    .setShortCriticalText("Placing")
                    .setStyle(buildBaseProgressStyle(INITIALIZING).setProgressIndeterminate(true))
            }
        },
        FOOD_PREPARATION(9000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(FOOD_PREPARATION)
                    .setContentTitle("Your order is being prepared")
                    .setContentText("Next step will be delivery")
                    .setShortCriticalText("Prepping")
                    .setLargeIcon(toBitmap(myAppRes))
                    .setStyle(buildBaseProgressStyle(FOOD_PREPARATION).setProgress(25))
            }
        },
        FOOD_ENROUTE(13000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(FOOD_ENROUTE)
                    .setContentTitle("Your order is on its way")
                    .setContentText("Enroute to destination")
                    .setStyle(
                        buildBaseProgressStyle(FOOD_ENROUTE)
                            .setProgressTrackerIcon(
                                IconCompat.createWithResource(
                                    AppCtx, myAppRes
                                )
                            )
                            .setProgress(50)
                    )
                    .setLargeIcon(toBitmap(myAppRes))
                    .setWhen(System.currentTimeMillis().plus(11 * 60 * 1000 /* 10 min */))
                    .setUsesChronometer(true)
                    .setChronometerCountDown(true)
            }
        },
        FOOD_ARRIVING(18000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(FOOD_ARRIVING)
                    .setContentTitle("Your order is arriving and has been dropped off")
                    .setContentText("Enjoy & don't forget to refrigerate any perishable items.")
                    .setStyle(
                        buildBaseProgressStyle(FOOD_ARRIVING)
                            .setProgressTrackerIcon(
                                IconCompat.createWithResource(
                                    AppCtx, myAppRes
                                )
                            )
                            .setProgress(75)
                    )
                    .setLargeIcon(toBitmap(myAppRes))
                    .setWhen(System.currentTimeMillis().plus(11 * 60 * 500 /* 5 min */))
                    .setUsesChronometer(true)
                    .setChronometerCountDown(true)
            }
        },
        ORDER_COMPLETE(21000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(ORDER_COMPLETE)
                    .setContentTitle("Your order is complete.")
                    .setContentText("Thank you for using JetSnack for your snacking needs.")
                    .setStyle(
                        buildBaseProgressStyle(ORDER_COMPLETE)
                            .setProgressTrackerIcon(
                                IconCompat.createWithResource(
                                    AppCtx, myAppRes
                                )
                            )
                            .setProgress(100)
                    )
                    .setShortCriticalText("Arrived")
                    .setLargeIcon(toBitmap(myAppRes))
            }
        };


        @RequiresApi(Build.VERSION_CODES.BAKLAVA)
        fun buildBaseProgressStyle(orderState: OrderState): ProgressStyle {
            val pointColor = android.graphics.Color.valueOf(
                236f / 255f, // Normalize red value to be between 0.0 and 1.0
                183f / 255f, // Normalize green value to be between 0.0 and 1.0
                255f / 255f, // Normalize blue value to be between 0.0 and 1.0
                1f,
            ).toArgb()
            val segmentColor = android.graphics.Color.valueOf(
                134f / 255f, // Normalize red value to be between 0.0 and 1.0
                247f / 255f, // Normalize green value to be between 0.0 and 1.0
                250f / 255f, // Normalize blue value to be between 0.0 and 1.0
                1f,
            ).toArgb()
            var progressStyle = NotificationCompat.ProgressStyle()
                .setProgressPoints(
                    listOf(
                        ProgressStyle.Point(25).setColor(pointColor),
                        ProgressStyle.Point(50).setColor(pointColor),
                        ProgressStyle.Point(75).setColor(pointColor),
                        ProgressStyle.Point(100).setColor(pointColor)
                    )
                ).setProgressSegments(
                    listOf(
                        ProgressStyle.Segment(25).setColor(segmentColor),
                        ProgressStyle.Segment(25).setColor(segmentColor),
                        ProgressStyle.Segment(25).setColor(segmentColor),
                        ProgressStyle.Segment(25).setColor(segmentColor)

                    )
                )
            when (orderState) {
                INITIALIZING -> {}
                FOOD_PREPARATION -> {}
                FOOD_ENROUTE -> progressStyle.setProgressPoints(
                    listOf(
                        ProgressStyle.Point(25).setColor(pointColor)
                    )
                )

                FOOD_ARRIVING -> progressStyle.setProgressPoints(
                    listOf(
                        ProgressStyle.Point(25).setColor(pointColor),
                        ProgressStyle.Point(50).setColor(pointColor)
                    )
                )

                ORDER_COMPLETE -> progressStyle.setProgressPoints(
                    listOf(
                        ProgressStyle.Point(25).setColor(pointColor),
                        ProgressStyle.Point(50).setColor(pointColor),
                        ProgressStyle.Point(75).setColor(pointColor)
                    )
                )
            }
            return progressStyle
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun buildBaseNotification(orderState: OrderState): NotificationCompat.Builder {
            val notificationBuilder = NotificationCompat.Builder(AppCtx, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true)
                .setRequestPromotedOngoing(true)

            when (orderState) {
                INITIALIZING -> {}
                FOOD_PREPARATION -> {}
                FOOD_ENROUTE -> {}
                FOOD_ARRIVING ->
                    notificationBuilder
                        .addAction(
                            NotificationCompat.Action.Builder(null, "Got it", null).build()
                        )
                        .addAction(
                            NotificationCompat.Action.Builder(null, "Tip", null).build()
                        )
                ORDER_COMPLETE ->
                    notificationBuilder
                        .addAction(
                            NotificationCompat.Action.Builder(
                                null, "Rate delivery", null).build()
                        )
            }
            return notificationBuilder
        }

        abstract fun buildNotification(): NotificationCompat.Builder
    }

    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    fun start() {
        for (state in OrderState.entries) {
            val notification = state.buildNotification().build()

            Logger.getLogger("canPostPromotedNotifications")
                .log(
                    Level.INFO,
                    notificationManager.canPostPromotedNotifications().toString())
            Logger.getLogger("hasPromotableCharacteristics")
                .log(
                    Level.INFO,
                    notification.hasPromotableCharacteristics().toString())

            Handler(Looper.getMainLooper()).postDelayed({
                notificationManager.notify(NOTIFICATION_ID, notification)
            }, state.delay)
        }
    }

    /*
    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    fun isPostPromotionsEnabled(): Boolean {
        return if (::notificationManager.isInitialized) {
           notificationManager.canPostPromotedNotifications()
        } else {
           false // or true if you prefer default
        }
    }
    */


    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    fun isPostPromotionsEnabled(): Bool {
        return notificationManager.canPostPromotedNotifications()
    }
}



