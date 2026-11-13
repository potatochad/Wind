package com.productivity.wind.Imports.Utils.Js



class JsButKotlin {
    fun function(name: Str, body: Str): Str =
        "function $name() { $body }"

    fun custom(js: Str) {
        runJs(js)//error, no such thing
    }

    fun htmlElements(selector: Str): Str =
        "document.querySelectorAll('*')"

    fun call(target: Str, method: Str): Str =
        "$target.$method()"
}

/*
function watchMedia(onUpdate) {
    const watched = new WeakSet();

    function attach(el) {
        if (watched.has(el)) return;
        watched.add(el);

        const update = event => onUpdate({
            event: event.type,
            type: el.tagName.toLowerCase(),
            title: document.title,
            src: el.currentSrc || el.src,
            paused: el.paused,
            ended: el.ended,
            currentTime: el.currentTime,
            duration: Number.isFinite(el.duration) ? el.duration : null
        });

        [
            "play", "playing", "pause", "ended",
            "loadedmetadata", "durationchange"
        ].forEach(e => el.addEventListener(e, update));

        update({ type: "detected" });
    }

    function scan(root) {
        if (root.matches?.("audio, video")) attach(root);
        root.querySelectorAll?.("audio, video").forEach(attach);
    }

    scan(document);

    const observer = new MutationObserver(records => {
        records.forEach(record =>
            record.addedNodes.forEach(node => {
                if (node.nodeType === 1) scan(node);
            })
        );
    });

    observer.observe(document.documentElement, {
        childList: true,
        subtree: true
    });

    return () => observer.disconnect();
}
*/
