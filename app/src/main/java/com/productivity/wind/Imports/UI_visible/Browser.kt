package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.*
import android.app.usage.UsageStatsManager
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import kotlin.collections.*
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.painterResource
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.rememberTextMeasurer
import android.graphics.drawable.Drawable
import android.content.pm.*
import java.util.*
import com.productivity.wind.R
import com.productivity.wind.Imports.UI_visible.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*
import java.time.*
import kotlin.concurrent.schedule
import java.io.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.*
import android.text.*
import android.text.style.*
import androidx.compose.ui.viewinterop.*
import android.widget.*
import android.text.method.*
import androidx.compose.ui.unit.*
import com.productivity.wind.Imports.*
import androidx.compose.ui.window.*
import androidx.compose.animation.*
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import javax.inject.Inject
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Color
import android.os.Message
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.WebBackForwardList
import android.net.http.SslCertificate
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution
import org.jetbrains.annotations.VisibleForTesting
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random
import androidx.core.net.toUri
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import android.net.Uri.parse
import android.os.Build
import androidx.core.net.toUri
import java.io.UnsupportedEncodingException
import java.net.InetAddress
import java.net.URLEncoder
import java.util.*
import androidx.activity.compose.BackHandler


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebXml(
    webViewState: m_<WebView?>,
    url: Str = "",
    isDesktopSite: Bool = no,
    onUrlChanged: DoStr = {},
    onProgressChanged: DoInt = {},
    onPageStarted: DoStr = {},
    onPageFinished: DoStr = {},
    loadPage: (url: Str) -> Bool = { yes },
) {
    BackHandler {
        webViewState.it?.goBack()
    }

    AndroidView(
        factory = { ctx ->
            val rootView = LayoutInflater.from(ctx).inflate(R.layout.web, null, no)
            val myWebView = rootView.findViewById<WebView>(R.id.myWebView)

            myWebView.settings.apply {
                javaScriptEnabled = yes
                domStorageEnabled = yes
                useWideViewPort = yes
                loadWithOverviewMode = yes
            }

            myWebView.webViewClient = object : WebViewClient() {
                override fun onLoadResource(view: WebView?, url: Str?) {
                    super.onLoadResource(view, url)
                    if (isDesktopSite) {
                        view?.evaluateJavascript(
                            "document.querySelector('meta[name=\"viewport\"]').setAttribute('content'," +
                                    "'width=1024px, initial-scale=' + (document.documentElement.clientWidth / 1024));",
                            null
                        )
                    }
                }

                override fun doUpdateVisitedHistory(view: WebView?, url: Str?, isReload: Bool) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    url?.let { 
                        onUrlChanged(it) 
                    }
                }

                override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
                    val raw = "${request.url}"

                    val stop = loadPage(raw)

                    if (!stop) {
                        goBackWeb(view)
                        return WebResourceResponse("text/plain", "utf-8", null)
                    }
                    return super.shouldInterceptRequest(view, request)
                }



                override fun onPageStarted(view: WebView?, url: Str?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    url?.let { onPageStarted(it) }
                }

                override fun onPageFinished(view: WebView?, url: Str?) {
                    super.onPageFinished(view, url)
                    url?.let { onPageFinished(it) }
                    view?.zoomOut()
                }
            }

            myWebView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    onProgressChanged(newProgress)
                }
            }

            myWebView.url("https://www.google.com/search?q=$url")
            webViewState.it = myWebView

            rootView
        },
        modifier = Mod.maxS(),
        update = { view ->
            val myWebView = view.findViewById<WebView>(R.id.myWebView)
        }
    )
}









 
