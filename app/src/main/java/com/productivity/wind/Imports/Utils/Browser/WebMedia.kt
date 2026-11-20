package com.productivity.wind.Imports.Utils.Browser

import com.productivity.wind.Imports.Utils.SaveData.List.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.ToX.*
import android.annotation.SuppressLint
import android.content.*
import androidx.compose.runtime.*
import androidx.activity.compose.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.*
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
import com.productivity.wind.Imports.Utils.String.*
import java.io.ByteArrayInputStream
import android.util.Log
import com.productivity.wind.Imports.Utils.NavControl.*
import android.app.Service
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import org.json.*


class WebMedia(
    val type: Str = "",
    val title: Str = "",
    val artist: Str = "",
    val album: Str = "",
    val artwork: Str = "",
    val src: Str = "",
    val currentTime: Double = 0.0,
    val duration: Double? = null,
    val paused: Bool = no,
    val ended: Bool = no,
    val muted: Bool = no,
    val volume: Double = 1.0,
    val playbackRate: Double = 1.0,
    val readyState: Int = 0,
    val networkState: Int = 0,
    val poster: Str = "",
    val videoWidth: Int = 0,
    val videoHeight: Int = 0
) {

    constructor(json: String) : this(
        try {
            JSONObject(json)
        } catch (e: Exception) {
            Vlog("WebMedia error: ${e.message}")
            JSONObject()
        }
    )
    
    private constructor(j: JSONObject) : this(
        type = j.optString("type"),
        title = j.optString("title"),
        artist = j.optString("artist"),
        album = j.optString("album"),
        artwork = j.optString("artwork"),
        src = j.optString("src"),
        currentTime = j.optDouble("currentTime"),
        duration = if (j.isNull("duration")) null else j.optDouble("duration"),
        paused = j.optBoolean("paused"),
        ended = j.optBoolean("ended"),
        muted = j.optBoolean("muted"),
        volume = j.optDouble("volume", 1.0),
        playbackRate = j.optDouble("playbackRate", 1.0),
        readyState = j.optInt("readyState"),
        networkState = j.optInt("networkState"),
        poster = j.optString("poster"),
        videoWidth = j.optInt("videoWidth"),
        videoHeight = j.optInt("videoHeight")
    )

    val isVideo get() = type == "video"
    val isAudio get() = type == "audio"

    val isPlaying get() = !paused && !ended
    val hasEnded get() = ended
    val hasStarted get() = currentTime > 0

    val hasDuration get() = duration != null && duration > 0
    val progress get() = if (hasDuration) currentTime / duration!! else 0.0
    val remaining get() = duration?.let { (it - currentTime).coerceAtLeast(0.0) }

    val hasArtwork get() = artwork.isNotEmpty()
    val hasPoster get() = poster.isNotEmpty()
    val hasArtist get() = artist.isNotEmpty()

    val isMuted get() = muted || volume == 0.0
    val isBuffering get() = isPlaying && readyState < 3
    val isReady get() = readyState >= 2

    val resolution get() = if (isVideo)
        "${videoWidth}x$videoHeight"
    else ""

    val displayTitle get() = title.ifEmpty { src.substringAfterLast("/") }

    fun formatTime(time: Double): Str {
        val seconds = time.toInt().coerceAtLeast(0)
        return "%d:%02d".format(seconds / 60, seconds % 60)
    }

    val time get() = formatTime(currentTime)
    val totalTime get() = duration?.let { formatTime(it) } ?: "--:--"
    val remainingTime get() = remaining?.let { formatTime(it) } ?: "--:--"
}
