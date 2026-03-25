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

fun BlockingLogic(web: WebController){
    web.doUpdateVisitedHistory { url, isReload ->
		var blocked by m(no)
		Bar.Url = url ?: "https://www.google.com"
		
		WebUtils.logs.ShouldBlock(txt, Bar.Url)
		
        web.allVisibleText { txt ->
            if (WebUtils.HasBadWord(txt) || WebUtils.HasBadWord(Bar.Url)){
				if (!WebUtils.HasGoodWord(txt)){
					if (!WebUtils.HasGoodWord(Bar.Url)){
						WebUtils.logs.Block(txt, Bar.Url)
						
						blocked = yes
						Bar.Url = ""
						goTo("WebHome")
						return@allVisibleText
					}
                }
			}
        }
    }
}













