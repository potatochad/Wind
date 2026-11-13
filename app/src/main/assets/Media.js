Web.getPlayingMedia = function() {
    const metadata = navigator.mediaSession?.metadata;

    return Array.from(
        document.querySelectorAll("audio, video")
    )
    .filter(el => !el.paused && !el.ended)
    .map(el => ({
        type: el.tagName.toLowerCase(),

        title: metadata?.title || document.title,
        artist: metadata?.artist || "",
        album: metadata?.album || "",
        artwork: metadata?.artwork?.[0]?.src || "",

        src: el.currentSrc || el.src || "",

        currentTime: el.currentTime,
        duration: Number.isFinite(el.duration)
            ? el.duration : null,

        paused: el.paused,
        ended: el.ended,
        muted: el.muted,
        volume: el.volume,
        playbackRate: el.playbackRate,

        readyState: el.readyState,
        networkState: el.networkState,

        poster: el.poster || "",
        videoWidth: el.videoWidth || 0,
        videoHeight: el.videoHeight || 0
    }));
};
