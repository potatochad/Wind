package com.productivity.wind.Imports.Utils
    
import android.annotation.SuppressLint
import timber.log.Timber
import java.text.*
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
import android.content.*
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import android.content.Intent
import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.layout.*
import android.graphics.drawable.Drawable
import android.content.pm.*
import com.productivity.wind.Imports.*
import java.util.*
import com.productivity.wind.R
import kotlin.reflect.full.*
import androidx.compose.ui.focus.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import java.io.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.text.style.*
import androidx.compose.foundation.lazy.*
import java.util.*
import kotlin.concurrent.*
import androidx.annotation.RequiresApi
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import androidx.compose.foundation.text.selection.*
import kotlin.system.*
import androidx.navigation.*
import android.webkit.*
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.cli.common.ExitCode
import com.productivity.wind.Imports.Utils.*
import android.location.*
import androidx.core.content.*
import androidx.compose.ui.text.*
import androidx.navigation.compose.*
import android.util.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.location.*
import android.content.*
import android.net.*
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import com.productivity.wind.Imports.UI_visible.*



typealias UIStr = AnnotatedString
typealias UIStrBuilder = AnnotatedString.Builder
typealias StrStyle = SpanStyle


fun makeUIStr(Do: UIStrBuilder.() -> Unit): UIStr {
    return buildAnnotatedString(Do)
}
fun UIStrBuilder.add(text: Char) = append(text)
fun UIStrBuilder.add(text: Str) = append(text)
fun UIStrBuilder.add(text: UIStr) = append(text)

fun UIStr(vararg parts: Any): UIStr = makeUIStr {
    parts.forEach { add(toUIStr(it)) }
}

fun toUIStr(it: Any?): UIStr = when (it) {
    is UIStr -> it
    is String -> makeUIStr { add(it) }
    is Char -> makeUIStr { add(it.toString()) }
    else -> makeUIStr { add(it?.toString() ?: "") }
}


fun Str.remove(x: Str): Str {
    return this.replace(x, "")
}



fun UIText(text: Any, style: StrStyle = StrStyle()): UIStr {
    return makeUIStr {
        pushStyle(style)
        add(toUIStr(text))
        pop()
    }
}
fun Any.getStyle(): StrStyle {
    return toUIStr(this).spanStyles.firstOrNull()?.item ?: StrStyle()
}

fun Any.txt(update: StrStyle.() -> StrStyle = { this }): UIStr = UIText(this, (this.getStyle().update()))

fun Any.size(x: Int) = txt { copy(fontSize = x.sp) }
fun Any.size(x: Float) = txt { copy(fontSize = x.sp) }
fun Any.size(x: TextUnit) = txt { copy(fontSize = x) }

fun Any.bold() = txt { copy(fontWeight = FontWeight.Bold) }

fun Any.color(x: Color) = txt { copy(color = x) }
fun Any.gold() = txt { copy(color = Gold) }
fun Any.green() = txt { copy(color = Color.Green) }
fun Any.red() = txt { copy(color = Color.Red) }
fun Any.white() = txt { copy(color = Color.White) }
fun Any.black() = txt { copy(color = Color.Black) }
fun Any.darkGray() = txt { copy(color = Color.DarkGray) }
fun Any.gray() = txt { copy(color = Color.Gray) }










val Str.size get() = length
val UIStr.size get() = this.text.size
fun Str.last(n: Int): Str = this.takeLast(n)
fun Str.fromTo(start: Int, end: Int = this.size) = this.substring(start, end)
fun UIStr.fromTo(start: Int, end: Int = this.size) = this.text.substring(start, end)


@Composable
fun charsW(text: Any, textStyle: TextStyle = LocalTextStyle.current, maxWidthPx: Float = 1000f): Int {
    val str = toStr(text)
    
    val textMeasurer = rememberTextMeasurer()

    val charWidth = textMeasurer.measure(
        text = AnnotatedString("k"),
        style = textStyle
    ).size.width

    return (maxWidthPx / charWidth).toInt()
}

fun String.safeSplit(delim: String, action: (String) -> Unit) {
    var i = 0
    while (i < this.length) {
        val next = this.indexOf(delim, i)
        if (next == -1) {
            action(this.substring(i))
            break
        } else {
            action(this.substring(i, next + delim.length)) // include the delimiter
            i = next + delim.length
        }
    }
}




@Composable
fun Any.toLines(maxWidthPx: Float): List<UIStr> {
    val lineChars = charsW(this, maxWidthPx = maxWidthPx)
    var str by r(toStr(this))

    val lines = remember(str, lineChars) {
        val result = mList<UIStr>()
        var line = ""
        
        str.safeSplit(" "){ word ->
            if (line.isEmpty() || line.size + 1 + word.size <= lineChars) {
                line += word
            } else {
                result.add(UIStr(line))
                line = word
            }
        }
        if (line.isNotEmpty()) result.add(UIStr(line))
        result
    }
    
    return lines
}







val String.notEmpty: Bool
    get() = this.isNotEmpty()

val String.empty: Bool
    get() = this.isEmpty()



fun ListStr.takeStr(x: Int): Str {
    var result = ""
    var remaining = x

    for (s in this) {
        if (remaining <= 0) break

        if (result.notEmpty) {
            result += " "
            remaining -= 1
            if (remaining <= 0) break
        }

        // take min of string length or remaining chars
        val take = minOf(s.length, remaining)
        result += s.take(take)
        remaining -= take
    }

    return result
}

fun ListStr.getLine(lineIndex: Int): String {
    return if (lineIndex in indices) this[lineIndex] else ""
}

fun ListStr.getLineByChar(charIndex: Int): String {
    var runningIndex = 0
    for (line in this) {
        val nextIndex = runningIndex + line.length
        if (charIndex in runningIndex until nextIndex) {
            return line
        }
        runningIndex = nextIndex + 1 // +1 for line break
    }
    return ""
}



