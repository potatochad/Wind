package com.productivity.wind.Imports.Utils

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



fun <T> CustomList(
    items: Collection<T> = emptyList(),

    add: ((T) -> Bool)? = null,
    addAt: ((Int, T) -> Unit)? = null,
    addAll: ((Collection<T>) -> Boolean)? = null,
    addAllAt: ((Int, Collection<T>) -> Boolean)? = null,

    clear: (() -> Unit)? = null,

    get: ((Int) -> T)? = null,

    remove: ((T) -> Bool)? = null,
    removeAt: ((Int) -> T)? = null,
    removeAll: ((Collection<T>) -> Boolean)? = null,

    set: ((Int, T) -> T)? = null,

    contains: ((T) -> Boolean)? = null,
    containsAll: ((Collection<T>) -> Boolean)? = null,

    indexOf: ((T) -> Int)? = null,
    lastIndexOf: ((T) -> Int)? = null,

    isEmpty: (() -> Boolean)? = null,

    toString: (() -> String)? = null
): MutableList<T> {

    val inner = items.toMutableList()

    return object : MutableList<T> {

        override val size get() = inner.size

        override fun add(element: T) =
            add?.invoke(element)
                ?: inner.add(element)

        override fun add(index: Int, element: T) {
            addAt?.invoke(index, element)
                ?: inner.add(index, element)
        }

        override fun addAll(elements: Collection<T>) =
            addAll?.invoke(elements)
                ?: inner.addAll(elements)

        override fun addAll(
            index: Int,
            elements: Collection<T>
        ) =
            addAllAt?.invoke(index, elements)
                ?: inner.addAll(index, elements)

        override fun clear() {
            clear?.invoke()
                ?: inner.clear()
        }

        override fun get(index: Int) =
            get?.invoke(index)
                ?: inner[index]

        override fun remove(element: T) =
            remove?.invoke(element)
                ?: inner.remove(element)

        override fun removeAt(index: Int) =
            removeAt?.invoke(index)
                ?: inner.removeAt(index)

        override fun removeAll(elements: Collection<T>) =
            removeAll?.invoke(elements)
                ?: inner.removeAll(elements)

        override fun set(index: Int, element: T) =
            set?.invoke(index, element)
                ?: inner.set(index, element)

        override fun contains(element: T) =
            contains?.invoke(element)
                ?: inner.contains(element)

        override fun containsAll(elements: Collection<T>) =
            containsAll?.invoke(elements)
                ?: inner.containsAll(elements)

        override fun indexOf(element: T) =
            indexOf?.invoke(element)
                ?: inner.indexOf(element)

        override fun lastIndexOf(element: T) =
            lastIndexOf?.invoke(element)
                ?: inner.lastIndexOf(element)

        override fun isEmpty() =
            isEmpty?.invoke()
                ?: inner.isEmpty()

        override fun iterator() =
            inner.iterator()

        override fun listIterator() =
            inner.listIterator()

        override fun listIterator(index: Int) =
            inner.listIterator(index)

        override fun subList(
            fromIndex: Int,
            toIndex: Int
        ) =
            inner.subList(fromIndex, toIndex)

        override fun toString() =
            toString?.invoke()
                ?: inner.toString()

        override fun retainAll(elements: Collection<T>) =
            inner.retainAll(elements)
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







//------------<Testing>----------//

/*
class TrackList<T : LazyData>(
    val listName: String,
    items: List<T> = emptyList()
) : MutableList<T> {

    private val inner = mutableStateListOf<T>()

    init {
        items.forEach(::bind)
        inner.addAll(items)
    }

    private fun bind(item: T) {
        item.parentList = this
    }

    fun onItemChanged(
        item: Any,
        prop: String,
        old: Any?,
        new: Any?
    ) {
        Vlog(
            """
            LIST   : $listName
            ITEM   : ${item.tempId}
            PROP   : $prop
            OLD    : $old
            NEW    : $new
            TIME   : ${System.currentTimeMillis()}
            """.trimIndent()
        )
    }

    override val size get() = inner.size

    override fun add(element: T): Boolean {
        bind(element)
        return inner.add(element)
    }

    override fun add(index: Int, element: T) {
        bind(element)
        inner.add(index, element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elements.forEach(::bind)
        return inner.addAll(elements)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        elements.forEach(::bind)
        return inner.addAll(index, elements)
    }

    override fun clear() = inner.clear()

    override fun get(index: Int): T = inner[index]

    override fun isEmpty(): Boolean = inner.isEmpty()

    override fun iterator(): MutableIterator<T> = inner.iterator()

    override fun listIterator(): MutableListIterator<T> =
        inner.listIterator()

    override fun listIterator(index: Int): MutableListIterator<T> =
        inner.listIterator(index)

    override fun remove(element: T): Boolean =
        inner.remove(element)

    override fun removeAll(elements: Collection<T>): Boolean =
        inner.removeAll(elements)

    override fun removeAt(index: Int): T =
        inner.removeAt(index)

    override fun retainAll(elements: Collection<T>): Boolean =
        inner.retainAll(elements)

    override fun set(index: Int, element: T): T {
        bind(element)
        return inner.set(index, element)
    }

    override fun subList(
        fromIndex: Int,
        toIndex: Int
    ): MutableList<T> = inner.subList(fromIndex, toIndex)

    override fun contains(element: T): Boolean =
        inner.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean =
        inner.containsAll(elements)

    override fun indexOf(element: T): Int =
        inner.indexOf(element)

    override fun lastIndexOf(element: T): Int =
        inner.lastIndexOf(element)
} 

@Serializable
abstract class LazyData {
    @Transient
    var parentList: TrackList<*>? = null

    fun <T> lazyState(default: T): ReadWriteProperty<LazyData, T> {

        return object : ReadWriteProperty<LazyData, T> {

            private var state = default

            override fun getValue(
                thisRef: LazyData,
                property: KProperty<*>
            ): T {
                return state
            }

            override fun setValue(
                thisRef: LazyData,
                property: KProperty<*>,
                value: T
            ) {
                val old = state
                state = value

                thisRef.parentList?.onItemChanged(
                    item = thisRef,
                    prop = property.name,
                    old = old,
                    new = value
                )
            }
        }
    }
}
*/

data class VarField(
    val name: Str,
    var value: Any?,
    val type: KType
)


abstract class LazyData {
    val varList = mutableMapOf<Str, VarField>()
    
    inline fun <reified T> lazyS(x: T): By<T> {
        return By(x)
            .onBuild { prop, id ->
                varList[id] = VarField(id, x, typeOf<T>())
                
                log("varList: $varList")
            }
            .onGet { prop ->
                
            }
            .onSet { prop, value ->
                varList[prop.name]?.value = value
                
                log("set: ${prop.name} = $value")
                log("varList: $varList")
            }
    }
}


/*
CustomList(
    items = listOf("A", "B"),

                            add = { true },
                            addAt = { _, _ -> },
                            addAll = { true },
                            addAllAt = { _, _ -> true },
                            clear = {},
                            get = { "fake-$it" },
                            remove = { true },
                            removeAt = { "removed" },
                            removeAll = { true },
                            set = { _, item -> item },
                            contains = { true },
                            containsAll = { true },
                            indexOf = { 99 },
                            lastIndexOf = { 100 },
                            isEmpty = { false },
                            toString = { "MY CUSTOM LIST" }
                        )
                        */


class TestData : LazyData() {

    var name by lazyS("hello")
    var boringName by lazyS("boring")
}

// list using testdata class














