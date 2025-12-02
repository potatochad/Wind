package com.productivity.wind

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import com.productivity.wind.Imports.*
import androidx.compose.material.icons.outlined.*
import com.productivity.wind.Imports.Data.Bar


@Composable
fun Achievements() {
    LazyScreen(top = { Text("Achievements") }) {
            
        LazyItem(
            icon = Icons.Outlined.AdminPanelSettings,
            title = "Typed letters",
            endContent = {
                    Text("${Bar.TotalTypedLetters}")
               }
            )
            
    }
}



























