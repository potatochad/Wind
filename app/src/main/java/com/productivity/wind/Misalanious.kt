package com.productivity.wind

import android.app.AlertDialog
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
import com.productivity.wind.data.PermissionType
import com.productivity.wind.ui.screen.Permissions_Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import android.provider.Settings
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import java.time.LocalDate
import androidx.compose.material.icons.outlined.QueryStats


//region NavController
//Global1.navController - to use anywhere, no input
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavGraph(navController: NavHostController, startDestination: String, modifier: Modifier = Modifier, activityResultLauncher: ActivityResultLauncher<Intent>?, requestPermissionLauncher: ActivityResultLauncher<Array<String>>?, allPermissionsGranted: Boolean = false, permissionType: PermissionType, showPermissionRequestDialog: MutableState<Boolean>, onRequestPermissions: () -> Unit, totalRequiredCount: Int, grantedCount: Int) {
    androidx.navigation.compose.NavHost(navController = navController, startDestination = startDestination) {
        composable("PERMISSIONS"){
            Permissions_Screen(
                navController = navController,
                allPermissionsGranted = allPermissionsGranted,
                activityResultLauncher = activityResultLauncher,
                requestPermissionLauncher = requestPermissionLauncher,
                permissionType = permissionType,
                showPermissionRequestDialog = showPermissionRequestDialog,
                onRequestPermissions = onRequestPermissions,
                totalRequiredCount = totalRequiredCount,
                grantedCount = grantedCount,
                configuredAppsCount = grantedCount,
            )
        }
        composable("Main") {
            Main()
        }
        composable("ChillScreen") {
            ChillScreen()
        }
        composable("RecommendedScreen") {
            RecommendedScreen()
        }
        composable("EditScreen") {
            EditScreen()
        }
        composable("Achievements") {
            Achievements()
        }
        composable("SettingsScreen") {
            SettingsScreen()
        }


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

@Composable
fun RecommendedScreen() {
    Button(onClick = {OpenDeviceAdminSettings()}) { Text("Make Uninstallable") }
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
        Global1.context.startActivity(Action)
        //endregion
    }

//endregion

//region Settings

@Composable
fun SettingsScreen() {
    SettingsScreen(titleContent  = {Text( "Settings")}, onSearchClick = { }) {

        SettingItem(
            icon = Icons.Outlined.Tune,
            title = "Permissions",
            subtitle = "Necesary - for app to work",
            endContent = { OnOffSwitch(Bar.BlockSettings) { Bar.BlockSettings = it }; Spacer(modifier = Modifier.width(35.dp)) }
        )
    }
}

//region PERMISSIONS

@Composable
fun SettingsP_Screen() {

}

//endregion PERMISSIONS


//endregion Settings


//region UI BUILDERS

@Composable
fun InfoPopup(content: @Composable () -> Unit) {
    var show by remember { mutableStateOf(true) }

    if (show) {
        Popup(onDismissRequest = { show = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000)) // semi-transparent black
                    .clickable { show = false }, // tap outside to close
                contentAlignment = Alignment.Center
            ) {
                // Animated popup box
                var visible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    visible = true
                }

                val scale by animateFloatAsState(
                    targetValue = if (visible) 1f else 0.8f,
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier
                        .graphicsLayer { scaleX = scale; scaleY = scale }
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color(0xFF2C2F48), Color(0xFF1C1C2E))
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFF88C0D0),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color(0xFFB5E8FF),
                            modifier = Modifier.size(28.dp).padding(end = 12.dp)
                        )
                        content()
                    }
                }
            }
        }
    }
}

//endregion

//region ONCES
suspend fun Context.getAllInstalledApps(){
    Bar.AppList.forEach { if (!it.Exists) { Bar.AppList.remove(it) } }
    Bar.AppList.forEach { it.Exists = false }

    log("LIST ON PAGE CREATION: ${Bar.AppList}", "bad")

    val myPackage = Global1.context.packageName
    return withContext(Dispatchers.IO) {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { it.packageName != myPackage }
            .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
            .map { appInfo ->
                val name = appInfo.loadLabel(packageManager).toString()

                /*BETTER ICON
                * IT IS 15ML FASTER TO LOAD AND 14KB LESS STORAGE*/
                val original = appInfo.loadIcon(packageManager) ; val bitmap = Bitmap.createBitmap(32, 32, Bitmap.Config.RGB_565) ; val canvas = android.graphics.Canvas(bitmap);original.setBounds(0, 0, 32, 32);original.draw(canvas)
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

fun DrawOnTopPermission(){
    context = Global1.context
    if (!Settings.canDrawOverlays(context)) {
        val alert = AlertDialog.Builder(context)
        alert.setTitle("Permission Needed")
        alert.setMessage("Grant appear on top permission")

        alert.setPositiveButton("ok") { _,_ ->
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }
        alert.create().show()
    }
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



fun OpenDeviceAdminSettings() {
    val context = Global1.context
    val intent = Intent(Settings.ACTION_SECURITY_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}
//must have this thing
class MyDeviceAdminReceiver : DeviceAdminReceiver()

val gotAdmin = (Global1.context
    .getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager)
    .isAdminActive(ComponentName(Global1.context, MyDeviceAdminReceiver::class.java))




//endregion

