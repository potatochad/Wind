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
import androidx.navigation.NavGraphBuilder
import com.productivity.wind.Imports.*
import androidx.compose.ui.platform.*
import kotlinx.coroutines.*
import android.webkit.*
import com.productivity.wind.Imports.Data.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import android.content.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.focus.*
import android.graphics.drawable.*
import android.content.pm.*
import androidx.compose.foundation.lazy.*

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
    url("SettingsOtherScreen") { SettingsOtherScreen() }
    url("LogsScreen") { LogsScreen() }
  
    
}




@Composable
fun Menu() {
    LazyScreen(
        title ={ UI.MenuHeader()},
        showBack = no,
        showDivider = no,
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
    fun TskInput(txt: Any, maxLetters: Int = 5) {  
        val TxtState = txt as? MutableState<Any> ?: run {
            Vlog("expected mutable")
            return
        }

        BasicInput(
            "${TxtState.it}",
            isInt = yes, 
        ) {
            val input = it.take(maxLetters)
            
            TxtState.it = if (TxtState.it is Int) {
                 input.toInt()
            } else {
                input
            }
        }
    }
    
    @Composable
    fun Logs(txt: Str, scrollV: LazyListState, scrollH: ScrollState) {
        Box(
            Modifier
                .w(App.screenWidth - 10.dp)
                .move(w = 5)
                .h(App.screenHeight - 35.dp)
        ) {
            Box(
                Modifier.Hscroll(scrollH)
            ) {
                LazyColumn(
                    state = scrollV,
                    modifier = Modifier.maxW()
                ) {
                    items(txt.lineSequence().toList()) { line ->
                        Text(
                            text = line,
                            fontSize = 14.sp,
                            softWrap = no
                        )
                    }
                }
            }
        }
    }


    fun enoughPoints(enough: Do) {
        if (Bar.funTime < Bar.Dpoints) {
            show(NeedMorePoints)
        }
        else enough()
    }

    @Composable
    fun AppTaskUI(app: AppTsk){
        val icon = getAppIcon(app.pkg)
        var name = app.name
        val progress = (app.NowTime.toFloat() / app.DoneTime.toFloat()).coerceIn(0f, 1f)

        LazyCard {
            LazzyRow {
                move(10)

                click({
                    UI.ProgressIcon(icon, progress)
                }){
                    Plog("$name app progress is ${progress*100}%; ${app.Worth/app.NowTime}points/s ")
                }


                move(12)
                Text("Points ${app.Worth}")


                UI.End {
                    Icon.Edit{
                        Bar.apps.edit(app){
                            Worth +=1
                        }
                    }
                    Icon.Delete{
                        Bar.apps.remove(app)
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
                BasicInput(
                    "${Bar.Dpoints}", 
                    isInt = yes, 
                ) {
                    val input = it.take(5).toIntOrNull() ?: 0
                    if (Bar.funTime>Bar.Dpoints) {
                        if (input<Bar.funTime) {
                            Bar.Dpoints = input
                        } else {
                            Vlog("More points: ${Bar.funTime} < $input ")
                        }
                    } else {
                        Vlog("More points: ${Bar.funTime} < ${Bar.Dpoints} ")
                    }
                }
            }
        )
    }
    @Composable
    fun Restore() {
        var restoring by r_m(no)
        LazyItem(
            BigIcon = Icons.Filled.Restore,
            BigIconColor = DarkBlue,
            title = "Restore [not work]",
            onClick = { 
                restoring = yes
            },
            bottomPadding = 2.dp
        )
        if (restoring) {
            BrestoreFromFile()
            restoring = no
        }
    }

    @Composable
    fun Backup() {
        var backup by r_m(no)
        
        LazyItem(
            topPadding = 1.dp,
            BigIcon = Icons.Filled.Backup,
            BigIconColor = DarkBlue,
            title = "BackUp [not work]",
            onClick = { 
                backup = yes
            }
        )
        if (backup) {
            BsaveToFile()
            backup = no
        }   
    }

    @Composable
    fun WebPointTimer(on: m_<Bool>) {
        RunOnce(on.it) {
            while (on.it) {
                if (Bar.Dpoints > 0) {
                    if (Bar.funTime < 1) {
                        goTo("main")
                        show(NeedMorePoints)
                        on.it = no
                    } else {
                        Bar.funTime -= 1
                    }
                }
                delay(1000L) 
            }
        }
    }

    
}

object Header {

    @Composable
    fun Logs(LogsTag: m_<Str>, filteredLogs: Str) {
            Row(
                Modifier.scroll(h=yes),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyInput(
                    LogsTag,
                    modifier = Modifier.h(34).w(120)
                )
            }
            
            UI.End {
                Row {
                    Icon.Delete {
                        Bar.logs = ""
                    }
                    Icon.Copy(filteredLogs)
                    Icon.Reload { 
                        Vlog("refreshed")
                    }
                }
            }
    }
    
    @Composable
    fun AppUsage(Time: m_<Str>, Points: m_<Str>, selectedApp: m_<Str>) {
        Text("AppUsage")
        
        UI.End {
            Icon.Add {
                UI.check(!isUsageP_Enabled()) { show(AskUsagePermission); return@Add}
                UI.check(Time.it.toInt() < 1,"Add time") {return@Add}
                UI.check(selectedApp.it.isEmpty(),"Select app") {return@Add}

                Bar.apps.add {
                    name = selectedApp.it
                    DoneTime = Time.it.toIntOrNull() ?: 0
                    Worth = Points.it.toIntOrNull() ?: 0
                }
                

                selectedApp.it= ""
                Points.it= "0"
                Time.it= "0"

                goTo("Main")
            }
        }
    }
    
    @Composable
    fun CopyPaste(text: m_<Str>, dailyMax: m_<Int>, doneWorth: m_<Int>, letterWorth: m_<Int>){
        Text("Copy Paste")
        
        UI.End {
                Icon.Add {
                    UI.check(text.it.isEmpty(),"Add text") {return@Add}
                    
                    Bar.copyTsk.add {
                        txt = text.it
                        DailyMax = dailyMax.it
                        Done_Worth = doneWorth.it
                        Letter_Worth = letterWorth.it
                    }                  
                }
        }
    }


    @Composable
    fun Main(){
        Icon.Menu()
        Icon.Chill()
        
        move(w = 12)
        
        Text("Points ${Bar.funTime}")
        
        UI.End {
                Icon.Add()
        }
    }

    
}


object Icon {
    @Composable
    fun Menu() {
        LazyIcon(Icons.Default.Menu) {
            App.Menu = yes
        }
    }

    @Composable
    fun Reload(Do: Do) {
        LazyIcon(Icons.Default.Refresh) { 
            Do() 
        }
    }

    @Composable
    fun Chill() {
        LazyIcon(Icons.Default.SportsEsports) {
            Item.enoughPoints{
                    goTo("Web")
            }
        }
    }

    @Composable
    fun Add(Do: Do = { goTo("Challenge") }) {
        LazyIcon(Icons.Default.Add) {
            Do()
        }
    }

    @Composable
    fun MoreMenu(Do: Do = {}) {
        LazyIcon(Icons.Default.MoreVert) {
            Do()
        }
    }


    @Composable
    fun Edit(
        Do: Do = {
            Item.enoughPoints{
                show(Edit)
            }
        },
    ) {
        LazyIcon(Icons.Default.Edit) {
            Do()
        }
    }

    @Composable
    fun Delete(Do: Do = {}) {
        LazyIcon(Icons.Default.Delete){
            Do()
        }
    }


    @Composable
    fun Copy(txt: Str) {
        val ctx = LocalContext.current
        var copied by r_m(no)

        RunOnce(copied) {
            if (copied) {
                delay(1000)
                copied = no
            }
        }

        LazyIcon(if (copied) Icons.Default.Check else Icons.Default.ContentCopy){
            UI.copyToClipboard(ctx, txt)
            copied = yes
        }
    }


    //ICONS!!!!!!-------------------------///
}







//region POPUP CONTROLLER

var Edit = m(no)
var NeedMorePoints = m(no)
var AskUsagePermission = m(no)
var AppSelect = m(no)
var DebugPopup = m(no)

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
        showCancel = no,
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
                    .maxW()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        showCancel = yes,
        onConfirm = { Bar.targetText = TemporaryTargetText; Bar.FirstEditText = false },
        onCancel = { TemporaryTargetText = Bar.targetText }
    )
}


@Composable
fun AskUsagePermission(show: m_<Bool>) {
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

var DebugPopupInfo by m("")
@Composable
fun DebugPopup(show: m_<Bool>) {
        LazyPopup(
            show = show,
            title = "ERROR",
            message = DebugPopupInfo,
            showCancel = no,
            showConfirm = no,
            content = {
                Column {
                    LazzyRow { Icon.Copy(DebugPopupInfo) }

                    Text(DebugPopupInfo)
                }
            }
            
        )
}










var selectedApp = m("")

@Composable
fun AppSelectPopup(show: m_<Bool>) {
    var appList by r_m<List<Pair<ResolveInfo, Drawable?>>>(emptyList())
    var loading by r_m(no)

    RunOnce {
        runHeavyTask(
            task = {
                getApps()
                    .filter { getAppPackage(it) != App.pkg }
                    .map { app ->
                        val icon = getAppIcon(getAppPackage(app))
                        app to icon
                    }
            },
            onResult = {
                appList = it
                loading = no
            }
        )
    }

    LazyPopup(
        show = show,
        showCancel = no,
        showConfirm = no,
        title = "Select App",
        message = "",
        content = {
            if (loading) {
                Text("Loading...")
            } else {
                LazzyList(appList) { (app, icon), _ ->
                    LazzyRow(
                        Modifier.click {
                            selectedApp.it = getAppName(app)
                            show.it = no
                        }
                    ) {
                        move(10)
                        LazyImage(icon)
                        move(10)
                        UI.Ctext(getAppName(app)) {
                            selectedApp.it = getAppName(app)
                            show.it = no
                        }
                    }
                }
            }
        }
    )
}
