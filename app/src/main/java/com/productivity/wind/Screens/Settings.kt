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
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@Composable
fun SettingsScreen() {
    LazyScreen("Settings") {
        Item.UnlockThreshold()
        
        var restore = r_m(no)
        LazyItem(
            BigIcon = Icons.Filled.Restore,
            BigIconColor = DarkBlue,
            title = "Restore",
            onClick = { 
                restore.it = yes
            },
            bottomPadding = 2.dp
        )
	    Restore(restore)
		

        var backup = r_m(no)
        LazyItem(
            topPadding = 1.dp,
            BigIcon = Icons.Filled.Backup,
            BigIconColor = DarkBlue,
            title = "BackUp",
            onClick = { 
                backup.it = yes
            }
        )
        Backup(backup)


    

        LazyItem(
                BigIcon = Icons.Filled.Extension,
                BigIconColor = Color(0xFF9C27B0),
                title = "Extension",
                onClick = {
                    goTo("ExtensionsScreen")
                }
        ) 
        LazyItem(
                BigIcon = Icons.Filled.Extension,
                BigIconColor = Color(0xFF9C27B0),
                title = "Privacy",
                onClick = {
                    goTo("PrivacyScreen")
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

@Composable()
fun PrivacyScreen() = LazyScreen("Privacy") {
   LazyRuleCard("If") {
      var show = m(no)
      UI.CheckRow("Activate at ", show) {
         UI.Ctext("location") {
            locationPermission {
               

    
               Vlog("Got permissionsss")
            }
         }
      }
    val cameraPositionState = rememberCameraPositionState {
     position = CameraPosition.fromLatLngZoom(
      LatLng(52.5200, 13.4050), // Berlin
      12f
     )
    }

    GoogleMap(
     modifier = Mod.fillMaxSize(),
     cameraPositionState = cameraPositionState
    ) {
     Marker(
      state = MarkerState(LatLng(52.5200, 13.4050)),
      title = "Here",
      snippet = "Classic Google Maps"
     )
    }
   }
        
 
}


@Composable
fun ExtensionsScreen() {

    LazyScreen("Extensions") {

        
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
    var scrollV = r_Scroll()
    var scrollH = r_Scroll()
    var Tag = r_m("")

	RunOnce {
		Do {
			scrollV.toBottom()
			getMyAppLogs() 
		}
	}
    
    val txt = remember(Bar.logs, Tag.it) {
		Bar.logs.filter { it.contains(Tag.it) }
	}
	

    LazyScreen(top = {
        Header.Logs(Tag)
    }) {
		
        if (Bar.logs.isEmpty()){
              UI.EmptyBox("No logs")
        } else {
			
			Box(
				Mod.w(AppW - 10.dp).move(w = 5).h(AppH - 35.dp)
			) {
				LazyColumn(
				// Mod.Vscroll(scrollV).maxW().Hscroll(scrollH)
				) {
					items(txt) { line ->
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

	
}


class MyNotificationListener : NotificationListenerService()

