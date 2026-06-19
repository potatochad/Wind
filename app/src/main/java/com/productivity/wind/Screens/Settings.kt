package com.productivity.wind.Screens

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


    

        LazyItem(
                icon = { BigIcon.Extension(0xFF9C27B0) },
                title = "Extension",
                onClick = {
                    goTo("ExtensionsScreen")
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
      var check = m(Bar.privacyLocation)
	  var show = m(no)
	  

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
fun ExtensionsScreen() = LazyScreen("Extensions") {

    val list = r {
        CustomList(
            items = listOf("A", "B"),

            add = { item ->
                log("ADD: $item")
                true
            },

            addAt = { index, item ->
                log("ADD AT: $index -> $item")
            },

            addAll = { items ->
                log("ADD ALL: $items")
                true
            },

            addAllAt = { index, items ->
                log("ADD ALL AT: $index -> $items")
                true
            },

            clear = {
                log("CLEAR")
            },

            get = { index ->
                log("GET: $index")
                "fake-$index"
            },

            remove = { item ->
                log("REMOVE: $item")
                true
            },

            removeAt = { index ->
                log("REMOVE AT: $index")
                "removed"
            },

            removeAll = { items ->
                log("REMOVE ALL: $items")
                true
            },

            set = { index, item ->
                log("SET: $index = $item")
                item
            },

            contains = { item ->
                log("CONTAINS: $item")
                true
            },

            containsAll = { items ->
                log("CONTAINS ALL: $items")
                true
            },

            indexOf = { item ->
                log("INDEX OF: $item")
                99
            },

            lastIndexOf = { item ->
                log("LAST INDEX OF: $item")
                100
            },

            isEmpty = {
                log("EMPTY?")
                false
            },

            toString = {
                "MY CUSTOM LIST"
            }
        )
    }

    val addBenchmark = r("Not run")
    val createBenchmark = r("Not run")
    val miscResult = r("")

    val items = r {
        mutableStateListOf(
            TestData(),
            TestData(),
            TestData()
        )
    }

    Column {

        Text("CustomList Tests")

        Row {

            Button(
                onClick = {

                    val start = System.nanoTime()

                    repeat(1_000_000) {
                        list.add(it.toString())
                    }

                    val end = System.nanoTime()

                    addBenchmark.value =
                        "${(end - start) / 1_000_000} ms"

                    log(
                        "Add Time: ${addBenchmark.value}"
                    )
                }
            ) {
                Text("Benchmark Add")
            }

            Button(
                onClick = {

                    val start = System.nanoTime()

                    repeat(1_000_000) {
                        CustomList(
                            items = listOf("A", "B"),

                            add = { true },
                            addAt = { _, _ -> },
                            addAll = { true },
                            addAllAt = { _, _ -> true },
                            clear = {},
                            get = { "fake-$it" },
                            remove = { true },
                            removeAt = { "removed" },
                            removeAll = { true },
                            set = { _, item -> item },
                            contains = { true },
                            containsAll = { true },
                            indexOf = { 99 },
                            lastIndexOf = { 100 },
                            isEmpty = { false },
                            toString = { "MY CUSTOM LIST" }
                        )
                    }

                    val end = System.nanoTime()

                    createBenchmark.value =
                        "${(end - start) / 1_000_000} ms"

                    log(
                        "Create Time: ${createBenchmark.value}"
                    )
                }
            ) {
                Text("Benchmark Create")
            }
        }

        Text("Add: ${addBenchmark.value}")
        Text("Create: ${createBenchmark.value}")

        Row {

            Button(
                onClick = {

                    list.add("C")
                    list.add(0, "Z")

                    list.addAll(
                        listOf(
                            "D",
                            "E"
                        )
                    )

                    list.addAll(
                        1,
                        listOf(
                            "X",
                            "Y"
                        )
                    )

                    miscResult.value =
                        "Add operations done"
                }
            ) {
                Text("Add Tests")
            }

            Button(
                onClick = {

                    val result = buildString {

                        appendLine("get = ${list[0]}")

                        list[0] = "NEW"

                        appendLine(
                            "contains = ${
                                list.contains("A")
                            }"
                        )

                        appendLine(
                            "containsAll = ${
                                list.containsAll(
                                    listOf("A")
                                )
                            }"
                        )

                        appendLine(
                            "indexOf = ${
                                list.indexOf("A")
                            }"
                        )

                        appendLine(
                            "lastIndexOf = ${
                                list.lastIndexOf("A")
                            }"
                        )

                        appendLine(
                            "isEmpty = ${
                                list.isEmpty()
                            }"
                        )

                        appendLine(
                            "toString = $list"
                        )
                    }

                    miscResult.value = result
                    log(result)
                }
            ) {
                Text("Read Tests")
            }
        }

        Row {

            Button(
                onClick = {

                    list.remove("A")

                    list.removeAt(0)

                    list.removeAll(
                        listOf("B")
                    )

                    miscResult.value =
                        "Remove operations done"
                }
            ) {
                Text("Remove Tests")
            }

            Button(
                onClick = {

                    list.clear()

                    miscResult.value =
                        "List cleared"
                }
            ) {
                Text("Clear")
            }
        }

        Text(
            text = miscResult.value
        )

        Text("Recompose Test")

        Row {

            Button(
                onClick = {
                    items += TestData()
                }
            ) {
                Text("Add")
            }

            Button(
                onClick = {
                    if (items.isNotEmpty()) {
                        items.removeAt(0)
                    }
                }
            ) {
                Text("Delete")
            }

            Button(
                onClick = {
                    if (items.isNotEmpty()) {
                        items[0].name =
                            "updated ${System.currentTimeMillis()}"
                    }
                }
            ) {
                Text("Edit First")
            }
        }

        Column(
            Mod.h(300)
        ) {

            LazyColumn {

                items(
                    items = items,
                    key = { it.hashCode() }
                ) { item ->

                    Text(
                        text = item.name
                    )
                }
            }
        }
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
			closeApp()
		}
		LazyItem(
            icon = { Icon.Whatshot() },
            title = "‼️ DELETE ALL DATA",
            onClick = {
				showDeleteSure.it = yes
			}
        )
}



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

	
	var maxW = Logs.rMaxWidth(
		letterS = 14,
		font = FontFamily.Monospace
	) + 15.dp

	

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
					items(Logs) { LogTxt(it) }
				}
			}
        }
    }
}


class MyNotificationListener : NotificationListenerService()

