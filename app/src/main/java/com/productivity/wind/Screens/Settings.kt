package com.productivity.wind.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import android.service.notification.NotificationListenerService
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.*
import com.productivity.wind.Imports.*
import com.productivity.wind.*
import com.productivity.wind.Imports.Data.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.*
import androidx.compose.foundation.lazy.*


@Composable
fun SettingsScreen() {
    LazyScreen(top = { 
        Text("Settings") 
    }) {
        Item.UnlockThreshold()
        Item.Restore()
        Item.Backup()

        LazyItem(
                BigIcon = Icons.Filled.Extension,
                BigIconColor = Color(0xFF9C27B0),
                title = "Extension",
                onClick = {}
        ) 
        LazyItem(
                BigIcon = Icons.Filled.Tune,
                BigIconColor = Color(0xFFB0BEC5),
                title = "Other",
                onClick = { goTo("SettingsOtherScreen") }
        ) 
    }
}


//region PERMISSIONS


//region OTHER SCREEN

@Composable
fun SettingsOtherScreen() {
    LazyScreen(top = { Text("Settings") }) {

        LazyItem(
                BigIcon = Icons.Filled.ListAlt,
                BigIconColor = Color(0xFF90A4AE),
                title = "Logs",
                onClick = { goTo("LogsScreen") }
        ) 
    }
}

//endregion OTHER SCREEN


@Composable
fun LogsScreen() {
    var Reload = r_m(no)
    var scrollV = rememberLazyListState()
    var scrollH = r_Scroll()
    var LogsTag = r_m("")
    
    RunOnce { 
        delay(100)
        scrollV.toBottom()
        getMyAppLogs() 
    }


    val filteredLogs = Bar.logs.lines()
        .filter { it.contains(LogsTag.it) }
        .joinToString("\n")

    LazyScreen(top={Header.Logs(LogsTag, filteredLogs)}) {
        if (Bar.logs.isEmpty()){
              UI.EmptyBox("No logs")
        } else {
           Item.Logs(filteredLogs, scrollV, scrollH) 
        }
    }
}


//endregion PERMISSIONS

class MyNotificationListener : NotificationListenerService()


//endregion
