package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.String.*
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
import kotlin.properties.*
import org.json.JSONObject
import com.productivity.wind.Imports.UI_visible.*



fun encrypt(text: Str, key: Int) = text.map { (it.code + key).toChar() }.joinToString("")           
fun decrypt(text: Str, key: Int) = text.map { (it.code - key).toChar() }.joinToString("")



object AppData {
	val storageFile = "Data"

	val it: Str
    	get() {
           val prefs = App.getSharedPreferences(storageFile, Context.MODE_PRIVATE)

           val json = JSONObject()

           for ((key, value) in prefs.all) {
               json.put(key, value)
           }

           return json.toString()
    	}
		
	val prefs: SharedPreferences
        get() = App.getSharedPreferences(storageFile, Context.MODE_PRIVATE)
		
	fun deleteAll() { prefs.edit().clear().commit() }


	val json11 = Json { ignoreUnknownKeys = yes }
	inline fun <reified T> toJson(x: T) = json11.encodeToString(x)
	inline fun <reified T> decodeJson(x: Str) = json11.decodeFromString<T>(x)

	@Suppress("UNCHECKED_CAST")
	fun <T> getX(id: Str, default: T): T {
		return when (default) {
			is Int -> prefs.getInt(id, default) as T
			is Bool -> prefs.getBoolean(id, default) as T
			is Float -> prefs.getFloat(id, default) as T
			is Long -> prefs.getLong(id, default) as T
			is Str -> (prefs.getString(id, default) ?: default) as T
			else -> default
		}
	}
	fun <T> putX(id: Str, x: T) {
        val e = prefs.edit()

        when (x) {
            is Int -> e.putInt(id, x)
            is Bool -> e.putBoolean(id, x)
            is Float -> e.putFloat(id, x)
            is Long -> e.putLong(id, x)
            is Str -> e.putString(id, x)
            else -> return
        }

        e.apply()
	}

	
	
}






class VarDelegate<T>(
    private var value: T
) {
	lateinit var id: Str
	fun fancyId(x: KProperty<*>): Str {
		"${x.name}, autoId: ${autoId()}"
	}

    // 1) FIRST TIME CREATION (runs once when "by" is attached)
    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): VarDelegate<T> {
        log("Created: ${property.name}, autoId: ${autoId()}")
        return this
    }

    // 2) GET
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        log("Get: ${id}")
        return value
    }

    // 3) SET
    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
        log("Set: ${property.name} = $newValue")
        value = newValue
    }
}

// helper function (clean usage)
fun <T> varDelegate(initial: T) = VarDelegate(initial)


fun autoId(): Str {
	val e = Throwable().stackTrace[2]
	return "${e.fileName}:${e.lineNumber}"
}




var restoring by m(no)
inline fun <reified T> sList(
	id: Str,
    default: List<T> = emptyList(),
): SnapshotStateList<T> {
    val list = mList<T>()
	val Oldlist = mList<T>()


    Try("error with list saving") {
        val json = AppData.getX(id, "")
        if (json.notEmpty) {
			val loaded = Json.decodeFromString<List<T>>(json)
			list.addAll(loaded)
		} else {
			log("json is empty: $id")
		}


        Each(1000){
			if (!restoring) {
				val jsonOut = Json.encodeToString(list.toList())
				
				AppData.putX(id, jsonOut)
			}
        }
    }

    return list
}

fun <T> MutableList<T>.edit(item: T, block: T.() -> Unit) {
	Do {
		val index = this.indexOf(item)
		val itemCopy = this[index] // get the item
        this.removeAt(index)       // remove old item

        itemCopy.block()           // apply the changes directly

        this.add(index, itemCopy) 
	}
}


fun <T> s(default: T, id: Str = autoId()): m_<T> {
    var x = m(default) 

	Try("<fun s >, id: $id") {
		x = m(AppData.getX(id, default))

		x.onChange {
			AppData.putX(id, x.it)
		}
	}
	
    return x
}



fun <T> m_<T>.onChange(callback: Wait_<T>) {
    Do {
        snapshotFlow { 
			this@onChange.it
		}
		.collectLatest {
			Do {
				callback(it)
			}
        }
    }
}






@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
inline fun <reified T : Any> SnapshotStateList<T>.add(block: T.() -> Unit) {
    try {
        val newItem = T::class.java.getDeclaredConstructor().newInstance()
        newItem.block()

        this += newItem

    } catch (e: Exception) {
		Vlog("error: ${e.message}")
	}
}



class synch<T>(initial: T, var onChange: (T) -> Unit) : ReadWriteProperty<Any?, T> {
    private var state = mutableStateOf(initial)
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = state.value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        state.value = value
        onChange(value)
    }
}

    









@Composable
fun Backup(show: mBool) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult

        App.contentResolver.openOutputStream(uri)
            ?.bufferedWriter()
            ?.use { File ->
                AppData.prefs.all.forEach { (key, value) ->
                    val type = when (value) {
                        is Int -> "Int"
                        is Bool -> "Boolean"
                        is Float -> "Float"
                        is Long -> "Long"
                        else -> "String"
                    }
					val line = "$key|$type|$value"
					if (Bar.encryptedBackup) {
						File.write(encrypt(line, 132) + "\n")
					} else {
						File.write(line + "\n")
					}
                }
            }
    }

    RunOnce(show.it) {
        if (show.it) {
            launcher.launch("backup.txt")
            show.it = no
        }
    }
}


@Composable
fun Restore(show: mBool) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult

        Do {
            val editor = AppData.prefs.edit()

            App.contentResolver.openInputStream(uri)
                ?.bufferedReader()
                ?.useLines { lines ->
                    lines.forEach { lineEncrypted ->
						var line by m("")
						if (Bar.encryptedBackup) {
							line = decrypt(lineEncrypted, 132)
						} else {
							line = lineEncrypted
						}

						
                        val parts = line.split("|", limit = 3)
                        if (parts.size != 3) return@forEach

                        val key = parts[0]
                        val type = parts[1]
                        val value = parts[2]

                        when (type) {
                            "Int" -> editor.putInt(key, value.toInt())
                            "Boolean" -> editor.putBoolean(key, value.toBoolean())
                            "Float" -> editor.putFloat(key, value.toFloat())
                            "Long" -> editor.putLong(key, value.toLong())
                            else -> editor.putString(key, value)
                        }
                    }
                }
			restoring = yes

            editor.commit() 
			Vlog("Reloading App...")

			closeApp() 
        }
    }

    RunOnce(show.it) {
        if (show.it) {
            launcher.launch(arrayOf("text/plain"))
            show.it = no
        }
    }
}






















