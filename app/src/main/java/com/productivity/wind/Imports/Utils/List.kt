package com.productivity.wind.Imports.Utils

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
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import android.content.*
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import android.content.Intent
import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.layout.*
import android.graphics.drawable.Drawable
import android.content.pm.*
import com.productivity.wind.Imports.*
import java.util.*
import com.productivity.wind.R
import kotlin.reflect.full.*
import androidx.compose.ui.focus.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import androidx.compose.ui.geometry.*
import androidx.compose.ui.text.style.*
import androidx.compose.foundation.lazy.*
import java.util.*
import kotlin.concurrent.*
import androidx.annotation.RequiresApi
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import androidx.compose.foundation.text.selection.*
import kotlin.system.*
import androidx.navigation.*
import android.webkit.*
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.cli.common.ExitCode
import com.productivity.wind.Imports.Utils.*
import android.location.*
import androidx.core.content.*
import androidx.compose.ui.text.*
import androidx.navigation.compose.*
import android.util.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.location.*
import android.content.*
import android.net.*
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlin.properties.*
import org.json.JSONObject
import com.productivity.wind.Imports.UI_visible.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.builtins.ListSerializer



fun <T> CustomOverrideList(
    items: Collection<T> = emptyList(),

    add: (MutableList<T>.(T) -> Bool)? = null,
    addAt: (MutableList<T>.(Int, T) -> Unit)? = null,
    addAll: (MutableList<T>.(Collection<T>) -> Bool)? = null,
    addAllAt: (MutableList<T>.(Int, Collection<T>) -> Bool)? = null,

    clear: (MutableList<T>.() -> Unit)? = null,

    get: (MutableList<T>.(Int) -> T)? = null,

    remove: (MutableList<T>.(T) -> Bool)? = null,
    removeAt: (MutableList<T>.(Int) -> T)? = null,
    removeAll: (MutableList<T>.(Collection<T>) -> Bool)? = null,

    set: (MutableList<T>.(Int, T) -> T)? = null,

    contains: (MutableList<T>.(T) -> Bool)? = null,
    containsAll: (MutableList<T>.(Collection<T>) -> Bool)? = null,

    indexOf: (MutableList<T>.(T) -> Int)? = null,
    lastIndexOf: (MutableList<T>.(T) -> Int)? = null,

    isEmpty: (MutableList<T>.() -> Bool)? = null,

    toString: (MutableList<T>.() -> Str)? = null
): mList<T> {
    val list = MutableList<T>().apply {
        addAll(items)
    }
    
    return object : MutableList<T> {
        override fun add(element: T) =
            add.doOr(list, element){ list.add(element) }

        override fun add(index: Int, element: T) {
            addAt.doOr(list, index, element){ list.add(index, element) }
        }

        override fun addAll(elements: Collection<T>) =
            addAll.doOr(list, elements){ list.addAll(elements) }

        override fun addAll(
            index: Int,
            elements: Collection<T>
        ) =
            addAllAt.doOr(list, index, elements){ list.addAll(index, elements) }
            

        override fun clear() {
            clear.doOr(list){ list.clear() }
            
        }

        override fun get(index: Int) =
            get.doOr(list, index){ list[index] }
        

        override fun remove(element: T) =
            remove.doOr(list, element){ list.remove(element) }

        override fun removeAt(index: Int) =
            removeAt.doOr(list, index){ list.removeAt(index) }
        

        override fun removeAll(elements: Collection<T>) =
            removeAll.doOr(list, elements){ list.removeAll(elements) }
        

        override fun set(index: Int, element: T) =
            set.doOr(list, index, element){ list.set(index, element) }
        

        override fun contains(element: T) =
            contains.doOr(list, element){ list.contains(element) }
        

        override fun containsAll(elements: Collection<T>) =
            containsAll.doOr(list, elements){ list.containsAll(elements) }
        

        override fun indexOf(element: T) =
            indexOf.doOr(list, element){ list.indexOf(element) }
        

        override fun lastIndexOf(element: T) =
            lastIndexOf.doOr(list, element){ list.lastIndexOf(element) }
        

        override fun isEmpty() =
            isEmpty.doOr(list){ list.isEmpty() }
        
        override fun toString() =
            toString.doOr(list){ toStr(list) }
        

        override val size get() = list.size
        override fun iterator() = list.iterator()
        override fun listIterator() = list.listIterator()
        override fun listIterator(index: Int) = list.listIterator(index)
        override fun subList(fromIndex: Int, toIndex: Int) = list.subList(fromIndex, toIndex)
        override fun retainAll(elements: Collection<T>) = list.retainAll(elements)
    }
}

fun <T> CustomAlertList(
    items: Collection<T> = emptyList(),

    onAdd: MutableList<T>.(T) -> Unit = {},
    onAddAt: MutableList<T>.(Int, T) -> Unit = { _, _ -> },
    onAddAll: MutableList<T>.(Collection<T>) -> Unit = {},
    onAddAllAt: MutableList<T>.(Int, Collection<T>) -> Unit = { _, _ -> },

    onClear: MutableList<T>.() -> Unit = {},

    onGet: MutableList<T>.(Int, T) -> Unit = { _, _ -> },

    onRemove: MutableList<T>.(T) -> Unit = {},
    onRemoveAt: MutableList<T>.(Int, T) -> Unit = { _, _ -> },
    onRemoveAll: MutableList<T>.(Collection<T>) -> Unit = {},

    onSet: MutableList<T>.(Int, T) -> Unit = { _, _ -> },

    onContains: MutableList<T>.(T) -> Unit = {},
    onContainsAll: MutableList<T>.(Collection<T>) -> Unit = {},

    onIndexOf: MutableList<T>.(T, Int) -> Unit = { _, _ -> },
    onLastIndexOf: MutableList<T>.(T, Int) -> Unit = { _, _ -> },

    onEmptyCheck: MutableList<T>.(Bool) -> Unit = {},

    onToString: MutableList<T>.(Str) -> Unit = {}
): MutableList<T> {

    val list = MutableList<T>().apply {
        addAll(items)
    }

    return object : MutableList<T> {

        override fun add(element: T): Bool {
            val result = list.add(element)
            if (result) list.onAdd(element)
            return result
        }

        override fun add(index: Int, element: T) {
            list.add(index, element)
            list.onAddAt(index, element)
        }

        override fun addAll(elements: Collection<T>): Boolean {
            val result = list.addAll(elements)
            if (result) list.onAddAll(elements)
            return result
        }

        override fun addAll(index: Int, elements: Collection<T>): Boolean {
            val result = list.addAll(index, elements)
            if (result) list.onAddAllAt(index, elements)
            return result
        }

        override fun clear() {
            list.clear()
            list.onClear()
        }

        override fun get(index: Int): T {
            val value = list[index]
            list.onGet(index, value)
            return value
        }

        override fun remove(element: T): Boolean {
            val result = list.remove(element)
            if (result) list.onRemove(element)
            return result
        }

        override fun removeAt(index: Int): T {
            val value = list.removeAt(index)
            list.onRemoveAt(index, value)
            return value
        }

        override fun removeAll(elements: Collection<T>): Boolean {
            val result = list.removeAll(elements)
            if (result) list.onRemoveAll(elements)
            return result
        }

        override fun set(index: Int, element: T): T {
            val old = list.set(index, element)
            list.onSet(index, element)
            return old
        }

        override fun contains(element: T): Boolean {
            val result = list.contains(element)
            list.onContains(element)
            return result
        }

        override fun containsAll(elements: Collection<T>): Boolean {
            val result = list.containsAll(elements)
            list.onContainsAll(elements)
            return result
        }

        override fun indexOf(element: T): Int {
            val result = list.indexOf(element)
            list.onIndexOf(element, result)
            return result
        }

        override fun lastIndexOf(element: T): Int {
            val result = list.lastIndexOf(element)
            list.onLastIndexOf(element, result)
            return result
        }

        override fun isEmpty(): Boolean {
            val result = list.isEmpty()
            list.onEmptyCheck(result)
            return result
        }

        override fun toString(): String {
            val result = list.toString()
            list.onToString(result)
            return result
        }

        override val size
            get() = list.size

        override fun iterator() = list.iterator()

        override fun listIterator() = list.listIterator()

        override fun listIterator(index: Int) = list.listIterator(index)

        override fun subList(
            fromIndex: Int,
            toIndex: Int
        ) = list.subList(fromIndex, toIndex)

        override fun retainAll(elements: Collection<T>): Boolean {
            return list.retainAll(elements)
        }

        override fun equals(other: Any?) = list == other

        override fun hashCode() = list.hashCode()
    }
}




inline fun <T, R : Comparable<R>> Iterable<T>.max(selector: (T) -> R): R? =
    maxOfOrNull(selector)

inline fun <T, R : Comparable<R>> Iterable<T>.min(selector: (T) -> R): R? =
    minOfOrNull(selector)


fun <T> MutableList<T>.keep(max: Int) {
    if (size > max) {
        subList(0, size - max).clear()
    }
}

    
@Composable
fun <T> List<T>.onDelete(
    onDelete: (List<T>) -> Unit
): List<T> {
    var previous by r(emptyList<T>())
    var deleted by r(emptyList<T>())

    LaunchedEffect(this) {
        snapshotFlow { this@onDelete.toList() }
            .collect { now ->
                val removed =
                    if (now.size < previous.size) {
                        previous - now.toSet()
                    } else emptyList()

                if (removed.notEmpty) {
                    onDelete(removed)
                    deleted = removed
                }

                previous = now
            }
    }

    return deleted
}


@Composable
fun <T> List<T>.rNewItems(): List<T> {
    var previous by r(emptyList<T>())
    var result by r(emptyList<T>())

    RunOnce(this.toList()) {
        snapshotFlow { this@rNewItems.toList() }
            .collect { now ->
                val newItems =
                    if (now.size < previous.size) {
                        now // reset
                    } else {
                        now.drop(previous.size)
                    }
                result = newItems
                previous = now
            }
    }

    return result
}


fun Any?.isArray(): Bool =
    this?.javaClass?.isArray == true


val List<*>.isRecomposable: Bool
    get() = this is SnapshotStateList<*>

inline fun <reified T> isSerializable(): Bool {
    return try {
        serializer<T>()
        true
    } catch (_: Exception) {
        false
    }
}
    
val Any?.tempId: Str
    get() = "${this?.javaClass?.simpleName ?: "null"}@${System.identityHashCode(this)}"

val List<*>.sameType: Bool
    get() {
        if (size <= 1) return true
        val type = firstOrNull()?.javaClass
        return all { it?.javaClass == type }
    }

val List<*>.itemKType: KClass<*>?
    get() = if (sameType) firstOrNull()?.let { it::class } else null

val KClass<*>.isDataClass: Bool
    get() = this.isData

val KClass<*>.isSimpleClass: Bool
    get() =
        !isData &&
        !isSealed &&
        !java.isInterface &&
        !java.isEnum

fun KClass<*>.hasId(): Bool {
    if (!this.isData) return no
    return this.props.any { it.name == "id" }
}
fun KClass<*>.hasProp(name: Str): Bool =
    props.any { it.name == name }
fun KClass<*>.hasProps(vararg names: Str): Bool =
    names.all { name ->
        props.any { it.name == name }
    }
    
val KClass<*>.props
    get() = this.memberProperties

fun <T : Any> KClass<T>.getProp(name: Str) =
    props.firstOrNull { it.name == name }
        .also { if (it == null) Vlog("Cant find prop ($name) in instance $this") }
    
fun <T : Any> KClass<T>.getPropValue(
    instance: T,   // User(1, "A")
    name: Str
): Any? {
    val prop = this.getProp(name)

    @Suppress("UNCHECKED_CAST")
    return (prop as? KProperty1<T, *>)?.get(instance)
}

val KProperty1<*, *>.isMutable: Bool
    get() = this is KMutableProperty1<*, *>

fun <T : Any> KClass<T>.setProp(
    instance: T,   // User(1, "A")
    name: Str,
    value: Any?
) {
    val prop = this.getProp(name) 
    val mutableProp = prop as? KMutableProperty1<T, Any?> ?: run {
        Vlog("Cant change val prop ($name) in instance $instance")
        return
    }

    mutableProp.set(instance, value)
}

val Any.className: Str
    get() = this::class.simpleName ?: "Unknown"





