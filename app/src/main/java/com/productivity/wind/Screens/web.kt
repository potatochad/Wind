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
import org.mozilla.geckoview.GeckoSession.NavigationDelegate.*
import org.mozilla.geckoview.GeckoSession.*

typealias Tab = GeckoSession
typealias ManageTab = GeckoSession.NavigationDelegate


var Tab.ManageTab: ManageTab?
    get() = this.navigationDelegate
    set(value) {
        this.navigationDelegate = value
    }



fun Tab.HideYoutubeRecommendations() {
    this.ManageTab = object : ManageTab {
        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoSession.NavigationDelegate.Action {
            // Always allow page load
            val js = """
                (function(){
                    setInterval(function(){
                        var r=document.getElementById('related');
                        if(r){r.style.display='none';}
                    },1000);
                })()
            """.trimIndent()

            this@HideYoutubeRecommendations.loadUri("javascript:$js")

            return GeckoSession.NavigationDelegate.Action.ALLOW
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






