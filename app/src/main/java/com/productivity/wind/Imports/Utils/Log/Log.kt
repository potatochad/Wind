//‼️‼️THIS IS VERY SENSITIVE CODE AND CAN EASILY NO WORK 
//‼️‼️‼️DONT USE MY OUTSIDE FUNCTIONS‼️‼️

package com.productivity.wind.Imports.Utils.Log

import com.productivity.wind.Bar
import com.productivity.wind.Imports.Utils.appScope
import com.productivity.wind.Imports.Utils.App

import android.os.*
import android.content.*
import android.util.*
import android.widget.Toast
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*

typealias Str = String
typealias Bool = Boolean
typealias Do = () -> Unit
typealias Wait = suspend () -> Unit
typealias mList<T> = MutableList<T>

typealias ui = @Composable () -> Unit
typealias ui_<T> = @Composable (T) -> Unit


val Str.size: Int
    get() = length
fun Str.last(n: Int): Str = takeLast(n)
fun Str.fromTo(start: Int, end: Int = this.size) = substring(start, end)
fun <T> mList<T>.fromTo(start: Int, end: Int = size): mList<T> = subList(start, end)

fun <T> mList<T>.keep(max: Int) {
    if (size > max) {
        fromTo(0, size - max).clear()
    }
}

@Composable
fun <T> r(x: () -> T) = remember { x() }
fun <T> m(value: T) = mutableStateOf(value)
@Composable
fun <T> r(x: T) = r { m(x) }
@Composable
inline fun <T> r(vararg keys: Any?, crossinline calc: () -> T): T = remember(*keys, calculation = calc)


fun <T> mList() = mutableStateListOf<T>()


fun Do(eLog: Str="", onError: Wait ={}, Do: Wait) {
	var whereCalled by m("")
	appScope.launch {
		try {
			whereCalled = callerId(2)
			Do()
		} catch (e: CancellationException) {
			throw e
		} catch (e: Exception) {
			Vlog("Do error, $eLog: ${e.message}, $whereCalled")
			onError()
		}
	} 
}



fun Any?.vlog(x: Str){
	Vlog("$x: [ $this ]", 800)
}
fun Any?.blog(x: Str){
	log("$x: [ $this ]", 800)
}

fun log(message: Str, int: Int = 800) {
    var msg = message.replace("\n", " | ").take(int)
    if (msg.length >= int) { msg += " ..." }

	val chunkSize = 2000
    var start = 0

    while (start < msg.length) {
        val end = minOf(start + chunkSize, msg.size)
		Log.e("[bad]", msg.fromTo(start, end))
        start = end
	}
}
private var lastToast: Toast? = null

fun Vlog(msg: Str, maxInt: Int = 800, special: Str = "none", delayLevel: Int = 0) {
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
    msg: Str,
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



fun MeasureWaitLag(title: Str, block: Wait) {
    val start = System.currentTimeMillis()
	Do { block() }
    val end = System.currentTimeMillis()
    log("[$title]: code took ${end - start} ms")
}
@Composable
fun MeasureUILag(title: Str, block: ui) {
    val start = System.currentTimeMillis()
	block()
    val end = System.currentTimeMillis()
    log("[$title]: code took ${end - start} ms")
}
fun <T> MeasureLag(title: Str, block: () -> T): T {
    val start = System.currentTimeMillis()

    val result = block()   // run and capture result

    val end = System.currentTimeMillis()
    log("[$title]: code took ${end - start} ms")

    return result
}
fun MeasureLagNoReturn(title: Str, block: () -> Unit) {
    val start = System.currentTimeMillis()

    block()   // just run it, ignore return

    val end = System.currentTimeMillis()
    log("[$title]: code took ${end - start} ms")
}


fun getMyAppLogs() {
	fun AddLog(s: Str) {
		Bar.logs.add(s)
		if (Bar.logs.size > 2000) Bar.logs.keep(2000)
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

//‼️‼️THIS IS VERY SENSITIVE CODE AND CAN EASILY NO WORK 
//‼️‼️‼️DONT USE OUTSIDE FUNCTIONS‼️‼️
object AppCrash {
	fun usefulFrame(frame: StackTraceElement): Bool {
		val name = frame.className

		return !name.startsWith("android.") &&
		   !name.startsWith("androidx.") &&
           !name.startsWith("java.") &&
           !name.startsWith("kotlin.") &&
           !name.startsWith("com.android.") &&
		   !name.contains("ComposableSingletons") &&
           !name.contains("${'$'}${'$'}ExternalSynthetic")
	}

	private fun usefulStackTrace(throwable: Throwable): Str {
		return buildString {
			appendLine(throwable::class.java.name)
			
			throwable.message?.let {
				appendLine(it)
			}

			throwable.stackTrace
				.filter(::usefulFrame)
				.take(20)
				.forEach {
					appendLine("at $it")
				}  

			throwable.cause?.let { cause ->
				appendLine("Caused by: ${cause::class.java.name}")
				appendLine(cause.message ?: "")
				cause.stackTrace
					.filter(::usefulFrame)
					.take(20)
					.forEach {
						appendLine("at $it")
					}
			}
			
		}
	}

    fun install(context: Context) {
        val previous = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val file = File(context.filesDir, "crash.txt")

                file.writeText("${usefulStackTrace(throwable)}")

            } catch (_: Throwable) {
            }

            previous?.uncaughtException(thread, throwable)
        }
    }

    fun printLastCrash(context: Context) {
        val file = File(context.filesDir, "crash.txt")

        if (!file.exists()) return

        try {
            Log.e("AppCrash", file.readText())
        } catch (_: Throwable) {
        } finally {
            file.delete()
        }
    }
}





fun folder(folderName: Str): File {
    val folder = File(App.filesDir, folderName)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    return folder
}
