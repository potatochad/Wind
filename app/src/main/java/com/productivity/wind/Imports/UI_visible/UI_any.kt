package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Data.*
import android.app.usage.UsageStatsManager
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import kotlin.collections.*
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.painterResource
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.rememberTextMeasurer
import android.graphics.drawable.Drawable
import android.content.pm.*
import java.util.*
import com.productivity.wind.R
import com.productivity.wind.Imports.UI_visible.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*
import java.time.*
import kotlin.concurrent.schedule
import java.io.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.*
import android.text.*
import android.text.style.*
import androidx.compose.ui.viewinterop.*
import android.widget.*
import android.text.method.*
import androidx.compose.ui.unit.*
import com.productivity.wind.Imports.*



@Composable 
fun RuleCard(
    txt: Str,
    ui: ui,
){
   LazyCard(corners = 8){
      Text(txt.size(17).bold())
      ui()
   }
}

@Composable
fun Ctext(
	text: Any,
	Do: Do={},
) {
	Text(
		text = UIStr(text).gold(),
		modifier = Mod.click(no) {
			Do()
		},
		maxLines = 1, 
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
fun EmptyBox(
	text: Str = "No Items",
	icon: icon = Icons.Default.Block,
	iconSize: Dp = 70.dp,
	textSize: Int = 18,
	color: Color = Color.Gray,
) {
	Column(
		Mod.maxS(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Icon(icon, iconSize, color = color)
	
		move(8)
		Text(text.size(textSize).color(color))
	}
}

@Composable
fun MenuHeader(
	title: Str = "Wind",
	iconRes: Int = R.drawable.baseline_radar_24,
	iconSize: Dp = 60.dp,
	iconTint: Color = Color(0xFFFFD700),
	titleSize: Int = 28,
	topPadding: Dp = 8.dp,
	bottomPadding: Dp = 20.dp,
	StartPaddingRemove: Int = 40,
) {
	val safeStartPadding = max(0.dp, (AppW+60.dp) / 4 - StartPaddingRemove.dp)

	Column(
		Modifier.space(start = safeStartPadding),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		move(h = topPadding)

		Icon(
			painter = painterResource(id = iconRes),
			contentDescription = "$title Icon",
			tint = iconTint,
			modifier = Mod.s(iconSize),
		)
		move(4)
		Text(title.size(titleSize))
        move(h = bottomPadding)
	}
}

@Composable
fun CheckRow(
	txt: Str="",
	isChecked: mBool,
	EndUI: ui_<Bool> = { _ -> }
) {
	LazzyRow{
		LazzyRow(Mod.click { isChecked.it = !isChecked.it }, 0) {
			Checkbox(
				checked = isChecked.it,
				onCheckedChange = { isChecked.it = it },
				colors = CheckboxDefaults.colors(
					checkedColor = Gold,
					uncheckedColor = Color.Gray,
					checkmarkColor = Color.White
				)
			)
			move(5)
			Text(txt)
			EndUI(isChecked.it)
		}
	}
}

@Composable
fun CheckCircle(
    index: Int,
    selectedIndex: m_<Int>,
) {
	Box(
		Mod.s(15)
	) {
		RadioButton(
			selected = selectedIndex.value == index,
			onClick = { set(selectedIndex, index) },
			colors = RadioButtonDefaults.colors(
				selectedColor = LightBlue,
				unselectedColor = Color.Gray
			),
			modifier = Mod.scale(0.85f)
		)
	}
	move(w=8)
}

@Composable
fun ProgressIcon(
	icon: Drawable?,              
	progress: Float,
) {
	val ringColor = ProgressColor(progress)

	Ring(
		color = ringColor,
		progress = progress,
		ContentPadding = -3,
	) {
		Box(contentAlignment = Alignment.Center) {
			LazyImage(icon)

			Canvas(
				modifier = Mod
					.matchParentSize()
					.space(4)
			) {
				drawArc(
					color = Color(0xFF171717),   // ~10% darker overlay
					startAngle = 0f,
					sweepAngle = 360f,
					useCenter = false,
					style = Stroke(width = 2.dp.toPx())
				)
			}
		}
	}
}

@Composable
fun Ring(
	color: Color,
	strokeWidth: Int = 3,
	progress: Float = 1f,
	ContentPadding: Int = 1,
	strokeCap: StrokeCap = StrokeCap.Butt,
	content: @Composable BoxScope.() -> Unit
) {
	Box(
		contentAlignment = Alignment.Center,
	) {
		Canvas(modifier = Mod.matchParentSize()) {
			val stroke = Stroke(width = strokeWidth.dp.toPx(), cap = strokeCap)
			val radius = size.minDimension / 2
			val topLeft = Offset(center.x - radius, center.y - radius)

			drawCircle(             
				color = Color.Black.copy(alpha = 0.1f), // faint dark circle
				radius = radius,                         // radius of the circle
			)
			drawArc(
				color = color,
				startAngle = -90f,
				sweepAngle = -360f * progress,
				useCenter = no,
				topLeft = topLeft,
				size = Size(radius * 2, radius * 2),
				style = stroke,
			)
		}
		Box(Mod.space(strokeWidth+ContentPadding)) {
			content()
		}
	}
}







