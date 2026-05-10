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
}



fun Any?.hideYoutubeChannel(channel: Str) {
    this.jsFun(
        """
        WindWeb.log("Channel blocker starting...");

        const target = "$channel".toLowerCase();

        function scan() {
            const items = document.querySelectorAll('a');

            items.forEach((item, index) => {
                const href = item.href || "";
                const text = item.innerText || "";

                WindWeb.log(index, ":", href, "TEXT:", text);

                const container = window.WindWeb.findContainerHTML(item);    

                if (container) {
                    WindWeb.log(
                        "FOUND CONTAINER:",
                        container.tagName,
                        container.className
                    );
                }
            });
        }

        // run immediately
        scan();

        // keep scanning (YouTube loads late)
        setInterval(scan, 3000);
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




    


