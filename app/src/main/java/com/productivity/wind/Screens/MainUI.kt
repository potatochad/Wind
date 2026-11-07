package com.productivity.wind.Screens

import com.productivity.wind.Imports.Utils.NavControl.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.productivity.wind.MAINStart
import com.productivity.wind.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.*
import androidx.compose.runtime.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.activity.compose.*
import com.productivity.wind.Screens.Task.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*



suspend fun TestLazyBatch() {
    Vlog("========== LAZY BATCH TEST START ==========")

    val events = mutableListOf<String>()

    val batch = LazyBatch(
        update = {
            Vlog("DEFAULT UPDATE EXECUTED")
            events += "default"
        },
        delay = 100
    )

    var a by batch(0)
    var b by batch(0)
    var custom by batch(0, {
        Vlog("CUSTOM UPDATE EXECUTED")
        events += "custom"
    })

    // TEST 1: Rapid repeated assignments
    Vlog("TEST 1: RAPID ASSIGNMENTS")

    a = 1
    a = 2
    a = 3
    a = 4

    check(events.isEmpty()) { "Executed too early!" }

    delay(150)

    check(events.count { it == "default" } == 1) {
        "Deduplication failed!"
    }

    Vlog("TEST 1 PASSED")

    // TEST 2: Multiple independent properties
    Vlog("TEST 2: MULTIPLE PROPERTIES")

    a = 10
    b = 20
    custom = 30

    delay(150)

    check(events.count { it == "default" } == 3)
    check(events.count { it == "custom" } == 1)

    Vlog("TEST 2 PASSED")

    // TEST 3: Nested batches
    Vlog("TEST 3: NESTED BATCHES")

    val before = events.size

    batch {
        a = 100
        a = 200

        batch {
            b = 300
            b = 400
            custom = 500
        }

        a = 600
        b = 700
    }

    check(events.size == before + 3) {
        "Nested batch did not deduplicate correctly!"
    }

    Vlog("TEST 3 PASSED")

    // TEST 4: Reentrant callback
    Vlog("TEST 4: REENTRANCY")

    var trigger by batch(0, {
        Vlog("REENTRANT CALLBACK: modifying b")
        b++
    })

    trigger = 1

    delay(250)

    check(b == 701) {
        "Reentrant update failed! b = $b"
    }

    Vlog("TEST 4 PASSED")

    // TEST 5: Thread enforcement
    Vlog("TEST 5: THREAD ENFORCEMENT")

    val rejected = withContext(Dispatchers.Default) {
        try {
            batch(999)
            false
        } catch (e: IllegalStateException) {
            Vlog("CORRECTLY REJECTED BACKGROUND THREAD")
            true
        }
    }

    Vlog("TEST 5 PASSED")

    // FINAL
    Vlog("========== ALL TESTS PASSED ==========")
    Vlog("TOTAL EVENTS: ${events.size}")
}



@Composable
fun Main() {
	RunOnce { MAINStart() }
	
	var searching by r(no)
	var Tag = r("")

	BtnFloating { goTo("Challenge") }

	fun stopSearch(){
		Tag.it = ""
		searching = no
	}
	
	LazyScreen(
		top = { 
			if (!searching) {
				AppItem.Menu()
				
				Icon.Chill { goTo("Web") }

				Icon.Reload{
					appScope.launch {
    TestLazyBatch()
					}
					//testBackgroundAudio()
				}
				move(12)
				Text("Points ${Bar.funTime}")
        
				End { 
					Icon.Search {
						searching = yes
					}
				}
			} else {
				Icon.Back { stopSearch() }
				TinyInput(Tag, Mod.h(40).weight(1f), isInt = no, maxLetters = 400)
				move(30)
				BackHandler { stopSearch() }
			}
		}, 
		backIcon = no
	) {
		
		Bar.copyTsk.findUI({ 
			if (searching) it.input.contains(Tag.it) else !it.done()
		}) { 
			LazyCard { 
				CopyTskUI(it) 
			} 
		}

		Bar.doTsk.findUI({ 
			if (searching) { it.name.contains(Tag.it) || it.description.contains(Tag.it) } else { !it.done() && taskDueToday(it.schedule) }					
		}) { 						
			LazyCard(
				modUI = Mod.space(start = 8),
				modCard = Mod.space(8, 10).maxW().click {    
					goTo("ToDo/${it.id}")
				},
			) { 
				DoTskUI(it)
			}
		}
		
		Bar.apps.findUI({ 
				if (searching) it.name.contains(Tag.it) else !it.done
		}) { 
			AppTaskUI(it) 
		}

			

	}
}







				
