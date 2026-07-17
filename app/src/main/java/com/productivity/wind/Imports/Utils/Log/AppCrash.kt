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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException



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



