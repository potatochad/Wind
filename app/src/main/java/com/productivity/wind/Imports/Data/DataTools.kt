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


fun writeToFile(ctx: Context, uri: Uri, text: Str) {
    ctx.contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { writer ->
        writer.write(text)
    }
}


@Composable
fun BsaveToFile() {
    val ctx = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        if (uri != null) {
            val Data = getStoredData()
            ctx.contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { out ->
                Data.all.forEach { (key, value) -> out.write("$key=$value\n") }
            }
        }
    }
    RunOnce {
        log("LAUNCHING BsaveTofile")
        launcher.launch("WindBackUp.txt")
    }
}


@Composable
fun BrestoreFromFile() {
    val ctx = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
            if (uri != null) {
                try {
                    val fileMap = mutableMapOf<Str, Str>()

                    TxtFileToMap(ctx, uri, fileMap)

                    SettingsSaved.initFromFile(fileMap)
                } catch (e: Exception) {
                    log("Restore failed: ${e.message}")
                }
            }
        }

    RunOnce {
        launcher.launch(arrayOf("text/plain"))
    }
}



val Bar = Settings(); //best variable

var initOnce= false

object SettingsSaved {
    private var Dosave: Job? = null
    private const val PREFS = "settingsLISTS"
    private val lastJson = mutableMapOf<Str, Str>()
    private var autoSaveJob: Job? = null


    fun Bsave() {
        if (Dosave?.isActive == true) return
        
        Dosave = GlobalScope.launch {
            while (isActive) {
                val Data = getStoredData().edit()
                var CPU = 0
                
                getClass(Bar).forEach { bar ->

                    /*CPU usage, forget this ok*/CPU+=20; if (CPU>2000) {
                    log("SettingsManager: Bsave is taking up to many resourcesss. Shorter delay, better synch, like skipping things, and maing sure only one runs, can greatly decrease THE CPU USAGE") }//ADD SUPER UNIVERSAL STUFFF
                    val value = bar.get(Bar)
                
                    Data.putAny(bar.name, value)
                }
                Data.apply()
                delay(1_000L)
            }
        }
    }
    fun init() {
        log("initializing data")
        val Data = getStoredData()
        if (Data.all.isEmpty() || initOnce) return
        initOnce = true


        getClass(Bar).forEach { barIdk ->
            val bar = barIdk as ClassVar<Settings, Any?>
            val gotValue = Data.getAny(bar) ?: return@forEach

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
                log("init error: ${e.message}")
                Vlog("init error: ${e.message}")
            }
        }

    }
    fun initFromFile(map: Map<Str, Str>) {
        val Data = getStoredData()
        var stop = no

        

        getClass(Bar).forEach { barIdk ->
            val bar = barIdk as ClassVar<Settings, Any?>

            if (stop) return@forEach

            try {
                val type = bar.returnType.classifier
                
                log("type $type")
                
                val outputRaw = map[bar.name]
                
                val valueNow = bar.get(Bar)
                
                val gotValue = StrToValue(valueNow, outputRaw)
                log("gotValue $gotValue")
                
            
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
                log("init error: ${e.message}")
                stop = yes
                Vlog("ERROR")
            }
        }
    }
}
