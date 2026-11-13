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

//RUNS BEFORE PAGE LOADS, but on finished
fun Any?.importsJS() {
    val media = getTextAsset("Media.js")

    this.jsFun(media)
}


fun Any?.youtubeFilter(
    allowOnly: ListStr? = null,
    block: ListStr? = null,
) {
    val allow1 = JSONArray(allowOnly?.map { it.lowercase() } ?: emptyList<String>()).toString()
    val block1 = JSONArray(block?.map { it.lowercase() } ?: emptyList<String>()).toString()

    var filter = getTextAsset("YoutubeFilter.js")
    var jsImports = getTextAsset("Imports.js")
    
    
    this.jsFun(
        """
        $jsImports
        const blockTargets = $block1;
        const allowTargets = $allow1;
        $filter
        """
    )
}

fun Any?.playingMedia(callback: ((Str?) -> Unit)?) {
    this.jsFun(
        "return Web.getPlayingMedia();",
        callback
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




    


    


