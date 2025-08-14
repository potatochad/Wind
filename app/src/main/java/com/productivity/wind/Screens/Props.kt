package com.productivity.wind.Screens

import androidx.compose.foundation.border
import android.app.AlertDialog
import android.app.AppOpsManager
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.compose.material3.Divider
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.productivity.wind.Global1.context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.provider.Settings
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.os.Process
import android.service.notification.NotificationListenerService
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import java.time.LocalDate
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.max
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.compose.NavHost
import com.productivity.wind.Bar
import com.productivity.wind.Global1
import com.productivity.wind.LazyPopup
import com.productivity.wind.NoLagCompose
import com.productivity.wind.PermissionsButton
import com.productivity.wind.R
import com.productivity.wind.SettingItem
import com.productivity.wind.apps
import com.productivity.wind.log
import com.productivity.wind.SettingsScreen
import com.productivity.wind.SettingsSaved
import com.productivity.wind.UI
//import com.productivity.wind.Popup
import com.productivity.wind.*
import com.productivity.wind.Screens.Challenge
import com.productivity.wind.Achievements
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.compose.ui.window.Popup


fun NavGraphBuilder.ScreenNav() {
    //Main—StartDestination    
    url("Main") { Main() }

    
    url("Achievements") { Achievements() }
    url("Challenge") { Challenge() }

    
    url("Web") { Web() }
    url("FunWeb") { FunWeb() }
    url("WorkWeb") { WorkWeb() }

    
    url("SettingsScreen") { SettingsScreen() }
    url("SettingsOtherScreen") { SettingsOtherScreen() }
    url("LogsScreen") { LogsScreen() }
}









//TOP BAR
@Composable
fun MainHeader(){
        Icon.Menu()
        Icon.Chill()
        
        UI.move(w = 12)
        
        Text(text = "Points ${Bar.funTime}", fontSize = 18.sp)
        
        
        UI.End { 
                Icon.Add()
        }

}



@Composable
fun Menu() {
    SettingsScreen(
        titleContent = { UI.MenuHeader() },
        showBack = false,
        showDivider = false,
        MheaderHeight = 700,
    ) {
        SettingItem(
            icon = Icons.Outlined.Chat,
            title = "Contact Support",
            onClick = { UI.SendEmail(); Bar.ShowMenu = false }
        )
        SettingItem(
            icon = Icons.Outlined.Landscape,
            title = "Settings",
            onClick = { Global1.navController.navigate("SettingsScreen"); Bar.ShowMenu = false  }
        )
        SettingItem(
            icon = Icons.Outlined.QueryStats,
            title = "Achievements",
            onClick = { Global1.navController.navigate("Achievements"); Bar.ShowMenu = false }
        )


        
    }

    
}




//Gets called on a new day


object Icon {
        @Composable
        fun Menu() {
                UI.SimpleIconButton(
                        onClick = { Bar.ShowMenu = true },
                        icon = Icons.Default.Menu
                )
        }
        
        @Composable
        fun Chill() {
                UI.SimpleIconButton(
                        onClick = { Global1.navController.navigate("Web") },
                        icon = Icons.Default.SportsEsports
                )
        }
        
        @Composable
        fun Add() {
                UI.SimpleIconButton(
                        onClick = { Global1.navController.navigate("Challenge") },
                        icon = Icons.Default.Add
                )
        }
        
        @Composable
        fun Edit() {
                UI.SimpleIconButton(
                        onClick = { 
                                if (Bar.funTime > Bar.Dpoints) Popup.Edit.value =true
                                else Popup.NeedMorePoints.value = true
                        },
                        icon = Icons.Default.Edit
                )
        }
        
        @Composable
        fun G_Edit() {
                UI.SimpleIconButton(
                        onClick = { 
                                if (Bar.funTime > Bar.Dpoints) Popup.G_Edit.value =true
                                else Popup.NeedMorePoints.value = true
                        },
                        icon = Icons.Default.Edit
                )
        }

 //ICONS!!!!!!-------------------------///
}







//region POPUP CONTROLLER

object Popup {
    var Edit = m(false)
    var G_Edit = m(false)
    var NeedMorePoints = m(false)
    var EnablePermissions = m(false)
    var EnableBlocking = m(false)

    var AppChallange = m(false)
}
//!Just call this on app start
@Composable
fun PopUps(){
   G_EditPopUp(Popup.G_Edit)
   EnablePermissionsPopup(Popup.EnablePermissions)
   EditPopUp(Popup.Edit)
   NeedMorePointsPopup(Popup.NeedMorePoints)
   EnableBlockingPopup(Popup.EnableBlocking)
   AppChallange(Popup.AppChallange)
}


// All other things
@Composable
fun NeedMorePointsPopup(show: MutableState<Boolean>){
    LazyPopup(
        show = Popup.NeedMorePoints, 
        title = "Not EnoughPoints", 
        message = "Need have ${Bar.Dpoints} points to do this. Only have ${Bar.funTime}"
    )
}

@Composable
fun EnableBlockingPopup(show: MutableState<Boolean>){
    LazyPopup(
            show = show,
            title = "Enable?",
            message = "If you enable blocking, an overlay screen will appear over the selected apps when you run out of points. (1 point = 1 second)\n\nTo disable blocking, you’ll need more than the selected points in the unlock Threashold",
            showCancel = true,
            showConfirm = true,
            onConfirm = {
                Bar.BlockingEnabled = true
            },
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
fun G_EditPopUp(show: MutableState<Boolean>) {
    var TemporaryTargetText by remember { mutableStateOf("") }
    TemporaryTargetText = Bar.G_targetText
    LazyPopup(
        show = show,
        onDismiss = { TemporaryTargetText = Bar.G_targetText },
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
        onConfirm = { Bar.G_targetText = TemporaryTargetText; Bar.G_FirstEditText = false },
        onCancel = { TemporaryTargetText = Bar.G_targetText }
    )
}

@Composable
fun EnablePermissionsPopup(show: MutableState<Boolean>) {
    LazyPopup(show = show,
              title = "Need Permissions", 
              message = "Please enable all permissions first.", 
              onConfirm = { Global1.navController.navigate("SettingsP_Screen")}
              )
}

//endregion POPUP CONTROLLER



//region ADD CHALLANGE POPUPS

@Composable
fun AppChallange(show: MutableState<Boolean>) {
    var Time = remember { m("50") }
    var Points = remember { m("0") }

    

    UI.ResetText(show){
        Time.value = "50"
        Points.value = "0"
    }


    fun DONE(){

        show.value = false
    }
    @Composable
    fun PopupContent() {
        
            refreshApps()

            UI.TextRow(){
                
                Text("If")
                Text("spend")
                UI.Cinput(Time){ 
                    Time.value = it 
                }
                Text("seconds")

                Text("on")
                UI.Ctext("0 apps"){
                    
                }
                Text("add")
                UI.Cinput(Points)
                UI.Ctext("points"){
                    
                }
                
                


                
            }

        
    }

    LazyPopup(show = show,
              title = "App usage", 
              content = { PopupContent() }, 
              onConfirm = {  },
              message="",
              onDismiss={},
              )

    
}

