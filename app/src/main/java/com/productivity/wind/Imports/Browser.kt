package com.productivity.wind.Imports

import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.Imports.*
import com.productivity.wind.*
import kotlin.random.*
import androidx.compose.ui.*
import android.webkit.*
import androidx.compose.ui.viewinterop.*
import org.mozilla.geckoview.*
import android.content.*
import androidx.compose.runtime.snapshots.*
import androidx.compose.runtime.*
import org.mozilla.geckoview.GeckoSession.NavigationDelegate.*
import org.mozilla.geckoview.GeckoSession.*


fun onlyAllowDomains(allowedDomains: List<String>): GeckoSession.NavigationDelegate {
    return object : GeckoSession.NavigationDelegate {
        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoResult<AllowOrDeny> {
            val url = request.uri.toString() // uri is a Uri object, convert to string

            val isAllowed = allowedDomains.any { domain ->
                url.contains(domain, ignoreCase = true)
            }

            return GeckoResult.fromValue(
                if (isAllowed) AllowOrDeny.ALLOW else AllowOrDeny.DENY
            )
        }
    }
}



fun GeckoSession.setYouTubeFilter() {
    // Block thumbnail network requests
    navigationDelegate = object : GeckoSession.NavigationDelegate {
        override fun onLoadRequest(
            session: GeckoSession,
            request: GeckoSession.NavigationDelegate.LoadRequest
        ): GeckoResult<AllowOrDeny> {
            val url = request.uri.toString()
            val blockedDomains = listOf("ytimg.com", "youtube.com/embed", "googlevideo.com")
            val deny = blockedDomains.any { url.contains(it, ignoreCase = true) }
            return GeckoResult.fromValue(
                if (deny) AllowOrDeny.DENY else AllowOrDeny.ALLOW
            )
        }
    }

    // Inject JS after full page load
    progressDelegate = object : GeckoSession.ProgressDelegate {
        override fun onProgressChange(session: GeckoSession, progress: Int) {
            if (progress == 100) injectYouTubeCleanJS()
        }
    }
}
