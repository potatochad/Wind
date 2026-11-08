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



open class MediaNotificationController() {
    protected val player = ExoPlayer.Builder(App).build()
    protected var media: MediaController? = null
    protected var waitDo: ListenableFuture<MediaController>? = null

    protected val batch = LazyBatch(::update)

    protected open fun update() {
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

    protected open var title: Str = "Music"
    protected open var artist: Str = ""
    protected open var text: Str = ""
    protected open var image: Uri? = null

    init {
        val token = SessionToken(
            App,
            ComponentName(App, BackgroundAudio::class.java)
        )

        waitDo = MediaController.Builder(App, token).buildAsync()

        waitDo?.addListener({
            media = waitDo?.get()
            batch { update() }
        }, MoreExecutors.directExecutor())
    }
}



class MediaNotification : MediaNotificationController() {
    var title: Str by batch("Music")
    var artist: Str by batch("")
    var text: Str by batch("")
    var image: Uri? by batch(null)

    fun edit(block: MediaNotification.() -> Unit) {
        batch { block() }
    }

    val isConnected: Bool
        get() = media != null

    val isPlaying: Bool
        get() = media?.isPlaying == true

    val currentPosition: Long
        get() = media?.currentPosition ?: 0L

    val duration: Long
        get() = media?.duration ?: 0L

    fun play() = media?.play()
    fun pause() = media?.pause()
    fun stop() = media?.stop()
    fun goTo(x: Long) = media?.seekTo(x)
    fun volume(x: Float) {
        media?.volume = x.fromTo(0f, 1f)
    }
    fun speed(x: Float) = media?.setPlaybackSpeed(x)

    fun audio(uri: Str) {
        media?.let {
            it.setMediaItem(MediaItem.fromUri(uri))
            it.prepare()
            it.play()
        }
    }

    fun close() {
        waitDo?.let(MediaController::releaseFuture)
        media = null
        waitDo = null
    }
}
