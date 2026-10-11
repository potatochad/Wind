package com.productivity.wind.Imports.Utils.String

import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import kotlin.collections.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.text.style.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.text.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.text.intl.LocaleList



typealias UIStr = AnnotatedString
typealias UIStrBuilder = AnnotatedString.Builder
typealias StrStyle = SpanStyle



val UIStr.size get() = this.text.size
fun UIStr.fromTo(start: Int, end: Int = this.size) = this.text.fromTo(start, end)



fun makeUIStr(Do: UIStrBuilder.() -> Unit): UIStr = buildAnnotatedString(Do)
fun UIStrBuilder.add(text: Char) = append(text)
fun UIStrBuilder.add(text: Str) = append(text)
fun UIStrBuilder.add(text: UIStr) = append(text)

fun UIStr(vararg parts: Any): UIStr = makeUIStr {
    parts.forEach { add(toUIStr(it)) }
}


fun UIStr.keepOneStyle(newText: Str): UIStr {
    val style = this.style
    return newText.strStyle(style)
}


val UIStr.style: StrStyle
    get() = spanStyles.firstOrNull()?.item ?: StrStyle()
val Str.style: StrStyle
    get() = toUIStr(this).spanStyles.firstOrNull()?.item ?: StrStyle()

fun UIStr.strStyle(x: StrStyle): UIStr = makeUIStr {
    pushStyle(x)
    add(this@strStyle.text)
    pop()
}
fun Str.strStyle(x: StrStyle): UIStr = makeUIStr {
    pushStyle(x)
    add(this@strStyle)
    pop()
}

    
val UIStr.textStyle: TextStyle
    get() {
        val s = this.style

        return TextStyle(
            color = s.color,
            fontSize = s.fontSize,
            fontWeight = s.fontWeight,
            fontStyle = s.fontStyle,
            fontSynthesis = s.fontSynthesis,
            fontFamily = s.fontFamily,
            fontFeatureSettings = s.fontFeatureSettings,
            letterSpacing = s.letterSpacing,
            baselineShift = s.baselineShift,
            textGeometricTransform = s.textGeometricTransform,
            localeList = s.localeList,
            background = s.background,
            textDecoration = s.textDecoration,
            shadow = s.shadow,
            drawStyle = s.drawStyle,
        )
    }
        



fun StrStyle.fontSize(x: TextUnit) = copy(fontSize = x)

fun StrStyle.color(x: Color) = copy(color = x)
fun StrStyle.green() = color(green)
fun StrStyle.red() = color(red)
fun StrStyle.gold() = color(gold)
fun StrStyle.gray() = color(gray)
fun StrStyle.darkGray() = color(darkGray)
fun StrStyle.black() = color(black)
fun StrStyle.white() = color(white)

fun StrStyle.thin() = copy(fontWeight = FontWeight.Thin)
fun StrStyle.light() = copy(fontWeight = FontWeight.Light)
fun StrStyle.medium() = copy(fontWeight = FontWeight.Medium)
fun StrStyle.semiBold() = copy(fontWeight = FontWeight.SemiBold)
fun StrStyle.extraBold() = copy(fontWeight = FontWeight.ExtraBold)
fun StrStyle.normal() = copy(fontWeight = FontWeight.Normal)
fun StrStyle.bold() = copy(fontWeight = FontWeight.Bold)

fun StrStyle.underline() = copy(textDecoration = TextDecoration.Underline)
fun StrStyle.strike() = copy(textDecoration = TextDecoration.LineThrough)
fun StrStyle.none() = copy(textDecoration = TextDecoration.None)

fun StrStyle.background(x: Color) = copy(background = x)
fun StrStyle.brush(x: Brush?) = copy(brush = x)
fun StrStyle.shadow(x: Shadow?) = copy(shadow = x)
fun StrStyle.draw(x: DrawStyle?) = copy(drawStyle = x)

fun StrStyle.italic() = copy(fontStyle = FontStyle.Italic)
fun StrStyle.font(x: FontFamily?) = copy(fontFamily = x)
fun StrStyle.letterSpacing(x: TextUnit) = copy(letterSpacing = x)
fun StrStyle.baselineShift(x: BaselineShift?) = copy(baselineShift = x)
fun StrStyle.fontFeatureSettings(x: String?) = copy(fontFeatureSettings = x)
fun StrStyle.synthesis(x: FontSynthesis?) = copy(fontSynthesis = x)

fun StrStyle.geometric(x: TextGeometricTransform?) = copy(textGeometricTransform = x)

fun StrStyle.locale(x: LocaleList?) = copy(localeList = x)
fun StrStyle.platform(x: PlatformSpanStyle?) = copy(platformStyle = x)




private fun Str.sty(Do: StrStyle.() -> StrStyle = { this }): UIStr = this.strStyle(this.style.Do())
private fun UIStr.sty(Do: StrStyle.() -> StrStyle = { this }): UIStr = this.strStyle(this.style.Do())      



fun Str.size(x: Int) = sty { fontSize(x.sp) }
fun Str.size(x: Float) = sty { fontSize(x.sp) }
fun Str.size(x: TextUnit) = sty { fontSize(x) }

fun Str.color(x: Color) = sty { color(x) }
fun Str.green() = sty { green() }
fun Str.red() = sty { red() }
fun Str.gold() = sty { gold() }
fun Str.gray() = sty { gray() }
fun Str.darkGray() = sty { darkGray() }
fun Str.black() = sty { black() }
fun Str.white() = sty { white() }

fun Str.thin() = sty { thin() }
fun Str.light() = sty { light() }
fun Str.medium() = sty { medium() }
fun Str.semiBold() = sty { semiBold() }
fun Str.extraBold() = sty { extraBold() }
fun Str.normal() = sty { normal() }
fun Str.bold() = sty { bold() }

fun Str.underline() = sty { underline() }
fun Str.strike() = sty { strike() }
fun Str.none() = sty { none() }

fun Str.background(x: Color) = sty { background(x) }
fun Str.brush(x: Brush?) = sty { brush(x) }
fun Str.shadow(x: Shadow?) = sty { shadow(x) }
fun Str.draw(x: DrawStyle?) = sty { draw(x) }

fun Str.italic() = sty { italic() }
fun Str.font(x: FontFamily?) = sty { font(x) }
fun Str.letterSpacing(x: TextUnit) = sty { letterSpacing(x) }
fun Str.baselineShift(x: BaselineShift?) = sty { baselineShift(x) }
fun Str.fontFeatureSettings(x: String?) = sty { fontFeatureSettings(x) }
fun Str.synthesis(x: FontSynthesis?) = sty { synthesis(x) }

fun Str.geometric(x: TextGeometricTransform?) = sty { geometric(x) }

fun Str.locale(x: LocaleList?) = sty { locale(x) }
fun Str.platform(x: PlatformSpanStyle?) = sty { platform(x) }



fun UIStr.size(x: Int) = sty { fontSize(x.sp) }
fun UIStr.size(x: Float) = sty { fontSize(x.sp) }
fun UIStr.size(x: TextUnit) = sty { fontSize(x) }

fun UIStr.color(x: Color) = sty { color(x) }
fun UIStr.green() = sty { green() }
fun UIStr.red() = sty { red() }
fun UIStr.gold() = sty { gold() }
fun UIStr.gray() = sty { gray() }
fun UIStr.darkGray() = sty { darkGray() }
fun UIStr.black() = sty { black() }
fun UIStr.white() = sty { white() }

fun UIStr.thin() = sty { thin() }
fun UIStr.light() = sty { light() }
fun UIStr.medium() = sty { medium() }
fun UIStr.semiBold() = sty { semiBold() }
fun UIStr.extraBold() = sty { extraBold() }
fun UIStr.normal() = sty { normal() }
fun UIStr.bold() = sty { bold() }

fun UIStr.underline() = sty { underline() }
fun UIStr.strike() = sty { strike() }
fun UIStr.none() = sty { none() }

fun UIStr.background(x: Color) = sty { background(x) }
fun UIStr.brush(x: Brush?) = sty { brush(x) }
fun UIStr.shadow(x: Shadow?) = sty { shadow(x) }
fun UIStr.draw(x: DrawStyle?) = sty { draw(x) }

fun UIStr.italic() = sty { italic() }
fun UIStr.font(x: FontFamily?) = sty { font(x) }
fun UIStr.letterSpacing(x: TextUnit) = sty { letterSpacing(x) }
fun UIStr.baselineShift(x: BaselineShift?) = sty { baselineShift(x) }
fun UIStr.fontFeatureSettings(x: String?) = sty { fontFeatureSettings(x) }
fun UIStr.synthesis(x: FontSynthesis?) = sty { synthesis(x) }

fun UIStr.geometric(x: TextGeometricTransform?) = sty { geometric(x) }

fun UIStr.locale(x: LocaleList?) = sty { locale(x) }
fun UIStr.platform(x: PlatformSpanStyle?) = sty { platform(x) }








