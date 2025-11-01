package com.productivity.wind.Imports

import android.app.usage.UsageStatsManager
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import kotlin.collections.*
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.painterResource
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.rememberTextMeasurer
import android.graphics.drawable.Drawable
import android.content.pm.*
import java.util.*
import com.productivity.wind.R
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*
import com.productivity.wind.Imports.Data.*
import java.time.*
import kotlin.concurrent.schedule
import java.io.*
import androidx.compose.ui.text.input.*

//region log

fun Plog(msg: Str = "test", s: Int = 1) {
    val delayMillis = s * 1000

    CoroutineScope(Dispatchers.Main).launch {
        delay(delayMillis.toLong())
        DebugPopupInfo = msg
        show(DebugPopup)
    }
}

private var lastToast: Toast? = null

fun Vlog(msg: Str, special: Str = "none", delayLevel: Int = 0) {
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

fun getMyAppLogs(): Str {
    val process = Runtime.getRuntime().exec("logcat -d *:V")
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val logs = mutableListOf<Str>()
    val myPackage = App.ctx.packageName

    reader.forEachLine { line ->
        if (line.contains(myPackage)) {
            // Keep only the message after the last colon
            val clean = line.substringAfter(":").trim()
                .substringAfter(":").trim()
                .substringAfter(":").trim()

            // Truncate if longer than 70 chars
            val finalLine = if (clean.length > 70) clean.take(70) + "..." else clean
            
			logs.add(finalLine)
        }
    }

    return logs.joinToString("\n")
}




@Composable
fun getStatusBarHeight(): Int {
    val insets = WindowInsets.statusBars.asPaddingValues()
    val density = LocalDensity.current
    return with(density) { insets.calculateTopPadding().toPx().toInt() }
}








//endregion

//region NO LAG COMPOSE
fun each(s: Long = 1000L, Do: Do) {
    Timer().schedule(0, s) {
        Do()
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
                Bar.apps.add {
					name = getAppName(info)
					pkg = pkgApp
					NowTime = getTodayAppUsage(pkgApp)
				}
            }
        }

		Bar.apps.forEach { app ->
			Bar.apps.edit(app){
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

fun getListsApp(pkg: Str): AppTsk? {
    val map = Bar.apps.associateBy { it.pkg }
    return map[pkg]
}










@Composable
fun eachSecond(onTick: Do) {
    RunOnce(Unit) {
        while (true) {
            onTick()
            delay(1000) // wait 1 second
        }
    }
}



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





var CardColor = Color(0xFF1A1A1A)
val DarkBlue = Color(0xFF00008B) 
val Gold = Color(0xFFFFD700)
val LightBlue = Color(0xFFADD8E6)

object UI {
    //No synched with actual settingsItem function YET
    var SettingsItemCardColor = Color(0xFF121212)


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
		Box(
			Modifier.maxW(),
			contentAlignment = Alignment.CenterEnd
		) {
			content()
			move(w = 10)
		}
	}


	fun copyToClipboard(ctx: Context, txt: Str) {
		val clipboard = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
		val clip = ClipData.newPlainText("label", txt)
		clipboard.setPrimaryClip(clip)
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
						.space(4)
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
			Box(Modifier.space(strokeWidth+ContentPadding)) {
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
		val sDp = toDp(s)
		val wDp = toDp(w)
		val hDp = toDp(h)


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
        isChecked: m_<Bool>,
	) {
		Checkbox(
			checked = isChecked.value,
			onCheckedChange = { set(isChecked, it) },
			colors = CheckboxDefaults.colors(
				checkedColor = LightBlue, // gold
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
				onClick = { set(selectedIndex, index) },
				colors = RadioButtonDefaults.colors(
					selectedColor = LightBlue,
					unselectedColor = Color.Gray
				),
				modifier = Modifier.scale(0.85f)
			)
		}
		move(w=8)
	}
	@OptIn(ExperimentalMaterial3Api::class)
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
        val safeStartPadding = max(0.dp, (App.screenWidth+60.dp) / 4 - StartPaddingRemove.dp)

        Column(
            Modifier.space(start = safeStartPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.h(topPadding))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "$title Icon",
                tint = iconTint,
                modifier = Modifier.size(iconSize),
            )
            Spacer(Modifier.h(4))
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
	fun dynamicTextWidth(
        text: Str,
        textStyle: TextStyle,
        widthMin: Int,
        widthMax: Int,
        h: Dp
	): Modifier {
		val measurer = rememberTextMeasurer()
		val density = LocalDensity.current

		// Measure text width
		val measuredWidth = measurer.measure(
			text.ifEmpty { " " }, // fallback if empty
			style = textStyle
		).size.w

		// Create modifier with dynamic width and fixed height
		return Modifier
        .w((with(density) { measuredWidth.toDp() } + 2.dp).coerceIn(widthMin.dp, widthMax.dp))
        .h(h)
	}


	@Composable
    fun Input(
        what: Str,
        textSize: TextUnit = 14.sp,
        height: Dp = 36.dp,
        MaxLetters: Int? = 5,
        WidthMin: Int = 10,
        WidthMax: Int = 120,
        isInt: Bool = no,

        onChange: Do_<Str>,
    ) {
        val TextColor = Color.White
        val FocusChange = TextMemory()
        val imeAction = ImeAction(null)
        val isFocused by IsFocused(FocusChange)

        val TextStyling = CTextStyle(TextColor, textSize)
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


    @Composable
    fun EmptyBox(
        text: Str = "No Items",
        icon: ImageVector = Icons.Default.Block,
        height: Dp = App.screenHeight - 150.dp,
        iconSize: Dp = 64.dp,
        topSpacing: Dp = App.screenHeight/2 - 160.dp,
        textSize: TextUnit = 18.sp,
        color: Color = Color.Gray,
    ) {
        Box(
            modifier = Modifier
                .maxS()
                .h(height),
        ) {

            Column(
                Modifier.maxS(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.h(topSpacing))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(iconSize),
                )
                UI.move(h = 8)
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
fun MyNavGraph(navController: NavHostController) {
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








