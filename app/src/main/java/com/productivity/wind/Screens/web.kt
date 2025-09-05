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
import org.mozilla.geckoview.*

typealias Tab = GeckoSession
typealias ManageTab = GeckoSession.NavigationDelegate


var Tab.ManageTab: ManageTab?
    get() = this.navigationDelegate
    set(value) {
        this.navigationDelegate = value
    }



fun GeckoSession.HideYoutubeRecommendations() {
    this.navigationDelegate = object : GeckoSession.NavigationDelegate {

        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoResult<AllowOrDeny> {
            return GeckoResult.fromValue(AllowOrDeny.ALLOW)
        }

        override fun onLoad(uri: Uri) {
            val url = uri.toString()
            if (url.contains("youtube.com/watch")) {
                // JS runs every 1 second to hide recommendations
                val js = """
                    setInterval(() => {
                        const related = document.getElementById('related');
                        if (related) related.style.display = 'none';
                    }, 1000);
                """.trimIndent()

                this@HideYoutubeRecommendations.loadUri("javascript:$js")
            }
        }
    }
}

fun onlyAllowDomains(allowedDomains: List<String>): GeckoSession.NavigationDelegate {
    return object : GeckoSession.NavigationDelegate {
        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoResult<AllowOrDeny> {
            val url = request.uri.toString() // uri is a Uri object, convert to string

            val isAllowed = allowedDomains.any { domain ->
                url.contains(domain, ignoreCase = true)
            }

            return GeckoResult.fromValue(
                if (isAllowed) AllowOrDeny.ALLOW else AllowOrDeny.DENY
            )
        }
    }
}






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
                .fillMaxSize(),
            factory = { ctx ->
                GeckoView(ctx).apply { 
                    setSession(Tab) 
                }
            }
        )
    }
}






