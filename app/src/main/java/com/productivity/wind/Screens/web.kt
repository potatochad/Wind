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
import org.mozilla.geckoview.AllowOrDeny

@Composable
fun Web() {
    val ctx = LocalContext.current

    // ✅ Keep runtime & session across recompositions
    val geckoRuntime = remember { GeckoRuntime.create(ctx) }
    val geckoSession = remember { GeckoSession() }

    DisposableEffect(Unit) {
        geckoSession.open(geckoRuntime)
        geckoSession.navigationDelegate = onlyAllowDomains(listOf("youtube.com"))
        geckoSession.loadUri("https://youtube.com")

        onDispose { geckoSession.close() }
    }

    LazyScreen(
        title = { 
            LazyRow {
                item {
                    UI.Ctext("URLS (click)") { }
                }
            }
        }
    ) {
        // ✅ Give GeckoView real size
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),   // instead of just fillMaxSize()
            factory = { ctx ->
                GeckoView(ctx).apply { 
                    setSession(geckoSession) 
                }
            }
        )
    }
}

fun onlyAllowDomains(allowedDomains: List<String>): GeckoSession.NavigationDelegate {
    return object : GeckoSession.NavigationDelegate {
        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoResult<AllowOrDeny>? {   // ✅ FIXED
            val url = request.uri ?: return GeckoResult.fromValue(AllowOrDeny.DENY)

            val isAllowed = allowedDomains.any { domain ->
                url.contains(domain, ignoreCase = true)
            }

            return GeckoResult.fromValue(
                if (isAllowed) AllowOrDeny.ALLOW else AllowOrDeny.DENY
            )
        }
    }
}
