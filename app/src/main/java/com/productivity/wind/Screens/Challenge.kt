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
    var CompletionPoints = r { m("10") }
    var Letter_points = r { m("1") }
    var Letter_points = r { m("") }
    

    LazyScreen(title = {
            Header.CopyPaste()
          }
      ) {
          LazyRuleCard("Worth"){
             Text("Letter: ${Letter_points} points")
             Text("Completion: ${CompletionPoints} points")
          }
          LazyRuleCard("Limits"){
             Text("Completions in a day: ${Letter_points}")
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



