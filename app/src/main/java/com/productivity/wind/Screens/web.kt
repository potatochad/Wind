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

    // Create Gecko runtime
    val geckoRuntime = r { GeckoRuntime.create(ctx) }

    // Create and configure Gecko session
    val geckoSession = r {
        GeckoSession().apply {
            open(geckoRuntime)
            loadUri(fullUrl) // initial load

            // Set up YouTube filter
            setYouTubeFilter()
        }
    }

    DisposableEffect(Unit) {
        onDispose { geckoSession.close() }
    }

    LazyScreen(
        title = { UI.Cinput(what = url, WidthMax = 150) }
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { ctx ->
                GeckoView(ctx).apply { setSession(geckoSession) }
            }
        )
    }
}

// Extension function to inject YouTube filter into a GeckoSession
fun GeckoSession.setYouTubeFilter() {
    // Runs on initial page load
    setContentDelegate(object : GeckoSession.ContentDelegate() {
        override fun onPageStop(session: GeckoSession, uri: String) {
            super.onPageStop(session, uri)
            injectYouTubeJS()
        }
    })
}

// Inject JS that removes YouTube content and dynamically handles new elements
private fun GeckoSession.injectYouTubeJS() {
    val js = """
    (function() {
        const url = window.location.href;

        if(url.includes('youtube.com')) {
            // Remove suggestions sidebar on video pages
            const suggestions = document.querySelector('#related');
            if(suggestions) suggestions.remove();

            // Remove search results
            const searchResults = document.querySelectorAll('ytd-item-section-renderer, ytd-video-renderer, ytd-channel-renderer');
            searchResults.forEach(el => el.remove());

            // Remove recommended videos under video
            const recommended = document.querySelector('#secondary');
            if(recommended) recommended.remove();

            // Dynamic observer for AJAX-loaded content
            const observer = new MutationObserver(mutations => {
                mutations.forEach(() => {
                    const suggestions = document.querySelector('#related');
                    if(suggestions) suggestions.remove();

                    const searchResults = document.querySelectorAll('ytd-item-section-renderer, ytd-video-renderer, ytd-channel-renderer');
                    searchResults.forEach(el => el.remove());
                });
            });
            observer.observe(document.body, { childList: true, subtree: true });
        }
    })();
    """
    loadUri("javascript:$js")
}
