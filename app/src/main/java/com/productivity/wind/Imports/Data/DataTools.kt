package com.productivity.wind.Imports.Data

import androidx.compose.runtime.*
import android.content.*
import android.util.*
import androidx.compose.ui.platform.*
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
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import java.lang.reflect.ParameterizedType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import java.time.LocalDate
import com.productivity.wind.Imports.*
import com.productivity.wind.Imports.Data.*

@Suppress("UNCHECKED_CAST")


//region Vals/ Vars FOR DATA

fun FindVar(listName: Str, where: Str = "com.productivity.wind.DataKt"): SnapshotStateList<Any>? {
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
                    /*CPU usage, forget this ok*/CPU+=20; if (CPU>2000) {
                    log("SettingsManager: Bsave is taking up to many resourcesss. Shorter delay, better synch, like skipping things, and maing sure only one runs, can greatly decrease THE CPU USAGE", "Bad") }//ADD SUPER UNIVERSAL STUFFF
                    val value = bar.get(Bar)
                
                    Data.putAny(bar.name, value)
                }
                Data.apply()
                delay(1_000L)
            }
        }
    }
    fun init() {
        val Data = getStoredData()
        if (Data.all.isEmpty() || initOnce) return
        initOnce = true

        getClass(Bar).forEach { bar ->
                val bar = bar as ClassVar<Settings, Any?>
                val name = bar.name
                var type = bar.getType()

                var FullBar: Any = bar.getDelegate(Bar)
    
                when (FullBar) {
                    is MutableState<*> -> {
                        loadMutableState(type, name, FullBar as m_<Any>, Data)
                    }

                    is SnapshotStateList<*> -> {
                        Vlog("loading mutable list: $name")
                        FullBar = Data.getMutableList("MutableList $name")
                    }
                    else -> {
                        Vlog("unsupported type for $name")
                    }
                }
        }
        ListStorage.initAll()
    }
    fun initFromFile(map: Map<Str, Str>) {
        getClass(Bar).forEach { barIDK ->
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
