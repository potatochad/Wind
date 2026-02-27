package com.productivity.wind.Screens
 
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
import androidx.compose.ui.text.input.*

@Composable
fun SettingsScreen() {
    LazyScreen("Settings") {
        Item.UnlockThreshold()
        
        var restore = r_m(no)
        LazyItem(
            BigIcon = Icons.Filled.Restore,
            BigIconColor = DarkBlue,
            title = "Restore",
            onClick = { 
                restore.it = yes
            },
            bottomPadding = 2.dp
        )
	    Restore(restore)
		

        var backup = r_m(no)
        LazyItem(
            topPadding = 1.dp,
            BigIcon = Icons.Filled.Backup,
            BigIconColor = DarkBlue,
            title = "BackUp",
			endContent = {
				Ctext(
					if (Bar.encryptedBackup) "encrypt".darkGray()
					else "txt".darkGray()
				) {
					Bar.encryptedBackup = !Bar.encryptedBackup
				}
			},
            onClick = { 
                backup.it = yes
            },
        )
        Backup(backup)


    

        LazyItem(
                BigIcon = Icons.Filled.Extension,
                BigIconColor = Color(0xFF9C27B0),
                title = "Extension",
                onClick = {
                    goTo("ExtensionsScreen")
                }
        ) 
		

		
        LazyItem(
                BigIcon = Icons.Filled.VisibilityOff,
                BigIconColor = Color(0xFF03A9F4),
                title = "Privacy",
                onClick = {
                    goTo("PrivacyScreen")
                }
        ) 
        LazyItem(
                BigIcon = Icons.Filled.Tune,
                BigIconColor = Color(0xFFB0BEC5),
                title = "Other",
                onClick = { goTo("SettingsOtherScreen") }
        ) 
	
    }
}

@Composable()
fun PrivacyScreen() = LazyScreen("Privacy") {
   RuleCard("If") {
      var check = m(Bar.privacyLocation)
	  var show = m(no)
	  

	  selectLocation(show)
	  
      CheckRow("Activate at ", check) {
         Ctext("${Bar.privacyGeo.size} locations") {
			check.it = yes
			location {
				show.it = yes
				Bar.privacyLocation = yes
		    }
		 }
      } 



	  
   }
}


@Composable
fun ExtensionsScreen() = LazyScreen("Extensions") {
	KwikWebview(
		url = "https://app.domain.com",
		webViewSettings = {
			cookies = mapOf(
				"cookieName" to "cookieValue",
				"anotherCookie" to "anotherValue"
			),
			userAgent = "Running on Kwik Android WebView"
		}
	)
}

@Composable
fun SettingsOtherScreen() = LazyScreen("Settings") {
        LazyItem(
            BigIcon = Icons.Filled.ListAlt,
            BigIconColor = Color(0xFF90A4AE),
            title = "Logs",
            onClick = { goTo("LogsScreen") }
        )
		
}


@Composable
fun LogsScreen() {
    var Reload = r_m(no)
    var Tag = r_m("")
	var scroll = LazyList()

	val Logs by remember(Tag.it, Bar.logs) {
		derivedStateOf {
			Bar.logs.filter { it.contains(Tag.it) }
		}
	}


	RunOnce {
		scroll.toBottom()
	}

    LazyScreen(
		top = {
			TinyInput(Tag, Mod.h(36).w(AppW - 180.dp), isInt = no, maxLetters = 100)

			val Logs by remember(Tag.it, Bar.logs) {
				derivedStateOf {
					Bar.logs.filter { it.contains(Tag.it) }
				}
			}
		
			End {
				Icon.Delete {
					Bar.logs.clear()
				}
				Icon.Copy(Logs.joinToString("\n"))
			}
		},
		scroll = no,
	) {
        if (Bar.logs.isEmpty()){
              EmptyBox("No logs")
        } else {
			Box(
				Mod.w(AppW - 10.dp).move(w = 5).h(AppH - 35.dp).Hscroll()
			) {
				LazyColumn(
					state = scroll
				) {
					items(
						Logs
					) { line ->
						Text(
							text = line,
							fontSize = 14.sp,
							softWrap = no
						)
					}
				}
			}
        }
    }

	
}


class MyNotificationListener : NotificationListenerService()

