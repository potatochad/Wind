package com.productivity.wind

import android.R
import android.app.AlertDialog
import android.app.AppOpsManager
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import android.graphics.Canvas
import android.net.Uri
import android.os.PowerManager
import android.os.Process
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.BatterySaver
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import java.time.LocalDate
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Visibility
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.NavHost

//region NavController
//Global1.navController - to use anywhere, no input
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Main") {
        composable("Main") {
            Main()
        }
        composable("ChillScreen") {
            ChillScreen()
        }
        composable("EditScreen") {
            EditScreen()
        }
        composable("Achievements") {
            Achievements()
        }

        //region SETTINGS

        composable("SettingsScreen") {
            SettingsScreen()
        }
        composable("SettingsP_Screen") {
            SettingsP_Screen()
        }

        //endregion SETTINGS
    }
}


//endregion






//region MICALANIOUS UI

@Composable
fun EditScreen() {
    if (Bar.funTime < 1000) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "NOT ENOUGH POINTS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Edit text",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = Bar.targetText,
            onValueChange = { Bar.targetText = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 500.dp)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun Achievements(){
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        Text(
            text = "Total typed letters: ${Bar.TotalTypedLetters}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//TOP BAR
@Composable
fun MainHeader(){
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        MenuIcon()
        EditIcon()


        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "Points: ${Bar.funTime}", fontSize = 18.sp)
    }
}


//region MENU
@Composable
fun Menu() {
    Text("HEADER")

    SettingsScreen(titleContent  = {} , onSearchClick = { },showBack = false, showSearch= false,) {
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

        //the inputs, work like you think
        val subject = ""
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

//endregion

//region Settings

@Composable
fun SettingsScreen() {
    SettingsScreen(titleContent  = {Text( "Settings")}, onSearchClick = { }) {

        SettingItem(icon = Icons.Outlined.AdminPanelSettings, title = "Permissions", subtitle = "Necesary - for app to work", onClick = { Global1.navController.navigate("SettingsP_Screen")} )

    }
}

//region PERMISSIONS

//region POPUP

fun showPermissionDialog(
    context: Context,
    title: String,
    message: String,
    settingsAction: String,
    dataUri: Uri? = null
) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setIcon(R.drawable.ic_dialog_alert)
        .setCancelable(false)
        .setPositiveButton("Grant") { _, _ ->
            Intent(settingsAction).apply {
                dataUri?.let { data = it }
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.also { context.startActivity(it) }
        }
        .setNegativeButton("Cancel", null)
        .show()
}

fun DrawOnTopP_PopUp(context: Context) =
    showPermissionDialog(
        context,
        "Grant Draw on top permission",
        Bar.DrawOnTopP_Description,
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.parse("package:${context.packageName}")
    )

fun NotificationP_PopUp(context: Context) =
    showPermissionDialog(
        context,
        "Grant Notification access",
        Bar.NotificationP_Description,
        Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
    )

fun OptimizationExclusionP_PopUp(context: Context) =
    showPermissionDialog(
        context,
        "Exclude from battery optimization",
        Bar.OptimizationExclusionP_Description,
        Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
    )

fun UsageStatsP_PopUp(context: Context) =
    showPermissionDialog(
        context,
        "Grant Usage-Access permission",
        Bar.UsageStatsP_Description,
        Settings.ACTION_USAGE_ACCESS_SETTINGS
    )

fun DeviceAdminP_PopUp(ctx: Context) {
    AlertDialog.Builder(ctx)
        .setTitle("Activate Device-Admin")
        .setMessage(Bar.DeviceAdminP_Description)
        .setIcon(R.drawable.ic_dialog_alert)
        .setCancelable(false)
        .setPositiveButton("Grant") { _, _ ->
            // Build the proper “add device admin” intent
            val comp = ComponentName(ctx, MyDeviceAdminReceiver::class.java)
            Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, comp)
                putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    Bar.DeviceAdminP_Description
                )
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.also { ctx.startActivity(it) }
        }
        .setNegativeButton("Cancel", null)
        .show()
}
class MyDeviceAdminReceiver : DeviceAdminReceiver()

//endregion POPUP


//region ENABLED??

fun isDrawOnTopEnabled(ctx: Context): Boolean =
    Settings.canDrawOverlays(ctx)

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

fun isDeviceAdminEnabled(ctx: Context): Boolean =
    (ctx.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager)
        .isAdminActive(ComponentName(ctx, MyDeviceAdminReceiver::class.java))


//endregion ENABLED??

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!AFRAID THAT MIGH BE LAGY
@Composable
fun SettingsP_Screen()= NoLagCompose {
    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        while(true) {
            Bar.NotificationPermission = isNotificationEnabled(ctx)
            Bar.DrawOnTopPermission = isDrawOnTopEnabled(ctx)
            Bar.OptimizationExclusionPermission = isBatteryOptimizationDisabled(ctx)
            Bar.UsageStatsPermission = isUsageStatsP_Enabled(ctx)
            Bar.DeviceAdminPermission = isDeviceAdminEnabled(ctx)

            log("""
            🔐 Permissions Status:
            - Notification: ${Bar.NotificationPermission}
            - Draw On Top: ${Bar.DrawOnTopPermission}
            - Battery Opt Excluded: ${Bar.OptimizationExclusionPermission}
            - Usage Stats: ${Bar.UsageStatsPermission}
            - Device Admin: ${Bar.DeviceAdminPermission}
            """.trimIndent(), "bad")

            delay(200L)
        }
    }

    SettingsScreen(titleContent = { Text("Permissions") }) {

        SettingItem(
            icon = Icons.Outlined.Notifications,
            title = "Notification",
            subtitle = "Necesary",
            endContent = {
                PermissionsButton(
                    isEnabled = Bar.NotificationPermission,
                    onEnable = {NotificationP_PopUp(ctx)}
                )
            }
        )
        SettingItem(
            icon = Icons.Outlined.Visibility,
            title = "Draw On Top",
            subtitle = "Necesary",
            endContent = {
                PermissionsButton(
                isEnabled = Bar.DrawOnTopPermission,
                onEnable = {DrawOnTopP_PopUp(ctx)}
                )
            }
        )
        SettingItem(
            icon = Icons.Outlined.BatterySaver,
            title = "Optimization Exclusion",
            subtitle = "Necesary",
            endContent = {
                PermissionsButton(
                    isEnabled = Bar.OptimizationExclusionPermission,
                    onEnable = { OptimizationExclusionP_PopUp(ctx) }
                )
            }
        )
        SettingItem(
            icon = Icons.Outlined.BarChart,
            title = "Usage Stats",
            subtitle = "Necesary",
            endContent = {
                PermissionsButton(
                    isEnabled = Bar.UsageStatsPermission,
                    onEnable = { UsageStatsP_PopUp(ctx) }
                )
            }
        )
        SettingItem(
            icon = Icons.Outlined.Security,
            title = "Device Admin",
            subtitle = "Optiona-for disipline",
            endContent = {
                PermissionsButton(
                    isEnabled = Bar.DeviceAdminPermission,
                    onEnable = { DeviceAdminP_PopUp(ctx) }
                )
            }
        )

    }
}

//endregion PERMISSIONS


//endregion Settings


//region UI BUILDERS


//endregion

//region ONCES
suspend fun Context.getAllInstalledApps(){
    Bar.AppList.forEach { if (!it.Exists) { Bar.AppList.remove(it) } }
    Bar.AppList.forEach { it.Exists = false }

    log("LIST ON PAGE CREATION: ${Bar.AppList}", "bad")

    val myPackage = context.packageName
    return withContext(Dispatchers.IO) {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { it.packageName != myPackage }
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
            .map { appInfo ->
                val name = appInfo.loadLabel(packageManager).toString()

                /*BETTER ICON
                * IT IS 15ML FASTER TO LOAD AND 14KB LESS STORAGE*/
                val original = appInfo.loadIcon(packageManager) ; val bitmap = Bitmap.createBitmap(32, 32, Bitmap.Config.RGB_565) ; val canvas = Canvas(bitmap);original.setBounds(0, 0, 32, 32);original.draw(canvas)
                val icon = BitmapDrawable(resources, bitmap)
                val pkg = appInfo.packageName

                val existing = Bar.AppList.find { it.packageName == pkg }
                if (existing != null) { existing.Exists = true } else {Bar.AppList.add(Apps(name = name, packageName = pkg));

                    log("ADDDING A NEW APP", "bad")
                }

            }

    }
}

//CHECKS IF NEW DAY/// WIRED UP TO SETTINGS VAR NEWDAY
object DayChecker {
    private var job: Job? = null
    private var lastDate: String = LocalDate.now().toString()

    fun start() {
        if (job?.isActive == true) return  // Already running

        job = CoroutineScope(Dispatchers.Default).launch {
            while (coroutineContext.isActive) {
                delay(60 * 1000L)
                val today = LocalDate.now().toString()
                if (today != lastDate) {
                    lastDate = today
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
fun MenuIcon() {
    IconButton(onClick = {Bar.ShowMenu = true}) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color(0xFFFFD700)
        )
    }
}
@Composable
fun ChillIcon() {

    IconButton(onClick = {Global1.navController.navigate("ChillScreen")}) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Menu",
            tint = Color(0xFFFFD700)
        )
    }

}

@Composable
fun EditIcon() {

    IconButton(onClick = {Global1.navController.navigate("EditScreen")}) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = Color(0xFFFFD700)
        )
    }

}
@Composable
fun Header(message: String) {
    Text(text = message, fontSize = 28.sp)
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

