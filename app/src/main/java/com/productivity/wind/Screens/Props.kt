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


object Item {
    @Composable
    fun UnlockThreshold() {
        LazyItem(
            topPadding = 1.dp,
            BigIcon = Icons.Filled.LockOpen,
            BigIconColor = Gold,
            title = "Unlock Threshold",
            endContent = {
                UI.Input(Bar.Dpoints.toString()) { it ->
                    val filtered = it.filter { it.isDigit() }.take(5)
                    val input = filtered.toIntOrNull() ?: 0
                    if (input > Bar.funTime) {
                        Vlog("$input input > ${Bar.funTime} → get more points", "one")
                    } else {
                        Bar.Dpoints = input
                    }
                }
            }
        )
    }
    
}

object Header {
    
    @Composable
    fun AppUsage(Time: MutableState<String>, Points: MutableState<String>, selectedApp: MutableState<String>) {
        Text("AppUsage")
        UI.End {
            Icon.Add(onClick = {

                UI.check(!UI.isUsageP_Enabled()) {Popup.AskUsagePermission.value = true; return@Add}
                UI.check(Time.value.toInt() < 1,"Add time") {return@Add}
                UI.check(Points.value.toInt() < 1,"Add points") {return@Add}
                UI.check(selectedApp.value.isEmpty(),"Select app") {return@Add}


                val app = apps.find { it.name == selectedApp.value }
                if (app == null) {
                    Vlog("NO such app found")
                    return@Add
                }

                if (app.Worth == 0) {
                    apps.edit(app){
                        DoneTime = Time.value.toIntOrNull() ?: 0
                        Worth = Points.value.toIntOrNull() ?: 0
                    }
                } else {
                    apps.new(
                        DataApps(
                            name = selectedApp.value,
                            DoneTime = Time.value.toIntOrNull() ?: 0,
                            Worth = Points.value.toIntOrNull() ?: 0,
                        )
                    )
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
    var TemporaryTargetText by r { m("") }
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
            message = "To function correctly, this app requires access to your app usage data. Granting this permission allows the app to monitor usage statistics and manage app-related tasks efficiently. Without it, this feature won't work.",
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
        val appList by r { m(getApps()) }
        val searchQuery by r { m("") }

        LazyPopup(
            show = show,
            title = "Select App",
            message = "",
            content = {
                // Search field
                UI.Input(searchQuery) { 
                    searchQuery = it 
                },
                

                // Filter apps based on search query
                val filteredList = appList.filter { appInfo ->
                    getAppName(appInfo).contains(searchQuery, ignoreCase = true)
                }

                // Only pass the filtered items to the LazyList
                LazzyList(filteredList) { appInfo ->
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
