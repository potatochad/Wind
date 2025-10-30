package com.productivity.wind.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
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
//region Settings



@Composable
fun SettingsScreen() {
    LazyScreen(title = { 
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
    LazyScreen(title = { Text("Settings") }) {

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
    // Load logs once
    RunOnce {
        Bar.logs = getMyAppLogs().joinToString("\n")
    }

    val scrollStateV = rememberScrollState()
    val scrollStateH = rememberScrollState()

    // Auto-scroll to the end when logs change
    LaunchedEffect(Bar.logs) {
        scrollStateV.scrollTo(scrollStateV.maxValue)
    }

    LazyScreen(
        title = {
            Text("Logs")
            UI.End {
                Row {
                    Icon.Delete { Bar.logs = "" }
                    Icon.Copy(Bar.logs)
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .verticalScroll(scrollStateV)
                .horizontalScroll(scrollStateH)
                .maxW()
                .padding(2.dp) 
        ) {
            Text(
                text = Bar.logs,
                modifier = Modifier
                    .maxS(),
                softWrap = yes,
            )
        }
    }
}



//endregion PERMISSIONS

class MyNotificationListener : NotificationListenerService()


//endregion
