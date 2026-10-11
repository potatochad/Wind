//‼️‼️‼️RULES:
//DONT SAVE ANY NOTIFI VALUE IN A GLOBAL VAR
//Call create channels when building notifi


package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.ToX.*
import android.app.*
import android.content.*
import android.view.*
import android.widget.*
import androidx.core.app.*
import com.productivity.wind.Imports.Utils.*


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
        setShowBadge(no)
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




fun Context.Notifi(title: Str, text: Str, id: Int) = LazyNotifi(title, text, id, this)

class LazyNotifi(
    title: Str,
    text: Str,
	val id: Int,
	val ctx: Context,
	
	val channel_Id: Str = notifi_Id,
	val channel_Name: Str = notifi_Name,
	val importance: Int = NotificationManager.IMPORTANCE_HIGH,
    val Do: NotificationChannel.() -> Unit = {
        description = "Channel description"
        setShowBadge(no)
	}
) {
	init { 
		NotifiChannel(ctx, channel_Id, channel_Name, importance, Do)
	}
	
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
        return NotifiBuilder(ctx, channel_Id)
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
			NotifiManager(ctx).notify(id, build())
		}
	}

	fun startForeground(service: Service) {
		if (Permission.notification()) {
			service.startForeground(id, build())
		}
	}
}



fun Context.Notifi(xml: Int, id: Int) = LazyNotifi_XML(xml, id, this)

//DIDNT TEST THISS
class LazyNotifi_XML(
    val xml: Int,
	val id: Int,
	val ctx: Context,
	
	val channel_Id: Str = notifi_Id,
	val channel_Name: Str = notifi_Name,
	val importance: Int = NotificationManager.IMPORTANCE_HIGH,
    val Do: NotificationChannel.() -> Unit = {
        description = "Channel description"
        setShowBadge(no)
	}
) {
	init {
		NotifiChannel(ctx, channel_Id, channel_Name, importance, Do)
	}
	
	var remoteView = RemoteViews(AppPkg, xml)
        set(value) {
            field = value
            make()
		}

    fun build(): Notification {
        return NotifiBuilder(ctx, channel_Id)
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
			NotifiManager(ctx).notify(id, build())
		}
	}

	fun startForeground(service: Service) {
		if (Permission.notification()) {
			service.startForeground(id, build())
		}
	}
}










