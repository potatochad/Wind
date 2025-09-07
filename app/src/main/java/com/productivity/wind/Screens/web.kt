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
        Tab.setYouTubeHARDFilter()
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


fun GeckoSession.setYouTubeHARDFilter() {
    // Block network requests for images
    this.navigationDelegate = object : GeckoSession.NavigationDelegate() {
        override fun onLoadRequest(
            session: GeckoSession?,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): Boolean {
            val url = request.uri.toString()
            // Block all common YouTube image sources
            if (url.contains("ytimg.com/vi/") ||
                url.contains("yt3.ggpht.com") ||
                url.contains("googlevideo.com")) {
                return true // block
            }
            return false // allow other requests
        }
    }

    // Inject CSS to hide any leftover images or thumbnails
    this.loadUri("javascript:(" +
        "function(){" +
        "let style = document.createElement('style');" +
        "style.innerHTML = 'img, ytd-thumbnail, ytd-video-renderer ytd-thumbnail, ytd-channel-renderer img {display:none !important}';" +
        "document.head.appendChild(style);" +
        "})()")
}




