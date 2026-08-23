package com.productivity.wind.Imports.Utils.Generic_list

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Renames.*
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
import com.productivity.wind.Imports.Utils.Log.logTimer


abstract class EasyListExtra<T>(
    items: Iterable<T>
) : Iterable<T> {
    var it = mStateList<T>().apply { addAll(items) }
    
    var onAdd: mList<T>.(T) -> Unit = {}
    var onAddAll: mList<T>.(Iterable<T>) -> Unit = {}
    var onRemove: mList<T>.(T) -> Unit = {}

    val pending = mList<Do>()
    var eachDepth = 0

    fun waitIfEach(Do: Do) {
        if (eachDepth > 0) pending.add(Do)
        else Do()
    }

    

    //‼️‼️ADD and remove can be the only code 
    // THAT ACTUALLY REMOVES THE ITEMS OR ADDS IT   
    //IF want add more: waitIfEach and onX
    //----------------------------------------//
    fun add(item: T) = waitIfEach {
        it.add(item)
        it.onAdd(item)
    }
    fun add(index: Int, item: T) = waitIfEach {
        it.add(index, item)
        it.onAdd(item)
    }
    fun addAll(items: Iterable<T>) = waitIfEach {
       it.addAll(items)
       it.onAddAll(items)
    }

    fun remove(item: T) = waitIfEach {
        val index = it.indexOf(item)

        if (index != -1) {
            it.removeAt(index)
            it.onRemove(item)
        }
    }
    //-----------------------------------------//


    operator fun get(index: Int) = it[index]
    operator fun set(index: Int, value: T){ it[index] = value }
    operator fun plusAssign(item: T) = add(item)
    operator fun minusAssign(item: T) = remove(item)
    
    //‼️ shall crash onRemove
    override fun iterator(): Iterator<T> = it.iterator()
}



//‼️‼️ INSIDE CODE
//ONLY USE function ‼️ MYYY EACH, REMOVE, ADD
class EasyList<T> : EasyListExtra<T> {
    constructor(vararg items: T) : super(items.toList())
    constructor(items: Iterable<T>) : super(items)
    
    val id = Id()

    
    val size get() = it.size
    val notEmpty get() = it.notEmpty
    val empty get() = it.empty


    fun clear() {
        it.toList().forEach { item ->
            remove(item)
        }
    }

    fun removeAt(index: Int) {
        it.getOrNull(index)?.let { item ->
            remove(item)
        }
    }


    fun each(block: (T) -> Unit) {
        each { _, item ->
            block(item)
        }
    }

    fun each(block: (Int, T) -> Unit) {
        eachDepth++
        try {
            for (index in it.indices) {
                block(index, it[index])
            }
        } finally {
            eachDepth--

            if (eachDepth == 0) {
                pending.forEach { it() }
                pending.clear()
            }

        }
    }
    
    
    fun filter(logic: (T) -> Bool): EasyList<T> {
        val result = EasyList<T>()
        
        each { _, item ->
            if (logic(item)) {
                result.add(item)
            }
        }
        return result
    }



}



@Composable
fun <T> rEasyList(vararg items: T) = r { EasyList(*items) }

fun <T, L : EasyListExtra<T>> L.onAdd(
    block: mList<T>.(T) -> Unit
): L {
    onAdd = block
    return this
}

fun <T, L : EasyListExtra<T>> L.onRemove(
    block: mList<T>.(T) -> Unit
): L {
    onRemove = block
    return this
}
fun <T, L : EasyListExtra<T>> L.onAddAll(
    block: mList<T>.(Iterable<T>) -> Unit
): L {
    onAddAll = block
    return this
}



