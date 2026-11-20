//you cannot make js renamed: its ugly

package com.productivity.wind.Imports.Utils.Browser

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.*
import org.json.JSONArray


fun Any?.js(code: Str, callback: ((Str?) -> Unit)? = null) {
    toWeb(this)?.evaluateJavascript(code) { result ->
        callback?.invoke(result)
    }
}

//every thing default local, not global
fun Any?.jsFun(code: Str, callback: ((Str?) -> Unit)? = null) {
    this.js(
        """
        (function() {
            try {
                window.WindWeb = window.WindWeb || {};
                const Web = window.WindWeb;
                $code
            } catch (e) {
                const errorMsg = "JS error: " + String(e.message);
                console.log("[WINDWEB_LOG]", errorMsg);
            }
        })();
        """.trimIndent(),
        callback
    )
}


fun Any?.playingMedia(callback: ((Str?) -> Unit)?) {
    this.jsFun(
        "return Web.getPlayingMedia();",
        callback
    )
}
fun Any?.playingAudioGoTo(time: Int = 120) {
    this.jsFun(
        """Web.controlMedia("seek", $time);"""
    )
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




    


    


