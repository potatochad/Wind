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
import androidx.compose.ui.graphics.*
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
import java.util.*
import com.productivity.wind.R




//region log

fun log(message: String, tag: String? = "Bad") {
    var LogMessage = message ; val stackTrace = Thread.currentThread().stackTrace ; val element = stackTrace[3] ; val fileName = element.fileName ; val lineNumber = element.lineNumber
    LogMessage= "[$fileName:$lineNumber] $message"
    if ("bad".equals(tag, true)) { Log.w(tag, LogMessage) }

    else { Log.d(tag, LogMessage) }
}



private var lastToast: Toast? = null

fun Vlog(msg: String, special: String = "none", delayLevel: Int = 0) {
    val delayMs = (delayLevel.coerceIn(0, 100)) * 30L // Example: Level 2 = 60ms

    if (special.equals("one", true)) {
        lastToast?.cancel()
    }

    Handler(Looper.getMainLooper()).postDelayed({
        val toast = Toast.makeText(Global1.context, msg, Toast.LENGTH_SHORT)
        lastToast = toast
        toast.show()
    }, delayMs)
}

fun getLogs(): String {
    return try {
        val process = Runtime.getRuntime().exec("logcat -d")
        val reader = process.inputStream.bufferedReader()
        val logs = reader.readText()

        // Optional: filter only your app logs
        logs.lines()
            .joinToString("\n")
    } catch (e: Exception) {
        "❌ Failed to read logs: ${e.message}"
    }
}








//endregion

//region NO LAG COMPOSE

/*EXPLANATION
*
* CUT LAG BY 80%, IN LISTS, ETC..
* BY MAKING SURE NOT EVERYTHING LOADS AT THE SAME TIME*/
@Composable
fun NoLagCompose(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        while (true) {
            delay(50) // space out work; LET UI LOAD
            yield()
        }
    }
    content()
}
@Composable
fun PreloadBox(
    whenDo: Boolean,
    what: @Composable () -> Unit
) {
    if (whenDo) {
        Box(
            Modifier
                .offset(x = (-10000).dp, y = (-10000).dp) // move it off-screen
                .clearAndSetSemantics { }
        ) { what() }
    }
}

//endregion NO LAG COMPOSE




fun refreshApps() {
    try {
        val context = Global1.context
        val realApps: List<ResolveInfo> = getApps()

        if (!UI.isUsageP_Enabled()) return

        realApps.forEach { info ->
            val pkgApp = getAppPackage(info)
            val ListsApp = getListsApp(pkgApp)

            if (ListsApp == null) {
                apps.new(
				    DataApps(
						name = getAppName(info),
						pkg = pkgApp,
						NowTime = getTodayAppUsage(pkgApp),
					)
                )
            }
        }

		apps.forEach { app ->
			apps.edit(app){
				NowTime = getTodayAppUsage(app.pkg)
			}
		}

    } catch (e: Exception) {
		Vlog("refreshApps: ${e.message?: "unknown error"}")
	}
}


fun getApps(): List<ResolveInfo> {
    val context = Global1.context
    val pm = context.packageManager
    val launchIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    return pm.queryIntentActivities(launchIntent, 0).abcOrder()
}
fun List<ResolveInfo>.abcOrder(): List<ResolveInfo> {
    val pm = Global1.context.packageManager
    return this.sortedWith(compareBy { it.loadLabel(pm).toString().lowercase() })
}

fun getListsApp(pkg: String): DataApps? {
    val map = apps.associateBy { it.pkg }
    return map[pkg]
}

fun getTodayAppUsage(packageName: String): Int {
    val context = Global1.context
    val end = System.currentTimeMillis()
    val cal = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val start = cal.timeInMillis

    val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Use INTERVAL_BEST and filter manually
    val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, start, end)
    val todayUsage = stats
        .filter { it.packageName == packageName && it.lastTimeUsed >= start }
        .sumOf { it.totalTimeInForeground }

    return (todayUsage / 1000L).toInt().coerceAtLeast(0)
}
fun getAppPackage(ri: ResolveInfo): String {
    return ri.activityInfo.packageName
}
fun getAppName(info: ResolveInfo): String {
    val context = Global1.context
    val pkg = info.activityInfo.packageName
    return info.loadLabel(context.packageManager)?.toString() ?: pkg
}

@Composable
fun getAppIcon(packageName: String): Drawable? {
    val context = Global1.context
    val pm = context.packageManager
    return remember(packageName) {
        try {
            pm.getApplicationIcon(packageName)
        } catch (_: Exception) {
            null
        }
    }
}











@Composable
fun eachSecond(onTick: () -> Unit) {
    LaunchedEffect(Unit) {
        while (true) {
            onTick()
            kotlinx.coroutines.delay(1000) // wait 1 second
        }
    }
}






object UI {
    //No synched with actual settingsItem function YET
    var SettingsItemCardColor = Color(0xFF121212)



	inline fun check(
		condition: Boolean,
		message: String = "",
		action: () -> Unit = {},
	) {
		if (condition) {
			if (message.isNotEmpty()) Vlog(message)
			action()        // safe
		}
	}


    @Composable
	fun End(content: @Composable () -> Unit) {
		Row(
			Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.End,
			verticalAlignment = Alignment.CenterVertically
		) {
			content()
			move(w = 10)
		}
	}


    @Composable
    fun move(s: Int = 0, w: Int = 0, h: Int = 0) {
        Spacer(
            modifier = when {
                s > 0 -> Modifier.size(s.dp)
                else -> Modifier
                    .width(w.dp)
                    .height(h.dp)
            },
        )
    }




	@Composable
	fun Checkbox(
		isChecked: MutableState<Boolean>,
	) {
		Checkbox(
			checked = isChecked.value,
			onCheckedChange = { isChecked.value = it },
			colors = CheckboxDefaults.colors(
				checkedColor = Color(0xFFFFD700), // gold
				uncheckedColor = Color.Gray,      // optional
				checkmarkColor = Color.White      // optional
			)
		)
	}

	@Composable
	fun CheckCircle(
		index: Int,                  // unique index of this circle
		selectedIndex: MutableState<Int>, // shared state of which is selected
	) {
		Box(
			modifier = Modifier.size(20.dp) // make box exactly the size you want
		) {
			RadioButton(
				selected = selectedIndex.value == index,
				onClick = { selectedIndex.value = index },
				colors = RadioButtonDefaults.colors(
					selectedColor = Color(0xFFFFD700),
					unselectedColor = Color.Gray
				),
				modifier = Modifier.fillMaxSize() // fills your exact box size
			)
		}
	}






    @Composable
    fun MenuHeader(
        title: String = "Wind",
        iconRes: Int = R.drawable.baseline_radar_24,
        iconSize: Dp = 60.dp,
        iconTint: Color = Color(0xFFFFD700),
        titleSize: TextUnit = 28.sp,
        topPadding: Dp = 8.dp,
        bottomPadding: Dp = 20.dp,
        StartPaddingRemove: Int = 40,
    ) {
        val safeStartPadding = max(0.dp, Bar.halfWidth / 2 - StartPaddingRemove.dp)

        Column(
            modifier = Modifier.padding(start = safeStartPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(topPadding))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "$title Icon",
                tint = iconTint,
                modifier = Modifier.size(iconSize),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = titleSize,
            )
            Spacer(Modifier.height(bottomPadding))
        }
    }

    fun SendEmail(
        recipient: String = "productivity.shield@gmail.com",
        subject: String = "Support Request – App Issue",
        includePhoneInfo: Boolean = true,
        prefillMessage: String = "I'm experiencing the following issue with the app:\n\n",
    ) {
        val context = Global1.context

        val body = buildString {
            appendLine()
            if (includePhoneInfo) {
                appendLine("Phone Info:")
                appendLine("• Manufacturer: ${Build.MANUFACTURER}")
                appendLine("• Model: ${Build.MODEL}")
                appendLine("• Android Version: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
                appendLine()
            }
            append(prefillMessage)
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        val chooser = Intent.createChooser(intent, "Send Email").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(chooser)
    }



	// IMPLEMENT LATEr...not synched with cinput
    data class InputStyle(
		/*
		val textColor: Color = Color(0xFFFFD700),
		val textSize: TextUnit = 14.sp,
		val height: Dp = 36.dp,
		val maxLetters: Int? = 5,
		val widthMin: Dp = 10.dp,
		val widthMax: Dp = 100.dp,
		val backgroundColor: Color = Color.Transparent,
		val singleLine: Boolean = true,
		val keyboardType: KeyboardType = KeyboardType.Text,
		val imeAction: ImeAction = ImeAction.Default,
		val cursorColor: Color = Color.Gray,
		*/
		val isNumber: Bool = true,
	)

    @Composable
    fun Cinput(
        what: MutableState<String>,
        textSize: TextUnit = 14.sp,
        height: Dp = 36.dp,
        MaxLetters: Int? = 5,
        WidthMin: Int = 10,
        WidthMax: Int = 100,
		style: InputStyle = InputStyle(),

        onChange: (String) -> Unit = {},
    ) {
        val value = what.value
        val TextColor = Color(0xFFFFD700)
        val FocusChange = TextMemory()
        val imeAction = ImeAction(null)
        val isFocused by IsFocused(FocusChange)

        val TextStyling = CTextStyle(TextColor, textSize)
		val isNumber = style.isNumber
        val measurer = rememberTextMeasurer()
        val density = LocalDensity.current
        val measuredWidth = measurer.measure(
            if (value.isEmpty()) " "
            else value,
            style = TextStyling,
        ).size.width
        val outerMod = Modifier.width(
            (with(density) { measuredWidth.toDp() } + 2.dp)
                .coerceIn(WidthMin.dp, WidthMax.dp),
        )
        OnLoseFocus(isFocused, null)




		
        BasicTextField(
			decorationBox = { innerTextField ->
                FieldBox(
                    height = height,
                    BackgroundColor = Color.Transparent,
                ) {
                    innerTextField()
                }
            },
			modifier = outerMod.height(height),
            textStyle = TextStyling,
            singleLine = true,
            keyboardOptions = KeyboardOptions(type(true), imeAction),
            keyboardActions = doneAction(null),
            cursorBrush = grayCursor(),
            interactionSource = FocusChange,







			
            value = value,
            onValueChange = {
                val input = FilterInput(isNumber, it)
                if (MaxLetters == null || input.length <= MaxLetters) {
					what.value = input
                    onChange(input)
                } else {
                    Vlog("max ${MaxLetters} letters")
                }
            },
        )
    }


    @Composable
    fun FieldBox(
        modifier: Modifier = Modifier,
        horizontal: Dp = 0.dp,
        height: Dp = 40.dp,
        BackgroundColor: Color = Color.Gray,
        where: Alignment = Alignment.CenterStart,
        content: @Composable () -> Unit,
    ) {
        Column {
            Box(
                modifier = modifier
                    .padding(horizontal = horizontal)
                    .height(height)
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(BackgroundColor),
                contentAlignment = where,
            ) {
                content()
            }
        }
    }


    @Composable
    fun TextRow(
        padding: Int = 0,
        hGap: Dp = 0.dp,          // space between items in a row
        vGap: Dp = 20.dp,          // space between rows
        content: @Composable () -> Unit,
    ) {
        Layout(
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding.dp),
        ) { measurables, constraints ->
            val h = hGap.roundToPx()
            val v = vGap.roundToPx()
            val maxW = constraints.maxWidth
            val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0)) }

            val rows = mutableListOf<MutableList<Placeable>>()
            val rowHeights = mutableListOf<Int>()
            var cur = mutableListOf<Placeable>()
            var curW = 0
            var curH = 0

            fun pushRow() {
                if (cur.isNotEmpty()) {
                    rows += cur
                    rowHeights += curH
                    cur = mutableListOf(); curW = 0; curH = 0
                }
            }

            for (p in placeables) {
                val nextW = if (cur.isEmpty()) p.width else curW + h + p.width
                if (nextW > maxW) pushRow()
                if (cur.isNotEmpty()) curW += h
                cur += p
                curW += p.width
                curH = maxOf(curH, p.height)
            }
            pushRow()

            val height = rowHeights.sum() + v * (rowHeights.size - 1).coerceAtLeast(0)

            layout(maxW, height) {
                var y = 0
                rows.forEachIndexed { i, row ->
                    var x = 0
                    val rowH = rowHeights[i]
                    row.forEach { p ->
                        val yCenter = y + (rowH - p.height) / 2
                        p.placeRelative(x, yCenter)
                        x += p.width + h
                    }
                    y += rowH + v
                }
            }
        }
    }


    @Composable
    fun CopyIcon(text: String) {
        val context = LocalContext.current
        var copied by remember { mutableStateOf(false) }

        LaunchedEffect(copied) {
            if (copied) {
                delay(1000) // Show checkmark for 1 second
                copied = false
            }
        }

        LazyIcon(
            icon = if (copied) Icons.Default.Check else Icons.Default.ContentCopy,
            onClick = {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", text)
                clipboard.setPrimaryClip(clip)
                copied = true
            },
        )
    }

// -------- Core ----------


//region CLICKALE TEXTTTTT■■■■■■■■■■■

    @Composable
    fun Ctext(
        text: String,
        onClick: () -> Unit,
    ) {
        Text(
            text = text,
            modifier = Modifier.clickable(onClick = onClick),
            style = TextStyle(
                color = Color(0xFFFFD700),           // gold
                fontWeight = FontWeight.Bold,        // bold
                textDecoration = TextDecoration.None, // no underline
            ),
        )
    }


//endregion CLICABLE TEXT ■■■■■■■■■


    //Region SETTING STUFF
    fun openPermissionSettings(action: String, uri: Uri? = null) {
        val intent = Intent(action).apply {
            uri?.let { data = it }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        Global1.context.startActivity(intent)
    }

    fun isNotificationEnabled(): Boolean {
        val ctx = Global1.context
        return NotificationManagerCompat
            .getEnabledListenerPackages(ctx)
            .contains(ctx.packageName)
    }

    fun isBatteryOptimizationDisabled(): Boolean {
        val ctx = Global1.context
        val pm = ctx.getSystemService(PowerManager::class.java)
        return pm.isIgnoringBatteryOptimizations(ctx.packageName)
    }

    fun isUsageP_Enabled(): Boolean {
        val ctx = Global1.context
        val appOps = ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        return appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            ctx.packageName,
        ) == AppOpsManager.MODE_ALLOWED
    }


//endregion SETTING STUFF


    @Composable
    fun EmptyBox(
        text: String = "No Items",
        icon: ImageVector = Icons.Default.Block,
        height: Dp = Bar.halfHeight * 2 - 200.dp,
        iconSize: Dp = 64.dp,
        topSpacing: Dp = Bar.halfHeight - 190.dp,
        textSize: TextUnit = 18.sp,
        color: Color = Color.Gray,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(height),
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(topSpacing))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(iconSize),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text, fontSize = textSize, color = color)
            }
        }
    }


    //INSIDE UI OBJECTTTTTT----------------------------------------//
}




//endregion




            
        
        
                    
object DayChecker {
    private var job: Job? = null

        
    fun start() {
        if (job?.isActive == true) return  // Already running
        if (Bar.lastDate == "") { Bar.lastDate = LocalDate.now().toString() }
            
        job = CoroutineScope(Dispatchers.Default).launch {
            while (coroutineContext.isActive) {
                delay(60 * 1000L)
                val today = LocalDate.now().toString()
                if (today != Bar.lastDate) {
                    Bar.lastDate = today
                    onNewDay()
                }
            }
        }
    }
}

    







	
fun goTo(route: String) = Global1.navController.navigate(route)



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) =NoLagCompose{
        NavHost(navController = navController, startDestination = "Main") {
            ScreenNav()
        }
}


// 3) Your DSL
fun NavGraphBuilder.url(
    route: String,
    content: @Composable () -> Unit
) {
    composable(route) { content() }
}








