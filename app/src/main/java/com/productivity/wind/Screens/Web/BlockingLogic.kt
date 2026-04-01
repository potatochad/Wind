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


fun BlockingLogic(web: WebController){
	web.shouldInterceptRequest {

		val url = it.url.toString()

		if (url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".png") ||
				url.endsWith(".gif") || url.endsWith(".webp") || url.endsWith(".ico")) {
			return@shouldInterceptRequest null
		}

		log("URL: ${url} | BadWord: ${WebUtils.FindBadWord(url)} | GoodWord: ${WebUtils.FindGoodWord(url)}")

		if (WebUtils.HasBadWord(url, { it.locked })){
			Bar.Url = "https://www.google.com"
		}

		if (WebUtils.HasBadWord(url)){
			if (!WebUtils.HasGoodWord(url)){
				Bar.Url = "https://www.google.com"
				
				return@shouldInterceptRequest WebResourceResponse(
					"text/plain",
					"UTF-8",
					ByteArrayInputStream("".toByteArray())
				)
			}
		}
						
		return@shouldInterceptRequest null
	}

    web.doUpdateVisitedHistory { url, isReload ->
		Bar.Url = url ?: "https://www.google.com"

		log("URL: ${url} | BadWord: ${WebUtils.FindBadWord(url.toString())} | GoodWord: ${WebUtils.FindGoodWord(url.toString())}")

		if (WebUtils.HasBadWord(url, { it.locked })){
			Bar.Url = "https://www.google.com"
		}
			
		if (WebUtils.HasBadWord(Bar.Url)){
			if (!WebUtils.HasGoodWord(Bar.Url)){
				Bar.Url = "https://www.google.com"
			}
		}
    }
}













