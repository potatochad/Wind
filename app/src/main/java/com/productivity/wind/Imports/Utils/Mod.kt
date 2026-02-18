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
import androidx.compose.ui.window.*
import android.os.Process.*
import com.productivity.wind.Imports.UI_visible.*



fun Mod.space(
    s: Any? = null,
    h: Any? = null,
    w: Any? = null,
    start: Any? = null,
    top: Any? = null,
    end: Any? = null,
    bottom: Any? = null
): Mod {
    return when {
        s != null -> this.padding(toDp(s))
        h != null || w != null -> this.padding(
            horizontal = toDp(h),
            vertical = toDp(w)
        )
        else -> this.padding(
            start = toDp(start),
            top = toDp(top),
            end = toDp(end),
            bottom = toDp(bottom)
        )
    }
}

typealias Mod = Modifier
typealias mod = Modifier

fun Mod.w(min: Any?, max: Any? = min) = this.widthIn(max = toDp(max), min = toDp(min))
fun Mod.h(min: Any?, max: Any? = min) = this.heightIn(max = toDp(max), min = toDp(min))
fun Mod.s(value: Any?) = this.size(toDp(value))
fun Mod.s(Do: Do_<IntSize>): Mod = this.onSizeChanged { Do(it) }

fun Mod.center() = this.wrapContentSize(align = Alignment.Center)
fun Mod.round(x: Any): Mod = this.clip(RoundedCornerShape(toDp(x)))


fun Mod.maxS(): Mod= this.fillMaxSize()
fun Mod.maxW(): Mod= this.fillMaxWidth()
fun Mod.maxH(): Mod= this.fillMaxHeight()

@Composable
fun Mod.scroll(
    v: Bool = yes,
    h: Bool = yes,
    r_v: Scroll = Scroll(),
    r_h: Scroll = Scroll(),
): Mod {
    var m = this
    if (v) m = m.verticalScroll(r_v)
    if (h) m = m.horizontalScroll(r_h)
    return m
}



@Composable
fun Mod.click(
    animate: Bool = yes,
    Do: Do,
): Mod {
    return this.clickable(
        indication = if (animate) LocalIndication.current else null,
        interactionSource = MutableInteractionSource()
    ) {
        Do()
    }
}



fun Mod.clickOrHold(
    hold: Bool = yes,
    action: Do,
): Mod {
   return if (hold) {
      pointerInput(Unit) {
          detectTapGestures(onLongPress = { action() })
      }
   } else {
      clickable(
         indication = null,
         interactionSource = MutableInteractionSource()
      ) {
         action()
      }
  	}
}



val Mod.transparent: Mod
    get() = this.background(Transparent)
        
val Mod.black: Mod
    get() = this.background(Color.Black)
    
val Mod.red: Mod
    get() = this.background(Color.Red)

val Mod.green: Mod
    get() = this.background(Color.Green)

val Mod.blue: Mod
    get() = this.background(Color.Blue)


    


fun Mod.getW(onWidth: Do_<Int>): Mod = this.then(
    Mod.onGloballyPositioned {
        onWidth(it.size.width)
    }
)
fun Mod.getH(onWidth: Do_<Int>): Mod = this.then(
    Mod.onGloballyPositioned {
        onWidth(it.size.height)
    }
)



fun Mod.mergeWith(update: Mod): Mod {
    val elements = mutableListOf<Mod.Element>()
    
    // collect elements from original Modifier
    this.foldIn(elements) { acc, element ->
        acc.add(element)
        acc
    }

    // add/update elements from update Modifier
    update.foldIn(elements) { acc, element ->
        acc.removeAll { it::class == element::class } // remove same type
        acc.add(element)
        acc
    }

    // rebuild Modifier from elements
    return elements.fold(Mod) { m, e -> m.then(e) }
}






















