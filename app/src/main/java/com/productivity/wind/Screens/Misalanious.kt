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
import com.productivity.wind.Bar
import com.productivity.wind.Global1
import com.productivity.wind.LazyPopup
import com.productivity.wind.NoLagCompose
import com.productivity.wind.PermissionsButton
import com.productivity.wind.R
import com.productivity.wind.SettingItem
//import com.productivity.wind.WatchdogAccessibilityService
import com.productivity.wind.apps
import com.productivity.wind.log
import com.productivity.wind.SettingsScreen
import com.productivity.wind.SettingsSaved
import com.productivity.wind.UI
import com.productivity.wind.Popup
import com.productivity.wind.Vlog
import com.productivity.wind.Screens.Challenge
import com.productivity.wind.Achievements
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind

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
            composable("Challenge") {
                    Challenge()
            }

            //region WEB

            composable("Web") {
                Web()
            }
            composable("FunWeb") {
                FunWeb()
            }
            composable("WorkWeb") {
                WorkWeb()
            }


            //endregion WEB


            //region SETTINGS

            composable("SettingsScreen") {
                SettingsScreen()
            }
            composable("SettingsOtherScreen") {
                    SettingsOtherScreen()
            }
            composable("LogsScreen") {
                    LogsScreen()
            }

            //endregion SETTINGS

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
        Icon.Menu()
        Icon.Chill()
        
        UI.move(w = 12)
        
        Text(text = "Points ${Bar.funTime}", fontSize = 18.sp)
        
        
        UI.End { 
                Icon.Add()
        }
        
        
    }

}











@Composable
fun MenuHeader6(
    title: String = "Wind",
    iconRes: Int = R.drawable.baseline_radar_24,
    iconSize: Dp = 60.dp,
    iconTint: Color = Color(0xFFFFD700),
    titleSize: TextUnit = 28.sp,
    topPadding: Dp = 8.dp,
    bottomPadding: Dp = 20.dp
) {
    val safeStartPadding = max(0.dp, Bar.halfWidth / 2 - 50.dp)

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
        showDivider = false,
        MheaderHeight = 500,
    ) {
        SettingItem(
            icon = Icons.Outlined.Chat,
            title = "Contact Support",
            onClick = { SupportEmail(); Bar.ShowMenu = false }
        )
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




//region CONFIGURE SCREEN




//endregion CONFIGURE SCREEN

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


object Icon {
        

        @Composable
        fun Menu() {
                UI.SimpleIconButton(
                        onClick = { Bar.ShowMenu = true },
                        icon = Icons.Default.Menu
                )
        }

@Composable
fun Chill() {
          UI.SimpleIconButton(
                  onClick = { Global1.navController.navigate("Web") },
                  icon = Icons.Default.SportsEsports
          )
}
@Composable
fun Add() {
  UI.SimpleIconButton(
            onClick = { Global1.navController.navigate("Challenge") },
            icon = Icons.Default.Add
        )
}


@Composable
fun Edit() {
    UI.SimpleIconButton(
            onClick = { 
                    if (Bar.funTime > Bar.Dpoints) Popup.Edit.value =true
                    else Popup.NeedMorePoints.value = true
            },
            icon = Icons.Default.Edit
        )

}
@Composable
fun G_Edit() {
    UI.SimpleIconButton(
            onClick = { 
                    if (Bar.funTime > Bar.Dpoints) Popup.G_Edit.value =true
                    else Popup.NeedMorePoints.value = true
            },
            icon = Icons.Default.Edit
        )
}

 //ICONS!!!!!!-------------------------///
}





@Composable
fun StopBlockingButton() {
    UI.OnOffSwitch(
        isOn = Bar.BlockingEnabled,
        onToggle = { isNowOn ->
            if (isNowOn) {
                Popup.EnableBlocking.value = true
            } else {
                val hasPoints = Bar.funTime > Bar.Dpoints
                if (hasPoints) {
                    Bar.BlockingEnabled = false
                } else {
                    Popup.NeedMorePoints.value = true
                }
            }
        }
    )
}


//endregion

