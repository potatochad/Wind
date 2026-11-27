// media.size
Object.defineProperty(HTMLMediaElement.prototype, "size", {
    get() {
        return this.clientWidth * this.clientHeight;
    }
});
function getElements(selector) {
    return Array.from(document.querySelectorAll(selector));
}
//-----------Imports--------///



function mediaElements() {
    return getElements("audio, video")
        .filter((media) => media && media.isConnected);
}

let currentMedia = null;
function chooseMedia() {
    if (currentMedia && currentMedia.isConnected && !currentMedia.ended) return currentMedia;

    currentMedia = mediaElements()
      .filter(it => !it.paused)
      .sort((first, second) => second.size - first.size)[0] || null;
  return currentMedia;
}

/*
function reportMediaState(force = false) {
  const media = chooseMedia();
  if (!media || !port) return;
  const now = Date.now();
  if (!force && now - lastMediaReport < 1000) return;
  lastMediaReport = now;
  try {
    port.postMessage({
      type: "media-state",
      title: document.title || "网页媒体",
      url: location.href,
      mediaUrl: media.currentSrc || media.src || "",
      playing: !media.paused && !media.ended,
      durationMs: Number.isFinite(media.duration) ? Math.round(media.duration * 1000) : -1,
      positionMs: Number.isFinite(media.currentTime) ? Math.round(media.currentTime * 1000) : 0,
    });
  } catch (_) { }
}
*/
Web.getPlayingMedia = function() {
    //artists name, title etc..but not audio.
    const details = navigator.mediaSession?.metadata;
    const it = chooseMedia();
    
    return {
        type: it.tagName.toLowerCase(),
        title: details?.title || document.title,
        artist: details?.artist || "",
        album: details?.album || "",
        artwork: details?.artwork?.[0]?.src || "",
        src: it.currentSrc || it.src || "",
        currentTime: it.currentTime,
        duration: Number.isFinite(it.duration) ? it.duration : null,
        muted: it.muted,
        volume: it.volume,
        playbackRate: it.playbackRate,
        readyState: it.readyState,
        networkState: it.networkState,
        poster: it.poster || "",
        videoWidth: it.videoWidth || 0,
        videoHeight: it.videoHeight || 0
    };
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
