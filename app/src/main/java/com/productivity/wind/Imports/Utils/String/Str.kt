package com.productivity.wind.Imports.Utils.String

import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import kotlin.collections.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.rememberTextMeasurer
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.text.*


val Str.notEmpty: Bool
    get() = this.isNotEmpty()

val Str.empty: Bool
    get() = this.isEmpty()

val <T> Collection<T>.notEmpty: Bool
    get() = this.isNotEmpty()

val <T> Collection<T>.empty: Bool
    get() = this.isEmpty()


val Str.image: Bool
    get() = this.endsWith(".jpg", yes) ||
            this.endsWith(".jpeg", yes) ||
            this.endsWith(".png", yes) ||
            this.endsWith(".gif", yes) ||
            this.endsWith(".webp", yes) ||
            this.endsWith(".ico", yes)
    

val Str.size get() = length
fun Str.last(n: Int): Str = this.takeLast(n)
fun Str.fromTo(start: Int, end: Int = this.size) = this.substring(start, end)


fun Str(vararg parts: Any?): Str {
    return buildString {
        parts.forEach {
            when (it) {
                null, Unit -> {} // skip
                else -> {
                    val s = it.toString()
                    if (s.isNotEmpty()) append(s)
                }
            }
        }
    }
}



fun Str.hasAny(vararg x: Str) = x.any { this.contains(it) }
fun Str.hasAny(x: ListStr) = x.any { this.contains(it) }
fun Str.hasAll(vararg x: Str) = x.all { this.contains(it) }
fun Str.hasAll(x: ListStr) = x.all { this.contains(it) }
fun Str.has(x: Str) = this.contains(x)




fun Str.overFlow(x: Int): Str {
    return if (this.size > x) {
        this.take(x) + "..."
    } else {
        this
    }
}


// Remove one or more characters or substrings from a string
fun Str.remove(vararg targets: Str): Str {
    var result = this
    for (t in targets) {
        result = result.replace(t, "")
    }
    return result
}
fun Str.remove(x: Str): Str {
    return this.replace(x, "")
}

fun Regex.group(str: Str, group: Int): Str? =
	matchEntire(str)?.groupValues?.getOrNull(group)




//Example:"abc {one} xyz {two} end"
//Result: "one} xyz {two"
fun InsideBraces(str: Str): Str? {
    val start = str.indexOf('{')
    val end = str.lastIndexOf('}')

    if (start == -1 || end == -1 || start >= end)
        return null

    return str.fromTo(start + 1, end).trim()
}


// Example: a:1, b:{x:2,y:3}, c:[1,2,3]
//Result:
//a:1
//b:{x:2,y:3}
//c:[1,2,3]
fun SplitTopLevel(
    str: Str,
    split: Char = ',',
    deeper: ListStr = listOf("()", "{}", "[]")
): ListStr {

    var depth = 0
    var current = ""
    val result = mList<Str>()

    for (c in str) {
        when {
            deeper.any { it[0] == c } -> {
                depth++
                current += c
            }

            deeper.any { it[1] == c } -> {
                depth--
                current += c
            }

            c == split && depth == 0 -> {
                result += current.trim()
                current = ""
            }

            else -> current += c
        }
    }

    if (current.isNotBlank())
        result += current.trim()

    return result
}



fun Str.takeWords(n: Int): Str{
    return this
        .trim()
        .split(Regex("\\s+"))
        .take(n)
        .joinToString(" ")
}
fun Str.splitStr(action: DoStr) {
    Regex("\\S+|\\s+").findAll(this).forEach {
        action(it.value)
    }
}


@Composable
fun Str.wordsFit(
    maxWidthPx: Float,
    leaveEmptySpace: Bool = no,
): Str {
    val textMeasurer = rTextMeasurer()
    val style = LocalTextStyle.current
    val words = this.trim().split(Regex("\\s+"))

    var line = ""

    fun fits(text: Str): Bool {
        return textMeasurer.measure(
            text = UIStr(text),
            style = style
        ).size.width.toFloat() <= maxWidthPx
    }

    for (word in words) {
        val test = if (line.isEmpty()) word else "$line $word"
        if (fits(test)) {
            line = test
        } else {
            break
        }
    }

    return if (leaveEmptySpace) {
        // preserve spacing at the end of last visible word
        if (line.isNotEmpty()) "$line " else ""
    } else {
        line
    }
}



@Composable
fun rTextMeasurer(): TextMeasurer {
    return rememberTextMeasurer()
}
fun TextMeasurer.w(text: UIStr): Float {
    return measure(
        text = text,
        style = text.textStyle
    ).size.width.toFloat()
}
fun TextMeasurer.h(text: UIStr): Float {
    return measure(
        text = text,
        style = text.textStyle
    ).size.height.toFloat()
}
fun TextMeasurer.s(text: UIStr): IntSize {
    return measure(
        text = text,
        style = text.textStyle
    ).size
}



