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
