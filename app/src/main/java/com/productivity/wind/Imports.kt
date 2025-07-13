package com.productivity.wind

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Divider
import com.google.accompanist.systemuicontroller.rememberSystemUiController


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

//endregion




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
//region DATA MANAGE

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
    modifier: Modifier = Modifier
) {
    val ui = rememberSystemUiController()
    LaunchedEffect(Unit) {
        ui.setStatusBarColor(Color.Black, darkIcons = false)
    }
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Color.Black)
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back button on the left
            if (showBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFFFD700)
                    )
                }
            }

            // Title slot
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                titleContent()
            }

            // Search icon on the right
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
        // Divider under header
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
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
                showSearch = showSearch
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








