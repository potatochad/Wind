package dev.hossain.keepalive

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.IBinder
import android.os.Message
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.hossain.keepalive.data.PermissionType
import dev.hossain.keepalive.data.logging.AppActivityLogger
import dev.hossain.keepalive.ui.screen.Permissions_Screen
import dev.hossain.keepalive.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber



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
        composable("FunScreen") {
            FunScreen()
        }
    }
}


//endregion






//region MICALANIOUS UI

@Composable
fun ChillTimeButton(){
    Button(onClick = { if (Bar.funTime <= 0) { Toast.makeText(Global1.context, "GET BACK TO WORK", Toast.LENGTH_SHORT).show() } else
    {
        Global1.navController.navigate("FunScreen")
    }

    }, modifier = Modifier.fillMaxWidth()) { Text("Chill Time 🌴") }
}


@Composable
fun MainHeader(){
    MenuIcon()
    ChillIcon()

}

//region MENU
@Composable
fun Menu() {
    val navController = Global1.navController
    fun Close() { Bar.ShowMenu = false }

    if (Bar.ShowMenu) {
        // Full-screen overlay; clicking it closes the menu
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC000000)).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { Close() }

        ) {
            // Center card; consume clicks so they don't bubble out
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF2C2F48), Color(0xFF1C1C2E))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFF88C0D0),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(24.dp)
                    .clickable( // consume clicks inside
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { /* no-op */ },
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MenuButton("Support", Icons.Filled.Email, Color(0xFFB5E8FF)) {
                    launchSupportEmail()
                    Close()
                }
                MenuButton("Statistics", Icons.Filled.QueryStats, Color(0xFF88C0D0)) {
                    navController.navigate("Achievements")
                    Close()
                }
                MenuButton("Settings", Icons.Filled.Settings, Color(0xFFD8DEE9)) {
                    navController.navigate("Settings")
                    Close()
                }
                MenuButton("Premium", Icons.Filled.Landscape, Color(0xFFFFD700)) {
                    navController.navigate("premium")
                    Close()
                }

                Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)

                MenuButton("Leave", Icons.Filled.ExitToApp, Color(0xFFFF6F61)) {
                    Close()
                }
            }
        }
    }
}

@Composable
fun MenuButton(label: String, icon: ImageVector, tint: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color(0xFF2E3440), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}


fun launchSupportEmail() {

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
        Global1.context.startActivity(Action)
        //endregion
    }

//endregion





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
fun Header(message: String) {
    Text(text = message, fontSize = 28.sp)
}

//endregion

