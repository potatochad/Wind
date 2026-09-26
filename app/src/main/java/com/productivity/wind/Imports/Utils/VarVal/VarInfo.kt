package com.productivity.wind.Imports.Utils.VarVal

import com.productivity.wind.Imports.Utils.SaveData.List.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.SaveData.*
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
import android.os.*
import android.content.*
import android.util.*
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
import android.widget.Toast
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import android.content.*
import java.lang.reflect.ParameterizedType
import android.content.Intent
import com.productivity.wind.Imports.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.text.*
import com.productivity.wind.Imports.UI_visible.*

	
class VarInfoWorker(){
	var dumbVars = MapVarInfo()
	
	fun getVarValue(type: Str, raw: Str): Any? {
		val clazz = StrToClass(type) ?: return null

		return when {
			isString(clazz) -> raw.removeSurrounding("\"")
			isInteger(clazz) -> raw.toIntOrNull()
			isBoolean(clazz) -> raw.toBooleanStrictOrNull()
			isLong(clazz) -> raw.toLongOrNull()
			isDouble(clazz) -> raw.toDoubleOrNull()
			isFloat(clazz) -> raw.toFloatOrNull()
			isEnum(clazz) -> getEnumValue(clazz, raw)
			//isMyAppClass(clazz) -> getComplexValue(clazz, raw)
			else -> null
		}
	}


	//it dumb: so values are STRING
	fun dumbProcess(strData: Str): List<VarInfo<Str>>{
		var varsStr = InsideBraces(strData) ?: return emptyList()
		val result = mList<VarInfo<Str>>()
		
		val vars = SplitTopLevel(
			varsStr,
			split = ',',
			deeper = listOf("()", "{}", "[]")
		)

		vars.forEach { variable ->
			val parts = SplitTopLevel(
				variable,
				split = ':',
				deeper = listOf("()", "{}", "[]")
			)

			if (parts.size >= 3) {
				result += VarInfo(
					name = parts[0],
					value = parts.drop(2).joinToString(":"),
					//TEMPORARY placeholder
					type = String::class.java,// parts[1],
					typeStr = parts[1]
				)
			}
		}
		return result
	}


	
}






data class VarInfo<T>(
	val name: Str,
    val value: T,
	var changed: Bool = no,
    val type: Class<*>? = value?.let { it::class.java },
	val typeStr: Str = type?.name ?: "null",
)

class MapVarInfo {
    private val map = mutableMapOf<Str, VarInfo<*>>()

    operator fun get(name: Str) = map[name]

    operator fun set(name: Str, info: VarInfo<*>) {
        map[name] = info
    }

	fun <T> add(name: Str, value: T, changed: Bool = no) {
        map[name] = VarInfo(name, value, changed)
	}
	fun add(info: VarInfo<*>) {
        map[info.name] = info
	}
	

	fun <T> edit(name: Str, value: T) {
        map[name] = VarInfo(name, value)
	}

	fun editAllChanged(edit: (Str, VarInfo<*>) -> VarInfo<*>) {
		map.entries
			.filter { it.value.changed }
			.forEach { (name, info) ->
				map[name] = edit(name, info)
			}
	}

	fun addAll(infos: List<VarInfo<*>>) {
		infos.forEach { add(it) }
	}


	
	

    fun remove(name: Str) = map.remove(name)

    fun clear() = map.clear()

    fun contains(name: Str) = name in map

	fun toList() = map.values.toList()
}



