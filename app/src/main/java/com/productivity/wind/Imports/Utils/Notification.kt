//‼️ DONT USE GLOBAL APP CONTEXT HERE


package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.String.*
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
import androidx.core.app.NotificationCompat
import com.productivity.wind.R
import android.view.*
import android.widget.*
import android.app.*
import android.content.*
import com.productivity.wind.Imports.UI_visible.*


val notif_Id = "WindApp"
val notif_Name = "WindChannel"



typealias NotifiBuilder = NotificationCompat.Builder

val notifMap = mutableMapOf<Int, NotifiBuilder>()
var notifyID by m(0)



fun NotifBuiltBefore(id: Int) = notifMap[id] != null

fun NotifiBuild(ctx: Context, id: Int): NotifiBuilder {
    val builder = notifMap[id] ?: NotifiBuilder(ctx, notif_Id)

    notifMap[id] = builder
    return builder
}


//called in App.kt
fun CreateNotificationChannel(context: Context) {
    Android8OrAbove {
        val channel = NotificationChannel(
            notif_Id,
            notif_Name,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel description"
            setShowBadge(false)
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}





fun NotifiBuilder.title(x: Str) = apply { setContentTitle(x) }
fun NotifiBuilder.text(x: Str)  = apply { setContentText(x) }

fun RemoteViews.text(id: Int, value: Any) = setTextViewText(id, toStr(value))
fun RemoteViews.image(id: Int, resId: Int) = setImageViewResource(id, resId)
fun RemoteViews.visible(id: Int, visible: Bool) = setViewVisibility(id, if (visible) View.VISIBLE else View.GONE)
fun RemoteViews.click(id: Int, pendingIntent: PendingIntent) = setOnClickPendingIntent(id, pendingIntent)
fun RemoteViews.color(id: Int, color: Int) = setTextColor(id, color)





    
