package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.foundation.*
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.collections.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.lazy.*


var Scroll.it: Int
    get() = this.value
    set(value) {
        CoroutineScope(Dispatchers.Main).launch {
            this@it.scrollTo(value)
        }
    }
    
val LazyList.it: Float
    get() {
        val firstIndex = firstVisibleItemIndex
        val firstOffset = firstVisibleItemScrollOffset
        val itemHeight = layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
        return toF(firstIndex * itemHeight + firstOffset)
    }
    
val LazyList.size: Float
    get() {
        val items = layoutInfo.visibleItemsInfo
        if (items.isEmpty()) return 0f

        val averageItemHeight = items.sumOf { it.size }.toFloat() / items.size
        return layoutInfo.totalItemsCount * averageItemHeight
    }

typealias Scroll = ScrollState
typealias LazyList = LazyListState

@Composable
fun Mod.scroll(
    v: Bool = yes,
    h: Bool = yes,
    r_v: Scroll = Scroll(),
    r_h: Scroll = Scroll(),
): Mod {
    var m = this
    if (v) m = m.verticalScroll(r_v)
    if (h) m = m.horizontalScroll(r_h)
    return m
}

@Composable
fun Mod.Yscroll(r_v: Scroll = Scroll(), scroll: Bool = yes) = scroll(scroll, no, r_v)
@Composable
fun Mod.Xscroll(r_h: Scroll = Scroll(), scroll: Bool = yes) = scroll(no, scroll, r_h=r_h)

fun Scroll.toBottom() = wait{ scrollTo(maxValue) }

fun LazyList.toBottom() = wait{
    if (layoutInfo.totalItemsCount > 0) {
        scrollToItem(layoutInfo.totalItemsCount - 1)
    }
}

suspend fun Scroll.scroll(it: Any) = animateScrollBy(toF(it))
suspend fun LazyList.scroll(it: Any) = animateScrollBy(toF(it))

fun LazyList.goToLineCentered(lineIndex: Int) = wait {
    val layout = layoutInfo
    val visible = layout.visibleItemsInfo.size
    val target = (lineIndex - visible / 2).coerceAtLeast(0)
    scrollToItem(target)
}

fun LazyList.goToLine(lineIndex: Int) = wait {
    scrollToItem(lineIndex.coerceAtLeast(0))
}

fun Scroll.goTo(it: Any) = wait{ scrollTo(toInt(it)) }
fun LazyList.goTo(x: Any) = wait {
   scrollBy(toF(
      toF(x) - this.it
   ))
}

val Scroll.size: Int
    get() = maxValue

val Scroll.isMax: Bool
    get() = value >= maxValue

@Composable
fun Scroll() = rememberScrollState()
@Composable
fun r_Scroll() = rememberScrollState()
@Composable
fun LazyList() = rememberLazyListState()











