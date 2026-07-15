package com.productivity.wind.Screens.Settings.LogScreen

import com.productivity.wind.Screens.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.String.*
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
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.*
import androidx.compose.foundation.lazy.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.productivity.wind.Imports.UI_visible.*
import android.os.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator      
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.platform.LocalLayoutDirection
import android.provider.Settings


@Composable
fun LogsScreen() {
    var Reload = r(no)
    var Tag = r("")
	var scroll = LazyList()

	
	val Logs = remember(Tag.it, Bar.logs.size) {
		Bar.logs
			.filter { it.contains(Tag.it) }
			.toList()
	}

	var strStyle by m(StrStyle())
	var maxW = remember(strStyle) { 
		Logs.rMaxWidth(strStyle) + 15.dp
	}
	

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
						   strStyle = style
					   } 
					}
				}
			}
        }
    }
}
