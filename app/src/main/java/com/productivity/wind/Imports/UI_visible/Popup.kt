@file:OptIn(ExperimentalFoundationApi::class)

package com.productivity.wind.Imports.UI_visible

import android.annotation.SuppressLint
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import androidx.compose.foundation.text.selection.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.activity.compose.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.gms.location.*
import android.os.*
import kotlin.math.*
import androidx.compose.ui.geometry.*
import androidx.compose.foundation.lazy.*
import com.productivity.wind.Imports.*
import android.content.*
import android.provider.*
import android.app.*


@Composable
fun IsSure(show: mBool, msg: Str = "delete this item for ever", Do: Do) {
    LazyPopup(
        show,
        "Delete",
        "Are you certain you want to $msg?",
        onConfirm = {Do()},
    )
}

@Composable
fun AllowAppUsage() {
    LazyPopup(
        m(yes),
        "Need Usage Permission",
        "To function correctly, this app requires access to your app usage data. Granting this permission allows the app to monitor usage statistics and manage app-related tasks efficiently. Without it, this feature won't work.",
        onConfirm = {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
            App.startActivity(intent)
        },
		onClose = {
			navBack()
		}
    )
}
fun popUpTest(activity: Activity=App, content: @Composable () -> Unit) {
    // Create a ComposeView tied to the Activity
    val composeView = androidx.compose.ui.platform.ComposeView(activity).apply {
        setContent {
            content() // <- Your custom Compose UI here
        }
    }

    // Wrap it in a PopupWindow
    val popup = android.widget.PopupWindow(
        composeView,
        android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        true
    )

    // Show in the center of the screen
    popup.showAtLocation(activity.window.decorView, android.view.Gravity.CENTER, 0, 0)

    // Optional: auto-dismiss after 3 seconds
    composeView.postDelayed({ popup.dismiss() }, 3000)
}




