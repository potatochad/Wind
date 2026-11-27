function sortUp(array) {
  return [...array].sort((a, b) => a - b);
}
function sortDown(array) {
  return [...array].sort((a, b) => b - a);
}


function getElements(selector) {
    return Array.from(document.querySelectorAll(selector));
}

function mediaElements() {
    return getElements("audio, video")
        .filter((media) => media && media.isConnected);
}

let currentMedia = null;
function chooseMedia() {
    if (currentMedia && currentMedia.isConnected && !currentMedia.ended) return currentMedia;
    
    currentMedia = mediaElements().sort((first, second) => {
    const playingDifference = Number(first.paused) - Number(second.paused);
    if (playingDifference !== 0) return playingDifference;
    return (second.clientWidth * second.clientHeight) -
      (first.clientWidth * first.clientHeight);
  })[0] || null;
  return currentMedia;
}


Web.getPlayingMedia = function() {
    //artists name, title etc..but not audio.
    const details = navigator.mediaSession?.metadata;

    return getElements("audio, video")
        .filter(it => !it.paused && !it.ended)
        .map(it => ({
        type: it.tagName.toLowerCase(),

        title: details?.title || document.title,
        artist: details?.artist || "",
        album: details?.album || "",
        artwork: details?.artwork?.[0]?.src || "",
        src: it.currentSrc || it.src || "",
        currentTime: it.currentTime,
        duration: Number.isFinite(it.duration)
            ? it.duration : null,

        muted: it.muted,
        volume: it.volume,
        playbackRate: it.playbackRate,

        readyState: it.readyState,
        networkState: it.networkState,
            
        poster: it.poster || "",
        videoWidth: it.videoWidth || 0,
        videoHeight: it.videoHeight || 0
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
