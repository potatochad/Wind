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
import androidx.compose.foundation.lazy.*

@Composable
fun Web() {
    val ctx = LocalContext.current

    // Create Gecko runtime
    val geckoRuntime = r { GeckoRuntime.create(ctx) }

    // Create and configure Gecko session
    val geckoSession = remember {
        GeckoSession().apply {
            open(geckoRuntime)
            loadUri("https://youtube.com")


            navigationDelegate = onlyAllowDomains(
                listOf("youtube.com")
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose { geckoSession.close() }
    }

    LazyScreen(
        title = { 
            LazyRow{
                item {
                    UI.Ctext("testing"){}
                }
            }
        }
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                GeckoView(ctx).apply { setSession(geckoSession) }
            }
        )
    }
}



fun onlyAllowDomains(allowedDomains: List<String>): GeckoSession.NavigationDelegate {
    return object : GeckoSession.NavigationDelegate {
        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoResult<GeckoSession.NavigationDelegate.LoadRequest>? {
            val url = request.uri ?: ""

            val isAllowed = allowedDomains.any { domain -> url.contains(domain) }

            return if (isAllowed) {
                // ✅ Allow loading
                GeckoResult.fromValue(request)
            } else {
                // ❌ Block loading
                null // returning null denies the navigation
            }
        }
    }
}
