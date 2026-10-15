package com.productivity.wind.Imports.Utils.SaveData.List

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.VarVal.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.String.*
import com.productivity.wind.Imports.Utils.*
import android.content.*
import kotlin.collections.*


class ListData(val listName: Str) {
	companion object {
        private val instances = mutableMapOf<Str, VarsList>()

        fun get(listName: Str): VarsList =
            synchronized(instances) {//fancy thing (for thread safety)
                instances.getOrPut(listName) {
                    
                }
            }
	}
	
	var lazyData = LazyData(listName)
	var varsList: VarsList? = null
	
	//for weird safety reason use get
	val prefs: SharedPreferences
        get() = lazyData.prefs
	
	fun get(id: Str) = prefs.getString(id, null)
	
	val all: Map<Str, Any?>
        get() = prefs.all

	val dataEdit get() = prefs.edit()


	fun each(Do: (Str, Any?) -> Unit) {
		prefs.all.forEach { (key, value) ->
			Do(key, value)
		}
	}

	fun deleteAll() = dataEdit.clear().apply()
	fun remove(id: Str) = dataEdit.remove(id).apply()

	fun hasKey(x: Str) = prefs.hasKey(x)
	fun find(match: (Str) -> Bool) = prefs.all.filter { (key, _) -> match(key) }


	
	fun getVarValue(idItem: Str, varName: Str): Any? {
		if (idItem.empty) return null.also { 
			Vlog("ListData id empty")
		}
		val data = get(idItem) ?: return null.also {
			Vlog("ListData, data = get(id)-> is null")
		}

		val worker = VarInfoWorker(data)
		val match = worker.findVar(data, varName) ?: return null

		val type = match.groupValues[1]
		val raw = match.groupValues[2]

		return worker.getVarValue(type, raw)
	}
	
	
	fun put(id: Str, varz: VarsList) {
		dataEdit.putString(id, "$varz").apply()
	}
	
	
}










	
