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
import org.json.JSONArray


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
                window.WindWeb = window.WindWeb || {};
                const Web = window.WindWeb;
                $code
            } catch (e) {
                const errorMsg = "JS error:" + String(e.message);
                console.log("[WINDWEB_LOG]", errorMsg);
            }
        })();
        """.trimIndent(),
        callback
    )
}

fun Any?.jsGlobalFun(name: Str, function: Str) {
   val global = "WindWeb"//dont change this!!
   this.jsFun(
       """
       window.$global = window.$global || {};

       window.$global.$name = $function
       //!!dont forget ;
       """
   )
}


//RUNS BEFORE PAGE LOADS
fun Any?.importsJS() {
    this.jsGlobalFun("log",
        """
        function(...args) {
           const msg = args
              .map(a => String(a))
              .join(" ")
              .replace(/\n/g, " | ");
                 
            console.log("[WINDWEB_LOG]", msg);
        };
        """
    )

    
    this.jsGlobalFun("findContainerHTML",
        """
        function(el, maxSteps = 3) {
           let current = el;

           for (let i = 0; i < maxSteps; i++) {
              if (!current || current === document.body) break;
              current = current.parentElement;
           }

           return current || el;
        };
        """
    )
    this.jsGlobalFun("webItemUrl",
        """
        function(el, depth = 3) {
            let cur = el;
            const stack = [];

            for (let i = 0; i < depth; i++) {
                if (!cur || cur === document.body) break;
                cur = cur.parentElement;
                if (!cur) break;

                stack.push(cur.tagName + ", " + (cur.className || ""));
            }

            return stack.reverse().join(" / ");
        };
        """
    )
    

    this.jsGlobalFun("unique",
        """
        function(list, getKey) {
            const arr = Array.from(list || []);
            const seen = new Set();

            return arr.filter(item => {
                const key = getKey ? getKey(item) : item;
                if (seen.has(key)) return false;
                seen.add(key);
                return true;
            });
        };
        """
    )
    this.jsGlobalFun("hide",
        """
        function(el) {
            if (el) {
               el.style.display = "none"
            }
        };
        """
    )
}


fun Any?.hideYoutubeChannel(channels: ListStr) {
    val jsChannels = JSONArray(channels.map { it.lowercase() }).toString()

    var testJs = getTextAsset("Test.js")
    this.jsFun(testJs)
    
    /*
    this.jsFun(
        """
        
    */
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




    


    


