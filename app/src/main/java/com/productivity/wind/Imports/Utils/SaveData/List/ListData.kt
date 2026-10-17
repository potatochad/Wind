package com.productivity.wind.Imports.Utils.SaveData.List

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.VarVal.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.String.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.Utils.ToX.*
import android.content.*
import kotlin.collections.*

//‼️ Fix the naming, its confusing
//idItem -> varsId
//listName -> varsName
class ListData(val listName: Str) {
	var lazyData = LazyData(listName)
	var varsList: VarsList? = null
	
	//for weird safety reason use get
	val prefs: SharedPreferences
        get() = lazyData.prefs

	//returns (should) vars String, the toStr gets rid of compile error 
	fun get(id: Str) = toStr(prefs.getString(id, "") ?: "")
	
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

	fun put(id: Str, varz: VarsList) {
		dataEdit.putString(id, "$varz").apply()
	}


	companion object {
        private val instances = mutableMapOf<Str, VarsList>()

        fun get(idItem: Str): VarsList =
            synchronized(instances) {//fancy thing (for thread safety)
                instances.getOrPut(listName) {
                    VarsList(get(idItem))
                }
            }
	}

	
	fun getVarValue(idItem: Str, varName: Str): Any? {
		if (idItem.empty) return null.also { 
			Vlog("ListData id empty")
		}
		val data = get(idItem) 
		
		if (get(idItem).empty){
			return null
			Vlog("ListData, get(id)-> is emptyy")
		}

		val worker = VarInfoWorker(data)
		val match = worker.findVar(data, varName) ?: return null

		val type = match.groupValues[1]
		val raw = match.groupValues[2]

		return worker.getVarValue(type, raw)
	}
	
}










	
