package com.productivity.wind.Screens.Task
  
import android.annotation.SuppressLint
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.*
import com.productivity.wind.R
import com.productivity.wind.Imports.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import androidx.compose.foundation.text.selection.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.geometry.*
import com.productivity.wind.Imports.UI_visible.*
import com.productivity.wind.Screens.*
 

object TskProp {
	
	@Composable
	fun PickApp(show: mBool =m(yes), Do: DoStr ={}) {
		var appList by r_m<List<Pair<AppInfo, Drawable?>>>(emptyList())
		var loading by r_m(no)

		RunOnce {
			runHeavyTask(
				task = {
					getApps()
						.filter { getAppPkg(it) != AppPkg }
						.map { app ->
							val icon = getAppIcon(getAppPkg(app))
							app to icon
						}
				},
				onResult = {
					appList = it
					loading = no
				}
			)
		}

		LazyPopup(
           show = show,
           showCancel = no,
           showConfirm = no,
           title = "Select App",
       ){
	    	Column(Mod.h(200).Vscroll()){
		    	appList.forEach{ (app, icon) ->
			    	LazzyRow(Mod.click {
			    		Do(app.name)
			    		show.it = no
			    	}) {
			    		LazyImage(icon)
			    		move(10)
			    		Text(app.name.white())
			    	}
 		    	}
    		}
    	}
    }



	fun UpdateAppTsk(){
        Bar.apps.each {
            it.edit { it.nowTime = getTodayAppUsage(it.pkg) }

            if (it.nowTime > it.doneTime - 1 && !it.done) {
                Bar.funTime += it.worth
                it.edit { done = yes }
                Vlog("${it.name} completed")
            }
        }
	}
}
	  

