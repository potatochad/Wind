package com.productivity.wind.Imports.Utils.String
    
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
import com.productivity.wind.Imports.Utils.*

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.text.intl.LocaleList



typealias UIStr = AnnotatedString
typealias UIStrBuilder = AnnotatedString.Builder
typealias StrStyle = SpanStyle


val UIStr.size get() = this.text.size
fun UIStr.fromTo(start: Int, end: Int = this.size) = this.text.fromTo(start, end)



fun makeUIStr(Do: UIStrBuilder.() -> Unit): UIStr = buildAnnotatedString(Do)
fun UIStrBuilder.add(text: Char) = append(text)
fun UIStrBuilder.add(text: Str) = append(text)
fun UIStrBuilder.add(text: UIStr) = append(text)

fun UIStr(vararg parts: Any): UIStr = makeUIStr {
    parts.forEach { add(toUIStr(it)) }
}


fun UIStr.keepOneStyle(newText: Str): UIStr {
    return UIText(newText, this.style)
}

fun UIText(text: Any, style: StrStyle = StrStyle()): UIStr {
    return makeUIStr {
        pushStyle(style)
        add(toUIStr(text))
        pop()
    }
}


val UIStr.style: StrStyle
    get() = spanStyles.firstOrNull()?.item ?: StrStyle()
val Str.style: StrStyle
    get() = toUIStr(this).spanStyles.firstOrNull()?.item ?: StrStyle()

    
val UIStr.textStyle: TextStyle
    get() {
        val span = this.style

        return TextStyle(
            color = span.color,
            fontSize = span.fontSize,
            fontWeight = span.fontWeight,
            fontStyle = span.fontStyle,
            fontFamily = span.fontFamily,
            letterSpacing = span.letterSpacing,
            background = span.background,
            textDecoration = span.textDecoration
        )
    }

fun UIStr.strStyle(x: StrStyle): UIStr = makeUIStr {
    pushStyle(x)
    add(this@strStyle.text)
    pop()
}
fun Str.strStyle(x: StrStyle): UIStr = makeUIStr {
    pushStyle(x)
    add(this@strStyle)
    pop()
}



fun StrStyle.fontSize(x: TextUnit) = copy(fontSize = x)

fun StrStyle.color(x: Color) = copy(color = x)
fun StrStyle.green() = color(green)
fun StrStyle.red() = color(red)
fun StrStyle.gold() = color(gold)
fun StrStyle.gray() = color(gray)
fun StrStyle.darkGray() = color(darkGray)
fun StrStyle.black() = color(black)
fun StrStyle.white() = color(white)

fun StrStyle.thin() = copy(fontWeight = FontWeight.Thin)
fun StrStyle.light() = copy(fontWeight = FontWeight.Light)
fun StrStyle.medium() = copy(fontWeight = FontWeight.Medium)
fun StrStyle.semiBold() = copy(fontWeight = FontWeight.SemiBold)
fun StrStyle.extraBold() = copy(fontWeight = FontWeight.ExtraBold)
fun StrStyle.normal() = copy(fontWeight = FontWeight.Normal)
fun StrStyle.bold() = copy(fontWeight = FontWeight.Bold)

fun StrStyle.underline() = copy(textDecoration = TextDecoration.Underline)
fun StrStyle.strike() = copy(textDecoration = TextDecoration.LineThrough)
fun StrStyle.none() = copy(textDecoration = TextDecoration.None)

fun StrStyle.background(x: Color) = copy(background = x)
fun StrStyle.brush(x: Brush?) = copy(brush = x)
fun StrStyle.shadow(x: Shadow?) = copy(shadow = x)
fun StrStyle.draw(x: DrawStyle?) = copy(drawStyle = x)

fun StrStyle.italic() = copy(fontStyle = FontStyle.Italic)
fun StrStyle.fontFamily(x: FontFamily?) = copy(fontFamily = x)
fun StrStyle.letterSpacing(x: TextUnit) = copy(letterSpacing = x)
fun StrStyle.baselineShift(x: BaselineShift?) = copy(baselineShift = x)
fun StrStyle.fontFeatureSettings(x: String?) = copy(fontFeatureSettings = x)
fun StrStyle.synthesis(x: FontSynthesis?) = copy(fontSynthesis = x)

fun StrStyle.geometric(x: TextGeometricTransform?) = copy(textGeometricTransform = x)

fun StrStyle.locale(x: LocaleList?) = copy(localeList = x)
fun StrStyle.platform(x: PlatformSpanStyle?) = copy(platformStyle = x)




fun Str.sty(fun1: StrStyle.() -> StrStyle = { this }): UIStr = UIText(this, (toUIStr(this).style.fun1()))
fun UIStr.sty(fun1: StrStyle.() -> StrStyle = { this }): UIStr = UIText(this, (toUIStr(this).style.fun1()))



fun Str.size(x: Int) = sty { copy(fontSize = x.sp) }
fun Str.size(x: Float) = sty { copy(fontSize = x.sp) }
fun Str.size(x: TextUnit) = sty { copy(fontSize = x) }
fun Str.bold() = sty { copy(fontWeight = FontWeight.Bold) }
fun Str.color(x: Color) = sty { copy(color = x) }
fun Str.gold() = sty { copy(color = gold) }
fun Str.green() = sty { copy(color = green) }
fun Str.red() = sty { copy(color = red) }
fun Str.white() = sty { copy(color = white) }
fun Str.black() = sty { copy(color = black) }
fun Str.darkGray() = sty { copy(color = darkGray) }
fun Str.gray() = sty { copy(color = gray) }

fun UIStr.size(x: Int) = sty { copy(fontSize = x.sp) }
fun UIStr.size(x: Float) = sty { copy(fontSize = x.sp) }
fun UIStr.size(x: TextUnit) = sty { copy(fontSize = x) }
fun UIStr.bold() = sty { copy(fontWeight = FontWeight.Bold) }
fun UIStr.color(x: Color) = sty { copy(color = x) }
fun UIStr.gold() = sty { copy(color = gold) }
fun UIStr.green() = sty { copy(color = green) }
fun UIStr.red() = sty { copy(color = red) }
fun UIStr.white() = sty { copy(color = white) }
fun UIStr.black() = sty { copy(color = black) }
fun UIStr.darkGray() = sty { copy(color = darkGray) }
fun UIStr.gray() = sty { copy(color = gray) }





