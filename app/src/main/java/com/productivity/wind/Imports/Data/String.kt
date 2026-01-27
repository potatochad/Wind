package com.productivity.wind.Imports.Data
    
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
import com.productivity.wind.Imports.Data.*
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










val Str.size get() = length
val UIStr.size get() = this.text.size
fun Str.last(n: Int): Str = this.takeLast(n)
fun Str.fromTo(start: Int, end: Int = this.size) = this.substring(start, end)
fun UIStr.fromTo(start: Int, end: Int = this.size) = this.text.substring(start, end)

/* 

@Composable
fun getW(
    text: Str,
    style: TextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
): Float {
    val textMeasurer = rememberTextMeasurer()
    val layoutResult = textMeasurer.measure(text, style = style)
    return toF(layoutResult.size.width)
}
@Composable
fun getH(
    text: Str,
    style: TextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
): Float {
    val textMeasurer = rememberTextMeasurer()
    val layoutResult = textMeasurer.measure(text, style = style)
    return toF(layoutResult.size.height)
}

// 100ms once
@Composable
fun charsInLine(): Int {
    var lineChars by r(0)
    
    var width = getW()
    lineChars = toInt(width/getW("k"))
    Vlog("width: $width, charW: ${getW("k")}, chars: $lineChars") 

    return lineChars
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




// 7s once
@Composable
fun Any.toLines(): List<UIStr> {
    var lineChars by r(0)
    var str by r(toStr(this))

    // Measure how many chars fit in one line
    lineChars = charsInLine()

    // Return empty if not measured yet
    if (lineChars == 0) return emptyList()

    
    val lines = mList<UIStr>()

    RunOnce {
        NoLag {
            var line = ""
        
            str.safeSplit(" "){ word ->
                if (line.isEmpty() || line.size + 1 + word.size <= lineChars) {
                    line += word
                } else {
                    lines.add(UIStr(line))
                    line = word
                }
            }
            if (line.isNotEmpty()) lines.add(UIStr(line))
        }
    }
    
    return lines
}


@Composable
fun Any.toLinesUltraFast(): List<UIStr> {
    val str = toStr(this)
    var lineChars by r(0)
    lineChars = charsW(str)
    if (lineChars == 0) return emptyList()

    val lines = mList<String>()
    val sb = StringBuilder()
    var charCount = 0

    RunOnce {
        NoLag {
            var wordStart = 0
            val length = str.length

            for (i in 0..length) {
                val c = if (i < length) str[i] else ' ' // handle last word
                if (c == ' ' || i == length) {
                    val wordLength = i - wordStart
                    if (charCount + if (charCount > 0) 1 else 0 + wordLength <= lineChars) {
                        if (charCount > 0) {
                            sb.append(' ')
                            charCount++
                        }
                        sb.append(str, wordStart, i)
                        charCount += wordLength
                    } else {
                        lines.add(sb.toString())
                        sb.clear()
                        sb.append(str, wordStart, i)
                        charCount = wordLength
                    }
                    wordStart = i + 1
                }
            }
            if (sb.isNotEmpty()) lines.add(sb.toString())
        }
    }

    return lines.map { UIStr(it) }
}
*/



@Composable
fun charsW(text: Any): Int {
    var lineChars by r(0)
    val str = toStr(text)

    if (lineChars == 0) {
        Text(
            text = "k".repeat(600),
            maxLines = 1,
            softWrap = yes,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.alpha(0f),
            onTextLayout = {
                lineChars = it.getLineEnd(0, visibleEnd = yes)
            }
        )
    }

    return lineChars
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




/*
@Composable
fun Any.toLines(): List<UIStr> {
    var lineChars by r(0)
    var str by r(toStr(this))

    // Measure how many chars fit in one line
    lineChars = charsW(str)

    // Return empty if not measured yet
    if (lineChars == 0) return emptyList()

    
    val lines = mList<UIStr>()

    RunOnce {
        var line = ""
        
        str.safeSplit(" "){ word ->
            if (line.isEmpty() || line.size + 1 + word.size <= lineChars) {
                line += word
            } else {
                lines.add(UIStr(line))
                line = word
            }
        }
        if (line.isNotEmpty()) lines.add(UIStr(line))
    }
    
    return lines
}

*/

@Composable
fun Any.toLines(): List<UIStr> {
    val str = toStr(this)
    
    // Measure how many chars fit in one line
    val lineChars = charsW(str)
    if (lineChars == 0) return emptyList()

    // Remember lines so it only recalculates when str or lineChars changes
    val lines = remember(str, lineChars) {
        val result = mutableListOf<UIStr>()
        var line = ""
        str.safeSplit(" ") { word ->
            if (line.isEmpty() || line.length + 1 + word.length <= lineChars) {
                if (line.isNotEmpty()) line += " "
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



