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



fun WebView.forceGoogleInputFocus() {
    this.evaluateJavascript(
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

        this.showKeyboard()
    }
}

fun WebView.allVisibleText(done: (String) -> Unit) {
    this.evaluateJavascript(
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


fun WebController.allVisibleText(done: (String) -> Unit) {
    this.webView.evaluateJavascript(
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



fun WebView.html(done: (String) -> Unit) {
    this.evaluateJavascript(
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


fun WebController.html(done: (String) -> Unit) {
    this.webView.evaluateJavascript(
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


fun WebView.gray(x: Float) {
    this.evaluateJavascript(
        "document.body.style.filter = 'grayscale(${x}%)';",
        null
    )
}
fun WebController.gray(x: Float) {
    this.webView.evaluateJavascript(
        "document.body.style.filter = 'grayscale(${x}%)';",
        null
    )
}




