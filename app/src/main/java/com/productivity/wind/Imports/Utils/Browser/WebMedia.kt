class WebMedia(
    val type: Str = "",
    val title: Str = "",
    val artist: Str = "",
    val album: Str = "",
    val artwork: Str = "",
    val src: Str = "",
    val currentTime: Double = 0.0,
    val duration: Double? = null,
    val paused: Bool = false,
    val ended: Bool = false,
    val muted: Bool = false,
    val volume: Double = 1.0,
    val playbackRate: Double = 1.0,
    val readyState: Int = 0,
    val networkState: Int = 0,
    val poster: Str = "",
    val videoWidth: Int = 0,
    val videoHeight: Int = 0
) {
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