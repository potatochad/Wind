package com.productivity.wind.Imports.Utils
 
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
import androidx.navigation.compose.*
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
import androidx.compose.ui.text.*
import com.productivity.wind.Imports.UI_visible.*
import java.time.*
import java.time.format.*
import android.view.*
import androidx.core.view.*
import android.view.inputmethod.InputMethodManager


data class KeyboardData(
    val open: Bool,
    val h: Int
)

@Composable
fun Keyboard(): KeyboardData {
    val h = WindowInsets.ime.getBottom(LocalDensity.current)
    var oldH by r(0)
    var open by r(no)

    if (h > 0) {
        open = yes
    }
    if (h < oldH) {
        open = no
    }

    oldH = h
    return KeyboardData(open, h)
}

fun View.showKeyboard() {
    this.requestFocus()

    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager

    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


//-------------/FOCUS/--------/
typealias FocusAsker = FocusRequester
typealias UIFocus = FocusManager

fun FocusAsker.ask() = this.requestFocus()

fun Mod.focusAsker(x: FocusAsker) = this.focusRequester(x)
    
@Composable
fun UIFocus(): UIFocus = LocalFocusManager.current
fun UIFocus.clear() = this.clearFocus()




class TextField(a: Any = "") {
    private var uiStr by m(UIStr(a))
    

    var it: TextFieldValue by m(
        TextFieldValue(uiStr)
    )
    val text: Str
        get() = it.text
    val UItext: AnnotatedString
        get() = it.annotatedString
    val selection: TextRange
        get() = it.selection
    

    fun it(x: TextFieldValue): TextField {
        it = x
        return this
    }
    fun text(str: Str): TextField {
        it = it.copy(
            text = str,
            annotatedString = it.annotatedString,
        )
        return this
    }
    fun text(str: Str, select: TextRange): TextField {
        it = it.copy(
            annotatedString = it.annotatedString,
            selection = select
        )
        return this
    }
    fun cursor(pos: Int): TextField {
        it = it.copy(
            selection = TextRange(pos, pos)
        )
        return this
    }
    fun select(pos1: Int, pos2: Int): TextField {
        it = it.copy(
            selection = TextRange(pos1, pos2)
        )
        return this
    }


    fun style(update: StrStyle.() -> StrStyle): TextField {
        it = it.copy(
            annotatedString = it.annotatedString.txt(update)
        )
        return this
    }
    fun add(str: Any, update: StrStyle.() -> StrStyle = { this }): TextField {
        it = it.copy(
            annotatedString = UIStr(it.annotatedString, UIStr(str).txt(update))
        )
        return this
    }

}





fun TextField.bold() = style { copy(fontWeight = FontWeight.Bold) }
fun TextField.gold() = style { copy(color = Gold) }
fun TextField.green() = style { copy(color = Color.Green) }
fun TextField.red() = style { copy(color = Color.Red) }
fun TextField.white() = style { copy(color = Color.White) }
fun TextField.black() = style { copy(color = Color.Black) }
fun TextField.darkGray() = style { copy(color = Color.DarkGray) }
fun TextField.gray() = style { copy(color = Color.Gray) }
fun TextField.size(x: Int) = style { copy(fontSize = x.sp) }
fun TextField.size(x: Float) = style { copy(fontSize = x.sp) }
fun TextField.size(x: TextUnit) = style { copy(fontSize = x) }







