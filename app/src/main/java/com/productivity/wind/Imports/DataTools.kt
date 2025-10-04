package com.productivity.wind.Imports

import timber.log.Timber
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
import android.content.ClipData
import android.content.ClipboardManager
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import android.content.Intent
import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import android.graphics.drawable.Drawable
import android.content.pm.*
import java.util.*
import com.productivity.wind.R
import kotlin.reflect.full.*

@Suppress("UNCHECKED_CAST")


//region Vals/ Vars FOR DATA




val gson = Gson()
val yes = true
val no = false
var <T> m_<T>.it: T
    get() = this.value
    set(value) { this.value = value }



fun Dp.toPx(): Int {
    var context = App.ctx
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.value,
        context.resources.displayMetrics
    ).toInt()
}
fun getStoredData(
    fileName: String = "settings",
    mode: Int = Context.MODE_PRIVATE
): SharedPreferences = App.ctx.getSharedPreferences(fileName, mode)


fun <T> SharedPreferences.Editor.putMutableList(id: String, list: MutableList<T>?) {
    if (list == null) {
        putString(id, null) // handle null safely
    } else {
        val json = Gson().toJson(list) // convert list to JSON
        putString(id, json)
    }
}
inline fun <reified T> SharedPreferences.getMutableList(id: String): MutableList<T>? {
    val json = getString(id, null) ?: return null
    val type = object : TypeToken<MutableList<T>>() {}.type
    return Gson().fromJson(json, type)
}

fun SharedPreferences.Editor.putAny(name: String, value: Any?) {
    when (value) {
        is Boolean -> putBoolean(name, value)
        is String -> putString(name, value)
        is Int -> putInt(name, value)
        is Float -> putFloat(name, value)
        is Long -> putLong(name, value)
        else -> return
    }
}

fun <T : Any> getClass(obj: T): List<KProperty1<T, *>> =
    obj::class.memberProperties
        .also { props ->
            props.forEach { prop ->
                if (prop.visibility != KVisibility.PUBLIC) {
                    log("NO private stuff")
                }
            }
        }
        .filter { it.visibility == KVisibility.PUBLIC } as List<KProperty1<T, *>>

@Composable
fun <T> r(value: () -> T) = remember { value() }
fun <T> m(value: T) = mutableStateOf(value)
fun <T> set(state: m_<T>?, value: T) { state?.value = value }
fun show(state: m_<Bool>?) = set(state, yes)
fun hide(state: m_<Bool>?) = set(state, no)
@Composable
fun <T> r_m(initial: T) = r { m(initial) }
inline fun <reified T> ml(): MutableList<T> = mutableListOf()
inline fun <reified T> ml(@Suppress("UNUSED_PARAMETER") dummy: T): SnapshotStateList<T> { return mutableStateListOf() }
fun Id(): String { return UUID.randomUUID().toString() }
typealias Content = @Composable () -> Unit
typealias Do = () -> Unit
typealias Content_<T> = @Composable (T) -> Unit
typealias Mod = Modifier
typealias Do_<T> = (T) -> Unit
typealias m_<T> = MutableState<T>
typealias Str = String
typealias Bool = Boolean
typealias ClassVar<T, R> = KMutableProperty1<T, R>
typealias ClassVal<T, R> = KProperty1<T, R>



object DayChecker {
    private var job: Job? = null

        
    fun start() {
        if (job?.isActive == true) return  // Already running
        if (Bar.lastDate == "") { Bar.lastDate = LocalDate.now().toString() }
            
        job = CoroutineScope(Dispatchers.Default).launch {
            while (coroutineContext.isActive) {
                delay(60 * 1000L)
                val today = LocalDate.now().toString()
                if (today != Bar.lastDate) {
                    Bar.lastDate = today
                    onNewDay()
                }
            }
        }
    }
}






inline fun <reified T> getListType(list: SnapshotStateList<T>): Type {
    return object : TypeToken<MutableList<T>>() {}.type
}

fun FindVar(listName: String, where: String = "com.productivity.wind.DataKt"): SnapshotStateList<Any>? {
    return try {
        val clazz = Class.forName(where)
        val field = clazz.declaredFields.firstOrNull { it.name == listName }
        field?.isAccessible = true
        field?.get(null) as? SnapshotStateList<Any> ?: run {
            Vlog("❌ List '$listName' not found")
            null
        }
    } catch (e: Exception) {
        Vlog("❌ Error resolving list '$listName': ${e.message}")
        null
    }
}

fun FindBar(statePath: Str): Pair<Any, ClassVar<Any, Str>>? {
    val parts = statePath.split(".")
    if (parts.size != 2) {
        Vlog("❌ Invalid state path: '$statePath'")
        return null
    }

    val (objectName, propertyName) = parts
    val instance: Any = when (objectName) {
        "Bar" -> Bar
        else -> {
            Vlog("❌ Unknown object: '$objectName'")
            return null
        }
    }

    val stateProp = instance::class.memberProperties
        .filterIsInstance<ClassVar<Any, *>>()
        .firstOrNull { it.name == propertyName } as? ClassVar<Any, String>
        ?: run {
            Vlog("❌ Property '$propertyName' not found in object '$objectName'")
            return null
        }

    return Pair(instance, stateProp)
}

//endregion Vals/ Vars FOR DATA


@Composable
fun BsaveToFile(trigger: Bool) {
    val ctx = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        if (uri != null) {
            val Data = getStoredData()
            ctx.contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { out ->
                Data.all.forEach { (key, value) -> out.write("$key=$value\n") }
            }
        }
    }

    LaunchedEffect(trigger) { if (trigger) launcher.launch("WindBackUp.txt")
    }
}
@Composable
fun BrestoreFromFile(trigger: m_<Bool>) {
    val context = LocalContext.current

    val launcher = rememberUpdatedState(
        newValue = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri ->
            if (uri != null) {
                try {
                    val fileMap = mutableMapOf<String, String>()

                    context.contentResolver.openInputStream(uri)?.bufferedReader()?.useLines { lines ->
                        lines.forEach { line ->
                            if (!line.contains("=")) { Vlog("Error...corrupted data"); return@forEach }
                            val (key, value) = line.split("=", limit = 2)
                            fileMap[key] = value
                        }
                    }

                    SettingsSaved.initFromFile(fileMap)
                } catch (e: Exception) {
                    log("Restore failed: ${e.message}", "bad")
                }
            }
        }
    )

    LaunchedEffect(trigger.it) {
        if (trigger.it) {
            App.restoringFromFile = no
            launcher.value.launch(arrayOf("text/plain"))
            delay(2000L)
            App.restoringFromFile = no
            trigger.it = false
            Vlog("Successfully restored")
        }
    }
}



val Bar = Settings(); //best variable

var initOnce= false

object SettingsSaved {
    private var Dosave: Job? = null
    private const val PREFS = "settingsLISTS"
    private val lastJson = mutableMapOf<Str, Str>()
    private var autoSaveJob: Job? = null


    fun Bsave() {
        if (Dosave?.isActive == true) return
        if (App.restoringFromFile) return
        Dosave = GlobalScope.launch {
            while (isActive) {
                val Data = getStoredData().edit()
                var CPU = 0
                
                getClass(Bar).forEach { bar ->
                    /*CPU usage, forget this ok*/CPU+=20; if (CPU>2000) {log("SettingsManager: Bsave is taking up to many resourcesss. Shorter delay, better synch, like skipping things, and maing sure only one runs, can greatly decrease THE CPU USAGE", "Bad") }//ADD SUPER UNIVERSAL STUFFF
                    bar.isAccessible = true
                    val value = bar.get(Bar)
                
                    Data.putAny(bar.name, value)
                }
                edit.apply()
                delay(1_000L)
            }
        }
    }
    fun init() {
        val prefs = getStoredData()
        if (prefs.all.isEmpty() || initOnce) return
        initOnce= true //MUST USE, ALL ARE ZERO OR NULL

        getClass(Bar).forEach { barIDK ->
            //best variable is variable//JUST MAKING SURE
            if (barIDK is ClassVar<Settings, *>) {
                val bar = barIDK as ClassVar<Settings, Any?>
                bar.isAccessible = true
                val name = bar.name
                val type = bar.returnType.classifier

                val stateProp = bar.getDelegate(Bar)
                when {
                    stateProp is m_<*> && type == Bool::class -> (stateProp as m_<Bool>).it = prefs.getBoolean(name, false)
                    stateProp is m_<*> && type == Str::class -> (stateProp as m_<Str>).it = prefs.getString(name, "") ?: ""
                    stateProp is m_<*> && type == Int::class -> (stateProp as m_<Int>).it = prefs.getInt(name, 0)
                    stateProp is m_<*> && type == Float::class -> (stateProp as m_<Float>).it = prefs.getFloat(name, 0f)
                    stateProp is m_<*> && type == Long::class -> (stateProp as m_<Long>).it = prefs.getLong(name, 0L)
                }
            }
            else { log("SettingsManager: Property '${barIDK.name}' is not a var! Make it mutable if you want to sync it.", "Bad") }
        }
        ListStorage.initAll()
    }
    fun initFromFile(map: Map<Str, Str>) {
        getClass(Bar).forEach { barIDK ->
            if (barIDK is ClassVar<Settings, *>) {
                val bar = barIDK as ClassVar<Settings, Any?>
                bar.isAccessible = true
                val name = bar.name
                val type = bar.returnType.classifier

                val stateProp = bar.getDelegate(Bar)
                val raw = map[name]

                when {
                    stateProp is m_<*> && type == Bool::class -> (stateProp as m_<Bool>).it = raw?.toBoolean() ?: no
                    stateProp is m_<*> && type == Str::class -> (stateProp as m_<Str>).it = raw ?: ""
                    stateProp is m_<*> && type == Int::class -> (stateProp as m_<Int>).it = raw?.toIntOrNull() ?: 0
                    stateProp is m_<*> && type == Float::class -> (stateProp as m_<Float>).it = raw?.toFloatOrNull() ?: 0f
                    stateProp is m_<*> && type == Long::class -> (stateProp as m_<Long>).it = raw?.toLongOrNull() ?: 0L
                }
            } else {
                log("SettingsManager: Property '${barIDK.name}' is not a var! Make it mutable if you want to sync it.", "Bad")
            }
        }
        ListStorage.initAll()
    }

}



data class Dset(
    val toFrom: String,
    val what: String
)

object ListStorage {

    //RUNS ON start and restore
    fun initAll() {
        val dataClass = Class.forName("com.productivity.wind.DataKt")
        for (x in trackedLists) {
            try {
                val jsonFieldName = x.toFrom.split(".").last()
                val listFieldName = x.what
                //log("🔍 Restoring $listFieldName from $jsonFieldName")

                val jsonProp = Settings::class.memberProperties
                    .firstOrNull { it.name == jsonFieldName } as? ClassVal<Settings, *>
                val json = jsonProp?.get(Bar) as? String
                if (json.isNullOrBlank()) {
                    log("⚠️ Skipping $listFieldName: JSON is blank")
                    continue
                }
                val listField = dataClass.getDeclaredField(listFieldName)
                listField.isAccessible = true

                @Suppress("UNCHECKED_CAST")
                val list = listField.get(null) as? SnapshotStateList<Any> ?: continue

                val genericType = listField.genericType
                val listParamType = (genericType as? ParameterizedType)
                    ?.actualTypeArguments
                    ?.firstOrNull()

                if (listParamType == null) {
                    log("❌ Could not detect type for $listFieldName")
                    continue
                }

                val type = TypeToken.getParameterized(List::class.java, listParamType).type
                val loaded = gson.fromJson<List<*>>(json, type)
                val typed = loaded as List<Any>

                list.clear()
                list.addAll(typed)
                log("✅ Restored $listFieldName: ${typed.size} items")

            } catch (e: Exception) {
                log("❌ Exception for '${x.what}': ${e.message}")
            }
        }
    }




    @Composable
    fun SynchAll(){
        for (x in trackedLists) {
            SSet2(x.toFrom + ", " + x.what)
        }

    }




    @Composable
    fun SSet2(stateCommand: Str) {

        LaunchedEffect(Unit) {
            val parts = stateCommand.split(",").map { it.trim() }
            if (parts.size != 2) {
                Vlog("❌ Format error: expected 'Object.property, ListName'")
                return@LaunchedEffect
            }

            val statePath = parts[0]   // e.g., Bar.myList
            val listName = parts[1]    // e.g., Tests

            val barResult = FindBar(statePath)
            if (barResult == null) {
                Vlog("❌ Failed to resolve state path: $statePath")
                return@LaunchedEffect
            }

            val listResult = FindVar(listName)
            if (listResult == null) {
                Vlog("❌ Failed to resolve list: $listName")
                return@LaunchedEffect
            }

            val instance = barResult.first
            val stateProp = barResult.second

            while (true) {
                stateProp.set(instance, gson.toJson(listResult))
                delay(1_000L)
            }
        }
    }



}
















// Every model should have an ID
interface Identifiable {
    val id: String
}

// Every model must support copy
interface Copyable<T> {
    fun copySelf(): T
}

// Extension to add new items
fun <T : Any> SnapshotStateList<T>.new(item: T) {
    this.add(item)
}

// Edit by index or object reference
fun <T : Copyable<T>> SnapshotStateList<T>.edit(item: T, block: T.() -> Unit) {
    val index = indexOf(item)
    if (index != -1) {
        // create a modified copy
        val copy = item.copySelf().apply(block)
        // assign it back to the same index to trigger recomposition
        this[index] = copy
        // tiny trick: touch the list itself to force Compose
        this[index] = this[index]
    }
}


// Edit by ID (for Identifiable)
fun <T> SnapshotStateList<T>.idEdit(id: String, block: T.() -> Unit)
    where T : Identifiable, T : Copyable<T> {
    val index = this.indexOfFirst { it.id == id }
    if (index != -1) {
        val copy = this[index].copySelf().apply(block)
        this[index] = copy
    }
}
