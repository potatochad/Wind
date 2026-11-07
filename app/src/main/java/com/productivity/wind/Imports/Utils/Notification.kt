//‼️‼️‼️RULES:
//DONT SAVE ANY NOTIFI VALUE IN A GLOBAL VAR
//Call create channels when building notifi

package com.productivity.wind.Imports.Utils

import com.productivity.wind.*
import com.productivity.wind.Imports.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.SaveData.List.*
import com.productivity.wind.Imports.Utils.String.*
import com.productivity.wind.Imports.Utils.ToX.*
import android.annotation.*
import android.app.*
import android.content.*
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.*
import android.provider.*
import android.support.v4.media.session.*
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.*
import androidx.core.app.NotificationCompat.ProgressStyle
import androidx.core.graphics.drawable.*
import androidx.lifecycle.*
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.*
import java.util.logging.*
import kotlin.math.*


typealias NotificationBuilder = NotificationCompat.Builder
fun NotificationBuilder() = NotificationBuilder(App, channelId).icon(myAppRes)
fun NotificationManager() = SystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//!MUST HAVE ALWAYS CONSTANT (every UPDATE!!) UNIQUE ID
//ONLY HAVE ONE! and call it on every app start.
val channelId = "WindID"
fun NotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        SystemService(NotificationManager::class)?.createNotificationChannel(
            NotificationChannel(channelId, "Wind", NotificationManager.IMPORTANCE_LOW).apply {
                this.description = "A general purpose notification channel"
            }
        )
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



