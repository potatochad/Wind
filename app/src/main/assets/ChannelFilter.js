function WatchHtml(root = document.body) {
    const listeners = new Set();

    const obs = new MutationObserver((mutations) => {
        const newElements = [];

        for (const m of mutations) {
            for (const node of m.addedNodes) {
                if (node.nodeType !== 1) continue;

                if (node.tagName === "A") {
                    newElements.push(node);
                }

                const anchors = node.querySelectorAll?.("a");
                if (anchors?.length) {
                    newElements.push(...anchors);
                }
            }
        }

        if (newElements.length) {
            for (const cb of listeners) {
                cb(newElements);
            }
        }
    });

    obs.observe(root, {
        childList: true,
        subtree: true
    });

    return {
        onNewElements(cb) {
            listeners.add(cb);
        },
        disconnect() {
            obs.disconnect();
            listeners.clear();
        }
    };
}


function getText(item) {
    return (
        item.innerText ||
        item.getAttribute("title") ||
        item.getAttribute("aria-label") ||
        item.textContent ||
        ""
    ).trim().toLowerCase();
}

function GetCardHtml(el) {
    /*
    const container = el.closest([
        "ytd-video-renderer",
        "ytd-rich-item-renderer",
        "ytd-compact-video-renderer",
        "ytd-grid-video-renderer",
        "ytd-reel-item-renderer",
        "ytd-mix-renderer",
        "ytd-playlist-video-renderer",

        "ytm-media-item",
        "ytm-video-with-context-renderer",
        "ytm-compact-video-renderer",
        "ytm-rich-item-renderer",
        "ytm-item-section-renderer",
        "ytm-shelf-renderer",
        "ytm-section-list-renderer",
        "ytm-browse",

        "ytm-shorts-lockup-view-model",
        "ytm-reel-video-renderer",
        "ytm-reel-item-view-model",

        "yt-list-item-view-model",
        "yt-lockup-view-model",
        "yt-lockup-view-model-wiz"
    ].join(","));

    // fallback to link itself (your href case)
    return container || 
    */
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

setTimeout(() => {
    buildDomTrace(document.querySelector("ytd-app"));
}, 2000);

function processItem(item) {
    const href = item.href || "";
    const text = getText(item);
    const listItem = targets.some(t => text.includes(t));
    const logUrl = webItemUrl(item, 8);

    log("1. TEXT:", text, "Url:", logUrl, "link:", href);

    
    shouldProcessItem({ text, href, listItem });

    if (/^\d+(?::\d+)+$/.test(text.trim())) return;

    if (!text) return;
    if (!href) return;
    if (!listItem) return;
    if (!href.includes("youtube.com/watch?v=")) return;
    if (href.startsWith("intent://")) return;

    log("2. TEXT:", text, "Url:", logUrl, "link:", href);

    

    log("Hiding:", item.parentElement?.parentElement?.parentElement?.parentElement?.tagName);   
    hide(item.parentElement?.parentElement?.parentElement?.parentElement);
    // Web.hide(container);
}

document.querySelectorAll("a").forEach(processItem);


WatchHtml().onNewElements(elements =>
    elements.forEach(processItem)
);




