function getElements(selector) {
    return Array.from(document.querySelectorAll(selector));
}


Web.getPlayingMedia = function() {
    //artists name, title etc..but not audio.
    const details = navigator.mediaSession?.metadata;

    return getElements("audio, video")
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

Web.controlMedia = function(action, value) {
    const el = [...document.querySelectorAll("audio, video")]
        .find(el => !el.paused && !el.ended)
        || document.querySelector("video, audio");

    if (!el) return;

    switch (action) {
        case "play": el.play(); break;
        case "pause": el.pause(); break;
        case "seek": el.currentTime = value; break;
    }
};
