package com.productivity.wind.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = darkColorScheme(
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    primary = Color.Black,
    onPrimary = Color.White
)

private val BlueTextSelectionColors = TextSelectionColors(
    handleColor = Color.Blue,                 // cursor handle
    backgroundColor = Color.Blue.copy(alpha = 0.4f)  // selection highlight
)

@Composable
fun WindTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides BlueTextSelectionColors
    ) {
        MaterialTheme(
            colorScheme = AppColorScheme,
            content = content
        )
    }
}
