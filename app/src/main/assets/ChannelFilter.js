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









//const targets = $jsChannels; //array


Web.log("FILTERRING LOGIC RUNNING");

function processItem(item) {
    const href = item.href || "";
    const text = (item.innerText || "").toLowerCase();

    if (/^\d+(?::\d+)+$/.test(text.trim())) return;

    if (!href.includes("youtube.com/watch?v=")) return;
    if (!text) return;
    if (!href) return;
    if (href.startsWith("intent://")) return;

    const hit = targets.some(t => text.includes(t));
    if (!hit) return;

    Web.log("link:", href, "TEXT:", text);

    const logUrl = Web.webItemUrl(item, 4);
    const container = Web.findContainerHTML(item, 4);

    Web.log("Hiding:", logUrl);

    Web.hide(container);
}

document.querySelectorAll("a").forEach(processItem);


WatchHtml().onNewElements(elements =>
    elements.forEach(processItem)
);


