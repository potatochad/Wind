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



//---------<Testing>-----//
class PersistList<T>(
    private val id: Str,
    private val serializer: KSerializer<List<T>>,
    items: List<T> = emptyList()
) : MutableList<T> {

    private val inner = mutableStateListOf<T>()


    var stop by m(no)
    
    private fun bind(element: T) {
        (element as? LazyData)?.parentList = this
    }

    

    init {
        if (id == "tempKeyWind") stop = yes
        items.forEach(::bind)
        inner.addAll(items)
    }


    private var saveJob: Job? = null

    fun save() {
        if (!stop) return
        saveJob?.cancel()
        
        saveJob = scope.launch {
            delay(300)
            AppData.saveList(id, inner, serializer)
        }
    }

    override val size get() = inner.size

    override fun add(element: T): Bool {
        bind(element)
        val r = inner.add(element)
        save()
        return r
    }

    override fun remove(element: T): Bool {
        val r = inner.remove(element)
        save()
        return r
    }

    override fun clear() {
        inner.clear()
        save()
    }

    override fun set(index: Int, element: T): T {
        bind(element)
        val r = inner.set(index, element)
        save()
        return r
    }

    override fun add(index: Int, element: T) {
        bind(element)
        inner.add(index, element)
        save()
    }

    override fun removeAt(index: Int): T {
        val r = inner.removeAt(index)
        save()
        return r
    }



    override fun addAll(elements: Collection<T>): Bool {
        elements.forEach(::bind)

        val r = inner.addAll(elements)
        if (r) save()

        return r
    }

    override fun addAll(index: Int, elements: Collection<T>): Bool {
        elements.forEach(::bind)

        val r = inner.addAll(index, elements)
        if (r) save()
    
        return r
    }


    override fun removeAll(elements: Collection<T>): Bool {
        val r = inner.removeAll(elements)

        if (r) save()

        return r
    }

    override fun retainAll(elements: Collection<T>): Bool {
        val r = inner.retainAll(elements)

        if (r) save()

        return r
    }

    override fun get(index: Int) = inner[index]
    override fun iterator() = inner.iterator()
    override fun indexOf(element: T) = inner.indexOf(element)
    override fun lastIndexOf(element: T) = inner.lastIndexOf(element)
    override fun listIterator() = inner.listIterator()
    override fun listIterator(index: Int) = inner.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int) = inner.subList(fromIndex, toIndex)
}

inline fun <reified T> specialList(
    default: List<T> = emptyList(),
    idExtra: Str = ""
): By<PersistList<T>> {
    val delegate = By(PersistList<T>("tempKeyWind", ListSerializer(serializer<T>()), default))

    val stop: (Str) -> By<PersistList<T>> = { log ->
        Vlog(log)
        By(PersistList<T>("tempKeyWind", ListSerializer(serializer<T>()), default))
    }
    
    if (!LazyData::class.java.isAssignableFrom(T::class.java)) return stop("The class ${T::class} must use LazyData")     
    if (!T::class.isSimpleClass) return stop("Only simple Data class allowed: class TestData(): LazyData(){}")
    if (!isSerializable<T>()) return stop("Class ${T::class} must be @Serializable")
    
    
    

    var goodId by m("")
    var badId by m(no)

    delegate
        .onBuild { prop, id ->

            goodId = "$id: $idExtra"

            badId = idList.has(goodId)
            idList.add(goodId)

            if (badId) {
                Vlog("Duplicate id detected: $goodId")
                return@onBuild
            }

            val saved = AppData.getList<T>(goodId)

            delegate.it = PersistList(
                goodId,
                ListSerializer(serializer<T>()),
                saved
            )
        }

    return delegate
}


var testList = mList<TestData>()

@Serializable
abstract class LazyData {
    val id: Str = Id()

    @Transient //dont save
    var parentList: PersistList<*>? = null
    
    fun <T> lazyState(default: T) =
      object {
        private var state by m(default)

        operator fun getValue(
            thisRef: LazyData,
            property: KProperty<*>
        ): T {
            return state
        }

        operator fun setValue(
            thisRef: LazyData,
            property: KProperty<*>,
            value: T
        ) {
            state = value

            thisRef.parentList?.save()
        }
    }
}

@Serializable
class TestData(): LazyData() {
    var name by lazyState("hello")
}















