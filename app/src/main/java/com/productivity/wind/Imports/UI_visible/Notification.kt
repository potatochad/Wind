import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

fun simpleNotification(
    context: Context,
    title: String,
    text: String,
    iconRes: Int
) {
    val notification = NotificationCompat.Builder(context, "default")
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(iconRes)
        .setAutoCancel(true) // disappears when swiped
        .build()

    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.notify(1, notification)
}
