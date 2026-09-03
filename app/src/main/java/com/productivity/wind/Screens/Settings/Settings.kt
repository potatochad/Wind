package com.productivity.wind.Screens.Settings

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.Renames.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
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
import com.productivity.wind.Imports.Utils.Log.logTimer

@Composable
fun SettingsScreen() {
    LazyScreen("Settings") {
        AppItem.UnlockThreshold()
		LazyItem(
			icon = { BigIcon.Lock(gold) },
			title = "Block Uninstall",
			onClick = {
				if (!Permission.deviceAdmin{
					Bar.noUninstall = yes
				}){
					Bar.noUninstall = no
					Vlog("Need Permission")
				}
			},
			endUI = {
				Text(if (Bar.noUninstall) "ON".gold() else "OFF".darkGray())
			},
			topPadding = 1.dp,
		)

		/*
		‼️‼️SAVE AND RESTORE DONT WORKKKK
        var restore = r(no)
        LazyItem(
            icon = { BigIcon.Restore(darkBlue) },
            title = "Restore",
            onClick = { 
                restore.it = yes
            },
            bottomPadding = 2.dp
        )
	    Restore(restore)
		

        var backup = r(no)
        LazyItem(
            topPadding = 1.dp,
            icon = { BigIcon.Backup(darkBlue) },
            title = "Backup",
			endUI = {
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
		*/


    

        LazyItem(
                icon = { BigIcon.Extension(0xFF9C27B0) },
                title = "Extension",
                onClick = {
                    goTo("ExtensionsScreen")
                }
        ) 
		LazyItem(
            icon = { BigIcon.Lock(gold) },
            title = "Grayscale",
            onClick = {
                goTo("GrayScaleScreen")
            }
        ) 
		

		
        LazyItem(
                icon = { BigIcon.VisibilityOff(0xFF03A9F4) },
                title = "Privacy",
                onClick = {
                    goTo("PrivacyScreen")
                }
        ) 
        LazyItem(
                icon = { BigIcon.Tune(0xFFB0BEC5) },
                title = "Other",
                onClick = { goTo("SettingsOtherScreen") }
        ) 
	
    }
}

@Composable()
fun PrivacyScreen() = LazyScreen("Privacy") {
   RuleCard("If") {
      var check = mState(Bar.privacyLocation)
	  var show = mState(no)
	  

	  PickLocation(show)
	  
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
fun ExtensionsScreen() = LazyScreen("Test Data") {

    LazzyColumn {

        LazyColumn {
            items(
                items = Bar.items.it,
                key = { it.id }
            ) { item ->
				/*

                LazzyColumn {
                    Text("Item ${item.id}")

                    LazzyRow {
                        Text("name: ${item.name}")

                        Btn("Change") {
                            item.name = "changed ${TimeMillis()}"
                        }
                    }

                    LazzyRow {
                        Text("intVar: ${item.intVar}")

                        Btn("Change") {
                            item.intVar += 1
                        }
                    }

                    LazzyRow {
                        Text("boolVar: ${item.boolVar}")

                        Btn("Change") {
                            item.boolVar = !item.boolVar
                        }
                    }

                    LazzyRow {
                        Text("doubleVar: ${item.doubleVar}")

                        Btn("Change") {
                            item.doubleVar += 1.1
                        }
                    }

                    LazzyRow {
                        Text("floatVar: ${item.floatVar}")

                        Btn("Change") {
                            item.floatVar += 1f
                        }
                    }

                    LazzyRow {
                        Text("longVar: ${item.longVar}")

                        Btn("Change") {
                            item.longVar += 1L
                        }
                    }

                    LazzyRow {
                        Text("nullVar: ${item.nullVar}")

                        Btn("Change") {
                            item.nullVar = null
                        }
                    }
                }

				*/
            }
        }

        LazzyRow {
            Btn("Add") {
                Bar.items += TestData()
            }

            Btn("Add 10") {
                repeat(10) {
                    Bar.items += TestData()
                }
            }
        }
		
    }
}



@Composable
fun GrayScaleScreen() = LazyScreen("GrayScale") {
	EmptyBox(
		text = "GrayScale",
		icon = Icons.Default.Palette,
		color = gold,
	)
	
	LazySlider(
		min = 0f,
		max = 1f,
		linear = yes,
		value = mState(0f),
	){
		DeviceGray(it)
	}

	
}
        

@Composable
fun SettingsOtherScreen() = LazyScreen("Settings") {
        LazyItem(
            icon = { BigIcon.ListAlt(0xFF90A4AE) },
            title = "Logs",
            onClick = { goTo("LogsScreen") }
        )
		LazyItem(
            icon = { Icon.Whatshot() },
            title = "Burn 5 points",
            onClick = {
				Bar.funTime -= 5
				Bar.funTime.vlog("Points left")
			}
        )
		LazyItem(
            icon = { Icon.Whatshot() },
            title = "Delete logs",
            onClick = {
				Vlog("Logs are cleared!")
				Bar.logs.clear()
			}
        )
		
		var showDeleteSure = r(no)
		IsSure(showDeleteSure) {
            AppData.deleteAll()
			CloseMyApp()
		}
		LazyItem(
            icon = { Icon.Whatshot() },
            title = "‼️ DELETE ALL DATA",
            onClick = {
				showDeleteSure.it = yes
			}
        )
}



