package com.productivity.wind.Imports.Utils.ToX

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.String.*
import androidx.compose.ui.unit.*
import android.util.*
import androidx.compose.ui.graphics.Color
import kotlin.collections.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import java.time.*
import android.graphics.drawable.Drawable
import android.webkit.*
import com.productivity.wind.Imports.Utils.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlin.math.*
import android.graphics.*
import com.productivity.wind.Imports.Utils.Browser.*


@Suppress("UNCHECKED_CAST")

fun toDp(it: Any?): Dp = when (it) {
    is Dp -> it           // already Dp
    is Int -> it.dp       // Int → Dp
    is Float -> it.dp     // Float → Dp
    is Double -> it.dp    // Double → Dp
    null -> 0.dp             // null → 0.dp
    else -> 0.dp             // anything else → 0.dp
}
fun toDp(value: Any?, density: Density): Dp = with(density) {
    when (value) {
        is Dp -> value
        is Float -> value.toDp()
        is Int -> value.toFloat().toDp()
        is Double -> value.toFloat().toDp()
        null -> 0.dp
        else -> 0.dp
    }
}

fun toF(it: Any?): Float = when (it) {
    is Float -> it
    is Int -> it.toFloat()
    is Double -> it.toFloat()
    is Dp -> it.value      // Dp → Float
    null -> 0f
    else -> 0f
}
fun toF(value: Any?, density: Density): Float = when (value) {
    is Float -> value
    is Int -> value.toFloat()
    is Double -> value.toFloat()
    is Dp -> with(density) { value.toPx() }
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


fun toColor(it: Any?): Color = when (it) {
    is Int -> Color(it)
    is Long -> Color(it.toInt())
    is Str -> {
        // Try parse hex like "FF9C27B0" or "#FF9C27B0"
        val hex = it.removePrefix("#")
        try { Color(hex.toLong(16).toInt()) } catch (_: Exception) { Color.Black }
    }
    is Color -> it
    null -> Color.Black
    else -> Color.Black
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

fun toBitmap(it: Any?): Bitmap {
    var default = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    val context = App

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
fun toMStateStr(what: Any?): mStateStr = when {
    isMStateStr(what) -> what as mStateStr
    else -> mState("$what") as mStateStr
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


fun toHumanReadableAmountWritten(n: Int): Str {
    val l = 50
    val s = 20
    val p = 300

    var rem = n
    val b = rem / (p * s * l); rem %= p * s * l
    val pg = rem / (s * l); rem %= s * l
    val sent = rem / l; rem %= l
    val letters = rem

    val parts = listOf(
        b.takeIf { it > 0 }?.let { "$it books " },
        pg.takeIf { it > 0 }?.let { "$it pages " },
        sent.takeIf { it > 0 }?.let { "$it sentences " },
        letters.takeIf { it > 0 }?.let { "$it letters" }
    ).filterNotNull()

    return Str(*parts.take(2).toTypedArray())
}


fun toWeb(x: Any?): WebView? = when (x) {
    is WebView -> x
    is WebController -> x.webView
    else -> null
}
fun toUIStr(it: Any?): UIStr = when (it) {
    is UIStr -> it
    else -> UIStr(toStr(it))
}
