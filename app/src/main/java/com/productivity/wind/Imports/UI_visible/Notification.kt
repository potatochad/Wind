package com.productivity.wind.Imports.UI_visible
 
import android.annotation.SuppressLint
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import androidx.compose.foundation.text.selection.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.activity.compose.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.gms.location.*
import android.os.*
import kotlin.math.*
import androidx.compose.ui.geometry.*
import androidx.compose.foundation.lazy.*
import com.productivity.wind.Imports.*
import android.app.NotificationManager
import com.productivity.wind.R
import android.view.*
import android.widget.*
import android.app.*
import android.content.*
import android.graphics.*


import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.*



import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.annotation.RequiresApi

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
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                  .setShowActionsInCompactView(0, 1)
                  .setMediaSession(myMediaSession.sessionToken)
            )

            


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



/**
 * Creates a Live Update (Promoted) notification.
 */
fun LiveUpdateNotification(
    title: Str,
    text: Str,
    id: Int = 2,
    shortCriticalText: Str? = null,
    whenTime: Long? = null,
    Do: suspend (builder: NotificationCompat.Builder, manager: NotificationManager) -> Unit = { _, _ -> }
): Notification {
    Permission.notification()
    
    val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Check if app can post promoted notifications (Android 15+)
    if (Build.VERSION.SDK_INT >= 35) {
        try {
            // Using reflection/string keys for safety against unresolved symbols
            val canPost = manager.javaClass.getMethod("canPostPromotedNotifications").invoke(manager) as Boolean
            if (!canPost) {
                val intent = Intent("android.settings.MANAGE_APP_PROMOTED_NOTIFICATIONS").apply {
                    putExtra("android.provider.extra.APP_PACKAGE", AppCtx.packageName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                AppCtx.startActivity(intent)
            }
        } catch (e: Exception) {
             // Fallback or ignore if SDK doesn't support the call yet
        }
    }

    val builder = notifMap[id] ?: NotificationCompat.Builder(AppCtx, "WindApp_id")
        .setSmallIcon(myAppRes)
        .setOngoing(true)
        .setCategory(NotificationCompat.CATEGORY_PROGRESS)
    
    builder.setContentTitle(title)
           .setContentText(text)
           .setOnlyAlertOnce(true)

    // Live Update requirement: Promotion request (using string key "android.request_promoted_ongoing")
    builder.extras.putBoolean("android.request_promoted_ongoing", true)
    
    // Status chip text (Android 15+)
    if (shortCriticalText != null && Build.VERSION.SDK_INT >= 35) {
        builder.extras.putCharSequence("android.shortCriticalText", shortCriticalText)
    }

    if (whenTime != null) {
        builder.setWhen(whenTime)
        builder.setShowWhen(true)
        builder.setUsesChronometer(true)
    }

    val notification = builder.build()
    
    // Check promotable characteristics (optional debug check)
    if (Build.VERSION.SDK_INT >= 35) {
        try {
            val hasPromotable = notification.javaClass.getMethod("hasPromotableCharacteristics").invoke(notification) as Boolean
            if (!hasPromotable) {
                log("Notification does not meet Live Update requirements")
            }
        } catch (e: Exception) {}
    }

    manager.notify(id, notification)
    notifMap[id] = builder

    CoroutineScope(Dispatchers.Default).launch {
        Do(builder, manager)
    }

    return notification
}
