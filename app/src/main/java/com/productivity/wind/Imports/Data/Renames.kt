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
import com.productivity.wind.Imports.Data.*
import androidx.compose.ui.text.*


var CardColor = Color(0xFF1A1A1A)
var InputColor = Color(0xFF272727)
val DarkBlue = Color(0xFF00008B) 
val Gold = Color(0xFFFFD700)
val LightBlue = Color(0xFFADD8E6)


val gson = Gson()
val yes = true
val no = false
var <T> m_<T>.it: T
    get() = this.value
    set(value) { this.value = value }
var Scroll.it: Int
    get() = this.value
    set(value) {
        CoroutineScope(Dispatchers.Main).launch {
            this@it.scrollTo(value)
        }
    }

fun Mod.w(min: Any?, max: Any? = min) = this.widthIn(max = toDp(max), min = toDp(min))
fun Mod.h(min: Any?, max: Any? = min) = this.heightIn(max = toDp(max), min = toDp(min))
fun Mod.s(value: Any?) = this.size(toDp(value))

fun Mod.maxS(): Mod= this.fillMaxSize()
fun Mod.maxW(): Mod= this.fillMaxWidth()
fun Mod.maxH(): Mod= this.fillMaxHeight()

fun <T> m(value: T) = mutableStateOf(value)
fun <T> set(state: m_<T>?, value: T) { state?.value = value }
fun show(state: m_<Bool>?) = set(state, yes)
fun hide(state: m_<Bool>?) = set(state, no)
fun Id(): Str { return UUID.randomUUID().toString() }

val maxInt = Int.MAX_VALUE

typealias Web = WebView
typealias UI = Content
typealias Content = @Composable () -> Unit
typealias Content_<T> = @Composable (T) -> Unit
typealias ui = @Composable () -> Unit
typealias ctx = Context
typealias ui_<T> = @Composable (T) -> Unit
typealias Do = () -> Unit
typealias UI_<T> = @Composable (T) -> Unit
typealias Mod = Modifier
typealias mod = Modifier
typealias Wait = suspend () -> Unit
typealias Wait_<T> = suspend (T) -> Unit
typealias Do_<T> = (T) -> Unit
typealias DoStr = (Str) -> Unit     
typealias DoInt = (Int) -> Unit        
typealias m_<T> = MutableState<T>
typealias mBool= MutableState<Bool>
typealias Str = String
typealias Bool = Boolean
typealias UIStr = AnnotatedString
typealias UIStrBuilder = AnnotatedString.Builder
typealias StrStyle = SpanStyle
typealias ClassVar<T, R> = KMutableProperty1<T, R>
typealias ClassValVar<T, R> = KProperty1<T, R>
typealias Scroll = ScrollState
typealias LazyList = LazyListState

    
fun KProperty<*>.getType(): KClass<*>? = this.returnType.classifier as? KClass<*>
fun <T> KProperty1<T, *>.getTheBy(instance: T): Any? {
    return this.getDelegate(instance)
}
@Composable
fun <T> r(value: () -> T) = remember { value() }
@Composable
fun <T> r_m(initial: T) = r { m(initial) }
inline fun <reified T> ml(): MutableList<T> = mutableListOf()
inline fun <reified T> ml(@Suppress("UNUSED_PARAMETER") dummy: T): SnapshotStateList<T> { return mutableStateListOf() }
@Composable
fun <T> track(value: () -> T): State<T> = r { derivedStateOf(value) }
fun <T> mList() = mutableStateListOf<T>()


@Composable
fun Mod.Vscroll(r_v: Scroll = Scroll()): Mod{return this.scroll(yes, no, r_v)}
@Composable
fun Mod.Hscroll(r_h: Scroll = Scroll()): Mod{return this.scroll(no, yes, r_h=r_h)}

fun Scroll.toBottom() = wait{ scrollTo(maxValue) }

fun LazyList.toBottom() = wait{
    if (layoutInfo.totalItemsCount > 0) {
        scrollToItem(layoutInfo.totalItemsCount - 1)
    }
}






fun Scroll.scroll(it: Any) = wait{ 
	// animateScrollBy(toF(it))
}






fun Scroll.goTo(it: Any) = wait{ scrollTo(toInt(it)) }

fun Mod.move(s: Any = 0, h: Any = s, w: Any = s): Mod =
    this.then(
        Modifier.offset(
            x = toDp(w), 
            y = toDp(h)
        )
    )



fun File.file(name: Str): File {
    return File(this, name)
}
val Scroll.size: Int
    get() = maxValue

val Scroll.isMax: Bool
    get() = value >= maxValue

@Composable
fun Scroll() = rememberScrollState()
@Composable
fun r_Scroll() = rememberScrollState()
@Composable
fun LazyList() = rememberLazyListState()






val Str.size get() = length
fun Str.last(n: Int): Str = this.takeLast(n)
fun Str.fromTo(start: Int, end: Int = this.size): Str = this.substring(start, end)

fun makeUIStr(Do: UIStrBuilder.() -> Unit): UIStr {
    return buildAnnotatedString(Do)
}
fun UIStrBuilder.add(text: Char) = append(text)
fun UIStrBuilder.add(text: Str) = append(text)
fun UIStrBuilder.add(text: UIStr) = append(text)

fun navBack() { AppNav.popBackStack() }

inline fun <reified T> NavBackStackEntry.url(key: Str): T? {
    val v = arguments?.get(key)
    if (v == "_") return null
    return v as? T
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



fun goTo(route: Str){ AppNav.navigate(route) }

fun NavGraphBuilder.url(txt: Str, UI: ui_<NavBackStackEntry>) {
    composable(txt) { UI(it) }
}
fun NavGraphBuilder.popup(txt: Str, UI: ui_<NavBackStackEntry>) {
    dialog(txt){ UI(it) }
}




fun faded(color: Color, alpha: Float = 0.4f) = color.copy(alpha = alpha)






//✴️ Data renames
fun Any.eachValVar(Do: (ClassValVar<Any, *>) -> Unit) {
    this::class.memberProperties.forEach {
        Do(it as ClassValVar<Any, *>)  // cast to safe type
    }
}






// ✴️ PERMISSION RENAMESSS
fun startActivity(intent: Intent) {
    App.startActivity(intent)
}



