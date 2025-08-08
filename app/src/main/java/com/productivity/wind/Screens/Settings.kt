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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import java.time.LocalDate
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
import com.productivity.wind.*
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import java.io.*


//region Settings

val DarkBlue = Color(0xFF00008B) 
val Gold = Color(0xFFFFD700)

@Composable
fun SettingsScreen() {
    SettingsScreen(titleContent = { Text("Settings") }) {

        if (!areAllPermissionsEnabled()) {
                SettingItem(
                        BigIcon = Icons.Filled.AdminPanelSettings,
                        BigIconColor = Color(0xFFFFD700),
                        title = "Permissions",
                        onClick = { Global1.navController.navigate("SettingsP_Screen") },
                        bottomPadding = 2.dp,
                )    
        }

        // NOT SYNCHED UP WITH APP YET
        //NEED EXPLANATION TOO
        SettingItem(
                topPadding = 1.dp,
                BigIcon = Icons.Filled.LockOpen,
                BigIconColor = Gold,
                title = "Unlock Threshold",
                endContent = {
                        UI.InputField(
                                value = Bar.Dpoints.toString(),
                                onValueChange = {
                                        val input = it.toIntOrNull() ?: 0  // convert input to number safely
                                        if (input > Bar.funTime) {
                                                Vlog("$input input > ${Bar.funTime}p= get more points", "one")
                                        } else {
                                                Bar.Dpoints = input
                                        }
                                },
                                InputWidth = 60.dp,
                                isNumber = true,
                                MaxLetters = 5,
                                OnMaxLetters = {
                                        Vlog("MAX: 99999 points")
                                },
                                //OnFocusLose = { if (Bar.Dpoints<1) Bar.Dpoints =0 }
                        ) 
                }
        )



        //region RESTORE/BACKUP
        
        var restoreTrigger = remember { mutableStateOf(false) }
        var backupTrigger by remember { mutableStateOf(false) }


        LaunchedEffect(backupTrigger) {
                if (backupTrigger) {
                        delay(1000L)
                        backupTrigger = false
                }
        }

        
        //! NOT ENCRIPTED WITH PREMIUM MIGHT BE A PROBLEM
        SettingItem(
                BigIcon = Icons.Filled.Restore,
                BigIconColor = DarkBlue,
                title = "Restore",
                onClick = { restoreTrigger.value = true },
                bottomPadding = 2.dp
        )
        SettingItem(
                topPadding = 1.dp,
                BigIcon = Icons.Filled.Backup,
                BigIconColor = DarkBlue,
                title = "BackUp",
                onClick = { backupTrigger = true }
        ) 
        
        UI.BrestoreFromFile(restoreTrigger)
        UI.BsaveToFile(backupTrigger)

        //endregion RESTORE/BACKUP

        Spacer(modifier = Modifier.fillMaxHeight())

        SettingItem(
                BigIcon = Icons.Filled.Extension,
                BigIconColor = Color(0xFF9C27B0),
                title = "Extension ${Tests[0].name}",
            onClick = {
                val newName = Tests[0].name + "D"
                Tests[0] = Tests[0].copy(name = newName)

            }

        ) 
        SettingItem(
                BigIcon = Icons.Filled.Tune,
                BigIconColor = Color(0xFFB0BEC5),
                title = "Other",
                onClick = { Global1.navController.navigate("SettingsOtherScreen") }
        ) 
    }
}


//region PERMISSIONS


//region OTHER SCREEN

@Composable
fun SettingsOtherScreen() {
    SettingsScreen(titleContent = { Text("Settings") }) {

        SettingItem(
            icon = Icons.Outlined.AdminPanelSettings,
            title = "Show Achievement icon shortcut",
            endContent = {
                    UI.OnOffSwitch(isOn = Bar.showUsageIcon, onToggle = { Bar.showUsageIcon = it }) 
               }
            )
        SettingItem(
            icon = Icons.Outlined.AdminPanelSettings,
            title = "Show Logs shortcut",
            endContent = {
                    UI.OnOffSwitch(isOn = Bar.showLogsIcon, onToggle = { Bar.showLogsIcon= it }) 
               }
            )
        SettingItem(
                BigIcon = Icons.Filled.ListAlt,
                BigIconColor = Color(0xFF90A4AE),
                title = "Logs",
                onClick = { Global1.navController.navigate("LogsScreen") }
        ) 


        
    }
}

//endregion OTHER SCREEN

//region POPUP

@Composable
fun NotificationP_PopUp(show: MutableState<Boolean>) =
        LazyPopup(
        show = show,
        onConfirm = {
            UI.openPermissionSettings(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        },
        title = "Grant Notification access",
        message = Bar.NotificationP_Description,
    )
    
@Composable
fun OptimizationExclusionP_PopUp(show: MutableState<Boolean>) =
        LazyPopup(
                show = show,
                onConfirm = {
                        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                                data = Uri.parse("package:${context.packageName}")
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(intent)
                },
                title = "Exclude from battery optimization",
                message = Bar.OptimizationExclusionP_Description,
        )

@Composable
fun UsageStatsP_PopUp(show: MutableState<Boolean>) =
        LazyPopup(
        show = show,
        onConfirm = {
            UI.openPermissionSettings(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        },
        title = "Grant Usage-Access permission",
        message = Bar.UsageStatsP_Description,
    )

//To lazy to delete this and manifest
class MyDeviceAdminReceiver : DeviceAdminReceiver() {}

//endregion POPUP



//region ENABLED??

fun areAllPermissionsEnabled(): Boolean {
    return UI.isNotificationEnabled()
            && UI.isBatteryOptimizationDisabled()
            && UI.isUsageStatsP_Enabled()
}

//endregion ENABLED??


@Composable
fun SettingsP_Screen()= NoLagCompose {
    LaunchedEffect(Unit) {
        while(true) {
            Bar.NotificationPermission = UI.isNotificationEnabled()
            Bar.OptimizationExclusionPermission = UI.isBatteryOptimizationDisabled()
            Bar.UsageStatsPermission = UI.isUsageStatsP_Enabled()
            delay(200L)
        }
    }
    var showNotificationPopup = remember { mutableStateOf(false) }
    var showOptimizationPopup = remember { mutableStateOf(false) }
    var showUsagePopup = remember { mutableStateOf(false) }


    NotificationP_PopUp(showNotificationPopup)
    OptimizationExclusionP_PopUp(showOptimizationPopup)
    UsageStatsP_PopUp(showUsagePopup)


    SettingsScreen(titleContent = { Text("Permissions") }) {

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











@Composable
fun LogsScreen() {
    
    val context = LocalContext.current
    var logText by remember { mutableStateOf("Loading...") }
        
    LaunchedEffect(Unit) {
        val file = File(context.filesDir, "vlog.txt")
        logText = try {
        file.readText()
        } catch (e: Exception) {
            "❌ Error reading logs: ${e.message}"
        }

    }
        
    SettingsScreen(
        titleContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logs")
                Spacer(modifier = Modifier.weight(1f))
                UI.CopyIcon(logText)
            }
        }) {
        
        Text(text = logText)
    }
}


//endregion PERMISSIONS

class MyNotificationListener : NotificationListenerService()


//endregion
