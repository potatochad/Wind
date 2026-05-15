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
import java.io.*
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



val List<*>.isRecomposable: Bool
    get() = this is SnapshotStateList<*>
    
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

fun KClass<*>.hasId(): Bool {
    if (!this.isData) return no
    return this.props.any { it.name == "id" }
}
fun KClass<*>.hasName(name: Str): Bool =
    props.any { it.name == name }
    
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





@Serializable
data class SavedListState<T>(
    val items: List<T>
)

class PersistedStateList<T : Any>(
    private val key: Str,
    private val clazz: KClass<T>,
    private val save: (Str, Str) -> Unit,
    private val load: (Str) -> Str?,
    private val json: Json = Json {
        ignoreUnknownKeys = yes
        encodeDefaults = yes
    }
) : ReadOnlyProperty<Any?, SnapshotStateList<T>> {

    private var cache: SnapshotStateList<T>? = null

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): SnapshotStateList<T> {

        cache?.let { return it }

        require(clazz.isData) {
            "Only data classes supported"
        }

        val list = mutableStateListOf<T>()

        // restore
        load(key)?.let { raw ->
            runCatching {
                val serializer = ListSerializer(clazz.serializer())
                val restored = json.decodeFromString(serializer, raw)
                list.addAll(restored)
            }
        }

        // auto save
        CoroutineScope(Dispatchers.IO).launch {

            snapshotFlow { list.toList() }
                .collect { items ->

                    runCatching {

                        val serializer = ListSerializer(clazz.serializer())
                        val encoded = json.encodeToString(serializer, items)

                        save(key, encoded)
                    }
                }
        }

        cache = list
        return list
    }
}

@Serializable
data class Todo(
    val id: Str = UUID.randomUUID().toString(),
    var text: Str = "",
    var done: Bool = no
)


var todos by PersistedStateList(
    key = "todos",
    clazz = Todo::class,

    save = { key, value ->
        prefs.edit().putString(key, value).apply()
    },

    load = { key ->
        prefs.getString(key, null)
    }
)

/*
```

Then use normally

todos.add(Todo(text = "Buy milk"))

todos[0] = todos[0].copy(done = yes)

todos.removeAt(0)
*/
/*
Rules for scalability:

* item must be `@Serializable`
* item must be `data class`
* mutations must use `.copy(...)`
* never mutate inner vars directly:
*/

todos[0].done = yes

// todos[0] = todos[0].copy(done = yes)



inline fun <T> SnapshotStateList<T>.update(
    index: Int,
    block: T.() -> T
) {
    this[index] = this[index].block()
}



    
    


  
