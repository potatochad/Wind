package com.productivity.wind.Imports

import com.productivity.wind.Imports.Data.*
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


fun UIStrBuilder.correctStr(text: Str, correctUntil: Int) {
    for (i in text.indices) {
        if (i < correctUntil) {
			text.bold().color(Color.Green)
        } else {
            add(text[i])
        }
    }
}

fun getApps(): List<ResolveInfo> {
    val pm = App.packageManager
    val launchIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    return pm.queryIntentActivities(launchIntent, 0).abcOrder()
}
fun List<ResolveInfo>.abcOrder(): List<ResolveInfo> {
    val pm = App.packageManager
    return this.sortedWith(compareBy { it.loadLabel(pm).toString().lowercase() })
}


fun CopyTsk.goodStr(): Int {
    val n = minOf(txt.size, input.size)
    for (i in 0 until n) {
        if (txt[i] != input[i]) return i
    }
    return n
}

fun CopyTsk.done(): Bool {
    return doneTimes >= maxDone
}
fun DoTsk.done(): Bool {
    return didTime >= doneTime
}



fun CopyTsk.edit(block: CopyTsk.() -> Unit) {
    Bar.copyTsk.edit(this, block)
}
fun GeoCircle.edit(block: GeoCircle.() -> Unit) {
	Bar.privacyGeo.edit(this, block)
}
fun DoTsk.edit(block: DoTsk.() -> Unit) {
	Bar.doTsk.edit(this, block)
}
fun DoTsk.remove() {
	Bar.doTsk.remove(this)
}

val DoTsk.timeLeft: Int
    get() = this.doneTime - this.didTime








