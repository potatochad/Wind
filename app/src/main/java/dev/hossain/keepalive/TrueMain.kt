package dev.hossain.keepalive

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AppOpsManager
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.IBinder
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.hossain.keepalive.data.AppDataStore
import dev.hossain.keepalive.data.SettingsRepository
import dev.hossain.keepalive.data.logging.AppActivityLogger
import dev.hossain.keepalive.data.model.AppActivityLog
import dev.hossain.keepalive.ui.screen.AppInfo
import dev.hossain.keepalive.util.AppConfig.DEFAULT_APP_CHECK_INTERVAL_MIN
import dev.hossain.keepalive.util.AppConfig.DELAY_BETWEEN_MULTIPLE_APP_CHECKS_MS
import dev.hossain.keepalive.util.AppLauncher
import dev.hossain.keepalive.util.NotificationHelper
import dev.hossain.keepalive.util.RecentAppChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber
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
import java.util.concurrent.TimeUnit

//region NavController

fun NavGraphBuilder.OtherScreens(){
    composable("TrueMain"){
        TrueMain()
    }
}

//endregion
//region OnAppStart

@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
}

@Composable
fun AppStart() {

}

//endregion


//region BACKGROUND TASK

class WatchdogService : Service() {
    companion object { private const val NOTIFICATION_ID = 1 }
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private val notificationHelper = NotificationHelper(this)
    private lateinit var activityLogger: AppActivityLogger
    private var currentServiceInstanceId: Int = 0
    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind: $intent")
        return null
    }

    //* FUNCTION RESPONSIBLE FOR WHAT HAPPENS WHEN BACKGROUND TASK RUNS
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        Timber.d("onStartCommand() called with: intent = $intent, flags = $flags, startId = $startId")
        currentServiceInstanceId = startId
        notificationHelper.createNotificationChannel()
        activityLogger = AppActivityLogger(applicationContext)
        startForeground(NOTIFICATION_ID, notificationHelper.buildNotification(),)


        val dataStore = AppDataStore.store(context = applicationContext)
        val appSettings = SettingsRepository(applicationContext)


        serviceScope.launch {

            while (true) {
                Timber.d("[Instance ID: $currentServiceInstanceId] Current time: ${System.currentTimeMillis()} @ ${Date()}")
                val monitoredApps: List<AppInfo> = dataStore.data.first()

                if (monitoredApps.isEmpty()) {
                    Timber.w("No apps configured yet. Skipping the check.")
                    continue
                }

                val recentlyUsedAppStats = RecentAppChecker.getRecentlyRunningAppStats(this@WatchdogService)
                val shouldForceStart = appSettings.enableForceStartAppsFlow.first()

                if (shouldForceStart) {
                    Timber.d("Force start apps settings is enabled.")
                } else {
                    Timber.d("Force start apps settings is disabled.")
                }

                monitoredApps.forEach { appInfo ->
                    val isAppRunningRecently = RecentAppChecker.isAppRunningRecently(recentlyUsedAppStats, appInfo.packageName)
                    val needsToStart = !isAppRunningRecently || shouldForceStart

                    val timestamp = System.currentTimeMillis()
                    val message =
                        if (needsToStart) {
                            if (shouldForceStart) {
                                "Force starting app regardless of running state"
                            } else {
                                "App was not running recently, attempting to start"
                            }
                        } else {
                            "App is running normally, no action needed"
                        }

                    val activityLog =
                        AppActivityLog(
                            packageId = appInfo.packageName,
                            appName = appInfo.appName,
                            wasRunningRecently = isAppRunningRecently,
                            wasAttemptedToStart = needsToStart,
                            timestamp = timestamp,
                            forceStartEnabled = shouldForceStart,
                            message = message,
                        )
                    activityLogger.logAppActivity(activityLog)


                    delay(DELAY_BETWEEN_MULTIPLE_APP_CHECKS_MS)
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: Service is being destroyed. Service ID: $currentServiceInstanceId ($this)")
        serviceScope.cancel()
    }
}


//endregion

//region TrueMain
//NOT CONNECTED YET
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







//region ALL MIGHTY DATA MANAGER

object Item {
    fun Defaults(
        defaults: Map<String, Any> = emptyMap()
    ): (Map<String, Any>) -> Map<String, Any> {
        return { data ->
            val id = data["id"] as? String ?: UUID.randomUUID().toString()
            defaults + data + mapOf("id" to id)
        }
    }
}

class ItemMap(private val map: Map<String, Any>) {
    val id: String get() = map["id"] as? String ?: ""
    fun string(key: String): String = map[key] as? String ?: ""
    fun bool(key: String): Boolean = map[key] as? Boolean ?: false
    fun int(key: String): Int = map[key] as? Int ?: 0
    fun float(key: String): Float = map[key] as? Float ?: 0f
    fun raw(): Map<String, Any> = map
}

class ItemManager(
    private val key: String
) {
    private val prefs = Global1.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _data = mutableStateListOf<Map<String, Any>>()
    val data: List<ItemMap> get() = _data.map { ItemMap(it) }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val raw = prefs.getString(key, null)
            val list: List<Map<String, Any>> = if (raw != null) {
                gson.fromJson(raw, object : TypeToken<List<Map<String, Any>>>() {}.type)
            } else emptyList()
            withContext(Dispatchers.Main) {
                _data.clear()
                _data.addAll(list)
            }
        }
    }

    private fun writeRaw(value: String) =
        prefs.edit().putString(key, value).commit()

    private suspend fun save() = withContext(Dispatchers.IO) {
        writeRaw(gson.toJson(_data.toList()))
    }

    fun getAll(): List<ItemMap> = data

    fun getById(id: String): ItemMap? =
        _data.firstOrNull { it["id"] == id }?.let { ItemMap(it) }

    suspend fun add(item: Map<String, Any>): ItemMap {
        val withId = if (item.containsKey("id")) item else item + ("id" to UUID.randomUUID().toString())
        withContext(Dispatchers.Main) {
            _data.add(withId)
        }
        save()
        return ItemMap(withId)
    }

    suspend fun remove(id: String) {
        withContext(Dispatchers.Main) {
            _data.removeAll { it["id"] == id }
        }
        save()
    }

    suspend fun update(id: String, fields: Map<String, Any>): ItemMap {
        val idx = _data.indexOfFirst { it["id"] == id }
        require(idx >= 0) { "No item with id '$id'" }
        val updated = _data[idx] + fields
        withContext(Dispatchers.Main) {
            _data[idx] = updated
        }
        save()
        return ItemMap(updated)
    }

    suspend fun createOrUpdate(
        id: String? = null,
        defaults: Map<String, Any> = emptyMap(),
        fields: Map<String, Any> = emptyMap()
    ): ItemMap {
        val realId = id ?: UUID.randomUUID().toString()
        val existing = getById(realId)
        return if (existing != null) {
            update(realId, fields)
        } else {
            val item = mutableMapOf<String, Any>("id" to realId).also {
                it.putAll(defaults)
                it.putAll(fields)
            }
            add(item)
        }
    }
}

/* USAGE EXAMPLE

@Composable
fun TaskScreen(manager: ItemManager) {
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("New Task") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                scope.launch {
                    manager.add(
                        Item.Defaults(mapOf("completed" to false))(mapOf("text" to text))
                    )
                    text = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(
                items = manager.getAll(),
                key = { it.id } // ✅ fix: add key parameter
            ) { item ->
                val id = item.id
                val taskText = item.string("text")
                val completed = item.bool("completed")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = completed,
                        onCheckedChange = {
                            scope.launch {
                                manager.update(id, mapOf("completed" to it))
                            }
                        }
                    )
                    Text(
                        taskText,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        scope.launch {
                            manager.remove(id)
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

*/

//endregion













//IMPORTED FROM TEST7 PROJECT



//!Must read
//region USER MANUAL

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


//!IMPORTANT
//region CHEET CODES

//! IMPORTANT
/* *ULTIMATE DATA MANAGEMENT


 */


/* Simple Synched:
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

/* SMALL THINGS
   val id: String = UUID.randomUUID().toString(),
   *a thing that exists, unique completly

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


//? LIFE SAVERS
//region MUST USE

//region simple SYCHED

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

//region GLOBAL CONTEXT
//* CONTEXT from anywhere!!!
object Global1 {
    lateinit var context: Context
}


//endregion
//region log

fun log(message: String, tag: String? = "AppLog") {
    Log.d(tag, message)
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
                /*
                TaskInputField(
                    value = AppSettings.data.DefaultDuration.toString(),
                    onValueChange = { newValue ->
                        AppSettings.update("DefaultDuration", newValue)
                    },
                    placeholderText = "Duration",
                    isNumber = true,
                    modifier = Modifier.widthIn(max = 65.dp)
                )
                */

            }
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
/* POP UPS

var showPopup by remember { mutableStateOf(false) }
Text("Daily", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f).clickable { showPopup = true })
if (showPopup) { AlertDialog(onDismissRequest = { showPopup = false }, confirmButton = { TextButton(onClick = { showPopup = false }) { Text("OK") }}, title = { Text("Look...") }, text = { Text("Me, one lazy dude, ok, i know productivity app, but do no judge, we all make mistakes from time to time, mine being that i am lazy, and this app, is to help me solve that, so if you really care about this feature i will implement it in the future, give me a dm, and paypal me 100 bucks, i will get it done that week, otherwise, lazyyyyyyyy, wait a year or something") }) }

*/
//endregion

//endregion








