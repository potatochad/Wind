package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.String.*
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
import androidx.navigation.compose.*
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
import androidx.compose.ui.text.*
import com.productivity.wind.Imports.UI_visible.*
import java.time.*
import java.time.format.*
import android.view.*
import androidx.core.view.*
import android.widget.RemoteViews
import androidx.core.net.toUri

//colors
var cardColor = Color(0xFF1A1A1A)
var inputColor = Color(0xFF272727)
val darkBlue = Color(0xFF00008B) 
val gold = Color(0xFFFFD700)
val white = Color.White
val gray = Color.Gray
val lightBlue = Color(0xFFADD8E6)
val darkGreen = Color(0xFF0A1F0D)
val orange = Color(0xF7FFA500)
val transparent = Color.Transparent
val black = Color.Black
val red = Color.Red
val green = Color.Green
val blue = Color.Blue
val cyan = Color.Cyan
val magenta = Color.Magenta
val yellow = Color.Yellow
val darkGray = Color.DarkGray
val lightGray = Color.LightGray

//tiny renames
val gson = Gson()
val yes = true
val no = false
val maxInt = Int.MAX_VALUE
var <T> m_<T>.it: T
    get() = this.value
    set(value) { this.value = value }

//tiny more complex renames
fun <T> set(state: m_<T>?, value: T) { state?.value = value }
fun show(state: m_<Bool>?) = set(state, yes)
fun hide(state: m_<Bool>?) = set(state, no)


// <<<---Type aliases--->>>
typealias Web = WebView
typealias ctx = Context

typealias Str = String
typealias Bool = Boolean

typealias Do = () -> Unit
typealias DoStr = (Str) -> Unit     
typealias DoInt = (Int) -> Unit 
typealias DoBool = (Bool) -> Unit 
typealias Do_<T> = (T) -> Unit
typealias Do2_<A, B> = (A, B) -> Unit
typealias Do3_<A, B, C> = (A, B, C) -> Unit

typealias ListStr = List<Str>
typealias ListInt = List<Int>
typealias ListBool = List<Bool>
typealias ListDouble = List<Double>

typealias mList<T> = MutableList<T>
	

typealias Wait = suspend () -> Unit
typealias Wait_<T> = suspend (T) -> Unit

typealias m_<T> = MutableState<T>
typealias mBool= m_<Bool>
typealias mInt= m_<Int>
typealias mStr= m_<Str>
	
	
typealias MutableStateList<T> = SnapshotStateList<T>


typealias ClassVar_<T, R> = KMutableProperty1<T, R>
typealias ClassVar = KMutableProperty1<*, *>
typealias ClassValVar_<T, R> = KProperty1<T, R>
typealias ClassValVar = KProperty1<*, *>
typealias ValVar_<R> = KProperty<R>
typealias ValVar = KProperty<*>
typealias Var_<R> = KMutableProperty<R>
typealias Var = KMutableProperty<*>

typealias AppInfo = ResolveInfo

typealias Content = @Composable () -> Unit
typealias Content_<T> = @Composable (T) -> Unit
typealias UI = @Composable () -> Unit
typealias UI_<T> = @Composable (T) -> Unit
typealias ui = @Composable () -> Unit
typealias ui_<T> = @Composable (T) -> Unit
typealias uiRow = @Composable RowScope.() -> Unit
typealias uiColumn = @Composable ColumnScope.() -> Unit

	
fun KProperty<*>.getType(): KClass<*>? = this.returnType.classifier as? KClass<*>
fun <T> KProperty1<T, *>.getTheBy(instance: T): Any? {
    return this.getDelegate(instance)
}

@Composable
fun <T> r(x: () -> T) = remember { x() }
fun <T> m(value: T) = mutableStateOf(value)
@Composable
fun <T> r(x: T) = r { m(x) }
@Composable
inline fun <T> r(vararg keys: Any?, crossinline calc: () -> T): T = remember(*keys, calculation = calc)


fun <T> mList() = mutableStateListOf<T>()

fun Mod.move(s: Any = 0, h: Any = s, w: Any = s): Mod =
    this.then(
        Modifier.offset(
            x = toDp(w), 
            y = toDp(h)
        )
    )


fun <T> Collection<T>.has(item: T): Bool = contains(item)
fun <K, V> Map<K, V>.hasKey(key: K): Bool = containsKey(key)
fun SharedPreferences.hasKey(key: Str) = contains(key)



fun File.file(name: Str): File {
    return File(this, name)
}


data class T<T>(
    val value: T,
    val type: Class<*> = value!!::class.java
)
data class VarInfo<T>(
	val name: Str,
    val value: T,
    val type: Class<*>? = value?.let { it::class.java },
)


fun Any?.commonType() = when (this) {
    null -> true
    is Str -> true
    is Number -> true
    is Bool -> true
    is Char -> true
    is Enum<*> -> true
    else -> false
}





fun Web?.url(url: Str) {
    this?.loadUrl(url)
}
fun Web?.reload() {
	this?.reload()
}
fun m_<Web?>.reload() {
    this.it?.reload()
}
fun m_<Web?>.url(url: Str) {
    this.it?.loadUrl(url)
}
val Web?.url: Str
    get() = this?.url ?: ""

val m_<Web?>.url: Str
    get() = this.it?.url ?: ""


typealias Nav = NavHostController
typealias NavBuilder = NavHostController

var AppNavUrlChanged by m(no)
fun goTo(route: Str){ 
	Do {
		AppNavUrlChanged = yes
		AppNav.navigate(route) 

		wait(1000)
		AppNavUrlChanged = no
	}
}
fun pop(route: Str){ 
	AppNav.navigate(route) 
}

fun NavGraphBuilder.url(txt: Str, UI: ui_<NavBackStackEntry>) {
    composable(txt) { UI(it) }
}
fun NavGraphBuilder.popup(txt: Str, UI: ui_<NavBackStackEntry>) {
    dialog(txt){ UI(it) }
}



fun DatePickerState.date(date: Any?) {
    this.selectedDateMillis = toLocalDate(date).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun DatePickerState.goTo(date: Any?) {
    var date2 = toLocalDate(date)
    val millis = YearMonth.of(date2.year, date2.monthValue)
        .atDay(1)
        .atStartOfDay()
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()

    // Apply it
    this.displayedMonthMillis = millis
}



fun Str.isBefore(x: Str): Bool = toLocalDate(this).isBefore(toLocalDate(x))
fun Str.isAfter(x: Str): Bool = toLocalDate(this).isAfter(toLocalDate(x))
fun Str.isEqual(x: Str): Bool = toLocalDate(this).isEqual(toLocalDate(x))


val LocalDate.words: Str
    get() {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
        return this.format(formatter)
    }

// Overload for String input (assume ISO date like "2026-01-31")
val Str.words: Str
    get() {
        return try {
            val date = LocalDate.parse(this)
            date.words
        } catch (e: Exception) {
            LocalDate.now().words // fallback to today if parsing fails
        }
	}




fun isM(x: Any?): Bool = x is m_<*>
fun isMBool(x: Any?): Bool = x is m_<*> && x.it is Bool
fun isMStr(x: Any?): Bool = x is m_<*> && x.it is Str
fun isMInt(x: Any?): Bool = x is m_<*> && x.it is Int






fun faded(color: Color, alpha: Float = 0.4f) = color.copy(alpha = alpha)

fun Color.darker(percent: Any): Color {
    val f = (1f - toF(percent)).coerceIn(0f, 1f)
    return Color(
        red = (red * f).coerceIn(0f, 1f),
        green = (green * f).coerceIn(0f, 1f),
        blue = (blue * f).coerceIn(0f, 1f),
        alpha = alpha
    )
}


fun getTextAsset(fileName: Str) = App.assets.open(fileName).bufferedReader().use { it.readText() }


// ✴️ PERMISSION RENAMESSS
fun startActivity(intent: Intent) {
    App.startActivity(intent)
}


//remember or (r) means composable or the output can change over time, screen rotation or other function change.
@Composable
fun rDensity(): Density = LocalDensity.current

@Composable
fun rLocalTextStyle(): TextStyle = LocalTextStyle.current

@Composable
fun rLocalTextSpanStyle(): SpanStyle = rLocalTextStyle().toSpanStyle()



fun <R> (() -> R)?.doOr(
    fallback: () -> R
): R = this?.invoke() ?: fallback()


fun <A, R> ((A) -> R)?.doOr(
    a: A,
    fallback: () -> R
): R = this?.invoke(a) ?: fallback()


fun <A, B, R> ((A, B) -> R)?.doOr(
    a: A,
    b: B,
    fallback: () -> R
): R = this?.invoke(a, b) ?: fallback()


fun <A, B, C, R> ((A, B, C) -> R)?.doOr(
    a: A,
    b: B,
    c: C,
    fallback: () -> R
): R = this?.invoke(a, b, c) ?: fallback()




val Any?.type: KClass<*>?
	get() = this?.let { it::class }


fun callerId(depth: Int = 0): Str {
    val stack = Throwable().stackTrace

    if (depth !in stack.indices) {
        return "invalid-depth"
    }

    val it = stack[depth]

    return "${it.fileName}-${it.className.substringAfterLast('.')}-${it.methodName}"
}

class By<T>(value: T) {
	var it by m(value)
	private var id by m("")
	

	
	private var onBuild: Do2_<ValVar, Str> = { _, _ -> }
    private var onGet: Do_<ValVar> = {}
    private var onSet: Do3_<ValVar, Str, T> = { _, _, _ -> }

	fun onBuild(x: Do2_<ValVar, Str>) = apply { onBuild = x }
    fun onGet(x: Do_<ValVar>) = apply { onGet = x }
    fun onSet(x: Do3_<ValVar, Str, T>) = apply { onSet = x }


	
    operator fun provideDelegate(thisRef: Any?, property: ValVar): By<T> {
		id = property.name
		onBuild(property, id)
        return this
    }
    operator fun getValue(thisRef: Any?, property: ValVar): T {
		onGet(property)
        return it
    }
    operator fun setValue(thisRef: Any?, property: ValVar, newValue: T) {
        it = newValue
		onSet(property, id, it)
    }
}



fun RemoteViews.onClick(
    viewId: Int,
    pendingIntent: PendingIntent
) {
    setOnClickPendingIntent(viewId, pendingIntent)
}

fun goTo(
    uri: Str,
    ctx: Context,
    requestCode: Int = 0
): PendingIntent {
    val intent = if (uri.startsWith("wind://")) {
        Intent(
            Intent.ACTION_VIEW,
            uri.toUri(),
            ctx,
            AppUI::class.java
        )
    } else {
        Intent()
    }

    return PendingIntent.getActivity(
        ctx,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

