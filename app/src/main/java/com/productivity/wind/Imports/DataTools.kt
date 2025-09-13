package com.productivity.wind.Imports
//
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


//region Vals/ Vars FOR DATA

val gson = Gson()

@Composable
fun <T> r(value: () -> T) = remember { value() }

fun <T> m(value: T) = mutableStateOf(value)
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

fun FindBar(statePath: String): android.util.Pair<Any, KMutableProperty1<Any, String>>? {
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
        .filterIsInstance<KMutableProperty1<Any, *>>()
        .firstOrNull { it.name == propertyName } as? KMutableProperty1<Any, String>
        ?: run {
            Vlog("❌ Property '$propertyName' not found in object '$objectName'")
            return null
        }

    return android.util.Pair(instance, stateProp) // 👈 THIS FIXES IT
}

//endregion Vals/ Vars FOR DATA


@Composable
fun BsaveToFile(trigger: Boolean) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        if (uri != null) {
            val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            context.contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { out ->
                prefs.all.forEach { (key, value) -> out.write("$key=$value\n") }
            }
        }
    }

    LaunchedEffect(trigger) { if (trigger) launcher.launch("WindBackUp.txt")
    }
}
@Composable
fun BrestoreFromFile(trigger: MutableState<Boolean>) {
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
                    //Vlog("Restore failed: ${e.message}")
                }
            }
        }
    )

    LaunchedEffect(trigger.value) {
        if (trigger.value) {
            App.restoringFromFile = true
            launcher.value.launch(arrayOf("text/plain"))
            delay(2000L)
            App.restoringFromFile = false
            trigger.value = false
            Vlog("Successfully restored")
        }
    }
}



val Bar = Settings(); //best variable

var initOnce= false

object SettingsSaved {
    private var Dosave: Job? = null
    private const val PREFS = "settingsLISTS"
    private val lastJson = mutableMapOf<String, String>()
    private var autoSaveJob: Job? = null


    fun Bsave() {
        if (Dosave?.isActive == true) return
        if (Bar.restoringFromFile) return
        Dosave = GlobalScope.launch {
            while (isActive) {
                val data = App.ctx.getSharedPreferences("settings", Context.MODE_PRIVATE)
                val edit = data.edit()

                var CPU = 0
                Settings::class.memberProperties.forEach { bar ->
                    /*CPU usage, forget this ok*/CPU+=20; if (CPU>2000) {log("SettingsManager: Bsave is taking up to many resourcesss. Shorter delay, better synch, like skipping things, and maing sure only one runs, can greatly decrease THE CPU USAGE", "Bad") }//ADD SUPER UNIVERSAL STUFFF
                    bar.isAccessible = true
                    val value = bar.get(Bar)
                    if (value is SnapshotStateList<*>) return@forEach

                    when (value) {
                        is Boolean -> edit.putBoolean(bar.name, value)
                        is String -> edit.putString(bar.name, value)
                        is Int -> edit.putInt(bar.name, value)
                        is Float -> edit.putFloat(bar.name, value)
                        is Long -> edit.putLong(bar.name, value)
                    }
                }
                edit.apply()
                delay(1_000L) // save every 5 seconds
            }
        }
    }
    fun init() {
        val prefs = App.ctx.getSharedPreferences("settings", Context.MODE_PRIVATE)
        if (prefs.all.isEmpty() || initOnce) return
        initOnce= true //MUST USE, ALL ARE ZERO OR NULL

        Settings::class.memberProperties.forEach { barIDK ->
            //best variable is variable//JUST MAKING SURE
            if (barIDK is KMutableProperty1<Settings, *>) {
                @Suppress("UNCHECKED_CAST")
                val bar = barIDK as KMutableProperty1<Settings, Any?>
                bar.isAccessible = true
                val name = bar.name
                val type = bar.returnType.classifier

                val stateProp = bar.getDelegate(Bar)
                when {
                    stateProp is MutableState<*> && type == Boolean::class -> (stateProp as MutableState<Boolean>).value = prefs.getBoolean(name, false)
                    stateProp is MutableState<*> && type == String::class -> (stateProp as MutableState<String>).value = prefs.getString(name, "") ?: ""
                    stateProp is MutableState<*> && type == Int::class -> (stateProp as MutableState<Int>).value = prefs.getInt(name, 0)
                    stateProp is MutableState<*> && type == Float::class -> (stateProp as MutableState<Float>).value = prefs.getFloat(name, 0f)
                    stateProp is MutableState<*> && type == Long::class -> (stateProp as MutableState<Long>).value = prefs.getLong(name, 0L)
                }
            }
            else { log("SettingsManager: Property '${barIDK.name}' is not a var! Make it mutable if you want to sync it.", "Bad") }
        }
        ListStorage.initAll()
    }
    fun initFromFile(map: Map<String, String>) {
        Settings::class.memberProperties.forEach { barIDK ->
            if (barIDK is KMutableProperty1<Settings, *>) {
                @Suppress("UNCHECKED_CAST")
                val bar = barIDK as KMutableProperty1<Settings, Any?>
                bar.isAccessible = true
                val name = bar.name
                val type = bar.returnType.classifier

                val stateProp = bar.getDelegate(Bar)
                val raw = map[name]

                when {
                    stateProp is MutableState<*> && type == Boolean::class -> (stateProp as MutableState<Boolean>).value = raw?.toBoolean() ?: false
                    stateProp is MutableState<*> && type == String::class -> (stateProp as MutableState<String>).value = raw ?: ""
                    stateProp is MutableState<*> && type == Int::class -> (stateProp as MutableState<Int>).value = raw?.toIntOrNull() ?: 0
                    stateProp is MutableState<*> && type == Float::class -> (stateProp as MutableState<Float>).value = raw?.toFloatOrNull() ?: 0f
                    stateProp is MutableState<*> && type == Long::class -> (stateProp as MutableState<Long>).value = raw?.toLongOrNull() ?: 0L
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
                    .firstOrNull { it.name == jsonFieldName } as? KProperty1<Settings, *>
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
    fun SSet2(stateCommand: String) {

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
