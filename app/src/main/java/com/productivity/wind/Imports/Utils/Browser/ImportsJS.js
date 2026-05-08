
window.WindWeb = {
    log(msg) {
        console.log("[WINDWEB_LOG] " + msg);
    },

    findContainerHTML(el) {
        let current = el;
        let best = el;

        while (current && current !== document.body) {

            const style = window.getComputedStyle(current);

            const hasLayout =
                style.display !== "inline" &&
                style.visibility !== "hidden";

            const hasChildren =
                current.children && current.children.length >= 2;

            const hasContent =
                (current.innerText || "").trim().length > 10;

            const isBigEnough =
                current.getBoundingClientRect().height > 20;

            if (hasLayout && hasChildren && hasContent && isBigEnough) {
                best = current;
            }

            current = current.parentElement;
        }

        return best;
    }
};




