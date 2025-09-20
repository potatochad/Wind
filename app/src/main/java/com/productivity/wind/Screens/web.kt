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
import androidx.activity.compose.*


typealias Tab = GeckoSession
typealias ManageTab = GeckoSession.NavigationDelegate


var Tab.ManageTab: ManageTab?
    get() = this.navigationDelegate
    set(value) {
        this.navigationDelegate = value
    }




@Composable
fun Web2() {
        val ctx = LocalContext.current
        val Web = r { GeckoRuntime.create(ctx) }
        val Tab = r { GeckoSession() }

        Item.WebPointTimer()
    
        DisposableEffect(Unit) {
        
            Tab.ManageTab = onlyAllowDomains(listOf("youtube.com"))
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
                    .fillMaxWidth()
                    .height(App.LazyScreenContentHeight),
                factory = { ctx ->
                    GeckoView(ctx).apply { 
                        setSession(Tab) 
                    }
                }
            )
        }
}







@Composable
fun Web7() {
    val ctx = App.ctx
    val Web = r { GeckoRuntime.create(ctx) }
    val Tab = r { GeckoSession() }

    Item.WebPointTimer()

    Tab.loadUri("https://google.com")
    Tab.open(Web)
    
    BackHandler { Tab.goBack() }
    DisposableEffect(Unit) { onDispose { Web.shutdown() } }

    LazyScreen(
        title = { Text(" Points ${Bar.funTime}")},
        Scrollable = false,
        DividerPadding = false,
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(App.LazyScreenContentHeight),
            factory = { ctx ->
                GeckoView(ctx).apply { 
                    setSession(Tab) 
                }
            }
        )
    }
}




@Composable
fun Web() {
    val ctx = App.ctx
    val engine = remember { GeckoRuntime.create(ctx) }   // brain
    val tab = remember { GeckoSession() }                // body (tab)

    // Inject JS after page finishes loading
    LaunchedEffect(tab) {
        tab.navigationDelegate = object : GeckoSession.NavigationDelegate() {
            override fun onPageFinished(session: GeckoSession, uri: android.net.Uri?) {
                // Use javascript: URI to execute code in page context
                // (alert may be blocked on some sites — try simple DOM change instead)
                session.loadUri("javascript:(function(){alert('hi');})();")
                // safer alternative: append a big red box into page
                // session.loadUri("javascript:(function(){let b=document.createElement('div');b.style.position='fixed';b.style.top='50px';b.style.left='50px';b.style.width='200px';b.style.height='200px';b.style.backgroundColor='red';b.style.zIndex='99999';document.body.appendChild(b);})();")
            }
        }
    }

    // Open session and load page; clean up when composable leaves
    DisposableEffect(engine) {
        tab.open(engine)
        tab.loadUri("https://example.com") // use example.com for predictable results
        onDispose {
            tab.close()
            engine.shutdown()
        }
    }

    // Optional: back button handled by session
    BackHandler {
        try { tab.goBack() } catch (e: Exception) { /* ignore if no history */ }
    }

    // UI: show the GeckoView
    LazyScreen(
        title = { Text(" Points ${Bar.funTime}") },
        Scrollable = false,
        DividerPadding = false,
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(App.LazyScreenContentHeight),
            factory = { ctx ->
                GeckoView(ctx).apply {
                    setSession(tab)
                }
            }
        )
    }
}
