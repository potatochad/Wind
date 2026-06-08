function watchHtml(root = document.body) {
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

const watcher = watchHtml();

watcher.onNewElements(elements =>
    elements.forEach(a => {
        
    })
);







//const targets = $jsChannels; //array


Web.log("FILTERRING LOGIC RUNNING");
let running = false;

function scan() {
    // prevent overlap
    if (running) return;
    running = true;

    const items = document.querySelectorAll('a');
                
    items.forEach((item) => {
        const href = item.href || "";
        const text = (item.innerText || "").toLowerCase();                
                    
        // skip duration links like 10:03, 1:02:15
        if (/^\d+(?::\d+)+$/.test(text.trim())) return;

        // skip empty fast
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
    });
    running = false;
}
// first run
scan();

// slower interval
setInterval(scan, 5000);




