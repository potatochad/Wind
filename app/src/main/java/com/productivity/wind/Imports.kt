package com.productivity.wind

import timber.log.Timber
import android.app.usage.UsageStatsManager
import android.app.usage.UsageStats
import androidx.compose.foundation.interaction.*
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import android.os.*
import java.io.*
import android.annotation.*
import android.content.*
import android.util.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.navigation.NavController
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*
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
import kotlin.reflect.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import android.widget.ScrollView
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import java.io.File
import kotlin.math.min
import android.content.ClipData
import android.content.ClipboardManager
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.ripple
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
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
import com.productivity.wind.*
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable

//import android.provider.Settings




//region Vals/ Vars FOR DATA

val gson = Gson()
typealias Str = String
typealias Bool = Boolean
fun Id(): String {
    return UUID.randomUUID().toString()
}
fun <T> m(value: T) = mutableStateOf(value)

inline fun <reified T> ml(@Suppress("UNUSED_PARAMETER") dummy: T): SnapshotStateList<T> {
    return mutableStateListOf()
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



//region log

fun log(message: String, tag: String? = "Bad") {
    var LogMessage = message ; val stackTrace = Thread.currentThread().stackTrace ; val element = stackTrace[3] ; val fileName = element.fileName ; val lineNumber = element.lineNumber
    LogMessage= "[$fileName:$lineNumber] $message"
    if ("bad".equals(tag, true)) { Log.w(tag, LogMessage) }

    else { Log.d(tag, LogMessage) }
}



private var lastToast: Toast? = null

fun Vlog(msg: String, special: String = "none", delayLevel: Int = 0) {
    val delayMs = (delayLevel.coerceIn(0, 100)) * 30L // Example: Level 2 = 60ms

    if (special.equals("one", true)) {
        lastToast?.cancel()
    }

    Handler(Looper.getMainLooper()).postDelayed({
        val toast = Toast.makeText(Global1.context, msg, Toast.LENGTH_SHORT)
        lastToast = toast
        toast.show()
    }, delayMs)
}

fun getLogs(): String {
    return try {
        val process = Runtime.getRuntime().exec("logcat -d")
        val reader = process.inputStream.bufferedReader()
        val logs = reader.readText()

        // Optional: filter only your app logs
        logs.lines()
            .joinToString("\n")
    } catch (e: Exception) {
        "❌ Failed to read logs: ${e.message}"
    }
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
@Composable
fun PreloadBox(
    whenDo: Boolean,
    what: @Composable () -> Unit
) {
    if (whenDo) {
        Box(
            Modifier
                .size(1.dp)
                .alpha(0f)
                .clearAndSetSemantics { }
        ) { what() }
    }
}
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

    
    fun Bsave() {
        if (Dosave?.isActive == true) return
        if (Bar.restoringFromFile) return
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







object UI {
	//No synched with actual settingsItem function YET
	var SettingsItemCardColor = Color(0xFF121212)
    
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
            Bar.restoringFromFile = true
            launcher.value.launch(arrayOf("text/plain"))
            delay(2000L)
            Bar.restoringFromFile = false
            trigger.value = false
            Vlog("Successfully restored")
        }
    }
}

@Composable
fun End(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        content()
    }
}
@Composable
fun move(w: Int = 0, h: Int = 0) {
    Spacer(
        Modifier
            .width(w.dp)
            .height(h.dp)
    )
}


@Composable
fun MenuHeader(
    title: String = "Wind",
    iconRes: Int = R.drawable.baseline_radar_24,
    iconSize: Dp = 60.dp,
    iconTint: Color = Color(0xFFFFD700),
    titleSize: TextUnit = 28.sp,
    topPadding: Dp = 8.dp,
    bottomPadding: Dp = 20.dp,
	StartPaddingRemove: Int = 40,
) {
    val safeStartPadding = max(0.dp, Bar.halfWidth / 2 - StartPaddingRemove.dp)

    Column(
        modifier = Modifier.padding(start = safeStartPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(topPadding))
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "$title Icon",
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = titleSize
        )
        Spacer(Modifier.height(bottomPadding))
    }
}

fun SendEmail(
    recipient: String = "productivity.shield@gmail.com",
    subject: String = "Support Request – App Issue",
    includePhoneInfo: Boolean = true,
    prefillMessage: String = "I'm experiencing the following issue with the app:\n\n"
) {
    val context = Global1.context
        
    val body = buildString {
        appendLine()
        if (includePhoneInfo) {
            appendLine("Phone Info:")
            appendLine("• Manufacturer: ${Build.MANUFACTURER}")
            appendLine("• Model: ${Build.MODEL}")
            appendLine("• Android Version: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
            appendLine()
        }
        append(prefillMessage)
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    val chooser = Intent.createChooser(intent, "Send Email").apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    context.startActivity(chooser)
}




@Composable
fun OnOffSwitch(isOn: Boolean, onToggle: (Boolean) -> Unit) {
    Switch(
        checked = isOn,
        onCheckedChange = onToggle,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color(0xFFFFD700),         // Gold thumb
            uncheckedThumbColor = Color.LightGray,         // Soft gray when off
            checkedTrackColor = Color(0xFFFFF8DC),         // Light creamy gold track
            uncheckedTrackColor = Color(0xFFE0E0E0)        // Muted gray track
        )
    )
}


//region TEXT VALS ■■■■■■■■■■■■■

@Composable
fun TextStyle(
    color: Color,
    fontSize: TextUnit
) = LocalTextStyle.current.copy(
    color = color,
    fontSize = fontSize
)

@Composable
fun TextMemory() = remember { MutableInteractionSource() }
fun type(isNumber: Boolean) =
    if (isNumber) KeyboardType.Number else KeyboardType.Text
fun ImeAction(onDone: (() -> Unit)?) =
    if (onDone != null) ImeAction.Done else ImeAction.Default
@Composable
fun IsFocused(source: InteractionSource) =
    source.collectIsFocusedAsState()
@Composable
fun OnLoseFocus(isFocused: Boolean, onFocusLose: (() -> Unit)?) {
    LaunchedEffect(isFocused) {
        if (!isFocused) {
            onFocusLose?.invoke()
        }
    }
}
fun FilterInput(isNumber: Boolean, input: String): String {
    return if (isNumber) input.toIntOrNull()?.toString() ?: "0" else input
}
fun max(maxLetters: Int?) =
    maxLetters ?: Int.MAX_VALUE
fun doneAction(onDone: (() -> Unit)?) =
    KeyboardActions(onDone = { onDone?.invoke() })
fun FocusAsk(focusRequester: FocusRequester?) =
    focusRequester?.let { Modifier.focusRequester(it) } ?: Modifier
fun KeyboardOptions(
    keyboardType: KeyboardType,
    imeAction: ImeAction
) = KeyboardOptions.Default.copy(
    keyboardType = keyboardType,
    imeAction = imeAction
)
fun grayCursor() = SolidColor(Color.Gray)





//endregion Text VALS■■■■■■■■■■■


@Composable
fun InputField(
    value: String,
    onChange: (String) -> Unit,
    placeholderText: String = "Input Text",
    isNumber: Boolean = false,
    onDone: (() -> Unit)? = null,  //When press done button
    Divider: Boolean = true,
    textSize: TextUnit = 14.sp,           
    height: Dp = 36.dp,                
	dividerY: Int = 0,
    InputWidth: Dp = 80.dp,
    MaxLetters: Int? = 20_000,
    OnMaxLetters: (() -> Unit) = { Vlog("MAX LETTERS") }, //each letter type doo
    TextColor: Color = Color.White,
    BackgroundColor: Color = SettingsItemCardColor,

    OnFocusLose: (() -> Unit)? = null,
	AutoWidth: Boolean = false,
	AutoWidthMin: Int = 60,
	AutoWidthMax: Int = 200,
	
	NoBottomPadding: Boolean = false,
) {
	val FocusChange = TextMemory()
    val imeAction = ImeAction(onDone)
    val isFocused by IsFocused(FocusChange)

	
	//val AutoWidthMaxVAL = AutoWidthMin + (charCount * charWidthDp) + (paddingHorizontalDp * 2)

	val outerMod = if (AutoWidth) {
		Modifier.widthIn(min = AutoWidthMin.dp, max = AutoWidthMax.dp)
	} else {
		Modifier.width(InputWidth)
	}
	
	OnLoseFocus(isFocused, OnFocusLose)


    BasicTextField(
        value = value,
        onValueChange = {
          val input = FilterInput(isNumber, it)   
		  if (input.length <= max(MaxLetters) ) {
            onChange(input) 
		  
		  } else {
            OnMaxLetters()
          }
		  
        },

        modifier = outerMod
            .height(height),
        textStyle = TextStyle(TextColor, textSize),
        singleLine = true,
		keyboardOptions = KeyboardOptions(type(isNumber), imeAction),
        keyboardActions = doneAction(onDone),
        cursorBrush = grayCursor(),
        interactionSource = FocusChange,
        
        decorationBox = { innerTextField ->
			FieldBox(
				height = height,
				BackgroundColor = BackgroundColor,
			) {
                    if (value.isEmpty()) {
                        Text(
                            placeholderText,
                            color = TextColor,
                            fontSize = textSize
                        )
                    }
					

						innerTextField()
					


					
                }
				
				SimpleDivider(
					show = Divider,
					MoveY = dividerY,
					width = InputWidth,
				)



				
        }
    )
}




















	
					








@OptIn(ExperimentalTextApi::class)
@Composable
fun Cinput(
    value: String,
    textSize: TextUnit = 14.sp,
    height: Dp = 36.dp,
    MaxLetters: Int? = 5,
    WidthMin: Int = 10,
    WidthMax: Int = 800,

	onChange: (String) -> Unit,
) {
	val TextColor = Color(0xFFFFD700)
    val FocusChange = TextMemory()
    val imeAction = ImeAction(null)
    val isFocused by IsFocused(FocusChange)


	
    val measurer = rememberTextMeasurer()
	val density = LocalDensity.current
	val measuredWidth = measurer.measure(
		if (value.isEmpty()) " " 
		else value, style = TextStyle(TextColor, textSize)).size.width
	val outerMod = Modifier.width(
		(with(density) { measuredWidth.toDp() } + 0.dp)
			.coerceIn(WidthMin.dp, WidthMax.dp)
	)



    OnLoseFocus(isFocused, null)

    BasicTextField(
        value = value,
        onValueChange = {
            val input = FilterInput(true, it)
            if (input.length <= max(MaxLetters)) {
                onChange(input)
            }
			else { Vlog("max ${MaxLetters} letters") }
        },
        modifier = outerMod.height(height),
        textStyle = TextStyle(TextColor, textSize),
        singleLine = true,
		keyboardOptions = KeyboardOptions(type(true), imeAction),
        keyboardActions = doneAction(null),
        cursorBrush = grayCursor(),
        interactionSource = FocusChange,
        decorationBox = { innerTextField ->
            FieldBox(
                height = height,
                BackgroundColor = Color.Transparent
            ) {
                innerTextField()
            }
        }
    )
}
































@Composable
fun FieldBox(
    modifier: Modifier = Modifier,
    horizontal: Dp = 0.dp,
    height: Dp = 40.dp,
    BackgroundColor: Color = Color.Gray,
	where: Alignment = Alignment.CenterStart,
	content: @Composable () -> Unit,
) {
    Column {
        Box(
            modifier = modifier
                .padding(horizontal = horizontal)
                .height(height)
				.wrapContentWidth()
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(BackgroundColor),
            contentAlignment = where,
        ) {
            content()
        }
    }
}


@Composable
fun SimpleDivider(
    show: Boolean,
    color: Color = Color(0xFFFFD700),
    thickness: Dp = 1.dp,
    MoveY: Int = 0,
    paddingHorizontal: Dp = 0.dp,
    width: Dp = Dp.Unspecified,
) {
    if (!show) return

    Divider(
        color = color,
        thickness = thickness,
        modifier = Modifier
            .offset(y = MoveY.dp)
            .padding(horizontal = paddingHorizontal)
            .then(if (width != Dp.Unspecified) Modifier.width(width) else Modifier)
    )
}




@Composable
fun TextRow2(
    padding: Int = 0,
    hGap: Dp = 5.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding.dp)
    ) { measurables, constraints ->
        val gap = hGap.roundToPx()
        val maxW = constraints.maxWidth
        val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0)) }

        val rows = mutableListOf<MutableList<Placeable>>()
        val rowHeights = mutableListOf<Int>()
        var cur = mutableListOf<Placeable>()
        var curW = 0
        var curH = 0

        fun pushRow() {
            if (cur.isNotEmpty()) {
                rows += cur
                rowHeights += curH
                cur = mutableListOf(); curW = 0; curH = 0
            }
        }

        for (p in placeables) {
            val nextW = if (cur.isEmpty()) p.width else curW + gap + p.width
            if (nextW > maxW) pushRow()
            if (cur.isNotEmpty()) curW += gap
            cur += p
            curW += p.width
            curH = maxOf(curH, p.height)
        }
        pushRow()

        val height = rowHeights.sum() + gap * (rowHeights.size - 1).coerceAtLeast(0)

        layout(maxW, height) {
            var y = 0
            rows.forEachIndexed { i, row ->
                var x = 0
                val rowH = rowHeights[i]
                row.forEach { p ->
                    val yCenter = y + (rowH - p.height) / 2
                    p.placeRelative(x, yCenter)
                    x += p.width + gap
                }
                y += rowH + gap
            }
        }
    }
}
@Composable
fun TextRow(
    padding: Int = 0,
    hGap: Dp = 5.dp,          // space between items in a row
    vGap: Dp = 6.dp,          // space between rows
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding.dp)
    ) { measurables, constraints ->
        val h = hGap.roundToPx()
        val v = vGap.roundToPx()
        val maxW = constraints.maxWidth
        val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0)) }

        val rows = mutableListOf<MutableList<Placeable>>()
        val rowHeights = mutableListOf<Int>()
        var cur = mutableListOf<Placeable>()
        var curW = 0
        var curH = 0

        fun pushRow() {
            if (cur.isNotEmpty()) {
                rows += cur
                rowHeights += curH
                cur = mutableListOf(); curW = 0; curH = 0
            }
        }

        for (p in placeables) {
            val nextW = if (cur.isEmpty()) p.width else curW + h + p.width
            if (nextW > maxW) pushRow()
            if (cur.isNotEmpty()) curW += h
            cur += p
            curW += p.width
            curH = maxOf(curH, p.height)
        }
        pushRow()

        val height = rowHeights.sum() + v * (rowHeights.size - 1).coerceAtLeast(0)

        layout(maxW, height) {
            var y = 0
            rows.forEachIndexed { i, row ->
                var x = 0
                val rowH = rowHeights[i]
                row.forEach { p ->
                    val yCenter = y + (rowH - p.height) / 2
                    p.placeRelative(x, yCenter)
                    x += p.width + h
                }
                y += rowH + v
            }
        }
    }
}






@Composable
fun SimpleRow(
	padding: Int = 0,
	content: @Composable () -> Unit,
){
	Row(
		modifier = Modifier
			.fillMaxWidth()
                        .padding(padding.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
		content()
		}
}
      

	
@Composable
fun LazyCard(
	content: @Composable () -> Unit,
	InputColor: Color = Color(0xFF1A1A1A),
	InnerPadding: Int = 16
) {
	Card(
		modifier = Modifier
			.padding(8.dp)
			.fillMaxWidth(), 
		shape = RoundedCornerShape(16.dp), 
		elevation = CardDefaults
			.cardElevation(defaultElevation = 8.dp), 
		colors = CardDefaults
			.cardColors(containerColor = InputColor)
	     ){
		Column(modifier = Modifier.padding(InnerPadding.dp)) {
                     content()
		}
	     }
}
//
@Composable
fun CopyIcon(text: String) {
    val context = LocalContext.current
    var copied by remember { mutableStateOf(false) }

    LaunchedEffect(copied) {
        if (copied) {
            delay(1000) // Show checkmark for 1 second
            copied = false
        }
    }

    SimpleIconButton(
        icon = if (copied) Icons.Default.Check else Icons.Default.ContentCopy,
        onClick = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
            copied = true
        }
    )
}

// -------- Core ----------


//region CLICKALE TEXTTTTT■■■■■■■■■■■

@Composable
fun Ctext(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        modifier = Modifier.clickable(onClick = onClick),
        style = TextStyle(
            color = Color(0xFFFFD700),           // gold
            fontWeight = FontWeight.Bold,        // bold
            textDecoration = TextDecoration.None // no underline
        )
    )
}


//endregion CLICABLE TEXT ■■■■■■■■■

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleIconButton(
    onClick: () -> Unit,
    icon: ImageVector? = null,
    BigIcon: ImageVector? = null,
    BigIconColor: Color? = null,
    SquareIcon: Boolean = false,
    BigIconSize: Int = 30,
    OuterPadding: Int = 5,          // outside space
    ButtonSize: Int = 40,           // actual button box (default M3 ~48)
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        IconButton(
            onClick = onClick,
            modifier = modifier
                .padding(OuterPadding.dp) // OUTER padding
                .size(ButtonSize.dp)      // controls inner room around icon
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(24.dp)
                )
            }

            if (BigIcon != null && BigIconColor != null) {
                val shape = if (SquareIcon) RoundedCornerShape(6.dp) else CircleShape
                val innerSize = if (SquareIcon) 24.dp else 20.dp
                Box(
                    modifier = Modifier
                        .size(BigIconSize.dp)
                        .clip(shape)
                        .background(BigIconColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = BigIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(innerSize)
                    )
                }
            }
        }
    }
}


//Region SETTING STUFF
	fun openPermissionSettings(action: String, uri: Uri? = null) {
		val intent = Intent(action).apply {
			uri?.let { data = it }
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		}
		Global1.context.startActivity(intent)
	}
	fun isNotificationEnabled(): Boolean {
		val ctx = Global1.context
		return NotificationManagerCompat
			.getEnabledListenerPackages(ctx)
			.contains(ctx.packageName)
	}

	fun isBatteryOptimizationDisabled(): Boolean {
		val ctx = Global1.context
		val pm = ctx.getSystemService(PowerManager::class.java)
		return pm.isIgnoringBatteryOptimizations(ctx.packageName)
	}
	fun isUsageStatsP_Enabled(): Boolean {
		val ctx = Global1.context
		val appOps = ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
		return appOps.checkOpNoThrow(
			AppOpsManager.OPSTR_GET_USAGE_STATS,
			Process.myUid(),
			ctx.packageName
		) == AppOpsManager.MODE_ALLOWED
	}
	fun requestUsageStatsPermission(activity: Activity) {
		
		AlertDialog.Builder(activity)
			.setTitle("Permission Needed")
			.setMessage("We need Usage Access to track app usage and help you stay productive. This will open Settings — please enable access for this app.")
			.setPositiveButton("OK") { _, _ ->
				val intent = android.content.Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
					addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
				}
				try {
					activity.startActivity(intent)
				} catch (_: ActivityNotFoundException) { /* no-op */ }
			}
			.setNegativeButton("Cancel", null)
			.show()
	}



//endregion SETTING STUFF


@Composable
fun EmptyBox(
    text: String = "No Items",
    icon: ImageVector = Icons.Default.Block,
    height: Dp = Bar.halfHeight * 2 - 200.dp,
    iconSize: Dp = 64.dp,
    topSpacing: Dp = Bar.halfHeight - 190.dp,
    textSize: TextUnit = 18.sp,
    color: Color = Color.Gray
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .height(height)) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topSpacing))
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, fontSize = textSize, color = color)
        }
    }
}



    //INSIDE UI OBJECTTTTTT----------------------------------------//
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


//region SCREENS

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
	
    icon: ImageVector? = null,
    BigIcon: ImageVector? = null,
    BigIconColor: Color? = null,
	
    onClick: (() -> Unit)? = null,

    topPadding: Dp= 7.dp,
    bottomPadding: Dp = 7.dp,
) {
	Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
		    top = topPadding,
		    bottom = bottomPadding,
		    start = 7.dp,
		    end = 7.dp
	    ),
 verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
    modifier = modifier
        .fillMaxWidth()
        .clickable(enabled = onClick != null) { onClick?.invoke() },
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
) {
            Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
	if (icon != null) {
		Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFFD700),
            modifier = Modifier
                .padding(end = 10.dp)
                .size(24.dp)
        )
	}	    

	if (BigIcon != null && BigIconColor != null) {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(BigIconColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = BigIcon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
	}



	
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
            subtitle?.let {
                Text(text = it, color = Color.Gray, fontSize = 12.sp)
            }
        }
        endContent?.invoke()
    }
}}

}

@Composable
fun settingsHeader(
    titleContent: @Composable () -> Unit,
    onSearchClick: () -> Unit,
    onBackClick: () -> Unit = {},
    showBack: Boolean = true,
    showSearch: Boolean = false,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
	Mheight: Int = 100,
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
				.padding(vertical = 12.dp)
			.heightIn(max = Mheight.dp),
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
				UI.SimpleRow(){
						titleContent()
				}
				
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
	    Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
	
    }
}


@Composable
fun SettingsScreen(
    titleContent: @Composable () -> Unit,
    onSearchClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    showBack: Boolean = true,
    showSearch: Boolean = false,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
	MheaderHeight: Int = 44,
    content: @Composable () -> Unit,
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
                showDivider = showDivider,
				Mheight = MheaderHeight,
            )
        }
        item {
		Column {
			content()
		}
        }
    }
}



//endregion


//region LAZY POPUP

@Composable
fun AnimateDown(
    modifier: Modifier = Modifier,
    duration: Int = 50,
    easing: Easing = LinearOutSlowInEasing,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier.animateContentSize(
            animationSpec = tween(durationMillis = duration, easing = easing)
        ),
        content = content
    )
}

@Composable
fun LazyPopup(
    show: MutableState<Boolean>,
    onDismiss: (() -> Unit)? = { show.value = false },
    title: String = "Info",
    message: String,
    content: (@Composable () -> Unit)? = null,
    showCancel: Boolean = true,
    showConfirm: Boolean = true,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,

	Preload: Boolean = true,
) = NoLagCompose {
	
	val preloading = !show.value && Preload
	
	PreloadBox(
        whenDo = preloading,
        what = { content?.invoke() ?: Text(message) }
    )
	
    if (show.value) { 
		AlertDialog(
			onDismissRequest = {
				onDismiss?.invoke()
			},
			title = { Text(title) },
			text = {
				AnimateDown() {
					if (content == null) {Text(message) }
					else {
						content()
					}
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

fun NavGraphBuilder.url(route: String, content: @Composable () -> Unit) {
    composable(route) { content() }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Main") {
            ScreenNav()
        }
}


//endregion

//endregion








//region LATER USE



class WatchdogService : Service() {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private var OneJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind: $intent")
        return null
    }

    fun GOtowindAPP(){
        val intent = Intent(Global1.context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Global1.context.startActivity(intent)
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        NotificationHelper(this).createNotificationChannel()
        startForeground(1, NotificationHelper(this).buildNotification(),)
        Global1.context = this
        if (Bar.BlockingEnabled) {
            if (OneJob == null || OneJob?.isActive == false) {
                OneJob = serviceScope.launch {
                    while (true) {

                        //region SAFETY PURPOSES

                        delay(1000L)
                        Bar.COUNT +=1

                        //endregion


                        //region CURRENT APP

                        val usageStatsManager = Global1.context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                        val NowTime = System.currentTimeMillis()

                        /*
                        * THIS IS NOT SUPER ACCURATE
                        ? If you want better precision, you’ll need an Accessibility Service.
                        !THIS REQUIRES NAVIGATING USER TO IT*/
                        val AppsUsed = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, NowTime - 20_000, NowTime)
                        val currentApp = AppsUsed?.maxByOrNull { it.lastTimeUsed }?.packageName

                        //LOG WHEN WANT TOOO
                        if (currentApp == Global1.context.packageName || currentApp == null) { } else { log("BACKGROUND — CURRENT APP: $currentApp", "bad") }

                        //endregion CURRENT APP

                        val blocked = apps.any { it.packageName == currentApp && it.Block }

                        if (blocked) {
                            if (Bar.funTime > 0) {
                                Bar.funTime -= 1
                                log("BACKGROUND---Spending Time??:::${Bar.funTime};", "bad")
                            } else {
                                GOtowindAPP()
                                log("BACKGROUND---Blocking APP:::${currentApp}; ${Bar.COUNT}", "bad")
                            }
                        }


                    if (currentApp == "com.seekrtech.waterapp") {
						
					}   }}
            }
        }

        return START_STICKY
    }

    override fun onDestroy() { super.onDestroy(); serviceScope.cancel() }
}




class NotificationHelper(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "WatchdogServiceChannel"
    }
    fun createNotificationChannel() {
        Timber.d("createNotificationChannel() called")
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_channel_name_watchdog_service),
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(channel)
    }

    fun buildNotification(): Notification {
        Timber.d("buildNotification() called")

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntentFlags =
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        val pendingIntent =
            PendingIntent.getActivity(context, 0, notificationIntent, pendingIntentFlags)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notification_title_app_watchdog))
            .setContentText(context.getString(R.string.notification_content_monitoring_apps))
            .setSmallIcon(R.drawable.baseline_radar_24)
            .setContentIntent(pendingIntent)
            // Low priority for ongoing background service notification
            .setPriority(NotificationCompat.PRIORITY_LOW)
            // Makes the notification persistent
            .setOngoing(true)
            .build()
    }
}

//endregion LATER USE



