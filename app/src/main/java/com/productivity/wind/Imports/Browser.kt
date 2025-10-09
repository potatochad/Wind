package com.productivity.wind.Imports
//
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.productivity.wind.*
import android.webkit.*
import com.productivity.wind.Imports.Data.Bool
import com.productivity.wind.Imports.Data.Str

@Suppress("DEPRECATION")
fun checkForInternet(): Bool {
    val context = App.ctx
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val activeNetwork = network?.let { connectivityManager.getNetworkCapabilities(it) }
        activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected == true
    }

    if (!isConnected) Vlog("No internet")
    return isConnected
}

@SuppressLint("SetJavaScriptEnabled")
fun WebView.clearWebData() {
    clearMatches()
    clearHistory()
    clearFormData()
    clearSslPreferences()
    clearCache(true)

    CookieManager.getInstance().removeAllCookies(null)
    WebStorage.getInstance().deleteAllData()
}

fun goBackWeb(webView: WebView?) {
    if (webView?.canGoBack() == true) {
        webView.goBack()
    }
}

@SuppressLint("SetJavaScriptEnabled")
fun WebSettings.applyDefaultConfig() {
    javaScriptEnabled = true
    setSupportZoom(true)
    builtInZoomControls = true
    displayZoomControls = false
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
fun UrlShort(input: String): String {
    return remember(input) {
        input.removePrefix("https://")
            .removePrefix("http://")
    }
}

