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
import android.webkit.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.graphics.painter.*
import com.google.accompanist.drawablepainter.*
import androidx.compose.ui.layout.*

import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.*
import androidx.compose.runtime.*
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
        val toast = Toast.makeText(App.ctx, msg, Toast.LENGTH_SHORT)
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


@Composable
fun getStatusBarHeight(): Int {
    val insets = WindowInsets.statusBars.asPaddingValues()
    val density = LocalDensity.current
    return with(density) { insets.calculateTopPadding().toPx().toInt() }
}

@Composable
fun bottomSystemHeight(): Dp {
    val insets = WindowInsets.navigationBars // includes bottom system bar
    val density = LocalDensity.current
    return with(density) { insets.getBottom(this).toDp() }
}








//endregion

//region NO LAG COMPOSE

/*EXPLANATION
*
* CUT LAG BY 80%, IN LISTS, ETC..
* BY MAKING SURE NOT EVERYTHING LOADS AT THE SAME TIME*/
@Composable
fun NoLagCompose(content: Content) {
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
    whenDo: Bool,
    what: Content
) {
    if (whenDo) {
        Box(
            Modifier
                .offset(x = (-10000).dp, y = (-10000).dp) // move it off-screen
                .clearAndSetSemantics { }
        ) { what() }
    }
}
@Composable
fun each(time: Long = 10_000L, action: Do) {
    LaunchedEffect(Unit) {
        while (true) {
            action()
            delay(time)
        }
    }
}
@Composable
fun UrlConverter(input: Str): Str {
    return remember(input) {
        if (input.startsWith("http://") || input.startsWith("https://")) {
            input
        } else {
            "https://$input"
        }
    }
}


//endregion NO LAG COMPOSE




fun refreshApps() {
    try {
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
    val pm = App.ctx.packageManager
    val launchIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    return pm.queryIntentActivities(launchIntent, 0).abcOrder()
}
fun List<ResolveInfo>.abcOrder(): List<ResolveInfo> {
    val pm = App.ctx.packageManager
    return this.sortedWith(compareBy { it.loadLabel(pm).toString().lowercase() })
}

fun getListsApp(pkg: Str): DataApps? {
    val map = apps.associateBy { it.pkg }
    return map[pkg]
}

fun getTodayAppUsage(packageName: Str): Int {
    val end = System.currentTimeMillis()
    val cal = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val start = cal.timeInMillis

    val usm = App.ctx.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Use INTERVAL_BEST and filter manually
    val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, start, end)
    val todayUsage = stats
        .filter { it.packageName == packageName && it.lastTimeUsed >= start }
        .sumOf { it.totalTimeInForeground }

    return (todayUsage / 1000L).toInt().coerceAtLeast(0)
}
fun getAppPackage(ri: ResolveInfo): Str {
    return ri.activityInfo.packageName
}
fun getAppName(info: ResolveInfo): Str {
    val pkg = info.activityInfo.packageName
    return info.loadLabel(App.ctx.packageManager)?.toString() ?: pkg
}


@Composable
fun getAppIcon(packageName: Str): Drawable? {
    val pm = App.ctx.packageManager
    return remember(packageName) {
        try {
            pm.getApplicationIcon(packageName)
        } catch (_: Exception) {
            null
        }
    }
}












@Composable
fun eachSecond(onTick: Do) {
    LaunchedEffect(Unit) {
        while (true) {
            onTick()
            delay(1000) // wait 1 second
        }
    }
}





val DarkBlue = Color(0xFF00008B) 
val Gold = Color(0xFFFFD700)

object UI {
    //No synched with actual settingsItem function YET
    var SettingsItemCardColor = Color(0xFF121212)
	var screenWidth = Bar.halfWidth*2
	var screenHeight = Bar.halfHeight*2



	inline fun check(
		condition: Bool,
		message: Str = "",
		action: Do = {},
	) {
		if (condition) {
			if (message.isNotEmpty()) Vlog(message)
			action()        // safe
		}
	}


    @Composable
	fun End(content: Content) {
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
	fun ProgressIcon(
		icon: Drawable?,              // whatever LazyImage accepts (Drawable, URL, etc.)
		progress: Float,
	) {
		val ringColor = UI.ProgressColor(progress)

		UI.Ring(
			color = ringColor,
			progress = progress,
			ContentPadding = -3,
		) {
			Box(contentAlignment = Alignment.Center) {
				LazyImage(icon)

				// Overlay circle on top
				Canvas(
					modifier = Modifier
						.matchParentSize()
						.padding(4.dp)
				) {
					drawArc(
						color = Color(0xFF171717),   // ~10% darker overlay
						startAngle = 0f,
						sweepAngle = 360f,
						useCenter = false,
						style = Stroke(width = 2.dp.toPx())
					)
				}
			}
		}
	}

	@Composable
	fun Ring(
		color: Color,
		strokeWidth: Int = 3,
		progress: Float = 1f,
		ContentPadding: Int = 1,
		strokeCap: StrokeCap = StrokeCap.Butt,
		content: @Composable BoxScope.() -> Unit
	) {
		Box(
			contentAlignment = Alignment.Center,
		) {
			Canvas(modifier = Modifier.matchParentSize()) {
				val stroke = Stroke(width = strokeWidth.dp.toPx(), cap = strokeCap)
				val radius = size.minDimension / 2
				val topLeft = Offset(center.x - radius, center.y - radius)

				drawCircle(             
					color = Color.Black.copy(alpha = 0.1f), // faint dark circle
					radius = radius,                         // radius of the circle
				)
				drawArc(
					color = color,
					startAngle = -90f,
					sweepAngle = -360f * progress.coerceIn(0f, 1f),
					useCenter = false,
					topLeft = topLeft,
					size = Size(radius * 2, radius * 2),
					style = stroke,
				)
			}
			Box(modifier = Modifier.padding((strokeWidth+ContentPadding).dp)) {
				content()
			}
		}
	}
	fun ProgressColor(progress: Float): Color {
		return when {
			progress < 0.33f -> Color.Red
			progress < 0.66f -> Color.Yellow
			else -> Color.Green
		}
	}


    @Composable
	fun move(s: Any = 0, w: Any = 0, h: Any = 0) {
		// Convert s, w, h to Dp
		fun Any.toDp(): Dp = when (this) {
			is Int -> this.dp
			is Dp -> this
			else -> 0.dp
		}

		val sDp = s.toDp()
		val wDp = w.toDp()
		val hDp = h.toDp()


		Spacer(
			modifier = if (sDp > 0.dp) {
				Modifier.size(sDp)  // uniform size
			} else {
				Modifier.width(wDp).height(hDp)
			}
		)
	}





	@Composable
	fun Checkbox(
		isChecked: m_<Boolean>,
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
		selectedIndex: m_<Int>, // shared state of which is selected
	) {
		Box(
			modifier = Modifier.size(15.dp) // make box exactly the size you want
		) {
			RadioButton(
				selected = selectedIndex.value == index,
				onClick = { selectedIndex.value = index },
				colors = RadioButtonDefaults.colors(
					selectedColor = Color(0xFFFFD700),
					unselectedColor = Color.Gray
				),
				modifier = Modifier.scale(0.85f)
			)
		}
		move(w=8)
	}
	@Composable
	fun ComposeCanBeTiny(content: Content) {
		CompositionLocalProvider(
			LocalMinimumInteractiveComponentEnforcement provides false
		) {
			content()
		}
	}






    @Composable
    fun MenuHeader(
        title: Str = "Wind",
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
        recipient: Str = "productivity.shield@gmail.com",
        subject: Str = "Support Request – App Issue",
        includePhoneInfo: Bool = true,
        prefillMessage: Str = "I'm experiencing the following issue with the app:\n\n",
    ) {
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

        App.ctx.startActivity(chooser)
    }


	@Composable
    fun Input(
        what: Str,
        textSize: TextUnit = 14.sp,
        height: Dp = 36.dp,
        MaxLetters: Int? = 5,
        WidthMin: Int = 10,
        WidthMax: Int = 120,
		style: InputStyle = InputStyle(),

        onChange: Do_<Str>,
    ) {
        val TextColor = Color(0xFFFFD700)
        val FocusChange = TextMemory()
        val imeAction = ImeAction(null)
        val isFocused by IsFocused(FocusChange)

        val TextStyling = CTextStyle(TextColor, textSize)
		val isNumber = style.isNumber
        val outerMod = dynamicTextWidth(
			what, TextStyling, WidthMin, WidthMax, height
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







			
            value = what,
            onValueChange = onChange,
        )
	}



    @Composable
    fun Cinput(
        what: m_<String>,
        textSize: TextUnit = 14.sp,
        height: Dp = 36.dp,
        MaxLetters: Int? = 5,
        WidthMin: Int = 10,
        WidthMax: Int = 100,
		isInt: Bool = false,
		
        onChange: Do_<Str> = {},
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
        modifier: Mod = Modifier,
        horizontal: Dp = 0.dp,
        height: Dp = 40.dp,
        BackgroundColor: Color = Color.Gray,
        where: Alignment = Alignment.CenterStart,
        content: Content,
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
    fun CopyIcon(text: Str) {
        val context = LocalContext.current
        var copied by r { m(false) }

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
		text: Str,
		onClick: Do,
	) {
		val interactionSource = r { MutableInteractionSource() }

		Text(
			text = text,
			modifier = Modifier.clickable(
				interactionSource = interactionSource,
				indication = LocalIndication.current, 
				onClick = onClick
			),
			style = TextStyle(
				color = Color(0xFFFFD700),           // gold
				fontWeight = FontWeight.Bold,        // bold
				textDecoration = TextDecoration.None // no underline
			)
		)
	}



//endregion CLICABLE TEXT ■■■■■■■■■


    //Region SETTING STUFF
    fun openPermissionSettings(action: Str, uri: Uri? = null) {
        val intent = Intent(action).apply {
            uri?.let { data = it }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        App.ctx.startActivity(intent)
    }

    fun isNotificationEnabled(): Bool {
        return NotificationManagerCompat
            .getEnabledListenerPackages(App.ctx)
            .contains(App.ctx.packageName)
    }

    fun isBatteryOptimizationDisabled(): Bool {
        val pm = App.ctx.getSystemService(PowerManager::class.java)
        return pm.isIgnoringBatteryOptimizations(App.ctx.packageName)
    }

    fun isUsageP_Enabled(): Bool {
        val appOps = App.ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        return appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            App.ctx.packageName,
        ) == AppOpsManager.MODE_ALLOWED
    }
	
	
//endregion SETTING STUFF


    @Composable
    fun EmptyBox(
        text: Str = "No Items",
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




            
        
        
                    



	
fun goTo(route: Str) = App.navHost.navigate(route)



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) =NoLagCompose{
        NavHost(navController = navController, startDestination = "Main") {
            ScreenNav()
        }
}


// 3) Your DSL
fun NavGraphBuilder.url(
    route: Str,
    content: Content
) {
    composable(route) { content() }
}








