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



/*
import com.duckduckgo.app.browser.favicon.FaviconManager
import com.duckduckgo.common.ui.view.listitem.OneLineListItem
import com.duckduckgo.mobile.android.databinding.RowOneLineListItemBinding
import com.duckduckgo.common.utils.UrlScheme.Companion.http

import com.duckduckgo.app.browser.history.NavigationHistoryEntry
import com.duckduckgo.common.utils.isHttpsVersionOfUri


import com.duckduckgo.feature.toggles.api.Toggle.FeatureName
import com.duckduckgo.feature.toggles.api.Toggle.State
import com.duckduckgo.feature.toggles.api.Toggle.State.Cohort
import com.duckduckgo.feature.toggles.api.Toggle.State.CohortName
import com.duckduckgo.feature.toggles.api.internal.CachedToggleStore
import com.duckduckgo.feature.toggles.api.internal.CachedToggleStore.Listener
import com.duckduckgo.feature.toggles.internal.api.FeatureTogglesCallback

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
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
*/



/** Public interface for managing site permissions data */
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
















interface WebNavigationState {
    val originalUrl: String?
    val currentUrl: String?
    val title: String?
    val stepsToPreviousPage: Int
    val canGoBack: Boolean
    val canGoForward: Boolean
    val hasNavigationHistory: Boolean
    val progress: Int?
    val navigationHistory: List<NavigationHistoryEntry>
}

sealed class WebNavigationStateChange {
    data class NewPage(
        val url: String,
        val title: String?,
    ) : WebNavigationStateChange()

    data class UrlUpdated(val url: String) : WebNavigationStateChange()
    data object PageCleared : WebNavigationStateChange()
    data object Unchanged : WebNavigationStateChange()
    data object PageNavigationCleared : WebNavigationStateChange()
    data object Other : WebNavigationStateChange()
}

fun WebNavigationState.compare(previous: WebNavigationState?): WebNavigationStateChange {
    if (this == previous) {
        return WebNavigationStateChange.Unchanged
    }

    if (this is EmptyNavigationState) {
        return WebNavigationStateChange.PageNavigationCleared
    }

    if (originalUrl == null && previous?.originalUrl != null) {
        return WebNavigationStateChange.PageCleared
    }

    val latestUrl = currentUrl ?: return WebNavigationStateChange.Other

    // A new page load is identified by the original url changing
    if (originalUrl != previous?.originalUrl) {
        return WebNavigationStateChange.NewPage(latestUrl, title)
    }

    // The most up-to-date record of the url is the current one, this may change many times during a page load
    // If the host changes too, we class it as a new page load
    if (currentUrl != previous?.currentUrl) {
        if (currentUrl?.toUri()?.host != previous?.currentUrl?.toUri()?.host) {
            return WebNavigationStateChange.NewPage(latestUrl, title)
        }

        return WebNavigationStateChange.UrlUpdated(latestUrl)
    }

    return WebNavigationStateChange.Other
}

data class WebViewNavigationState(
    val stack: WebBackForwardList,
    override val progress: Int? = null,
) : WebNavigationState {

    override val originalUrl: String? = stack.originalUrl

    override val currentUrl: String? = stack.currentUrl

    override val title: String? = stack.currentItem?.title

    override val stepsToPreviousPage: Int = if (stack.isHttpsUpgrade) 2 else 1

    override val canGoBack: Boolean = stack.currentIndex >= stepsToPreviousPage

    override val canGoForward: Boolean = stack.currentIndex + 1 < stack.size

    override val hasNavigationHistory = stack.size != 0

    override val navigationHistory: List<NavigationHistoryEntry> = stack.toNavigationStack()

    /**
     * Auto generated equality method. We create this manually to omit the privately stored system stack property as
     * we are only interested in our properties and the stacks are never equal unless the same instances are compared.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WebViewNavigationState
        if (originalUrl != other.originalUrl) return false
        if (currentUrl != other.currentUrl) return false
        if (title != other.title) return false
        if (stepsToPreviousPage != other.stepsToPreviousPage) return false
        if (canGoBack != other.canGoBack) return false
        if (canGoForward != other.canGoForward) return false
        if (hasNavigationHistory != other.hasNavigationHistory) return false
        if (progress != other.progress) return false

        return true
    }

    /**
     * Auto generated hash method to support equality method
     */
    override fun hashCode(): Int {
        var result = originalUrl?.hashCode() ?: 0
        result = 31 * result + (currentUrl?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + stepsToPreviousPage
        result = 31 * result + canGoBack.hashCode()
        result = 31 * result + canGoForward.hashCode()
        result = 31 * result + hasNavigationHistory.hashCode()
        result = 31 * result + (progress?.hashCode() ?: 0)
        return result
    }

    private fun WebBackForwardList.toNavigationStack(): List<NavigationHistoryEntry> {
        val entryList = mutableListOf<NavigationHistoryEntry>()
        for (i in this.currentIndex downTo 0) {
            val currentStackItem = getItemAtIndex(i)
            entryList.add(NavigationHistoryEntry(title = currentStackItem.title, url = currentStackItem.url))
        }
        return entryList
    }
}

/**
 * In some error scenarios (namely malicious site protections when block happens on device), we don't get to actually load a URL
 * into the WebView. Therefore, in terms of establishing navigation state changes, we can't rely on the WebView's stack URLs
 */
data class ErrorNavigationState(
    val stack: WebBackForwardList,
    private val maliciousSiteUrl: Uri,
    private val maliciousSiteTitle: String?,
    private val clientSideHit: Boolean,
    private val isMainframe: Boolean,
) : WebNavigationState {

    private val maliciousSiteUrlString: String = maliciousSiteUrl.toString()

    override val originalUrl: String = maliciousSiteUrlString

    override val currentUrl: String = maliciousSiteUrlString

    override val title: String? = maliciousSiteTitle

    override val stepsToPreviousPage: Int = if (stack.isHttpsUpgrade) 2 else 1

    override val canGoBack: Boolean = stack.currentIndex >= stepsToPreviousPage && !(isMainframe && clientSideHit)

    override val canGoForward: Boolean = stack.currentIndex + 1 < stack.size

    override val hasNavigationHistory = stack.size != 0

    override val navigationHistory: List<NavigationHistoryEntry> = stack.toNavigationStack()

    override val progress: Int? = null

    /**
     * Auto generated equality method. We create this manually to omit the privately stored system stack property as
     * we are only interested in our properties and the stacks are never equal unless the same instances are compared.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ErrorNavigationState
        if (originalUrl != other.originalUrl) return false
        if (currentUrl != other.currentUrl) return false
        if (title != other.title) return false
        if (stepsToPreviousPage != other.stepsToPreviousPage) return false
        if (canGoBack != other.canGoBack) return false
        if (canGoForward != other.canGoForward) return false
        if (hasNavigationHistory != other.hasNavigationHistory) return false
        if (progress != other.progress) return false

        return true
    }

    /**
     * Auto generated hash method to support equality method
     */
    override fun hashCode(): Int {
        var result = originalUrl?.hashCode() ?: 0
        result = 31 * result + (currentUrl?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + stepsToPreviousPage
        result = 31 * result + canGoBack.hashCode()
        result = 31 * result + canGoForward.hashCode()
        result = 31 * result + hasNavigationHistory.hashCode()
        result = 31 * result + (progress?.hashCode() ?: 0)
        return result
    }

    private fun WebBackForwardList.toNavigationStack(): List<NavigationHistoryEntry> {
        val entryList = mutableListOf<NavigationHistoryEntry>()
        for (i in this.currentIndex downTo 0) {
            val currentStackItem = getItemAtIndex(i)
            entryList.add(NavigationHistoryEntry(title = currentStackItem.title, url = currentStackItem.url))
        }
        return entryList
    }
}

@Suppress("DataClassPrivateConstructor")
data class EmptyNavigationState private constructor(
    override val originalUrl: String?,
    override val currentUrl: String?,
    override val title: String?,
    override val navigationHistory: List<NavigationHistoryEntry> = emptyList(),
) : WebNavigationState {
    companion object {
        operator fun invoke(webNavigationState: WebNavigationState): EmptyNavigationState {
            return EmptyNavigationState(
                webNavigationState.originalUrl,
                webNavigationState.currentUrl,
                webNavigationState.title,
            )
        }
    }

    override val stepsToPreviousPage: Int = 0
    override val canGoBack: Boolean = false
    override val canGoForward: Boolean = false
    override val hasNavigationHistory: Boolean = false
    override val progress: Int? = null
}

private val WebBackForwardList.originalUrl: String?
    get() = currentItem?.originalUrl

private val WebBackForwardList.currentUrl: String?
    get() = currentItem?.url

private val WebBackForwardList.isHttpsUpgrade: Boolean
    get() {
        if (currentIndex < 1) return false
        val current = originalUrl?.toUri() ?: return false
        val previous = getItemAtIndex(currentIndex - 1)?.originalUrl?.toUri() ?: return false
        return current.isHttpsVersionOfUri(previous)
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
















class FeatureToggles private constructor(
    private val store: Toggle.Store,
    private val appVersionProvider: () -> Int,
    private val flavorNameProvider: () -> String,
    private val featureName: String,
    private val appVariantProvider: () -> String?,
    private val forceDefaultVariant: () -> Unit,
    private val callback: FeatureTogglesCallback?,
    private val ioDispatcher: CoroutineDispatcher,
) {

    private val featureToggleCache = mutableMapOf<Method, Toggle>()

    data class Builder(
        private var store: Toggle.Store? = null,
        private var appVersionProvider: () -> Int = { Int.MAX_VALUE },
        private var flavorNameProvider: () -> String = { "" },
        private var featureName: String? = null,
        private var appVariantProvider: () -> String? = { "" },
        private var forceDefaultVariant: () -> Unit = { /** noop **/ },
        private var callback: FeatureTogglesCallback? = null,
        private var ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {

        fun store(store: Toggle.Store) = apply { this.store = store }
        fun appVersionProvider(appVersionProvider: () -> Int) = apply { this.appVersionProvider = appVersionProvider }
        fun flavorNameProvider(flavorNameProvider: () -> String) = apply { this.flavorNameProvider = flavorNameProvider }
        fun featureName(featureName: String) = apply { this.featureName = featureName }
        fun appVariantProvider(variantName: () -> String?) = apply { this.appVariantProvider = variantName }
        fun forceDefaultVariantProvider(forceDefaultVariant: () -> Unit) = apply { this.forceDefaultVariant = forceDefaultVariant }
        fun callback(callback: FeatureTogglesCallback) = apply { this.callback = callback }

        @VisibleForTesting
        fun ioDispatcher(ioDispatcher: CoroutineDispatcher) = apply { this.ioDispatcher = ioDispatcher }
        fun build(): FeatureToggles {
            val missing = StringBuilder()
            if (this.store == null) {
                missing.append("store")
            }
            if (this.featureName == null) {
                missing.append(", featureName ")
            }
            if (missing.isNotBlank()) {
                throw IllegalArgumentException("This following parameters can't be null: $missing")
            }
            return FeatureToggles(
                store = this.store!!,
                appVersionProvider = appVersionProvider,
                flavorNameProvider = flavorNameProvider,
                featureName = featureName!!,
                appVariantProvider = appVariantProvider,
                forceDefaultVariant = forceDefaultVariant,
                callback = this.callback,
                ioDispatcher = this.ioDispatcher,
            )
        }
    }

    fun <T> create(toggles: Class<T>): T {
        validateFeatureInterface(toggles)

        return Proxy.newProxyInstance(
            toggles.classLoader,
            arrayOf(toggles),
        ) { _, method, args ->
            validateMethod(method, args.orEmpty())

            if (method.declaringClass === Any::class.java) {
                return@newProxyInstance method.invoke(this, args) as T
            }
            return@newProxyInstance loadToggleMethod(method)
        } as T
    }

    private fun loadToggleMethod(method: Method): Toggle {
        synchronized(featureToggleCache) {
            featureToggleCache[method]?.let { return it }

            val defaultValue = try {
                method.getAnnotation(Toggle.DefaultValue::class.java).defaultValue
            } catch (t: Throwable) {
                throw IllegalStateException("Feature toggle methods shall have annotated default value")
            }
            val resolvedDefaultValue = when (val value = defaultValue.toValue()) {
                is Boolean -> value
                is String -> value.lowercase() == flavorNameProvider.invoke().lowercase()
                else -> throw IllegalStateException("Unsupported default value type")
            }

            val isInternalAlwaysEnabledAnnotated: Boolean = runCatching {
                method.getAnnotation(Toggle.InternalAlwaysEnabled::class.java)
            }.getOrNull() != null
            val isExperiment: Boolean = runCatching {
                method.getAnnotation(Toggle.Experiment::class.java)
            }.getOrNull() != null

            return ToggleImpl(
                store = if (store is CachedToggleStore) store else CachedToggleStore(store),
                key = getToggleNameForMethod(method),
                defaultValue = resolvedDefaultValue,
                isInternalAlwaysEnabled = isInternalAlwaysEnabledAnnotated,
                isExperiment = isExperiment,
                appVersionProvider = appVersionProvider,
                flavorNameProvider = flavorNameProvider,
                appVariantProvider = appVariantProvider,
                forceDefaultVariant = forceDefaultVariant,
                callback = callback,
                ioDispatcher = ioDispatcher,
            ).also { featureToggleCache[method] = it }
        }
    }

    private fun validateFeatureInterface(feature: Class<*>) {
        if (!feature.isInterface) {
            throw IllegalArgumentException("Feature declarations must be interfaces")
        }
    }

    private fun getToggleNameForMethod(method: Method): String {
        if (method.name != "self") return "${featureName}_${method.name}"

        // This can throw but should never happen as it's guarded by codegen as well
        return featureName
    }

    private fun validateMethod(method: Method, args: Array<out Any>) {
        if (method.returnType != Toggle::class.java) {
            throw IllegalArgumentException("Feature method return types must be Toggle")
        }

        if (args.isNotEmpty()) {
            throw IllegalArgumentException("Feature methods must not have arguments")
        }
    }
}

interface Toggle {
    /**
     * @return returns the [FeatureName]
     */
    fun featureName(): FeatureName

    /**
     * This method
     * - Enrolls the user if not previously enrolled (ie. no cohort currently assigned) AND [isEnabled].
     * - Is idempotent, ie. calling the function multiple times has the same effect as calling it once.
     *
     * @return `true` when the first enrolment is done, `false` in any other subsequent call
     */
    suspend fun enroll(): Boolean

    /**
     * Returns a cold [Flow] of [Boolean] values representing whether this toggle is enabled.
     *
     * ### Behavior
     * - When a collector starts, the current toggle value is emitted immediately.
     * - Subsequent emissions occur whenever the underlying [store] writes a new [State].
     * - The flow is cold: a listener is only registered while it is being collected.
     * - When collection is cancelled or completed, the registered listener is automatically unregistered.
     *
     * ### Thread-safety
     * Emissions are delivered on the coroutine context where the flow is collected.
     * Multiple collectors will each register their own listener instance.
     *
     * ### Example
     * ```
     * viewModelScope.launch {
     *     toggle.enabled()
     *         .distinctUntilChanged()
     *         .collect { enabled ->
     *             if (enabled) {
     *                 showOnboarding()
     *             } else {
     *                 showLoading()
     *             }
     *         }
     * }
     * ```
     *
     * Note: When not context is specified, [Dispatchers.IO] will be used
     *
     * @return a cold [Flow] that emits the current enabled state and any subsequent changes
     *         until the collector is cancelled.
     */
    fun enabled(): Flow<Boolean>

    /**
     * This method
     *    - Returns whether the feature flag state is enabled or disabled.
     *    - It is not affected by experiment cohort assignment. It just checks whether the feature is enabled or not.
     *    - It considers all other constraints like targets, minSupportedVersion, etc.
     *
     * @return `true` if the feature should be enabled, `false` otherwise
     */
    fun isEnabled(): Boolean

    /**
     * The usage of this API is only useful for internal/dev settings/features
     * If you find yourself having to call this method in production code, then YOU'RE DOING SOMETHING WRONG
     *
     * @param state update the stored [State] of the feature flag
     */
    fun setRawStoredState(state: State)

    /**
     * The usage of this API is only useful for internal/dev settings/features
     * If you find yourself having to call this method in production code, then YOU'RE DOING SOMETHING WRONG
     * The raw state is the stored state. [isEnabled] method takes the raw state and computes whether the feature should be enabled or disabled.
     * eg. by factoring in [State.minSupportedVersion] amongst others.
     *
     * You should never use individual properties on that raw state, eg. [State.enable] to decide whether the feature is enabled/disabled.
     *
     * @return the raw [State] store for this feature flag.
     */
    fun getRawStoredState(): State?

    /**
     * @return a JSON string containing the `settings`` of the feature or null if not present in the remote config
     */
    fun getSettings(): String?

    /**
     * Convenience method that checks if [getCohort] is not null
     *  - WARNING: This method does not check if the experiment is still enabled or not.
     * @return `true` if the user is enrolled in the experiment and `false` otherwise
     */
    suspend fun isEnrolled(): Boolean

    /**
     * @return `true` if the user is enrolled in the given cohort and the experiment is enabled or `false` otherwise
     */
    suspend fun isEnrolledAndEnabled(cohort: CohortName): Boolean

    /**
     * @return the list of domain exceptions`exceptions` of the feature or empty list if not present in the remote config
     */
    fun getExceptions(): List<FeatureException>

    /**
     * WARNING: This method always returns the cohort assigned regardless it the experiment is still enabled or not.
     * @return a [Cohort] if one has been assigned or `null` otherwise.
     */
    suspend fun getCohort(): Cohort?

    /**
     * This represents the state of a [Toggle]
     * @param remoteEnableState is the enabled/disabled state in the remote config
     * @param enable is the ultimate (computed) enabled state
     * @param minSupportedVersion is the lowest Android version for which this toggle can be enabled
     * @param rollout is the rollout specified in remote config
     * @param rolloutThreshold is the percentile for which this flag will be enabled. It's a value between 0-1
     *  Example: If [rolloutThreshold] = 0.3, if [rollout] is  <0.3 then the toggle will be disabled
     * @param targets specified the target audience for this toggle. If the user is not within the targets the toggle will be disabled
     * @param metadataInfo Some metadata info about the toggle. It is not stored and its computed when calling [getRawStoredState].
     */
    data class State(
        val remoteEnableState: Boolean? = null,
        val enable: Boolean = false,
        val minSupportedVersion: Int? = null,
        val rollout: List<Double>? = null,
        val rolloutThreshold: Double? = null,
        val targets: List<Target> = emptyList(),
        val metadataInfo: String? = null,
        val cohorts: List<Cohort> = emptyList(),
        val assignedCohort: Cohort? = null,
        val settings: String? = null,
        val exceptions: List<FeatureException> = emptyList(),
    ) {
        /**
         * The targeting properties that can be used to specify the target audience for a feature flag.
         *
         * Each property acts as a filter criterion. When a property is `null`, it is ignored during
         * matching (i.e., any value matches). When multiple properties are specified, all must match
         * for the target to be considered a match (AND logic).
         *
         * @param variantKey The experiment variant key to target (e.g., "mc"). When specified, only users
         *   assigned to this variant will match. Used for A/B testing and experiment targeting.
         * @param localeCountry The ISO 3166-1 alpha-2 country code to target (e.g., "US", "FR"). Matching
         *   is case-insensitive.
         * @param localeLanguage The ISO 639-1 language code to target (e.g., "en", "fr"). Matching is
         *   case-insensitive.
         * @param isReturningUser When `true`, targets users who have reinstalled the app. When `false`,
         *   targets new users only.
         * @param isPrivacyProEligible When `true`, targets users eligible for Privacy Pro subscription.
         *   When `false`, targets users not eligible for Privacy Pro.
         * @param entitlement The subscription Product entitlement string. When specified, only users with
         *   this active entitlement will match. Matching is case-insensitive.
         * @param minSdkVersion The minimum Android SDK version required. Devices running an SDK version
         *   greater than or equal to this value will match.
         */
        data class Target(
            val variantKey: String?,
            val localeCountry: String?,
            val localeLanguage: String?,
            val isReturningUser: Boolean?,
            val isPrivacyProEligible: Boolean?,
            val entitlement: String?,
            val minSdkVersion: Int?,
        )
        data class Cohort(
            val name: String,
            val weight: Int,

            /**
             * Represents serialized [ZonedDateTime] with "America/New_York" zone ID.
             *
             * This is nullable because only assigned cohort should have a value here, it's ET timezone
             */
            val enrollmentDateET: String? = null,
        )
        interface CohortName {
            val cohortName: String
        }
    }

    /**
     * The feature
     * [name] the name of the feature
     * [parentName] the name of the parent feature, or `null` if the feature has no parent (root feature)
     */
    data class FeatureName(
        val parentName: String?,
        val name: String,
    )

    interface Store {
        fun set(key: String, state: State)

        fun get(key: String): State?
    }

    /**
     * It is possible to add feature [Target]s.
     * To do that, just add the property inside the [Target] and implement the [TargetMatcherPlugin] to do the matching
     */
    interface TargetMatcherPlugin {
        /**
         * Implement this method when adding a new target property.
         * @return `true` if the target matches else false
         */
        fun matchesTargetProperty(target: State.Target): Boolean
    }

    /**
     * This annotation is required.
     * It specifies the default value of the feature flag when it's not remotely defined
     */
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DefaultValue(
        val defaultValue: DefaultFeatureValue,
    )

    enum class DefaultFeatureValue {
        FALSE,
        TRUE,
        INTERNAL,
        ;

        fun toValue(): Any {
            return when (this) {
                FALSE -> false
                TRUE -> true
                INTERNAL -> "internal"
            }
        }
    }

    /**
     * This annotation is optional.
     * It will make the feature flag ALWAYS enabled for internal builds
     */
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class InternalAlwaysEnabled

    /**
     * This annotation should be used in feature flags that related to experimentation.
     * It will make the feature flag to set the default variant if [isEnabled] is called BEFORE any variant has been allocated.
     * It will make the feature flag to consider the target variants during the [isEnabled] evaluation.
     */
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Experiment
}

internal class ToggleImpl constructor(
    private val store: Toggle.Store,
    private val key: String,
    private val defaultValue: Boolean,
    private val isInternalAlwaysEnabled: Boolean,
    private val isExperiment: Boolean,
    private val appVersionProvider: () -> Int,
    private val flavorNameProvider: () -> String = { "" },
    private val appVariantProvider: () -> String?,
    private val forceDefaultVariant: () -> Unit,
    private val callback: FeatureTogglesCallback?,
    private val ioDispatcher: CoroutineDispatcher,
) : Toggle {

    override fun equals(other: Any?): Boolean {
        if (other !is Toggle) {
            return false
        }
        return this.featureName() == other.featureName()
    }

    override fun hashCode(): Int {
        return this.featureName().hashCode()
    }

    override fun featureName(): FeatureName {
        val parts = key.split("_")
        return if (parts.size == 2) {
            FeatureName(name = parts[1], parentName = parts[0])
        } else {
            FeatureName(name = parts[0], parentName = null)
        }
    }

    override suspend fun enroll(): Boolean {
        return enrollInternal()
    }

    override fun enabled(): Flow<Boolean> = callbackFlow {
        // emit current value when someone starts collecting
        trySend(isEnabled())

        val unsubscribe = when (val s = store) {
            is CachedToggleStore -> {
                s.setListener(
                    object : Listener {
                        override fun onToggleStored(newValue: State) {
                            // emit value just stored
                            launch { trySend(isEnabled()) }
                        }
                    },
                )
            }
            else -> { -> Unit }
        }

        // when flow collection is cancelled/closed, run the unsubscribe to avoid leaking the listener
        awaitClose { unsubscribe() }
    }.conflate().flowOn(ioDispatcher)

    private fun enrollInternal(force: Boolean = false): Boolean {
        // if the Toggle is not enabled, then we don't enroll
        if (isEnabled() == false) {
            return false
        }

        store.get(key)?.let { state ->
            if (force || state.assignedCohort == null) {
                val updatedState = state.copy(assignedCohort = assignCohortRandomly(state.cohorts, state.targets)).also {
                    it.assignedCohort?.let { cohort ->
                        callback?.onCohortAssigned(this.featureName().name, cohort.name, cohort.enrollmentDateET!!)
                    }
                }
                store.set(key, updatedState)
                return updatedState.assignedCohort != null
            }
        }

        return false
    }

    override fun isEnabled(): Boolean {
        // This fun is in there because it should never be called outside this method
        fun Toggle.State.evaluateTargetMatching(isExperiment: Boolean): Boolean {
            val variant = appVariantProvider.invoke()
            // no targets then consider always treated
            if (this.targets.isEmpty()) {
                return true
            }
            // if it's an experiment we only check target variants and ignore all the rest
            // this is because the (retention) experiments define their targets some place else
            val variantTargets = this.targets.mapNotNull { it.variantKey }
            if (isExperiment && variantTargets.isNotEmpty()) {
                return variantTargets.contains(variant)
            }
            // finally, check all other targets
            val nonVariantTargets = this.targets.filter { it.variantKey == null }

            // callback should never be null, but if it is, consider targets a match
            return callback?.matchesToggleTargets(nonVariantTargets) ?: true
        }

        // This fun is in there because it should never be called outside this method
        fun evaluateLocalEnable(state: State, isExperiment: Boolean): Boolean {
            // variants are only considered for Experiment feature flags
            val doTargetsMatch = state.evaluateTargetMatching(isExperiment)

            return state.enable &&
                doTargetsMatch &&
                appVersionProvider.invoke() >= (state.minSupportedVersion ?: 0)
        }
        // check if it should always be enabled for internal builds
        if (isInternalAlwaysEnabled && flavorNameProvider.invoke().lowercase() == "internal") {
            return true
        }
        // If there's not assigned variant yet and is an experiment feature, set default variant
        if (appVariantProvider.invoke() == null && isExperiment) {
            forceDefaultVariant.invoke()
        }

        // normal check
        return store.get(key)?.let { state ->
            state.remoteEnableState?.let { remoteState ->
                remoteState && evaluateLocalEnable(state, isExperiment)
            } ?: evaluateLocalEnable(state, isExperiment)
        } ?: return defaultValue
    }

    @Suppress("NAME_SHADOWING")
    override fun setRawStoredState(state: Toggle.State) {
        var state = state

        // remote is disabled, store and skip everything
        if (state.remoteEnableState == false) {
            store.set(key, state)
            return
        }

        state = evaluateRolloutThreshold(state)

        // remote state is null, means app update. Propagate the local state to remote state
        if (state.remoteEnableState == null) {
            state = state.copy(remoteEnableState = state.enable)
        }

        // finally store the state
        store.set(key, state)
    }

    override fun getRawStoredState(): State? {
        val metadata = listOf(
            isExperiment to "Retention Experiment",
            isInternalAlwaysEnabled to "Internal builds forced-enabled",
        )
        val info = metadata.filter { it.first }.joinToString(",") { it.second }
        return store.get(key)?.copy(metadataInfo = info)
    }

    private fun evaluateRolloutThreshold(
        inputState: State,
    ): State {
        fun checkAndSetRolloutThreshold(state: State): State {
            if (state.rolloutThreshold == null) {
                val random = Random.nextDouble(100.0)
                return state.copy(rolloutThreshold = random)
            }
            return state
        }

        val state = checkAndSetRolloutThreshold(inputState)

        // there is no rollout, return whatever the previous state was
        if (state.rollout.isNullOrEmpty()) {
            // when there is no rollout we don't continue calculating the state
            // however, if remote config has an enable value, ie. remoteEnableState we need to honour it
            // that covers eg. the fresh installed case
            return state.remoteEnableState?.let { remoteEnabledValue ->
                state.copy(enable = remoteEnabledValue)
            } ?: state
        }

        val scopedRolloutRange = state.rollout.filter { it in 0.0..100.0 }
        if (scopedRolloutRange.isEmpty()) return state

        return state.copy(
            enable = (state.rolloutThreshold ?: 0.0) <= scopedRolloutRange.last(),
        )
    }

    private fun assignCohortRandomly(
        cohorts: List<Cohort>,
        targets: List<State.Target>,
    ): Cohort? {
        fun getRandomCohort(cohorts: List<Cohort>): Cohort? {
            return kotlin.runCatching {
                @Suppress("NAME_SHADOWING") // purposely shadowing to ensure positive weights
                val cohorts = cohorts.filter { it.weight >= 0 }

                val indexArray = IntArray(cohorts.size) { i -> i }
                val weightArray = cohorts.map { it.weight.toDouble() }.toDoubleArray()

                val randomIndex = EnumeratedIntegerDistribution(indexArray, weightArray)

                cohorts[randomIndex.sample()]
            }.getOrNull()
        }
        fun containsAndMatchCohortTargets(targets: List<State.Target>): Boolean {
            return callback?.matchesToggleTargets(targets) ?: true
        }

        // In the remote config, targets is a list, but it should not be. So we pick the first one (?)
        if (!containsAndMatchCohortTargets(targets)) {
            return null
        }

        @Suppress("NAME_SHADOWING") // purposely shadowing to make sure we remove invalid variants
        val cohorts = cohorts.filter { it.weight >= 0 }

        val totalWeight = cohorts.sumOf { it.weight }
        if (totalWeight == 0) {
            // no variant active
            return null
        }

        return getRandomCohort(cohorts)?.copy(
            enrollmentDateET = ZonedDateTime.now(ZoneId.of("America/New_York")).toString(),
        )
    }

    override fun getSettings(): String? = store.get(key)?.settings

    override fun getExceptions(): List<FeatureException> {
        return store.get(key)?.exceptions.orEmpty()
    }

    override suspend fun getCohort(): Cohort? {
        val state = store.get(key)
        state?.assignedCohort?.let { assignedCohort ->
            // cohort is assigned and assignedCohort is no longer in remote config, then re-enroll
            if (!state.cohorts.map { it.name }.contains(assignedCohort.name)) {
                enrollInternal(force = true)
            }
        }

        return store.get(key)?.assignedCohort
    }

    override suspend fun isEnrolled(): Boolean = getCohort() != null

    override suspend fun isEnrolledAndEnabled(cohort: CohortName): Boolean {
        return cohort.cohortName.lowercase() == getCohort()?.name?.lowercase() && isEnabled()
    }
}






class NavigationHistoryAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val faviconManager: FaviconManager,
    private val tabId: String,
    private val listener: NavigationHistoryListener,
) : RecyclerView.Adapter<NavigationViewHolder>() {

    interface NavigationHistoryListener {
        fun historicalPageSelected(stackIndex: Int)
    }

    private var navigationHistory: List<NavigationHistoryEntry> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NavigationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowOneLineListItemBinding.inflate(inflater, parent, false)
        return NavigationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NavigationViewHolder,
        position: Int,
    ) {
        val entry = navigationHistory[position]
        val listItem = holder.binding.root

        loadFavicon(entry, holder.binding.root)
        listItem.setPrimaryText(entry.title.orEmpty())
        listItem.setOnClickListener { listener.historicalPageSelected(position) }
    }

    override fun getItemCount(): Int {
        return navigationHistory.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNavigationHistory(navigationHistory: List<NavigationHistoryEntry>) {
        this.navigationHistory = navigationHistory
        notifyDataSetChanged()
    }

    private fun loadFavicon(
        historyEntry: NavigationHistoryEntry,
        oneListItem: OneLineListItem,
    ) {
        lifecycleOwner.lifecycleScope.launch {
            faviconManager.loadToViewFromLocalWithPlaceholder(url = historyEntry.url, tabId = tabId, view = oneListItem.leadingIcon())
        }
    }
}

data class NavigationViewHolder(val binding: RowOneLineListItemBinding) :
    RecyclerView.ViewHolder(binding.root)

data class NavigationHistoryEntry(
    val title: String? = null,
    val url: String,
)












val IP_REGEX =
    Regex(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(:[0-9]+)?$",
    )

fun Uri.withScheme(): Uri {
    // Uri.parse function falsely parses IP:PORT string.
    // For example if input is "255.255.255.255:9999", it falsely flags 255.255.255.255 as the
    // scheme.
    // Therefore in the withScheme method, we need to parse it after manually inserting "http".
    if (scheme == null || scheme!!.matches(IP_REGEX)) {
        return parse("$http://${toString()}")
    }

    return this
}

/**
 * Tries to resolve a host (even if the scheme is missing), returning a basic host without the "www"
 * subdomain.
 */
val Uri.baseHost: String?
    get() = withScheme().host?.removePrefix("www.")

val Uri.isHttp: Boolean
    get() = scheme?.equals(http, true) ?: false

val Uri.isHttps: Boolean
    get() = scheme?.equals(UrlScheme.https, true) ?: false

val Uri.toHttps: Uri
    get() = buildUpon().scheme(UrlScheme.https).build()

val Uri.isHttpOrHttps: Boolean
    get() = isHttp || isHttps

val Uri.hasIpHost: Boolean
    get() {
        return baseHost?.matches(IP_REGEX) ?: false
    }

/**
 * Checks if the URI represents a local or private network address.
 * This includes:
 * - file:// URLs (local filesystem)
 * - localhost hostname
 * - IPv4/IPv6 loopback addresses (127.0.0.0/8, ::1)
 * - IPv4/IPv6 private network ranges (10.0.0.0/8, 172.16.0.0/12, 192.168.0.0/16, fc00::/7)
 * - IPv6 link-local addresses (fe80::/10)
 *
 * Note: .local domain names are NOT treated as local (they require mDNS resolution).
 */
val Uri.isLocalUrl: Boolean
    get() {
        if (scheme == "file") return true

        val host = this.host?.lowercase() ?: return false
        if (host == "localhost") return true

        // Use InetAddresses.parseNumericAddress (API 29+) which only parses numeric IPs
        // and never performs DNS resolution. On API 26-28, fall back to strict IPv4-only
        // validation to avoid InetAddress.getByName() which can trigger DNS lookups.
        val addr = if (Build.VERSION.SDK_INT >= 29) {
            runCatching { android.net.InetAddresses.parseNumericAddress(host) }.getOrNull()
        } else {
            host.parseAsStrictIPv4()
        } ?: return false

        return addr.isLoopbackAddress || addr.isSiteLocalAddress || addr.isLinkLocalAddress || addr.isIPv6UniqueLocal()
    }

/**
 * Strictly parses a string as an IPv4 address without DNS resolution.
 * Returns null if the string is not a valid IPv4 address (exactly 4 octets, each 0-255).
 */
private fun String.parseAsStrictIPv4(): InetAddress? {
    val parts = split('.')
    if (parts.size != 4) return null
    val bytes = ByteArray(4)
    for (i in parts.indices) {
        val octet = parts[i].toIntOrNull() ?: return null
        if (octet !in 0..255) return null
        bytes[i] = octet.toByte()
    }
    return InetAddress.getByAddress(bytes)
}

/**
 * Checks if an IPv6 address is a Unique Local Address (ULA) in the fc00::/7 range.
 * These are the IPv6 equivalent of private IPv4 addresses.
 * Java's [InetAddress.isSiteLocalAddress] only covers the deprecated fec0::/10 range,
 * so this is needed to detect the current fc00::/7 ULA range.
 */
private fun InetAddress.isIPv6UniqueLocal(): Boolean {
    val bytes = this.address
    return bytes.size == 16 && (bytes[0].toInt() and 0xfe) == 0xfc
}

val Uri.absoluteString: String
    get() {
        return "$scheme://$host$path"
    }

fun Uri.toStringDropScheme(): String {
    return if (scheme != null) this.toString().substringAfter("$scheme://") else this.toString()
}

fun Uri.isHttpsVersionOfUri(other: Uri): Boolean {
    return isHttps && other.isHttp && other.toHttps == this
}

private val MOBILE_URL_PREFIXES = listOf("m.", "mobile.")

val Uri.isMobileSite: Boolean
    get() {
        val auth = authority ?: return false
        return MOBILE_URL_PREFIXES.firstOrNull { auth.startsWith(it) } != null
    }

fun Uri.toDesktopUri(): Uri {
    if (!isMobileSite) return this

    val newUrl =
        MOBILE_URL_PREFIXES.fold(toString()) { url, prefix -> url.replaceFirst(prefix, "") }

    return parse(newUrl)
}

fun Uri.domain(): String? = this.host

// to obtain a favicon for a website, we go directly to the site and look for /favicon.ico
private const val faviconBaseUrlFormat = "%s://%s/favicon.ico"
private const val touchFaviconBaseUrlFormat = "%s://%s/apple-touch-icon.png"

fun Uri?.faviconLocation(): Uri? {
    if (this == null) return null
    val host = this.host
    if (host.isNullOrBlank()) return null
    val isHttps = this.isHttps
    return parse(String.format(faviconBaseUrlFormat, if (isHttps) "https" else "http", host))
}

fun Uri?.touchFaviconLocation(): Uri? {
    if (this == null) return null
    val host = this.host
    if (host.isNullOrBlank()) return null
    val isHttps = this.isHttps
    return parse(String.format(touchFaviconBaseUrlFormat, if (isHttps) "https" else "http", host))
}

fun Uri.getValidUrl(): ValidUrl? {
    val validHost = host ?: return null
    val validBaseHost = baseHost ?: return null
    return ValidUrl(validBaseHost, validHost, path)
}

data class ValidUrl(
    val baseHost: String,
    val host: String,
    val path: String?,
)

fun Uri.replaceQueryParameters(queryParameters: List<String>): Uri {
    val newUri = buildUpon().clearQuery()
    val query = queryParameters.joinToString(separator = "&") { parameter ->
        getEncodedQueryParameters(parameter).joinToString(separator = "&") {
            "$parameter=$it"
        }
    }
    newUri.encodedQuery(query)
    return newUri.build()
}

/**
 * This method is exactly the same as [Uri.getQueryParameters] except it doesn't decode each entry in the values list.
 * @return a list of encoded values.
 */
fun Uri.getEncodedQueryParameters(key: String?): List<String> {
    if (isOpaque) {
        throw UnsupportedOperationException("This isn't a hierarchical URI.")
    }
    if (key == null) {
        throw NullPointerException("key")
    }
    val query: String = encodedQuery ?: return emptyList()
    val encodedKey: String = try {
        URLEncoder.encode(key, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
        throw AssertionError(e)
    }
    val values = ArrayList<String>()
    var start = 0
    do {
        val nextAmpersand = query.indexOf('&', start)
        val end = if (nextAmpersand != -1) nextAmpersand else query.length
        var separator = query.indexOf('=', start)
        if (separator > end || separator == -1) {
            separator = end
        }
        if (separator - start == encodedKey.length &&
            query.regionMatches(start, encodedKey, 0, encodedKey.length)
        ) {
            if (separator == end) {
                values.add("")
            } else {
                values.add(query.substring(separator + 1, end))
            }
        }

        // Move start to end of name.
        start = if (nextAmpersand != -1) {
            nextAmpersand + 1
        } else {
            break
        }
    } while (true)
    return Collections.unmodifiableList(values)
}

fun String.extractDomain(): String? {
    return if (this.startsWith("http")) {
        this.toUri().domain()
    } else if (this.startsWith("duck")) {
        this.toUri().buildUpon().path("").toString()
    } else {
        "https://$this".extractDomain()
    }
}

fun String.normalizeScheme(): String {
    if (!startsWith("https://") && !startsWith("http://")) {
        return "https://$this"
    }
    return this
}
