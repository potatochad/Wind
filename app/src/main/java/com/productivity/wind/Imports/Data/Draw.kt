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



fun DrawScope.drawPin(
    circleRadius: Float = 14f,
    triangleWidth: Float = 24f,
    triangleHeight: Float = 16f,
    color: Color = Color.Black
) {
    // circle on top
 /*
    drawCircle(
        color = color,
        radius = circleRadius,
        center = Offset(size.width / 2f, circleRadius)
    )
    */

    drawArc(
    color = color,
    startAngle = -90f,      // top of circle
    sweepAngle = 288f,      // 80% of 360°
    useCenter = false,      // hollow like progress
    topLeft = Offset(
        size.width / 2f - circleRadius,
        0f
    ),
    size = androidx.compose.ui.geometry.Size(circleRadius * 2, circleRadius * 2),
    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f) // optional thickness
)


    // triangle below
    drawTriangle(
        x = (size.width - triangleWidth) / 2f,
        y = circleRadius, // just below circle
        width = triangleWidth,
        height = triangleHeight,
        color = color
    )
}








/*
val context = LocalContext.current
val bitmap = r {
    val drawable = AppCompatResources.getDrawable(context, R.drawable.incognito)!!.mutate()

    // Tint it white
    drawable.setTint(Color.White.toArgb())

    // Desired size in pixels
    val width = 90
    val height = 90

    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    drawable.setBounds(0, 0, width, height)
    drawable.draw(canvas)
    bmp
}

Marker(
    state = rememberMarkerState(position = center),
    icon = BitmapDescriptorFactory.fromBitmap(bitmap),
    title = "Incognito Spot"
)
*/

