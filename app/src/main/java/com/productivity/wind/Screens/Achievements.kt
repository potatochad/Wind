package com.productivity.wind

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import com.productivity.wind.Imports.*
import androidx.compose.material.icons.outlined.*
import com.productivity.wind.Imports.Data.*


@Composable
fun Achievements() {
    LazyScreen("Achievements") {
            
        LazyItem(
            icon = Icons.Outlined.AdminPanelSettings,
            title = "Typed letters",
            endContent = {
                Text("${Bar.LettersTyped}")
                move(6)
            }
        )
    }
}



























