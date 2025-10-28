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
fun TxtFileSaver(onFileCreated: (Uri?) -> Unit): ManagedActivityResultLauncher<String, Uri?> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain"),
        onResult = { uri -> onFileCreated(uri) }
    )
}

fun getJavaClass(bar: ClassVar<Settings, Any?>): Class<*>? {
    var name = bar.name
    val kProperty = bar as? KProperty1<*, *> ?: return null  // ensure it’s a property
    val classifier = kProperty.returnType.arguments.firstOrNull()?.type?.classifier as? KClass<*>

    return when {
        classifier != null -> classifier.java                        // generic type
        kProperty.returnType.classifier is KClass<*> ->
            (kProperty.returnType.classifier as KClass<*>).java     // plain type
        else -> {
            log("No type info for $name, skipping")
            null
        }
    }
}

fun SharedPreferences.getAny(bar: ClassVar<Settings, Any?>): Any? {
    val name = bar.name

    bar.isAccessible = true

    val clazz = getJavaClass(bar)
    //! clazz ONLY GIVES TYPE that in the box box.

    val storedValue = getString(name, null)

    return try {
        gson.fromJson(storedValue, clazz)
    } catch (e: Exception) {
        try {
            val type = TypeToken.getParameterized(List::class.java, clazz).type
            gson.fromJson(storedValue, type)

        } catch (e: Exception) {
            null
        }
    }
}




inline fun <reified T> SharedPreferences.Editor.putAny(name: Str, value: T?) {
    val json = Gson().toJson(value)
    putString(name, json)
}











