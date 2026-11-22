//stuff that moves stuff into place

package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.String.*
import androidx.compose.ui.draw.*
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
import kotlinx.coroutines.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.productivity.wind.Imports.Utils.*
import androidx.activity.compose.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlin.math.*
import androidx.compose.ui.zIndex

@Composable
fun Center(ui: uiColumn){
	LazzyColumn(Mod.maxS().center) { ui() }
}


@Composable
fun move(s: Any = 0, w: Any = 0, h: Any = 0) {
	val sDp = toDp(s)
	val wDp = toDp(w)
	val hDp = toDp(h)

	Spacer(
		modifier = if (sDp > 0.dp) {
			Mod.s(sDp)  // uniform size
		} else {
			Mod.w(wDp).h(hDp)
		}
	)
}


@Composable
fun End(mod: Mod = Mod, ui: ui) {
	Row(
		mod.maxW(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.End
	){
		ui()
		move(10)
	}
}
@Composable
fun RowScope.SafeEnd(ui: uiRow) {
    Spacer(modifier = Mod.weight(1f))
    ui()
}
@Composable
fun RowScope.SafeMaxW(ui: ui) {
    Box(Mod.weight(1f)) {
        ui()
    }
}
@Composable
fun RowScope.Weight(ui: uiRow){
	Row(Mod.weight(1f)) {
		ui()
	}
}






