package com.productivity.wind.Screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.OutlinedTextField
import com.productivity.wind.*
import com.productivity.wind.Achievements
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.productivity.wind.Imports.*
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.*
import androidx.compose.animation.core.*

fun NavGraphBuilder.ScreenNav() {
    //Main—StartDestination    
    url("Main") { Main() }

    url("Achievements") { Achievements() }
    url("Challenge") { Challenge() }
    url("AppUsage") { AppUsage() }

    url("Web") { Web() }
    url("FunWeb") { FunWeb() }
    url("WorkWeb") { WorkWeb() }

    url("SettingsScreen") { SettingsScreen() }
    url("SettingsOther") { SettingsOtherScreen() }
    url("LogsScreen") { LogsScreen() }
    
}




@Composable
fun Menu() {
    LazyScreen(
        title ={ UI.MenuHeader()},
        showBack = false,
        showDivider = false,
        MheaderHeight = 700,
    ) {
        LazyItem(
            icon = Icons.Outlined.Chat,
            title = "Contact Support",
            onClick = { UI.SendEmail(); Bar.ShowMenu = false }
        )
        LazyItem(
            icon = Icons.Outlined.Landscape,
            title = "Settings",
            onClick = { goTo("SettingsScreen"); Bar.ShowMenu = false  }
        )
        LazyItem(
            icon = Icons.Outlined.QueryStats,
            title = "Achievements",
            onClick = { goTo("Achievements"); Bar.ShowMenu = false }
        )


        
    }

    
}




object Header {
    
    @Composable
    fun AppUsage(Time: MutableState<String>, Points: MutableState<String>, selectedApp: MutableState<String>) {
        Text("AppUsage")
        UI.End {
            Icon.Add(onClick = {
                when {
                    Time.value.toInt() < 1 -> {
                        Vlog("add time")
                        return@Add
                    }
                    Points.value.toInt() < 1 -> {
                        Vlog("add points")
                        return@Add
                    }
                    selectedApp.value.isEmpty() -> {
                        Vlog("select app")
                        return@Add
                    }
                }

                val app = apps.find { it.name == selectedApp.value }
                if (app == null) {
                    Vlog("NO such app found")
                    return@Add
                }

                if (app.Worth == 0) {
                    edit(apps, app.id) {
                        DoneTime = Time.value.toIntOrNull() ?: 0
                        Worth = Points.value.toIntOrNull() ?: 0
                    }
                } else {
                    add(apps) {
                        name = selectedApp.value
                        DoneTime = Time.value.toIntOrNull() ?: 0
                        Worth = Points.value.toIntOrNull() ?: 0
                    }
                }

                selectedApp.value = ""
                Points.value = "0"
                Time.value = "0"

                goTo("Main")
            })
        }
    }


    @Composable
    fun Main(){
        Icon.Menu()
        Icon.Chill()
        
        UI.move(w = 12)
        
        Text("Points ${Bar.funTime}")
        
        UI.End {
                Icon.Add()
        }
    }

    
}


object Icon {
        @Composable
        fun Menu() {
                LazyIcon(
                        onClick = { Bar.ShowMenu = true },
                        icon = Icons.Default.Menu
                )
        }
        
        @Composable
        fun Chill() {
                LazyIcon(
                        onClick = { goTo("Web") },
                        icon = Icons.Default.SportsEsports
                )
        }
        
        @Composable
        fun Add(onClick: () -> Unit = { goTo("Challenge") }) {
                LazyIcon(
                        onClick = onClick,
                        icon = Icons.Default.Add
                )
        }
        
        @Composable
        fun Edit() {
                LazyIcon(
                        onClick = { 
                                if (Bar.funTime > Bar.Dpoints) show(Popup.Edit)
                                else show(Popup.NeedMorePoints)
                        },
                        icon = Icons.Default.Edit
                )
        }

 //ICONS!!!!!!-------------------------///
}







//region POPUP CONTROLLER

//!Just call this on app start
@Composable
fun PopUps(){
   AskUsagePermission(Popup.AskUsagePermission)
   EditPopUp(Popup.Edit)
   NeedMorePointsPopup(Popup.NeedMorePoints)
   AppSelectPopup(Popup.AppSelect)
}


// All other things
@Composable
fun NeedMorePointsPopup(show: MutableState<Boolean>){
    LazyPopup(
        show = show, 
        title = "Not EnoughPoints", 
        message = "Need have ${Bar.Dpoints} points to do this. Only have ${Bar.funTime}"
    )
}

@Composable
fun EditPopUp(show: MutableState<Boolean>) {
    var TemporaryTargetText by remember { mutableStateOf("") }
    TemporaryTargetText = Bar.targetText
    LazyPopup(
        show = show,
        onDismiss = { TemporaryTargetText = Bar.targetText },
        title = "Edit Text",
        message = "",
        content = {
            OutlinedTextField(
                value = TemporaryTargetText,
                onValueChange = { TemporaryTargetText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        showCancel = true,
        onConfirm = { Bar.targetText = TemporaryTargetText; Bar.FirstEditText = false },
        onCancel = { TemporaryTargetText = Bar.targetText }
    )
}


@Composable
fun AskUsagePermission(show: MutableState<Boolean>) {
    if (show.value) {
        LazyPopup(
            show = show,
            title = "Need Usage Permission",
            message = "Ahhh, mon cher, zis is ze moment, oui oui! We must, we must have ze permission, ha ha! Let us peeky-peek at ze screen time of your apps, magnifique—oh la la, ze app goes zoom-zoom perfectly, trés bien, oui oui!",
            onConfirm = {
                // Open Usage Access Settings
                val context = Global1.context
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                context.startActivity(intent)
            }
        )
    }
}


var selectedApp = m("")
suspend fun wait(ms: Long) {
    kotlinx.coroutines.delay(ms)
}

@Composable
fun AppSelectPopup(
    show: MutableState<Boolean>,
) {
    if (show.value) {
        val appList by remember { mutableStateOf(getApps()) }
        var loadedCount by remember { mutableStateOf(6) }

        // gradually increase loaded count
        LaunchedEffect(Unit) {
            for (i in 6..appList.size) {
                wait(20)
                loadedCount = i
            }
        }

        LazyPopup(
            show = show,
            title = "Select App",
            message = "",
            content = {
                // Only pass the loaded items to the LazyList
                LazzyList(appList.take(loadedCount)) { appInfo ->
                    val icon = getAppIcon(getAppPackage(appInfo))
                    LazzyRow {
                        UI.move(10)
                        LazyImage(icon)
                        UI.move(10)
                        UI.Ctext(getAppName(appInfo)) {
                            selectedApp.value = getAppName(appInfo)
                            show.value = false
                        }
                    }
                }
            }
        )
    }
}
