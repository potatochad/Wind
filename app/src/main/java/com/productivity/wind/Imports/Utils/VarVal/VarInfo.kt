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
					dumbProcess(varsStr)
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

	// regex looks for this pattern:   x:y:c
	fun findVar(data: Str, varName: Str) =
	    Regex("""${Regex.escape(varName)}:([^:]+):("[^"]*"|[^,}]+)""").find(data)
	
	/*
	fun getVarValue(idItem: Str, varName: Str): Any? {
		if (idItem.empty) return null
		val data = get(idItem) ?: return null

		val worker = VarInfoWorker()
		val match = worker.findVar(data, varName) ?: return null

		val type = match.groupValues[1]
		val raw = match.groupValues[2]

		return worker.getVarValue(type, raw)
	}
	*/
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
	//‼️‼️‼️TODO FOR YOU (have classes in one file:
	//MAKE A CLASS FOR: VarList:
	//have it run some logic and so on
	//MAKE A CLASS FOR VARINFO like thingy:
	//SORYY you already got that, so have it like compute stuff for youu
	
	/*
	fun getVar(varName: Str): Any? {
		val clazz = StrToClass(type) 
		if (clazz == null){
			Vlog("getVarValue ClassError: $type, $raw, $clazz")
			return null
		}
		val value = toValueOrNull(clazz, raw)
		if (value == null) Vlog("getVarValue ValueError: $type, $raw, $clazz, $value")
		return value
	}
	*/


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


	
	//it dumb: so values are STRING
	fun dumbProcess(strData: Str): List<Var<Str>>{
		var varsStr = InsideBraces(strData) ?: return emptyList()
		val result = mList<Var<Str>>()
		
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
				result += Var(
					name = parts[0],
					value = parts.drop(2).joinToString(":"),
					type = String::class.java,
					typeStr = parts[1]
				)
			}
		}
		return result
	}


	
}




class VarsList(
	val vars: List<Var<*>>
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

		vars.forEach { variable ->
			val parts = SplitTopLevel(
				variable,
				split = ':',
				deeper = listOf("()", "{}", "[]")
			)

			if (parts.size >= 3) {
				result += Var(
					name = parts[0],
					value = parts.drop(2).joinToString(":"),
					type = String::class.java,
					typeStr = parts[1]
				)
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
    val typeStr: Str = type?.name ?: "null",

	var corrupted: Bool = no,
){
	constructor(varStr: Str) : this(
    name = getName(varStr) ?: error("Invalid VarInfo: $varStr"),
    value = (getStrValue(varStr) ?: error("Invalid VarInfo: $varStr")) as T,
    type = String::class.java,
    typeStr = getTypeStr(varStr) ?: error("Invalid VarInfo: $varStr")
)
	/*
	constructor(varStr: Str) : this<String>(
		name = "testName",
		value = "stringValue",
		type = String::class.java,
		typeStr = "null",
		/*
        name = getName(varStr) ?: error("Invalid VarInfo: $varStr"),
        value = getStrValue(varStr) ?: error("Invalid VarInfo: $varStr"),
        type = String::class.java,
        typeStr = getTypeStr(varStr) ?: error("Invalid VarInfo: $varStr")
		*/
    )
	*/

    companion object {
        val regex = Regex("""^([^:]+):([^:]+):(.*)$""")

        fun getName(str: Str) = regex.group(str, 1)
        fun getStrValue(str: Str) = regex.group(str, 3)
        fun getTypeStr(str: Str) = regex.group(str, 2)
	}
	
	val isNull get() = value == null
    val isChanged get() = changed
    val hasType get() = type != null

	override fun toString(): Str = "$name:$typeStr:${ComplexTypeToStr(value)}"
	
}
/*
companion object {
    fun parse(data: Str): VarInfo<*>? {
        val match = Regex(
            """^([^:]+):([^:]+):(.*)$"""
        ).matchEntire(data) ?: return null

        val name = match.groupValues[1]
        val typeStr = match.groupValues[2]
        val raw = match.groupValues[3]

        val clazz = StrToClass(typeStr) ?: return null
        val value = toValueOrNull(clazz, raw) ?: return null

        return VarInfo(name, value, clazz)
    }
}

*/

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



