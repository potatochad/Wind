package com.productivity.wind.Screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import android.os.*
import android.webkit.*
import androidx.activity.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.*
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
        Browser()
    }



}

@Composable
fun Browser() {
    var url by remember { mutableStateOf("https://www.google.com") }
        LazzyRow() {
            TextField(
                value = url,
                onValueChange = { url = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter URL") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = { /* nothing here, handled in update */ }) {
                Text("Go")
            }
        }

        // WebView embedded inside Compose
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(url) // initial load
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            },
        )
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
