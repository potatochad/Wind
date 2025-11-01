package com.productivity.wind.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.*
import com.productivity.wind.Imports.*
import androidx.compose.ui.graphics.*
import com.productivity.wind.Imports.Data.*

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
                LazyInput(letterPoints, yes, Modifier.w(40, 120))
                Text(" points")
            }
            LazzyRow {
                Text("Text retyped: ")
                LazyInput(completionPoints, yes, Modifier.w(40, 120))
                Text(" points")
            }
        }

        // “Do” section - action/reward
        LazyRuleCard("Other") {
            LazzyRow {
                Text("Maximum retypes a day: ")
                LazyInput(Retypes, yes, Modifier.w(40, 120))
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
    
    RunOnce {
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
          LazyInput(Time, yes, Modifier.w(40, 120))
          Text(" seconds")
          
          Text(" on ")
          UI.Ctext(if (selectedApp.it.isEmpty()) "app" else selectedApp.it) {
            show(AppSelect)
          }
        }
      }
      LazyRuleCard("Do"){
        LazzyRow{
          Text("Add ")
          LazyInput(Points, yes, Modifier.w(40, 120))
          Text(" points")
        }
      }


    }

}



