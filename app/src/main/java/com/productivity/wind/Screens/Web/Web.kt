package com.productivity.wind.Screens.Web

import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.String.*
import androidx.compose.foundation.layout.*
import com.productivity.wind.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.unit.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.compose.ui.platform.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.productivity.wind.Screens.*
import com.productivity.wind.Imports.Utils.Browser.*



@Composable
fun QuickItem(
    title: Str,
    url: Str,
    icon: icon,
) {
    LazyCard(
        modCard = Mod
		    .space(5,0)
            .click {
                Bar.Url = url
                goTo("Web")
            },
		modUI = Mod.space(
			start = 12,
			end = 12,
			bottom = 4,
			top = 8,
		),
    ) {
        LazzyColumn(Mod.centerX) {
            Icon(icon = icon, size = 20)

            move(8)

            Text(title.size(11.sp))
        }
    }
}



@Composable
fun WebHome(){
	var ctx = LocalContext.current
    val web = r { WebController(ctx) }
	
    LazyScreen(
        top = {
            End {
                AppItem.Add {
                    goTo("WebKeywords")
                }
            }
        },
        scroll = no,
		onBackIcon = { goTo("Main") }
    ) {
		
        LazzyRow(Mod.centerX.space(5)) {
			Text("Home".size(28.sp))
		}
		WebProps.Home.Input(web)

        move(h = 16)

        // Quick access cards (like Google shortcuts)
		Text("Recent".size(12.sp).gray(), modifier = Mod.space(start = 8))
		
		
		
		LazzyRow(Mod.centerX.space(5).Xscroll()) {
			QuickItem("Youtube", "https://www.youtube.com/@melrobbins", Icons.Default.PlayArrow)
            QuickItem("My First Million", "www.mfmpod.com", Icons.Default.PlayArrow)
            QuickItem("Mel Robbins", "https://www.melrobbins.com/podcast/", Icons.Default.PlayArrow)
        }



		
    }
}


@Composable
fun Web(){
    //returns class: WebController
    var ctx = LocalContext.current
    val web = r { WebController(ctx) }

	RunOnce {
		web.url(Bar.Url)
	}
    
    WebProps.WebPointTimer()

	BlockingLogic(web)

    LazyScreen(
        top = {
			WebProps.TopBarInput(web){
				if (it.empty) goTo("WebHome")
			}
            End {
                Row{
                    Icon.Reload{ 
                        web.reload()
                    } 
                    AppItem.Add {
                        goTo("WebKeywords")
                    }
                }
            }
        },
        scroll = no,
        dividerP = no,
		onBackIcon = { goTo("Main") }
    ) {
        WebXml(web)
    }
}








