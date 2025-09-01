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
import org.mozilla.geckoview.*
import android.content.*

@Composable
fun Web() {
    var url = r { m("https://www.google.com") }
    // Create Gecko runtime once

    // Show GeckoView in Compose
    LazyScreen(
        title = {
            UI.Cinput(what=url, WidthMax=150)
        },
    ) {
        val ctx = androidx.compose.ui.platform.LocalContext.current
        val geckoRuntime = r { GeckoRuntime.create(ctx) }
        val geckoSession = r {
            GeckoSession().apply {
                open(geckoRuntime)
                loadUri(url.value)
            }
        }
        
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp), 
            factory = { ctx: Context ->
                GeckoView(ctx).apply {
                    setSession(geckoSession)
                }
            },
            update = { view ->
                // If you want to reload when url changes
                view.session?.loadUri(url.value)
            }
        )
    }
}








