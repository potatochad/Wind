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
                BlockKeywords(webView, badWords)
            },
            onProgressChanged = {
                BlockKeywords(webView, badWords)
            },
            onPageStarted = {
                BlockKeywords(webView, badWords)
            },
            onPageFinished = { 
                BlockKeywords(webView, badWords)
            }
        )
    }
}


fun BlockYoutubeChannel(webViewState: m_<WebView?>) {
    val webView = webViewState.it ?: return

    // Get YouTube page JSON
    val jsGetData = """
        (function() {
            return JSON.stringify(window["ytInitialData"] || {});
        })();
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
                } else if (item && typeof item === 'object' && !visited.has(item) && !(item instanceof Element) && !(item instanceof Node) && item !== window) {
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
            } else if (value && typeof value === 'object' && !visited.has(value) && !(value instanceof Element) && !(value instanceof Node) && value !== window) {
                queue.push(value);
            }

            if (result.handle && result.id) break;
        }
    }

    return result;
}
    """.trimIndent()
    
try{
    webView.evaluateJavascript(jsGetData) { jsonResult ->
        
        } catch (e: Exception) {
            log("$e")
        }
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

