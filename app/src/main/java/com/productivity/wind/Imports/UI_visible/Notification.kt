//‼️‼️‼️RULES:
//DONT SAVE ANY NOTIFI VALUE IN A GLOBAL VAR
//Call create channels when building notifi


package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.SaveData.List.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.ToX.*
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



typealias NotificationBuilder = NotificationCompat.Builder
fun NotificationBuilder() = NotificationBuilder(App, channelId).icon(myAppRes)
fun NotificationManager() = App.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//!MUST HAVE ALWAYS CONSTANT (every UPDATE!!) UNIQUE ID
//ONLY HAVE ONE! and call it on every app start.
val channelId = "WindID"
fun NotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        App.getSystemService(NotificationManager::class.java).createNotificationChannel(
            NotificationChannel(channelId, "Wind", NotificationManager.IMPORTANCE_LOW).apply {
                this.description = "A general purpose notification channel"
            }
        )
    }
}

//! ONLY use this for notifications
//it has id manager and can be easily messed up
class LazyNotification(
    private val id: Int,
    title: Str = "Wind",
    text: Str = "",
    icon: Int = myAppRes
) {
    val manager = NotificationManager()
    val builder = NotificationBuilder()
        .title(title)
        .text(text)
        .icon(icon)
        .silentUpdates()

    fun update(text: Str) {
        manager.notify(id, builder.text(text).build())
    }

    fun show() {
        manager.notify(id, builder.build())
    }

    fun cancel() {
        manager.cancel(id)
    }
}


fun NotificationBuilder.silentUpdates(x: Bool = yes) = apply { setOnlyAlertOnce(x) }
fun NotificationBuilder.title(x: Str) = apply { setContentTitle(x) }
fun NotificationBuilder.text(x: Str)  = apply { setContentText(x) }

//android weird, ignore the official naming
fun NotificationBuilder.image(x: Bitmap) = apply { setLargeIcon(x) }
fun NotificationBuilder.icon(x: Int) = apply { setSmallIcon(x) }



fun RemoteViews.text(id: Int, value: Any) = setTextViewText(id, toStr(value))
fun RemoteViews.image(id: Int, resId: Int) = setImageViewResource(id, resId)
fun RemoteViews.visible(id: Int, visible: Bool) = setViewVisibility(id, if (visible) View.VISIBLE else View.GONE)
fun RemoteViews.click(id: Int, pendingIntent: PendingIntent) = setOnClickPendingIntent(id, pendingIntent)
fun RemoteViews.color(id: Int, color: Int) = setTextColor(id, color)











