package com.productivity.wind.Imports.Utils.VarVal

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.String.*
import kotlinx.coroutines.*
import kotlin.collections.*
import com.productivity.wind.Imports.Utils.*


class VarInfoWorker(val varsStr: Str){
	private var dumbVars = MapVarInfo()
	var map = MapVarInfo()

	init {
			appNonUIScope.launch {
				dumbVars.addAll(
					//returns dumb vars
					VarsList(varsStr).vars
				)
				dumbVars.each { name, it ->
					val processed: Var<*>? = processVar(it)
					if (processed == null) {
						Vlog("ExcludingVar: Error trying processVar")
					} else {
						map.add(processed)
					}
				}
				
			}
	}

	fun findVar(data: Str, varName: Str) =
	    Regex("""${Regex.escape(varName)}:([^:]+):("[^"]*"|[^,}]+)""").find(data)

	fun getVarValue(type: Str, raw: Str): Any? {
		val clazz = StrToClass(type) 
		if (clazz == null){
			Vlog("getVarValue ClassError: $type, $raw, $clazz")
			return null
		}
		val value = toValueOrNull(clazz, raw)
		if (value == null) Vlog("getVarValue ValueError: $type, $raw, $clazz, $value")
		return value
	}

	fun processVar(dumbVar: Var<*>): Var<*>? {
		val raw = dumbVar.value
		val type = dumbVar.typeStr
		val name = dumbVar.name

		val clazz = StrToClass(type)
		if (clazz == null) {
			Vlog("process ClassError: $type, $raw, $clazz")
			return null
		}
				
		val value = toValueOrNull(clazz, toStr(raw))
				
		if (value == null) {
			Vlog("process ValueError: $type, $raw, $clazz, $value")
			return null
		}
	
		return Var(name, value, clazz)
	}
	
}




class VarsList(
	val vars: List<Var<*>>,
	var smartProcess: Bool = no,
) {
	constructor(map: MapVarInfo) : this(map.toList())

    override fun toString(): Str {
        val varsStr = vars.joinToString(", ") { "$it" }
        return "vars: { $varsStr }"
    }

	constructor(varsStr: Str) : this(
		dumbProcess(varsStr)
	)


	//it dumb: so values are STRING
	companion object {
		fun dumbProcess(strData: Str): List<Var<Str>>{
			var varsStr = InsideBraces(strData) ?: return emptyList()
			val result = mList<Var<Str>>()
		
			val vars = SplitTopLevel(
				varsStr,
				split = ',',
				deeper = listOf("()", "{}", "[]")
			)

			vars.forEach {
				try {
					result += Var(it)
				} catch (e: Exception) {
					Vlog("VarsList, error with var, skipping: [$it]")
				}
			}
			
			return result
		}
	}
}



class Var<T>(
	val name: Str,
    val value: T,
	val type: Class<*>? = value?.let { it::class.java },
	
	var changed: Bool = no,
	var marker: Int = 0,
	var dumb: Bool = no,
    val typeStr: Str = type?.name ?: "null",

	var corrupted: Bool = no,
){
	//it dumb: so values are STRING
	//PUT INTO IT   "X:Y:C" ONLYY
	constructor(varStr: Str) : this(
		name = getName(varStr) ?: error("Invalid VarInfo: $varStr"),
		value = (getStrValue(varStr) ?: error("Invalid VarInfo: $varStr")) as T,
		type = String::class.java,
		typeStr = getTypeStr(varStr) ?: error("Invalid VarInfo: $varStr"),
		dumb = yes
	)

    companion object {
        val regex = Regex("""^([^:]+):([^:]+):(.*)$""")

        fun getName(str: Str) = regex.group(str, 1)
        fun getStrValue(str: Str) = regex.group(str, 3)
        fun getTypeStr(str: Str) = regex.group(str, 2)

		fun smart(){
			val raw = value
			val type = typeStr

			val clazz = StrToClass(type)
			if (clazz == null) {
				Vlog("process ClassError: $type, $raw, $clazz")
				return null
			}
				
			val value = toValueOrNull(clazz, toStr(raw))
				
			if (value == null) {
				Vlog("process ValueError: $type, $raw, $clazz, $value")
				return null
			}
			return Var(name, value, clazz)
		}
	}
	
	val isNull get() = value == null
    val isChanged get() = changed
    val hasType get() = type != null

	override fun toString(): Str = "$name:$typeStr:${ComplexTypeToStr(value)}"
	
}


class MapVarInfo {
    private val map = mutableMapOf<Str, Var<*>>()

    operator fun get(name: Str) = map[name]

    operator fun set(name: Str, info: Var<*>) {
        map[name] = info
    }

	fun <T> add(name: Str, value: T, changed: Bool = no) {
        map[name] = Var(name, value, null, changed)
	}
	fun add(info: Var<*>) {
        map[info.name] = info
	}
	

	fun <T> edit(name: Str, value: T) {
        map[name] = Var(name, value)
	}

	fun editAllChanged(edit: (Str, Var<*>) -> Var<*>) {
		map.entries
			.filter { it.value.changed }
			.forEach { (name, info) ->
				map[name] = edit(name, info)
			}
	}

	fun addAll(infos: List<Var<*>>) {
		infos.forEach { add(it) }
	}



	fun each(Do: (Str, Var<*>) -> Unit) {
		map.forEach { (name, info) ->
			Do(name, info)
		}
	}
	

    fun remove(name: Str) = map.remove(name)

    fun clear() = map.clear()

    fun contains(name: Str) = name in map

	fun toList() = map.values.toList()

	val empty: Bool
    	get() = map.isEmpty()
}



