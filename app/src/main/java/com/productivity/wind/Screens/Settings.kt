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
    LazyScreen("Settings") {
        Item.UnlockThreshold()
        Item.Restore()
        Item.Backup()

        LazyItem(
                BigIcon = Icons.Filled.Extension,
                BigIconColor = Color(0xFF9C27B0),
                title = "Extension",
                onClick = {
                    goTo("ExtensionsScreen")
                }
        ) 
        LazyItem(
                BigIcon = Icons.Filled.Tune,
                BigIconColor = Color(0xFFB0BEC5),
                title = "Other",
                onClick = { goTo("SettingsOtherScreen") }
        ) 
    }
}

@Composable
fun ExtensionsScreen() {
    var mods by r_m(mList("Mod A", "Mod B"))
    var newModName by r_m("")

    LazyScreen("Extensions") {
        LazyColumn(Mod.maxS().space(16)) {
            item {
                Text("Your Mods")
                move(16)
            }
            
            items(mods) { mod ->
                LazyCard {
                    LazyRow {
                        Text(mod)
                        Icon.Delete{
                            mods.remove(mod)
                        }
                    }
                }
            }

            item {
                move(24)
                OutlinedTextField(
                    value = newModName,
                    onValueChange = { newModName = it },
                    label = { Text("New Mod Name") },
                    modifier = Mod.maxW()
                )
                move(8)
                Button(
                    onClick = {
                        if (newModName.isNotBlank()) {
                            mods.add(newModName)
                            newModName = ""
                        }
                    },
                    modifier = Mod.maxW()
                ) {
                    Text("Add Mod")
                }
                move(h=16)
            }
        }
    }
}

@Composable
fun SettingsOtherScreen() {
    LazyScreen("Settings") {
        LazyItem(
            BigIcon = Icons.Filled.ListAlt,
            BigIconColor = Color(0xFF90A4AE),
            title = "Logs",
            onClick = { goTo("LogsScreen") }
        ) 
    }
}


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

    LazyScreen(top = {
        Header.Logs(LogsTag, filteredLogs)
    }) {
        if (Bar.logs.isEmpty()){
              UI.EmptyBox("No logs")
        } else {
           Item.Logs(filteredLogs, scrollV, scrollH) 
        }
    }
}


class MyNotificationListener : NotificationListenerService()

