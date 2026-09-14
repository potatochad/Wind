package com.productivity.wind.Imports.Utils.SaveData.List

import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.Renames.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.String.*
import android.annotation.SuppressLint
import timber.log.Timber
import java.text.*
import android.app.usage.UsageStatsManager
import androidx.compose.foundation.interaction.*
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.isAccessible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type
import kotlin.collections.*
import android.content.*
import java.lang.reflect.ParameterizedType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import android.content.Intent
import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.layout.*
import android.graphics.drawable.Drawable
import android.content.pm.*
import com.productivity.wind.Imports.*
import java.util.*
import com.productivity.wind.R
import kotlin.reflect.full.*
import androidx.compose.ui.focus.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import java.io.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.text.style.*
import androidx.compose.foundation.lazy.*
import java.util.*
import kotlin.concurrent.*
import androidx.annotation.RequiresApi
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import androidx.compose.foundation.text.selection.*
import kotlin.system.*
import androidx.navigation.*
import android.webkit.*
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.cli.common.ExitCode
import com.productivity.wind.Imports.Utils.*
import android.location.*
import androidx.core.content.*
import androidx.compose.ui.text.*
import androidx.navigation.compose.*
import android.util.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.location.*
import android.content.*
import android.net.*
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlin.properties.*
import org.json.JSONObject
import com.productivity.wind.Imports.UI_visible.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.builtins.ListSerializer



class ListData(
    val Where: Str,
) {
	val saveTo = Where

	
	var strData = LazyData(saveTo) 
	//‼️‼️‼️‼️ what does this actually return
	
	
	val prefs: SharedPreferences
        get() = App.getSharedPreferences(saveTo, Context.MODE_PRIVATE)

	val all: Map<Str, Any?>
        get() = prefs.all

	val dataEdit get() = prefs.edit()


	fun each(Do: (Str, Any?) -> Unit) {
		prefs.all.forEach { (key, value) ->
			Do(key, value)
		}
	}

	fun deleteAll() = dataEdit.clear().apply()
	fun remove(id: Str) = dataEdit.remove(id).apply()

	fun hasKey(x: Str) = prefs.hasKey(x)
	fun find(match: (Str) -> Bool) = prefs.all.filter { (key, _) -> match(key) }
	
	

	
		
    

	/* returns
	[
    VarInfo(name = "foo",  value = "hello",    type = "String"),
    VarInfo(name = "bar",  value = "123",      type = "Int"),
    VarInfo(name = "baz",  value = "[a,b,c]",  type = "List<String>")
	]
	*/
	//‼️‼️ADD A STANDARDIZED WAY TO SAVEE (STRING)
	//‼️‼️WHICH FUNCTIONS CAN READD
	//‼️‼️AND I CAN LATER CHANGE EDIT
	//‼️‼️ MAYBE TWO VARS: example and computer readable 'x':y'c'
	//‼️‼️ HAVE COMMENTS AND EXPLANATION OF COMPUTER READABLE
	fun process(): List<VarInfo<*>> {
        return processVarInfoSTRING(strData)
	}
    
    
    

    fun get(id: Str) = prefs.getString(id, null)
	fun put(id: Str, x: Str, Do: (SharedPreferences.Editor) -> Unit = { it.apply() }) {
        val e = dataEdit
        e.putString(id, x)
		Do(e)
	}
	fun <T> commit(id: Str, x: T) = put(id, x, { it.commit() })
	fun <T> apply(id: Str, x: T) = put(id, x)

	
	
}


private fun processVarInfoSTRING(strData: Str): List<VarInfo<Str>> {
    val start = strData.indexOf('{')
    val end = strData.lastIndexOf('}')

    if (start == -1 || end == -1 || start >= end)
        return emptyList()

    val varsStr = strData.substring(start + 1, end).trim()

    if (varsStr.isEmpty())
        return emptyList()

    val result = mutableListOf<VarInfo<String>>()

    // Split by commas outside (), {}, []
    val vars = mutableListOf<String>()
    var depth = 0
    var current = StringBuilder()

    for (c in varsStr) {
        when (c) {
            '(', '{', '[' -> {
                depth++
                current.append(c)
            }

            ')', '}', ']' -> {
                depth--
                current.append(c)
            }

            ',' -> {
                if (depth == 0) {
                    vars += current.toString().trim()
                    current.clear()
                } else {
                    current.append(c)
                }
            }

            else -> current.append(c)
        }
    }

    if (current.isNotBlank())
        vars += current.toString().trim()

    // name:type:value
    for (variable in vars) {
        val parts = mutableListOf<String>()
        depth = 0
        current.clear()

        for (c in variable) {
            when (c) {
                '(', '{', '[' -> {
                    depth++
                    current.append(c)
                }

                ')', '}', ']' -> {
                    depth--
                    current.append(c)
                }

                ':' -> {
                    if (depth == 0) {
                        parts += current.toString().trim()
                        current.clear()
                    } else {
                        current.append(c)
                    }
                }

                else -> current.append(c)
            }
        }

        parts += current.toString().trim()

        if (parts.size >= 3) {
            val name = parts[0]
            val type = parts[1]
            val value = parts.drop(2).joinToString(":")

            result += VarInfo(
                name = name,
                value = value,
                type = type,
                typeStr = type
            )
        }
    }

    return result
}




