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
import com.productivity.wind.Vlog
import com.productivity.wind.Achievements
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import com.productivity.wind.Imports.*
import androidx.compose.foundation.lazy.*

@Composable
fun Challenge() {
  LazyScreen(titleContent = { Text("Challenge") }) {
    LazyItem(
            BigIcon = Icons.Filled.Backup,
            BigIconColor = DarkBlue,
            title = "App Usage",
            onClick = { Global1.navController.navigate("AppUsage")},
        )
    
   }
}

@Composable
fun AppUsage2() {
  var Time = remember { m("50") }
  var Points = remember { m("0") }

  LazyScreen(titleContent = { Text("App Usage") }) {

        
            refreshApps()
                
                Text("If")
                Text(" spend ")
                UI.Cinput(Time){ 
                    Time.value = it 
                }
                Text(" seconds ")

                Text("on ")
                UI.Ctext("app"){
                    
                }
                Text(", add")
                UI.Cinput(Points) {
                  Points.value = it
                }
                Text(" points")
                
                
    
   }
}



@Composable
fun AppUsage() {
    var Time = remember { m("50") }
    var Points = remember { m("0") }
    var selectedApp = remember { m("") }
    var showAppList = remember { m(false) }

    LazyScreen(titleContent = { Text("App Usage") }) {
        refreshApps()

        LazzyRow {
          
        Text("If")
        Text(" spend ")
        UI.Cinput(Time)
        Text(" seconds ")

        Text("on ")
        UI.Ctext(
            if (selectedApp.value.isEmpty()) "app" else selectedApp.value
        ) {
            showAppList.value = true
        }

        Text(", add ")
        UI.Cinput(Points)
        Text(" points")
        }
    }

    // Popup list for selecting app
    if (showAppList.value) {
        LazyPopup(
            show = showAppList,
            title = "Select App",
            message = "",
            content = {
                LazzyList(
                  apps.toList(),
                ) { app ->
                  LazzyRow{
                    val icon = getAppIcon(app.packageName)

                    icon?.let {
                      Image(
                        painter = rememberDrawablePainter(it),
                        contentDescription = app.name,
                        modifier = Modifier.size(20.dp).padding(end = 6.dp)
                      )
                    }

                    
                  UI.Ctext(app.name) {
                    selectedApp.value = app.name
                  }
                }
                }
                
            }
        )
    }
}






@Composable
fun <T> LazzyList(
    list: List<T>,
    modifier: Modifier = Modifier.heightIn(max = 200.dp),
    itemContent: @Composable (T) -> Unit
) =NoLagCompose {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        list.forEach { item ->
            itemContent(item)
        }
    }
}
@Composable
fun getAppIcon(packageName: String): Drawable? {
    val context = Global1.context
    val pm = context.packageManager
    return remember(packageName) {
        try {
            pm.getApplicationIcon(packageName)
        } catch (_: Exception) {
            null
        }
    }
}

