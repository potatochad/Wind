package com.productivity.wind.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.*
import com.productivity.wind.*
import com.productivity.wind.Imports.*


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
        Text("If")
        Text(" spend ")
        UI.Cinput(Time)
        Text(" seconds ")

        Text("on ")
        UI.Ctext(if (selectedApp.value.isEmpty()) "app" else selectedApp.value) {
            show(Popup.AppSelect)
        }

        Text(", add ")
        UI.Cinput(Points)
        Text(" points")
        }


      
         
         Icon.Add(onClick = {
           edit(apps, 0) {
             Worth += 1
           }
         })





         UI.move(h = 20)
         Text("all apps")
      LazzyList(apps) { app ->
        LazyCard {
          LazzyRow {
            Text("${app.name}: ${app.Worth}")
          }
        }
      }


      
    }

}



