package com.productivity.wind.Screens

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.material.icons.filled.*
import com.productivity.wind.*
import com.productivity.wind.Imports.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.UI_visible.*


@Composable
fun Menu() {
    LazzyColumn(Mod.black().maxS()) {
		move(20)
        MenuHeader()
		move(20)
        LazyItem(
            icon = { Icon.Chat() },
            title = "Support",
            onClick = { SendEmail(); menu = no },
        )
        LazyItem(
            icon = { Icon.Landscape() },
            title = "Settings",
            onClick = { goTo("SettingsScreen"); menu = no },         
        )
        LazyItem(
            icon = { Icon.QueryStats() },
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
				list.delete(item) 
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
            icon = { BigIcon.LockOpen(gold) },
            title = "Unlock Threshold",
			modUI = Mod.space(2.5, 0),
			bottomPadding = 2.dp,
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
        mState(yes), 
        "Get ${Bar.funTime- Bar.Dpoints} more points", 
        "Only need ${Bar.funTime}(points)-${Bar.Dpoints}(unlock)=${Bar.funTime- Bar.Dpoints}",
        showCancel = no,
		onClose = {
			navBack()
		},
    )
}



@Composable
fun PickLocation(show: mStateBool = mState(yes), Do: DoStr ={}) {
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
						GeoCircle(it, mState(no)){ Id ->
							
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




