package com.productivity.wind.Screens
   
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.OutlinedTextField
import com.productivity.wind.*
import androidx.navigation.NavGraphBuilder
import com.productivity.wind.Imports.*
import androidx.compose.ui.platform.*
import kotlinx.coroutines.*
import android.webkit.*
import com.productivity.wind.Imports.Utils.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import android.content.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.focus.*
import android.graphics.drawable.*
import android.content.pm.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.relocation.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.gms.location.*
import android.os.*
import kotlin.math.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.input.pointer.*
import android.graphics.Canvas
import android.graphics.Bitmap
import androidx.appcompat.content.res.*
import androidx.compose.ui.platform.*
import com.productivity.wind.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.drawscope.*
import com.productivity.wind.Imports.UI_visible.*
import android.app.*
import com.productivity.wind.Screens.Web.*
import android.widget.*
import androidx.activity.compose.*
import com.productivity.wind.Screens.Task.*



fun NavGraphBuilder.ScreenNav() {
    //Main—StartDestination    
    url("Main") { Main() }

    url("Achievements") { Achievements() }
    url("Challenge") { Challenge() }
    url("AppUsage/{appId}") {
		var x: Str = it.url("appId") ?: ""
		AppUsage(x) 
	}

    url("CopyPaste/{appId}") { 
		var x: Str = it.url("appId") ?: ""
		CopyPaste(x) 
	}
	url("ToDo/{toDoId}") { 
		var x: Str = it.url("toDoId") ?: ""
		ToDo(x) 
	}

    url("Web") { Web() }
    url("WebKeywords") { WebKeywords() }
	url("WebHome") { WebHome() }
	url("WebWordConfigure/{WordId}") { 
		var x: Str = it.url("WordId") ?: ""
		WebWordConfigure(x) 
	}
	
	url("filterExtraWeb") { filterExtraWeb() }
    

    url("SettingsScreen") { SettingsScreen() }
    url("ExtensionsScreen") { ExtensionsScreen() }
	url("PrivacyScreen") { PrivacyScreen() }
    url("SettingsOtherScreen") { SettingsOtherScreen() }
    url("LogsScreen") { LogsScreen() }

	// popups ‼️Nav back on close
	popup("GetPoints") { GetPoints() }
	popup("AllowAppUsage") { AllowAppUsage() }
}




@Composable
fun Menu() {
    LazyScreen(
        top = { MenuHeader() },
        showBack = no,
        showDivider = no,
        headerHeight = 170,
    ) {
        LazyItem(
            icon = Icon.Chat(),
            title = "Support",
            onClick = { SendEmail(); menu = no },
        )
        LazyItem(
            icon = Icon.Landscape(),
            title = "Settings",
            onClick = { goTo("SettingsScreen"); menu = no },         
        )
        LazyItem(
            icon = Icon.QueryStats(),
            title = "Achievements",
            onClick = { goTo("Achievements"); menu = no },
        )
    }
}



object AppItem {
	
	@Composable
	fun Menu(){
		Icon.Menu { menu = yes }
	}
	@Composable
    fun Delete(
		mod: Mod = Mod.space(5),
		Do: Do = {}
	) {
        var show = r(no)
        
        Icon(Icons.Default.Delete, mod = mod){
            show.it = yes
        }
        IsSure(show) {
          Do()
        }
	}

	@Composable
	fun <T> ItemDelete(
		list: MutableList<T>,
		item: T?,
		Do: Do
	) {
		if (item != null) {
			AppItem.Delete {
				list.remove(item) 
				Do()
			}
		}
	}
	
	@Composable
	fun <T> FancyAdd(
		list: MutableList<T>,
		item: T?,
		newItem: () -> T,
		stop: () -> Bool = { no },
		edit: (T) -> Unit = {},
	) {
		val iconPick = if (item != null) Icons.Default.Edit else Icons.Default.Add

		Icon(iconPick) {
			if (stop()) return@Icon

			val target = item ?: newItem()
			if (item == null) list.add(target)

			edit(target)
		}
	}
	
	
	@Composable
    fun Add(Do: Do = { goTo("Challenge") }) {
        Icon.Add {     
            AppItem.EnoughPoints{
                Do()
            }
        }
    }
	@Composable
    fun Edit(
        noPoints: Bool = no,
		mod: Mod = Mod.space(10),
        Do: Do,
    ) {
        Icon.Edit(mod) {
            if (!noPoints){
                AppItem.EnoughPoints{
                    Do()
                }
            } else {
                Do()
            }
        }
	}


    fun EnoughPoints(enough: Do) {
        if (Bar.funTime < Bar.Dpoints) {
            pop("GetPoints")
        }
        else enough()
    }

    @Composable
    fun UnlockThreshold() {
        LazyItem(
            topPadding = 1.dp,
            BigIcon = Icons.Filled.LockOpen,
            BigIconColor = gold,
            title = "Unlock Threshold",
			modUI = Mod.space(h=2.5.dp),
            endUI = {             
                TinyInput(
                    "${Bar.Dpoints}", 
					Mod.w(70),
                    isInt = yes, 
					maxLetters = 5,
                ) {
					var input = toInt(it)
                    if (Bar.funTime > Bar.Dpoints) {
                        if (input < Bar.funTime) {
                            Bar.Dpoints = input
                        } else {
                            Vlog("More points: ${Bar.funTime} < $input")
                        }
                    } else {
                        Vlog("More points: ${Bar.funTime} < ${Bar.Dpoints}")        
                    }
					
                }
            }
        )
    }

    
}





@Composable
fun GetPoints(){
    LazyPopup(
        m(yes), 
        "Get ${Bar.funTime- Bar.Dpoints} more points", 
        "Only need ${Bar.funTime}(points)-${Bar.Dpoints}(unlock)=${Bar.funTime- Bar.Dpoints}",
        showCancel = no,
		onClose = {
			navBack()
		},
    )
}



@Composable
fun PickLocation(show: mBool = m(yes), Do: DoStr ={}) {
    LazyBigPopup(
        show,
		"Choose Locations",
		mod = Mod.w(360).h(600),
    ){
		var slider = r(30f)
		var selected by r<GeoCircle?>(null)
	
		Column {
			Box(Mod.weight(1f)) {
				LazyMaps(
					mapLongClick = {
						var new = GeoCircle(
							Lat = it.latitude,
							Lng = it.longitude,
							radius = slider.it
						) 
						selected = new
						Bar.privacyGeo.add(new)
					},
				){
					Bar.privacyGeo.each {
						GeoCircle(it, m(no)){ Id ->
							
							selected = Bar.privacyGeo.find(Id)
							slider.it = selected?.radius ?: slider.it
						}
					}
				}
			}
			LazySlider(
				value = slider,
				min = 5f,
				max = 100_000f,
			){
				slider.it = it
				selected?.let { item ->
					item.edit {
						radius = slider.it
					}
				}
			}
		}
	}
}




