package com.productivity.wind.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.*
import com.productivity.wind.*
import com.productivity.wind.Imports.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*


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
            BigIcon = Icons.Filled.ContentPaste,
            BigIconColor = Color(0xFF1E88E5), 
            title = "Copy Paste",
            onClick = { 
              goTo("CopyPaste")
            },
        )
    
   }
}


@Composable
fun CopyPaste() {
    // State variables
    var completionPoints = r_m("10")  // “If” completion points
    var letterPoints = r_m("2")     // “If” letter points
    var Retypes = r_m("0")         // “Do” limit points

    LazyScreen(title = { 
        Header.CopyPaste() 
    }) {
        // “If” section
        LazyRuleCard("If") {
            LazzyRow {
                Text("Letter completed: ")
                UI.Cinput(letterPoints)
                Text(" points")
            }
            LazzyRow {
                Text("Text retyped: ")
                UI.Cinput(completionPoints)
                Text(" points")
            }
        }

        // “Do” section - action/reward
        LazyRuleCard("Other") {
            LazzyRow {
                Text("Maximum retypes a day: ")
                UI.Cinput(Retypes)
            }
        }
    }
}




@Composable
fun AppUsage() {
    var Time = r_m("50")
    var Points = r_m("0")
    var WhichIf = r_m(0)
    set(selectedApp, "")
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
            show(AppSelect)
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



