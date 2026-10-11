package com.productivity.wind.Imports.Utils.SaveData

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import androidx.compose.runtime.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.productivity.wind.*
import kotlin.collections.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.productivity.wind.Imports.Utils.*


fun encrypt(text: Str, key: Int) = text.map { (it.code + key).toChar() }.joinToString("")           
fun decrypt(text: Str, key: Int) = text.map { (it.code - key).toChar() }.joinToString("")


@Composable
fun Backup(show: mStateBool) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult

        App.contentResolver.openOutputStream(uri)
            ?.bufferedWriter()
            ?.use { File ->
                AppData.prefs.all.forEach { (key, value) ->
                    val type = when (value) {
                        is Int -> "Int"
                        is Bool -> "Boolean"
                        is Float -> "Float"
                        is Long -> "Long"
                        else -> "String"
                    }
					val line = "$key|$type|$value"
					if (Bar.encryptedBackup) {
						File.write(encrypt(line, 132) + "\n")
					} else {
						File.write(line + "\n")
					}
                }
            }
    }

    RunOnce(show.it) {
        if (show.it) {
            launcher.launch("backup.txt")
            show.it = no
        }
    }
}


@Composable
fun Restore(show: mStateBool) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult

        Do {
            val editor = AppData.prefs.edit()

            App.contentResolver.openInputStream(uri)
                ?.bufferedReader()
                ?.useLines { lines ->
                    lines.forEach { lineEncrypted ->
						var line by mState("")
						if (Bar.encryptedBackup) {
							line = decrypt(lineEncrypted, 132)
						} else {
							line = lineEncrypted
						}

						
                        val parts = line.split("|", limit = 3)
                        if (parts.size != 3) return@forEach

                        val key = parts[0]
                        val type = parts[1]
                        val value = parts[2]

                        when (type) {
                            "Int" -> editor.putInt(key, value.toInt())
                            "Boolean" -> editor.putBoolean(key, value.toBoolean())
                            "Float" -> editor.putFloat(key, value.toFloat())
                            "Long" -> editor.putLong(key, value.toLong())
                            else -> editor.putString(key, value)
                        }
                    }
                }

            editor.commit() 
			Vlog("Reloading App...")

			CloseMyApp() 
        }
    }

    RunOnce(show.it) {
        if (show.it) {
            launcher.launch(arrayOf("text/plain"))
            show.it = no
        }
    }
}
