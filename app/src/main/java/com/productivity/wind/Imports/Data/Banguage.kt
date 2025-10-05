package com.productivity.wind.Imports.Data

import timber.log.Timber
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
import android.content.ClipData
import android.content.ClipboardManager
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
import androidx.annotation.RequiresApi
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


val gson = Gson()
val yes = true
val no = false
var <T> m_<T>.it: T
    get() = this.value
    set(value) { this.value = value }


fun Dp.toPx(): Int {
    var context = App.ctx
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.value,
        context.resources.displayMetrics
    ).toInt()
}
fun getStoredData(
    fileName: Str = "settings",
    mode: Int = Context.MODE_PRIVATE
): SharedPreferences = App.ctx.getSharedPreferences(fileName, mode)

fun <T : Any> getClass(obj: T): List<KProperty1<T, *>> =
    obj::class.memberProperties
        .also { props ->
            props.forEach { prop ->
                if (prop.visibility != KVisibility.PUBLIC) {
                    log("NO private stuff")
                }
            }
        }
        .filter { it.visibility == KVisibility.PUBLIC } as List<KProperty1<T, *>>



fun <T> m(value: T) = mutableStateOf(value)
fun <T> set(state: m_<T>?, value: T) { state?.value = value }
fun show(state: m_<Bool>?) = set(state, yes)
fun hide(state: m_<Bool>?) = set(state, no)

fun Id(): Str { return UUID.randomUUID().toString() }
typealias Content = @Composable () -> Unit
typealias Do = () -> Unit
typealias Content_<T> = @Composable (T) -> Unit
typealias Mod = Modifier
typealias Do_<T> = (T) -> Unit
typealias m_<T> = MutableState<T>
typealias Str = String
typealias Bool = Boolean
typealias ClassVar<T, R> = KMutableProperty1<T, R>
typealias ClassVal<T, R> = KProperty1<T, R>





//?FOR TYPES

fun KProperty<*>.getType(): KClass<*>? = this.returnType.classifier as? KClass<*>
val KClass<*>.isBool get() = this == Bool::class
val KClass<*>.isInt get() = this == Int::class
val KClass<*>.isFloat get() = this == Float::class
val KClass<*>.isDouble get() = this == Double::class
val KClass<*>.isLong get() = this == Long::class
val KClass<*>.isStr get() = this == Str::class
val KClass<*>.isShort get() = this == Short::class
val KClass<*>.isByte get() = this == Byte::class
val KClass<*>.isChar get() = this == Char::class
val KClass<*>.isDp get() = this == Dp::class
val KClass<*>.isMutableList get() = this == MutableList::class
val KClass<*>.isList get() = this == List::class
val KClass<*>.isMutableStateListOf get() = this == SnapshotStateList::class

val KClass<*>?.isBool get() = this == Bool::class
val KClass<*>?.isInt get() = this == Int::class
val KClass<*>?.isFloat get() = this == Float::class
val KClass<*>?.isDouble get() = this == Double::class
val KClass<*>?.isLong get() = this == Long::class
val KClass<*>?.isStr get() = this == Str::class
val KClass<*>?.isShort get() = this == Short::class
val KClass<*>?.isByte get() = this == Byte::class
val KClass<*>?.isChar get() = this == Char::class
val KClass<*>?.isDp get() = this == Dp::class
val KClass<*>?.isMutableList get() = this == MutableList::class
val KClass<*>?.isList get() = this == List::class
val KClass<*>?.isMutableStateListOf get() = this == SnapshotStateList::class



fun SharedPreferences.Editor.putAny(name: Str, value: Any?) {
    when (value) {
        is Bool -> putBoolean(name, value)
        is Str -> putString(name, value)
        is Int -> putInt(name, value)
        is Float -> putFloat(name, value)
        is Long -> putLong(name, value)
        is MutableList<*> -> putMutableList(name, value)
        else -> Vlog("skip — $value")
    }
}

fun <T> SharedPreferences.Editor.putMutableList(id: Str, list: MutableList<T>?) {
    if (list == null) {
        Vlog("no mutable list")
        return
    }
    
    val json = gson.toJson(list)
    putString("MutableList $id", json)
}

inline fun <reified T> SharedPreferences.getMutableList(id: Str): MutableList<T>? {
    val json = getString("MutableList $id", null) ?: return null
    val type = object : TypeToken<MutableList<T>>() {}.type
    return gson.fromJson(json, type)
}


@Suppress("UNCHECKED_CAST")
fun <T : Any> loadMutableState(type: KClass<*>, name: Str, fullBar: m_<T>, Data: SharedPreferences) {
    when {
        type.isBool -> fullBar.it = Data.getBoolean(name, no) as T
        type.isStr -> fullBar.it = (Data.getString(name, "") ?: "") as T
        type.isInt -> fullBar.it = Data.getInt(name, 0) as T
        type.isFloat -> fullBar.it = Data.getFloat(name, 0f) as T
        type.isLong -> fullBar.it = Data.getLong(name, 0L) as T
        else -> log("Unsupported type: $type")
    }
}













@Composable
fun <T> r(value: () -> T) = remember { value() }
@Composable
fun <T> r_m(initial: T) = r { m(initial) }
inline fun <reified T> ml(): MutableList<T> = mutableListOf()
inline fun <reified T> ml(@Suppress("UNUSED_PARAMETER") dummy: T): SnapshotStateList<T> { return mutableStateListOf() }
