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
import com.productivity.wind.*
import org.mozilla.geckoview.*
import android.content.*
import androidx.compose.ui.platform.*
import androidx.compose.foundation.lazy.*
import org.mozilla.geckoview.AllowOrDeny
import org.mozilla.geckoview.*
import org.mozilla.geckoview.GeckoSession.NavigationDelegate.*
import org.mozilla.geckoview.GeckoSession.*

typealias Tab = GeckoSession
typealias ManageTab = GeckoSession.NavigationDelegate


var Tab.ManageTab: ManageTab?
    get() = this.navigationDelegate
    set(value) {
        this.navigationDelegate = value
    }




@Composable
fun Web() {
    val ctx = LocalContext.current
    val Web = r { GeckoRuntime.create(ctx) }
    val Tab = r { GeckoSession() }

    Item.WebPointTimer()
    
    DisposableEffect(Unit) {
        
        Tab.ManageTab = onlyAllowDomains(listOf("youtube.com"))
        Tab.setYouTubeFilter()
        Tab.blockAllImages_Maximum()
        Tab.loadUri("https://youtube.com")
        Tab.open(Web)

        onDispose { Tab.close() }
    }

    LazyScreen(
        title = { 
            Text(" Points ${Bar.funTime}")
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


fun GeckoSession.blockAllImages_Maximum() {
    // 1️⃣ Block network requests for images/videos
    this.navigationDelegate = object : GeckoSession.NavigationDelegate {
        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoResult<AllowOrDeny>? {
            val url = request.uri.toString().lowercase()
            return if (
                url.endsWith(".png") ||
                url.endsWith(".jpg") ||
                url.endsWith(".jpeg") ||
                url.endsWith(".gif") ||
                url.endsWith(".webp") ||
                url.endsWith(".svg") ||
                url.contains("/images/") ||
                url.contains("logo") ||
                url.contains("favicon")
            ) {
                GeckoResult.fromValue(AllowOrDeny.DENY)
            } else {
                GeckoResult.fromValue(AllowOrDeny.ALLOW)
            }
        }
    }

    // 2️⃣ Inject JS to hide everything image-related
    val js = """
        (function(){
            function hideEverything() {
                // Hide all <img> and <svg> elements
                document.querySelectorAll('img, svg').forEach(el=>{
                    el.style.display='none !important';
                });
                
                // Remove all favicons
                document.querySelectorAll('link[rel~="icon"]').forEach(el=>{
                    el.parentNode.removeChild(el);
                });
                
                // Remove all CSS background images
                document.querySelectorAll('*').forEach(el=>{
                    el.style.backgroundImage='none !important';
                });
                
                // Remove pseudo-element images
                const style = document.createElement('style');
                style.innerHTML = '*::before, *::after { background-image: none !important; content: none !important; }';
                document.head.appendChild(style);
            }

            // Run immediately
            hideEverything();

            // Observe future DOM changes
            var observer = new MutationObserver(hideEverything);
            observer.observe(document.body, {childList:true, subtree:true});

            // Run after page fully loads
            window.addEventListener('load', hideEverything);
            document.addEventListener('DOMContentLoaded', hideEverything);
        })();
    """.trimIndent()

    this.loadUri("javascript:$js")
}
