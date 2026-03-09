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
import com.productivity.wind.Screens.*


@Composable
fun Web(){
    //returns class: WebController
    var ctx = LocalContext.current
    val web = r { WebController(ctx) }
    
    web.applyFancySettings()
    
    web.url(Bar.Url)
        
    Item.WebPointTimer()

    LazyScreen(
        top = {
            Text(" Points ${Bar.funTime}")
    
            End {
                Row{
                    Icon.Reload{ 
                        web.reload()
                    } 
                    Icon.Delete {
                        Bar.Url = "https://google.com"
                    }
                    Item.Add {
                        goTo("WebKeywords")
                    }
                }
            }
        },
        scroll = no,
        DividerPadding = no,
		onBackClick = { goTo("Main") }
    ) {
        WebXml(web)
    }
}








