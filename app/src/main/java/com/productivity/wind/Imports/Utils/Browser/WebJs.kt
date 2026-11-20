//you cannot make js renamed: its ugly

package com.productivity.wind.Imports.Utils.Browser

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.ToX.*
import com.productivity.wind.Imports.Utils.*
import org.json.JSONArray


//RUNS BEFORE PAGE LOADS
fun Any?.importsJS() {
    val media = getTextAsset("Media.js")

    this.jsFun(media)
}

//RUNS BEFORE PAGE LOADS
fun Any?.backgroundAudio(){
   this.playingMedia {
      if (it != null) Vlog("WebMedia: ${WebMedia(it)}")
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





