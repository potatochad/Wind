package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.String.*
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



class Notifi(
    title: Str,
    text: Str,
	val id: Int = 111,
) {
    val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

	
    var title = title
        set(value) {
            field = value
            make()
        }

    var text = text
        set(value) {
            field = value
            make()
        }

    fun build(): Notification {
        return getNotifBuilder(id)
            .title(title)
            .text(text)
			.setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
			.setOngoing(true)
			.setSilent(true)
            .build()
    }

    fun make() {
        if (Permission.notification()) {
			manager.notify(id, build())
		}
	}

	fun startForeground(service: Service) {
		if (Permission.notification()) {
			service.startForeground(id, build())
		}
	}
}


/*
class Notifi(
    xml: Int,
    val id: Int = 111,
) {

    val manager =
        AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    var remoteView = RemoteViews(AppCtx.packageName, xml)
        set(value) {
            field = value
            make()
        }

    val builder = NotificationCompat.Builder(AppCtx, notif_Id)
        .setSmallIcon(myAppRes)
        .setAutoCancel(false)
        .setCustomContentView(remoteView)
        .setCustomBigContentView(remoteView)
        .setDeleteIntent(
            PendingIntent.getBroadcast(
                AppCtx,
                id,
                Intent(AppCtx, NotificationSwipeReceiver::class.java).apply {
                    putExtra(notif_Id, id)
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

    fun build(): android.app.Notification {
        builder.setCustomContentView(remoteView)
        builder.setCustomBigContentView(remoteView)
        return builder.build()
    }

    fun make() {
        if (Permission.notification()) {
            manager.notify(id, build())
        }
    }

    fun startForeground(service: Service) {
        if (Permission.notification()) {
            service.startForeground(id, build())
        }
    }

    fun update(block: RemoteViews.() -> Unit) {
        remoteView.block()
        make()
    }
}
*/

fun Notification(
    title: Str,
    text: Str,
    id: Int = 1,                         
    Do: suspend (builder: NotificationBuilder, manager: NotificationManager) -> Unit = { _, _ -> }     
): Notification {
    val myMediaSession = MediaSessionCompat(AppCtx, "MyMedia")

    Permission.notification()
    var firstTime = !NotifBuiltBefore(id)
    
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



/*
// doesnt work‼️‼️
fun Notification(
    xml: Int,
    id: Int = 1,
    Do: suspend (builder: NotificationCompat.Builder, remoteView: RemoteViews, manager: NotificationManager) -> Unit = { _, _, _ -> }
) {
    val deleteIntent = Intent(AppCtx, NotificationSwipeReceiver::class.java).apply {
        putExtra(notif_Id, id)
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
        val builder = notifMap[id] ?: NotificationCompat.Builder(AppCtx, notif_Id)
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


fun showOrderNotification(
    id: Int = 2,
    title: Str = "hi",
    text: Str = "test",                        
    Do: suspend (builder: NotificationBuilder, manager: NotificationManager) -> Unit = { _, _ -> }     
): Notification {
    Permission.notification()
    
    val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val builder = NotificationCompat.Builder(AppCtx, notif_Id)
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

	if (Build.VERSION.SDK_INT >= 36) {
		if (!manager.canPostPromotedNotifications()) {
			Vlog("CANNOT POST PROMOTED")
			/*
			// real way: Settings.ACTION_MANAGE_APP_PROMOTED_NOTIFICATIONS
			val intent = Intent(Settings.ACTION_MANAGE_APP_PROMOTED_NOTIFICATIONS).apply {
				putExtra(Settings.EXTRA_APP_PACKAGE, AppCtx.packageName)
			}
			startActivity(intent)
			*/
		}
	} else {
		Vlog("Device too old for promoted notifications")
	}
	

          
            
    val notifi = builder.build()
    manager.notify(id, notifi)
        
    return notifi
}


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
    val intent = Intent(context, AppUI::class.java)
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




*/
