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
fun UrlConverter(input: String): String {
    return remember(input) {
        if (input.startsWith("http://") || input.startsWith("https://")) {
            input
        } else {
            "https://$input"
        }
    }
}
@Composable
fun Web() {
    var url = r { m("google.com") }
    val fullUrl = UrlConverter(url.value)

    val ctx = LocalContext.current

    val geckoRuntime = r { GeckoRuntime.create(ctx) }

    val geckoSession = r {
        GeckoSession().apply {
            open(geckoRuntime)
            loadUri(fullUrl) // load once initially
        }
    }

    DisposableEffect(Unit) {
        onDispose { geckoSession.close() }
    }

    LazyScreen(
        title = {
            UI.Cinput(what=url, WidthMax=150)
        }
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { ctx ->
                GeckoView(ctx).apply { setSession(geckoSession) }
            }
        )
    }
}

@Composable
fun Web2() {
    var url = r { m("google.com") }
    // Create Gecko runtime once
    val fullUrl = UrlConverter(url.value)

    // Show GeckoView in Compose
    LazyScreen(
        title = {
            UI.Cinput(what=url, WidthMax=150)
        },
    ) {
            val ctx = LocalContext.current
            val geckoRuntime = r { GeckoRuntime.create(ctx) }
            val geckoSession = r {
                GeckoSession().apply {
                    open(geckoRuntime)
                    loadUri(fullUrl)
                }
            }
        
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth(),
                factory = { ctx: Context ->
                    GeckoView(ctx).apply {
                    setSession(geckoSession)
                    }
                },
                update = { view ->
                    // If you want to reload when url changes
                    view.session?.loadUri(fullUrl)
                }
            )
    }
}








