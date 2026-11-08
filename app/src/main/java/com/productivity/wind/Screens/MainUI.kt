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
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.productivity.wind.Imports.Utils.Log.*
import kotlinx.coroutines.*


import android.os.SystemClock
import android.util.Log



suspend fun testMediaNotification() = withContext(Dispatchers.Main) {

    val start = SystemClock.elapsedRealtime()

    fun test(name: String, result: Boolean) {
        Vlog("${if (result) "PASS" else "FAIL"}: $name")
    }

    fun timed(name: String, block: () -> Unit) {
        val t = SystemClock.elapsedRealtimeNanos()
        try {
            block()
            val ms = (SystemClock.elapsedRealtimeNanos() - t) / 1_000_000.0
            Vlog("PASS: $name (${ms}ms)")
        } catch (e: Exception) {
            Vlog("FAIL: $name - ${e.message}")
        }
    }

    Vlog("========== STARTING TEST ==========")

    val music = MediaNotification()

    try {
        // 1. CONNECTION

        Vlog("Waiting for connection...")

        val connectionTime = SystemClock.elapsedRealtime()

        while (!music.isConnected &&
            SystemClock.elapsedRealtime() - connectionTime < 10_000
        ) {
            delay(50)
        }

        test("Controller connected", music.isConnected)

        if (!music.isConnected) {
            Vlog("Connection failed. Remaining tests skipped.")
            return@withContext
        }

        Vlog("Connection took ${SystemClock.elapsedRealtime() - connectionTime}ms")

        // 2. INITIAL VALUES

        test("Default title", music.title == "Music")
        test("Default artist", music.artist.isEmpty())
        test("Default text", music.text.isEmpty())
        test("Default image", music.image == null)

        // 3. NOTIFICATION EDITING

        timed("Edit notification") {
            music.edit {
                title = "Test Song"
                artist = "Test Artist"
                text = "Testing MediaNotification"
                image = null
            }
        }

        delay(200)

        test("Title updated", music.title == "Test Song")
        test("Artist updated", music.artist == "Test Artist")
        test("Text updated", music.text == "Testing MediaNotification")

        // 4. RAPID NOTIFICATION CHANGES

        val editTime = SystemClock.elapsedRealtimeNanos()

        repeat(1000) { i ->
            music.edit {
                title = "Song $i"
                artist = "Artist $i"
                text = "Update $i"
            }
        }

        Vlog("1000 edits took ${
            (SystemClock.elapsedRealtimeNanos() - editTime) / 1_000_000.0
        }ms")

        delay(300)

        test("Final title correct", music.title == "Song 999")
        test("Final artist correct", music.artist == "Artist 999")
        test("Final text correct", music.text == "Update 999")

        // 5. LOAD MUSIC

        timed("Load audio") {
            music.audio(
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
            )
        }

        Vlog("Waiting for music to start...")

        val playbackTime = SystemClock.elapsedRealtime()

        while (!music.isPlaying &&
            SystemClock.elapsedRealtime() - playbackTime < 15_000
        ) {
            delay(100)
        }

        test("Music started", music.isPlaying)

        Vlog("Playback startup: ${
            SystemClock.elapsedRealtime() - playbackTime
        }ms")

        // 6. PLAYBACK INFORMATION

        Vlog("Playing: ${music.isPlaying}")
        Vlog("Position: ${music.currentPosition}ms")
        Vlog("Duration: ${music.duration}ms")

        test("Duration available", music.duration > 0)

        // 7. PLAYBACK CONTROLS

        timed("Pause") {
            music.pause()
        }

        delay(200)
        test("Paused", !music.isPlaying)

        timed("Play") {
            music.play()
        }

        delay(200)
        test("Playing again", music.isPlaying)

        timed("Stop") {
            music.stop()
        }

        delay(200)
        test("Stopped", !music.isPlaying)

        // 8. VOLUME AND SPEED

        timed("Volume 50%") {
            music.volume(0.5f)
        }

        timed("Volume 100%") {
            music.volume(1f)
        }

        timed("Volume 0%") {
            music.volume(0f)
        }

        timed("Speed 1.25x") {
            music.speed(1.25f)
        }

        timed("Speed 1x") {
            music.speed(1f)
        }

        // 9. SEEKING

        music.audio(
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        )

        delay(2000)

        timed("Seek to 30 seconds") {
            music.goTo(30_000L)
        }

        delay(500)

        Vlog("Position after seeking: ${music.currentPosition}ms")

        test(
            "Seek reached target",
            music.currentPosition in 29_000L..33_000L
        )

        // 10. RAPID PLAYBACK COMMANDS

        timed("100 rapid playback commands") {
            repeat(100) {
                music.pause()
                music.play()
            }
        }

        delay(500)

        test("Still connected", music.isConnected)
        test("Still playing", music.isPlaying)

        // 11. EXTREME INPUTS

        timed("Negative volume") {
            music.volume(-100f)
        }

        timed("Excessive volume") {
            music.volume(100f)
        }

        timed("Very slow speed") {
            music.speed(0.1f)
        }

        timed("Fast speed") {
            music.speed(5f)
        }

        timed("Negative seek") {
            music.goTo(-1000L)
        }

        timed("Maximum seek") {
            music.goTo(Long.MAX_VALUE)
        }

        // 12. FINAL CONNECTION CHECK

        test("Connection survived", music.isConnected)

        // 13. CLOSE

        timed("Close") {
            music.close()
        }

        test("Disconnected after close", !music.isConnected)

        timed("Close again") {
            music.close()
        }

        timed("Close third time") {
            music.close()
        }

    } catch (e: Exception) {
        Vlog("TEST CRASHED: ${e.stackTraceToString()}")
    } finally {
        music.close()
    }

    Vlog("========== TEST FINISHED ==========")
    Vlog("Total runtime: ${SystemClock.elapsedRealtime() - start}ms")
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
					appScope.launch{
					testMediaNotification()
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







				
