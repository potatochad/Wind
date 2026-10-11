//‼️‼️THIS IS VERY SENSITIVE CODE AND CAN EASILY NO WORK 
//‼️‼️‼️DONT USE MY OUTSIDE FUNCTIONS‼️‼️
//‼️NO RENAMES

package com.productivity.wind.Imports.Utils.Log

import android.content.*
import android.util.*
import java.io.File


object AppCrash {
	fun usefulFrame(frame: StackTraceElement): Boolean {
		val name = frame.className

		return !name.startsWith("android.") &&
		   !name.startsWith("androidx.") &&
           !name.startsWith("java.") &&
           !name.startsWith("kotlin.") &&
           !name.startsWith("com.android.") &&
		   !name.contains("ComposableSingletons") &&
           !name.contains("${'$'}${'$'}ExternalSynthetic")
	}

	private fun usefulStackTrace(throwable: Throwable): String {
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



