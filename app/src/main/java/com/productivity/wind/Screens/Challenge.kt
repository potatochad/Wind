package com.productivity.wind.Screens

import android.annotation.SuppressLint
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.*
import com.productivity.wind.Imports.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import androidx.compose.foundation.text.selection.*
import com.productivity.wind.Imports.Data.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*

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
                LazyInput(letterPoints, yes, modifier = Modifier
                        .h(34)
                        .space(h = 8, w = 4)
                        .w(60)
                        .background(CardColor, shape = RoundedCornerShape(4.dp))
                        .wrapContentHeight(Alignment.CenterVertically), 
                    )
                Text(" points")
            }
            LazzyRow {
                Text("Text retyped: ")
                LazyInput(completionPoints, yes, modifier = Modifier
                        .h(34)
                        .space(h = 8, w = 4)
                        .w(60)
                        .background(CardColor, shape = RoundedCornerShape(4.dp))
                        .wrapContentHeight(Alignment.CenterVertically), 
                    )
                Text(" points")
            }
        }

        // “Do” section - action/reward
        LazyRuleCard("Other") {
            LazzyRow {
                Text("Maximum retypes a day: ")
                LazyInput(Retypes, yes, modifier = Modifier
                        .h(34)
                        .space(h = 8, w = 4)
                        .w(60)
                        .background(CardColor, shape = RoundedCornerShape(4.dp))
                        .wrapContentHeight(Alignment.CenterVertically), 
                    )
            }
        }
    }
}




@Composable
fun AppUsage() {
    var Time = r_m("50")
    var Points = r_m("0")
    var WhichIf = r_m(0)
    selectedApp.it= ""

    LazyScreen(title = {
        Header.AppUsage(Time, Points, selectedApp)
       }
    ) {
      LazyRuleCard("If"){
        LazzyRow{
          //does nothing YET
          UI.CheckCircle(1, WhichIf)
          Text("Spend ")
          LazyInput(Time, yes, modifier = Modifier
                        .h(34)
                        .space(h = 8, w = 4)
                        .w(60)
                        .background(CardColor, shape = RoundedCornerShape(4.dp))
                        .wrapContentHeight(Alignment.CenterVertically), 
                    )
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
          LazyInput(Points, yes, modifier = Modifier
                        .h(34)
                        .space(h = 8, w = 4)
                        .w(60)
                        .background(CardColor, shape = RoundedCornerShape(4.dp))
                        .wrapContentHeight(Alignment.CenterVertically), 
                    )
          Text(" points")
        }
      }


    }

}



