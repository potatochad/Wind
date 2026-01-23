package com.productivity.wind.Imports.UI_visible

import android.annotation.SuppressLint
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import androidx.compose.foundation.text.selection.*
import com.productivity.wind.Imports.Data.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.activity.compose.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.gms.location.*
import android.os.*
import kotlin.math.*
import androidx.compose.ui.geometry.*
import androidx.compose.foundation.lazy.*
import com.productivity.wind.Imports.*


@Composable
	fun BigInput(txt: m_<Str>, scrollV: ScrollState = r_Scroll(), Do: DoStr={ txt.it = it }){
		val scroll = scrollV
		var Field by r_m(TextFieldValue(txt.it))
		var done = r_m(no)
		var itIndex by r_m(0)
		
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
    fun TinyInput(txt: Any, maxLetters: Int = 4, isInt: Bool =yes, w: Int = 60) {  
        val TxtState = txt as? MutableState<Any> ?: run {
            Vlog("expected mutable")
            return
        }

        BasicInput(
            "${TxtState.it}",
            isInt = isInt, 
            w=w,
        ) {
            val input = it.take(maxLetters)
            
            TxtState.it = if (TxtState.it is Int) {
                 if (input.isEmpty()) 0 else input.toInt()
            } else {
                input
            }
        }
	}




	




