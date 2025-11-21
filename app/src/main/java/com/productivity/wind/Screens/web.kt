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



var WebUrl by m("")
@Composable
fun Web(){
    val webView = r { mutableStateOf<WebView?>(null) }
    val badWords = mutableListOf<Str>()
    var on = r_m(yes)

    RunOnce(Bar.badWords) {
        Bar.badWords.forEach {
            badWords += it.word
        }
    }

    
    WebUrl = "${UrlShort(webView.value?.url ?: "https://google.com")}"
       


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
        (function() {
            const hide = () => {
                const sidebar = document.getElementById("related");
                if (sidebar) {
                    sidebar.style.display = "none";
                }
            };

            // run now
            hide();

            // run again every 500ms because YouTube reloads DOM
            if (!window._sidebarHider) {
                window._sidebarHider = setInterval(hide, 500);
            }
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

