//you cannot make js renamed: its ugly

package com.productivity.wind.Imports.Utils.Browser

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.UI_visible.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import org.json.*
import android.net.*
import kotlinx.coroutines.*


//RUNS BEFORE PAGE LOADS
fun Any?.importsJS() {
    val media = getTextAsset("Media.js")

    this.jsFun(media)
}

//RUNS BEFORE PAGE LOADS
fun Any?.backgroundAudio() {
    val media = MediaNotification()
    var m: WebMedia? = null

    Each(5000){
        this.playingMedia {
            if (it != null && it != "null" && it != "") {
                Vlog("it: $it")
                m = WebMedia(it)

                m?.let {
                    media.audio("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
                    
                    media.UI(
                        title = it.title
                        artist = it.artist
                        text = it.album
                        image = m.artwork.takeIf { it.isNotEmpty() }?.let { Uri.parse(it) }
                    )
                }
            }
        }
    }

    scope.launch {
        delay(2000)
        Vlog("calling media")
        m?.let {
            media.edit {
                title = it.title
                artist = it.artist
                text = it.album
                image = m.artwork.takeIf { it.isNotEmpty() }?.let { Uri.parse(it) }
            }
        }
    }
    
}

//needs a on pageLoad to work
fun Any?.youtubeFilter(
    allowOnly: ListStr? = null,
    block: ListStr? = null,
) {
    val allow1 = JSONArray(allowOnly?.map { it.lowercase() } ?: emptyList<String>()).toString()
    val block1 = JSONArray(block?.map { it.lowercase() } ?: emptyList<String>()).toString()

    var jsImports = getTextAsset("Imports.js")
    var filter = getTextAsset("YoutubeFilter.js")
    
    
    this.jsFun(
        """
        $jsImports
        const blockTargets = $block1;
        const allowTargets = $allow1;
        $filter
        """
    )
}





