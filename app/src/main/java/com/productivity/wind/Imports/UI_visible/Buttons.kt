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
fun Ctext(
	text: Any,
	mod: Mod = Mod,
	animate: Bool = no,
	selected: Bool = yes,
	Do: Do={},
) {
	Text(
		text = if (selected) { UIStr(text).gold() } else { UIStr(text).gray() },
		modifier = mod.click(animate) {
			Do()
		},
		maxLines = 1, 
	)
}


