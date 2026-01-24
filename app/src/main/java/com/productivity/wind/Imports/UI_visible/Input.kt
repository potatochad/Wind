package com.productivity.wind.Imports.UI_visible

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
import com.productivity.wind.Imports.Data.*
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
import com.productivity.wind.Imports.UI_visible.*
import android.os.Process.*
import android.content.ClipData
import android.content.ClipboardManager

@Suppress("UNCHECKED_CAST")

@Composable
fun BasicInput(
    value: Str,
    isInt: Bool = no,
	w: Int=60,
	modifier: Mod = Mod
		.h(34).space(h = 8, w = 4).w(w)
		.background(InputColor, shape = RoundedCornerShape(4.dp))
		.wrapContentHeight(Alignment.CenterVertically),            
	textStyle: TextStyle = TextStyle(
		color = Gold,
		fontSize = 14.sp,
		textAlign = TextAlign.Start
	),
	oneLine: Bool= yes,
    Do: DoStr = {},
) {
	val focusManager = LocalFocusManager.current
	val focusRequester = r { FocusRequester() }

	Row(
		modifier = modifier.click { focusRequester.requestFocus() },
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Start       
	) {
		move(w=3)
		BasicTextField(
			value = value,
			onValueChange = { Do(it) },
			textStyle = textStyle, 
			singleLine = oneLine, 
			keyboardOptions = KeyboardOptions(
				keyboardType = if (isInt) KeyboardType.Number else KeyboardType.Text,
				imeAction = ImeAction.Done
			),
			keyboardActions = KeyboardActions(
				onDone = { focusManager.clearFocus() }
			),
			modifier = Mod.focusRequester(focusRequester)
		)
	}
}

@Composable
fun Input(
    what: m_<Str>,
    isInt: Bool = no,
	modifier: Mod = Mod, 
	textStyle: TextStyle = TextStyle(),
    onChange: DoStr = {},
) {
	val focusManager = LocalFocusManager.current

	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Start       
	) {
		move(w=5)
		BasicTextField(
			value = what.it,
			onValueChange = {
				val filtered = if (isInt) it.filter { c -> c.isDigit() } else it

				what.it = filtered
		
				onChange(filtered)
			},
			textStyle = textStyle, 
			singleLine = yes,
			// modifier = modifier, 
			keyboardOptions = KeyboardOptions(
				keyboardType = if (isInt) KeyboardType.Number else KeyboardType.Text,
				imeAction = ImeAction.Done
			),
			keyboardActions = KeyboardActions(
				onDone = { focusManager.clearFocus() }
			)
		)
	}
	
}


@Composable
fun LazyInput(
    str: mStr,
    isInt: Bool = no,
	modifier: Mod = Mod,
    maxLetters: Int = 20,
    textStyle: TextStyle = TextStyle(
		color = Color.White,
		fontSize = 14.sp,
		textAlign = TextAlign.Start
	),
    onChange: DoStr = {},
) {
    val finalMod = modifier.space(h = 8, w = 4).background(CardColor, shape = RoundedCornerShape(4.dp))
	
    Input(
        what = str,
        isInt = isInt,
        modifier = finalMod,
        textStyle = textStyle,
    ) { input ->
		if (isInt && input.isEmpty()) {
			str.it = "0"
		} else {
			str.it = input.take(maxLetters)
		}
		onChange(str.it)
    }
}


@Composable
fun BigInput(txt: mStr, scrollV: ScrollState = r_Scroll(), Do: DoStr={ txt.it = it }){
	val scroll = scrollV
	var Field by r(TextFieldValue(txt.it))
	var done = r(no)
	var itIndex by r(0)
		
    OutlinedTextField(
        value = Field,
        onValueChange = {    
					
			Do(it.text)
			Field = TextFieldValue(
				text = txt.it,
				selection = it.selection
			)
			itIndex = it.selection.start
				
			fixedInputScroll(Field, itIndex, done, scroll)

		},
        modifier = Mod.maxW().h(150).Vscroll(scroll).onFocusChanged{
			if (!it.isFocused) done.it = no
		},
		placeholder = { Text("Start typing...") },
    )
}



@Composable
fun TinyInput(txt: m_<Str>, maxLetters: Int = 4, isInt: Bool =yes, w: Int = 60, Do: DoStr={_->}) {  
    BasicInput(
        "${txt.it}",
        isInt = isInt, 
        w=w,
    ) {
        val str = it.take(maxLetters)
		
		if (isInt) {
            // Convert to Int immediately, fallback to 0
            val num = toInt(str)
			
			if (mInt) { txt.it = num } else {
				txt.it = "$num"
				Do("$num")
			}
         } else {
			if (mInt) Vlog("error: got mInt, expected mStr")
			
            txt.it = str
            Do(str)
		}
    }
}




	




