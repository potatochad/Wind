package dev.hossain.keepalive

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AppOpsManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Process
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KMutableProperty1

//region NavController

fun NavGraphBuilder.OtherScreens(){
    composable("TrueMain"){
        TrueMain()
    }
}

//endregion
//region OnAppStart

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OnAppStart() {

}

//endregion



//region TrueMain

@Composable
fun TrueMain(){

}

//endregion




/* TODO

DO THE SETUP NOW
TEST CONFIGURE
AND GET TO POINT WHERE CAN SET YOUR FORGROUND SERVICE TODO ANYTHING YOU WANT, UPLOAD IT TO GITHUB
TODO AS TEMPLATE FOR FUTURE THINGS

ADDDDDDDDDDDDDDDDDDDD MY FUN MANAGER THING/ GREAT FOR EVERYTHING
 */







// DOING SOME LEARNING NOW



//region DATA ITEMS
object ItemFactory {
    fun createItemFactory(
        defaults: Map<String, Any> = emptyMap()
    ): (Map<String, Any>) -> Map<String, Any> {
        return { data ->
            val id = data["id"] as? String ?: UUID.randomUUID().toString()
            defaults + data + mapOf("id" to id)
        }
    }
}

//endregion

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID
import kotlinx.coroutines.launch

class UniversalManager(
    context: Context,
    private val key: String,
    private val lifecycleScope: LifecycleCoroutineScope
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    private val gson = Gson()
    private var data: MutableList<Map<String, Any>> = mutableListOf()

    private fun readRaw(): String? =
        prefs.getString(key, null)

    private fun writeRaw(value: String) =
        prefs.edit().putString(key, value).apply()

    private suspend fun load() {
        val raw = readRaw()
        data = if (raw != null) {
            gson.fromJson(
                raw,
                object : TypeToken<MutableList<Map<String, Any>>>() {}.type
            )
        } else {
            mutableListOf()
        }
    }

    private suspend fun save() {
        writeRaw(gson.toJson(data))
    }

    fun getById(id: String, callback: (Map<String, Any>?) -> Unit) {
        lifecycleScope.launch {
            load()
            callback(data.find { it["id"] == id })
        }
    }

    fun add(item: MutableMap<String, Any>, callback: (MutableMap<String, Any>) -> Unit) {
        lifecycleScope.launch {
            load()
            if (!item.containsKey("id")) {
                item["id"] = UUID.randomUUID().toString()
            }
            data.add(item)
            save()
            callback(item)
        }
    }

    fun remove(id: String, callback: () -> Unit = {}) {
        lifecycleScope.launch {
            load()
            data = data.filter { it["id"] != id }.toMutableList()
            save()
            callback()
        }
    }

    fun update(id: String, fields: Map<String, Any>, callback: (Map<String, Any>) -> Unit) {
        lifecycleScope.launch {
            load()
            val idx = data.indexOfFirst { it["id"] == id }
            if (idx == -1) throw IllegalArgumentException("No item with id '$id'")
            val updated = data[idx] + fields
            data[idx] = updated
            save()
            callback(updated)
        }
    }

    fun createOrUpdate(
        id: String? = null,
        defaults: Map<String, Any> = emptyMap(),
        fields: Map<String, Any> = emptyMap(),
        callback: (Map<String, Any>) -> Unit
    ) {
        lifecycleScope.launch {
            load()
            val realId = id ?: UUID.randomUUID().toString()
            val existing = data.find { it["id"] == realId }
            if (existing != null) {
                update(realId, fields, callback)
            } else {
                val item = mutableMapOf<String, Any>("id" to realId) + defaults + fields
                add(item as MutableMap<String, Any>, callback)
            }
        }
    }

    fun getAll(callback: (List<Map<String, Any>>) -> Unit) {
        lifecycleScope.launch {
            load()
            callback(data)
        }
    }
}












































































//IMPORTED FROM TEST7 PROJECT




//region USER MANUAL

/* HOW USE REGION

    //region

    requires a space of one between // and code to work well

    //endregion

*/

/*HOW CODE

    use region, one file, and yea
    plus, like to have functions, a section for universal functions

*/

/*HOW DATA manage

    use, object, and the synch function always

    viewmodel-only, only for rotating screens!
*/

//region CHEET CODES

/* Synched:
MutableState updates automatically,
doesn't cost cpu, not using loop


Example usage:
val syncedValue = Synched { someValue }

syncedValue will update whenever someValue changes.
updating composable
*/

/*
log:

Example usage:
log("Button clicked")
log("Error happened", "ErrorTag")
*/

/* Background alarms/reminders/onces
Background alarms: Schedule alarms that trigger at specific times.

1. Add <receiver android:name=".AlarmReceiver" /> in your AndroidManifest.xml.

Example:
AlarmScheduler.setAlarm(context, 15, 45) {
   //                 (context, hour, minute)
   // Code to run when alarm triggers
}
*/

/* SMALL THINGS
   val id: String = UUID.randomUUID().toString(),
   *a thing that exists, unique completly

 */

/* DATA MANAGEMENT LISTS
@RequiresApi(Build.VERSION_CODES.O)
object Reminder {
    var where_Store_Data = "reminders.json"

    //region BORING
    private lateinit var settingsFile: File; var data by mutableStateOf(mutableListOf<RData>()); private set
    fun init(context: Context) {
        settingsFile = File(context.filesDir, where_Store_Data)
        data = if (settingsFile.exists()) load() else mutableListOf<RData>().also { save(it) }
    }
    fun save(reminders: List<RData>) {
        val json = Json.encodeToString(ListSerializer(RData.serializer()), reminders)
        settingsFile.writeText(json)
    }
    fun load(): MutableList<RData> {
        val json = settingsFile.readText()
        return Json.decodeFromString(ListSerializer(RData.serializer()), json).toMutableList()
    }
    //endregion

    fun update(id: String, fieldName: String, valueString: String) {
        try {
            val index = data.indexOfFirst { it.id == id }
            if (index == -1) throw IllegalArgumentException("No reminder with id '$id' found")
            val reminder = data[index]
            val kClass = RData::class
            val prop = kClass.memberProperties
                .find { it.name == fieldName } as? KMutableProperty1<RData, Any?>
                ?: throw IllegalArgumentException("No mutable property '$fieldName' found")

            val typedValue: Any = when (prop.returnType.classifier) {
                Boolean::class -> valueString.toBooleanStrictOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Boolean for '$fieldName'")
                Long::class -> valueString.toLongOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Long for '$fieldName'")
                Int::class -> valueString.toIntOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Int for '$fieldName'")
                String::class -> valueString
                else -> throw IllegalArgumentException("Unsupported type for '$fieldName'")
            }

            val newReminder = reminder.copy()
            prop.setter.call(newReminder, typedValue)
            val newData = data.toMutableList()
            newData[index] = newReminder

            save(newData)
            data = newData

        } catch (e: Exception) {
            println("updateField CRASH: ${e.message}")
            throw e
        }
    }
    fun add(reminder: RData) {
        val newData = data.toMutableList()
        val nextOrder = newData.size + 1
        val reminderWithOrder = reminder.copy(order = nextOrder)
        newData.add(reminderWithOrder)

        save(newData)
        data = newData
    }
    fun remove(id: String) {
        val newData = data.filter { it.id != id }.toMutableList()
        save(newData)
        data = newData
    }
}
*/

/* DATA MANAGEMENT ONCES
object AppSettings {
    var where_Store_Data = "settings.json"
    var Data_Class = SData()

    //region NEVER TO

    private lateinit var settingsFile: File ; var data by mutableStateOf(Data_Class) ; private set
    fun init(context: Context) {
        settingsFile = File(context.filesDir, where_Store_Data)
        data = if (settingsFile.exists()) load() else Data_Class.also { save(it) }
    }
    fun save(settings: SData) {
        val json = Json.encodeToString(SData.serializer(), settings)
        settingsFile.writeText(json)
    }
    fun load(): SData {
        val json = settingsFile.readText()
        return Json.decodeFromString(SData.serializer(), json)
    }

    //endregion

    fun update(fieldName: String, valueString: String) {
        try {
            val kClass = SData::class
            val prop = kClass.memberProperties
                .find { it.name == fieldName } as? KMutableProperty1<SData, Any?>
                ?: throw IllegalArgumentException("No mutable property '$fieldName' found")
            val typedValue: Any = when (prop.returnType.classifier) {
                Boolean::class -> valueString.toBooleanStrictOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Boolean for '$fieldName'")
                Long::class -> valueString.toLongOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Long for '$fieldName'")
                Int::class -> valueString.toIntOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Int for '$fieldName'")
                String::class -> valueString
                else -> throw IllegalArgumentException("Unsupported type for '$fieldName'")
            }
            val newData = data.copy()
            prop.setter.call(newData, typedValue)
            save(newData)

            data = newData

        } catch (e: Exception) {
            println("updateField CRASH: ${e.message}")
            throw e
        }
    }
}

*/

//endregion

//region How Commit/push
/* Commits
use them often:
- on fail; (the last on fail, use that)
    come back if mess, up
- or just saving, which also do often

*/
/* Push
push every day, before bed,
just saves your thing online, like backup
*/
//endregion

//endregion







//region DONE's

//region SwipeContainer

@OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SwipeContainer(innerPadding: PaddingValues) {
    // PagerState no longer takes pageCount here
    val pagerState = rememberPagerState(
        initialPage = 0
    )
    var navController = rememberNavController()

    HorizontalPager(
        count    = 2,
        state    = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) { page ->
        if (page == 0) MainScreenContent()
        else           TasksList(navController = navController)
    }
}

//endregion

//region DATA MANAGEMENT

//region Settings Data

@Serializable
data class SData(
    /* var x = AppSettings.data.DefaultSchedule
       AppSettings.update("DefaultDuration", newValue.toString())
    */
    var DefaultDuration: Long = 10L,
    var DefaultSchedule: String = "daily",
    var DefaultValue: Int = 10,

    var AskUsagePermissions: Boolean = true
)

object AppSettings {
    var where_Store_Data = "settings.json"
    var Data_Class = SData()

    //region NEVER TO

    private lateinit var settingsFile: File; var data by mutableStateOf(Data_Class) ; private set
    fun init(context: Context) {
        settingsFile = File(context.filesDir, where_Store_Data)
        data = if (settingsFile.exists()) load() else Data_Class.also { save(it) }
    }
    fun save(settings: SData) {
        val json = Json.encodeToString(SData.serializer(), settings)
        settingsFile.writeText(json)
    }
    fun load(): SData {
        val json = settingsFile.readText()
        return Json.decodeFromString(SData.serializer(), json)
    }

    //endregion

    fun update(fieldName: String, valueString: String) {
        try {
            val kClass = SData::class
            val prop = kClass.memberProperties
                .find { it.name == fieldName } as? KMutableProperty1<SData, Any?>
                ?: throw IllegalArgumentException("No mutable property '$fieldName' found")
            val typedValue: Any = when (prop.returnType.classifier) {
                Boolean::class -> valueString.toBooleanStrictOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Boolean for '$fieldName'")
                Long::class -> valueString.toLongOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Long for '$fieldName'")
                Int::class -> valueString.toIntOrNull()
                    ?: throw IllegalArgumentException("Can't convert '$valueString' to Int for '$fieldName'")
                String::class -> valueString
                else -> throw IllegalArgumentException("Unsupported type for '$fieldName'")
            }
            val newData = data.copy()
            prop.setter.call(newData, typedValue)
            save(newData)

            data = newData

        } catch (e: Exception) {
            println("updateField CRASH: ${e.message}")
            throw e
        }
    }
}

//endregion

//endregion

//region TIMER logic

@Composable
fun Reset_Button_TimerOnly(onReset: () -> Unit) {
    val timerViewModel: Timer = viewModel()
    val context = LocalContext.current

    // Define the updated timer data when resetting.
    val updatedTimerData = TimerData(
        isTimerRunning = "false",
        lastUpdateTime = null,
        elapsedTime = 0L,
        currentTaskIndex = 0,
        currentTime = null
    )

    // Update the timer data and notify the parent via onReset.
    IconButton(
        onClick = {
            forceEnableBlocksLocally(context)
            timerViewModel.overwritte_timer(context, updatedTimerData)
            onReset() // Notify parent to update its states
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Reset Timer",
            modifier = Modifier.size(32.dp)
        )
    }
}


//endregion

//region Blocking logic

fun disableBlocksLocally(context: Context) {
    val prefs = context.getSharedPreferences("blocks_toggle", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("blocks_enabled", false).apply()
    Log.d("LocalStorage", "Blocks have been disabled in local storage.")
}
fun enableBlocksLocally(context: Context) {
    val prefs = context.getSharedPreferences("blocks_toggle", Context.MODE_PRIVATE)
    val lastEnabledDate = prefs.getString("last_enabled_date", null)

    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    if (today == lastEnabledDate) {
        Log.d("LocalStorage", "Blocks already enabled today. Skipping.")
        return
    }

    // Enable blocks
    prefs.edit()
        .putBoolean("blocks_enabled", true)
        .putString("last_enabled_date", today)
        .apply()

    Log.d("LocalStorage", "Blocks have been enabled and date updated to $today.")

    // (Optional) If you still want to schedule future alarms here, you can — but based on what you said, maybe not needed.
}
fun forceEnableBlocksLocally(context: Context) {
    context.getSharedPreferences("blocks_toggle", Context.MODE_PRIVATE)
        .edit()
        .putBoolean("blocks_enabled", true)     // no date guard
        .remove("last_enabled_date")            // clear the flag so normal enable() still works
        .apply()

    Log.d("LocalStorage", "Blocks force-enabled (date check bypassed).")
}

//endregion

//region UI elements

//region Blocking UI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TOP_BLOCK_BAR(
    navController: NavHostController,
    blocksEnabled: Boolean,
    tasksFinished: Boolean,
    onToggle: (Boolean) -> Unit,
    setBlocksEnabled: (Boolean) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = { Text("Blocks", color = Color.White) },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF6200EE)),
        actions = {
            // "Settings" icon
            IconButton(onClick = {
                Log.d(TAG, "Navigating to settingsBLOCK")
                navController.navigate("settingsBLOCK")
            }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            On_Off_Blocks_Button()

        }
    )
}


//endregion

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

//region Timer UI

@Composable
fun StartButton() {
    var isPressed by remember { mutableStateOf(false) }
    val timerViewModel: Timer = viewModel()
    val context = LocalContext.current

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scaleAnimation"
    )

    Spacer(modifier = Modifier.width(12.dp))
    Button(
        onClick = {
            isPressed = true
            // Set the timer state immediately
            timerViewModel.isTimerRunning.value = "true"
            // Optionally, update persistent storage as needed
            val timerInfo = TimerData(
                isTimerRunning = "true",
                lastUpdateTime = System.currentTimeMillis(),
                elapsedTime = 0L,
                currentTaskIndex = timerViewModel.currentTaskIndex.value ?: 0,
                currentTime = System.currentTimeMillis()
            )
            timerViewModel.overwritte_timer(context, timerInfo)
        },
        modifier = Modifier.scale(scale),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = "Start", color = Color.Black, fontSize = 16.sp)
    }
    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150L)
            isPressed = false
        }
    }
}

@Composable
fun WhichButtonShow_TimerEdition() {
    val timerViewModel: Timer = viewModel()
    Spacer(modifier = Modifier.height(24.dp))
    // Directly read the current state from the view model.
    if (timerViewModel.isTimerRunning.value == "true") {
        StopButton()
    } else {
        StartButton()
    }
}

@Composable
fun StopButton() {
    var isPressed by remember { mutableStateOf(false) }
    val timerViewModel: Timer = viewModel()
    val context = LocalContext.current

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scaleAnimation"
    )

    Spacer(modifier = Modifier.width(12.dp))
    Button(
        onClick = {
            isPressed = true
            // Immediately update the timer state
            timerViewModel.isTimerRunning.value = "false"
            val timerInfo = TimerData(
                isTimerRunning = "false",
                lastUpdateTime = System.currentTimeMillis(),
                elapsedTime = timerViewModel.elapsedTime.value ?: 0L,
                currentTaskIndex = timerViewModel.currentTaskIndex.value ?: 0,
                currentTime = System.currentTimeMillis()
            )
            timerViewModel.overwritte_timer(context, timerInfo)
        },
        modifier = Modifier.scale(scale),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = "Stop", color = Color.Black, fontSize = 16.sp)
    }
    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150L)
            isPressed = false
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Timer_Desighn(
    tasksForTimer: State<List<Task_for_timer>>,
    currentTaskIndex: State<Int>,
    leftTime: Long,
    handleReset: () -> Unit,
    taskViewModel: All_Tasks,
    timerViewModel: Timer,
    context: Context
) {
    enableBlocksLocally(context)
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Top right reset button.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Reset_Button_TimerOnly(onReset = handleReset)
            }

            // Display current task details or a fallback message.
            if (tasksForTimer.value.isNotEmpty() && currentTaskIndex.value < tasksForTimer.value.size) {
                Text(
                    text = "${currentTaskIndex.value + 1}/${tasksForTimer.value.size}",
                    modifier = Modifier.padding(top = 6.dp)
                )
                Text(
                    text = tasksForTimer.value[currentTaskIndex.value].name,
                    modifier = Modifier.padding(top = 6.dp)
                )
                Text(
                    text = "${(leftTime * -1)} sec",
                    modifier = Modifier.padding(top = 6.dp)
                )
            } else {
                Text(
                    text = "No task available",
                    modifier = Modifier.padding(top = 8.dp)
                )
                disableBlocksLocally(context)
            }

            // Show additional UI elements based on task progress.
            if (currentTaskIndex.value < taskViewModel.todayTasks.size) {
                WhichButtonShow_TimerEdition()
            }
            TasksLeft() // Additional action buttons if needed.
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksLeft() {
    val taskViewModel: All_Tasks = viewModel()
    val context = LocalContext.current

    // Load tasks from the ViewModel using LaunchedEffect
    LaunchedEffect(taskViewModel) {
        val loadedTasks = taskViewModel.load_tasks(context, "today_tasks")
        taskViewModel.todayTasks.clear()
        taskViewModel.todayTasks.addAll(loadedTasks)
    }

    // Assuming currentTaskIndex is a state variable, you need to define it
    val currentTaskIndex = remember { mutableStateOf(0) }

    // Filter tasks based on the current task index
    val tasksLeft = filterTasks(
        tasks = taskViewModel.todayTasks, // Use todayTasks from the ViewModel
        attribute = "order",
        operator = ">",
        value = currentTaskIndex.value,
        sortBy = "order"
    )

    // Display the filtered tasks in a column (LazyColumn)
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "expand_transition")

    val arrowRotation by transition.animateFloat(label = "arrow_rotation") {
        if (it) 180f else 0f
    }

    val columnHeight by transition.animateDp(label = "column_height") {
        if (it) 200.dp else 0.dp
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Tasks Left",
                style = MaterialTheme.typography.titleMedium,
            )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    modifier = Modifier.rotate(arrowRotation)
                )
            }
        }


        AnimatedVisibility(visible = expanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = columnHeight)
            ) {
                items(tasksLeft) { task ->
                    TaskRow(task)
                }
            }
        }
    }
}
@Composable
fun TaskRow(task: Task) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Task Name: ${task.name}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Order: ${task.order}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Duration: ${task.duration} s", style = MaterialTheme.typography.bodySmall)
    }
}

//endregion

//region SETTINGS UI

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Settings(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    SettingsScreen(
        navController = navController,
        title = "Settings",
        onSearchClick = { }
    ) {

        SettingItem(
            icon = Icons.Outlined.Tune,
            title = "Defaults",
            subtitle = "Task, System",
            onClick = { navController.navigate("S_DefaultsScreen")  }
        )

        SettingItem(
            icon = Icons.Outlined.LockOpen,
            title = "Usage permissions",
            endContent = {
                var isOn by Synched { AppSettings.data.AskUsagePermissions }

                OnOffSwitch(isOn) {
                    AppSettings.update("AskUsagePermissions", it.toString())
                    isOn = it
                }
                Spacer(modifier = Modifier.width(35.dp))
            }
        )
    }
}

@Composable
fun S_DefaultsScreen(navController: NavHostController){
    SettingsScreen(
        navController = navController,
        title = "Defaults",
        onSearchClick = { }
    ) {
        SettingItem(
            icon = Icons.Outlined.Schedule,
            title = "Default duration",
            endContent = {
                TaskInputField(
                    value = AppSettings.data.DefaultDuration.toString(),
                    onValueChange = { newValue ->
                        AppSettings.update("DefaultDuration", newValue)
                    },
                    placeholderText = "Duration",
                    isNumber = true,
                    modifier = Modifier.widthIn(max = 65.dp)
                )
            }
        )
        SettingItem(
            icon = Icons.Outlined.AttachMoney,
            title = "Default value",
            endContent = {
                TaskInputField(
                    value = AppSettings.data.DefaultValue.toString(),
                    onValueChange = { AppSettings.update("DefaultValue", it) },
                    placeholderText = "Value",
                    isNumber = true,
                    modifier = Modifier.widthIn(max = 65.dp)
                )
            }
        )
        SettingItem(
            icon = Icons.Outlined.CalendarToday,
            title = "Default schedule",
            endContent = {
                var showPopup by remember { mutableStateOf(false) }
                Text(
                    "Daily",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable { showPopup = true }
                        .padding(vertical = 16.dp)
                        .padding(end = 16.dp)
                )
                if (showPopup) {
                    AlertDialog(
                        onDismissRequest = { showPopup = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showPopup = false
                            }) { Text("OK") }
                        },
                        title = { Text("Look...") },
                        text = { Text("Me, one lazy dude, ok, i know productivity app, but do no judge, we all make mistakes from time to time, mine being that i am lazy, and this app, is to help me solve that, so if you really care about this feature i will implement it in the future, give me a dm, and paypal me 100 bucks, i will get it done that week, otherwise, lazyyyyyyyy, wait a year or something") })
                }
            }
        )
    }
}

//region UI TOOLKIT

@Composable
fun CheckUsageStatsPermission() {
    val context = LocalContext.current
    var showPopup by remember { mutableStateOf(false) }

    //Checks permissions, null=SHOW POPUP TRUE
    LaunchedEffect(Unit) {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        if (mode != AppOpsManager.MODE_ALLOWED && AppSettings.data.AskUsagePermissions) {
            showPopup = true
        }
    }

    //CHECK SETTINGS IF SHOULD SHOW
    showPopup = AppSettings.data.AskUsagePermissions
    if (showPopup) {
        AlertDialog(onDismissRequest = { showPopup = false }, dismissButton = { TextButton(onClick = { showPopup = false; AppSettings.update("AskUsagePermissions", false.toString()) }) { Text("Cancel") } }, confirmButton = { TextButton(onClick = { showPopup = false; context.startActivity(
            Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }) }) { Text("OK") } }, title = { Text("Permissions") }, text = { Text("Grant usage permissions so blocking and app tracking can work.\n\nYou can enable it later in settings anytime.") })
    }
}
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
    title: String,
    onSearchClick: () -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick) {   // Back button on the left
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFFFD700) // gold
                )
            }

            Text(
                text = title,
                color = Color.White, // gold vibe kept
                fontWeight = FontWeight.ExtraBold,
                fontSize = 23.sp,
                modifier = Modifier.weight(1f),   // Take available space between buttons
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFFFFD700) // gold
                )
            }
        }
    }
}


@Composable
fun SettingsScreen(
    navController: NavController,
    title: String,
    onSearchClick: () -> Unit = {},
    modifier: Modifier = Modifier,
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
                title = title,
                onSearchClick = onSearchClick,
                onBackClick = { navController.popBackStack() }
            )
        }
        item {
            content()
        }
    }
}

//endregion


//endregion

//region ReminderUI

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderPage(navController: NavController) {
    var ReminderValue by remember { mutableStateOf(AppSettings.data.DefaultValue.toString()) }
    var scheduleSelected by remember { mutableStateOf(AppSettings.data.DefaultSchedule) }
    var ReminderName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp)
            .clipToBounds()
    ) {
        // Input Row: Task name field and an arrow button to add task.
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TaskInputField(
                value = ReminderName,
                onValueChange = { ReminderName = it },
                placeholderText = "Enter name",
                focusRequester = focusRequester,
                onDone = {
                    if (ReminderName.isNotBlank()) {
                        Reminder.add(
                            RData(
                                name = ReminderName,
                                schedule = scheduleSelected,
                                startDate = startDate,
                                value = ReminderValue.toInt()
                            )
                        )

                        keyboardController?.hide()
                        navController.popBackStack()
                    }
                }
            )


            IconButton(
                onClick = {
                    if (ReminderName.isNotBlank()) {
                        Reminder.add(
                            RData(
                                name = ReminderName,
                                schedule = scheduleSelected,
                                startDate = startDate,
                                value = ReminderValue.toInt()
                            )
                        )
                        keyboardController?.hide()
                    }
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
                    .background(
                        color = if (ReminderName.isNotBlank()) Color(0xFFFFD700) else Color.DarkGray,
                        shape = CircleShape
                    )
            ) {
                Submit_Icon()
            }
        }

        Spacer(Modifier.height(12.dp))

        ShowMore(title = "Advanced") {
            // Input  Duration/value row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TaskInputField(
                    value = ReminderValue,
                    onValueChange = { ReminderValue = it },
                    placeholderText = "Value",
                    isNumber = true,
                    modifier = Modifier.widthIn(max = 125.dp)
                )

            }


            // ScheduleRow displays the calendar and updates scheduleSelected and startDate.
            ScheduleRow(
                onDateSelected = { dateString ->
                    Log.d("ScheduleRow", "Selected schedule: $dateString")
                    scheduleSelected = dateString
                },
                start_Date = { startDateString ->
                    Log.d("ScheduleRow", "Selected start date: $startDateString")
                    startDate = startDateString
                }
            )
        }
    }
}

//endregion

//region BORING

//region Task..._inputs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputField(
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
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color(0xFFFFD700),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
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
        MenuItem("Statistics", Icons.Filled.QueryStats) { navController.navigate("MoneyStatistics") },
        MenuItem("Settings", Icons.Filled.Settings) { navController.navigate("settings") },
        MenuItem("Premium", Icons.Filled.Landscape) { navController.navigate("premium") }
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
    val totalValueViewModel: TotalValueViewModel = viewModel()
    val total by totalValueViewModel.totalValue.observeAsState("0")
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

            TotalMoney(total)

            // Right: Nav buttons
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigate("blockPage") }) {
                    Icon(
                        imageVector = Icons.Default.Block,
                        contentDescription = "Block page",
                        tint = Color(0xFFFFD700)
                    )
                }
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
@Composable
fun TotalMoney(total: String) {
    val context = LocalContext.current
    val rawHistory = remember {
        context.getSharedPreferences("tasks", Context.MODE_PRIVATE)
            .getString("task_history", "[]")!!
    }
    val logs = remember(rawHistory) { parseHistory(rawHistory) }
    val totalEarnings = logs.sumOf { it.value }
    val totalSessions = logs.size
    val totalDurationSec = logs.sumOf { it.duration }
    val valuePerHour = if (totalDurationSec > 0) {
        totalEarnings.toDouble() / totalDurationSec * 3600
    } else 0.0

    var showPopup by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = total,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )


        Box {
            Icon(
                imageVector = Icons.Default.AttachMoney,
                contentDescription = "Total Money",
                tint = Color(0xFFFFD700),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showPopup = !showPopup }
            )

            DropdownMenu(
                expanded = showPopup,
                onDismissRequest = { showPopup = false },
                offset = DpOffset(x = 0.dp, y = 4.dp)
            ) {
                DropdownMenuItem(text = { Text("Hourly: $${"%.2f".format(valuePerHour)}") }, onClick = {})
                DropdownMenuItem(text = { Text("Total Sessions: $totalSessions") }, onClick = {})
                DropdownMenuItem(text = { Text("Duration: ${totalDurationSec / 60} min") }, onClick = {})
            }
        }
    }
}

//endregion

//endregion

//region TASK LIST

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksList(navController: NavHostController) {
    val viewModel: All_Tasks = viewModel()
    val currentMonth = remember {
        LocalDate.now().month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }
    // Save the ordered tasks for this day (e.g., "day_tasks_1" for day 1, etc.)

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        // Top bar + content (centered)
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = currentMonth,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFFFD700)) // Gold line
            )
            displayDayInfo(navController)
        }
    }

}


//endregion

//endregion

//endregion








//region USEFULL for future

//region GLOBAL OBJECTS, SYCHED

//  on value change force updates, does not consume cpu (none aka)
//      val x = SynchedState { false}
@Composable
fun <T> Synched(valueProvider: () -> T): MutableState<T> {
    val state = remember { mutableStateOf(valueProvider()) }

    LaunchedEffect(Unit) {
        snapshotFlow { valueProvider() }
            .collect { newValue -> state.value = newValue }
    }

    return state
}


//endregion

//region log

fun log(message: String, tag: String? = "AppLog") {
    Log.d(tag, message)
}

//endregion

//region Background alarms/reminders/onces

/*

<receiver android:name=".AlarmReceiver" />

AlarmScheduler.setAlarm(context, 15, 45) {
    println("Alarm fired at 15:45 - get to work!")
    // Put your code here to show screen over app, notification, whatever action
}

*/

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return

        Log.d("AlarmReceiver", "Alarm received: $action")

        val callback = AlarmScheduler.getCallback(action)
        if (callback != null) {
            callback.invoke()
        } else {
            Log.w("AlarmReceiver", "No callback found for action: $action")
        }
    }
}

object AlarmScheduler {
    private val actionMap = ConcurrentHashMap<String, () -> Unit>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun setAlarm(
        context: Context,
        hour: Int,
        minute: Int,
        callback: () -> Unit
    ) {
        val actionName = "ALARM_ACTION_${System.currentTimeMillis()}"
        val requestCode = actionName.hashCode()

        actionMap[actionName] = callback

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = actionName
        }

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, requestCode, intent, flags
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Log.d("AlarmScheduler", "Alarm set for $hour:$minute with action $actionName")
    }

    fun getCallback(actionName: String): (() -> Unit)? = actionMap[actionName]
}

//endregion

//region TASK MANAGEMENT

//region Add tasks button

@Composable
fun OneThingButtonLayout(
    onAddTask: () -> Unit,
    onReminder: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 30.dp, bottom = 112.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onReminder,
                shape = CircleShape,
                elevation = ButtonDefaults.buttonElevation(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.size(56.dp)
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Add Task Button (Lower)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onAddTask,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                modifier = Modifier.size(72.dp)
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

//endregion

//region TaskListDays-in progress, buggy

@RequiresApi(Build.VERSION_CODES.O)
@Composable
inline fun <reified T : Any> TaskListDays(
    tasks: List<T>,
    modifier: Modifier = Modifier,
    dayQueryProvider: (LocalDate) -> String = { date ->
        when (date.dayOfWeek) {
            DayOfWeek.MONDAY -> "mo"
            DayOfWeek.TUESDAY -> "tu"
            DayOfWeek.WEDNESDAY -> "we"
            DayOfWeek.THURSDAY -> "th"
            DayOfWeek.FRIDAY -> "fr"
            DayOfWeek.SATURDAY -> "sa"
            DayOfWeek.SUNDAY -> "su"
        }
    }
) {
    val today = LocalDate.now()

    val daysWithTasks = (0..90).map { offset ->
        val date = today.plusDays(offset.toLong())
        val label = when (offset) {
            0 -> "Today"
            1 -> "Tomorrow"
            else -> date.format(DateTimeFormatter.ofPattern("EEEE, MMM dd"))
        }
        val dayQuery = dayQueryProvider(date)
        val filteredTasks = filterByDay(tasks, dayQuery)
        label to filteredTasks
    }.filter { it.second.isNotEmpty() }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        daysWithTasks.forEach { (label, dayTasks) ->
            item {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(dayTasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        val kClass = task::class
                        val name = kClass.memberProperties.find { it.name == "name" }
                            ?.getter?.call(task)?.toString() ?: "Unnamed"

                        val schedule = kClass.memberProperties.find { it.name == "schedule" }
                            ?.getter?.call(task)?.toString() ?: ""

                        Text(name, style = MaterialTheme.typography.titleMedium)
                        if (schedule.isNotBlank()) {
                            Text("Schedule: $schedule", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}


//endregion


//endregion

//endregion

//region TEMPLATES

//region FULL SCREEN

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
/* POP UPS

var showPopup by remember { mutableStateOf(false) }
Text("Daily", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f).clickable { showPopup = true })
if (showPopup) { AlertDialog(onDismissRequest = { showPopup = false }, confirmButton = { TextButton(onClick = { showPopup = false }) { Text("OK") }}, title = { Text("Look...") }, text = { Text("Me, one lazy dude, ok, i know productivity app, but do no judge, we all make mistakes from time to time, mine being that i am lazy, and this app, is to help me solve that, so if you really care about this feature i will implement it in the future, give me a dm, and paypal me 100 bucks, i will get it done that week, otherwise, lazyyyyyyyy, wait a year or something") }) }

*/
//endregion

//endregion









