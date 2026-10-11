package com.productivity.wind.Screens.Settings.LogScreen

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.String.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.productivity.wind.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.lazy.*
import com.productivity.wind.Imports.UI_visible.*


@Composable
fun LogsScreen() {
	OnLogsScreen() 
	
    var Reload = r(no)
    var Tag = r("")
	var scroll = LazyList()

	
	val Logs = remember(Tag.it, Bar.logs.size) {
		Bar.logs
			.filter { it.contains(Tag.it) }
			.toList()
	}

	var gotStyle by r(no)
	var strStyle by r(StrStyle())
	var maxW = Logs.rMaxWidth(strStyle) + 15.dp
	

	RunOnce {
		scroll.toBottom()
	}

    LazyScreen(
		top = {
			TinyInput(Tag, Mod.h(36).w(AppW - 180.dp), isInt = no, maxLetters = 100)
		
			End {
				Icon.Delete {
					Bar.logs.clear()
				}
				Icon.Copy(Logs.joinToString("\n"))
			}
		},
		scroll = no,
	) {
        if (Bar.logs.empty){
              EmptyBox("No logs")
        } else {
			Box(
				Mod.w(AppW - 10.dp).move(w = 5).h(AppH - 35.dp).Xscroll()
			) {
				LazyColumn(
					state = scroll,
					modifier = Mod.w(maxW)   
				) {
					items(Logs) { 
						LogTxt(it){ style ->
						   if (gotStyle == no) {
							   gotStyle = yes
							   Vlog("gotStyle: $gotStyle")
							   strStyle = style
						   }
					   } 
					}
				}
			}
        }
    }
}
