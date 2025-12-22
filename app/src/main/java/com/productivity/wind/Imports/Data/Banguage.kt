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
import com.productivity.wind.Imports.Data.*
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



fun goBackWeb(web: Web?) {
    web?.post {
        if (web?.canGoBack()==yes) {
            web.goBack()
        }
    }
}
fun goBackWeb(web: m_<Web?>) {
    val w = web.it
    w?.post {
        if (w.canGoBack()) {
            w.goBack()
        }
    }
}

fun m_<Web?>.txt(done: DoStr = {}) {
    this.it?.evaluateJavascript(
        "(function(){ return document.body.innerText; })();"
    ) { text ->
        done(text ?: "")
    }
}



fun Mod.space(
    s: Any? = null,
    h: Any? = null,
    w: Any? = null,
    start: Any? = null,
    top: Any? = null,
    end: Any? = null,
    bottom: Any? = null
): Mod {
    return when {
        s != null -> this.padding(toDp(s))
        h != null || w != null -> this.padding(
            horizontal = toDp(h),
            vertical = toDp(w)
        )
        else -> this.padding(
            start = toDp(start),
            top = toDp(top),
            end = toDp(end),
            bottom = toDp(bottom)
        )
    }
}

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
fun toUIStr(it: Any?): UIStr = when (it) {
    is UIStr -> it
    is String -> UIStr(it)
    is Str -> UIStr(it)
    else -> UIStr(it?.toString() ?: "")
}

@Composable
fun toUI(it: Any?): UI {
    return when (it) {
        is Str -> { { Text(it) } }
        is Function0<*> -> it as UI   // unsafe cast but works at runtime
        else -> { { Text("Unsupported type (toUI) $it") } }
    }
}

@Composable
fun click(x: UI, Do: Do) {
	Box(Mod.click{Do()}){
		x()
	}
}

@Composable
fun Mod.click(
    animate: Bool = yes,
    Do: Do,
): Mod {
    return this.clickable(
        indication = if (animate) LocalIndication.current else null,
        interactionSource = MutableInteractionSource()
    ) {
        Do()
    }
}

fun Mod.clickOrHold(
    hold: Bool = yes,
    action: Do,
): Mod {
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

@Composable
fun move(s: Any = 0, w: Any = 0, h: Any = 0) {
	val sDp = toDp(s)
	val wDp = toDp(w)
	val hDp = toDp(h)

	Spacer(
		modifier = if (sDp > 0.dp) {
			Mod.s(sDp)  // uniform size
		} else {
			Mod.w(wDp).h(hDp)
		}
	)
}




fun Dp.toPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.value,
        App.resources.displayMetrics
    ).toInt()
}


@Composable
fun RunOnce(key1: Any? = Unit, key2: Any? = Unit, Do: Wait) {
    LaunchedEffect(key1, key2) {
        Do()
    }
}


val runOnceSet = mutableSetOf<Any>()
fun RunOnce(Do: Wait) {
	Do {
		val key = Do as Any
		if (key !in runOnceSet) {
			runOnceSet.add(key)
			Do()
		}
	}
}

fun runOnceEver(action: Wait) {
    val key = autoId()
    if (!getData("RunOnceEver").getBoolean(key, no)) {
        Do{
			action()
		}
        getData("RunOnceEver").edit().putBoolean(key, yes).apply()
    }
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




@Composable
fun r_Scroll() = rememberScrollState()

@Composable
fun Mod.scroll(
    v: Bool = yes,
    h: Bool = yes,
    r_v: ScrollState = r_Scroll(),
    r_h: ScrollState = r_Scroll(),
): Mod {
    var m = this
    if (v) m = m.verticalScroll(r_v)
    if (h) m = m.horizontalScroll(r_h)
    return m
}


@Composable
fun BasicInput(
    value: Str,
    isInt: Bool = no,
	w: Int=60,
	modifier: Mod = Mod
		.h(34).space(h = 8, w = 4).w(w)
		.background(InputColor, shape = RoundedCornerShape(4.dp))
		.wrapContentHeight(Alignment.CenterVertically),            
	textStyle: TextStyle = TextStyle(
		color = Gold,
		fontSize = 14.sp,
		textAlign = TextAlign.Start
	),
	oneLine: Bool= yes,
    Do: DoStr = {},
) {
	val focusManager = LocalFocusManager.current
	val focusRequester = r { FocusRequester() }

	Row(
		modifier = modifier.click { focusRequester.requestFocus() },
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Start       
	) {
		move(w=3)
		BasicTextField(
			value = value,
			onValueChange = { Do(it) },
			textStyle = textStyle, 
			singleLine = oneLine, 
			keyboardOptions = KeyboardOptions(
				keyboardType = if (isInt) KeyboardType.Number else KeyboardType.Text,
				imeAction = ImeAction.Done
			),
			keyboardActions = KeyboardActions(
				onDone = { focusManager.clearFocus() }
			),
			modifier = Mod.focusRequester(focusRequester)
		)
	}
}


@Composable
fun Input(
    what: m_<Str>,
    isInt: Bool = no,
	modifier: Mod = Mod, 
	textStyle: TextStyle = TextStyle(),
    onChange: DoStr = {},
) {
	val focusManager = LocalFocusManager.current

	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Start       
	) {
		move(w=5)
		BasicTextField(
			value = what.it,
			onValueChange = {
				val filtered = if (isInt) it.filter { c -> c.isDigit() } else it

				what.it = filtered
		
				onChange(filtered)
			},
			textStyle = textStyle, 
			singleLine = yes,
			// modifier = modifier, 
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







fun androidSettings(action: Str) {
    startActivity(
		Intent(action).apply {
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		}
	)
}

fun isNotificationEnabled(): Bool {
     return NotificationManagerCompat
        .getEnabledListenerPackages(App)
        .contains(AppPkg)
}

fun isBatteryOptimizationDisabled(): Bool {
    val pm = App.getSystemService(PowerManager::class.java)
    return pm.isIgnoringBatteryOptimizations(AppPkg)
}

fun isUsageP_Enabled(): Bool {
	val appOps = App.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    return appOps.checkOpNoThrow(
		AppOpsManager.OPSTR_GET_USAGE_STATS,
        Process.myUid(),
        AppPkg,
    ) == AppOpsManager.MODE_ALLOWED
}


@Suppress("DEPRECATION")
fun gotInternet(): Bool {
    val connectivityManager = App.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val activeNetwork = network?.let { connectivityManager.getNetworkCapabilities(it) }
        activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == yes ||
                activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == yes
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected == yes
    }

    if (!isConnected) Vlog("No internet")
    return isConnected
}

fun isLocationEnabled(): Bool {
    val lm = App.getSystemService(LocationManager::class.java)
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
           lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun locationPermission(Do: Do = {}) {
    if (ContextCompat.checkSelfPermission(
            App,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
		Do()
	} else {
        ActivityCompat.requestPermissions(
            App,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )
    }
}








@Composable
fun bottomSystemHeight(): Dp {
    val insets = WindowInsets.navigationBars // includes bottom system bar
    val density = LocalDensity.current
    return with(density) { insets.getBottom(this).toDp() }
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

    val usm = App.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Use INTERVAL_BEST and filter manually
    val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, start, end)
    val todayUsage = stats
        .filter { it.packageName == packageName && it.lastTimeUsed >= start }
        .sumOf { it.totalTimeInForeground }

    return (todayUsage / 1000L).toInt().coerceAtLeast(0)
}
fun getAppPkg(input: Any): Str {
    val pm = App.packageManager
    val result: Str = when (input) {
        is ResolveInfo -> input.activityInfo.packageName
        is Str -> {
            // Try to find package by app name
            pm.getInstalledApplications(0)
                .firstOrNull { 
                    pm.getApplicationLabel(it).toString().equals(input, ignoreCase = true) 
                }?.packageName ?: ""
        }
        else -> ""
    }
    return result
}
fun openApp(pkg: Str) {
    val pm: PackageManager = App.packageManager
    val launchIntent: Intent? = pm.getLaunchIntentForPackage(pkg)
    if (launchIntent != null) {
        App.startActivity(launchIntent)
    } else {
        Vlog("App not installed")
    }
}

fun getAppName(info: ResolveInfo): Str {
    val pkg = info.activityInfo.packageName
    return info.loadLabel(App.packageManager)?.toString() ?: pkg
}
fun getAppIcon(packageName: Str?): Drawable? {
    if (packageName.isNullOrBlank()) {
        return App.getDrawable(android.R.drawable.sym_def_app_icon)
    }

    val pm = App.packageManager
    return try {
        val info = pm.getApplicationInfo(packageName, 0)
        info.loadIcon(pm)
    } catch (e: Exception) {
        log("Bad package: $packageName")
        App.getDrawable(android.R.drawable.sym_def_app_icon)
    }
}








fun log(message: Str, int: Int = 200, tag: Str = "bad") {
    var msg = message.take(int)
    if (msg.length >= int) {msg += " ..."}

    Log.w(tag, msg)
}


fun getMyAppLogs() {
	Thread {
		val pid = android.os.Process.myPid()
		val process = Runtime.getRuntime().exec("logcat --pid=$pid *:W")
		val reader = BufferedReader(InputStreamReader(process.inputStream))
		val logs = mutableListOf<Str>()

		reader.forEachLine { line ->
			val s = line.replace(Regex("""^\d{2}-\d{2}\s+|\s+\d+\s+\d+\s+"""), " ")
			if ("ApkAssets: Deleting" in s) return@forEachLine
			if ("WindowOnBackDispatcher" in s) return@forEachLine
		
		
			logs.add(if (s.length > 300) s.take(300) + "..." else s)

			var cutLines = logs.takeLast(2000)

			cutLines.forEach { line ->
				if (line !in Bar.logs) {
					Bar.logs += "\n$line" // add only new lines to Bar.logs
				}
			}
			Bar.logs = Bar.logs.lines().takeLast(2000).joinToString("\n")
		}
	}.start()
}



fun <T> runHeavyTask(
    task: () -> T,         
    onResult: Do_<T>  
) {
    CoroutineScope(Dispatchers.Default).launch { // off UI
        val result = task()                       // run heavy work
        withContext(Dispatchers.Main) {           // back to UI
            onResult(result)                      // update Compose with result
        }
    }
}
fun wait(x: Any = 20, Do: Wait) {
    run.launch {
        delay(toL(x))
        Do()
    }
}
suspend fun wait(x: Any = 20) { delay(toL(x)) }

fun each(s: Any = 1000, Do: Wait) {
    RunOnce {
        while (yes) {
            Do()
            wait(s)
        }
    }
}




inline fun <T> MutableList<T>.each(
    block: MutableList<T>.(T) -> Unit
) {
    var i = 0
    while (i < this.size) {
        val item = this[i]
        this.block(item)
        i++
    }
}


fun folder(folderName: Str): File {
    val folder = File(App.filesDir, folderName)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    return folder
}





fun UIText(text: UIStr, style: StrStyle = StrStyle()): UIStr {
    return makeUIStr {
        pushStyle(style)
        add(text)
        pop()
    }
}

fun UIStr.getStyle(): StrStyle {
    return this.spanStyles.firstOrNull()?.item ?: StrStyle()
}

fun Any.size(value: TextUnit): UIStr {
    val uiStr = toUIStr(this)
    return UIText(uiStr, uiStr.getStyle().copy(fontSize = value))
}

fun Any.bold(): UIStr {
	val uiStr = toUIStr(this)
	return UIText(uiStr, uiStr.getStyle().copy(fontWeight = FontWeight.Bold))
}

fun Any.color(value: Color): UIStr {
	val uiStr = toUIStr(this)
	return UIText(uiStr, uiStr.getStyle().copy(color = value))
}
fun Any.gold(): UIStr {
	val uiStr = toUIStr(this)
	return UIText(uiStr, uiStr.getStyle().copy(color = value))
}







lateinit var App: ComponentActivity
lateinit var AppNav: NavHostController
lateinit var AppPkg: Str
val run: CoroutineScope
    get() = App.lifecycleScope 

var AppH by m(0.dp)
var AppW by m(0.dp)
var AppLazyH by m(0.dp)
	
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set navigation bar black with white icons
        WindowCompat.setDecorFitsSystemWindows(window, yes)

        // Set navigation bar black with white icons
        window.navigationBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightNavigationBars = no
            show(WindowInsetsCompat.Type.systemBars()) // Force visible
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }



/*
		val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
    Log.e(
        "CRASH",
        "==== CRASH ====\n${throwable.stackTraceToString()}"
    )

    defaultHandler?.uncaughtException(thread, throwable)
}
*/
		App = this
		AppPkg = this.packageName

		AppStart_beforeUI()

        setContent { 
			AppNav = rememberNavController()
			AppH = LocalConfiguration.current.screenHeightDp.dp
			AppW = LocalConfiguration.current.screenWidthDp.dp
			AppLazyH = AppH - 100.dp - bottomSystemHeight()
		
			AppContent()
        }
    }

    override fun onResume() {
        super.onResume()
        // re-apply nav bar color to prevent flashing
        window.navigationBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = no

        OnResume()
    }
	
	override fun onDestroy() {
		super.onDestroy()
		
	}
	
}





