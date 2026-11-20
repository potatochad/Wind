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
        run { org.json.JSONObject(json) }.optString("type"),
        run { org.json.JSONObject(json) }.optString("title"),
        run { org.json.JSONObject(json) }.optString("artist"),
        run { org.json.JSONObject(json) }.optString("album"),
        run { org.json.JSONObject(json) }.optString("artwork"),
        run { org.json.JSONObject(json) }.optString("src"),
        run { org.json.JSONObject(json) }.optDouble("currentTime"),
        run { org.json.JSONObject(json) }.let {
            if (it.isNull("duration")) null else it.optDouble("duration")
        },
        run { org.json.JSONObject(json) }.optBoolean("paused"),
        run { org.json.JSONObject(json) }.optBoolean("ended"),
        run { org.json.JSONObject(json) }.optBoolean("muted"),
        run { org.json.JSONObject(json) }.optDouble("volume", 1.0),
        run { org.json.JSONObject(json) }.optDouble("playbackRate", 1.0),
        run { org.json.JSONObject(json) }.optInt("readyState"),
        run { org.json.JSONObject(json) }.optInt("networkState"),
        run { org.json.JSONObject(json) }.optString("poster"),
        run { org.json.JSONObject(json) }.optInt("videoWidth"),
        run { org.json.JSONObject(json) }.optInt("videoHeight")
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
