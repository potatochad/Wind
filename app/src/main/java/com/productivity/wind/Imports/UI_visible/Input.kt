package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.String.*
import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.focus.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.text.*


@Suppress("UNCHECKED_CAST")

@Composable
fun BasicInput(
    Field: InputField,
    isInt: Bool = no,
	mod: Mod = Mod,
	onLayout: Do_<TextLayoutResult> = {},
	onAction: Do = {},
	oneLine: Bool = yes,
    Do: Do_<InputField> = {},
) {
	val baseMod = Mod.w(70).h(32).space(8, 4).background(inputColor, shape = RoundedCornerShape(4.dp)).space(start = 3, top = 5)             
	
	BasicTextField(
		value = Field.it,
		onValueChange = { Do(Field.it(it)) },
		onTextLayout = { onLayout(it) },
		singleLine = oneLine, 
		keyboardOptions = KeyboardOptions(
			keyboardType = if (isInt) KeyboardType.Number else KeyboardType.Text,
			imeAction = ImeAction.Done
		),
		visualTransformation = object : VisualTransformation {
			override fun filter(text: UIStr): TransformedText {
				return TransformedText(
					Field.UIStr.gold().size(14.sp),
					OffsetMapping.Identity
				)
			}
		},
		keyboardActions = KeyboardActions(
			onDone = {
				AppFocus.clear() 
				onAction()
			}
		),
		modifier = baseMod.mix(new = mod).canFocus()
	)
}



@Composable
fun ScrollInput(
	txt: mStateStr, 
	mod: Mod = Mod, 
	scroll: ScrollState = Scroll(), 
	h: Int = 150,  
	Do: DoStr = { txt.it = it }
){    
	var Field by r(InputField(txt.it, no))
	var done = r(no)
	var itIndex by r(0)

	var baseMod = Mod.maxW().h(h).Yscroll(scroll)
		
    OutlinedTextField(
        value = Field.it,
        onValueChange = {    
					
			Do(it.text)
			Field.it(it)
				
			FixedInputScroll(Field.it, done, scroll)
		},
        modifier = baseMod.mix(new = mod).onFocusChanged{
			if (!it.isFocused) done.it = no
		},
		placeholder = { Text("Start typing...") },
    )
}


@Composable
fun BigInput(txt: mStateStr, mod: Mod = Mod, Do: DoStr = { txt.it = it }){    
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
	var txt = toMStateStr(value)
	var Field by r(InputField(txt.it).gold().size(14.sp))
	
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
fun TinyInput(value: mStateInt, mod: Mod = Mod, maxLetters: Int = 4, onAction: Do = {}, Do: DoInt = { _ -> }) {  
	var f1 by r(InputField(toStr(value.it)).gold().size(14.sp))
	
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



@Composable
fun RowScope.LongInput(value: Any?, mod: Mod = Mod, maxLetters: Int = 40, onAction: Do = {}, Do: DoStr = { _ -> }) {  
	var txt = toMStateStr(value)
	var Field by r(InputField(txt.it).white().size(19.sp))
	val baseMod = Mod.weight(1f).h(50).space(8, 4).background(inputColor, shape = RoundedCornerShape(4.dp))          

	
    BasicInput(
        Field,
        isInt = no, 
        mod = baseMod.mix(new = mod), // Mod.h(50).weight(1f).mix(new = mod),
		onAction = onAction,
    ) { newF ->
		var it = newF.text.take(maxLetters)
		
        txt.it = it
		newF.text(txt.it)
        Do(it)
    }
}



	
