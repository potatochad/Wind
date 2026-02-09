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


fun runOnceEver(action: Wait) {
    val key = autoId()
    if (!getData("RunOnceEver").getBoolean(key, no)) {
        Do{
          action()
        }
        getData("RunOnceEver").edit().putBoolean(key, yes).apply()
    }
}



@Composable
fun OnceEach(
    s: Any = 1000,
    condition: () -> Bool = { yes },
    action: Wait
) {
	RunOnce {
		while (condition()) {
			action()
			delay(toL(s))
		}
	}
}
fun Each(
	s: Any = 1000,
    condition: () -> Bool = { yes },
    action: Do
) {
	Do {
		while (condition()) {
			action()
			delay(toL(s))
		}
	}
}


@Composable
fun RunOnce(key1: Any? = Unit, key2: Any? = Unit, Do: Wait) {
    LaunchedEffect(key1, key2) {
        Do()
    }
}

fun wait(x: Any = 20, Do: Wait) {
    scope.launch {
		try {
			wait(x)
			Do()
		} catch (e: Exception) {
			log("<fun wait>: ${e.message}")
		}
    }
}
suspend fun wait(x: Any = 20) { delay(toL(x)) }

fun Try(log: Str="", onError: Wait = {}, Do: Wait){
    App.lifecycleScope.launch {
		try {
			Do()
		} catch (e: Exception) {
			Vlog("$log: ${e.message}")
			onError()
		}
	} 
}
fun Do(onError: Wait ={}, Do: Wait) {
	App.lifecycleScope.launch {
		try {
			Do()
		} catch (e: Exception) {
			Vlog("error: ${e.message}")
			onError()
		}
	} 
}
fun NoLag(onError: Wait ={}, Do: Wait) {
    App.lifecycleScope.launch(Dispatchers.Default) {
		try {
			Do()
		} catch (e: Exception) {
			Vlog("error: ${e.message}")
			onError()
		}
    }
}


inline fun check(
    condition: Bool,
    msg: Str = "",
    Do: Do = {},
) {
	if (condition) {
		if (msg.isNotEmpty()) Vlog(msg)
		Do()        // safe
	}
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








