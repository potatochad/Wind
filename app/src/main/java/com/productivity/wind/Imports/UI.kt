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
import com.productivity.wind.Imports.UI_visible.*
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
import androidx.compose.ui.unit.*


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
fun getW(): Float {
    return toF(BoxWithConstraints {
        toInt(maxWidth)
    })
}

@Composable
fun getH(): Float {
    return toF(BoxWithConstraints {
        toInt(maxHeight)
    })
}


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

fun copyToClipboard(txt: Str) {
	val clipboard = App.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
	val clip = ClipData.newPlainText("label", txt)
	clipboard.setPrimaryClip(clip)
}

fun ProgressColor(progress: Float): Color {
	return when {
		progress < 0.33f -> Color.Red
		progress < 0.66f -> Color.Yellow
		else -> Color.Green
	}
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Main") {
            ScreenNav()
        }
}











