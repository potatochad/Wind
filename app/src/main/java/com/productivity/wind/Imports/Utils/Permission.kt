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
import android.*
import android.provider.*


object Permission {
    private fun getAndDo(permissionStr: Str, onGranted: Do): Bool {
        return if (ContextCompat.checkSelfPermission(App, permissionStr) == PackageManager.PERMISSION_GRANTED) {
            onGranted()
            true // permission already granted
        } else {
            permission.launch(permissionStr) // request permission
            false // permission not granted yet
        }
    }

    fun notification(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.POST_NOTIFICATIONS, onGranted)
    }

    fun camera(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.CAMERA, onGranted)
    }

    fun locationFine(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.ACCESS_FINE_LOCATION, onGranted)
    }

    fun locationCoarse(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.ACCESS_COARSE_LOCATION, onGranted)
    }

    fun readStorage(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.READ_EXTERNAL_STORAGE, onGranted)
    }

    fun writeStorage(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.WRITE_EXTERNAL_STORAGE, onGranted)
    }

    fun recordAudio(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.RECORD_AUDIO, onGranted)
    }

    fun readContacts(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.READ_CONTACTS, onGranted)
    }

    fun sendSMS(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.SEND_SMS, onGranted)
    }

    fun callPhone(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.CALL_PHONE, onGranted)
    }

    fun readPhoneState(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.READ_PHONE_STATE, onGranted)
    }

    fun backgroundLocation(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.ACCESS_BACKGROUND_LOCATION, onGranted)
    }

    fun bodySensors(onGranted: Do= {}): Bool {
        return getAndDo(Manifest.permission.BODY_SENSORS, onGranted)
    }


	fun ignoreOptimizations(onGranted: Do = {}): Bool {
		val pm = App.getSystemService(Context.POWER_SERVICE) as PowerManager

		// Already ignoring? Run callback and return true
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && pm.isIgnoringBatteryOptimizations(App.packageName)) {
			onGranted()
			return true
		}

		// Not ignoring yet? Launch system dialog
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			val intent = Intent().apply {
				action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
				data = Uri.parse("package:${App.packageName}")
				flags = Intent.FLAG_ACTIVITY_NEW_TASK
			}

			return try {
				App.startActivity(intent)
				false
			} catch (e: Exception) {
				false
			}
		}

		// Older Android versions don't have battery optimizations
		onGranted()
		return true
	}


	
	val systemAlertWindow = "android.permission.SYSTEM_ALERT_WINDOW"
	
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

// ✴️NOT WHAT YOU THINK FUNCTION, IGNORE
fun isNotificationEnabled(): Bool {
     return NotificationManagerCompat
        .getEnabledListenerPackages(App)
        .contains(AppPkg)
}






fun Android8OrAbove(Do: Do) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Do()
    }
}

fun CreateNotificationChannel(context: Context) {
    Android8OrAbove {
        val channel = NotificationChannel(
            "WindApp_id",
            "WindChannel_name",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel description"
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}













