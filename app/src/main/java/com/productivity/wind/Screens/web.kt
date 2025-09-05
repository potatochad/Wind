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

typealias ManageTab = GeckoSession.NavigationDelegate
typealias Tab = GeckoSession


@Composable
fun Web() {
    val ctx = LocalContext.current
    val Web = r { GeckoRuntime.create(ctx) }
    val Tab = r { GeckoSession() }

    DisposableEffect(Unit) {
        Tab.open(Web)
        Tab.ManageTab = onlyAllowDomains(listOf("youtube.com"))
        Tab.loadUri("https://youtube.com")

        onDispose { Tab.close() }
    }

    LazyScreen(
        title = { 
            LazyRow {
                item {
                    UI.Ctext("URLS (click)") { }
                }
            }
        },
        Scrollable = false,
        DividerPadding = false,
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
            factory = { ctx ->
                GeckoView(ctx).apply { 
                    setSession(Tab) 
                }
            }
        )
    }
}


fun Tab.HideYoutubeRecommendations() {
    this.NavigationDelegate = object : ManageTab {
        override fun onLoadRequest(
            request: GeckoSession.LoadRequest,
            callback: GeckoSession.LoadRequestCallback
        ) {
            callback.proceed() // always allow page load

            // Inject JS to hide recommendations
            this@HideYoutubeRecommendations.loadUri(
                "javascript:(function(){" +
                "setInterval(function(){" +
                "var r=document.getElementById('related');" +
                "if(r){r.style.display='none';}" +
                "}, 1000);" +
                "})()"
            )
        }
    }
}


//no clue if WORKS????
fun onlyAllowDomains(allowedDomains: List<String>): Tab.ManageTab {
    return object : Tab.ManageTab {
        override fun onLoadRequest(
            session: Tab,
            ression.ManageTab.LoadRequest
        ): GeckoResult<AllowOrDeny>? { 
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
