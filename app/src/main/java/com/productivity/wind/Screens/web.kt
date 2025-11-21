package com.productivity.wind.Screens

import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import com.productivity.wind.*
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.productivity.wind.R
import com.productivity.wind.Imports.*
import android.annotation.SuppressLint
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.graphics.Bitmap
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.compose.*
import com.productivity.wind.Imports.Data.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.text.style.*


fun hideYoutubeShorts(webViewState: m_<WebView?>) {
    val webView = webViewState.it ?: return

    val js = """
        (function() {
            if (window._hideShortsInstalled) return;
            window._hideShortsInstalled = true;

            const css = `
                /* Shorts shelf on homepage */
                ytd-rich-shelf-renderer[is-shorts],
                ytd-rich-shelf-renderer:has(#shorts),
                ytd-reel-shelf-renderer,
                ytd-reel-player-renderer,

                /* Shorts in feed */
                ytd-rich-item-renderer:has(a[href*="/shorts/"]),
                ytd-video-renderer:has(a[href*="/shorts/"]),

                /* Shorts buttons */
                a[href*="/shorts/"],
                ytd-guide-entry-renderer[role="shorts"],
                ytd-mini-guide-entry-renderer[role="shorts"],

                /* Shorts on channel page */
                ytd-reel-shelf-renderer,
                #shorts-container,
                #shorts-player,
                .ytd-reel-shelf-renderer,

                /* Shorts player */
                #player-container ytd-shorts,
                ytd-shorts,
                #shorts {
                    display: none !important;
                    visibility: hidden !important;
                    height: 0 !important;
                    max-height: 0 !important;
                    overflow: hidden !important;
                }
            `;

            // Add CSS
            const style = document.createElement("style");
            style.id = "hide-shorts-style";
            style.appendChild(document.createTextNode(css));
            document.head.appendChild(style);

            // Mutation observer to kill new Shorts elements
            const kill = () => {
                document.querySelectorAll('a[href*="/shorts/"]').forEach(e => e.remove());
                document.querySelectorAll('#shorts, ytd-shorts, ytd-reel-shelf-renderer')
                    .forEach(e => e.remove());
            };

            kill();
            setInterval(kill, 600);  // YouTube loves recreating shorts
        })();
    """.trimIndent()

    webView.evaluateJavascript(js, null)
}


var WebUrl by m("")
@Composable
fun Web(){
    val webView = r { mutableStateOf<WebView?>(null) }
    val badWords = mutableListOf<Str>()
    var on = r_m(yes)

    RunOnce {
        webView.it?.loadUrl("https://youtube.com")
        hideYoutubeShorts(webView)
    }

    RunOnce(Bar.badWords) {
        Bar.badWords.forEach {
            badWords += it.word
        }
    }

    
    WebUrl = "${UrlShort(webView.it?.url ?: "https://youtube.com")}"
       


    Item.WebPointTimer(on)

    LazyScreen(
        title = {
            Text(" Points ${Bar.funTime}")
    
            UI.End {
                Row{
                    Icon.Reload{ 
                        webView.it?.reload()
                    } 
                    Icon.Add {
                        goTo("BlockKeyword")
                    }
                }
            }
        },
        DividerPadding = no,
    ) {
        WebXml(
            webViewState = webView,
            onUrlChanged = {
                getYoutubeVideoTitle(webView)
                getYoutubeVideoChannel(webView)
                BlockKeywords(webView, badWords)
                hideYoutubeSidebar(webView)
            },
            onProgressChanged = {
                getYoutubeVideoTitle(webView)
                getYoutubeVideoChannel(webView)
                BlockKeywords(webView, badWords)
                hideYoutubeSidebar(webView)
            },
            onPageStarted = {
                getYoutubeVideoTitle(webView)
                getYoutubeVideoChannel(webView)
                BlockKeywords(webView, badWords)
                hideYoutubeSidebar(webView)
            },
            onPageFinished = { 
                getYoutubeVideoTitle(webView)
                getYoutubeVideoChannel(webView)
                BlockKeywords(webView, badWords)
                hideYoutubeSidebar(webView)
            }
        )
    }
}

fun getYoutubeVideoTitle(webViewState: m_<WebView?>) {
    val webView = webViewState.it ?: return

val jsCode = """
(function() {
    function extractHandle(str) {
        const m = str.match(/@[\w\d_]+/);
        return m ? m[0] : null;
    }

    function extractChannelId(str) {
        const m = str.match(/UC[\w\d_-]{22}/);
        return m ? m[0] : null;
    }

    function scan(root) {
        const result = { handle: '', id: '', title: '' };
        if (!root || typeof root !== 'object') return result;

        const visited = new Set();
        const queue = [root];
        const MAX = 3000;
        let i = 0;

        while (queue.length && i < MAX && (!result.handle || !result.id || !result.title)) {
            const cur = queue.shift();
            i++;

            if (!cur || typeof cur !== 'object') continue;
            if (visited.has(cur)) continue;
            visited.add(cur);

            if (Array.isArray(cur)) {
                for (const item of cur) {
                    if (typeof item === 'string') {
                        if (!result.handle) {
                            const h = extractHandle(item);
                            if (h) result.handle = h;
                        }
                        if (!result.id) {
                            const id = extractChannelId(item);
                            if (id) result.id = id;
                        }
                        if (!result.title && item.length < 200 && item.trim().length > 0) {
                            result.title = item;
                        }
                    } else if (item && typeof item === 'object') {
                        queue.push(item);
                    }
                }
                continue;
            }

            for (const v of Object.values(cur)) {
                if (typeof v === 'string') {
                    if (!result.handle) {
                        const h = extractHandle(v);
                        if (h) result.handle = h;
                    }
                    if (!result.id) {
                        const id = extractChannelId(v);
                        if (id) result.id = id;
                    }
                    if (!result.title && v.length < 200 && v.trim().length > 0) {
                        result.title = v;
                    }
                } else if (v && typeof v === 'object') {
                    queue.push(v);
                }
            }
        }

        return result;
    }

    return scan(window["ytInitialData"] || {});
})();
""".trimIndent()
try {
        webView.evaluateJavascript(jsCode) { jsonResult ->
            // jsonResult is a JSON string like: {"handle":"@xyz","id":"UCabc..."}
            Vlog("Channel info: $jsonResult")
            // Here you can parse it and do your blocking logic
        }
    } catch (e: Exception) {
        Vlog("Error evaluating JS: $e")
    }
}

fun hideYoutubeSidebar(webViewState: m_<WebView?>) {
    val webView = webViewState.it ?: return

    val js = """
        (function(){
            // Only install once
            if (window._killYoutubeSidebarInstalled) return;
            window._killYoutubeSidebarInstalled = true;

            // 1) Inject strong CSS rule to hide common sidebar elements
            const css = `
                /* main related/secondary containers */
                #related, #secondary, ytd-watch-next-secondary-results-renderer, ytd-browse, ytd-two-column-browse-results-renderer {
                    display: none !important;
                    visibility: hidden !important;
                    width: 0 !important;
                    max-width: 0 !important;
                    overflow: hidden !important;
                }
                /* homepage/side panels */
                ytd-rich-grid-renderer, ytd-rich-section-renderer, #contents > ytd-rich-item-renderer {
                    display: none !important;
                }
            `;
            let style = document.getElementById('__hideYoutubeSidebarStyle');
            if (!style) {
                style = document.createElement('style');
                style.id = '__hideYoutubeSidebarStyle';
                style.appendChild(document.createTextNode(css));
                document.head && document.head.appendChild(style);
            }

            // 2) Helper to remove nodes that slip through
            const selectors = [
                '#related',
                '#secondary',
                'ytd-watch-next-secondary-results-renderer',
                'ytd-compact-video-renderer',
                'ytd-rich-grid-renderer',
                'ytd-rich-section-renderer',
                'ytd-browse'
            ];

            function zapOnce() {
                let found = false;
                for (const sel of selectors) {
                    const nodes = document.querySelectorAll(sel);
                    nodes.forEach(n => {
                        // hide aggressively
                        n.style.setProperty('display', 'none', 'important');
                        n.style.setProperty('visibility', 'hidden', 'important');
                        n.remove && n.remove(); // remove if possible
                        found = true;
                    });
                }
                return found;
            }

            // run immediately a few times (page may still be constructing)
            zapOnce();
            setTimeout(zapOnce, 200);
            setTimeout(zapOnce, 700);

            // 3) MutationObserver to catch dynamically added recommendation nodes
            if (!window.__hideYoutubeSidebarObserver) {
                window.__hideYoutubeSidebarObserver = new MutationObserver(mutations => {
                    for (const m of mutations) {
                        if (m.addedNodes && m.addedNodes.length) {
                            zapOnce();
                        }
                    }
                });

                try {
                    window.__hideYoutubeSidebarObserver.observe(document.documentElement || document.body, {
                        childList: true,
                        subtree: true
                    });
                } catch (e) {
                    // fallback: periodic zap
                    if (!window.__hideYoutubeSidebarInterval) {
                        window.__hideYoutubeSidebarInterval = setInterval(zapOnce, 700);
                    }
                }
            }

            // 4) Grab SPA nav events used by YouTube to re-apply on navigation
            function reapplyOnNav() {
                zapOnce();
                // sometimes yt fires special events
                document.dispatchEvent(new Event('hide-sidebar-check'));
            }

            // Listen to known YouTube navigation events
            window.addEventListener('yt-navigate-finish', reapplyOnNav);
            window.addEventListener('yt-page-data-updated', reapplyOnNav);
            window.addEventListener('yt-player-updated', reapplyOnNav);
            // general history changes
            const pushState = history.pushState;
            history.pushState = function() {
                pushState.apply(this, arguments);
                setTimeout(reapplyOnNav, 200);
            };
            const replaceState = history.replaceState;
            history.replaceState = function() {
                replaceState.apply(this, arguments);
                setTimeout(reapplyOnNav, 200);
            };

            // 5) expose a manual cleanup function (optional)
            window.killYoutubeSidebar = {
                remove: function() {
                    if (window.__hideYoutubeSidebarObserver) {
                        try { window.__hideYoutubeSidebarObserver.disconnect(); } catch(e){}
                        window.__hideYoutubeSidebarObserver = null;
                    }
                    if (window.__hideYoutubeSidebarInterval) {
                        clearInterval(window.__hideYoutubeSidebarInterval);
                        window.__hideYoutubeSidebarInterval = null;
                    }
                    const s = document.getElementById('__hideYoutubeSidebarStyle');
                    if (s) s.remove();
                    window._killYoutubeSidebarInstalled = false;
                }
            };

        })();
    """.trimIndent()

    webView.evaluateJavascript(js, null)
}



fun getYoutubeVideoChannel(webViewState: m_<WebView?>) {
    val webView = webViewState.it ?: return

    val jsCode = """
        (function() {
            function extractHandleFromString(str) {
                const match = str.match(/@[\w\d_]+/);
                return match ? match[0] : null;
            }

            function extractChannelIdFromString(str) {
                const match = str.match(/UC[\w\d_-]{22}/);
                return match ? match[0] : null;
            }

            function scanDataForChannelIdentifiers(root) {
                const result = { handle: '', id: '' };
                if (!root || typeof root !== 'object') return result;

                const visited = new Set();
                const queue = [root];
                const MAX_ITERATIONS = 2000;
                let iterations = 0;

                while (queue.length && iterations < MAX_ITERATIONS && (!result.handle || !result.id)) {
                    const current = queue.shift();
                    iterations++;

                    if (!current || typeof current !== 'object') continue;
                    if (visited.has(current)) continue;
                    visited.add(current);

                    if (Array.isArray(current)) {
                        for (const item of current) {
                            if (typeof item === 'string') {
                                if (!result.handle) {
                                    const handle = extractHandleFromString(item);
                                    if (handle) result.handle = handle;
                                }
                                if (!result.id) {
                                    const id = extractChannelIdFromString(item);
                                    if (id) result.id = id;
                                }
                            } else if (item && typeof item === 'object') {
                                queue.push(item);
                            }
                            if (result.handle && result.id) break;
                        }
                        continue;
                    }

                    for (const value of Object.values(current)) {
                        if (typeof value === 'string') {
                            if (!result.handle) {
                                const handle = extractHandleFromString(value);
                                if (handle) result.handle = handle;
                            }
                            if (!result.id) {
                                const id = extractChannelIdFromString(value);
                                if (id) result.id = id;
                            }
                        } else if (value && typeof value === 'object') {
                            queue.push(value);
                        }

                        if (result.handle && result.id) break;
                    }
                }
                return result;
            }

            return scanDataForChannelIdentifiers(window["ytInitialData"] || {});
        })();
    """.trimIndent()

    try {
        webView.evaluateJavascript(jsCode) { jsonResult ->
            // jsonResult is a JSON string like: {"handle":"@xyz","id":"UCabc..."}
            Vlog("Channel info: $jsonResult")
            // Here you can parse it and do your blocking logic
        }
    } catch (e: Exception) {
        Vlog("Error evaluating JS: $e")
    }
}


@Composable
fun BlockKeyword() {
    var BadWord = r_m("someWord")

    RunOnce(Bar.badWords) {
        BadWord.it = "${Bar.badWords.size}"
    }

    LazyScreen(
        title = {
            Row(
                Modifier
                    .scroll(h = yes)
                    .w(App.screenWidth / 2),
            ) {
                Text(WebUrl, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            UI.End {
                Icon.MoreMenu{
                    goTo("filterExtraWeb")
                }
                Icon.Add {
                    Bar.badWords.add {
                        word = BadWord.it
                    }
                }
            }
        },
    ) {
        LazyRuleCard("If") {
            LazzyRow {
                Text("Detect ")
                Item.TskInput(BadWord, maxLetters = 12, w=120, isInt=no)
            }
        }

        LazyRuleCard("Do") {
            LazzyRow {
                Text("Go back")
            }
        }

        LazyCard {
            Bar.badWords.forEach {
                Column(Modifier.Vscroll()){


                LazzyRow {
                    Text(text = it.word)

                    Icon.Edit {
                        Item.enoughPoints{
                            Bar.badWords.edit(it) {
                                word = BadWord.it
                            }
                        }

                    }

                    Icon.Delete {
                        Item.enoughPoints {
                            Bar.badWords.remove(it)
                        }
                    }
                }}
            }
        }
    }
}



@Composable
fun filterExtraWeb() {
    LazyScreen(
        title = {
            Text("fancy blocking")
        },
    ) {
        
    }
}

