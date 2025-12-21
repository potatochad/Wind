package com.productivity.wind.Imports.Data

import androidx.compose.runtime.*
import android.content.*
import android.util.*
import androidx.compose.ui.platform.*
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
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import java.lang.reflect.ParameterizedType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import java.time.LocalDate
import com.productivity.wind.Imports.*
import com.productivity.wind.Imports.Data.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaField
import android.net.*
import androidx.activity.compose.*




fun getData(File: Str = "Data"): SharedPreferences {
	return App.getSharedPreferences(File, Context.MODE_PRIVATE)
}
@Suppress("UNCHECKED_CAST")
fun <T> SharedPreferences.basicValue(key: Str, default: T): T {
    return when (default) {
        is Int -> getInt(key, default) as T
        is Bool -> getBoolean(key, default) as T
        is Float -> getFloat(key, default) as T
        is Long -> getLong(key, default) as T
        is Str -> (getString(key, default) ?: default) as T
        else -> default
    }
}

fun <T> saveBasic(key: Str, x: T, File: Str = "Data") {
    val Data = getData(File).edit()
    when (x) {
        is Int -> Data.putInt(key, x)
        is Bool -> Data.putBoolean(key, x)
        is Float -> Data.putFloat(key, x)
        is Long -> Data.putLong(key, x)
        is Str -> Data.putString(key, x)
        else -> return
    }
    Data.apply()
}

fun autoId(): Str {
    val e = Throwable().stackTrace[2]
    return "${e.fileName}:${e.lineNumber}"
}

fun <T> s(default: T, id: Str = autoId()): m_<T> {
    var x = m(default) 

	try {
		x = m(getData().basicValue(id, default))

		x.onChange {
			saveBasic(id, x.it)
		}
	} catch (e: Exception) {
		Vlog("error, deleting data for basic values: ${e.message}")
		getData().edit().clear().apply()
	}

	
    return x
}

fun <T> m_<T>.onChange(callback: Wait_<T>) {
    Do {
        snapshotFlow { this@onChange.it }
            .collectLatest {
                Do {
					callback(it)
				}
            }
    }
}








