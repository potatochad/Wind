//‼️‼️THIS IS VERY SENSITIVE CODE AND CAN EASILY NO WORK 
//‼️‼️‼️DONT USE MY OUTSIDE FUNCTIONS‼️‼️
//‼️DONT ADD AN IMPORTS FOLDER OR FILE HERE (THEY CONFLICT)

package com.productivity.wind.Imports.Utils.Log

import com.productivity.wind.Bar
import com.productivity.wind.Imports.Utils.AppsAndDevice.appScope
import com.productivity.wind.Imports.Utils.AppsAndDevice.App

import android.os.*
import android.content.*
import android.util.*
import android.widget.Toast
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException



fun Any?.vlog(x: String) = Vlog("$x: [ $this ]", 800)
fun Any?.blog(x: String) = log("$x: [ $this ]", 800)

//‼️DONT use any lateinit vars here
fun log(message: String, int: Int = 800) {
    var msg = message.replace("\n", " | ").take(int)
    if (msg.length >= int) { msg += " ..." }

	val chunkSize = 2000
    var start = 0

    while (start < msg.length) {
        val end = minOf(start + chunkSize, msg.length)
		Log.e("[bad]", msg.substring(start, end))
        start = end
	}
}
private var lastToast: Toast? = null

//‼️DONT use any lateinit vars here
fun Vlog(msg: String, maxInt: Int = 800, special: String = "none", delayLevel: Int = 0) {
    val delayMs = (delayLevel.coerceIn(0, 100)) * 30L // Example: Level 2 = 60ms

    if (special.equals("one", true)) {
        lastToast?.cancel()
    }

	log(msg, maxInt)


    Handler(Looper.getMainLooper()).postDelayed({
        val toast = Toast.makeText(App, msg, Toast.LENGTH_SHORT)
        lastToast = toast
        toast.show()
    }, delayMs)
}

// This always cancels the previous toast before showing the new one.
fun VlogOne(
    msg: String,
    maxInt: Int = 800,
    delayLevel: Int = 0
) {
    Vlog(
        msg = msg,
        maxInt = maxInt,
        special = "one",
        delayLevel = delayLevel
    )
}


fun getMyAppLogs() {
	fun AddLog(s: String) {
		Bar.logs.add(s)

        if (Bar.logs.size > 2000) {
            Bar.logs.subList(0, Bar.logs.size - 2000).clear()
        }
	}
	Thread {
		val pid = android.os.Process.myPid()
		val process = Runtime.getRuntime().exec("logcat --pid=$pid *:W")
		val reader = BufferedReader(InputStreamReader(process.inputStream))

		reader.forEachLine { line ->
			val s = line.replace(Regex("""^\d{2}-\d{2}\s+|\s+\d+\s+\d+\s+"""), " ").takeLast(3000)
			if ("ApkAssets: Deleting" in s) return@forEachLine
			if ("WindowOnBackDispatcher" in s) return@forEachLine
			if (" W " in s) return@forEachLine

			val last = Bar.logs.lastOrNull()

			if (last != s){
				AddLog(s)
			} else {
				if ("[bad]" in s) {
					AddLog(s)
				}
			}

			
		}
	}.start()
}



fun folder(folderName: String): File {
    val folder = File(App.filesDir, folderName)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    return folder
}

class logTimer(val name: String = "") {

    private val start = System.nanoTime()

    fun stop() {
        val ms = String.format(
            "%.3f",
            (System.nanoTime() - start) / 1_000_000.0
        )

        Vlog("${if (name.isEmpty()) "It" else name} took ${ms}ms")
    }
}









