package com.productivity.wind.Imports.Utils.SaveData

import com.productivity.wind.Imports.Utils.VarVal.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.String.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.reflect.*
import java.util.UUID
import kotlin.collections.*
import androidx.compose.runtime.getValue
import androidx.annotation.RequiresApi
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import com.productivity.wind.Imports.Utils.*
import kotlinx.coroutines.flow.*
import kotlin.properties.*
import org.json.JSONObject

fun Id() = UUID.randomUUID().toString()

//basic data (‼️Dont change storage location)
private val basicTo = "Data"

//treat as global objects
val AppData = LazyData()

class LazyData(Where: Str = basicTo) {
	val saveTo = Where

	val prefs: SharedPreferences
        get() = App.getSharedPreferences(saveTo, Context.MODE_PRIVATE)

	val dataEdit get() = prefs.edit()
	
	fun each(Do: (Str, Any?) -> Unit) {
		prefs.all.forEach { (key, value) ->
			Do(key, value)
		}
	}

	val it: Str
    	get() {
           val json = JSONObject()

           each { key, value ->
               json.put(key, value)
           }

           return json.toString()
    	}
	val all: Map<Str, Any?>
        get() = prefs.all
		
	
	fun deleteAll() = dataEdit.clear().apply()
	fun remove(id: Str) = dataEdit.remove(id).apply()

	fun hasKey(x: Str) = prefs.hasKey(x)
	fun find(match: (Str) -> Bool) = prefs.all.filter { (key, _) -> match(key) }
	
	val json11 = Json { ignoreUnknownKeys = yes }
	inline fun <reified T> toJson(x: T) = json11.encodeToString(x)
	fun <T> toJson(x: T, serializer: KSerializer<T>) = json11.encodeToString(serializer, x)
	inline fun <reified T> decodeJson(x: Str) = json11.decodeFromString<T>(x)


	
	

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
        val e = dataEdit
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
				mStateList()
			} else {
				decodeJson<List<T>>(json).toMutableStateList()
			}
		} catch (e: Exception) {
			Vlog("Cant load list: $id, [ ${T::class} ] -> ${e.message}")
			mStateList()
		}
	}
}



var idList = mutableListOf<Str>()
fun <T> s(
	default: T,
	idExtra: Str = "",
): By<T> {
	val delegate = By(default)
	var goodId by mState("")
	var badId by mState(no)
	
	delegate
		.onBuild{ prop, id, mValue ->
			goodId = "$id: $idExtra"
			
			badId = idList.has(goodId)
			idList.add(goodId)
			if (badId) Vlog("Duplicate id detected: $goodId")
			else mValue.it = AppData.get(goodId, default)
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

    val list = mutableStateListOf<T>()
	
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

    

