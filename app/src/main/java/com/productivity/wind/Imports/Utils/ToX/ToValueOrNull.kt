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








