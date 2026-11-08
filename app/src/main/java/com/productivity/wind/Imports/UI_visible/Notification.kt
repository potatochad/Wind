//‼️‼️‼️RULES:
//DONT SAVE ANY NOTIFI VALUE IN A GLOBAL VAR
//Call create channels when building notifi


package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.SaveData.List.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.String.*
import android.annotation.*
import androidx.annotation.RequiresApi
import android.app.*
import android.app.NotificationManager.*
import android.content.*
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.*
import android.os.Handler
import android.provider.*
import android.view.*
import android.widget.*
import android.support.v4.media.session.*
import androidx.activity.compose.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import androidx.core.app.*
import androidx.core.app.NotificationCompat.ProgressStyle
import androidx.core.graphics.drawable.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.*
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.permissions.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.productivity.wind.*
import com.productivity.wind.Imports.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.R
import kotlinx.coroutines.*
import kotlin.math.*
import java.util.logging.*
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import android.content.ComponentName
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.google.common.util.concurrent.ListenableFuture
import android.net.Uri


//! ONLY use this for notifications
//it has id manager and can be easily messed up
class LazyNotification(
    val id: Int,
    title: Str = "Wind",
    text: Str = "",
    icon: Int = myAppRes
) {
    val manager = NotificationManager()
    val builder = NotificationBuilder()
        .title(title)
        .text(text)
        .icon(icon)
        .silentUpdates()

    var text: Str = text
       set(value) {
           field = value
           manager.notify(id, builder.text(value).build())
       }

    fun show() {
        manager.notify(id, builder.build())
    }

    fun cancel() {
        manager.cancel(id)
    }
}


class MediaNotification {
    private var media: MediaController? = null
    private var waitDo: ListenableFuture<MediaController>? = null
    private val pending = mutableListOf<() -> Unit>()

    val isConnected: Bool
        get() = media != null

    val isPlaying: Bool
        get() = media?.isPlaying == true

    val currentPosition: Long
        get() = media?.currentPosition ?: 0L

    val duration: Long
        get() = media?.duration ?: 0L

    init {
        val token = SessionToken(App, ComponentName(App, BackgroundAudio::class.java))

        waitDo = MediaController.Builder(App, token).buildAsync()

        waitDo?.addListener({
            media = waitDo?.get()

            pending.toList().forEach { it() }
            pending.clear()
        }, MoreExecutors.directExecutor())
    }

    private fun run(x: MediaController.() -> Unit) {
        media?.let { it.x() } ?: pending.add {
            media?.x()
        }
    }

    fun play() = run { play() }
    fun pause() = run { pause() }
    fun stop() = run { stop() }
    fun goTo(x: Long) = run { seekTo(x) }
    fun volume(x: Float) = run { volume = x.fromTo(0f, 1f) }
    fun speed(speed: Float) = run { setPlaybackSpeed(speed) }

    fun audio(uri: Str) = run {
        setMediaItem(MediaItem.fromUri(uri))
        prepare()
        play()
    }
    
    fun close() {
        pending.clear()

        waitDo?.let {
            MediaController.releaseFuture(it)
        }

        media = null
        waitDo = null
    }
}



class MediaNotificationUI(val player: ExoPlayer) {
    private val batch = LazyBatch(::update)

    var title: Str by batch("Music")
    var artist: Str by batch("")
    var text: Str by batch("")
    var image: Uri? by batch(null)

    fun edit(block: MediaNotificationUI.() -> Unit) {
        batch { block() }
    }

    private fun update() {
        val item = player.currentMediaItem ?: return
        val uri = item.localConfiguration?.uri ?: return

        player.replaceMediaItem(
            player.currentMediaItemIndex,
            MediaItem.Builder()
                .setUri(uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(title)
                        .setArtist(artist)
                        .setDisplayTitle(text)
                        .setArtworkUri(image)
                        .build()
                )
                .build()
        )
    }
}
