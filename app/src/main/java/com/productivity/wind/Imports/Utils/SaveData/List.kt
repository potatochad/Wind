package com.productivity.wind.Imports.Utils.SaveData

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

fun <T : LazyData> TrackList(
    items: List<T> = emptyList()
): By<mList<T>> {

    var listName by m("")
    var customList: mList<T>? = null


    fun save(){
        val list = customList ?: run {
            VlogOne("Custom list is not initialized before saving!")
            return
        }
        list.forEach {
            it.save(listName)
        }
        VlogOne("saving...")
    }


    var saveJob: Job? = null
    var changed by m(no)

    fun changed() {
        changed = yes

        if (saveJob != null) return

        saveJob = scope.launch {
            delay(300)

            if (changed) {
                save()
                changed = no
            }

            saveJob = null
        }
    }
    
    items.forEach { it.onChanged = ::changed }


    customList = CustomList(
        items = items,
        add = {
            this.add(it)
            it.onChanged = ::changed
            changed()
            true
        },
        addAt = { index, item ->
            this.add(index, item)
            item.onChanged = ::changed
            changed()
        },
        addAll = { items -> 
            this.addAll(items)
            items.forEach {
                it.onChanged = ::changed
            }
            changed()
            true
        },
        addAllAt = { index, items ->
            this.addAll(index, items)
            items.forEach {
                it.onChanged = ::changed
            }
            changed()
            true
        },
        clear = {
            this.clear()
            changed()
        },
        remove = {
            this.remove(it)
            changed()
            true
        },
        removeAt = {
            val result = this.removeAt(it)
            changed()
            result
        },
        removeAll = {
            this.removeAll(it)
            changed()
            true
        },
        set = { index, item ->
            this[index] = item
            changed()
            item
        },
        toString = {
            this.firstOrNull()?.className ?: "Unknown"
        }
    )

    
    
    return By(customList)
        .onBuild { prop, id -> 
            listName = id
        }
        .onSet { prop, id, value -> VlogOne("LIST MUST BE VAL") }
}

//nothing can be private
abstract class LazyData {
    var onChanged: Do = {}
    val id by m(Id())
    val clazzName = this.className
    
    val vars = mutableMapOf<String, VarInfo<*>>()
    inline fun <reified T> lazyS(x: T): By<T> {
        return By(x)
            .onBuild { prop, name ->
                vars[name] = VarInfo(name, x)
            }
            .onGet { prop ->
                
            }
            .onSet { prop, name, value ->
                vars[name] = VarInfo(name, value)
                onChanged()
            }
    }


    open fun save(listName: Str){
        var customStr by m(toStr("$listName:$id", vars.values.toList()))
        
        VlogOne(customStr, 10000)
    }
    
    
}



class Schedule6(
    val name: Str
) {
    var time = 10
    val created = System.currentTimeMillis()

    fun reset() {
        time = 0
    }
}
object Config {
    val version = 1 // not testedd
}


class TestData : LazyData() {

    var name by lazyS("hello")
}


data class ComplexClass(
    val list: List<String>,
    val map: Map<String, Int>,
    val array: Array<Int>
)


data class Box<T>(
    val value: T
)


sealed class TestSealed

data class SomeData(
    val number: Int
) : TestSealed()


// list using testdata class
/*
‼️‼️ Do one last test of everythinggg
‼️‼️ And make a cheat sheet (.md) so can look up + for others)

WHAT TO STORING works with (only TO STORING)

    var name by lazyS("hello")

    //DOESNT MATTER IF DATA CLASS OR CLASS
    var time by lazyS(Schedule()) //doesnt matter if vals or vars or about defaults (TALKING ONLY ABOUT WHAT INSIDR (), NOT {} 
    
    var done by lazyS(no)
    var doneTimes by lazyS(3)
    var string by lazyS("hello")
    var long by lazyS(3L)
    var double by lazyS(3.0)
    var char by lazyS('a')
    var status by lazyS(Status.WAITING)// enum BUT TYPE IS LIKE ANY OTHER CLASS

    var nullable by lazyS<Str?>(null)// stored as varName[null][null]

    //works the same with T as any other class
    data class Box<T>(
       val value: T
    )

    //!! SEALED CLASS STORES a bit strange
    .SealedClass$DataClassThatInIt][DataClassThatInIt(6)]

    // treated same as any other class
    class Child : Base() {
       val b = 2
    }

    //class in class is stored correctly pvz: x(y=c(5))


    //TYPE UNKNOWNNN
    // emptyList][class kotlin.collections.EmptyList][List<?>[]],!!!!!
    var emptyList by lazyS(emptyList<String>())
    var emptyMutableList by lazyS(mutableListOf<String>())
    var emptySet by lazyS(emptySet<String>())
    var emptyMap by lazyS(emptyMap<String, String>())


    //saved save as listOf
    var mutableList by lazyS(mutableListOf("A", "B", "C"))

    //saved slightly diff, but works
    var set by lazyS(setOf("A", "B", "C"))


    //the type isnt registerd as NULL!!, but values correctly
    var nullableList by lazyS<List<String?>>(listOf("A", null, "B"))

    //MAP type NOT DPECIFIC
    [nullableMap][class java.util.LinkedHashMap][Map["a"=null, null="B"]
    var nullableMap by lazyS<Map<String?, String?>>(
        mapOf(
            "a" to null,
            null to "B"
        )
    )
*/
