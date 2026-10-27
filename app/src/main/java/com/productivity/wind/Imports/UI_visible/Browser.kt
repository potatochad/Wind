package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import com.productivity.wind.Imports.Utils.Browser.*

import androidx.compose.runtime.DisposableEffect


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebXml(web: WebController) {
    BackHandler { web.back() }

    val lifecycle = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(web, lifecycle) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            when (event) {
                androidx.lifecycle.Lifecycle.Event.ON_RESUME ->
                    web.webView.onResume()

                // Intentionally don't call webView.onPause():
                // testing whether this is involved in stopping audio.
                else -> Unit
            }
        }

        lifecycle.lifecycle.addObserver(observer)

        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
        }
    }
    
    AndroidView(
        factory = { 
            web.rootView
        },
        modifier = Mod.maxS(),
    )
}








/*
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewFullExample(
    url: String = "https://www.google.com",
    onUrlChanged: (String) -> Unit = {},
    onProgress: (Int) -> Unit = {},
) {
    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
            }

            // --- WEBVIEWCLIENT ---
            val client = object : WebViewClient() {
                
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val u = request?.url.toString()
                    onUrlChanged(u)
                    view?.loadUrl(u)
                    return true
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    url: String?
                ): Boolean {
                    url?.let { onUrlChanged(it) }
                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                }

                override fun doUpdateVisitedHistory(
                    view: WebView?,
                    url: String?,
                    isReload: Boolean
                ) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                }

                override fun onFormResubmission(
                    view: WebView?,
                    dontResend: Message?,
                    resend: Message?
                ) {
                    super.onFormResubmission(view, dontResend, resend)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                }

                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    return super.shouldInterceptRequest(view, request)
                }
            }

            // --- WEBCHROMECLIENT ---
            val chrome = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    onProgress(newProgress)
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                }

                override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                    super.onReceivedIcon(view, icon)
                }

                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    super.onShowCustomView(view, callback)
                }

                override fun onHideCustomView() {
                    super.onHideCustomView()
                }

                override fun onConsoleMessage(message: ConsoleMessage?): Boolean {
                    return super.onConsoleMessage(message)
                }

                override fun onJsAlert(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
                ): Boolean {
                    return super.onJsAlert(view, url, message, result)
                }

                override fun onJsConfirm(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
                ): Boolean {
                    return super.onJsConfirm(view, url, message, result)
                }

                override fun onJsPrompt(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    defaultValue: String?,
                    result: JsPromptResult?
                ): Boolean {
                    return super.onJsPrompt(view, url, message, defaultValue, result)
                }

                override fun onPermissionRequest(request: PermissionRequest?) {
                    super.onPermissionRequest(request)
                    request?.grant(request.resources)
                }

                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
                }
            }

            this.webViewClient = client
            this.webChromeClient = chrome
            this.loadUrl(url)
        },
        update = { web ->
            // update if needed
        }
    )
}
*/




 
