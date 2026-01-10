package com.productivity.wind.Imports

import com.productivity.wind.Imports.Data.*
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
import java.time.*
import kotlin.concurrent.schedule
import java.io.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.*
import android.text.*
import android.text.style.*
import androidx.compose.ui.viewinterop.*
import android.widget.*
import android.text.method.*


//region log

private var lastToast: Toast? = null

fun Vlog(msg: Str, special: Str = "none", delayLevel: Int = 0) {
    val delayMs = (delayLevel.coerceIn(0, 100)) * 30L // Example: Level 2 = 60ms

    if (special.equals("one", true)) {
        lastToast?.cancel()
    }

	log(msg)


    Handler(Looper.getMainLooper()).postDelayed({
        val toast = Toast.makeText(App, msg, Toast.LENGTH_SHORT)
        lastToast = toast
        toast.show()
    }, delayMs)
}

@Composable
fun getStatusBarHeight(): Int {
    val insets = WindowInsets.statusBars.asPaddingValues()
    val density = LocalDensity.current
    return with(density) { insets.calculateTopPadding().toPx().toInt() }
}








//endregion

/*
object DayChecker {
    private var job: Job? = null

    fun start() {
        if (job?.isActive == yes) return  // Already running
        if (Bar.lastDate == "") { Bar.lastDate = LocalDate.now().toString() }

        job = CoroutineScope(Dispatchers.Default).launch {
            while (coroutineContext.isActive) {
                delay(60 * 1000L)
                val today = "${LocalDate.now()}"
                if (today != Bar.lastDate) {
                    Bar.lastDate = today
                    onNewDay()
                }
            }
        }
    }
}
*/


fun fixedInputScroll(
    text: TextFieldValue,
    cursorPos: Int,
    done: m_<Bool>,
    scroll: ScrollState
) {
    if (text.text.isNotEmpty() && !done.it) {
		done.it = yes

        val ratio = toF(cursorPos) / toF(text.text.size)
        val max = toF(scroll.maxValue)
        val scrollTo = (max * ratio)

        scroll.goTo(scrollTo)
    }

	if (cursorPos == text.text.size) {
		scroll.toBottom()
	}
}


@Composable
fun FastTextView(
    text: AnnotatedString, // use AnnotatedString like in Compose
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                setTextIsSelectable(no)
                isVerticalScrollBarEnabled = no
                movementMethod = ScrollingMovementMethod()
            }
        },
        update = { textView ->
            val spannable = SpannableString(text.text)
            
            // Apply Compose SpanStyles to Android Spannable
            text.spanStyles.forEach { styleRange ->
                val color = styleRange.item.color?.toArgb()
                if (color != null) {
                    spannable.setSpan(
                        ForegroundColorSpan(color),
                        styleRange.start,
                        styleRange.end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                // You can add more spans: TypefaceSpan, StyleSpan, UnderlineSpan, etc.
            }
            
            textView.text = spannable
        },
        modifier = modifier
    )
}




// ✴️ LIST SPECIFIC UI

fun UIStrBuilder.correctStr(text: Str, correctUntil: Int) {
    for (i in text.indices) {
        if (i < correctUntil) {
			text.bold().color(Color.Green)
        } else {
            add(text[i])
        }
    }
}

fun getApps(): List<ResolveInfo> {
    val pm = App.packageManager
    val launchIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    return pm.queryIntentActivities(launchIntent, 0).abcOrder()
}
fun List<ResolveInfo>.abcOrder(): List<ResolveInfo> {
    val pm = App.packageManager
    return this.sortedWith(compareBy { it.loadLabel(pm).toString().lowercase() })
}


fun CopyTsk.goodStr(): Int {
    val n = minOf(txt.size, input.size)
    for (i in 0 until n) {
        if (txt[i] != input[i]) return i
    }
    return n
}

fun CopyTsk.done(): Bool {
    return doneTimes >= maxDone
}
fun CopyTsk.edit(block: CopyTsk.() -> Unit) {
    Bar.copyTsk.edit(this, block)
}

// ✴️ LIST SPECIFIC UI



LazyIcon(
    icon: ImageVector,
    size: Any = 40,        
    mod: Mod = Mod,
    color: Color = Color.White,
	onClick: Do = {},
) {Box(
                        modifier = Mod
                            .space(end = 10)
                            .s(30)
                            .clip(CircleShape)
                            .background(BigIconColor),
                        contentAlignment = Alignment.Center
                    ) {




object UI {
    //No synched with actual settingsItem function YET
    var SettingsItemCardColor = Color(0xFF121212)


	inline fun check(
        condition: Bool,
        msg: Str = "",
        Do: Do = {},
	) {
		if (condition) {
			if (msg.isNotEmpty()) Vlog(msg)
			Do()        // safe
		}
	}
	
	@Composable
	fun End(mod: Mod = Mod, ui: ui) {
		Row(
			mod.maxW(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.End
		){
			ui()
			move(10)
		}
	}


	fun copyToClipboard(txt: Str) {
		val clipboard = App.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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
					modifier = Mod
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
			Canvas(modifier = Mod.matchParentSize()) {
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
					sweepAngle = -360f * progress,
					useCenter = false,
					topLeft = topLeft,
					size = Size(radius * 2, radius * 2),
					style = stroke,
				)
			}
			Box(Mod.space(strokeWidth+ContentPadding)) {
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
	fun CheckRow(
		txt: Str="",
		isChecked: m_<Bool>,
		EndUI: ui_<Bool> = { _ -> }
	) {
		LazzyRow{
			LazzyRow(Mod.click { isChecked.it = !isChecked.it }, 0) {
				Checkbox(
					checked = isChecked.it,
					onCheckedChange = { isChecked.it = it },
					colors = CheckboxDefaults.colors(
						checkedColor = Gold,
						uncheckedColor = Color.Gray,
						checkmarkColor = Color.White
					)
				)
				move(5)
				Text(txt)
				EndUI(isChecked.it)
			}
		}
	}


	@Composable
	fun CheckCircle(
        index: Int,                  // unique index of this circle
        selectedIndex: m_<Int>, // shared state of which is selected
	) {
		Box(
			Mod.s(15) // make box exactly the size you want
		) {
			RadioButton(
				selected = selectedIndex.value == index,
				onClick = { set(selectedIndex, index) },
				colors = RadioButtonDefaults.colors(
					selectedColor = LightBlue,
					unselectedColor = Color.Gray
				),
				modifier = Mod.scale(0.85f)
			)
		}
		move(w=8)
	}
	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	fun ComposeCanBeTiny(ui: ui) {
		CompositionLocalProvider(
			LocalMinimumInteractiveComponentEnforcement provides false
		) {
			ui()
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
        val safeStartPadding = max(0.dp, (AppW+60.dp) / 4 - StartPaddingRemove.dp)

        Column(
            Modifier.space(start = safeStartPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Mod.h(topPadding))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "$title Icon",
                tint = iconTint,
                modifier = Mod.s(iconSize),
            )
            move(4)
            Text(
                text = title,
                fontSize = titleSize,
            )
            Spacer(Mod.h(bottomPadding))
        }
    }

    fun SendEmail(
        recipient: Str = "productivity.shield@gmail.com",
        subject: Str = "Support Request – App Issue",
        includePhoneInfo: Bool = yes,
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

        startActivity(chooser)
    }


	@Composable
	fun Ctext(
        text: Any,
        Do: Do={},
	) {
		val displayText = when (text) {
			is Str -> UIStr(text)
			is UIStr -> text
			else -> UIStr("$text")
		}
		Text(
			text = displayText.gold(),
			modifier = Mod.click(no) {
				Do()
			},
			maxLines = 1, 
		)
	}


    @Composable
    fun EmptyBox(
        text: Str = "No Items",
        icon: ImageVector = Icons.Default.Block,
        iconSize: Dp = 70.dp,
        textSize: TextUnit = 18.sp,
        color: Color = Color.Gray,
    ) {
			Column(
				Mod.maxS(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Icon(
					imageVector = icon,
					contentDescription = null,
					tint = color,
					modifier = Mod.s(iconSize),
				)
				move(h=8)
				Text(text, fontSize = textSize, color = color)
			}
    }


    //INSIDE UI OBJECTTTTTT----------------------------------------//
}




//endregion







@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Main") {
            ScreenNav()
        }
}











