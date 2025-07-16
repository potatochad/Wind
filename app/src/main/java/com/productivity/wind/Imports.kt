package com.productivity.wind

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.isAccessible

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor


//region USER MANUAL

//region Usefull

/** HOW USE REGION

    //region

    requires a space of one between // and code to work well

    //endregion

*/

/*HOW CODE
    REWRITE NONE UNIVERSAL CODE-TALKING SMALL 100
    all else rewriteee

    use region, one file, and yea
    plus, like to have functions, a section for universal functions

*/

/*HOW MAKE DRAWERS, SLIDY THINGIES, ETC...
    DO NOT USE THE ANDROID DEFAULTS FOR THAT
    NO XML

    *JUST MAKE POPUP, GOT IT, HANDLES LIKE 80% OF THE PAIN-
    ?TO LAZY TO MAKE UNIVERSAL THINGY, WILL DO LATER, ONECE FINISH THIS
    AND TRYING TO KEEP CUSTOMERSS
*/


//! IMPORTANT

//region DATA MANAGEMENT
// all you need
//coudn't be simpler::
/*NEEDED SETUP
* PUT IT HERE!!;
@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
    SettingsSaved.init()
    SettingsSaved.Bsave()
}
*
*
*
class Settings {
    var show by mutableStateOf(false)
    var CurrentInput by mutableStateOf("")
}
*/
/*How Use
* YOU CAN READ THE DATA, CHANGE IT, AUTO UPDATE, saves, etc..
* WORKS FOR LISTS TOOOO
? HANDLES ABOUT 600 ITEMS MAX-recommended is 300
Bar.funTime += 1
    Bar.currentInput = "Testing input"
    Bar.highestCorrect = max(Bar.highestCorrect, 12)
    *
    *
   class Settings {
    var ShowMenu by mutableStateOf(false)
    val AppList = mutableStateListOf<Apps>()  // Apps- is a data class, enter in any you want
*/


/*?HOW CHANGE LIST
* ✅ Add
AppList.add(app)
AppList.addAll(listOfApps)
AppList.add(index, app)
*
❌ Remove
AppList.remove(app)
AppList.removeAt(index)
AppList.removeIf { it.Block }
AppList.clear()
*
🔄 Update
AppList[index] = app.copy(Block = true)
AppList[index].Block = true  // if mutable inside

* val app = AppList[0]  // 0 = first app in the list
val id = app.id       // this is the app's unique ID (UUID)

*
🔍 Find
val found = AppList.find { it.name == "YouTube" }
val exists = AppList.any { it.Block }
val count = AppList.count { it.Exists }
*
🧠 Smart Filtering
val onlyBlocked = AppList.filter { it.Block }
val top3 = AppList.sortedByDescending { it.TimeSpent }.take(3)
*
🔁 Loop
AppList.forEach { app -> println(app.name) }
for (i in AppList.indices) { AppList[i].Exists = false }*/
//endregion


/* Simple Synched:
    var funTime by Synched { (0) }
*/

/*
log:

Example usage:
log("Button clicked")
log("Error happened", "ErrorTag")
*/

/* SMALL THINGS
   val id: String = UUID.randomUUID().toString(),
   *a thing that exists, unique completly

 */

/*NO LAG
USE NO LAG COMPOSE
NoLagCompose

@Composable
fun ChillScreen()=NoLagCompose {}
* */

//endregion Usefull


//region ONCEs

/* ACCESABILITY PERMISSION
*
<service
            android:name=".WatchdogAccessibilityService"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
            android:exported="false">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />

            </intent-filter>

            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service>
*/


//endregion

//endregion





//region GOOD STUFFF

//region log

fun log(message: String, tag: String? = "AppLog") {
    var LogMessage = message ; val stackTrace = Thread.currentThread().stackTrace ; val element = stackTrace[3] ; val fileName = element.fileName ; val lineNumber = element.lineNumber
    LogMessage= "[$fileName:$lineNumber] $message"
    if ("bad".equals(tag, true)) { Log.w(tag, LogMessage) }

    else { Log.d(tag, LogMessage) }
}

//endregion

//region NO LAG COMPOSE

/*EXPLANATION
*
* CUT LAG BY 80%, IN LISTS, ETC..
* BY MAKING SURE NOT EVERYTHING LOADS AT THE SAME TIME*/
@Composable
fun NoLagCompose(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        while (true) {
            delay(50) // space out work; LET UI LOAD
            yield()
        }
    }
    content()
}

//endregion



//endregion
//region DATA MANAGE ONCES

/*NEEDED SETUP
* PUT IT HERE!!;
@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
    SettingsSaved.init()
    SettingsSaved.Bsave()
    SettingsSaved.initialize(Bar)
    SettingsSaved.startAutoSave(Bar)
}
*
*
*
class Settings {
    var show by mutableStateOf(false)
    var CurrentInput by mutableStateOf("")
}
*/
/*How Use
* YOU CAN READ THE DATA, CHANGE IT, AUTO UPDATE, saves, etc..
* WORKS FOR LISTS TOOOO
? HANDLES ABOUT 600 ITEMS MAX-recommended is 300
Bar.funTime += 1
    Bar.currentInput = "Testing input"
    Bar.highestCorrect = max(Bar.highestCorrect, 12)
    *
    *
   class Settings {
    var ShowMenu by mutableStateOf(false)
    val AppList = mutableStateListOf<Apps>()  // Apps- is a data class, enter in any you want
*/

val Bar = Settings(); //best variable

var initOnce= false

object SettingsSaved {
    private var Dosave: Job? = null
    private const val PREFS = "settingsLISTS"
    private val lastJson = mutableMapOf<String, String>()
    private var autoSaveJob: Job? = null

    /*
    private fun Any.toPlainMap(): Map<String, Any?> = this::class.memberProperties
        .filter { it.name.contains('$').not() }     // drop Compose internals
        .mapNotNull { prop ->
            prop.isAccessible = true
            val value = try { prop.getter.call(this) } catch (_: Exception) { return@mapNotNull null }
            if (value is SnapshotStateList<*>) return@mapNotNull null  // skip lists
            prop.name to value
        }.toMap()
    private fun <T : Any> reconstruct(clazz: KClass<T>, data: Map<String, Any?>): T {
        // 1) Build constructor args
        val ctor = clazz.primaryConstructor
            ?: error("Class ${clazz.simpleName} needs a primary constructor")
        val args = mutableMapOf<KParameter, Any?>()
        ctor.parameters.forEach { param ->
            val name = param.name
            if (name != null && data.containsKey(name)) {
                args[param] = data[name]
            }
        }
        // 2) Instantiate
        val instance = ctor.callBy(args)
        // 3) Set any remaining mutable var properties
        clazz.memberProperties.forEach { prop ->
            val name = prop.name
            if (data.containsKey(name) && prop is KMutableProperty1) {
                prop.isAccessible = true
                prop.setter.call(instance, data[name])
            }
        }
        return instance
    }
    fun initialize(context: Context, holder: Any) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        holder::class.memberProperties.forEach { prop ->
            prop.isAccessible = true
            val raw = prop.getter.call(holder)
            if (raw !is SnapshotStateList<*>) return@forEach

            @Suppress("UNCHECKED_CAST")
            val stateList = raw as SnapshotStateList<Any>
            val elementK = prop.returnType
                .arguments.firstOrNull()?.type?.classifier as? KClass<Any>
                ?: run { /* cannot handle */ return@forEach }

            // Read JSON of List<Map<String,Any?>>
            val json = prefs.getString(prop.name, "[]") ?: "[]"
            lastJson[prop.name] = json

            val mapListType = object : TypeToken<List<Map<String, Any?>>>() {}.type
            val listOfMaps: List<Map<String, Any?>> = Gson().fromJson(json, mapListType)

            // Rebuild each element
            stateList.clear()
            listOfMaps.forEach { dataMap ->
                val item = reconstruct(elementK, dataMap)
                stateList.add(item)
            }
        }
    }
    fun startAutoSave(context: Context, holder: Any, intervalMs: Long = 1_000L) {
        if (autoSaveJob?.isActive == true) return
        autoSaveJob = CoroutineScope(Dispatchers.IO).launch {
            val gson = Gson()
            val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

            while (isActive) {
                val editor = prefs.edit()
                var modified = false

                holder::class.memberProperties.forEach { prop ->
                    prop.isAccessible = true
                    val raw = prop.getter.call(holder) ?: return@forEach
                    if (raw !is SnapshotStateList<*>) return@forEach

                    @Suppress("UNCHECKED_CAST")
                    val list = raw as SnapshotStateList<Any>

                    // Snapshot + clean → List<Map<String,Any?>>
                    val cleanList = list.toList().map { it.toPlainMap() }
                    val json = gson.toJson(cleanList)
                    val key = prop.name

                    if (lastJson[key] != json) {
                        editor.putString(key, json)
                        lastJson[key] = json
                        modified = true
                    }
                }

                if (modified) editor.apply()
                delay(intervalMs)
            }
        }
    }
    */


    fun Bsave() {
        if (Dosave?.isActive == true) return
        Dosave = GlobalScope.launch {
            while (isActive) {
                val data = Global1.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
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
        val prefs = Global1.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
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
    }
}

//endregion

//region DATA MANAGE LISTS

//! HOW USE BETTER LIST
/* *update("shopping, id = ${id}, name = Chicken")
! The spaces and ,  are for me-do nothing (make sure not supper close together)
? works with index to, instead of id doo
* index = ${index}
*/

/* *remove("shopping, id = ${id}")
! The spaces and ,  are for me-do nothing (make sure not supper close together)
? works with index to, instead of id doo
* index = ${index}
*/

/* *add("shopping, name = ckicken")
! The spaces and ,  are for me-do nothing (make sure not supper close together)
? ID, pregenerated, other things are also prefilled out (can though add field if want overwrite default)
! But some might not be, Check logs if that the case --"bad",
*/
fun add(input: String) {
    try {
        log("Raw input: '$input'")

        // 1) Match: listName and everything after (the rawFields)
        val pattern = Regex(
            """^\s*([A-Za-z]\w*)\s*,?\s*(.*)$""",
            RegexOption.IGNORE_CASE
        )
        val match = pattern.matchEntire(input)
        if (match == null) {
            log("❌ Input didn't match expected pattern")
            return
        }

        val (rawList, rawFields) = match.destructured
        val listName = rawList.lowercase()
        log("✓ target list = '$listName'")

        // 2) Grab the SnapshotStateList from Blist
        val listProp = Blist::class.members
            .firstOrNull { it.name.equals(listName, ignoreCase = true) }
            ?: run { log("❌ No list named '$listName'"); return }
        val list = listProp.call(Blist) as? SnapshotStateList<Any>
            ?: run { log("❌ Property '$listName' is not a SnapshotStateList"); return }

        // 3) Figure out the element class from the list's generic
        val elementKClass = (listProp.returnType.arguments
            .firstOrNull()?.type?.classifier as? KClass<*>)
            ?: run { log("❌ Couldn't determine element type for '$listName'"); return }

        // 4) Instantiate with defaults via primary constructor
        val ctor = elementKClass.primaryConstructor
            ?: run { log("❌ No primary constructor on ${elementKClass.simpleName}"); return }
        ctor.isAccessible = true
        // call with no args → all parameters must have defaults
        val newItem = ctor.callBy(emptyMap())
        log("✓ Created new ${elementKClass.simpleName} with defaults: $newItem")

        // 5) Apply overrides from rawFields (e.g. "name = Chicken, done = true")
        if (rawFields.isNotBlank()) {
            val kvRegex = Regex("""(\w+)\s*=\s*([^\s,]+)""")
            kvRegex.findAll(rawFields).forEach { m ->
                val field = m.groupValues[1]
                val value = m.groupValues[2]
                log("→ override '$field' = '$value'")

                // find the MutableState<Property> on newItem
                val prop = elementKClass.memberProperties
                    .firstOrNull { it.name.equals(field, ignoreCase = true) }
                    ?: run { log("   • no property '$field' on ${elementKClass.simpleName}"); return@forEach }

                // we expect backing state: e.g. val name = mutableStateOf(default)
                val state = prop.getter.call(newItem)
                if (state is MutableState<*>) {
                    val parsed: Any? = when (state.value) {
                        is Boolean -> value.equals("true", ignoreCase = true)
                        is Int     -> value.toIntOrNull()
                        is Float   -> value.toFloatOrNull()
                        is Double  -> value.toDoubleOrNull()
                        else       -> value
                    }
                    if (parsed == null) {
                        log("   • failed to parse '$value' for field '${prop.name}'")
                    } else {
                        @Suppress("UNCHECKED_CAST")
                        (state as MutableState<Any>).value = parsed
                        log("   • set '${prop.name}' = $parsed")
                    }
                } else {
                    log("   • property '${prop.name}' is not a MutableState")
                }
            }
        } else {
            log("✓ no overrides provided")
        }

        // 6) Finally, add to the list
        list.add(newItem)
        log("✓ Added new item to '$listName': $newItem")

    } catch (e: Exception) {
        log("Add failed: ${e.message}")
    }
}
fun remove(input: String) {
    try {
        log("Raw input: '$input'")

        // 1) Match: listName, (id or index)
        val pattern = Regex(
            """^\s*([A-Za-z]\w*)\s*,?\s*(?:id\s*=\s*([^\s,]+)|index\s*=\s*(\d+))\s*$""",
            RegexOption.IGNORE_CASE
        )
        val match = pattern.matchEntire(input)
        if (match == null) {
            log("❌ Input didn't match expected pattern")
            return
        }

        val (rawList, rawId, rawIndex) = match.destructured
        val listName = rawList.lowercase()
        log("✓ target list = '$listName'")

        val isByIndex = rawIndex.isNotBlank()
        val selector = if (isByIndex) rawIndex else rawId
        log("✓ selecting by ${if (isByIndex) "index" else "id"} = '$selector'")

        // 2) Get the SnapshotStateList from Blist
        val listProp = Blist::class.members
            .firstOrNull { it.name.equals(listName, ignoreCase = true) }
            ?: run { log("❌ No list named '$listName'"); return }
        val list = listProp.call(Blist) as? SnapshotStateList<Any>
            ?: run { log("❌ Property '$listName' is not a SnapshotStateList"); return }

        // 3) Remove the item
        if (isByIndex) {
            val idx = selector.toIntOrNull()
                ?: run { log("❌ Invalid index '$selector'"); return }
            if (idx in 0 until list.size) {
                list.removeAt(idx)
                log("✓ Removed item at index $idx from '$listName'")
            } else {
                log("❌ Index $idx out of bounds (size=${list.size})")
            }
        } else {
            val toRemove = list.find { elem ->
                val idProp = elem::class.members.firstOrNull { it.name == "id" } ?: return@find false
                idProp.call(elem) == selector
            }
            if (toRemove != null) {
                list.remove(toRemove)
                log("✓ Removed item with id='$selector' from '$listName'")
            } else {
                log("❌ No item with id='$selector' found in '$listName'")
            }
        }
    } catch (e: Exception) {
        log("Remove failed: ${e.message}")
    }
}
fun update(input: String) {
    try {
        log("Raw input: '$input'")
        // 1) Match: listName, (id or index), restOfFields
        val pattern = Regex(
            """^\s*([A-Za-z]\w*)\s*,?\s*(?:id\s*=\s*([^\s,]+)|index\s*=\s*(\d+))\s*(?:,\s*(.*))?$""",
            RegexOption.IGNORE_CASE
        )
        val match = pattern.matchEntire(input)
        if (match == null) {
            log("❌ Input didn't match expected pattern")
            return
        }

        val (rawList, rawId, rawIndex, rawFields) = match.destructured
        val listName = rawList.lowercase()
        log("✓ target list = '$listName'")
        val isByIndex = rawIndex.isNotBlank()
        val selector = if (isByIndex) rawIndex else rawId
        log("✓ selecting by ${if (isByIndex) "index" else "id"} = '$selector'")

        // 2) Get the SnapshotStateList from Blist
        val listProp = Blist::class.members
            .firstOrNull { it.name.equals(listName, ignoreCase = true) }
            ?: run { log("❌ No list named '$listName'"); return }
        val list = listProp.call(Blist) as? SnapshotStateList<Any>
            ?: run { log("❌ Property '$listName' is not a SnapshotStateList"); return }

        // 3) Locate the item
        val item = if (isByIndex) {
            val idx = selector.toIntOrNull()
                ?: run { log("❌ Invalid index '$selector'"); return }
            list.getOrNull(idx)
        } else {
            list.find { elem ->
                val idProp = elem::class.members.firstOrNull { it.name == "id" } ?: return@find false
                idProp.call(elem) == selector
            }
        } ?: run { log("❌ No item found for '$selector'"); return }

        log("✓ Found item: $item")

        // 4) Parse and apply each field=value from rawFields
        if (!rawFields.isNullOrBlank()) {
            val kvRegex = Regex("""(\w+)\s*=\s*([^\s,]+)""")
            kvRegex.findAll(rawFields).forEach { m ->
                val field = m.groupValues[1].lowercase()
                val value = m.groupValues[2]
                log("→ updating field '$field' to '$value'")

                val member = item::class.members.firstOrNull { it.name.equals(field, true) }
                    ?: run { println("   • no member '$field' on item"); return@forEach }
                val state = member.call(item)
                if (state is MutableState<*>) {
                    val parsed: Any? = when (state.value) {
                        is Boolean -> value.equals("true", true)
                        is Int     -> value.toIntOrNull()
                        is Float   -> value.toFloatOrNull()
                        is Double  -> value.toDoubleOrNull()
                        else       -> value
                    }
                    if (parsed == null) {
                        log("   • failed to parse '$value' for ${state.value}")
                    } else {
                        @Suppress("UNCHECKED_CAST")
                        (state as MutableState<Any>).value = parsed
                        log("   • set '${field}' = $parsed")
                    }
                } else {
                    log("   • member '$field' is not a MutableState")
                }
            }
        } else {
            log("✓ no additional fields to update")
        }
    } catch (e: Exception) {
        log("Update failed: ${e.message}")
    }
}



/*
? Call on app start once             where all lists sit [object]
    UniversalListManager.initialize(context, Blist)

? FOR IT TO BE GLOBAL MUST DO THIS
    data class Item(
    var name: MutableState<String> = mutableStateOf(""),
    var done: MutableState<Boolean> = mutableStateOf(false)
)

2) Create a registry object holding all your lists:
object Blist {
    val shoppingList = mutableStateListOf<Item>()
    val tasksList    = mutableStateListOf<Item>()
}

! INITIALIZING AND SAVING TAKEN CARE OF

* */
object UniversalListManager {
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val gson = Gson()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var saveJob: Job? = null

    fun initialize(
        context: Context,
        registry: Any,
        prefsName: String = "MyPrefs",
        intervalMs: Long = 1000L
    ) {
        prefs = context.applicationContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        editor = prefs.edit()
        loadAll(registry)
        startAutosave(registry, intervalMs)
    }

    // Load each SnapshotStateList from JSON
    private fun loadAll(registry: Any) {
        registry::class.memberProperties
            .filterIsInstance<KProperty1<Any, Any?>>()
            .forEach { prop ->
                prop.isAccessible = true
                val rawList = prop.getter.call(registry)
                if (rawList is SnapshotStateList<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val stateList = rawList as SnapshotStateList<Any?>
                    val json = prefs.getString(prop.name, null) ?: "[]"
                    val type = object : TypeToken<List<Map<String, Any?>>>() {}.type
                    val simpleList: List<Map<String, Any?>> = gson.fromJson(json, type)

                    stateList.clear()

                    val elemKClass = prop.returnType.arguments
                        .first().type?.classifier as? KClass<Any> ?: return@forEach

                    simpleList.forEach { map ->
                        val instance = elemKClass.createInstance()
                        elemKClass.memberProperties
                            .filterIsInstance<KProperty1<Any, Any?>>()
                            .forEach { field ->
                                field.isAccessible = true
                                val fieldVal = field.getter.call(instance)
                                if (fieldVal is MutableState<*>) {
                                    @Suppress("UNCHECKED_CAST")
                                    (fieldVal as MutableState<Any?>).value = map[field.name]
                                }
                            }
                        stateList.add(instance)
                    }
                }
            }
    }

    // Start a background job to save periodically
    private fun startAutosave(registry: Any, intervalMs: Long) {
        if (saveJob?.isActive == true) return
        saveJob = scope.launch {
            while (isActive) {
                saveOnce(registry)
                delay(intervalMs)
            }
        }
    }

    // Save all SnapshotStateList properties once
    private fun saveOnce(registry: Any) {
        registry::class.memberProperties
            .filterIsInstance<KProperty1<Any, Any?>>()
            .forEach { prop ->
                prop.isAccessible = true
                val rawList = prop.getter.call(registry)
                if (rawList is SnapshotStateList<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val stateList = (rawList as SnapshotStateList<Any?>).toList()
                    val simpleList = stateList.map { item ->
                        val map = mutableMapOf<String, Any?>()
                        item!!::class.memberProperties
                            .filterIsInstance<KProperty1<Any, Any?>>()
                            .forEach { field ->
                                field.isAccessible = true
                                val fieldVal = field.getter.call(item)
                                if (fieldVal is MutableState<*>) {
                                    map[field.name] = fieldVal.value
                                }
                            }
                        map
                    }
                    val json = gson.toJson(simpleList)
                    editor.putString(prop.name, json)
                }
            }
        editor.apply()
    }
}


//endregion

//endregion

//region TEMPLATES

//region UI elements

//region ReloadButton

@Composable
fun ReloadButton(navController: NavController) {
    var hasReloaded by remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (!hasReloaded) {
                // Pop current and navigate back to the same route
                navController.currentBackStackEntry
                    ?.destination
                    ?.route
                    ?.let { route ->
                        navController.popBackStack()
                        navController.navigate(route)
                    }
                hasReloaded = true
            }
        },
        enabled = !hasReloaded,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(if (hasReloaded) "Reloaded" else "Reload")
    }
}

//endregion

//region Task..._inputs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
        .background(Color.Black), // Default background
    isNumber: Boolean = false,
    focusRequester: FocusRequester? = null,
    onDone: (() -> Unit)? = null
) {
    Spacer(modifier = Modifier.width(8.dp))

    TextField(
        value = value,
        onValueChange = {
            val parsed = if (isNumber) it.toIntOrNull()?.toString() ?: "0" else it
            onValueChange(parsed)
        },
        placeholder = { Text(placeholderText, color = Color.LightGray) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text,
            imeAction = if (onDone != null) ImeAction.Done else ImeAction.Default
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone?.invoke() }
        ),
        modifier = modifier.then(
            focusRequester?.let { Modifier.focusRequester(it) } ?: Modifier
        )
    )

    Spacer(modifier = Modifier.width(8.dp))
}



//endregion

//region MENU

@SuppressLint("RememberReturnType")
@Composable
fun MenuPopup(navController: NavController, onDismiss: () -> Unit, menuItems: List<MenuItem>) {
    Surface(
        color = Color(0xFF000000),
        modifier = Modifier
            .width(200.dp)
            .padding(end = 1.dp)
            .drawWithContent {
                drawContent()
                drawRect(
                    color = Color(0xFFFFD700),
                    topLeft = Offset(size.width - 1.dp.toPx(), 0f),
                    size = Size(1.dp.toPx(), size.height)
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            menuItems.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .clickable {
                            item.onClick()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if (item.label == "Premium") Color(0xFFFFD700) else Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = item.label,
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Productivity Shield",
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }
    }
}
data class MenuItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

fun launchSupportEmail(context: Context) {

    //the inputs, work like you think
    val subject = "bug: x"
    val body = buildString {
        append("help, x is not working;\n\n")
        append("Phone Info:\n")
        append("Manufacturer: ${Build.MANUFACTURER}\n")
        append("Model: ${Build.MODEL}\n")
        append("SDK: ${Build.VERSION.SDK_INT}\n")
        append("Version: ${Build.VERSION.RELEASE}\n")
    }
    val email = arrayOf("productivity.shield@gmail.com")


    //region SENDS THE EMAIL
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, email)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    val Action = Intent.createChooser(intent, "Send Email").apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(Action)
    //endregion
}

@Composable
fun getMenuItems(navController: NavController): List<MenuItem> {
    val context = LocalContext.current

    return listOf(
        MenuItem("Support", Icons.Filled.Email) {
            launchSupportEmail(context)
        },
        MenuItem("Statistics", Icons.Filled.QueryStats) { navController.navigate("Statistics") },
        MenuItem("Settings", Icons.Filled.Settings) { navController.navigate("Settings") },
        MenuItem("Premium", Icons.Filled.Landscape) { navController.navigate("Premium") }
    )
}

//endregion

//region Submit_Icon

@Composable
fun Submit_Icon(){
    Icon(

        imageVector = Icons.Filled.ArrowUpward,
        contentDescription = "Submit Task",
        tint = Color.White
    )
}

//endregion

//region COLLAPSABLE_cONTENTButton

@Stable
@Composable
fun ShowMore(
    modifier: Modifier = Modifier,
    title: String = "Show more",
    initiallyExpanded: Boolean = false,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }
    val rotation by animateFloatAsState(targetValue = if (expanded) 90f else 0f)

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = if (expanded) "Show less" else "Show more",
                modifier = Modifier.rotate(rotation),
                tint = Color(0xFFFFD700) // GOLD icon
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                color = Color(0xFFFFD700) // GOLD text
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(
                animationSpec = tween(durationMillis = 400),
                expandFrom = Alignment.Top
            ),
            exit = shrinkVertically(
                animationSpec = tween(durationMillis = 300),
                shrinkTowards = Alignment.Top
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, top = 4.dp) // indent content nicely
            ) {
                content()
            }
        }
    }
}

//endregion

//region TOPBAR

@Composable
fun TopBar(navController: NavController, onClick: () -> Unit) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Menu button
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFFFFD700)
                )
            }


            // Right: Nav buttons
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigate("lock") }) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock",
                        tint = Color(0xFFFFD700)
                    )
                }
            }
        }
    }
}
//endregion

//endregion

//region SCREENS

//region SETTINGS SCREEN
//region SETTINGS UI

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Settings_Example() {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    SettingsScreen(
        titleContent  = {Text("HEADER")} ,
        onSearchClick = { }
    ) {

        SettingItem(
            icon = Icons.Outlined.Tune,
            title = "Defaults",
            subtitle = "Task, System",
            onClick = { Global1.navController.navigate("S_DefaultsScreen")  }
        )

        SettingItem(
            icon = Icons.Outlined.LockOpen,
            title = "Usage permissions",
        )
    }
}

//region UI TOOLKIT

@Composable
fun OnOffSwitch(isOn: Boolean, onToggle: (Boolean) -> Unit) {
    Switch(
        checked = isOn,
        onCheckedChange = onToggle,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.Blue,
            uncheckedThumbColor = Color.Gray
        )
    )
}

@Composable
fun PermissionsButton(
    isEnabled: Boolean,
    onEnable: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = if (isEnabled) Color.DarkGray else Color(0xFFFFD700),
        contentColor = if (isEnabled) Color.LightGray else Color.Black,
        disabledContainerColor = Color.DarkGray,
        disabledContentColor = Color.Gray
    )
    if (isEnabled)
    {Text(text = "✓", fontWeight = FontWeight.Bold, color = Color.Green) }

    if (!isEnabled) {
        Button(
            onClick = { onEnable() },
            enabled = !isEnabled,
            shape = shape,
            colors = buttonColors,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            border = BorderStroke(1.dp, if (isEnabled) Color.Gray else Color.Black),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            interactionSource = remember { MutableInteractionSource() },
            modifier = Modifier
                .height(40.dp)
        ) {
            Text(text = if (isEnabled) "✓" else "Enable", fontWeight = FontWeight.Bold)
        }
    }


}


@Composable
fun SettingItem(
    title: String,
    subtitle: String? = null,
    endContent: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Settings,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(Color(0xFF121212), shape = RoundedCornerShape(12.dp))
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFFD700),
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
            subtitle?.let {
                Text(text = it, color = Color.Gray, fontSize = 12.sp)
            }
        }
        endContent?.invoke()
    }
}

@Composable
fun settingsHeader(
    titleContent: @Composable () -> Unit,
    onSearchClick: () -> Unit,
    onBackClick: () -> Unit = {},
    showBack: Boolean = true,
    showSearch: Boolean = true,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true
) {
    val ui = rememberSystemUiController()
    var DisableTB_Button by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        ui.setStatusBarColor(Color.Black, darkIcons = false)
    }

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            /* 🔒 Btn Cooldown
             *
             * ⚠️ Problem:
             * Rapid back clicks (especially after popupBackStack or screen transitions)
             * sometimes cause a full black screen in Compose — likely due to
             * navigation state confusion or overlapping recompositions.
             *
             * ✅ Fix:
             * We temporarily disable this button after it's clicked once,
             * using the `DisableTB_Button` flag, to prevent double-taps.
             *
             * This protects Compose from crashing or losing its state tree.
             */
            if (showBack ) {
                IconButton(onClick = { if (DisableTB_Button) { }; if (!DisableTB_Button) { DisableTB_Button = true

                        onBackClick()
                        Global1.navController.popBackStack()
                    }

                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFFFD700)
                    )
                }
            }


            // Title content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = if (showBack) 8.dp else 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                titleContent()
            }

            // Search icon
            if (showSearch) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFFFFD700)
                    )
                }
            }
        }

        if (showDivider) {
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}


@Composable
fun SettingsScreen(
    titleContent: @Composable () -> Unit,
    onSearchClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    showBack: Boolean = true,
    showSearch: Boolean = true,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    content: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val baseModifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .clipToBounds()
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            focusManager.clearFocus()
        }



    LazyColumn(
        modifier = baseModifier.then(modifier)
    ) {
        item {
            settingsHeader(
                titleContent = titleContent,
                onSearchClick = onSearchClick,
                onBackClick = onBackClick,
                showBack = showBack,
                showSearch = showSearch,
                showDivider = showDivider
            )
        }
        item {
            content()
        }
    }
}



//endregion


//endregion

//endregion


/* DEFAULT

@Composable
fun Screen(navController: NavHostController){
var Content = null
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp)
            .clipToBounds()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) { IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }


            Content

        }
    }
}

*/
//endregion

//region POP UPS

/* Example
LazyPopup(
    onDismiss = { },
    onConfirm = { },
    title = "Look...",
    message = "Me, one lazy dude... etc..."
)
*/
@Composable
fun LazyPopup(
    show: MutableState<Boolean>,
    onDismiss: (() -> Unit)? = null,
    title: String = "Info",
    message: String,
    content: (@Composable () -> Unit)? = null,
    showCancel: Boolean = true,
    showConfirm: Boolean = true,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {

    if (!show.value) {return}

    AlertDialog(
        onDismissRequest = {
            onDismiss?.invoke()

            show.value = false
        },
        title = { Text(title) },
        text = { if (content == null) {Text(message) }
            else {
                content()
            }

               },
        confirmButton = {
            if (showConfirm) {
                TextButton(onClick = {
                    onConfirm?.invoke()
                    show.value = false
                }) {
                    Text("OK")
                }
            }

        },
        dismissButton = if (showCancel) {
            {
                TextButton(onClick = {
                    onCancel?.invoke()
                    show.value = false
                }) {
                    Text("Cancel")
                }
            }
        } else null
    )

}

@Composable
fun LazyMenu(
    onDismiss: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val visible = remember { mutableStateOf(false) }
    val internalVisible = remember { mutableStateOf(false) }

    // Trigger showing/hiding Popup
    LaunchedEffect(Bar.ShowMenu) {
        if (Bar.ShowMenu) {
            visible.value = true
            delay(16)
            internalVisible.value = true
        } else {
            internalVisible.value = false
            delay(200) // Wait for animation out
            visible.value = false
        }
    }

    if (!visible.value) return

    // Slide offset
    val offsetX by animateDpAsState(
        targetValue = if (internalVisible.value) 0.dp else -Bar.halfWidth,
        animationSpec = tween(durationMillis = 200),
        label = "MenuSlide"
    )

    // Background fade
    val backgroundAlpha by animateFloatAsState(
        targetValue = if (internalVisible.value) 0.4f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "Fade"
    )

    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = {
            onDismiss?.invoke()
            Bar.ShowMenu = false
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = backgroundAlpha))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                onDismiss?.invoke()
                    Bar.ShowMenu = false
                }
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToPx(), 0) }
                .width(Bar.halfWidth)
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .background(Color.DarkGray)
        ) {
            content()
        }
    }
}



//endregion

//endregion








