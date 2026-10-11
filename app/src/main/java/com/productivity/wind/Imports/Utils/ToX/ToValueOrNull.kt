package com.productivity.wind.Imports.Utils.VarVal

import kotlin.collections.*
import com.productivity.wind.Imports.Utils.*

fun Str.toEnumOrNull(clazz: Class<*>): Any? =
    clazz.enumConstants?.firstOrNull { (it as Enum<*>).name == this }

fun toValueOrNull(clazz: Class<*>, raw: Str): Any? {
    return when {
        isString(clazz) -> raw.removeSurrounding("\"")//removes any "", "hello"  →  hello        
        isInteger(clazz) -> raw.toIntOrNull()
        isBoolean(clazz) -> raw.toBooleanStrictOrNull()
        isLong(clazz) -> raw.toLongOrNull()
        isDouble(clazz) -> raw.toDoubleOrNull()
        isFloat(clazz) -> raw.toFloatOrNull()
        isEnum(clazz) -> raw.toEnumOrNull(clazz)
        else -> null
    }
}








