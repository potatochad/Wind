package com.productivity.wind.Imports.UI_visible
 
import android.annotation.*
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
import android.app.*
import android.content.*
import android.provider.*



import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.*
import com.google.accompanist.permissions.*
import android.app.NotificationManager.*
import androidx.core.app.NotificationCompat.*
import androidx.core.app.*
import androidx.core.graphics.drawable.*
import java.util.logging.*



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














































@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Composable
fun LiveUpdateSample() {
    val notificationManager =
        LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    SnackbarNotificationManager.initialize(LocalContext.current.applicationContext, notificationManager)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            NotificationPermission()
            Spacer(modifier = Modifier.height(4.dp))
            NotificationPostPromotedPermission()
            Text(stringResource( R.string.live_update_summary_text))
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {
                    onCheckout()
                    scope.launch {
                        snackbarHostState.showSnackbar("Order placed")
                    }
                },
            ) {
                Text("Checkout")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.BAKLAVA)
fun onCheckout() {
    SnackbarNotificationManager.start()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermission() {
    @SuppressLint("InlinedApi") // Granted at install time on API <33.
    val notificationPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS,
    )
    if (!notificationPermissionState.status.isGranted) {
        NotificationPermissionCard(
            shouldShowRationale = notificationPermissionState.status.shouldShowRationale,
            onGrantClick = {
                notificationPermissionState.launchPermissionRequest()
            },
            modifier = Modifier
                .fillMaxWidth(),
            permissionStringResourceId = R.string.permission_message,
            permissionRationalStringResourceId = R.string.permission_rationale,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@Composable
fun NotificationPostPromotedPermission() {
    val context = LocalContext.current
    var isPostPromotionsEnabled by remember { mutableStateOf(SnackbarNotificationManager.isPostPromotionsEnabled()) }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        isPostPromotionsEnabled = SnackbarNotificationManager.isPostPromotionsEnabled()
    }
    if (!isPostPromotionsEnabled) {
        Text(
            text = stringResource(R.string.post_promoted_permission_message),
            modifier = Modifier.padding(horizontal = 10.dp),
        )
        Button(
            onClick = {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_PROMOTION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                context.startActivity(intent)
            },
        ) {
            Text(text = stringResource(R.string.to_settings))
        }
    }
}

@Composable
private fun NotificationPermissionCard(
    shouldShowRationale: Boolean,
    onGrantClick: () -> Unit,
    modifier: Modifier = Modifier,
    permissionStringResourceId: Int,
    permissionRationalStringResourceId: Int,
) {
    Card(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(permissionStringResourceId),
            modifier = Modifier.padding(16.dp),
        )
        if (shouldShowRationale) {
            Text(
                text = stringResource(permissionRationalStringResourceId),
                modifier = Modifier.padding(horizontal = 10.dp),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
           Button(onClick = onGrantClick) {
                Text(text = stringResource(R.string.permission_grant))
            }
        }
    }
}


object SnackbarNotificationManager {
    private lateinit var notificationManager: NotificationManager
    private lateinit var appContext: Context
    const val CHANNEL_ID = "live_updates_channel_id"
    private const val CHANNEL_NAME = "live_updates_channel_name"
    private const val NOTIFICATION_ID = 1234


    @RequiresApi(Build.VERSION_CODES.O)
    fun initialize(context: Context, notifManager: NotificationManager) {
        notificationManager = notifManager
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE_DEFAULT)
        appContext = context
        notificationManager.createNotificationChannel(channel)
    }

    private enum class OrderState(val delay: Long) {
        INITIALIZING(5000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(appContext, INITIALIZING)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("You order is being placed")
                    .setContentText("Confirming with bakery...")
                    .setShortCriticalText("Placing")
                    .setStyle(buildBaseProgressStyle(INITIALIZING).setProgressIndeterminate(true))
            }
        },
        FOOD_PREPARATION(9000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(appContext, FOOD_PREPARATION)
                    .setContentTitle("Your order is being prepared")
                    .setContentText("Next step will be delivery")
                    .setShortCriticalText("Prepping")
                    .setLargeIcon(
                        IconCompat.createWithResource(
                            appContext, R.drawable.cupcake
                        ).toIcon(appContext)
                    )
                    .setStyle(buildBaseProgressStyle(FOOD_PREPARATION).setProgress(25))
            }
        },
        FOOD_ENROUTE(13000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(appContext, FOOD_ENROUTE)
                    .setContentTitle("Your order is on its way")
                    .setContentText("Enroute to destination")
                    .setStyle(
                        buildBaseProgressStyle(FOOD_ENROUTE)
                            .setProgressTrackerIcon(
                                IconCompat.createWithResource(
                                    appContext, R.drawable.shopping_bag
                                )
                            )
                            .setProgress(50)
                    )
                    .setLargeIcon(
                        IconCompat.createWithResource(
                            appContext, R.drawable.cupcake
                        ).toIcon(appContext)
                    )
                    .setWhen(System.currentTimeMillis().plus(11 * 60 * 1000 /* 10 min */))
                    .setUsesChronometer(true)
                    .setChronometerCountDown(true)
            }
        },
        FOOD_ARRIVING(18000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(appContext, FOOD_ARRIVING)
                    .setContentTitle("Your order is arriving and has been dropped off")
                    .setContentText("Enjoy & don't forget to refrigerate any perishable items.")
                    .setStyle(
                        buildBaseProgressStyle(FOOD_ARRIVING)
                            .setProgressTrackerIcon(
                                IconCompat.createWithResource(
                                    appContext, R.drawable.delivery_truck
                                )
                            )
                            .setProgress(75)
                    )
                    .setLargeIcon(
                        IconCompat.createWithResource(
                            appContext, R.drawable.cupcake
                        ).toIcon(appContext)
                    )
                    .setWhen(System.currentTimeMillis().plus(11 * 60 * 500 /* 5 min */))
                    .setUsesChronometer(true)
                    .setChronometerCountDown(true)
            }
        },
        ORDER_COMPLETE(21000) {
            @RequiresApi(Build.VERSION_CODES.BAKLAVA)
            override fun buildNotification(): NotificationCompat.Builder {
                return buildBaseNotification(appContext, ORDER_COMPLETE)
                    .setContentTitle("Your order is complete.")
                    .setContentText("Thank you for using JetSnack for your snacking needs.")
                    .setStyle(
                        buildBaseProgressStyle(ORDER_COMPLETE)
                            .setProgressTrackerIcon(
                                IconCompat.createWithResource(
                                    appContext, R.drawable.check_circle
                                )
                            )
                            .setProgress(100)
                    )
                    .setShortCriticalText("Arrived")
                    .setLargeIcon(
                        IconCompat.createWithResource(
                            appContext, R.drawable.cupcake
                        ).toIcon(appContext)
                    )
            }
        };


        @RequiresApi(Build.VERSION_CODES.BAKLAVA)
        fun buildBaseProgressStyle(orderState: OrderState): ProgressStyle {
            val pointColor = Color.valueOf(
                236f / 255f, // Normalize red value to be between 0.0 and 1.0
                183f / 255f, // Normalize green value to be between 0.0 and 1.0
                255f / 255f, // Normalize blue value to be between 0.0 and 1.0
                1f,
            ).toArgb()
            val segmentColor = Color.valueOf(
                134f / 255f, // Normalize red value to be between 0.0 and 1.0
                247f / 255f, // Normalize green value to be between 0.0 and 1.0
                250f / 255f, // Normalize blue value to be between 0.0 and 1.0
                1f,
            ).toArgb()
            var progressStyle = NotificationCompat.ProgressStyle()
                .setProgressPoints(
                    listOf(
                        ProgressStyle.Point(25).setColor(pointColor),
                        ProgressStyle.Point(50).setColor(pointColor),
                        ProgressStyle.Point(75).setColor(pointColor),
                        ProgressStyle.Point(100).setColor(pointColor)
                    )
                ).setProgressSegments(
                    listOf(
                        ProgressStyle.Segment(25).setColor(segmentColor),
                        ProgressStyle.Segment(25).setColor(segmentColor),
                        ProgressStyle.Segment(25).setColor(segmentColor),
                        ProgressStyle.Segment(25).setColor(segmentColor)

                    )
                )
            when (orderState) {
                INITIALIZING -> {}
                FOOD_PREPARATION -> {}
                FOOD_ENROUTE -> progressStyle.setProgressPoints(
                    listOf(
                        ProgressStyle.Point(25).setColor(pointColor)
                    )
                )

                FOOD_ARRIVING -> progressStyle.setProgressPoints(
                    listOf(
                        ProgressStyle.Point(25).setColor(pointColor),
                        ProgressStyle.Point(50).setColor(pointColor)
                    )
                )

                ORDER_COMPLETE -> progressStyle.setProgressPoints(
                    listOf(
                        ProgressStyle.Point(25).setColor(pointColor),
                        ProgressStyle.Point(50).setColor(pointColor),
                        ProgressStyle.Point(75).setColor(pointColor)
                    )
                )
            }
            return progressStyle
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun buildBaseNotification(appContext: Context, orderState: OrderState): NotificationCompat.Builder {
            val notificationBuilder = NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true)
                .setRequestPromotedOngoing(true)

            when (orderState) {
                INITIALIZING -> {}
                FOOD_PREPARATION -> {}
                FOOD_ENROUTE -> {}
                FOOD_ARRIVING ->
                    notificationBuilder
                        .addAction(
                            NotificationCompat.Action.Builder(null, "Got it", null).build()
                        )
                        .addAction(
                            NotificationCompat.Action.Builder(null, "Tip", null).build()
                        )
                ORDER_COMPLETE ->
                    notificationBuilder
                        .addAction(
                            NotificationCompat.Action.Builder(
                                null, "Rate delivery", null).build()
                        )
            }
            return notificationBuilder
        }

        abstract fun buildNotification(): NotificationCompat.Builder
    }

    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    fun start() {
        for (state in OrderState.entries) {
            val notification = state.buildNotification().build()

            Logger.getLogger("canPostPromotedNotifications")
                .log(
                    Level.INFO,
                    notificationManager.canPostPromotedNotifications().toString())
            Logger.getLogger("hasPromotableCharacteristics")
                .log(
                    Level.INFO,
                    notification.hasPromotableCharacteristics().toString())

            Handler(Looper.getMainLooper()).postDelayed({
                notificationManager.notify(NOTIFICATION_ID, notification)
            }, state.delay)
        }
    }

    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    fun isPostPromotionsEnabled(): Boolean {
        return notificationManager.canPostPromotedNotifications()
    }
}



