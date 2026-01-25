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
import com.productivity.wind.Imports.Data.*
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
import android.content.Context
import androidx.core.app.NotificationCompat

fun Notification(
    title: Str,
    text: Str,
) {
    Notification{
        val notification = NotificationCompat.Builder(AppCtx, "default")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(myAppRes)
            .setAutoCancel(yes) // disappears when swiped
            .build()

        val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }
}

fun funNotification(title: Str, text: Str) {
    // Create a bitmap from Compose UI
    val bitmap = Bitmap.createBitmap(400, 100, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    ComposeView(AppCtx).apply {
        setContent {
            Column(
                modifier = Modifier
                    .background(Color(0xFF1A1A1A))
                    .padding(12.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = text,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
        }
        draw(canvas)
    }

    // Build the notification
    val notification = NotificationCompat.Builder(AppCtx, "default")
        .setSmallIcon(R.drawable.baseline_radar_24)
        .setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null) // remove default large icon
        )
        .setAutoCancel(true)
        .build()

    val manager = AppCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.notify(1, notification)
}









