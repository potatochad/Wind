package com.productivity.wind.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import android.service.notification.NotificationListenerService
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.*
import com.productivity.wind.Imports.*
import androidx.compose.ui.*
//region Settings



@Composable
fun SettingsScreen() {
    LazyScreen(title = { 
        Text("Settings") 
        UI.End {
           Icon.Info{
               
           }
        }
    }) {
    
        
        Item.UnlockThreshold()



        //region RESTORE/BACKUP
        
        var restoreTrigger = r { m(false) }
        var backupTrigger by r { m(false) }


        LaunchedEffect(backupTrigger) {
                if (backupTrigger) {
                        delay(1000L)
                        backupTrigger = false
                }
        }

        
        //! NOT ENCRIPTED WITH PREMIUM MIGHT BE A PROBLEM
        LazyItem(
                BigIcon = Icons.Filled.Restore,
                BigIconColor = DarkBlue,
                title = "Restore",
                onClick = { restoreTrigger.value = true },
                bottomPadding = 2.dp
        )
        LazyItem(
                topPadding = 1.dp,
                BigIcon = Icons.Filled.Backup,
                BigIconColor = DarkBlue,
                title = "BackUp",
                onClick = { backupTrigger = true }
        ) 
        
        BrestoreFromFile(restoreTrigger)
        BsaveToFile(backupTrigger)

        //endregion RESTORE/BACKUP

        
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
fun LogsScreen()= NoLagCompose {
    
    var logText by r { m("") }

    LaunchedEffect(Unit) {
        while (true) {
            logText = getLogs()
            delay(1_000)
        }
    }
        
    LazyScreen(
        title= {
                Text("Logs")
                
                UI.End { UI.CopyIcon(logText) }
        }) {
        
        
        Text(
            text = logText,
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth(),
            softWrap = false,
            maxLines = Int.MAX_VALUE
        )

        
    }
}


//endregion PERMISSIONS

class MyNotificationListener : NotificationListenerService()


//endregion
