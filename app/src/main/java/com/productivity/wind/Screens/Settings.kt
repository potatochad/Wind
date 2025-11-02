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
    var Reload by m(no)
    var scrollV = r_Scroll()
    var scrollH = r_Scroll()
    var LogsTag = r_m("")

    
    RunOnce(Reload) {
        val logs = getMyAppLogs().lines()
        Bar.Newlogs = logs - Bar.Oldlogs.toSet()
        Bar.Oldlogs = logs
        scrollV.toBottom()
        Reload=no
    }


    val filteredLogs = Bar.Newlogs.lines()
        .filter { it.contains(Bar.logsTag) }
        .joinToString("\n")

    LazyScreen(
        title = {
            Row(
                Modifier.scroll(h = yes),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyInput(
                    LogsTag,
                    modifier = Modifier
                        .h(34)
                        .space(h = 8, v = 4)
                        .w(60)
                        .background(CardColor, shape = RoundedCornerShape(4.dp))
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {
                    Bar.logsTag = LogsTag.it
                }
            }
            
            UI.End {
                Row {
                    Icon.Delete { 
                        Bar.Newlogs = ""
                        Bar.Oldlogs = getMyAppLogs().lines()
                        Reload = yes
                    }
                    Icon.Copy(Bar.Newlogs)
                    Icon.Reload { Reload = yes }
                }
            }
            
        }
    ) {
        Box(Modifier
                .scroll(yes, yes, scrollV, scrollH)
                .maxW()
                .space(h=6) 
        ) {
            Text(
                text = filteredLogs,
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
