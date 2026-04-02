package com.productivity.wind.Screens.Web

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
import com.productivity.wind.Imports.Utils.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.graphics.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.compose.ui.platform.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import android.webkit.WebResourceResponse
import java.io.ByteArrayInputStream
import com.productivity.wind.Imports.Utils.Browser.*

data class BlockAction(
	var power: Int = 1,
	var type: WebType,
){
	
}


fun BlockingLogic(web: WebController){
	fun Block(){
		goTo("WebHome")
		Bar.Url = "google.com"
	}

	web.doUpdateVisitedHistory { url, isReload ->
		Bar.Url = url ?: "https://www.google.com"

		if (WebUtils.HasBadWord(Bar.Url, { it.locked })) Block()
			
		if (WebUtils.HasBadWord(Bar.Url)){
			if (!WebUtils.HasGoodWord(Bar.Url)){
				Block()
			}
		}
	}
	
	web.shouldInterceptRequest {
		val url = it.url.toString()

		if (url.image) return@shouldInterceptRequest null

		val actions = mList<BlockAction>()
		WebUtils.EachFoundBadWord(url){
			if (it.locked) {//‼️ forget this
				Block()
			}
			if (it.type == WebType.Blot){
				actions.add {
					type = WebType.Blot
				}
				return@EachFoundBadWord
			}
			if (it.type == WebType.KeyWord){
				actions.add {
					type = WebType.KeyWord
				}
				return@EachFoundBadWord
			}
		}

		
		if (actions.empty) return@shouldInterceptRequest null

		if (!WebUtils.HasGoodWord(url)){
			Block()
			return@shouldInterceptRequest null
		}
		
						
		return@shouldInterceptRequest null
	}
}













