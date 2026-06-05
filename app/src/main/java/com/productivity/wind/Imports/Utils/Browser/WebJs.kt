package com.productivity.wind.Imports.Utils.Browser
 
import android.annotation.SuppressLint
import android.content.*
import androidx.compose.runtime.*
import androidx.activity.compose.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.ui.viewinterop.*
import android.view.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import com.productivity.wind.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.productivity.wind.R
import com.productivity.wind.Imports.*
import android.webkit.*
import android.graphics.*
import kotlinx.coroutines.*
import com.productivity.wind.Imports.Utils.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.webkit.WebResourceRequest
import com.productivity.wind.Imports.Utils.String.*



fun Any?.js(code: Str, callback: ((Str?) -> Unit)? = null) {
    toWeb(this)?.evaluateJavascript(code) { result ->
        callback?.invoke(result)
    }
}
fun Any?.jsFun(code: Str, callback: ((Str?) -> Unit)? = null) {
    this.js(
        """
        (function() {
            try {
                $code
            } catch (e) {
                WindWeb.log("JS error:", e.message);
            }
        })();
        """.trimIndent(),
        callback
    )
}


//RUNS BEFORE PAGE LOADS
fun Any?.importsJS() {
    this.jsFun(
        """
        window.WindWeb = window.WindWeb || {};

        window.WindWeb.log = function(...args) {
           const msg = args
              .map(a => String(a))
              .join(" ")
              .replace(/\n/g, " | ");
                 
            console.log("[WINDWEB_LOG]", msg);
        };
        """
    )

    this.jsFun(
        """
        window.WindWeb = window.WindWeb || {};

        window.WindWeb.findContainerHTML = function(el, maxSteps = 3) {
           let current = el;

           for (let i = 0; i < maxSteps; i++) {
              if (!current || current === document.body) break;
              current = current.parentElement;
           }

           return current || el;
        };
        """
    )
    this.jsFun(
        """
        window.WindWeb = window.WindWeb || {};

        window.WindWeb.itemCustomUrl = function(el) {
           const container1 = window.WindWeb.findContainerHTML(el, 1);
           const container2 = window.WindWeb.findContainerHTML(el, 2);
           const container3 = window.WindWeb.findContainerHTML(el, 3);
           const container4 = window.WindWeb.findContainerHTML(el, 4);   
           const urls = [ container1, container2, container3, container4 ];
           
           const divUrl = urls
              .map(a => String(a))
              .join("/")

           return divUrl;
        };
        """
    )
}


fun Any?.hideYoutubeChannel(channel: Str) {
    this.jsFun(
        """
        WindWeb.log("Channel blocker starting...");

        const target = "$channel".toLowerCase();
        let running = false;

        function scan() {
            // prevent overlap
            if (running) return;
            running = true;

            try {
                const items = document.querySelectorAll('a');

                items.forEach((item) => {
                    const href = item.href || "";
                    const text = (item.innerText || "").toLowerCase();

                    // skip empty fast
                    if (!text) return;
                    if (!href) return;

                    WindWeb.log("link:", href, "TEXT:", text);

                    // only work when needed
                    if (text.includes(target)) {
                        const container1 = window.WindWeb.findContainerHTML(item, 1);
                        const container2 = window.WindWeb.findContainerHTML(item, 2);
                        const container3 = window.WindWeb.findContainerHTML(item, 3);
                        const container4 = window.WindWeb.findContainerHTML(item, 4);


                        if (container1) {
                            WindWeb.log(
                               "FOUND CONTAINER:",
                               container1.tagName,
                               container1.className
                            );
                            
                            
                            // container1.style.display = "none";
                        }
                    }
                });

            } catch (e) {
                WindWeb.log("Scan error:", e.message);
            }

            running = false;
        }

        // first run
        scan();

        // slower interval
        setInterval(scan, 5000);
        """
    )
}


fun Any?.forceGoogleInputFocus() {
    this.js(
        """
        (function() {

            let input =
                document.querySelector('textarea[name="q"]') ||
                document.querySelector('input[name="q"]') ||
                document.querySelector('[role="combobox"]');

            if (!input) return "not found";

            input.scrollIntoView({behavior: "smooth", block: "center"});

            input.focus();
            input.click();

            input.dispatchEvent(new Event('focus', {bubbles: true}));

            return "focused";

        })();
        """.trimIndent()
    ) {
        log("JS result: $it")

        toWeb(this)?.showKeyboard()
    }
}


fun Any?.allVisibleText(done: DoStr) {
    this.js(
        """
        (function(){

            // Create a full copy of the page
            const bodyCopy = document.body.cloneNode(true);

            // Clean the copy only (not the real page)
            bodyCopy.querySelectorAll('style, script, link, noscript').forEach(e => e.remove());

            function getText(node){
                let text = "";

                node.childNodes.forEach(n => {

                    if(n.nodeType === Node.COMMENT_NODE) return;

                    if(n.nodeType === Node.TEXT_NODE){
                        let t = n.textContent.trim();
                        if(t.length > 0){
                            text += t + "\n";
                        }
                    }
                    else if(n.nodeType === Node.ELEMENT_NODE){
                        text += getText(n);
                    }

                });

                return text;
            }

            return getText(bodyCopy);

        })();
        """.trimIndent()
    ) { result ->

        val cleaned = result
            ?.removePrefix("\"")
            ?.removeSuffix("\"")
            ?.replace("\\n", "\n")
            ?.replace("\\\"", "\"")

        done(cleaned ?: "")
    }
 }


fun Any?.html(done: DoStr) {
    this.js(
        """
        (function(){
        // Clone the body so we don't break the live page
        var clone = document.body.cloneNode(true);

        // Remove unwanted tags inside body
        clone.querySelectorAll('style, script, link').forEach(e => e.remove());

        // Remove all images
        clone.querySelectorAll('img').forEach(e => e.remove());
        clone.querySelectorAll('svg').forEach(e => e.remove());

        // Remove inline style attributes
        clone.querySelectorAll('*').forEach(e => e.removeAttribute('style'));

        // Return only cleaned body HTML
        return clone.outerHTML;
    })();
    """.trimIndent()
    ) { html ->
        val cleaned = html
        ?.removePrefix("\"")
        ?.removeSuffix("\"")
        ?.replace("\\u003C", "<")
        ?.replace("\\u003E", ">")
        ?.replace("\\\"", "\"")
        ?.replace("\\n", "\n")
        
        done(cleaned ?: "")
    }
}



fun Any?.gray(x: Float) {
    this.js(
        "document.body.style.filter = 'grayscale(${x}%)';",
        null
    )
}




    


