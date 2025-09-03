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
import androidx.compose.ui.platform.*


@Composable
fun Web() {
    val ctx = LocalContext.current

    // Create Gecko runtime
    val geckoRuntime = r { GeckoRuntime.create(ctx) }

    // Create and configure Gecko session
    val geckoSession = r {
        GeckoSession().apply {
            open(geckoRuntime)
            loadUri(youtube.com) // initial load

            // Set up YouTube filter
            setYouTubeFilter()
        }
    }

    DisposableEffect(Unit) {
        onDispose { geckoSession.close() }
    }

    LazyScreen(
        title = { }
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                GeckoView(ctx).apply { setSession(geckoSession) }
            }
        )
    }
}

// Extension function to inject YouTube filter into a GeckoSession
