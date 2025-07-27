package com.productivity.wind.Screens

import androidx.compose.foundation.border
import android.app.AlertDialog
import android.app.AppOpsManager
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.compose.material3.Divider
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.productivity.wind.Global1.context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.provider.Settings
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.os.Process
import android.service.notification.NotificationListenerService
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.AppBlocking
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.BatterySaver
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import java.time.LocalDate
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.max
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.compose.NavHost
import com.productivity.wind.Bar
import com.productivity.wind.Blist
import com.productivity.wind.Global1
import com.productivity.wind.LazyPopup
import com.productivity.wind.NoLagCompose
import com.productivity.wind.PermissionsButton
import com.productivity.wind.R
import com.productivity.wind.SettingItem
import com.productivity.wind.WatchdogAccessibilityService
import com.productivity.wind.apps
import com.productivity.wind.log
import com.productivity.wind.SettingsScreen
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material.icons.outlined.Backup
import com.productivity.wind.SettingsSaved
import com.productivity.wind.UI
import com.productivity.wind.Vlog
import com.productivity.wind.Achievements
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset

//region NavController
//Global1.navController - to use anywhere, no input
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Main") {
            composable("Main") {
                Main()
            }
            composable("Achievements") {
                Achievements()
            }
            composable("ConfigureScreen") {
                ConfigureScreen()
            }
            //region SETTINGS

            composable("SettingsScreen") {
                SettingsScreen()
            }
            composable("SettingsP_Screen") {
                SettingsP_Screen()
            }

            //endregion SETTINGS

            
            //region ACHIEVEMENTS


            
            //endregion ACHIEVEMENTS

        }
}



//endregion






//region MICALANIOUS UI

//TOP BAR
@Composable
fun MainHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuIcon()
        ChillIcon()
        UsageIcon()
        
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "Points ${Bar.funTime}", fontSize = 18.sp)
        
        Spacer(modifier = Modifier.weight(1f))

        ConfigureIcon()
    }

}


@Composable
fun UsageIcon() {
    if (Bar.showUsageIcon) {
        SimpleIconButton(
            onClick = { Global1.navController.navigate("Achievements") },
            icon = Icons.Outlined.Chat
        )
    }
}

@Composable
fun SimpleIconButton(onClick: () -> Unit, icon: ImageVector) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFFD700)
        )
    }
}


//region MENU
@Composable
fun MenuHeader(){
    val safeStartPadding = max(0.dp, Bar.halfWidth / 2 - 50.dp)
    Column(modifier = Modifier.padding(start = safeStartPadding), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.baseline_radar_24),
            contentDescription = "Radar Icon",
            tint = Color(0xFFFFD700),
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Wind",
            fontSize = 28.sp // 👈 make it bigger here
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

}
@Composable
fun Menu() {

    SettingsScreen(
        titleContent = { MenuHeader() },
        onSearchClick = { },
        showBack = false,
        showSearch = false,
        showDivider = false
    ) {
        SettingItem(
            icon = Icons.Outlined.Chat,
            title = "Contact Support",
            onClick = { SupportEmail(); Bar.ShowMenu = false }
        )
        /*
    SettingItem(
        icon = Icons.Outlined.Landscape,
        title = "Premium",
        onClick = { Global1.navController.navigate("")  }
    )
    */
        SettingItem(
            icon = Icons.Outlined.Landscape,
            title = "Settings",
            onClick = { Global1.navController.navigate("SettingsScreen"); Bar.ShowMenu = false }
        )
        SettingItem(
            icon = Icons.Outlined.QueryStats,
            title = "Achievements",
            onClick = { Global1.navController.navigate("Achievements"); Bar.ShowMenu = false }
        )
    }
}


fun SupportEmail() {
    val subject = "Support Request – App Issue"

    val body = buildString {
        appendLine()
        appendLine("Phone Info:")
        appendLine("• Manufacturer: ${Build.MANUFACTURER}")
        appendLine("• Model: ${Build.MODEL}")
        appendLine("• Android Version: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
        appendLine()
        appendLine("I'm experiencing the following issue with the app:")
        appendLine()

    }

    val email = arrayOf("productivity.shield@gmail.com")

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, email)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    val chooser = Intent.createChooser(intent, "Send Email").apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    context.startActivity(chooser)
}

//endregion MENU

@Composable
fun EditPopUp(show: MutableState<Boolean>) {
    var TemporaryTargetText by remember { mutableStateOf("") }
    TemporaryTargetText = Bar.targetText
    LazyPopup(
        show = show,
        onDismiss = { TemporaryTargetText = Bar.targetText },
        title = "Edit Text",
        message = "",
        content = {
            OutlinedTextField(
                value = TemporaryTargetText,
                onValueChange = { TemporaryTargetText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        showCancel = true,
        onConfirm = { Bar.targetText = TemporaryTargetText; Bar.FirstEditText = false },
        onCancel = { TemporaryTargetText = Bar.targetText }
    )
}




//region CONFIGURE SCREEN

@Composable
fun ConfigureS_Header() = NoLagCompose {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Configure apps")
        Spacer(modifier = Modifier.weight(1f))
        StopBlockingButton()
    }
}

@Composable
fun ConfigureScreen() = NoLagCompose {
    val iconMap = remember { mutableStateMapOf<String, ImageBitmap>() }
    var show = remember { mutableStateOf(false) }
    var showPick = remember { mutableStateOf(false) }
    var showNeedMorePoints = remember { mutableStateOf(false) }


    // Load apps gradually in background
    LaunchedEffect(Unit) {

        withContext(Dispatchers.IO) {
            log("Blist.apps---${Blist.apps}", "bad")

            val pm = context.packageManager
            val intent = Intent(Intent.ACTION_MAIN, null).apply { addCategory(Intent.CATEGORY_LAUNCHER) }

            val InstalledApps = pm.queryIntentActivities(intent, 0)

            //region REMOVE UNINSTALLED APPS

            val installedPackageNames = InstalledApps.map { it.activityInfo.packageName }.toSet()
            withContext(Dispatchers.Main) {
                Blist.apps.removeAll { it.packageName.value !in installedPackageNames }
            }

            //endregion REMOVE UNINSTALLED APPS

            for (info in InstalledApps) {
                val label = info.loadLabel(pm)?.toString() ?: continue
                val packageName = info.activityInfo.packageName ?: continue
                val iconDrawable = info.activityInfo.loadIcon(pm)

                if (packageName == "com.productivity.wind") {continue}
                if (Blist.apps.any { it.packageName.value == packageName }) continue



                val app = apps(
                    name = mutableStateOf(label),
                    packageName = mutableStateOf(packageName)
                )

                withContext(Dispatchers.Main) {
                    Blist.apps.add(app)
                }

                val iconBitmap = iconDrawable.toBitmap().asImageBitmap()
                withContext(Dispatchers.Main) {
                    iconMap[packageName] = iconBitmap
                }

                delay(20)
            }
        }
    }

    val BlockedApps = Blist.apps.filter { it.Block.value }
    LazyPopup(show = showNeedMorePoints, title = "Not EnoughPoints", message = "Need 1k points to edit blocks", onConfirm = {},)

    LazyPopup(show = showPick, title = "Add Blocks", message = "", showCancel = false, showConfirm = false, content = {
        LazyColumn(modifier = Modifier.height(300.dp)) {
            items(Blist.apps, key = { it.id }) { app ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = app.Block.value,
                        onCheckedChange = { app.Block.value = it},
                    )
                    val icon = iconMap[app.packageName.value]
                    if (icon != null) {
                        Image(
                            bitmap = icon,
                            contentDescription = null,
                            modifier = Modifier.size(35.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(app.name.value)
                }
            }
        }
    }, onConfirm = {},)

    SettingsScreen(titleContent = { ConfigureS_Header() }, showSearch = false) { Card(modifier = Modifier.padding(16.dp).fillMaxWidth(), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))) {
            if (!areAllPermissionsEnabled(context)) { show.value = true; LazyPopup(show = show, onDismiss = { Global1.navController.navigate("Main")}, title = "Need Permissions", message = "Please enable all permissions first. They are necessary for the app to work ", showCancel = true, onConfirm = { Global1.navController.navigate("SettingsP_Screen")}, onCancel = { Global1.navController.navigate("Main")}) } else {
                SettingItem(icon = Icons.Outlined.AppBlocking, title = "Blocked Apps", endContent = {
                        Button(
                            onClick = { if(Bar.funTime < 1000 && !BlockedApps.isEmpty()) {showNeedMorePoints.value = true} else { showPick.value = true } },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)), // gold
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(6.dp),
                            modifier = Modifier
                                .size(40.dp)
                        ) {
                            Text("+", fontSize = 24.sp, color = Color.White)
                        }

                    })
                if (BlockedApps.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize().height(Bar.halfHeight*2-200.dp),) { Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height( Bar.halfHeight-190.dp))
                            Icon(
                                imageVector = Icons.Default.Block,
                                contentDescription = "Blocked Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No Blocks", fontSize = 18.sp, color = Color.Gray)
                        }
                    }

                }
                else {

                    LazyColumn(modifier = Modifier.fillMaxSize().height(500.dp)) {
                        items(BlockedApps, key = { it.id }) { app ->
                            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

                                val icon = iconMap[app.packageName.value]
                                if (icon != null) {
                                    Image(
                                        bitmap = icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(35.dp)
                                    )
                                } else {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp)
                                    )
                                }


                                Spacer(modifier = Modifier.width(8.dp))
                                Text(app.name.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

//endregion CONFIGURE SCREEN


//region Settings

@Composable
fun SettingsScreen() {
    SettingsScreen(titleContent = { Text("Settings") }, showSearch = false) {

        SettingItem(
            icon = Icons.Outlined.AdminPanelSettings,
            title = "Permissions",
            onClick = { Global1.navController.navigate("SettingsP_Screen") }
        )


        //region RESTORE/BACKUP
        
        var restoreTrigger = remember { mutableStateOf(false) }
        var backupTrigger by remember { mutableStateOf(false) }
        SettingItem(
                icon = Icons.Outlined.Restore,
                title = "Restore",
                onClick = { restoreTrigger.value = true }
        )
        SettingItem(
                icon = Icons.Outlined.Backup,
                title = "BackUp",
                onClick = { backupTrigger = true }
        )
        UI.BrestoreFromFile(restoreTrigger)
        UI.BsaveToFile(backupTrigger)

        //endregion RESTORE/BACKUP

            
        var text by remember { mutableStateOf("") }
        SettingItem(
                icon = Icons.Outlined.AdminPanelSettings,
                title = "Permissions",
                endContent = {
                        UI.InputField(
                                value = text,
                                onValueChange = { text = it },
                                placeholderText = "text",
                                InputWidth = 50.dp,
                                MaxLetters = 5,
                                OnMaxLetters =  { Vlog("Maximum of 5 letters") },
                        )
                }
        )
    }
}


//region PERMISSIONS

//region POPUP

@Composable
fun showPermissionDialog(
    show: MutableState<Boolean>,
    context: Context,
    title: String,
    message: String,
    settingsAction: String,
    dataUri: Uri? = null
) {
    LazyPopup(
        show = show,
        onDismiss = { },
        onConfirm = {
            Intent(settingsAction).apply {
                dataUri?.let { data = it }
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.also { context.startActivity(it) }
        },
        title = title,
        message = message,
    )
}
@Composable
fun showPermissionDialog2(
    show: MutableState<Boolean>,
    context: Context,
    title: String,
    message: String,
    onConfirm: () -> Unit,
) {
    LazyPopup(
        show = show,
        onDismiss = { },
        onConfirm = {
            onConfirm()
        },
        title = title,
        message = message,
    )
}



@Composable
fun NotificationP_PopUp(context: Context, show: MutableState<Boolean>) =
    showPermissionDialog(
        show,
        context,
        "Grant Notification access",
        Bar.NotificationP_Description,
        Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
    )

@Composable
fun OptimizationExclusionP_PopUp(context: Context, show: MutableState<Boolean>) =
    showPermissionDialog2(
        show,
        context,
        "Exclude from battery optimization",
        Bar.OptimizationExclusionP_Description,
        onConfirm = {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    )


@Composable
fun UsageStatsP_PopUp(context: Context, show: MutableState<Boolean>) =
    showPermissionDialog(
        show,
        context,
        "Grant Usage-Access permission",
        Bar.UsageStatsP_Description,
        Settings.ACTION_USAGE_ACCESS_SETTINGS
    )


class MyDeviceAdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.w("DeviceAdmin", "✅ Admin activated!")
        Toast.makeText(context, "Admin access granted!", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Log.w("DeviceAdmin", "❌ Admin removed!")
        Toast.makeText(context, "Admin access removed!", Toast.LENGTH_SHORT).show()
    }
}

//endregion POPUP

//region ENABLED??

fun isNotificationEnabled(ctx: Context): Boolean =
    NotificationManagerCompat
        .getEnabledListenerPackages(ctx)
        .contains(ctx.packageName)

fun isBatteryOptimizationDisabled(ctx: Context): Boolean {
    val pm = ctx.getSystemService(PowerManager::class.java)
    return pm.isIgnoringBatteryOptimizations(ctx.packageName)
}

fun isUsageStatsP_Enabled(ctx: Context): Boolean {
    val appOps = ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    return appOps.checkOpNoThrow(
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        Process.myUid(),
        ctx.packageName
    ) == AppOpsManager.MODE_ALLOWED
}

fun areAllPermissionsEnabled(ctx: Context): Boolean {
    return isNotificationEnabled(ctx)
            && isBatteryOptimizationDisabled(ctx)
            && isUsageStatsP_Enabled(ctx)
}


//endregion ENABLED??

@Composable
fun SettingsP_Screen()= NoLagCompose {
    val ctx = LocalContext.current
    LaunchedEffect(Unit) {

        while(true) {
            Bar.NotificationPermission = isNotificationEnabled(ctx)
            Bar.OptimizationExclusionPermission = isBatteryOptimizationDisabled(ctx)
            Bar.UsageStatsPermission = isUsageStatsP_Enabled(ctx)
            delay(200L)
        }
    }
    var showNotificationPopup = remember { mutableStateOf(false) }
    var showOptimizationPopup = remember { mutableStateOf(false) }
    var showUsagePopup = remember { mutableStateOf(false) }


    NotificationP_PopUp(ctx, showNotificationPopup)
    OptimizationExclusionP_PopUp(ctx, showOptimizationPopup)
    UsageStatsP_PopUp(ctx, showUsagePopup)


    SettingsScreen(titleContent = { Text("Permissions") }, showSearch = false) {

        SettingItem(
            icon = Icons.Outlined.Notifications,
            title = "Notification",
            endContent = {
                PermissionsButton(
                    isEnabled = Bar.NotificationPermission,
                    onEnable = {
                        showNotificationPopup.value= true
                    }
                )
            }
        )
        SettingItem(
            icon = Icons.Outlined.BatterySaver,
            title = "Optimization Exclusion",
            endContent = {
                PermissionsButton(
                    isEnabled = Bar.OptimizationExclusionPermission,
                    onEnable = { showOptimizationPopup.value = true }
                )
            }
        )
        SettingItem(
            icon = Icons.Outlined.BarChart,
            title = "Usage Stats",
            endContent = {
                PermissionsButton(
                    isEnabled = Bar.UsageStatsPermission,
                    onEnable = { showUsagePopup.value = true }
                )
            }
        )
    }
}

//endregion PERMISSIONS


//endregion Settings

//endregion


//region UI BUILDERS


//endregion

//region ONCES
class MyNotificationListener : NotificationListenerService()

//CHECKS IF NEW DAY/// WIRED UP TO SETTINGS VAR NEWDAY
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

    private fun onNewDay() {
        Bar.NewDay = true
        Bar.WaterDOtime_spent = 0
    }
}


@Composable
fun NewDayWaterDo() {
    val context = LocalContext.current
    var show = remember { mutableStateOf(Bar.NewDay) }
    LazyPopup(
        show = show,
        title = "FREE 15M. DO WATERDO",
        message = "Spend only 50s on waterdo app and get 900 POINTS!!!",
        showCancel = false,
        onConfirm = {
            val intent = context.packageManager.getLaunchIntentForPackage("com.seekrtech.waterapp")
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
                log("WaterApp not installed", "bad")
            }
        },
    )
}
@Composable
fun MenuIcon() {
    IconButton(onClick = { Bar.ShowMenu = true }) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color(0xFFFFD700)
        )
    }
}

@Composable
fun ChillIcon() {
  IconButton(onClick = {  }) {
        Icon(
            imageVector = Icons.Default.SportsEsports,
            contentDescription = "Chill",
            tint = Color(0xFFFFD700)
        )
  }       
}

@Composable
fun EditIcon() {

    //region THE SAFETY

    val showBeginnerAlert = remember { mutableStateOf(false) }
    val showVeteranAlert = remember { mutableStateOf(false) }

    var BeginnerA_Title by remember { mutableStateOf("Get 10 points") }
    var BeginnerA_Message by remember { mutableStateOf("Before being allowed to change the text, need a minimum of 10 points. [After changing the text once it increases permanantly to 100]. This is to help you stay disiplined afterwards") }

    var VeteranA_Title by remember { mutableStateOf("Get 100 points") }
    var VeteranA_Message by remember { mutableStateOf("Need 100 points before changing the text: this is to help you stay disiplined") }


    LazyPopup(show = showBeginnerAlert, title = BeginnerA_Title, message = BeginnerA_Message)
    LazyPopup(show = showVeteranAlert, title = VeteranA_Title, message = VeteranA_Message)

    //endregion THE SAFETY

    val show = remember { mutableStateOf(false) }
    EditPopUp(show = show)


    IconButton(onClick = {
        if (Bar.FirstEditText && Bar.funTime > 9) show.value=true
        else if (!Bar.FirstEditText && Bar.funTime > 99) show.value=true
        else if (Bar.FirstEditText) showBeginnerAlert.value = true
        else if (!Bar.FirstEditText) showVeteranAlert.value = true
    }
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = Color(0xFFFFD700)
        )
    }

}
@Composable
fun G_EditIcon() {

    //region THE SAFETY

    val showBeginnerAlert = remember { mutableStateOf(false) }
    val showVeteranAlert = remember { mutableStateOf(false) }

    var BeginnerA_Title by remember { mutableStateOf("Get 10 points") }
    var BeginnerA_Message by remember { mutableStateOf("Before being allowed to change the text, need a minimum of 10 points. [After changing the text once it increases permanantly to 100]. This is to help you stay disiplined afterwards") }

    var VeteranA_Title by remember { mutableStateOf("Get 100 points") }
    var VeteranA_Message by remember { mutableStateOf("Need 100 points before changing the text: this is to help you stay disiplined") }


    LazyPopup(show = showBeginnerAlert, title = BeginnerA_Title, message = BeginnerA_Message)
    LazyPopup(show = showVeteranAlert, title = VeteranA_Title, message = VeteranA_Message)

    //endregion THE SAFETY

    val show = remember { mutableStateOf(false) }
    G_EditPopUp(show = show)


    IconButton(onClick = {
        if (Bar.G_FirstEditText && Bar.funTime > 99) show.value=true
        else if (!Bar.G_FirstEditText && Bar.funTime > 999) show.value=true
        else if (Bar.G_FirstEditText) showBeginnerAlert.value = true
        else if (!Bar.G_FirstEditText) showVeteranAlert.value = true
    }
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = Color(0xFFFFD700)
        )
    }

}
@Composable
fun G_EditPopUp(show: MutableState<Boolean>) {
    var TemporaryTargetText by remember { mutableStateOf("") }
    TemporaryTargetText = Bar.G_targetText
    LazyPopup(
        show = show,
        onDismiss = { TemporaryTargetText = Bar.G_targetText },
        title = "Edit Text",
        message = "",
        content = {
            OutlinedTextField(
                value = TemporaryTargetText,
                onValueChange = { TemporaryTargetText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        showCancel = true,
        onConfirm = { Bar.G_targetText = TemporaryTargetText; Bar.G_FirstEditText = false },
        onCancel = { TemporaryTargetText = Bar.G_targetText }
    )
}

@Composable
fun ConfigureIcon() {

    IconButton(onClick = {
        Global1.navController.navigate("ConfigureScreen")
    }




    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "configure",
            tint = Color(0xFFFFD700)
        )
    }

}
@Composable
fun StopBlockingButton() {
    var showEnablePopup = remember { mutableStateOf(false) }
    var showUnsuccessfulD_Popup = remember { mutableStateOf(false) }

    // Show BEFORE enabling blocking
    if (showEnablePopup.value) {
        LazyPopup(
            show = showEnablePopup,
            onDismiss = { showEnablePopup.value = false },
            title = "Enable?",
            message = "If you enable blocking, an overlay screen will appear over the selected apps when you run out of points. (1 point = 1 second)\n\nTo disable blocking, you’ll need at least 1_000 points.",
            showCancel = true,
            showConfirm = true,
            onConfirm = {
                Bar.BlockingEnabled = true
                showEnablePopup.value = false
            },
            onCancel = {
                showEnablePopup.value = false
            }
        )
    }

    // Show if disabling fails
    if (showUnsuccessfulD_Popup.value) {
        LazyPopup(
            show = showUnsuccessfulD_Popup,
            onDismiss = { showUnsuccessfulD_Popup.value = false },
            title = "Not enough points",
            message = "You need at least 1000 points to disable blocking",
            showCancel = true,
            showConfirm = false,
            onConfirm = {},
            onCancel = {
                showUnsuccessfulD_Popup.value = false
            }
        )
    }

    // Main switch
    UI.OnOffSwitch(
        isOn = Bar.BlockingEnabled,
        onToggle = { isNowOn ->
            if (isNowOn) {
                showEnablePopup.value = true
            } else {
                val hasPoints = Bar.funTime > 1000
                if (hasPoints) {
                    Bar.BlockingEnabled = false
                } else {
                    showUnsuccessfulD_Popup.value = true
                }
            }
        }
    )
}




//!DISABLED
@Composable
fun AccessibilityPermission() {
    val context = LocalContext.current
    val serviceId = "${context.packageName}/${WatchdogAccessibilityService::class.java.name}"

    val enabledServices = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    ) ?: ""

    val isEnabled = enabledServices.contains(serviceId)

    log("Service id AccessibilityPermission${serviceId} ", "bad")
    log("isEnabled? AccessibilityPermission${isEnabled} ", "bad")
    if (!isEnabled) {
            //!Bar.AccesabilityPermission = false
            AlertDialog.Builder(context)
                .setTitle("Permission Needed")
                .setMessage("Please enable accessibility for full features.")
                .setPositiveButton("ok") { _, _ ->
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
                .show()
    } else {
        //!Bar.AccesabilityPermission = true
    }
}



//endregion

