package com.productivity.wind.Imports.Utils.String

import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.*
import kotlin.collections.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import com.productivity.wind.Imports.Utils.*


@Composable
fun Any.toLines(maxWidthPx: Float): List<UIStr> {
    val measure = rTextMeasurer()
    val texttstyle = LocalTextStyle.current
    val style = texttstyle.toSpanStyle()
    val str = toStr(this)

    fun measure2(x: Str) = measure.w(UIStr(x).strStyle(style))

    val lines = remember(str) {
        val lines2 = mutableListOf<UIStr>()
        var line = ""
        
        str.splitStr {
            val future = line + it
            // future.blog("future")

            val isFutureTooBig = measure2(future) > maxWidthPx - 100    
            // log("${measure2(future)} > ${maxWidthPx-100}= [$isFutureTooBig]")   
            
            if (line.isNotEmpty() && isFutureTooBig) {
                lines2.add(UIStr(line))
                line = it
            } 
            else {
                line += it
            }
        }
        if (line.notEmpty) lines2.add(UIStr(line))
        lines2
    }
    
    return lines
}




fun ListStr.takeStr(x: Int): Str {
    var result = ""
    var remaining = x

    for (s in this) {
        if (remaining <= 0) break

        if (result.notEmpty) {
            result += " "
            remaining -= 1
            if (remaining <= 0) break
        }

        // take min of string length or remaining chars
        val take = minOf(s.length, remaining)
        result += s.take(take)
        remaining -= take
    }

    return result
}

fun ListStr.getLine(lineIndex: Int): Str {
    return if (lineIndex in indices) this[lineIndex] else ""
}

fun ListStr.lineByChar(charIndex: Int): String {
    var runningIndex = 0
    for (line in this) {
        val nextIndex = runningIndex + line.length
        if (charIndex in runningIndex until nextIndex) {
            return line
        }
        runningIndex = nextIndex
    }
    return ""
}

fun ListStr.lineIndexByChar(charIndex: Int): Int {
    var runningIndex = 0

    for ((i, line) in this.withIndex()) {
        val nextIndex = runningIndex + line.length

        if (charIndex in runningIndex until nextIndex) {
            return i
        }

        runningIndex = nextIndex
    }

    return -1
}





@Composable
fun ListStr.rMaxWidth(style: StrStyle): Dp {
	val density = rDensity()
	val measure = rTextMeasurer()
	val newLogs = this.rNewItems()
	
	var maxW by r(100.dp)

	var font = if (style.fontFamily == FontFamily.Monospace) FontFamily.Monospace else null
	val monoW = r(style) {
		font?.let { measure.w("x".strStyle(style)) }
	}
	this.onDelete{
		maxW = 100.dp
	}
    
	RunOnce(newLogs) {
		var tempPx = newLogs.max { line -> 
			if (monoW == null) measure.w(line.strStyle(style))         
			else monoW * line.size
		}
		var temp = toDp(tempPx, density)
		
		if (temp > maxW) maxW = temp
    }
    return maxW
}



















