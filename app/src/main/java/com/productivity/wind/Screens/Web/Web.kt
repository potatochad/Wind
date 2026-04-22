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
import com.productivity.wind.Screens.*
import com.productivity.wind.Imports.Utils.Browser.*



@Composable
fun QuickAccessItem(
    title: String,
    url: String,
    icon: ImageVector,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .size(90.dp)
            .padding(6.dp)
            .clickable { onClick(url) },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
    }
}



@Composable
fun WebHome(){
	var ctx = LocalContext.current
    val web = r { WebController(ctx) }
	val items = listOf(
        Triple("YouTube", "https://youtube.com", Icons.Default.PlayArrow),
        Triple("Google", "https://google.com", Icons.Default.Search),
        Triple("Twitter", "https://twitter.com", Icons.Default.Share),
        Triple("GitHub", "https://github.com", Icons.Default.Code)
    )
	
    LazyScreen(
        top = {
            End {
                AppItem.Add {
                    goTo("WebKeywords")
                }
            }
        },
        scroll = no,
		onBackIcon = { goTo("Main") }
    ) {
		
        LazzyRow(Mod.centerX.space(5)) {
			Text("Home".size(28.sp))
		}
		WebProps.Home.Input(web)

        move(h = 16)

        // Quick access cards (like Google shortcuts)
		Text("Recent".size(12.sp).gray(), modifier = Mod.space(start = 8))
		
		
		
		LazzyRow(Mod.centerX.space(5)) {
			LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(10.dp)
    ) {
        items(items) { item ->

            QuickAccessItem(
                title = item.first,
                url = item.second,
                icon = item.third
            ) { clickedUrl ->
                Bar.Url = clickedUrl
                goTo("Web")
            }
        }
			}
		}



		
    }
}


@Composable
fun Web(){
    //returns class: WebController
    var ctx = LocalContext.current
    val web = r { WebController(ctx) }

	RunOnce {
		web.applyFancySettings()
		web.url(Bar.Url)
	}
    
    WebProps.WebPointTimer()

	BlockingLogic(web)

    LazyScreen(
        top = {
			WebProps.TopBarInput(web)
            End {
                Row{
                    Icon.Reload{ 
                        web.reload()
                    } 
                    AppItem.Add {
                        goTo("WebKeywords")
                    }
                }
            }
        },
        scroll = no,
        dividerM = no,
		onBackIcon = { goTo("Main") }
    ) {
        WebXml(web)
    }
}








