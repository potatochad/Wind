package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.ToX.*
import java.time.*
import androidx.core.net.toUri
import java.time.format.*
import android.widget.*
import kotlinx.coroutines.sync.*
import kotlinx.coroutines.channels.*
import android.app.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.content.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import kotlinx.coroutines.*
import kotlin.reflect.KMutableProperty1
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.*
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import kotlin.collections.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import android.content.Intent
import java.time.LocalDate
import android.content.pm.*
import java.util.*
import java.io.*
import android.webkit.*
import androidx.compose.ui.text.*

fun AndroidSettings(action: Str) {
    startActivity(
		Intent(action).apply {
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		}
	)
}


@Composable
fun getW(): Float {
    return toF(BoxWithConstraints {
        toInt(maxWidth)
    })
}

@Composable
fun getH(): Float {
    return toF(BoxWithConstraints {
        toInt(maxHeight)
    })
}




fun ProgressColor(progress: Float): Color {
	return when {
		progress < 0.33f -> Color.Red
		progress < 0.66f -> Color.Yellow
		else -> Color.Green
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeCanBeTiny(ui: ui) {
	CompositionLocalProvider(
		LocalMinimumInteractiveComponentEnforcement provides false
	) {
		ui()
	}
}



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
var <T> mState_<T>.it: T
    get() = this.value
    set(value) { this.value = value }

//tiny more complex renames
fun <T> set(state: mState_<T>?, value: T) { state?.value = value }
fun show(state: mStateBool?) = set(state, yes)
fun hide(state: mStateBool?) = set(state, no)


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
typealias mListStr = mList<Str>
typealias mListInt = mList<Int>
typealias mListBool = mList<Bool>
typealias mListDouble = mList<Double>

	
typealias Wait = suspend () -> Unit
typealias Wait_<T> = suspend (T) -> Unit

typealias mState_<T> = MutableState<T>
typealias mStateBool= mState_<Bool>
typealias mStateInt= mState_<Int>
typealias mStateStr= mState_<Str>
	

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


fun <T> mList(vararg items: T) = mutableListOf(*items)
fun <T> mStateList(vararg items: T) = mutableStateListOf(*items)


@Composable
fun <T> r(x: () -> T) = remember { x() }
fun <T> mState(value: T) = mutableStateOf(value)
@Composable
fun <T> r(x: T) = r { mState(x) }
@Composable
inline fun <T> r(vararg keys: Any?, crossinline calc: () -> T): T = remember(*keys, calculation = calc)


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

	



fun Any?.commonType() = when (this) {
    null -> true
    is Str -> true
    is Number -> true
    is Bool -> true
    is Char -> true
    is Enum<*> -> true
    else -> false
}


val MakeTxtFile = ActivityResultContracts.CreateDocument("text/plain")

fun TxtFileToMap(ctx: ctx, uri: Uri, fileMap: MutableMap<Str, Str>) {
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



fun Web?.url(url: Str) {
    this?.loadUrl(url)
}
fun Web?.reload() {
	this?.reload()
}
fun mState_<Web?>.reload() {
    this.it?.reload()
}
fun mState_<Web?>.url(url: Str) {
    this.it?.loadUrl(url)
}
val Web?.url: Str
    get() = this?.url ?: ""

val mState_<Web?>.url: Str
    get() = this.it?.url ?: ""





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




fun isMState(x: Any?): Bool = x is mState_<*>
fun isMStateBool(x: Any?): Bool = x is mState_<*> && x.it is Bool
fun isMStateStr(x: Any?): Bool = x is mState_<*> && x.it is Str
fun isMStateInt(x: Any?): Bool = x is mState_<*> && x.it is Int






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


fun RemoteViews.onClick(
    viewId: Int,
    pendingIntent: PendingIntent
) {
    setOnClickPendingIntent(viewId, pendingIntent)
}






// no courotines!! 
class OneWorker {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val queue = Channel<suspend () -> Unit>()

    init {
        scope.launch {
            for (task in queue) {
                task()
            }
        }
    }

    fun post(task: suspend () -> Unit) {
        queue.trySend(task)
    }
}

class OneAtATime {
    private val mutex = Mutex()

    fun <T> use(block: suspend () -> T): Deferred<T> {
		return appScope.async {
			mutex.withLock {
				block()
			}
		}
	}
	fun <T> lazyUse(block: suspend () -> T) {
		appScope.async {
			mutex.withLock {
				block()
			}
		}
	}
}





class IgnoreRepeatedCalls(
    val delay: Long = 300,
    val scope: CoroutineScope = appScope,
	var block: suspend () -> Unit,
) {
    private var job: Job? = null
    private var pending = false

    fun run() {
        pending = true

        if (job != null) return

        job = scope.launch {
            delay(delay)

            if (pending) {
                block()
                pending = false
            }

            job = null
        }
    }
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
