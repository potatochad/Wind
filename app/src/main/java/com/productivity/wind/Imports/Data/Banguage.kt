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
import androidx.compose.ui.focus.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.ActivityResultLauncher

val gson = Gson()
val yes = true
val no = false
var <T> m_<T>.it: T
    get() = this.value
    set(value) { this.value = value }

fun Modifier.move(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp
) = this.padding(start = start, top = top, end = end, bottom = bottom)

fun Modifier.pad(h: Dp = 0.dp, v: Dp = 0.dp) = this.padding(horizontal = h, vertical = v)



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




@Composable
fun RunOnce(key1: Any? = Unit, block: suspend () -> Unit) {
    LaunchedEffect(key1) {
        block()
    }
}

val MakeTxtFile = ActivityResultContracts.CreateDocument("text/plain")


@Composable
fun <I, O> RememberLauncher(
    contract: ActivityResultContract<I, O>,
    onResult: (O) -> Unit
): ActivityResultLauncher<I> {
    return rememberLauncherForActivityResult(contract, onResult)
}


fun TxtFileToMap(ctx: Context, uri: Uri, fileMap: MutableMap<Str, Str>) {
    ctx.contentResolver.openInputStream(uri)?.bufferedReader()?.useLines { lines ->
        lines.forEach { line ->
            if (!line.contains("=")) {
                Vlog("Error...corrupted data")
                return@forEach
            }
            val (key, value) = line.split("=", limit = 2)
            fileMap[key] = value
        }
    }
}

fun StrToValue(valueNow: Any?, outputRaw: String?): Any? {
    return when (valueNow) {
        is Int -> outputRaw?.toIntOrNull()
        is Long -> outputRaw?.toLongOrNull()
        is Float -> outputRaw?.toFloatOrNull()
        is Double -> outputRaw?.toDoubleOrNull()
        is Boolean -> outputRaw?.toBooleanStrictOrNull()
        is SnapshotStateList<*> -> {
            val items = outputRaw?.split(",")?.map { it.trim() } ?: emptyList()
            mutableStateListOf(*items.toTypedArray())
        }
        else -> outputRaw
    }
}


@Composable
fun Modifier.scroll(
    v: Bool = yes,
    h: Bool = yes,
    r_v: ScrollState = rememberScrollState(),
    r_h: ScrollState = rememberScrollState(),
): Mod {
    var m = this
    if (v) m = m.verticalScroll(r_v)
    if (h) m = m.horizontalScroll(r_h)
    return m
}

suspend fun ScrollState.toBottom() {
    scrollTo(maxValue)
}

@Composable
fun r_Scroll() = rememberScrollState()






fun <T> MutableList<T>.edit(item: T, block: T.() -> Unit) {
	try {
		val index = this.indexOf(item)
		if (index != -1) {
			this[index].block()
		} else {
			Plog("failed to edit a list")
		}  
	} catch (e: Exception) {
		Plog("Edit crashed for item $item: ${e.message}")
	}
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
inline fun <reified T : Any> SnapshotStateList<T>.add(block: T.() -> Unit) {
    try {
        val newItem = T::class.java.getDeclaredConstructor().newInstance()
        newItem.block()

        this += newItem

    } catch (e: Exception) {
        println("Add failed: ${e.message}")
    }
}





@Composable
fun BasicInput(
    what: MutableState<String>,
    height: Dp = 34.dp,
    isInt: Bool = no,
	modifier: Modifier = Modifier
		.height(height)
        .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp))
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .onFocusChanged {}, 
	textStyle: TextStyle = TextStyle(),
    onChange: (Str) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current


	Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = r { MutableInteractionSource() }
            ) { focusManager.clearFocus() }
    ) {

    BasicTextField(
        value = what.value,
        onValueChange = {
            val filtered = if (isInt) it.filter { c -> c.isDigit() } else it
            what.value = filtered
            onChange(filtered)
        },
        textStyle = textStyle, 
		singleLine = yes,
        modifier = modifier, 
		keyboardOptions = KeyboardOptions(
            keyboardType = if (isInt) KeyboardType.Number else KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
	}
}



