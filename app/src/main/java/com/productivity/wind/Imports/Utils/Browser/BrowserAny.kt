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



@SuppressLint("SetJavaScriptEnabled")
fun WebView.enable(){
	this.settings.apply {
		mediaPlaybackRequiresUserGesture = no
        javaScriptEnabled = yes
		domStorageEnabled = yes
        useWideViewPort = yes
        loadWithOverviewMode = yes
	}
}


fun createRootView(ctx: Context): View =
    LayoutInflater.from(ctx).inflate(R.layout.web, null, no)
fun getWebView(rootView: View): WebView =
    rootView.findViewById(R.id.myWebView)



@SuppressLint("SetJavaScriptEnabled")
fun WebView.clearWebData() {
    clearMatches()
    clearHistory()
    clearFormData()
    clearSslPreferences()
    clearCache(yes)

    CookieManager.getInstance().removeAllCookies(null)
    WebStorage.getInstance().deleteAllData()
}

@SuppressLint("SetJavaScriptEnabled")
fun WebSettings.applyDefaultConfig() {
    javaScriptEnabled = yes
    setSupportZoom(yes)
    builtInZoomControls = yes
    displayZoomControls = no
}

@Composable
fun UrlLong(input: Str): Str {
    return remember(input) {
        if (input.startsWith("http://") || input.startsWith("https://")) {
            input
        } else {
            "https://$input"
        }
    }
}
@Composable
fun UrlShort(x: String): String {
    return remember(x) {
        x.remove("https://", "http://", "www.")
    }
}

fun BlockKeywords(
    web: WebController,
    keywords: List<Str>,
    Do: Do = {},
) {
    web.html {
        if (it != null) {
            val lowerHtml = it.lowercase()
            for (word in keywords) {
                if (lowerHtml.contains(word.lowercase())) {
                    web.back()
                    Do()
                    Vlog("Blocked $word") 
                    return@html
                }
            }
        }
    }
}

val WebResourceRequest.image: Bool
        get() = url.toString().lowercase().image


class WebResource {
    companion object {
        fun empty(): WebResourceResponse {
            return WebResourceResponse(
                "text/plain",
                "UTF-8",
                ByteArrayInputStream(ByteArray(0))
            )
        }

        fun emptyImage(): WebResourceResponse {
            return WebResourceResponse(
                "image/png",
                null,
                ByteArrayInputStream(ByteArray(0))
            )
        }
    }
}











