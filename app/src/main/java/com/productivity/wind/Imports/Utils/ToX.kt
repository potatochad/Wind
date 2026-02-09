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
import java.time.*
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
import androidx.compose.ui.window.*
import com.google.maps.android.compose.*
import kotlin.math.*     
import com.productivity.wind.Imports.UI_visible.*
import java.time.format.*

@Suppress("UNCHECKED_CAST")


fun toDp(it: Any?): Dp = when (it) {
    is Dp -> it           // already Dp
    is Int -> it.dp       // Int → Dp
    is Float -> it.dp     // Float → Dp
    is Double -> it.dp    // Double → Dp
    null -> 0.dp             // null → 0.dp
    else -> 0.dp             // anything else → 0.dp
}
fun toF(it: Any?): Float = when (it) {
    is Float -> it
    is Int -> it.toFloat()
    is Double -> it.toFloat()
    is Dp -> it.value      // Dp → Float
    null -> 0f
    else -> 0f
}
fun toInt(it: Any?): Int = when (it) {
    is Int -> it
    is Float -> it.toInt()
    is Double -> it.toInt()
    is String -> it.toIntOrNull() ?: 0
    is Dp -> it.value.toInt()  // Dp → Int
    null -> 0
    else -> 0
}

fun toL(it: Any?): Long = when (it) {
    is Long -> it
    is Int -> it.toLong()
    is Float -> it.toLong()
    is Double -> it.toLong()
    is Str -> it.toLongOrNull() ?: 0L
    null -> 0L
    else -> 0L
}

fun toD(it: Any?): Double = when (it) {
    is Double -> it
    is Float -> it.toDouble()
    is Long -> it.toDouble()
    is Int -> it.toDouble()
    is Str -> it.toDoubleOrNull() ?: 0.0
    null -> 0.0
    else -> 0.0
}


fun toLatLng(it: Any?): LatLng = when (it) {
    is LatLng -> it
    is Pair<*, *> -> LatLng(it.first.toString().toDouble(), it.second.toString().toDouble())
    is Str -> {
        val parts = it.split(",")
        LatLng(parts.getOrNull(0)?.toDoubleOrNull() ?: 0.0,
               parts.getOrNull(1)?.toDoubleOrNull() ?: 0.0)
    }
    null -> LatLng(0.0, 0.0)
    else -> LatLng(0.0, 0.0)
}

fun toStr(it: Any?): String = when (it) {
    is Str -> it
    is AnnotatedString -> it.text
    is LatLng -> "${it.latitude},${it.longitude}"
    is Pair<*, *> -> "${it.first},${it.second}"
    is Double, is Float, is Int, is Long -> it.toString()
    null -> ""
    else -> it.toString()
}

fun toLocalDate(x: Any?): LocalDate = when (x) { 
    is Str -> LocalDate.parse(x)
    is Long -> Instant.ofEpochMilli(x)
        .atOffset(ZoneOffset.UTC)
        .toLocalDate()
    null -> LocalDate.now()
    else -> LocalDate.now()
}

fun LocalDate.toMillis(): Long {
    return this.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
}


fun toWeekDay(dateInput: Any): Str {
    val date = when (dateInput) {
        is LocalDate -> dateInput
        is String -> LocalDate.parse(dateInput)
        else -> throw IllegalArgumentException("Must be LocalDate or String")
    }

    return when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> "MO"
        DayOfWeek.TUESDAY -> "TU"
        DayOfWeek.WEDNESDAY -> "WE"
        DayOfWeek.THURSDAY -> "TH"
        DayOfWeek.FRIDAY -> "FR"
        DayOfWeek.SATURDAY -> "SA"
        DayOfWeek.SUNDAY -> "SU"
    }
}





fun toH(x: Int): Int = x / 3600
fun toMin(x: Int): Int = (x % 3600) / 60
fun toS(x: Int): Int = x % 60



@Composable
fun toUI(it: Any?): UI {
    return when (it) {
        is Str -> { { Text(it) } }
        is Function0<*> -> it as UI   // unsafe cast but works at runtime
        else -> { { Text("Unsupported type (toUI) $it") } }
    }
}

fun toBitmap(it: Any?, context: Context): Bitmap {
    var default = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    return try {
        when (it) {
            is Int -> BitmapFactory.decodeResource(context.resources, it)
            is Bitmap -> it
            is Drawable -> {
                val bitmap = Bitmap.createBitmap(
                    it.intrinsicWidth,
                    it.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                it.setBounds(0, 0, canvas.width, canvas.height)
                it.draw(canvas)
                bitmap
            }
            else -> default
        }
    } catch (e: Exception) {
        e.printStackTrace()
        log("Failed to do to Bitmap")
        default
    }
}




@Composable
fun toMStr(what: Any?): mStr = when {
    isMStr(what) -> what as mStr
    else -> m("$what") as mStr
}





fun Any.toMeters(
    cameraState: CameraPositionState,
    density: Float = AppDensity
): Double {
    val zoom = cameraState.position.zoom
    val latitude = cameraState.position.target.latitude
    val metersPerPixel = 156543.03392 * cos(Math.toRadians(latitude)) / (1 shl zoom.toInt())
    val pixels = toF(this) * density
    return pixels * metersPerPixel
}




