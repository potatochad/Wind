package com.productivity.wind.Imports.Utils.ToX

import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.String.*
import android.util.*
import kotlin.collections.*
import kotlin.reflect.full.*
import com.productivity.wind.Imports.Utils.*
import com.google.android.gms.maps.model.*


fun toStr(it: Any?): Str = when (it) {
    is Str -> it
    is UIStr -> it.text
    is LatLng -> "${it.latitude},${it.longitude}"
    is Pair<*, *> -> "${it.first},${it.second}"
    is Double, is Float, is Int, is Long -> it.toString()
    is List<*> -> it.joinToString("\n") { it.toString() }
    null -> ""
    else -> it.toString()
}




private val fieldsCache = mutableMapOf<Class<*>, List<java.lang.reflect.Field>>()

// SLOWWWW
fun ComplexTypeToStr(value: Any?): Str {
    fun listToStr(list: List<*>): String {
        val type = list.firstOrNull()?.let {
            if (it.commonType()) it.javaClass.simpleName
            else it.javaClass.simpleName
        } ?: "?"

        val values = list.joinToString(", ") {
            ComplexTypeToStr(it)
        }

        return "List<$type>[$values]"
    }
    
    fun setToStr(set: Set<*>): Str =
       "Set[${set.joinToString(", ") { ComplexTypeToStr(it) }}]"

    fun mapToStr(map: Map<*, *>): Str =
       "Map[" + map.entries.joinToString(", ") {
           "${ComplexTypeToStr(it.key)}=${ComplexTypeToStr(it.value)}"
       } + "]"

    fun arrayToStr(array: Any): String {
        val values = List(java.lang.reflect.Array.getLength(array)) {
            java.lang.reflect.Array.get(array, it)
        }

        val type = values.firstOrNull()?.javaClass?.simpleName ?: "?"

        return "Array<$type>[${values.joinToString(", ") { ComplexTypeToStr(it) }}]"
    }


    if (value == null) return "null"
    if (value.isArray()) return arrayToStr(value)

    when (value) {
        is List<*> -> return listToStr(value)
        is Set<*> -> return setToStr(value)
        is Map<*, *> -> return mapToStr(value)
    }

    if (value.commonType()) {
        return when (value) {
            is String -> "\"$value\""
            is Enum<*> -> value.name
            else -> value.toString()
        }
    }

    val fields = getFields(value.javaClass)
        .joinToString(", ") { field ->
            "${field.name}=${ComplexTypeToStr(field.get(value))}"
        }

    return "${value.javaClass.simpleName}($fields)"
}


fun getFields(clazz: Class<*>): List<java.lang.reflect.Field> {
    return fieldsCache.getOrPut(clazz) {
        val order = clazz.kotlin.primaryConstructor?.parameters?.map { it.name } ?: emptyList()

        clazz.declaredFields
            .filter { 
                !it.isSynthetic &&
                !java.lang.reflect.Modifier.isStatic(it.modifiers) &&
                it.name in order
            }
            .sortedBy { order.indexOf(it.name) }
            .onEach { it.isAccessible = true }
    }
}





fun toListStr(it: Any?): ListStr = when (it) {      
    is List<*> -> it.map { toStr(it) }              
    is Str -> it.lines()                        
    is UIStr -> it.text.lines()          
    null -> emptyList()
    else -> listOf(toStr(it))
}



