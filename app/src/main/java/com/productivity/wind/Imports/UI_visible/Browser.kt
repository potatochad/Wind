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



private const val ABOUT_BLANK = "about:blank"

class BrowserWebViewClient @Inject constructor(
    private val webViewHttpAuthStore: WebViewHttpAuthStore,
    private val trustedCertificateStore: TrustedCertificateStore,
    private val requestRewriter: RequestRewriter,
    private val specialUrlDetector: SpecialUrlDetector,
    private val requestInterceptor: RequestInterceptor,
    private val cookieManagerProvider: CookieManagerProvider,
    private val loginDetector: DOMLoginDetector,
    private val dosDetector: DosDetector,
    private val thirdPartyCookieManager: ThirdPartyCookieManager,
    @AppCoroutineScope private val appCoroutineScope: CoroutineScope,
    private val dispatcherProvider: DispatcherProvider,
    private val browserAutofillConfigurator: BrowserAutofill.Configurator,
    private val ampLinks: AmpLinks,
    private val printInjector: PrintInjector,
    private val internalTestUserChecker: InternalTestUserChecker,
    private val adClickManager: AdClickManager,
    private val autoconsent: Autoconsent,
    private val pixel: Pixel,
    private val crashLogger: CrashLogger,
    private val jsPlugins: PluginPoint<JsInjectorPlugin>,
    private val currentTimeProvider: CurrentTimeProvider,
    private val pageLoadedHandler: PageLoadedHandler,
    private val pageLoadWideEvent: PageLoadWideEvent,
    private val shouldSendPagePaintedPixel: PagePaintedHandler,
    private val mediaPlayback: MediaPlayback,
    private val subscriptions: Subscriptions,
    private val duckPlayer: DuckPlayer,
    private val duckDuckGoUrlDetector: DuckDuckGoUrlDetector,
    private val uriLoadedManager: UriLoadedManager,
    private val androidFeaturesHeaderPlugin: AndroidFeaturesHeaderPlugin,
    private val duckChat: DuckChat,
    private val contentScopeExperiments: ContentScopeExperiments,
) : WebViewClient() {
    var webViewClientListener: WebViewClientListener? = null
    var clientProvider: ClientBrandHintProvider? = null
    private var lastPageStarted: String? = null
    private var start: Long? = null

    private var shouldOpenDuckPlayerInNewTab: Boolean = true

    private var currentLoadOperationId: String? = null
    private var parallelRequestsOnStart = 0

    init {
        appCoroutineScope.launch {
            duckPlayer.observeShouldOpenInNewTab().collect {
                shouldOpenDuckPlayerInNewTab = it is On
            }
        }
    }

    private fun incrementAndTrackLoad() {
        // a new load operation is starting for this WebView instance.
        val loadId = UUID.randomUUID().toString()
        this.currentLoadOperationId = loadId

        parallelRequestsOnStart = parallelRequestCounter.incrementAndGet() - 1

        val job = timeoutScope.launch {
            delay(REQUEST_TIMEOUT_MS)
            // attempt to remove the job - if successful, it means it hasn't been finished/errored/cancelled yet
            if (activeRequestTimeoutJobs.remove(loadId) != null) {
                parallelRequestCounter.decrementAndGet()
            }
        }
        activeRequestTimeoutJobs[loadId] = job
    }

    private fun decrementLoadCountAndGet(): Int {
        this.currentLoadOperationId?.let { loadId ->
            val job = activeRequestTimeoutJobs.remove(loadId)

            // if we successfully removed the job (it means it hadn't timed out yet)
            if (job != null) {
                job.cancel()
                parallelRequestCounter.decrementAndGet()
            }
        }
        this.currentLoadOperationId = null
        return parallelRequestCounter.get()
    }

    /**
     * This is the method of url overriding available from API 24 onwards
     */
    @UiThread
    override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest,
    ): Boolean {
        val url = request.url
        return shouldOverride(view, url, request.isForMainFrame, request.isRedirect)
    }

    /**
     * API-agnostic implementation of deciding whether to override url or not
     */
    private fun shouldOverride(
        webView: WebView,
        url: Uri,
        isForMainFrame: Boolean,
        isRedirect: Boolean,
    ): Boolean {
        try {
            logcat(VERBOSE) { "shouldOverride webViewUrl: ${webView.url} URL: $url" }
            webViewClientListener?.onShouldOverride()
            if (requestInterceptor.shouldOverrideUrlLoading(webViewClientListener, url, webView.url?.toUri(), isForMainFrame)) {
                return true
            }

            if (isForMainFrame && dosDetector.isUrlGeneratingDos(url)) {
                webView.loadUrl(ABOUT_BLANK)
                webViewClientListener?.dosAttackDetected()
                return false
            }

            return when (val urlType = specialUrlDetector.determineType(initiatingUrl = webView.originalUrl, uri = url)) {
                is SpecialUrlDetector.UrlType.ShouldLaunchPrivacyProLink -> {
                    subscriptions.launchPrivacyPro(webView.context, url)
                    true
                }

                is SpecialUrlDetector.UrlType.Email -> {
                    webViewClientListener?.sendEmailRequested(urlType.emailAddress)
                    true
                }

                is SpecialUrlDetector.UrlType.Telephone -> {
                    webViewClientListener?.dialTelephoneNumberRequested(urlType.telephoneNumber)
                    true
                }

                is SpecialUrlDetector.UrlType.Sms -> {
                    webViewClientListener?.sendSmsRequested(urlType.telephoneNumber)
                    true
                }

                is SpecialUrlDetector.UrlType.AppLink -> {
                    logcat(INFO) { "Found app link for ${urlType.uriString}" }
                    webViewClientListener?.let { listener ->
                        return listener.handleAppLink(urlType, isForMainFrame)
                    }
                    false
                }

                is SpecialUrlDetector.UrlType.ShouldLaunchDuckChatLink -> {
                    runCatching {
                        val query = url.getQueryParameter(QUERY)
                        if (query != null) {
                            duckChat.openDuckChatWithPrefill(query)
                        } else {
                            duckChat.openDuckChat()
                        }
                    }.isSuccess
                }

                is SpecialUrlDetector.UrlType.ShouldLaunchDuckPlayerLink -> {
                    if (isRedirect && isForMainFrame) {
                        /*
                        This forces shouldInterceptRequest to be called with the YouTube URL, otherwise that method is never executed and
                        therefore the Duck Player page is never launched if YouTube comes from a redirect.
                         */
                        webViewClientListener?.let {
                            loadUrl(it, webView, url.toString())
                        }
                        return true
                    } else {
                        shouldOverrideWebRequest(
                            url,
                            webView,
                            isForMainFrame,
                            openInNewTab = shouldOpenDuckPlayerInNewTab && isForMainFrame && webView.url != url.toString(),
                            willOpenDuckPlayer = isForMainFrame,
                        )
                    }
                }

                is SpecialUrlDetector.UrlType.NonHttpAppLink -> {
                    logcat(INFO) { "Found non-http app link for ${urlType.uriString}" }
                    if (isForMainFrame) {
                        webViewClientListener?.let { listener ->
                            return listener.handleNonHttpAppLink(urlType)
                        }
                    }
                    true
                }

                is SpecialUrlDetector.UrlType.Unknown -> {
                    logcat(WARN) { "Unable to process link type for ${urlType.uriString}" }
                    webView.originalUrl?.let {
                        webView.loadUrl(it)
                    }
                    false
                }

                is SpecialUrlDetector.UrlType.SearchQuery -> false
                is SpecialUrlDetector.UrlType.Web -> {
                    shouldOverrideWebRequest(url, webView, isForMainFrame)
                }

                is SpecialUrlDetector.UrlType.ExtractedAmpLink -> {
                    if (isForMainFrame) {
                        webViewClientListener?.let { listener ->
                            listener.startProcessingTrackingLink()
                            logcat { "AMP link detection: Loading extracted URL: ${urlType.extractedUrl}" }
                            loadUrl(listener, webView, urlType.extractedUrl)
                            return true
                        }
                    }
                    false
                }

                is SpecialUrlDetector.UrlType.CloakedAmpLink -> {
                    val lastAmpLinkInfo = ampLinks.lastAmpLinkInfo
                    if (isForMainFrame && (lastAmpLinkInfo == null || lastPageStarted != lastAmpLinkInfo.destinationUrl)) {
                        webViewClientListener?.let { listener ->
                            listener.handleCloakedAmpLink(urlType.ampUrl)
                            return true
                        }
                    }
                    false
                }

                is SpecialUrlDetector.UrlType.TrackingParameterLink -> {
                    if (isForMainFrame) {
                        webViewClientListener?.let { listener ->
                            listener.startProcessingTrackingLink()
                            logcat { "Loading parameter cleaned URL: ${urlType.cleanedUrl}" }

                            return when (
                                val parameterStrippedType =
                                    specialUrlDetector.processUrl(initiatingUrl = webView.originalUrl, uriString = urlType.cleanedUrl)
                            ) {
                                is SpecialUrlDetector.UrlType.AppLink -> {
                                    loadUrl(listener, webView, urlType.cleanedUrl)
                                    listener.handleAppLink(parameterStrippedType, isForMainFrame)
                                }

                                is SpecialUrlDetector.UrlType.ExtractedAmpLink -> {
                                    logcat { "AMP link detection: Loading extracted URL: ${parameterStrippedType.extractedUrl}" }
                                    loadUrl(listener, webView, parameterStrippedType.extractedUrl)
                                    true
                                }

                                else -> {
                                    loadUrl(listener, webView, urlType.cleanedUrl)
                                    true
                                }
                            }
                        }
                    }
                    false
                }

                is SpecialUrlDetector.UrlType.DuckScheme -> {
                    webViewClientListener?.let { listener ->
                        if (
                            url.pathSegments?.firstOrNull()?.equals(DUCK_PLAYER_OPEN_IN_YOUTUBE_PATH, ignoreCase = true) == true ||
                            !shouldOpenDuckPlayerInNewTab
                        ) {
                            loadUrl(listener, webView, url.toString())
                        } else {
                            listener.openLinkInNewTab(url)
                        }
                        true
                    } ?: false
                }
            }
        } catch (e: Throwable) {
            appCoroutineScope.launch(dispatcherProvider.io()) {
                crashLogger.logCrash(CrashLogger.Crash(shortName = "m_webview_should_override", t = e))
            }
            return false
        }
    }

    private fun shouldOverrideWebRequest(
        url: Uri,
        webView: WebView,
        isForMainFrame: Boolean,
        openInNewTab: Boolean = false,
        willOpenDuckPlayer: Boolean = false,
    ): Boolean {
        if (requestRewriter.shouldRewriteRequest(url)) {
            webViewClientListener?.let { listener ->
                val newUri = requestRewriter.rewriteRequestWithCustomQueryParams(url)
                loadUrl(listener, webView, newUri.toString())
                return true
            }
        }
        if (isForMainFrame) {
            webViewClientListener?.let { listener ->
                listener.willOverrideUrl(url.toString())
                clientProvider?.let { provider ->
                    if (provider.shouldChangeBranding(url.toString())) {
                        provider.setOn(webView.settings, url.toString())
                        if (openInNewTab) {
                            listener.openLinkInNewTab(url)
                        } else {
                            loadUrl(listener, webView, url.toString())
                        }
                        return true
                    } else if (willOpenDuckPlayer && webView.url?.let { duckDuckGoUrlDetector.isDuckDuckGoUrl(it) } == true) {
                        duckPlayer.setDuckPlayerOrigin(SERP_AUTO)
                        if (openInNewTab) {
                            listener.openLinkInNewTab(url)
                            return true
                        } else {
                            return false
                        }
                    } else if (openInNewTab) {
                        listener.openLinkInNewTab(url)
                        return true
                    } else {
                        // See: https://app.asana.com/1/137249556945/project/1200905986587319/task/1212075841576596?focus=true
                        // val headers = androidFeaturesHeaderPlugin.getHeaders(url.toString())
                        // if (headers.isNotEmpty()) {
                        //     loadUrl(webView, url.toString(), headers)
                        //     return true
                        // }
                        return false
                    }
                }
                return false
            }
        } else if (openInNewTab) {
            webViewClientListener?.openLinkInNewTab(url)
            return true
        }
        return false
    }

    @UiThread
    override fun onPageCommitVisible(
        webView: WebView,
        url: String,
    ) {
        logcat(VERBOSE) { "onPageCommitVisible webViewUrl: ${webView.url} URL: $url progress: ${webView.progress}" }
        // Show only when the commit matches the tab state
        if (webView.url == url) {
            val navigationList = webView.safeCopyBackForwardList() ?: return
            webViewClientListener?.onPageCommitVisible(WebViewNavigationState(navigationList), url)
            webViewClientListener?.getCurrentTabId()?.let { tabId ->
                pageLoadWideEvent.onPageVisible(tabId, url, webView.progress)
            }
        }
    }

    private fun loadUrl(
        listener: WebViewClientListener,
        webView: WebView,
        url: String,
    ) {
        if (listener.linkOpenedInNewTab()) {
            webView.post {
                webView.loadUrl(url)
            }
        } else {
            webView.loadUrl(url)
        }
    }

    private fun loadUrl(
        webView: WebView,
        url: String,
        headers: Map<String, String>,
    ) {
        webView.loadUrl(url, headers)
    }

    @UiThread
    override fun onPageStarted(
        webView: WebView,
        url: String?,
        favicon: Bitmap?,
    ) {
        logcat { "onPageStarted webViewUrl: ${webView.url} URL: $url lastPageStarted $lastPageStarted" }
        url?.let {
            // See https://app.asana.com/0/0/1206159443951489/f (WebView limitations)
            if (it != ABOUT_BLANK && start == null) {
                start = currentTimeProvider.elapsedRealtime()
                webViewClientListener?.getCurrentTabId()?.let { tabId ->
                    pageLoadWideEvent.onPageStarted(tabId, it)
                }
                incrementAndTrackLoad() // increment the request counter
                requestInterceptor.onPageStarted(url)
            }

            handleMediaPlayback(webView, it)
            autoconsent.injectAutoconsent(webView, url)
            adClickManager.detectAdDomain(url)
            appCoroutineScope.launch(dispatcherProvider.io()) {
                thirdPartyCookieManager.processUriForThirdPartyCookies(webView, url.toUri())
            }
        }
        val navigationList = webView.safeCopyBackForwardList() ?: return

        appCoroutineScope.launch(dispatcherProvider.main()) {
            val activeExperiments = contentScopeExperiments.getActiveExperiments()
            webViewClientListener?.pageStarted(WebViewNavigationState(navigationList), activeExperiments)
            jsPlugins.getPlugins().forEach {
                it.onPageStarted(webView, url, webViewClientListener?.getSite()?.isDesktopMode, activeExperiments)
            }
        }
        if (url != null && url == lastPageStarted) {
            webViewClientListener?.pageRefreshed(url)
        }
        lastPageStarted = url
        browserAutofillConfigurator.configureAutofillForCurrentPage(webView, url)
        loginDetector.onEvent(WebNavigationEvent.OnPageStarted(webView))
    }

    private fun handleMediaPlayback(
        webView: WebView,
        url: String,
    ) {
        // The default value for this flag is `true`.
        webView.settings.mediaPlaybackRequiresUserGesture = mediaPlayback.doesMediaPlaybackRequireUserGestureForUrl(url)
    }

    @UiThread
    override fun onPageFinished(
        webView: WebView,
        url: String?,
    ) {
        logcat(VERBOSE) { "onPageFinished webViewUrl: ${webView.url} URL: $url progress: ${webView.progress}" }

        // See https://app.asana.com/0/0/1206159443951489/f (WebView limitations)
        if (webView.progress == 100) {
            jsPlugins.getPlugins().forEach {
                it.onPageFinished(
                    webView,
                    url,
                    webViewClientListener?.getSite(),
                )
            }

            url?.let {
                // We call this for any url but it will only be processed for an internal tester verification url
                internalTestUserChecker.verifyVerificationCompleted(it)
            }

            val navigationList = webView.safeCopyBackForwardList() ?: return
            webViewClientListener?.run {
                pageFinished(webView, WebViewNavigationState(navigationList), url)
            }
            flushCookies()
            printInjector.injectPrint(webView)

            if (url != null && url != ABOUT_BLANK) {
                start?.let { safeStart ->
                    val concurrentRequestsOnFinish = decrementLoadCountAndGet()
                    webViewClientListener?.getCurrentTabId()?.let { tabId ->
                        pageLoadWideEvent.onPageLoadFinished(
                            tabId = tabId,
                            url = url,
                            errorDescription = null,
                            isTabInForegroundOnFinish = webViewClientListener?.isTabInForeground() ?: true,
                            activeRequestsOnLoadStart = parallelRequestsOnStart,
                            concurrentRequestsOnFinish = concurrentRequestsOnFinish,
                        )
                    }

                    // TODO (cbarreiro - 22/05/2024): Extract to plugins
                    pageLoadedHandler.onPageLoaded(
                        url = url,
                        title = navigationList.currentItem?.title,
                        start = safeStart,
                        end = currentTimeProvider.elapsedRealtime(),
                        isTabInForegroundOnFinish = webViewClientListener?.isTabInForeground() ?: true,
                        activeRequestsOnLoadStart = parallelRequestsOnStart,
                        concurrentRequestsOnFinish = concurrentRequestsOnFinish,
                    )
                    shouldSendPagePaintedPixel(webView = webView, url = url)
                    appCoroutineScope.launch(dispatcherProvider.io()) {
                        if (duckPlayer.getDuckPlayerState() == ENABLED && duckPlayer.isYoutubeWatchUrl(url.toUri())) {
                            duckPlayer.duckPlayerNavigatedToYoutube()
                        }
                    }
                    uriLoadedManager.sendUriLoadedPixels(duckDuckGoUrlDetector.isDuckDuckGoQueryUrl(url))

                    webViewClientListener?.onSiteVisited(url, navigationList.currentItem?.title)

                    start = null
                }
            }
        }
    }

    private fun flushCookies() {
        appCoroutineScope.launch(dispatcherProvider.io()) {
            cookieManagerProvider.get()?.flush()
        }
    }

    @WorkerThread
    override fun shouldInterceptRequest(
        webView: WebView,
        request: WebResourceRequest,
    ): WebResourceResponse? =
        runBlocking {
            val documentUrl = withContext(dispatcherProvider.main()) { webView.url }
            withContext(dispatcherProvider.main()) {
                loginDetector.onEvent(WebNavigationEvent.ShouldInterceptRequest(webView, request))
            }
            logcat(VERBOSE) { "Intercepting resource ${request.url} type:${request.method} on page $documentUrl" }
            requestInterceptor.shouldIntercept(
                request,
                webView,
                documentUrl?.toUri(),
                webViewClientListener,
            )
        }

    override fun onRenderProcessGone(
        view: WebView?,
        detail: RenderProcessGoneDetail?,
    ): Boolean {
        logcat(WARN) { "onRenderProcessGone. Did it crash? ${detail?.didCrash()}" }
        val didCrash = detail?.didCrash() == true
        if (didCrash) {
            pixel.fire(WEB_RENDERER_GONE_CRASH)
        } else {
            pixel.fire(WEB_RENDERER_GONE_KILLED)
        }

        if (this.start != null) {
            val concurrentRequestsOnFinish = decrementLoadCountAndGet()
            view?.url?.let { url ->
                webViewClientListener?.getCurrentTabId()?.let { tabId ->
                    pageLoadWideEvent.onPageLoadFinished(
                        tabId = tabId,
                        url = url,
                        errorDescription = if (didCrash) "ERROR_RENDERER_CRASHED" else "ERROR_RENDERER_KILLED",
                        isTabInForegroundOnFinish = webViewClientListener?.isTabInForeground() ?: true,
                        activeRequestsOnLoadStart = parallelRequestsOnStart,
                        concurrentRequestsOnFinish = concurrentRequestsOnFinish,
                    )
                }
            }
            this.start = null
        }

        webViewClientListener?.recoverFromRenderProcessGone()
        return true
    }

    @UiThread
    override fun onReceivedHttpAuthRequest(
        view: WebView?,
        handler: HttpAuthHandler?,
        host: String?,
        realm: String?,
    ) {
        logcat(VERBOSE) { "onReceivedHttpAuthRequest ${view?.url} $realm, $host" }
        if (handler != null) {
            logcat(VERBOSE) { "onReceivedHttpAuthRequest - useHttpAuthUsernamePassword [${handler.useHttpAuthUsernamePassword()}]" }
            if (handler.useHttpAuthUsernamePassword()) {
                val credentials =
                    view?.let {
                        webViewHttpAuthStore.getHttpAuthUsernamePassword(it, host.orEmpty(), realm.orEmpty())
                    }

                if (credentials != null) {
                    handler.proceed(credentials.username, credentials.password)
                } else {
                    requestAuthentication(view, handler, host, realm)
                }
            } else {
                requestAuthentication(view, handler, host, realm)
            }
        } else {
            super.onReceivedHttpAuthRequest(view, handler, host, realm)
        }
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler,
        error: SslError,
    ) {
        var trusted: CertificateValidationState = CertificateValidationState.UntrustedChain

        when (error.primaryError) {
            SSL_UNTRUSTED -> {
                logcat { "The certificate authority ${error.certificate.issuedBy.dName} is not trusted" }
                trusted = trustedCertificateStore.validateSslCertificateChain(error.certificate)
            }

            else -> logcat { "SSL error ${error.primaryError}" }
        }

        logcat { "The certificate authority validation result is $trusted" }
        if (trusted is CertificateValidationState.TrustedChain) {
            handler.proceed()
        } else {
            webViewClientListener?.onReceivedSslError(handler, parseSSlErrorResponse(error))
        }
    }

    private fun parseSSlErrorResponse(sslError: SslError): SslErrorResponse {
        logcat { "SSL Certificate: parseSSlErrorResponse ${sslError.primaryError}" }
        val sslErrorType =
            when (sslError.primaryError) {
                SSL_UNTRUSTED -> UNTRUSTED_HOST
                SSL_EXPIRED -> EXPIRED
                SSL_DATE_INVALID -> EXPIRED
                SSL_IDMISMATCH -> WRONG_HOST
                else -> GENERIC
            }
        return SslErrorResponse(sslError, sslErrorType, sslError.url)
    }

    private fun requestAuthentication(
        view: WebView?,
        handler: HttpAuthHandler,
        host: String?,
        realm: String?,
    ) {
        webViewClientListener?.let {
            logcat(VERBOSE) { "showAuthenticationDialog - $host, $realm" }

            val siteURL = if (view?.url != null) "${URI(view.url).scheme}://$host" else host.orEmpty()

            val request =
                BasicAuthenticationRequest(
                    handler = handler,
                    host = host.orEmpty(),
                    realm = realm.orEmpty(),
                    site = siteURL,
                )

            it.requiresAuthentication(request)
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?,
    ) {
        error?.let { webResourceError ->
            val parsedError = parseErrorResponse(webResourceError)
            if (request?.isForMainFrame == true) {
                if (parsedError != OMITTED) {
                    if (this.start != null) {
                        // Trigger Wide Event failure for main frame errors
                        val concurrentRequestsOnFinish = decrementLoadCountAndGet()
                        request.url?.toString()?.let { url ->
                            webViewClientListener?.getCurrentTabId()?.let { tabId ->
                                pageLoadWideEvent.onPageLoadFinished(
                                    tabId = tabId,
                                    url = url,
                                    errorDescription = webResourceError.errorCode.asStringErrorCode(),
                                    isTabInForegroundOnFinish = webViewClientListener?.isTabInForeground() ?: true,
                                    activeRequestsOnLoadStart = parallelRequestsOnStart,
                                    concurrentRequestsOnFinish = concurrentRequestsOnFinish,
                                )
                            }
                        }
                        this.start = null
                    }
                    webViewClientListener?.onReceivedError(parsedError, request.url.toString())
                }
                logcat { "recordErrorCode for ${request.url}" }
                webViewClientListener?.recordErrorCode(
                    "${webResourceError.errorCode.asStringErrorCode()} - ${webResourceError.description}",
                    request.url.toString(),
                )
            }
        }
        super.onReceivedError(view, request, error)
    }

    private fun parseErrorResponse(error: WebResourceError): WebViewErrorResponse =
        if (error.errorCode == ERROR_HOST_LOOKUP) {
            when (error.description) {
                "net::ERR_NAME_NOT_RESOLVED" -> BAD_URL
                "net::ERR_INTERNET_DISCONNECTED" -> CONNECTION
                else -> OMITTED
            }
        } else if (error.errorCode == ERROR_FAILED_SSL_HANDSHAKE && error.description == "net::ERR_SSL_PROTOCOL_ERROR") {
            WebViewErrorResponse.SSL_PROTOCOL_ERROR
        } else {
            OMITTED
        }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?,
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        view?.url?.let {
            // We call this for any url but it will only be processed for an internal tester verification url
            internalTestUserChecker.verifyVerificationErrorReceived(it)
        }
        if (request?.isForMainFrame == true) {
            errorResponse?.let {
                logcat { "recordHttpErrorCode for ${request.url}" }
                webViewClientListener?.recordHttpErrorCode(it.statusCode, request.url.toString())
            }
        }
    }

    private fun Int.asStringErrorCode(): String =
        when (this) {
            ERROR_AUTHENTICATION -> "ERROR_AUTHENTICATION"
            ERROR_BAD_URL -> "ERROR_BAD_URL"
            ERROR_CONNECT -> "ERROR_CONNECT"
            ERROR_FAILED_SSL_HANDSHAKE -> "ERROR_FAILED_SSL_HANDSHAKE"
            ERROR_FILE -> "ERROR_FILE"
            ERROR_FILE_NOT_FOUND -> "ERROR_FILE_NOT_FOUND"
            ERROR_HOST_LOOKUP -> "ERROR_HOST_LOOKUP"
            ERROR_IO -> "ERROR_IO"
            ERROR_PROXY_AUTHENTICATION -> "ERROR_PROXY_AUTHENTICATION"
            ERROR_REDIRECT_LOOP -> "ERROR_REDIRECT_LOOP"
            ERROR_TIMEOUT -> "ERROR_TIMEOUT"
            ERROR_TOO_MANY_REQUESTS -> "ERROR_TOO_MANY_REQUESTS"
            ERROR_UNKNOWN -> "ERROR_UNKNOWN"
            ERROR_UNSAFE_RESOURCE -> "ERROR_UNSAFE_RESOURCE"
            ERROR_UNSUPPORTED_AUTH_SCHEME -> "ERROR_UNSUPPORTED_AUTH_SCHEME"
            ERROR_UNSUPPORTED_SCHEME -> "ERROR_UNSUPPORTED_SCHEME"
            SAFE_BROWSING_THREAT_BILLING -> "SAFE_BROWSING_THREAT_BILLING"
            SAFE_BROWSING_THREAT_MALWARE -> "SAFE_BROWSING_THREAT_MALWARE"
            SAFE_BROWSING_THREAT_PHISHING -> "SAFE_BROWSING_THREAT_PHISHING"
            SAFE_BROWSING_THREAT_UNKNOWN -> "SAFE_BROWSING_THREAT_UNKNOWN"
            SAFE_BROWSING_THREAT_UNWANTED_SOFTWARE -> "SAFE_BROWSING_THREAT_UNWANTED_SOFTWARE"
            else -> "ERROR_OTHER"
        }

    fun addExemptedMaliciousSite(
        url: Uri,
        feed: Feed,
    ) {
        requestInterceptor.addExemptedMaliciousSite(url, feed)
    }

    companion object {
        val parallelRequestCounter = AtomicInteger(0)
        private val activeRequestTimeoutJobs = ConcurrentHashMap<String, Job>()
        private const val REQUEST_TIMEOUT_MS = 30000L // 30 seconds

        // dedicated scope for request count timeout jobs (static, to be shared across all instances)
        @SuppressLint("NoHardcodedCoroutineDispatcher")
        private val timeoutScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}








