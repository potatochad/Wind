//‼️‼️‼️RULES:
//DONT USE GLOBAL CONTEXT HERE
//DONT USE LATEINIT HERE
//DONT SAVE ANY NOTIFI VALUE IN A GLOBAL VAR
//Call create channels when building notifi


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




val notifi_Id = "WindApp"
val notifi_Name = "WindChannel"



typealias NotifiBuilder = NotificationCompat.Builder


fun NotifiManager(ctx: Context) = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


fun NotifiChannel(
	ctx: Context,
    id: Str = notifi_Id,
    name: Str = notifi_Name,
    importance: Int = NotificationManager.IMPORTANCE_HIGH,
    Do: NotificationChannel.() -> Unit = {
        description = "Channel description"
        setShowBadge(false)
	}
) {
    Android8OrAbove {
        NotifiManager(ctx).createNotificationChannel(
			NotificationChannel(
				id,
				name,
				importance,
			).apply {
				Do()
			}
		)
    }
}





fun NotifiBuilder.title(x: Str) = apply { setContentTitle(x) }
fun NotifiBuilder.text(x: Str)  = apply { setContentText(x) }

fun RemoteViews.text(id: Int, value: Any) = setTextViewText(id, toStr(value))
fun RemoteViews.image(id: Int, resId: Int) = setImageViewResource(id, resId)
fun RemoteViews.visible(id: Int, visible: Bool) = setViewVisibility(id, if (visible) View.VISIBLE else View.GONE)
fun RemoteViews.click(id: Int, pendingIntent: PendingIntent) = setOnClickPendingIntent(id, pendingIntent)
fun RemoteViews.color(id: Int, color: Int) = setTextColor(id, color)





    

fun Context.Notifi(
    title: Str,
    text: Str,
    id: Int = 111,
) = LazyNotifi(title, text, id, this)

class LazyNotifi(
    title: Str,
    text: Str,
	val id: Int = 111,
	val ctx: Context,
) {
    val manager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

	
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
        return NotifiBuilder(ctx, notifi_Id)
			.setSmallIcon(myAppRes)
			.setAutoCancel(false)
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



fun Context.Notifi(
    xml: Int,
    id: Int = 111,
) = LazyNotifi_XML(xml, id, this)

//DIDNT TEST THISS
class LazyNotifi_XML(
    val xml: Int,
	val id: Int = 111,
	val ctx: Context
) {
    val manager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    var remoteView = RemoteViews(AppPkg, xml)
        set(value) {
            field = value
            make()
		}

    fun build(): Notification {
        return NotifiBuilder(ctx, notifi_Id)
			.setSmallIcon(myAppRes)
			.setAutoCancel(false)
			.setCustomContentView(remoteView)
			.setCustomBigContentView(remoteView)
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










