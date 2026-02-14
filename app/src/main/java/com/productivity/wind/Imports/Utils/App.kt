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
import com.productivity.wind.Imports.UI_visible.*
import android.os.Process.*
import android.content.ClipData
import android.content.ClipboardManager



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

fun closeApp() {
    App.finishAffinity()
    killProcess(myPid())
    System.exit(0)
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
fun DarkBackground(onDismiss: Do = {}){
	Popup(
		onDismissRequest = { onDismiss() },
		alignment = Alignment.Center,
	) {
		Box(
			Mod.maxS().background(faded(Color.Black)).pointerInput(Unit) {
				detectTapGestures(
					onTap = {
						onDismiss()
						Vlog("black click")
					}
				)
			}
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








fun log(message: Str, int: Int = 200) {
    var msg = message.take(int)
    if (msg.length >= int) {msg += " ..."}

    Log.e("[bad]", msg)
}


fun getMyAppLogs() {
	Thread {
		val pid = android.os.Process.myPid()
		val process = Runtime.getRuntime().exec("logcat --pid=$pid *:W")
		val reader = BufferedReader(InputStreamReader(process.inputStream))

		reader.forEachLine { line ->
			val s = line.replace(Regex("""^\d{2}-\d{2}\s+|\s+\d+\s+\d+\s+"""), " ").takeLast(3000)
			if ("ApkAssets: Deleting" in s) return@forEachLine
			if ("WindowOnBackDispatcher" in s) return@forEachLine
			if (" W " in s) return@forEachLine

			val last = Bar.logs.lastOrNull()

			if (last != s){
				Bar.logs.add(s)
			} else {
				if ("[bad]" in s) {
					Bar.logs.add(s)
				}
			}

			
		}
	}.start()
}

fun captureAppCrashes() {
    Thread {
        try {
            val process = Runtime.getRuntime().exec("logcat *:E") 
            val reader = BufferedReader(InputStreamReader(process.inputStream))

			val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())


            reader.forEachLine { line ->
				val s = line.replace(Regex("""^\d{2}-\d{2}\s+|\s+\d+\s+\d+\s+"""), " ").takeLast(3000)

				var logTime = s.takeLast(5) //09:25
				
                
                if (s.contains("FATAL EXCEPTION") && s.contains(BuildConfig.APPLICATION_ID) && logTime == currentTime) {
                    Bar.logs.add(s)
                }
            }
        } catch (e: Exception) {
			Vlog("exception trigerred")
            e.printStackTrace()
        }
    }.start()
}


fun folder(folderName: Str): File {
    val folder = File(App.filesDir, folderName)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    return folder
}




private var lastToast: Toast? = null

fun Vlog(msg: Str, special: Str = "none", delayLevel: Int = 0) {
    val delayMs = (delayLevel.coerceIn(0, 100)) * 30L // Example: Level 2 = 60ms

    if (special.equals("one", true)) {
        lastToast?.cancel()
    }

	log(msg)


    Handler(Looper.getMainLooper()).postDelayed({
        val toast = Toast.makeText(App, msg, Toast.LENGTH_SHORT)
        lastToast = toast
        toast.show()
    }, delayMs)
}

@Composable
fun getStatusBarHeight(): Int {
    val insets = WindowInsets.statusBars.asPaddingValues()
    val density = LocalDensity.current
    return with(density) { insets.calculateTopPadding().toPx().toInt() }
}

fun fixedInputScroll(
    text: TextFieldValue,
    cursorPos: Int,
    done: m_<Bool>,
    scroll: ScrollState
) {
    if (text.text.isNotEmpty() && !done.it) {
		done.it = yes

        val ratio = toF(cursorPos) / toF(text.text.size)
        val max = toF(scroll.maxValue)
        val scrollTo = (max * ratio)

        scroll.goTo(scrollTo)
    }

	if (cursorPos == text.text.size) {
		scroll.toBottom()
	}
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


var SettingsItemCardColor = Color(0xFF121212)


fun CopyToClipboard(txt: Str) {
	val clipboard = App.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
	val clip = ClipData.newPlainText("label", txt)
	clipboard.setPrimaryClip(clip)
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

fun SendEmail(
	recipient: Str = "productivity.shield@gmail.com",
	subject: Str = "Support Request – App Issue",
	includePhoneInfo: Bool = yes,
	prefillMessage: Str = "I'm experiencing the following issue with the app:\n\n",
) {
	val body = buildString {
		appendLine()
		if (includePhoneInfo) {
			appendLine("Phone Info:")
			appendLine("• Manufacturer: ${Build.MANUFACTURER}")
			appendLine("• Model: ${Build.MODEL}")
			appendLine("• Android Version: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
			appendLine()
		}
		append(prefillMessage)
	}

	val intent = Intent(Intent.ACTION_SEND).apply {
		type = "message/rfc822"
		putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
		putExtra(Intent.EXTRA_SUBJECT, subject)
		putExtra(Intent.EXTRA_TEXT, body)
	}

	val chooser = Intent.createChooser(intent, "Send Email").apply {
		flags = Intent.FLAG_ACTIVITY_NEW_TASK
	}

	startActivity(chooser)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Main") {
            ScreenNav()
        }
}




lateinit var App: ComponentActivity
lateinit var AppCtx: Context
lateinit var AppNav: NavHostController
lateinit var AppPkg: Str
lateinit var permission: ActivityResultLauncher<Str>

var AppH by m(0.dp)
var AppW by m(0.dp)
var AppLazyH by m(0.dp)
var AppDensity by m(0f)

lateinit var scope: CoroutineScope

  
	
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


		App = this
		AppCtx = this.applicationContext
		AppPkg = this.packageName



		

		permission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            log("permission granted?: $granted")
		}


		CreateNotificationChannel(this)
		
		

		AppStart_beforeUI()

        setContent { 
			AppDensity = LocalDensity.current.density
			scope = rememberCoroutineScope()

			AppNav = rememberNavController()
			AppH = LocalConfiguration.current.screenHeightDp.dp
			AppW = LocalConfiguration.current.screenWidthDp.dp
			AppLazyH = AppH - 100.dp - bottomSystemHeight()

			Bar.userLocation = toLatLng(Bar.userLatLng)
		
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
	override fun onPause() {
		super.onPause()
		
		OnLeaveApp()
		log("[LEFT APP]")
	}

	
}





