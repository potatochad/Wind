package com.productivity.wind.Imports.Utils.AppsAndDevice

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.*
import android.app.*
import androidx.core.app.*
import android.os.*
import android.content.*
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import android.content.Intent
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import android.content.ComponentName
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors

/*
fun testBackgroundAudio() {
    val media = MediaNotification()

    media.audio("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")

    media.UI(
        title = "Wind Audio Test",
        artist = "Wind"
    )
}
*/

class BackgroundAudio : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()

        val player = ExoPlayer.Builder(this).build()

        mediaSession = MediaSession.Builder(this, player)
            .build()
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession? = mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
        }

        mediaSession = null
        super.onDestroy()
    }
}


class BackgroundService : Service() {
    val notifi = LazyNotification(1, "Background tasks", "Loading...")
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var job: Job? = null
    

    override fun onCreate() {
        super.onCreate()

		Permission.notification()

        startForeground(notifi)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (job?.isActive != true) {
            job = serviceScope.launch {
				while (yes){
					BackgroundWork(notifi)
				}
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        job?.cancel()
        serviceScope.cancel()

        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null
}

















