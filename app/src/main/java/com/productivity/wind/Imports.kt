package com.productivity.wind

import android.os.Process
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import android.app.AppOpsManager
import android.os.PowerManager
import androidx.core.app.NotificationManagerCompat
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.Dp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.rememberUpdatedState
import android.os.Environment
import java.io.File
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import android.widget.ScrollView
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextFieldDefaults




//region log

fun log(message: String, tag: String? = "Bad") {
    var LogMessage = message ; val stackTrace = Thread.currentThread().stackTrace ; val element = stackTrace[3] ; val fileName = element.fileName ; val lineNumber = element.lineNumber
    LogMessage= "[$fileName:$lineNumber] $message"
    if ("bad".equals(tag, true)) { Log.w(tag, LogMessage) }

    else { Log.d(tag, LogMessage) }
}



private var lastToast: Toast? = null

fun Vlog(msg: String, special: String = "none") {
    if (special.equals("one", true)) {
        lastToast?.cancel()
    }

    val toast = Toast.makeText(Global1.context, msg, Toast.LENGTH_SHORT)
    lastToast = toast
    toast.show()
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
                    Vlog("Restore failed: ${e.message}")
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


/* USAGE
    InputField(
    value = myText,
    onValueChange = { myText = it },
    placeholderText = "Enter name",
    maxLength = 20,
    width = 200.dp,
    height = 40.dp,
    backgroundColor = Color.DarkGray,
    isNumber = false,
    onDone = { println("Done pressed") }
)
*/
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "Input Text",
    modifier: Modifier = Modifier,
    isNumber: Boolean = false,
    focusRequester: FocusRequester? = null,
    onDone: (() -> Unit)? = null,  //When press done button
    showDivider: Boolean = true,
    textSize: TextUnit = 14.sp,           
    boxHeight: Dp = 36.dp,                
    innerPadding: Dp = 4.dp,
    InputWidth: Dp = 80.dp,
    MaxLetters: Int? = 20_000,
    OnMaxLetters: (() -> Unit) = { Vlog("MAX LETTERS") }, //each letter type doo
    InputTextColor: Color = Color.White,
    InputBackgroundColor: Color = SettingsItemCardColor,

    OnFocusLose: (() -> Unit)? = null,
) {
    val keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
    val imeAction = if (onDone != null) ImeAction.Done else ImeAction.Default
    val interactionSource = remember { MutableInteractionSource() }

    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
	    if (!isFocused) {
		    OnFocusLose?.invoke()
	    }
    }

    BasicTextField(
        value = value,
        onValueChange = {
          val parsed = if (isNumber) it.toIntOrNull()?.toString() ?: "0" else it
          if (parsed.length <= (MaxLetters ?: Int.MAX_VALUE)) {
            onValueChange(parsed) }  
          else {
            OnMaxLetters
          }
        },

        modifier = modifier
            .height(boxHeight)
            .then(focusRequester?.let { Modifier.focusRequester(it) } ?: Modifier),
        textStyle = LocalTextStyle.current.copy(color = InputTextColor, fontSize = textSize),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = { onDone?.invoke() }),
        cursorBrush = SolidColor(Color.Gray),
        interactionSource = interactionSource,
        
        decorationBox = { innerTextField ->
            Column {
                Box(
                    modifier = Modifier
                        .padding(horizontal = innerPadding)
                        .width(InputWidth)
						.height(boxHeight - 8.dp)
						.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)) // 👈 clips background to round shape
						.background(InputBackgroundColor), // 👈 this sets the color
					contentAlignment = Alignment.CenterStart
				) {
                    if (value.isEmpty()) {
                        Text(
                            placeholderText,
                            color = InputTextColor,
                            fontSize = textSize
                        )
                    }
                    innerTextField()
                }
                if (showDivider) {
                    Divider(
                        color = Color(0xFFFFD700),
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(horizontal = innerPadding)
                            .width(InputWidth)
                    )

                }
            }
        }
    )
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

@Composable
fun SimpleIconButton(
	onClick: () -> Unit, 
	icon: ImageVector? = null,
	BigIcon: ImageVector? = null,
	BigIconColor: Color? = null,
) {
    IconButton(onClick = onClick) {
	    if (icon != null) {
		    Icon(
			    imageVector = icon,
			    contentDescription = null,
			    tint = Color(0xFFFFD700),
			    modifier = Modifier
				    .size(24.dp)
		    )
	    }	    
	    
	    if (BigIcon != null && BigIconColor != null) {
                    Box(
                        modifier = Modifier
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
		Column {
			content()
		}
        }
    }
}



//endregion


//region LAZY POPUP

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








