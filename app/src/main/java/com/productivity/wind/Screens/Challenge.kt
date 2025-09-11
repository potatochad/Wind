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
            BigIcon = Icons.Filled.AccessTime,
            BigIconColor = DarkBlue,
            title = "App Usage",
            onClick = { 
              goTo("AppUsage")
            },
        )
    LazyItem(
            BigIcon = Icons.Filled.AccessTime,
            BigIconColor = DarkBlue,
            title = "Copy Paste",
            onClick = { 
              goTo("CopyPaste")
            },
        )
    
   }
}


@Composable
fun CopyPaste() {
    var Time = r { m("50") }
    var Points = r { m("0") }
    

    LazyScreen(title = {
            Text("missing")
          }
      ) {
          LazyCard(""){
             Text("Points per letter")
             Text("Points on completion")
          }

    }

}




@Composable
fun AppUsage() {
    var Time = r { m("50") }
    var Points = r { m("0") }
    var WhichIf = r { m(0) }
    selectedApp.value = ""
    LaunchedEffect(Unit) {
        refreshApps()  
    }

    LazyScreen(title = {
        Header.AppUsage(Time, Points, selectedApp)
       }
    ) {
      LazyRuleCard("If"){
        LazzyRow{
          //does nothing YET
          UI.CheckCircle(1, WhichIf)
          Text("Spend ")
          UI.Cinput(Time)
          Text(" seconds")
          
          Text(" on ")
          UI.Ctext(if (selectedApp.value.isEmpty()) "app" else selectedApp.value) {
            show(Popup.AppSelect)
          }
        }
      }
      LazyRuleCard("Do"){
        LazzyRow{
          Text("Add ")
          UI.Cinput(Points)
          Text(" points")
        }
      }


    }

}



