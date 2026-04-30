package com.productivity.wind.Screens.Web

import com.productivity.wind.Imports.Utils.String.*
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


fun BlockingLogic(web: WebController){
	fun Block(){
		goTo("WebHome")
		Bar.Url = "google.com"
	}
	/*


	web.doUpdateVisitedHistory { url, isReload ->
		Bar.Url = url ?: "https://www.google.com"

		Bar.Url.blog("BarUrl")
		web.blockImages({Bar.Url.hasAny("youtube.com")})

		if (Bar.Url.hasAny("melrobbins.com/podcast/", "mfmpod.com", "youtube.com")) {
			//Allow
		} else {
			Block()
		}
	}
	*/


	/*

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
		if (it.image) return@shouldInterceptRequest null
		
		var x = WebUtils.IsGood(it)
		if (x == WebAction.Allow) return@shouldInterceptRequest null
		if (x == WebAction.Block) Block()
		if (x == WebAction.Blot) return@shouldInterceptRequest EmptyWebResource()
		
		//: WebAction



		
		return@shouldInterceptRequest null
	}
	*/
}













