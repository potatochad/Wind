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
import androidx.compose.ui.platform.*
import kotlinx.coroutines.*

fun NavGraphBuilder.ScreenNav() {
    //Main—StartDestination    
    url("Main") { Main() }

    url("Achievements") { Achievements() }
    url("Challenge") { Challenge() }
    url("AppUsage") { AppUsage() }
    url("CopyPaste") { CopyPaste() }

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
        headerHeight = 190,
    ) {
        LazyItem(
            icon = Icons.Outlined.Chat,
            title = "Contact Support",
            onClick = { UI.SendEmail(); App.Menu = false }
        )
        LazyItem(
            icon = Icons.Outlined.Landscape,
            title = "Settings",
            onClick = { goTo("SettingsScreen"); App.Menu = false }
        )
        LazyItem(
            icon = Icons.Outlined.QueryStats,
            title = "Achievements",
            onClick = { goTo("Achievements"); App.Menu = false }
        )


        
    }

    
}


object Item {
    @Composable
    fun AppTaskUI(app: DataApps){
            val icon = getAppIcon(app.pkg)
            val progress = (app.NowTime.toFloat() / app.DoneTime.toFloat()).coerceIn(0f, 1f)

            if (app.NowTime > app.DoneTime - 1 && !app.done) {
                Bar.funTime += app.Worth
                apps.edit(app) { done = true }
                Vlog("${app.name} completed")
            }

            LazyCard {
                LazzyRow {
                    UI.move(10)

                    LazyInfo(infoContent = { Text("${app.NowTime}s/${app.DoneTime}s") }) {
                        UI.ProgressIcon(icon, progress)
                    }


                    UI.move(12)
                    Text("Points ${app.Worth}")
                    LazyInfo(infoContent = { Text("you clicked MORE MENU") }) {
                            UI.End {
                               Icon.MoreMenu {}
                            }
                    }
                    


                }
            }
    }
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
    @Composable
    fun Restore() {
        var restoreTrigger = r_m(false)

        LazyItem(
            BigIcon = Icons.Filled.Restore,
            BigIconColor = DarkBlue,
            title = "Restore",
            onClick = { 
                if (Bar.Dpoints > Bar.funTime) {
                    show(NeedMorePoints)    
                } else {
                    set(restoreTrigger, true) 
                }
            },
            bottomPadding = 2.dp
        )

        BrestoreFromFile(restoreTrigger)
    }

    @Composable
    fun Backup() {
        var backupTrigger by r_m(false)

        LaunchedEffect(backupTrigger) {
            if (backupTrigger) {
                delay(1000L)
                backupTrigger = false
            }
        }

        LazyItem(
            topPadding = 1.dp,
            BigIcon = Icons.Filled.Backup,
            BigIconColor = DarkBlue,
            title = "BackUp",
            onClick = { backupTrigger = true }
        )

        BsaveToFile(backupTrigger)
    }

    @Composable
    fun WebPointTimer(){
        if (Bar.Dpoints > 0){
            each(1000L){
                if (Bar.funTime == 0) {
                    goTo("main")
                    show(NeedMorePoints)
                }
                else{
                    Bar.funTime -=1
                }
            }
        }
    }
    
}

object Header {
    
    @Composable
    fun AppUsage(Time: m_<Str>, Points: m_<Str>, selectedApp: m_<Str>) {
        Text("AppUsage")
        UI.End {
            Icon.Add(onClick = {

                UI.check(!UI.isUsageP_Enabled()) { show(AskUsagePermission); return@Add}
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
                

                set(selectedApp, "")
                set(Points, "0")
                set(Time, "0")

                goTo("Main")
            })
        }
    }
    @Composable
    fun CopyPaste(){
        Text("Copy Paste")
        
        UI.End {
                Icon.Add {
                    
                    
                }
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
                        onClick = { App.Menu=true},
                        icon = Icons.Default.Menu
                )
        }
        @Composable
        fun Chill() {
                LazyIcon(
                        onClick = { 
                            if (Bar.funTime < Bar.Dpoints) {show(NeedMorePoints)}
                            else {goTo("Web")}
                        },
                        icon = Icons.Default.SportsEsports
                )
        }
        
        @Composable
        fun Add(onClick: Do = { goTo("Challenge") }) {
                LazyIcon(
                        onClick = onClick,
                        icon = Icons.Default.Add
                )
        }
        @Composable
        fun MoreMenu(onClick: Do = {}) {
            LazyIcon(
                onClick = onClick,
                icon = Icons.Default.MoreVert
            )
        }

        
        @Composable
        fun Edit() {
                LazyIcon(
                        onClick = { 
                                if (Bar.funTime < Bar.Dpoints) show(NeedMorePoints)
                                else show(Edit)
                        },
                        icon = Icons.Default.Edit
                )
        }

 //ICONS!!!!!!-------------------------///
}







//region POPUP CONTROLLER
fun <T> set(state: m_<T>?, value: T) { state?.value = value }

fun show(state: m_<Boolean>?) = set(state, true)
fun hide(state: m_<Boolean>?) = set(state, false)


var Edit = m(false)
var NeedMorePoints = m(false)
var AskUsagePermission = m(false)
var AppSelect = m(false)

object Popup {
    
    @Composable
    fun Init(){
        AskUsagePermission(AskUsagePermission)
        EditPopUp(Edit)
        NeedMorePointsPopup(NeedMorePoints)
        AppSelectPopup(AppSelect)
    }
    
    

}


@Composable
fun NeedMorePointsPopup(show: m_<Bool>){
    LazyPopup(
        show = show, 
        title = "Get more points", 
        message = "Only need ${Bar.funTime}(points)-${Bar.Dpoints}(unlock)=${Bar.funTime-Bar.Dpoints}",
        showCancel = false,
    )
}

@Composable
fun EditPopUp(show: m_<Bool>) {
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
fun AskUsagePermission(show: m_<Bool>) {
    if (show.value) {
        LazyPopup(
            show = show,
            title = "Need Usage Permission",
            message = "To function correctly, this app requires access to your app usage data. Granting this permission allows the app to monitor usage statistics and manage app-related tasks efficiently. Without it, this feature won't work.",
            onConfirm = {
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                App.ctx.startActivity(intent)
            }
        )
    }
}


var selectedApp = m("")


@Composable
fun AppSelectPopup(show: m_<Bool>) {
    if (show.value) {
        val myPackage = LocalContext.current.packageName // your app's package
        var appList by r_m(getApps().filter { getAppPackage(it) != myPackage }) // filter self out

        LazyPopup(
            show = show,
            showCancel = false,
            showConfirm = false,
            title = "Select App",
            message = "",
            content = {

                // Only pass the filtered items to the LazyList
                LazzyList(appList) { app ->
                    val icon = getAppIcon(getAppPackage(app))
                    LazzyRow {
                        UI.move(10)
                        LazyImage(icon)
                        UI.move(10)
                        
                        UI.Ctext(getAppName(app)) {
                            selectedApp.value = getAppName(app)
                            show.value = false
                        }
                    }
                }
            }
        )
    }
}
