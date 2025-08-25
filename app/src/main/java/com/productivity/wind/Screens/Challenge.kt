package com.productivity.wind.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.*
import com.productivity.wind.*
import com.productivity.wind.Imports.*
import androidx.compose.ui.unit.*


@Composable
fun Challenge() {
  LazyScreen(title = { Text("Challenge") }) {
    LazyItem(
            BigIcon = Icons.Filled.Backup,
            BigIconColor = DarkBlue,
            title = "App Usage",
            onClick = { 
              if (!UI.isUsageP_Enabled()) {
                show(Popup.AskUsagePermission)
              } else { goTo("AppUsage") }
            },
        )
    
   }
}



@Composable
fun AppUsage() {
    var Time = remember { m("50") }
    var Points = remember { m("0") }
    selectedApp.value = ""
    LaunchedEffect(Unit) {
        refreshApps()
    }

    LazyScreen(title = {
        Header.AppUsage(Time, Points, selectedApp)
       }
    ) {
      LazzyRow {
        UI.move(w = 10.dp)
        Text("If")
        Text(" spend ")
        UI.Cinput(Time)
        Text(" seconds ")

        Text("on ")
        UI.Ctext(if (selectedApp.value.isEmpty()) "app" else selectedApp.value) {
            show(Popup.AppSelect)
        }
      }

        LazzyRow{
          Text(", add ")
          UI.Cinput(Points)
          Text(" points")
        }


    }

}



