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




class TextField(
    a: Str = "",
    private val UIStrOn: Bool = yes,
) {
    var it by m(TextFieldValue(a))
    var UIStr by m(UIStr(a))
    
    val text: Str
        get() = it.text
    val selection: TextRange
        get() = it.selection
    

    fun it(x: TextFieldValue): TextField {
        it = x
        return this
    }
    fun text(str: Str): TextField {
        it = it.copy(
            text = str,
        )
        if (UIStrOn) { UIStr = UIStr.keepOneStyle(str) }//super big lag, 
        return this
    }
    fun text(str: Str, select: TextRange): TextField {
        it = it.copy(
            text = str,
            selection = select
        )
        if (UIStrOn) { UIStr = UIStr.keepOneStyle(str) }
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

    fun bold(): TextField{ UIStr.bold(); return this }   
    fun gold(): TextField{ UIStr.gold(); return this }
    fun green(): TextField{ UIStr.green(); return this }
    fun red(): TextField{ UIStr.red(); return this }
    fun white(): TextField{ UIStr.white(); return this }
    fun black(): TextField{ UIStr.black(); return this }
    fun darkGray(): TextField{ UIStr.darkGray(); return this }
    fun gray(): TextField{ UIStr.gray(); return this }
    fun size(x: Int): TextField{ UIStr.size(x); return this }
    fun size(x: Float): TextField{ UIStr.size(x); return this }
    fun size(x: TextUnit): TextField{ UIStr.size(x); return this }

}








