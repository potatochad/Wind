function getText(item) {
    return (
        item.innerText ||
        item.getAttribute("title") ||
        item.getAttribute("aria-label") ||
        item.textContent ||
        ""
    ).trim().toLowerCase();
}



function shouldProcessItem({ text, href, listItem }) {
    if (/^\d+(?::\d+)+$/.test(text.trim())) {
        log("REJECT: duration only", text);
        return false;
    }

    if (!text) {
        log("REJECT: empty text", href);
        return false;
    }

    if (!href) {
        log("REJECT: empty href");
        return false;
    }

    if (!listItem) {
        log("REJECT: text not matched target list", text);
        return false;
    }

    if (!href.includes("youtube.com/watch?v=")) {
        log("REJECT: not a watch url", href);
        return false;
    }

    if (href.startsWith("intent://")) {
        log("REJECT: intent url", href);
        return false;
    }

    log("ACCEPT:", href);
    return true;
}




//const targets = $jsChannels; //array


log("Filtering Youtube");

function processItem(item2) {
    const item =
    item2.closest("yt-lockup-view-model") ||
    item2.closest("ytm-media-item") ||
    item2.closest("ytm-video-with-context-renderer") ||
    item2;
    
    
    

    

    const href = item.querySelector('a[href*="/watch?v="]')?.href || "";
    const text = getText(item);
    const listItem = targets.some(t => text.includes(t));
    const logUrl = webItemUrl(item, 8);

    log("1. TEXT:", text, "Url:", logUrl, "link:", href);


    const shorts =
    item2.closest("ytd-reel-item-renderer") ||
    item2.closest("ytm-reel-item-renderer");

if (shorts) {
    log("Hiding shorts, TEXT:", text);
    hide(shorts);
    return;
}

    
    if (!shouldProcessItem({ text, href, listItem })) return;

    
    log("2. TEXT:", text, "Url:", logUrl, "link:", href);

    hide(item);
}

document.querySelectorAll("a").forEach(processItem);


WatchHtml().onNewElements(elements =>
    elements.forEach(processItem)
);


/*
item2.closest("yt-lockup-view-model") ||
    item2.closest("ytm-media-item") ||
    item2.closest("ytm-video-with-context-renderer") ||
    
function getFilterContainers(root = document) {
    return root.querySelectorAll(`
        ytd-video-renderer,
        ytd-compact-video-renderer,
        ytd-grid-video-renderer,
        ytd-rich-item-renderer,
        ytd-watch-card-compact-video-renderer,
        ytd-channel-video-player-renderer,
        ytd-shelf-renderer,
        ytd-reel-shelf-renderer,
        ytd-horizontal-card-list-renderer,
        ytd-universal-watch-card-renderer,
        ytd-radio-renderer,
        ytd-channel-renderer,
        ytd-grid-channel-renderer,
        ytd-reel-item-renderer,
        ytm-shorts-lockup-view-model,
        ytd-movie-renderer,
        ytd-mix-renderer,
        ytd-reel-video-renderer,
        ytd-search-refinement-card-renderer,
        ytd-watch-card-rich-header-renderer,
        ytd-watch-card-section-sequence-renderer,
        yt-lockup-metadata-view-model,
        .yt-lockup-metadata-view-model-wiz,
        .yt-lockup-view-model-wiz
    `);
}
*/



