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
import com.productivity.wind.Imports.UI_visible.*
import android.os.Process.*
import android.content.ClipData
import android.content.ClipboardManager

@Suppress("UNCHECKED_CAST")

@Composable
fun BasicInput(
    Field: TextField,
    isInt: Bool = no,
	mod: Mod = Mod,
	onLayout: Do_<TextLayoutResult> = {},
	onAction: Do = {},
	oneLine: Bool = yes,
    Do: Do_<TextField> = {},
) {
	val focus = UIFocus()
	val focusAsker = r { FocusRequester() }
	val baseMod = Mod.space(h = 8, w = 4).w(60).h(26).background(InputColor, shape = RoundedCornerShape(4.dp))   
	var w by r(0)

	val keyboard = Keyboard()
	if (!keyboard.open) focus.clear()

	
	move(3)
	LazzyRow(baseMod.centerV.start.mix(new = mod).getW{ w = it }) {
		move(3)
		BasicTextField(
			value = Field.it,
			onValueChange = { 
				Do(Field.it(it))
			},
			onTextLayout = { onLayout(it) },
			singleLine = oneLine, 
			keyboardOptions = KeyboardOptions(
				keyboardType = if (isInt) KeyboardType.Number else KeyboardType.Text,
				imeAction = ImeAction.Done
			),
			visualTransformation = object : VisualTransformation {
				override fun filter(text: UIStr): TransformedText {
					return TransformedText(
						Field.UIStr,
						OffsetMapping.Identity
					)
				}
			},
			keyboardActions = KeyboardActions(
				onDone = {
					focus.clear() 
					onAction()
				}
			),
			modifier = Mod.w(toF(w)*0.85).focusAsker(focusAsker).click { focusAsker.ask() }
		)
	}
}


@Composable
fun ScrollInput(
	txt: mStr, 
	mod: Mod = Mod, 
	scroll: ScrollState = r_Scroll(), 
	h: Int = 150,  
	Do: DoStr = { txt.it = it }
){    
	var Field by r(TextField(txt.it))
	var done = r(no)
	var itIndex by r(0)

	var baseMod = Mod.maxW().h(h).Vscroll(scroll)
		
    OutlinedTextField(
        value = Field.it,
        onValueChange = {    
					
			Do(it.text)
			Field.text(txt.it, it.selection)
				
			FixedInputScroll(Field.it, done, scroll)
		},
        modifier = baseMod.mix(new = mod).onFocusChanged{
			if (!it.isFocused) done.it = no
		},
		placeholder = { Text("Start typing...") },
    )
}


@Composable
fun BigInput(txt: mStr, mod: Mod = Mod, Do: DoStr = { txt.it = it }){    
	var Field by r(TextFieldValue(txt.it))
	var done = r(no)
	var itIndex by r(0)

	var baseMod = Mod.maxW().wrapContentHeight()
		
    OutlinedTextField(
        value = Field,
        onValueChange = {    		
			Do(it.text)
			Field = TextFieldValue(
				text = txt.it,
				selection = it.selection
			)
			itIndex = it.selection.start
		},
        modifier = baseMod.mix(new = mod).onFocusChanged{
			if (!it.isFocused) done.it = no
		},
		placeholder = { Text("Start typing...") },
    )
}






@Composable
fun TinyInput(value: Any?, mod: Mod = Mod, maxLetters: Int = 4, isInt: Bool = yes, onAction: Do = {}, Do: DoStr = { _ -> }) {  
	var txt = toMStr(value)
	var Field by r(TextField(txt.it).gold().size(14.sp))
	
    BasicInput(
        Field,
        isInt = isInt, 
        mod = mod,
		onAction = onAction,
    ) { newF ->
		var it = newF.text.take(maxLetters)
		
		if (isInt) {
            val num = toInt(it)
			txt.it = "$num"
			newF.text(txt.it)
			Do("$num")
         } else {
            txt.it = it
			newF.text(txt.it)
            Do(it)
		}
    }
}
@Composable
fun TinyInput(value: mInt, mod: Mod = Mod, maxLetters: Int = 4, onAction: Do = {}, Do: DoInt = { _ -> }) {  
	var f1 by r(TextField(toStr(value.it)).gold().size(14.sp))
	
    BasicInput(
        f1,
        isInt = yes, 
		onAction = onAction,
        mod = mod,
    ) { Field ->
        var it = Field.text.take(maxLetters)
		
        val num = toInt(it)
		value.it = num
		Field.text("$num")
		Do(num)
    }
}




	




