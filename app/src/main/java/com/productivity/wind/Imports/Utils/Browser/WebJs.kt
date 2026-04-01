package com.productivity.wind.Imports.Utils.Browser
 
import android.annotation.SuppressLint
import android.content.*
import androidx.compose.runtime.*
import androidx.activity.compose.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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



fun WebView.forceGoogleInputFocus() {
    this.evaluateJavascript(
        """
        (function() {

            let input =
                document.querySelector('textarea[name="q"]') ||
                document.querySelector('input[name="q"]') ||
                document.querySelector('[role="combobox"]');

            if (!input) return "not found";

            input.scrollIntoView({behavior: "smooth", block: "center"});

            input.focus();
            input.click();

            input.dispatchEvent(new Event('focus', {bubbles: true}));

            return "focused";

        })();
        """.trimIndent()
    ) {
        log("JS result: $it")

        this.showKeyboard()
    }
}




