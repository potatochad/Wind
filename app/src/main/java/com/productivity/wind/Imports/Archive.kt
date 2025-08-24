package com.productivity.wind.Imports

import timber.log.Timber
import android.app.usage.UsageStatsManager
import androidx.compose.foundation.interaction.*
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.isAccessible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import android.content.ClipData
import android.content.ClipboardManager
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import android.content.Intent
import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import android.graphics.drawable.Drawable
import android.content.pm.*
import com.productivity.wind.Imports.UI.FieldBox
import com.productivity.wind.Imports.UI.SettingsItemCardColor
import java.util.*
import com.productivity.wind.R


//region TEXT VALS ■■■■■■■■■■■■■

@Composable
fun TextStyle(
    color: Color,
    fontSize: TextUnit,
) = LocalTextStyle.current.copy(
    color = color,
    fontSize = fontSize,
)

@Composable
fun CTextStyle2(
    color: Color,
    fontSize: TextUnit,
) = LocalTextStyle.current.copy(
    color = color,
    fontSize = fontSize,
)

@Composable
fun CTextStyle(
    color: Color,
    fontSize: TextUnit,
) = LocalTextStyle.current.copy(
    color = color,
    fontSize = fontSize,
    fontWeight = FontWeight.Bold,
)


@Composable
fun TextMemory() = remember { MutableInteractionSource() }
fun type(isNumber: Boolean) =
    if (isNumber) KeyboardType.Number else KeyboardType.Text

fun ImeAction(onDone: (() -> Unit)?) =
    if (onDone != null) ImeAction.Done else ImeAction.Default

@Composable
fun IsFocused(source: InteractionSource) =
    source.collectIsFocusedAsState()

@Composable
fun OnLoseFocus(isFocused: Boolean, onFocusLose: (() -> Unit)?) {
    LaunchedEffect(isFocused) {
        if (!isFocused) {
            onFocusLose?.invoke()
        }
    }
}

fun FilterInput(isNumber: Boolean, input: String): String {
    return if (isNumber) input.toIntOrNull()?.toString() ?: "0" else input
}

fun max(maxLetters: Int?) =
    maxLetters ?: Int.MAX_VALUE

fun doneAction(onDone: (() -> Unit)?) =
    KeyboardActions(onDone = { onDone?.invoke() })

fun FocusAsk(focusRequester: FocusRequester?) =
    focusRequester?.let { Modifier.focusRequester(it) } ?: Modifier

fun KeyboardOptions(
    keyboardType: KeyboardType,
    imeAction: ImeAction,
) = KeyboardOptions.Default.copy(
    keyboardType = keyboardType,
    imeAction = imeAction,
)

fun grayCursor() = SolidColor(Color.Gray)


//endregion Text VALS■■■■■■■■■■■


@Composable
fun InputField(
    value: String,
    onChange: (String) -> Unit,
    placeholderText: String = "Input Text",
    isNumber: Boolean = false,
    onDone: (() -> Unit)? = null,  //When press done button
    Divider: Boolean = true,
    textSize: TextUnit = 14.sp,
    height: Dp = 36.dp,
    dividerY: Int = 0,
    InputWidth: Dp = 80.dp,
    MaxLetters: Int? = 20_000,
    OnMaxLetters: (() -> Unit) = { Vlog("MAX LETTERS") }, //each letter type doo
    TextColor: Color = Color.White,
    BackgroundColor: Color = SettingsItemCardColor,

    OnFocusLose: (() -> Unit)? = null,
    AutoWidth: Boolean = false,
    AutoWidthMin: Int = 60,
    AutoWidthMax: Int = 200,

    ) {
    val FocusChange = TextMemory()
    val imeAction = ImeAction(onDone)
    val isFocused by IsFocused(FocusChange)


    //val AutoWidthMaxVAL = AutoWidthMin + (charCount * charWidthDp) + (paddingHorizontalDp * 2)

    val outerMod = if (AutoWidth) {
        Modifier.widthIn(min = AutoWidthMin.dp, max = AutoWidthMax.dp)
    } else {
        Modifier.width(InputWidth)
    }

    OnLoseFocus(isFocused, OnFocusLose)


    BasicTextField(
        value = value,
        onValueChange = {
            val input = FilterInput(isNumber, it)
            if (input.length <= max(MaxLetters)) {
                onChange(input)

            } else {
                OnMaxLetters()
            }

        },

        modifier = outerMod
            .height(height),
        textStyle = TextStyle(TextColor, textSize),
        singleLine = true,
        keyboardOptions = KeyboardOptions(type(isNumber), imeAction),
        keyboardActions = doneAction(onDone),
        cursorBrush = grayCursor(),
        interactionSource = FocusChange,

        decorationBox = { innerTextField ->
            FieldBox(
                height = height,
                BackgroundColor = BackgroundColor,
            ) {
                if (value.isEmpty()) {
                    Text(
                        placeholderText,
                        color = TextColor,
                        fontSize = textSize,
                    )
                }


                innerTextField()


            }

            LazyLine(
                show = Divider,
                MoveY = dividerY,
                width = InputWidth,
            )


        },
    )
}


//region LATER USE


/*
class WatchdogService : Service() {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private var OneJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind: $intent")
        return null
    }

    fun GOtowindAPP(){
        val intent = Intent(Global1.context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Global1.context.startActivity(intent)
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        NotificationHelper(this).createNotificationChannel()
        startForeground(1, NotificationHelper(this).buildNotification(),)
        Global1.context = this
        if (Bar.BlockingEnabled) {
            if (OneJob == null || OneJob?.isActive == false) {
                OneJob = serviceScope.launch {
                    while (true) {

                        //region SAFETY PURPOSES

                        delay(1000L)
                        Bar.COUNT +=1

                        //endregion


                        //region CURRENT APP

                        val usageStatsManager = Global1.context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                        val NowTime = System.currentTimeMillis()

                        /*
                        * THIS IS NOT SUPER ACCURATE
                        ? If you want better precision, you’ll need an Accessibility Service.
                        !THIS REQUIRES NAVIGATING USER TO IT*/
                        val AppsUsed = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, NowTime - 20_000, NowTime)
                        val currentApp = AppsUsed?.maxByOrNull { it.lastTimeUsed }?.packageName

                        //LOG WHEN WANT TOOO
                        if (currentApp == Global1.context.packageName || currentApp == null) { } else { log("BACKGROUND — CURRENT APP: $currentApp", "bad") }

                        /*

                        val blocked = apps.any { it.packageName == currentApp && it.Block }

                        if (blocked) {
                            if (Bar.funTime > 0) {
                                Bar.funTime -= 1
                                log("BACKGROUND---Spending Time??:::${Bar.funTime};", "bad")
                            } else {
                                GOtowindAPP()
                                log("BACKGROUND---Blocking APP:::${currentApp}; ${Bar.COUNT}", "bad")
                            }
                        }


                    if (currentApp == "com.seekrtech.waterapp") {

					}*/   }}
            }
        }

        return START_STICKY
    }

    override fun onDestroy() { super.onDestroy(); serviceScope.cancel() }
}

*/


class NotificationHelper(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "WatchdogServiceChannel"
    }
    fun createNotificationChannel() {
        Timber.d("createNotificationChannel() called")
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_channel_name_watchdog_service),
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(channel)
    }

    fun buildNotification(): Notification {
        Timber.d("buildNotification() called")

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntentFlags =
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        val pendingIntent =
            PendingIntent.getActivity(context, 0, notificationIntent, pendingIntentFlags)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notification_title_app_watchdog))
            .setContentText(context.getString(R.string.notification_content_monitoring_apps))
            .setSmallIcon(R.drawable.baseline_radar_24)
            .setContentIntent(pendingIntent)
            // Low priority for ongoing background service notification
            .setPriority(NotificationCompat.PRIORITY_LOW)
            // Makes the notification persistent
            .setOngoing(true)
            .build()
    }
}

//endregion LATER USE
