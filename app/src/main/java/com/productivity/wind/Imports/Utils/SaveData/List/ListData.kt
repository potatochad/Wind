package com.productivity.wind.Imports.Utils.SaveData.List

import com.productivity.wind.Imports.Utils.VarVal.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.String.*
import android.content.*
import kotlin.collections.*
import com.productivity.wind.Imports.Utils.*


class ListData(val listName: Str) {
	var lazyData = LazyData(listName)
	
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
		if (idItem.empty) return null
		val data = get(idItem) ?: return null

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










	
