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
fun Web() {
    val context = LocalContext.current
    val geckoView = remember { GeckoView(context) }
    val session = remember { GeckoSession() }

    // Initialize GeckoRuntime once
    val runtime = remember { GeckoRuntime.create(context) }

    // Side-effect to open session and load page
    LaunchedEffect(Unit) {
        session.open(runtime)
        geckoView.setSession(session)
        session.loadUri("https://example.com")

        // Wait for page load
        session.addProgressListener { _, progress ->
            if (progress == 100) {
                val jsCode = """
                    alert('Hello from GeckoView!');
                    console.log('JS injected via Compose!');
                """.trimIndent()

                session.evaluateJavascript(jsCode) { result ->
                    Log.d("GeckoViewJS", "Result: $result")
                }
            }
        }
    }

    // Compose view container for GeckoView
    AndroidView(
        factory = { geckoView },
        modifier = Modifier.fillMaxSize()
    )
}

