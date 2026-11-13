package com.productivity.wind.Imports.Utils.Browser

import com.productivity.wind.Imports.Utils.SaveData.List.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.ToX.*
import android.annotation.SuppressLint
import android.content.*
import androidx.compose.runtime.*
import androidx.activity.compose.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.*
import androidx.compose.ui.viewinterop.*
import android.view.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import com.productivity.wind.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.productivity.wind.R
import com.productivity.wind.Imports.*
import android.webkit.*
import android.graphics.*
import kotlinx.coroutines.*
import com.productivity.wind.Imports.Utils.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.productivity.wind.Imports.Utils.String.*
import java.io.ByteArrayInputStream
import android.util.Log
import com.productivity.wind.Imports.Utils.NavControl.*
import android.app.Service
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService



class WebController(
    ctx: Context //!NEEDS ONLY LOCAL CONTEXT
): WebEngine(ctx) {  
	init {
		
	}
	
    fun settings(configure: WebSettings.() -> Unit) {
        webView.settings.apply {
            configure()
        }
    }

	fun zoomOut() = webView.zoomOut()
    fun reload() = webView.reload()
	fun canGoBack() = webView.canGoBack()
    fun back() {
		if (webView.canGoBack()) {
			webView.goBack()
		}
	}
	
    val url: Str?
        get() = webView.url
    fun url(u: Str) {
    val url = u.trim()
		webView.loadUrl(
			if (url.isUrl()) {
				url
			} else {
				"https://$url"
			}
		)
	}
   
	val web: WebView?
        get() = webView

    fun blockImages(Do: () -> Bool = { yes }) {
        shouldInterceptRequest { request ->
            val url = request.url.toString().lowercase()

            val host = request.url.host?.lowercase()

            if (host?.contains("ytimg") == true) {

                return@shouldInterceptRequest WebResource.emptyImage()
            }
            
            if (!url.image && Do()) return@shouldInterceptRequest null
            else WebResource.emptyImage()
        }
    }
}



abstract class WebEngine(
    protected val ctx: Context
) {
    val rootView = createRootView(ctx)
    val webView = getWebView(rootView)
    val swipeRefresh = getSwipeRefresh(rootView)

	var shouldOverrideUrlLoading = mList<(Str?) -> Bool>()
    var onPageFinished = mList<(Str?) -> Unit>()
    var onLoadResource = mList<(Str?) -> Unit>()
    val doUpdateVisitedHistory = mList<(Str?, Bool) -> Unit>()
    val onPageStarted = mList<(Str?) -> Unit>()
    val shouldInterceptRequest = mList<(WebResourceRequest) -> WebResourceResponse?>()
    var onProgressChanged = mList<(Int) -> Unit>()
    var onReceivedTitle = mList<(Str?) -> Unit>()

	init {
		webView.enable()
		swipeRefresh.setUp(webView)
        
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: Str?): Bool {
                return shouldOverrideUrlLoading.any { it(url) } // return true if any handler wants to override
            }
			override fun onPageCommitVisible(view: WebView?, url: Str?) {
				super.onPageCommitVisible(view, url)

				view?.importsJS()
			}

			private var lastFinishedUrl: Str? = null
            override fun onPageFinished(view: WebView?, url: Str?) {
                super.onPageFinished(view, url)
				if (url == lastFinishedUrl) return

				//this.zoomOut()
                view?.gray(90f)
                swipeRefresh.isRefreshing = no
                
                onPageFinished.forEach { it(url) }
            }
            override fun onLoadResource(view: WebView?, url: Str?) {
                super.onLoadResource(view, url)
                onLoadResource.forEach { it(url) }
            }
            override fun doUpdateVisitedHistory(view: WebView?, url: Str?, isReload: Bool) {
                super.doUpdateVisitedHistory(view, url, isReload)
                doUpdateVisitedHistory.forEach { it(url, isReload) } // call your handlers
            }
            override fun onPageStarted(view: WebView?, url: Str?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                onPageStarted.forEach { it(url) }
            }
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
                shouldInterceptRequest.forEach { 
                    val result = it(request)
                    if (result != null) return result
                }
                return super.shouldInterceptRequest(view, request)
            }
        }
        
        
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                onProgressChanged.forEach { it(newProgress) }
            }
            override fun onReceivedTitle(view: WebView?, title: Str?) {
                onReceivedTitle.forEach { it(title) }
            }
			override fun onConsoleMessage(message: ConsoleMessage): Bool {
				val msg = message.message()

				if (msg.has("[WINDWEB_LOG]")) {
					Log.e("badWEB", msg)
				}
				return yes
			}
        }
    }

    fun shouldOverrideUrlLoading(Do: (Str?) -> Bool) {
        shouldOverrideUrlLoading.add(Do)
    }
    fun onPageFinished(Do: (Str?) -> Unit) {
        onPageFinished.add(Do)
    }
    fun onLoadResource(Do: (Str?) -> Unit) {
        onLoadResource.add(Do)
    }
    fun doUpdateVisitedHistory(handler: (Str?, Bool) -> Unit) {
        doUpdateVisitedHistory.add(handler)
    }
    fun onPageStarted(handler: (Str?) -> Unit) {
        onPageStarted.add(handler)
    }
    fun shouldInterceptRequest(handler: (WebResourceRequest) -> WebResourceResponse?) {
        shouldInterceptRequest.add(handler)
    }
    

    fun onProgressChanged(handler: (Int) -> Unit) {
        onProgressChanged.add(handler)
    }
    fun onReceivedTitle(handler: (Str?) -> Unit) {
        onReceivedTitle.add(handler)
    }    

    
}


