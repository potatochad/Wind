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

//TREAT THIS AS WEIRD ENUM 
class ItemVars {
    var increased = ""
    var decreased = ""
    var same = yes
    
    fun Increased(){
        
    }
    fun Decreased(){
        
    }
    fun Same(): Bool {
        same = no
        return same
    }
}

/*
fun LoadList(name: Str): ListData {
return lists.getOrPut(listName) {
            LazyData(listName)
        }
    return LazyData(name, savedString)
}
*/

class ListData {
    private val lists = mutableMapOf<Str, LazyData>()

    operator fun get(listName: Str): LazyData {
        
    }

    fun varsIncreased(listName: Str, varList: MapVarInfo): Bool {
        varList.toList()
        LazyData(listName)
        return no
    }
    fun varsDecreased(listName: Str, varList: MapVarInfo): Bool {
        varList.toList()
        LazyData(listName)
        return no
    }
}


/*
data class VarInfo(
    val name: Str,
    val type: Str,
    val value: Str
)

class ListSaveStr(listName: Str) {

    var strData: Str = ""

    private fun getVars(): List<VarInfo> {
        val start = strData.indexOf('{')
        val end = strData.lastIndexOf('}')

        if (start == -1 || end == -1 || start >= end)
            return emptyList()

        val varsStr = strData.fromTo(start + 1, end).trim()

        if (varsStr.empty)
            return emptyList()

        val result = mutableListOf<VarInfo>()

        // Split variables by commas outside nested structures
        val vars = mutableListOf<Str>()
        var depth = 0
        var current = ""

        for (c in varsStr) {
            when (c) {
                '(', '{', '[' -> {
                    depth++
                    current += c
                }

                ')', '}', ']' -> {
                    depth--
                    current += c
                }

                ',' -> {
                    if (depth == 0) {
                        vars.add(current.trim())
                        current = ""
                    } else {
                        current += c
                    }
                }

                else -> current += c
            }
        }

        if (current.trim().notEmpty())
            vars.add(current.trim())

        // Parse name:type:value
        for (variable in vars) {
            val parts = mutableListOf<Str>()
            depth = 0
            current = ""

            for (c in variable) {
                when (c) {
                    '(', '{', '[' -> {
                        depth++
                        current += c
                    }

                    ')', '}', ']' -> {
                        depth--
                        current += c
                    }

                    ':' -> {
                        if (depth == 0) {
                            parts.add(current.trim())
                            current = ""
                        } else {
                            current += c
                        }
                    }

                    else -> current += c
                }
            }

            parts.add(current.trim())

            if (parts.size >= 3) {
                result.add(
                    VarInfo(
                        name = parts[0],
                        type = parts[1],
                        value = parts.drop(2).joinToString(":")
                    )
                )
            }
        }

        return result
    }

    private fun countVars(): Int {
        return getVars().size
    }

    private fun varsMatch(map: MapVarInfo): Bool {
        return map.entries.size == countVars()
    }
}
*/
















