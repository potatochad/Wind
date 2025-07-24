package com.productivity.wind

import android.app.AlertDialog
import android.app.AppOpsManager
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
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
import androidx.compose.ui.Modifier
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
import com.productivity.wind.OnOffSwitch
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


@Composable
fun Achievements()= NoLagCompose {
    SettingsScreen(titleContent = { Text("Achievements") }, showSearch = false) {

            
            
            SettingItem(
            icon = Icons.Outlined.AdminPanelSettings,
            title = "${toggleText(Bar.showUsageIcon)} usage icon",
            endContent = {
                    UI.OnOffSwitch(isOn = Bar.showUsageIcon, onToggle = { Bar.showUsageIcon = it }) 
               }
            )
            
            SettingItem(
            icon = Icons.Outlined.AdminPanelSettings,
            title = "Typed letters",
            endContent = {
                    Text("${Bar.TotalTypedLetters}")
               }
            )
            
    }
}

fun toggleText(state: Boolean): String {
    val action = if (state) "disable" else "enable"
    return action
}


























