@file:OptIn(ExperimentalLayoutApi::class)

package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.String.*
import androidx.compose.ui.text.input.*
import androidx.compose.runtime.*
import android.content.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.*
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.composed



object Keyboard {
    var h by mState(0)
        private set
    var isOpen by mState(no)
        private set
    

    @Composable
    fun track() {
        val density = LocalDensity.current

        h = WindowInsets.ime.getBottom(density)
        isOpen = WindowInsets.isImeVisible
    }
    
}


fun View.showKeyboard() {
    this.requestFocus()

    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager

    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


//focus manager DONT PUT in box
object AppFocus {
    private var clear by mState(0)

    fun clear() {
        clear++
    }

    @Composable
    fun track() {
        val focusManager = LocalFocusManager.current

        LaunchedEffect(clear) {
            if (clear > 0) {
                focusManager.clearFocus()
            }
        }
    }
}

fun Mod.canFocus(): Mod = composed {
    val fr = r { FocusRequester() }

    this.focusRequester(fr)
}



class InputField(
    a: Str = "",
    private val UIStrOn: Bool = yes,
) {
    var it by mState(TextFieldValue(a))
    var UIStr by mState(UIStr(a))
    
    val text: Str
        get() = it.text
    val selection: TextRange
        get() = it.selection
    

    fun it(x: TextFieldValue): InputField {
        it = x
        return this
    }
    fun text(str: Str): InputField {
        it = it.copy(
            text = str,
        )
        if (UIStrOn) { UIStr = UIStr.keepOneStyle(str) }//super big lag, 
        return this
    }
    fun text(str: Str, select: TextRange): InputField {
        it = it.copy(
            text = str,
            selection = select
        )
        if (UIStrOn) { UIStr = UIStr.keepOneStyle(str) }
        return this
    }
    fun cursor(pos: Int): InputField {
        it = it.copy(
            selection = TextRange(pos, pos)
        )
        return this
    }
    fun select(pos1: Int, pos2: Int): InputField {
        it = it.copy(
            selection = TextRange(pos1, pos2)
        )
        return this
    }

    fun bold(): InputField{ UIStr.bold(); return this }   
    fun gold(): InputField{ UIStr.gold(); return this }
    fun green(): InputField{ UIStr.green(); return this }
    fun red(): InputField{ UIStr.red(); return this }
    fun white(): InputField{ UIStr.white(); return this }
    fun black(): InputField{ UIStr.black(); return this }
    fun darkGray(): InputField{ UIStr.darkGray(); return this }
    fun gray(): InputField{ UIStr.gray(); return this }
    fun size(x: Int): InputField{ UIStr.size(x); return this }
    fun size(x: Float): InputField{ UIStr.size(x); return this }
    fun size(x: TextUnit): InputField{ UIStr.size(x); return this }

}


fun FixedInputScroll(
    text: TextFieldValue,
    done: mState_<Bool>,
    scroll: ScrollState
) {
	var cursorPos = text.selection.start
    if (text.text.isNotEmpty() && !done.it) {
		done.it = yes

        val ratio = toF(cursorPos) / toF(text.text.size)
        val max = toF(scroll.maxValue)
        val scrollTo = (max * ratio)

        scroll.goTo(scrollTo)
    }

	if (cursorPos == text.text.size) {
		scroll.toBottom()
	}
}






