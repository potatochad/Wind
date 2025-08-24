package com.productivity.wind.Screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import com.productivity.wind.*

import com.productivity.wind.Imports.*

@Composable
fun Web() {
    LazyScreen(
        title = {
                    Text("Web")

                    // WORK WEB
                    UI.End {
                    LazyIcon(
                        onClick = { goTo("WorkWeb") },
                        BigIcon = Icons.Default.Work,
                        BigIconColor = Color(0xFF4CAF50), // Green
                        OuterPadding = 0,
                    )

                    LazyIcon(
                        onClick = { goTo("FunWeb") },
                        BigIcon = Icons.Default.SentimentVerySatisfied,
                        BigIconColor = Color(0xFFFFC107), // Amber
                        OuterPadding = 0,
                    )
                    }


        },
    ) {
        UI.EmptyBox(text = "TO DO")

    }



}


@Composable
fun FunWeb() {
    LazyScreen(title = { Text("Fun Web") }) {
        UI.EmptyBox(text = "TO DO")

    }




}


@Composable
fun WorkWeb() {
    LazyScreen(title = {Text("Work Web")}) {
        UI.EmptyBox(text = "TO DO")
    }




}
