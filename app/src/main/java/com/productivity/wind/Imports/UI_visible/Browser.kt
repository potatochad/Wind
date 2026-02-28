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
import android.annotation.SuppressLint
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
import android.view.ViewGroup
import android.webkit.CookieManager
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











/*
import com.duckduckgo.app.browser.navigation.safeCopyBackForwardList
import com.duckduckgo.app.di.AppCoroutineScope
import com.duckduckgo.appbuildconfig.api.AppBuildConfig
import com.duckduckgo.common.utils.DefaultDispatcherProvider
import com.duckduckgo.common.utils.DispatcherProvider
import com.duckduckgo.site.permissions.api.SitePermissionsManager
import com.duckduckgo.site.permissions.api.SitePermissionsManager.LocationPermissionRequest
*/


import javax.inject.Inject
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Color
import android.os.Message
import android.view.View

/*
import android.webkit.GeolocationPermissions
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
*/




class BrowserChromeClient @Inject constructor(
    private val appBuildConfig: AppBuildConfig,
    @AppCoroutineScope private val appCoroutineScope: CoroutineScope,
    private val coroutineDispatcher: DispatcherProvider = DefaultDispatcherProvider(),
    private val sitePermissionsManager: SitePermissionsManager,
) : WebChromeClient() {

    var webViewClientListener: WebViewClientListener? = null

    private var customView: View? = null

    override fun onShowCustomView(
        view: View,
        callback: CustomViewCallback?,
    ) {
        log("on show custom view")
        if (customView != null) {
            callback?.onCustomViewHidden()
            return
        }

        customView = view
        webViewClientListener?.goFullScreen(view)
    }

    override fun onHideCustomView() {
        logcat("on hide custom view")
        webViewClientListener?.exitFullScreen()
        customView = null
    }

    override fun onProgressChanged(
        webView: WebView,
        newProgress: Int,
    ) {
        // We want to use webView.progress rather than newProgress because the former gives you the overall progress of the new site
        // and the latter gives you the progress of the current main request being loaded and one site could have several redirects.
        logcat("onProgressChanged ${webView.url}, ${webView.progress}")
        if (webView.progress == 0) return
        val navigationList = webView.safeCopyBackForwardList() ?: return
        webViewClientListener?.progressChanged(webView.progress, WebViewNavigationState(navigationList, webView.progress))
        webViewClientListener?.onCertificateReceived(webView.certificate)
    }

    override fun onReceivedIcon(
        webView: WebView,
        icon: Bitmap,
    ) {
        webView.url?.let {
            log("Favicon bitmap received: ${webView.url}")
            webViewClientListener?.iconReceived(it, icon)
        }
    }

    override fun onReceivedTouchIconUrl(
        view: WebView?,
        url: String?,
        precomposed: Boolean,
    ) {
        log("Favicon touch received: ${view?.url}, $url")
        val visitedUrl = view?.url ?: return
        val iconUrl = url ?: return
        webViewClientListener?.iconReceived(visitedUrl, iconUrl)
        super.onReceivedTouchIconUrl(view, url, precomposed)
    }

    override fun onReceivedTitle(
        view: WebView,
        title: Str,
    ) {
        webViewClientListener?.titleReceived(title)
    }

    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams,
    ): Boolean {
        return try {
            webViewClientListener?.showFileChooser(filePathCallback, fileChooserParams)
            true
        } catch (e: Throwable) {
            // cancel the request using the documented way
            filePathCallback.onReceiveValue(null)
            throw e
        }
    }

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?,
    ): Boolean {
        val isGesture = if (appBuildConfig.isTest) true else isUserGesture
        if (isGesture && resultMsg?.obj is WebView.WebViewTransport) {
            webViewClientListener?.openMessageInNewTab(resultMsg)
            return true
        }
        return false
    }

    override fun onPermissionRequest(request: PermissionRequest) {
        log("Permissions: permission requested ${request.resources.asList()}")
        webViewClientListener?.getCurrentTabId()?.let { tabId ->
            appCoroutineScope.launch(coroutineDispatcher.io()) {
                val permissionsAllowedToAsk = sitePermissionsManager.getSitePermissions(tabId, request)
                if (permissionsAllowedToAsk.userHandled.isNotEmpty()) {
                    logcat { "Permissions: permission requested not user handled" }
                    webViewClientListener?.onSitePermissionRequested(request, permissionsAllowedToAsk)
                }
            }
        }
    }

    override fun onGeolocationPermissionsShowPrompt(
        origin: String,
        callback: GeolocationPermissions.Callback,
    ) {
        logcat { "Permissions: location permission requested $origin" }
        onPermissionRequest(LocationPermissionRequest(origin, callback))
    }

    override fun onCloseWindow(window: WebView?) {
        webViewClientListener?.closeCurrentTab()
    }

    /**
     * Called when a site's javascript tries to create a javascript alert dialog
     * @return false to allow it to happen as normal; return true to suppress it from being shown
     */
    override fun onJsAlert(
        view: WebView?,
        url: String,
        message: String,
        result: JsResult,
    ): Boolean = shouldSuppressJavascriptDialog(result)

    /**
     * Called when a site's javascript tries to create a javascript prompt dialog
     * @return false to allow it to happen as normal; return true to suppress it from being shown
     */
    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult,
    ): Boolean = shouldSuppressJavascriptDialog(result)

    /**
     * Called when a site's javascript tries to create a javascript confirmation dialog
     * @return false to allow it to happen as normal; return true to suppress it from being shown
     */
    override fun onJsConfirm(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult,
    ): Boolean = shouldSuppressJavascriptDialog(result)

    /**
     * Determines if we should allow or suppress a javascript dialog from being shown
     *
     * If suppressing it, we also cancel the pending javascript result so JS execution can continue
     * @return false to allow it to happen as normal; return true to suppress it from being shown
     */
    private fun shouldSuppressJavascriptDialog(result: JsResult): Boolean {
        if (webViewClientListener?.isActiveTab() == true) {
            return false
        }

        log("javascript dialog attempting to show but is not the active tab; suppressing dialog")
        result.cancel()
        return true
    }

    override fun getDefaultVideoPoster(): Bitmap {
        return Bitmap.createBitmap(intArrayOf(Color.TRANSPARENT), 1, 1, ARGB_8888)
    }
}














