package com.productivity.wind.Imports.Data
 
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.OutlinedTextField
import com.productivity.wind.*
import androidx.navigation.NavGraphBuilder
import com.productivity.wind.Imports.*
import androidx.compose.ui.platform.*
import kotlinx.coroutines.*
import android.webkit.*
import com.productivity.wind.Imports.Data.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import android.content.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.focus.*
import android.graphics.drawable.*
import android.content.pm.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.relocation.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.gms.location.*
import android.os.*
import kotlin.math.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.input.pointer.*
import android.graphics.Canvas
import android.graphics.Bitmap
import androidx.appcompat.content.res.*
import androidx.compose.ui.platform.*
import com.productivity.wind.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.drawscope.*




fun DrawScope.drawTriangle(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    color: Color = Color.Black
) {
    drawPath(
        Path().apply {
            moveTo(x + width / 2f, y + height) // bottom center
            lineTo(x, y)                       // top left
            lineTo(x + width, y)               // top right
            close()
        },
        color
    )
}




@Composable
fun drawPin(
	
){
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			Mod.s(50).move(h = 10)
				.background(Gold, CircleShape)
				.border(3.dp, Color.White, CircleShape),
			contentAlignment = Alignment.Center
		) {
			Icon(
				imageVector = Icons.Default.VisibilityOff,
				contentDescription = null,
				tint = Color.White,
				modifier = Mod.s(24)
			)
		}

		// Triangle (pin tip)
		Canvas(Mod.w(36).h(24)) {
			// Draw gold filled triangle
			val path = Path().apply {
				moveTo(size.width / 2f, size.height) // bottom center
				lineTo(0f, 0f)                        // top left
				lineTo(size.width, 0f)                // top right
				close()
			}
			
			drawPath(path, Gold)
			
			// Draw left border
			drawLine(
				color = Color.White,
				start = Offset(0f, 0f),
				end = Offset(size.width / 2f, size.height),
				strokeWidth = 9.3f
			)

			// Draw right border
			drawLine(
				color = Color.White,
				start = Offset(size.width, 0f),
				end = Offset(size.width / 2f, size.height),
				strokeWidth = 9.3f
			)
		}
	}
}



@Composable
fun drawSlider(
    sliderPos: Float,
    thumbSize: Float,
    trackColor: Color = Color.Gray,
    fillColor: Color = Gold,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxWidth()) {
        val centerY = size.height / 2

        // Track
        drawLine(
            color = trackColor,
            start = Offset(0f, centerY),
            end = Offset(size.width, centerY),
            strokeWidth = 4f
        )

        // Filled portion
        drawLine(
            color = fillColor,
            start = Offset(0f, centerY),
            end = Offset(size.width * sliderPos, centerY),
            strokeWidth = 4f
        )

        // Thumb
        drawCircle(
            color = fillColor,
            radius = thumbSize,
            center = Offset(size.width * sliderPos, centerY)
        )
    }
}
