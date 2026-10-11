package com.productivity.wind.Imports.UI_visible

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.*import androidx.compose.material.icons.filled.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*


fun DrawScope.drawTriangle(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    color: Color = black
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
				.background(gold, CircleShape)
				.border(3.dp, white, CircleShape),
			contentAlignment = Alignment.Center
		) {
			Icon(Icons.Default.VisibilityOff)
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
			
			drawPath(path, gold)
			
			// Draw left border
			drawLine(
				color = white,
				start = Offset(0f, 0f),
				end = Offset(size.width / 2f, size.height),
				strokeWidth = 9.3f
			)

			// Draw right border
			drawLine(
				color = white,
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
    trackColor: Color = gray,
    fillColor: Color = gold,
    modifier: Mod = Mod
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




