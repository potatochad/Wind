package com.productivity.wind.Screens

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
        val appId = apps.find { it.name == selectedApp.value }?.id

        Text("AppUsage")
        UI.End {
            Icon.Add(onClick = {
                when {
                    Time.value.toInt() < 1 -> Vlog("add time")
                    Points.value.toInt() < 1 -> Vlog("add points")
                    selectedApp.value.isEmpty() -> Vlog("select app")

                    else -> if (appId != null) {
                        selectedApp.value = ""
                        Points.value = "0"
                        Time.value = "0"
                    }
                }
            })
        }
    }

    @Composable
    fun Main(){
        Icon.Menu()
        Icon.Chill()
        
        UI.move(w = 12)
        
        Text(text = "Points ${Bar.funTime}", fontSize = 18.sp)
        
        
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
            message = "To make continue further we need this permision for the app. Allowing us to acces your screen time of indvidual apps",
            onConfirm = {
                // Open Usage Access Settings
                val context = Global1.context
                val intent = android.content.Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    .apply { addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK) }
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
        LazyPopup(
            show = show,
            title = "Select App",
            message = "",
            content = {
                var loadedCount by remember { m(6) }

LaunchedEffect(Unit) {
    for (i in 6..apps.size) {
        wait(20)
        loadedCount = i
    }
}

LazzyList(apps) { app ->
    LazzyRow() {
        val index = apps.indexOf(app)
        if (index < loadedCount) {
            val icon = getAppIcon(app.packageName)
            UI.move(10)
            LazyImage(icon)
            UI.move(10)
            UI.Ctext(app.name) {
                selectedApp.value = app.name
                show.value = false
            }
        } else {
            LazzyRow(){
                Box(
                    Modifier
                        .padding(10.dp)
                        .size(32.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.1f)) // 10% black
                        .padding(12.dp)
                ){}
                Row(
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.1f)) // 10% black
                        .width(160.dp)
                        .height(30.dp)
                ){}
                
            }


        }
    }
}

            }
        )
    }
}






//endregion POPUP CONTROLLER



