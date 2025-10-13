package com.productivity.wind.Screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.OutlinedTextField
import com.productivity.wind.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.productivity.wind.Imports.*
import androidx.compose.ui.platform.*
import kotlinx.coroutines.*
import android.webkit.*
import com.productivity.wind.Imports.Data.*

fun NavGraphBuilder.ScreenNav() {
    //Main—StartDestination    
    url("Main") { Main() }

    url("Achievements") { Achievements() }
    url("Challenge") { Challenge() }
    url("AppUsage") { AppUsage() }
    url("CopyPaste") { CopyPaste() }

    url("Web") { Web() }
    url("BlockKeyword") { BlockKeyword() }
    

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
    fun AppTaskUI(app: AppTsk){
            val icon = getAppIcon(app.pkg)
            val progress = (app.NowTime.toFloat() / app.DoneTime.toFloat()).coerceIn(0f, 1f)

            if (app.NowTime > app.DoneTime - 1 && !app.done) {
                Bar.funTime += app.Worth
                Bar.apps.edit(app) { done = true }
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
                                LazyInfo(infoContent = { Text("you clicked MORE MENU") }) {
                                    Icon.MoreMenu {}
                                }
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


                val app = Bar.apps.find { it.name == selectedApp.value }
                if (app == null) {
                    Vlog("NO such app found")
                    return@Add
                }

                if (app.Worth == 0) {
                    Bar.apps.edit(app){
                        DoneTime = Time.value.toIntOrNull() ?: 0
                        Worth = Points.value.toIntOrNull() ?: 0
                    }
                } else {
                    Bar.apps.new(
                        AppTsk(
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
            onClick = { App.Menu = true },
            icon = Icons.Default.Menu,
        )
    }

    @Composable
    fun Reload(webView: m_<WebView?>) {
        LazyIcon(
            onClick = { webView.it?.reload() },
            icon = Icons.Default.Refresh,
        )
    }

    @Composable
    fun Chill() {
        LazyIcon(
            onClick = {
                if (Bar.funTime < Bar.Dpoints) {
                    show(NeedMorePoints)
                } else {
                    goTo("Web")
                }
            },
            icon = Icons.Default.SportsEsports,
        )
    }

    @Composable
    fun Add(onClick: Do = { goTo("Challenge") }) {
        LazyIcon(
            onClick = onClick,
            icon = Icons.Default.Add,
        )
    }

    @Composable
    fun MoreMenu(onClick: Do = {}) {
        LazyIcon(
            onClick = onClick,
            icon = Icons.Default.MoreVert,
        )
    }


    @Composable
    fun Edit(
        onClick: Do = {
            if (Bar.funTime < Bar.Dpoints) show(NeedMorePoints)
            else show(Edit)
        },
    ) {
        LazyIcon(
            onClick = {
                onClick()
            },
            icon = Icons.Default.Edit,
        )
    }

    @Composable
    fun Delete(
        onClick: Do = {},
    ) {
        LazyIcon(
            onClick = { onClick() },
            icon = Icons.Default.Delete,
        )
    }

    //ICONS!!!!!!-------------------------///
}







//region POPUP CONTROLLER

var Edit = m(false)
var NeedMorePoints = m(false)
var AskUsagePermission = m(false)
var AppSelect = m(false)
var DebugPopup = m(false)

object Popup {
    
    @Composable
    fun Init(){
        AskUsagePermission(AskUsagePermission)
        EditPopUp(Edit)
        NeedMorePointsPopup(NeedMorePoints)
        AppSelectPopup(AppSelect)
        DebugPopup(DebugPopup)
    }
    
    

}


@Composable
fun NeedMorePointsPopup(show: m_<Bool>){
    LazyPopup(
        show = show, 
        title = "Get more points", 
        message = "Only need ${Bar.funTime}(points)-${Bar.Dpoints}(unlock)=${Bar.funTime- Bar.Dpoints}",
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

var DebugPopupInfo by m("")
@Composable
fun DebugPopup(show: m_<Bool>) {
    if (show.value) {
        LazyPopup(
            show = show,
            title = "ERROR",
            message = DebugPopupInfo,
            showCancel = no,
            showConfirm = no,
            content = {
                Column {
                    LazzyRow { UI.CopyIcon(DebugPopupInfo) }

                    Text(DebugPopupInfo)
                }
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

        var Loaded by r_m(no)

        LazyPopup(
            show = show,
            showCancel = no,
            showConfirm = no,
            title = "Select App",
            message = "",
            content = {
                Loaded = yes
                Vlog("Loaded: $Loaded")

                // Only pass the filtered items to the LazyList
                LazzyList(appList) { app, index ->
                    val icon = getAppIcon(getAppPackage(app))
                    LazzyRow(Modifier.clickOrHold{
                        selectedApp.value = getAppName(app)
                        show.value = false
                    }) {
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
