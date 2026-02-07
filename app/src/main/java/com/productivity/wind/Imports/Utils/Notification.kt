package com.productivity.wind.Imports.Utils

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





typealias NotificationBuilder = NotificationCompat.Builder

val notifMap = mutableMapOf<Int, NotificationBuilder>()
var notifyID by m(0)

fun Show(notifi: Notification, id: Int = 1) { 
    val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.notify(id, notifi) 
}

class NotificationSwipeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notifId = intent.getIntExtra("notif_id", -1)

        
        log("DOESNT WORK CURRENTLY")
        
    }
}



fun getNotifBuilder(id: Int): NotificationBuilder {
    val builder = notifMap[id] ?: NotificationBuilder(AppCtx, "WindApp_id")
        .setSmallIcon(myAppRes)
        .setAutoCancel(true)

    notifMap[id] = builder
    return builder
}




fun NotificationBuilder.title(x: Str) = apply { setContentTitle(x) }
fun NotificationBuilder.text(x: Str)  = apply { setContentText(x) }







// 1. Set text (any type, auto toString)
fun RemoteViews.text(id: Int, value: Any) = setTextViewText(id, value.toString())

// 2. Set image resource
fun RemoteViews.image(id: Int, resId: Int) = setImageViewResource(id, resId)

// 3. Set visibility
fun RemoteViews.visible(id: Int, visible: Bool) =
    setViewVisibility(id, if (visible) View.VISIBLE else View.GONE)

// 4. Set click pending intent
fun RemoteViews.click(id: Int, pendingIntent: PendingIntent) =
    setOnClickPendingIntent(id, pendingIntent)

// 5. Set text color
fun RemoteViews.color(id: Int, color: Int) =
    setTextColor(id, color)





    
