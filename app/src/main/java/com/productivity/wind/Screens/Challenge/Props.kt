package com.productivity.wind.Screens.Challenge
  
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
import com.productivity.wind.R
import com.productivity.wind.Imports.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import androidx.compose.foundation.text.selection.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.geometry.*
import com.productivity.wind.Imports.UI_visible.*


/*
!!!
Prop functions are strictly not allow to be imported up a folder

*/

data class LineInfo(
    val text: String,        // the full text of the line
    var typedChars: Int = 0 // how many characters are typed correctly
) {
    val remainingChars: Int
        get() = text.length - typedChars

    val isDone: Bool
        get() = typedChars >= text.length

    fun updateTyped(globalTyped: Int, startIndex: Int): LineInfo {
        // globalTyped = how many characters typed in the whole text
        val newTyped = (globalTyped - startIndex).coerceIn(0, text.length)
        return copy(typedChars = newTyped)
    }
}

fun mapLinesToInfo(lines: List<Str>, typedSoFar: Int = 0): List<LineInfo> {
    var sum = 0
    return lines.map { line ->
        val lineInfo = LineInfo(line).updateTyped(typedSoFar, sum)
        sum += line.length
        lineInfo
    }
}







