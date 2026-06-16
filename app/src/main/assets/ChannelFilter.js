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




