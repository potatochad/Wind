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
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*


val gson = Gson()
val yes = true
val no = false
var <T> m_<T>.it: T
    get() = this.value
    set(value) { this.value = value }

fun Modifier.space(
    s: Any? = null,
    h: Any? = null,
    v: Any? = null,
    start: Any? = null,
    top: Any? = null,
    end: Any? = null,
    bottom: Any? = null
): Modifier {
    return when {
        s != null -> this.padding(toDp(s))
        h != null || v != null -> this.padding(
            horizontal = toDp(h),
            vertical = toDp(v)
        )
        else -> this.padding(
            start = toDp(start),
            top = toDp(top),
            end = toDp(end),
            bottom = toDp(bottom)
        )
    }
}

fun toDp(value: Any?): Dp = when (value) {
    is Dp -> value           // already Dp
    is Int -> value.dp       // Int → Dp
    is Float -> value.dp     // Float → Dp
    is Double -> value.dp    // Double → Dp
    null -> 0.dp             // null → 0.dp
    else -> 0.dp             // anything else → 0.dp
}



fun goTo(route: Str) = App.navHost.navigate(route)





fun Modifier.w(value: Any?): Modifier =
    this.then(Modifier.width(toDp(value)))

fun Modifier.h(value: Any?): Modifier =
    this.then(Modifier.height(toDp(value)))


fun Modifier.clickOrHold(
    hold: Bool = yes,
    action: Do,
): Modifier {
    return if (hold) {
        pointerInput(Unit) {
            detectTapGestures(onLongPress = { action() })
        }
    } else {
        clickable(
            indication = null,
            interactionSource = MutableInteractionSource()
        ) {
            action()
        }
	}
}

fun Modifier.maxS(): Mod= this.fillMaxSize()
fun Modifier.maxW(): Mod= this.fillMaxWidth()
fun Modifier.maxH(): Mod= this.fillMaxHeight()





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
    what: MutableState<Str>,
    isInt: Bool = no,
	modifier: Modifier = Modifier
		.h(34)
        .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp))
        .space(h= 8, v= 4)
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
            what.it = filtered
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


fun openPermissionSettings(action: Str, uri: Uri? = null) {
        val intent = Intent(action).apply {
            uri?.let { data = it }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        App.ctx.startActivity(intent)
    }

    fun isNotificationEnabled(): Bool {
        return NotificationManagerCompat
            .getEnabledListenerPackages(App.ctx)
            .contains(App.ctx.packageName)
    }

    fun isBatteryOptimizationDisabled(): Bool {
        val pm = App.ctx.getSystemService(PowerManager::class.java)
        return pm.isIgnoringBatteryOptimizations(App.ctx.packageName)
    }

    fun isUsageP_Enabled(): Bool {
        val appOps = App.ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        return appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            App.ctx.packageName,
        ) == AppOpsManager.MODE_ALLOWED
	}




@Composable
fun bottomSystemHeight(): Dp {
    val insets = WindowInsets.navigationBars // includes bottom system bar
    val density = LocalDensity.current
    return with(density) { insets.getBottom(this).toDp() }
}


fun log(message: Str, int: Int = 200, tag: Str = "Bad") {
    var msg = message.take(int)
    if (msg.length >= int) {msg += " ..."}
    Log.w(tag, msg)
}



fun getTodayAppUsage(packageName: Str): Int {
    val end = System.currentTimeMillis()
    val cal = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val start = cal.timeInMillis

    val usm = App.ctx.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Use INTERVAL_BEST and filter manually
    val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, start, end)
    val todayUsage = stats
        .filter { it.packageName == packageName && it.lastTimeUsed >= start }
        .sumOf { it.totalTimeInForeground }

    return (todayUsage / 1000L).toInt().coerceAtLeast(0)
}
fun getAppPackage(ri: ResolveInfo): Str {
    return ri.activityInfo.packageName
}
fun getAppName(info: ResolveInfo): Str {
    val pkg = info.activityInfo.packageName
    return info.loadLabel(App.ctx.packageManager)?.toString() ?: pkg
}


@Composable
fun getAppIcon(packageName: Str): Drawable? {
    val pm = App.ctx.packageManager
    return remember(packageName) {
        try {
            pm.getApplicationIcon(packageName)
        } catch (_: Exception) {
            null
        }
    }
}

