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










/*
import com.duckduckgo.app.browser.model.BasicAuthenticationRequest
import com.duckduckgo.app.global.model.Site
import com.duckduckgo.app.surrogates.SurrogateResponse
import com.duckduckgo.app.trackerdetection.model.TrackingEvent
import com.duckduckgo.feature.toggles.api.Toggle
import com.duckduckgo.malicioussiteprotection.api.MaliciousSiteProtection.Feed
import com.duckduckgo.site.permissions.api.SitePermissionsManager.SitePermissions

import com.duckduckgo.app.browser.navigation.safeCopyBackForwardList
import com.duckduckgo.app.di.AppCoroutineScope
import com.duckduckgo.appbuildconfig.api.AppBuildConfig
import com.duckduckgo.common.utils.DefaultDispatcherProvider
import com.duckduckgo.common.utils.DispatcherProvider
import com.duckduckgo.site.permissions.api.SitePermissionsManager
import com.duckduckgo.site.permissions.api.SitePermissionsManager.LocationPermissionRequest

import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
*/


interface AppBuildConfig {
    val isTest: Boolean
    val isPerformanceTest: Boolean
    val isDebug: Boolean
    val applicationId: String
    val buildType: String
    val versionCode: Int
    val versionName: String
    val flavor: BuildFlavor
    val sdkInt: Int
    val manufacturer: String
    val model: String
    val deviceLocale: Locale
    val isDefaultVariantForced: Boolean
    val buildDateTimeMillis: Long
    val canSkipOnboarding: Boolean

    /**
     * You should call [variantName] in a background thread
     */
    val variantName: String?

    /**
     * @return `true` if the user re-installed the app, `false` otherwise
     */
    suspend fun isAppReinstall(): Boolean

    /**
     * Detects if this is a fresh installation of the app (first time install).
     *
     * @return true if this is a new installation (not an update), false otherwise
     */
    fun isNewInstall(): Boolean
}

enum class BuildFlavor {
    INTERNAL,
    FDROID,
    PLAY,
}

/**
 * Convenience extension function
 * @return `true` when the current build flavor is INTERNAL, `false` otherwise
 */
fun AppBuildConfig.isInternalBuild(): Boolean = flavor == BuildFlavor.INTERNAL


interface DispatcherProvider {

    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun computation(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class DefaultDispatcherProvider : DispatcherProvider




interface SitePermissionsManager {
    /**
     * Returns an array of permissions that we support and the user has to manually handle
     *
     * @param tabId the tab where the request was originated
     * @param request original permission request
     * @return map where keys are the type [PermissionsKey] and have a list of [String] as values
     */
    suspend fun getSitePermissions(tabId: String, request: PermissionRequest): SitePermissions

    /**
     * Deletes all site permissions but the ones that are fireproof
     *
     * @param fireproofDomains list of domains that are fireproof
     */
    suspend fun clearAllButFireproof(fireproofDomains: List<String>)

    /**
     * Returns the proper response for a permissions.query JavaScript API call - see
     * https://developer.mozilla.org/en-US/docs/Web/API/Permissions/query
     *
     * @param url website querying the permission
     * @param tabId the tab where the query was originated
     * @param queriedPermission permission being queried (note: this is different from WebView permissions, check link above)
     * @return state of the permission as expected by the API: 'granted', 'prompt', or 'denied'
     */
    suspend fun getPermissionsQueryResponse(url: String, tabId: String, queriedPermission: String): SitePermissionQueryResponse

    /**
     * Checks if a site has been granted a specific set of permissions permanently
     *
     * @param url website querying the permission
     * @param request original permission request type
     * @return Returns true if site has that type of permission enabled
     */
    suspend fun hasSitePermanentPermission(url: String, request: String): Boolean

    data class SitePermissions(
        val autoAccept: List<String>,
        val userHandled: List<String>,
    )

    /**
     * Contains possible responses to the permissions.query JavaScript API call - see
     * https://developer.mozilla.org/en-US/docs/Web/API/Permissions/query
     */
    sealed class SitePermissionQueryResponse {
        data object Granted : SitePermissionQueryResponse()
        data object Prompt : SitePermissionQueryResponse()
        data object Denied : SitePermissionQueryResponse()
    }

    /**
     * Class that represents a location permission asked
     * callback is used to interact with the site that requested the permission
     */
    data class LocationPermissionRequest(
        val origin: String,
        val callback: GeolocationPermissions.Callback,
    ) : PermissionRequest() {

        override fun getOrigin(): Uri {
            return origin.toUri()
        }

        override fun getResources(): Array<String> {
            return listOf(RESOURCE_LOCATION_PERMISSION).toTypedArray()
        }

        override fun grant(p0: Array<out String>?) {
            callback.invoke(origin, true, false)
        }

        override fun deny() {
            callback.invoke(origin, false, false)
        }

        companion object {
            const val RESOURCE_LOCATION_PERMISSION: String = "com.duckduckgo.permissions.resource.LOCATION_PERMISSION"
        }
    }
}





/**
 * There is a bug in WebView whereby `webView.copyBackForwardList()` can internally throw a NPE
 *
 * This extension function can be used as a direct replacement of `copyBackForwardList()`
 * It will catch the NullPointerException and return `null` when it happens.
 *
 * https://bugs.chromium.org/p/chromium/issues/detail?id=498796
 */
fun WebView.safeCopyBackForwardList(): WebBackForwardList? {
    return try {
        copyBackForwardList()
    } catch (e: NullPointerException) {
        log("Failed to extract WebView back forward list: ${e.message}")
        null
    }
}




class BrowserChromeClient @Inject constructor(
    private val appBuildConfig: AppBuildConfig,
    val appCoroutineScope: CoroutineScope = scope,
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
        log("on hide custom view")
        webViewClientListener?.exitFullScreen()
        customView = null
    }

    override fun onProgressChanged(
        webView: WebView,
        newProgress: Int,
    ) {
        // We want to use webView.progress rather than newProgress because the former gives you the overall progress of the new site
        // and the latter gives you the progress of the current main request being loaded and one site could have several redirects.
        log("onProgressChanged ${webView.url}, ${webView.progress}")
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
                    log("Permissions: permission requested not user handled")
                    webViewClientListener?.onSitePermissionRequested(request, permissionsAllowedToAsk)
                }
            }
        }
    }

    override fun onGeolocationPermissionsShowPrompt(
        origin: String,
        callback: GeolocationPermissions.Callback,
    ) {
        log("Permissions: location permission requested $origin")
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




























interface WebViewClientListener {
    fun onPageContentStart(url: String)

    fun pageRefreshed(refreshedUrl: String)

    fun progressChanged(
        newProgress: Int,
        webViewNavigationState: WebViewNavigationState,
    )

    fun willOverrideUrl(newUrl: String)

    fun redirectTriggeredByGpc()

    fun onSitePermissionRequested(
        request: PermissionRequest,
        sitePermissionsAllowedToAsk: SitePermissions,
    )

    fun titleReceived(newTitle: String)

    fun trackerDetected(event: TrackingEvent)

    fun pageHasHttpResources(page: String)

    fun pageHasHttpResources(page: Uri)

    fun onCertificateReceived(certificate: SslCertificate?)

    fun sendEmailRequested(emailAddress: String)

    fun sendSmsRequested(telephoneNumber: String)

    fun dialTelephoneNumberRequested(telephoneNumber: String)

    fun goFullScreen(view: View)

    fun exitFullScreen()

    fun showFileChooser(
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: WebChromeClient.FileChooserParams,
    )

    fun handleAppLink(
        appLink: SpecialUrlDetector.UrlType.AppLink,
        isForMainFrame: Boolean,
    ): Boolean

    fun handleNonHttpAppLink(nonHttpAppLink: SpecialUrlDetector.UrlType.NonHttpAppLink): Boolean

    fun handleCloakedAmpLink(initialUrl: String)

    fun startProcessingTrackingLink()

    fun openMessageInNewTab(message: Message)

    fun openLinkInNewTab(uri: Uri)

    fun recoverFromRenderProcessGone()

    fun requiresAuthentication(request: BasicAuthenticationRequest)

    fun closeCurrentTab()

    fun closeAndSelectSourceTab()

    fun upgradedToHttps()

    fun surrogateDetected(surrogate: SurrogateResponse)

    fun isDesktopSiteEnabled(): Boolean

    fun isTabInForeground(): Boolean

    fun loginDetected()

    fun dosAttackDetected()

    fun iconReceived(
        url: String,
        icon: Bitmap,
    )

    fun iconReceived(
        visitedUrl: String,
        iconUrl: String,
    )

    fun prefetchFavicon(url: String)

    fun linkOpenedInNewTab(): Boolean

    fun isActiveTab(): Boolean

    fun onReceivedError(
        errorType: WebViewErrorResponse,
        url: String,
    )

    fun onReceivedMaliciousSiteWarning(
        url: Uri,
        feed: Feed,
        exempted: Boolean,
        clientSideHit: Boolean,
        isMainframe: Boolean,
    )

    fun onReceivedMaliciousSiteSafe(
        url: Uri,
        isForMainFrame: Boolean,
    )

    fun recordErrorCode(
        error: String,
        url: String,
    )

    fun recordHttpErrorCode(
        statusCode: Int,
        url: String,
    )

    fun onSiteVisited(url: String, title: String?)

    fun getCurrentTabId(): String

    fun getSite(): Site?

    fun onReceivedSslError(
        handler: SslErrorHandler,
        errorResponse: SslErrorResponse,
    )

    fun onShouldOverride()

    fun pageFinished(
        webView: WebView,
        webViewNavigationState: WebViewNavigationState,
        url: String?,
    )

    fun onPageCommitVisible(
        webViewNavigationState: WebViewNavigationState,
        url: String,
    )

    fun pageStarted(
        webViewNavigationState: WebViewNavigationState,
        activeExperiments: List<Toggle>,
    )
}
