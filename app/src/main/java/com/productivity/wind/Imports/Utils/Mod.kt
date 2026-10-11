package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*


fun Mod.space(s: Any): Mod = padding(toDp(s))
fun Mod.space(x: Any, y: Any): Mod = padding(toDp(x), toDp(y))
fun Mod.space(start: Any? = null, top: Any? = null, end: Any? = null, bottom: Any? = null): Mod = padding(toDp(start), toDp(top), toDp(end), toDp(bottom))


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

fun Mod.mix(new: Mod): Mod{
    return new.then(this)
}


//‼️ Keep them functions
fun Mod.transparent() = this.background(Color.Transparent)
fun Mod.black() = this.background(Color.Black)
fun Mod.white() = this.background(Color.White)
fun Mod.red() = this.background(Color.Red)
fun Mod.green() = this.background(Color.Green)
fun Mod.blue() = this.background(Color.Blue)
fun Mod.cardColor() = this.background(cardColor)

    


fun Mod.getW(onWidth: DoInt): Mod = this.then(
    Mod.onGloballyPositioned {
        onWidth(it.size.width)
    }
)
fun Mod.getH(onWidth: DoInt): Mod = this.then(
    Mod.onGloballyPositioned {
        onWidth(it.size.height)
    }
)





//THESE NAMES MUST BE: Modifier
//‼️CANT BE Mod
object CenterY_Tag : Modifier.Element
object CenterX_Tag : Modifier.Element
object Top_Tag : Modifier.Element
object Bottom_Tag : Modifier.Element
object Start_Tag : Modifier.Element
object End_Tag : Modifier.Element

inline fun <reified T : Modifier.Element> Mod.hasTag(): Bool =
    this.any { it is T }



val Mod.centerX: Mod get() = this.then(CenterX_Tag)
val Mod.centerY: Mod get() = this.then(CenterY_Tag)
val Mod.top: Mod get() = this.then(Top_Tag)
val Mod.bottom: Mod get() = this.then(Bottom_Tag)
val Mod.start: Mod get() = this.then(Start_Tag)
val Mod.end: Mod get() = this.then(End_Tag)


fun AutoHorizontalArrangement(mod: Mod): Arrangement.Horizontal {
    return when {
        mod.hasTag<CenterX_Tag>() -> Arrangement.Center
        mod.hasTag<Start_Tag>() -> Arrangement.Start
        mod.hasTag<End_Tag>() -> Arrangement.End
        else -> Arrangement.Start
    }
}

fun AutoVerticalAlignment(mod: Mod): Alignment.Vertical {
    return when {
        mod.hasTag<CenterY_Tag>() -> Alignment.CenterVertically
        mod.hasTag<Top_Tag>() -> Alignment.Top
        mod.hasTag<Bottom_Tag>() -> Alignment.Bottom
        else -> Alignment.CenterVertically
    }
}


fun AutoHorizontalAlignment(mod: Mod): Alignment.Horizontal {
    return when {
        mod.hasTag<CenterX_Tag>() -> Alignment.CenterHorizontally
        mod.hasTag<Start_Tag>() -> Alignment.Start
        mod.hasTag<End_Tag>() -> Alignment.End
        else -> Alignment.Start
    }
}

fun AutoVerticalArrangement(mod: Mod): Arrangement.Vertical {
    return when {
        mod.hasTag<CenterY_Tag>() -> Arrangement.Center
        mod.hasTag<Top_Tag>() -> Arrangement.Top
        mod.hasTag<Bottom_Tag>() -> Arrangement.Bottom
        else -> Arrangement.Top
    }
}













