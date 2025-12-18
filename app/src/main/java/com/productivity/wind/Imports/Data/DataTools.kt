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


@Suppress("UNCHECKED_CAST")

val Bar = Settings()

var initOnce= no

object SettingsSaved {
    var Dosave: Job? = null
    const val PREFS = "settingsLISTS"
    val lastJson = mutableMapOf<Str, Str>()
    var autoSaveJob: Job? = null


    fun Bsave() {
        if (Dosave?.isActive == yes) return
        
        Dosave = GlobalScope.launch {
            while (isActive) {
                val Data = getStoredData().edit()
                
                Bar.eachValVar {
                    Data.putAny(it.name, it.get(Bar))
                }
                Data.apply()
                wait(1000)
            }
        }
    }
    
    fun init() {
        log("initializing data")
        val Data = getStoredData()
        if (Data.all.isEmpty() || initOnce) return
        initOnce = yes


        Bar.eachValVar {
            val bar = it as ClassVar<Settings, Any?>
            val gotValue = Data.getAny(bar) ?: return@eachValVar

            try {
                when (gotValue) {
                    is SnapshotStateList<*> -> {
                        (bar as? SnapshotStateList<Any?>)?.apply {
                            clear()
                            addAll(gotValue as SnapshotStateList<Any?>)
                        } ?: bar.set(Bar, gotValue)
                    }
                    is List<*> -> {
                        val snapshotValue = mutableStateListOf(*gotValue.toTypedArray())
                        (bar as? SnapshotStateList<Any?>)?.apply {
                            clear()
                            addAll(snapshotValue)
                        } ?: bar.set(Bar, snapshotValue)
                    }
                    else -> {
                        bar.set(Bar, gotValue)
                    }
                }
            } catch (e: Exception) {
                Vlog("init error: ${e.message}")
            }
        }

    }
    

}








