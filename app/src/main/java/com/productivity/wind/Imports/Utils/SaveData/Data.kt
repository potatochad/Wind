package com.productivity.wind.Imports.Utils.SaveData

import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.ToX.*
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
import kotlinx.coroutines.flow.*
import kotlinx.serialization.builtins.ListSerializer

fun Id() = UUID.randomUUID().toString()

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
	fun <T> toJson(x: T, serializer: KSerializer<T>) = json11.encodeToString(serializer, x)
	inline fun <reified T> decodeJson(x: Str) = json11.decodeFromString<T>(x)


	
	fun hasKey(x: Str) = prefs.hasKey(x)

	@Suppress("UNCHECKED_CAST")
	fun <T> get(id: Str, x: T): T {
		return when (x) {
			is Int -> prefs.getInt(id, x) as T
			is Bool -> prefs.getBoolean(id, x) as T
			is Float -> prefs.getFloat(id, x) as T
			is Long -> prefs.getLong(id, x) as T
			is Str -> (prefs.getString(id, x) ?: x) as T
			else -> {
				Vlog("Cant get a complex type: $id, [ ${x.type} ]")
				x
			}
		}
	}

	
	fun <T> put(id: Str, x: T, Do: (SharedPreferences.Editor) -> Unit = { it.apply() }) {
        val e = prefs.edit()
        when (x) {
            is Int -> e.putInt(id, x)
            is Bool -> e.putBoolean(id, x)
            is Float -> e.putFloat(id, x)
            is Long -> e.putLong(id, x)
            is Str -> e.putString(id, x)
            else -> {
				Vlog("Cant save a complex type: $id, [ ${x.type} ]")
				return
			}
        }
		Do(e)
	}
	fun <T> commit(id: Str, x: T) = put(id, x, { it.commit() })
	fun <T> apply(id: Str, x: T) = put(id, x)


	

	inline fun <reified T> saveList(id: Str, list: List<T>) {
		try {
			if(!list.isRecomposable) Vlog("Warning: saveList and getList work with snapshotList only")
			put(id, toJson(list))
		} catch (e: Exception) {
			Vlog("Cant save list: $id, [ ${T::class} ] -> ${e.message}")
		}
	}
	fun <T> saveList(id: Str, list: List<T>, serializer: KSerializer<List<T>>) {
		try {
			if (!list.isRecomposable) Vlog("Warning: saveList and getList work with snapshotList only")

			put(id, toJson(list, serializer))
		} catch (e: Exception) {
			Vlog("Cant save list: $id -> ${e.message}")
		}
	}
	
	inline fun <reified T> getList(id: Str): SnapshotStateList<T> {
		return try {
			val json = get(id, "")
			if (json.isBlank()) {
				mutableStateListOf()
			} else {
				decodeJson<List<T>>(json).toMutableStateList()
			}
		} catch (e: Exception) {
			Vlog("Cant load list: $id, [ ${T::class} ] -> ${e.message}")
			mutableStateListOf()
		}
	}

	
	
}





var idList = mList<Str>()
fun <T> s(
	default: T,
	idExtra: Str = "",
): By<T> {
	val delegate = By(default)
	var goodId by m("")
	var badId by m(no)
	
	delegate
		.onBuild{ prop, id, delegateIt ->
			goodId = "$id: $idExtra"
			
			badId = idList.has(goodId)
			idList.add(goodId)
			if (badId) Vlog("Duplicate id detected: $goodId")
			else delegateIt = AppData.get(goodId, default)
		}
		.onSet{ prop, id, newValue ->
			if (!badId) AppData.put(goodId, newValue)
		}
	return delegate
}


inline fun <reified T> sList(
    id: Str,
    default: List<T> = emptyList(),
): SnapshotStateList<T> {

    val list = mList<T>()
	
    try {
        val json = AppData.get(id, "")

        if (json.notEmpty) {
            val loaded = Json.decodeFromString<List<T>>(json)
            list.addAll(loaded)
        } else {
            list.addAll(default)
        }

    } catch (e: Exception) {
        Vlog("Error loading list [$id]: ${e.message}")
        list.addAll(default)
    }

	Do(eLog = "error saving list $id") {
		snapshotFlow { Json.encodeToString(list.toList()) }
		.distinctUntilChanged()
        .debounce(1200)
		.collectLatest { jsonOut ->
            try {
				// log("SAVING $id")
                AppData.put(id, jsonOut)

            } catch (e: Exception) {
				Vlog("Error saving list [$id]: ${e.message}")
            }
		}
    }

    return list
}


fun <T> SnapshotStateList<T>.edit(item: T, block: T.() -> Unit) {
	try {
		val index = this.indexOf(item)
		val itemCopy = this[index] // get the item
        this.removeAt(index)       // remove old item

        itemCopy.block()           // apply the changes directly

        this.add(index, itemCopy) 
	} catch (e: Exception) {
		Vlog("error editting list: ${e.message}: [ $this:$item ]")
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

    







fun encrypt(text: Str, key: Int) = text.map { (it.code + key).toChar() }.joinToString("")           
fun decrypt(text: Str, key: Int) = text.map { (it.code - key).toChar() }.joinToString("")


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

            editor.commit() 
			Vlog("Reloading App...")

			CloseMyApp() 
        }
    }

    RunOnce(show.it) {
        if (show.it) {
            launcher.launch(arrayOf("text/plain"))
            show.it = no
        }
    }
}
