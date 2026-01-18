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
import com.productivity.wind.Imports.Data.*
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

    url("Web") { Web() }
    url("BlockKeyword") { BlockKeyword() }
    url("filterExtraWeb") { filterExtraWeb() }
    

    url("SettingsScreen") { SettingsScreen() }
    url("ExtensionsScreen") { ExtensionsScreen() }
	url("PrivacyScreen") { PrivacyScreen() }
    url("SettingsOtherScreen") { SettingsOtherScreen() }
    url("LogsScreen") { LogsScreen() }

	// popups
	popup("getPoints") { getPoints() }
	popup("usagePermission") { usagePermission() }

  
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
            icon = Icons.Outlined.Chat,
            title = "Support",
            onClick = { SendEmail(); menu = no },
        )
        LazyItem(
            icon = Icons.Outlined.Landscape,
            title = "Settings",
            onClick = { goTo("SettingsScreen"); menu = no },
        )
        LazyItem(
            icon = Icons.Outlined.QueryStats,
            title = "Achievements",
            onClick = { goTo("Achievements"); menu = no },
        )
    }
}



object Item {

    fun UpdateAppTsk(){
        Bar.apps.each {
            Bar.apps.edit(it) { it.NowTime = getTodayAppUsage(it.pkg) }

            if (it.NowTime > it.DoneTime - 1 && !it.done) {
                Bar.funTime += it.Worth
                Bar.apps.edit(it) { done = yes }
                Vlog("${it.name} completed")
            }
        }
    }

	@Composable
	fun BigTskInput(txt: m_<Str>, scrollV: ScrollState = r_Scroll(), Do: DoStr={ txt.it = it }){
		val scroll = scrollV
		var Field by r_m(TextFieldValue(txt.it))
		var done = r_m(no)
		var itIndex by r_m(0)
		
        OutlinedTextField(
            value = Field,
            onValueChange = {    
					
				Do(it.text)
				Field = TextFieldValue(
					text = txt.it,
					selection = it.selection
				)
				itIndex = it.selection.start
				
				fixedInputScroll(Field, itIndex, done, scroll)

			},
            modifier = Mod.maxW().h(150).Vscroll(scroll).onFocusChanged{
				if (!it.isFocused) done.it = no
			},
		    placeholder = { Text("Start typing...") },
        )
	}



    @Composable
    fun TskInput(txt: Any, maxLetters: Int = 4, isInt: Bool =yes, w: Int = 60) {  
        val TxtState = txt as? MutableState<Any> ?: run {
            Vlog("expected mutable")
            return
        }

        BasicInput(
            "${TxtState.it}",
            isInt = isInt, 
            w=w,
        ) {
            val input = it.take(maxLetters)
            
            TxtState.it = if (TxtState.it is Int) {
                 if (input.isEmpty()) 0 else input.toInt()
            } else {
                input
            }
        }
    }


    fun enoughPoints(enough: Do) {
        if (Bar.funTime < Bar.Dpoints) {
            goTo("getPoints")
        }
        else enough()
    }

    @Composable
    fun AppTaskUI(app: AppTsk){
        val icon = getAppIcon(app.pkg)
        var name = app.name
		val progress = (toF(app.NowTime) / toF(app.DoneTime)).coerceIn(0f, 1f)
        
        LazyCard {
            LazzyRow {
                move(10)

                click({
                    ProgressIcon(icon, progress)
                }){
                    openApp(app.pkg)
                }


                move(12)
                Text("Points ${app.Worth}")

                End {
                    Icon.Edit{
                        enoughPoints {
                            goTo("AppUsage/${app.id}")
                        }
                    }
                    Icon.Delete{
                       Bar.apps.remove(app)
                    }
                }

            }
        }
    }
    @Composable
    fun UnlockThreshold() {
        LazyItem(
            topPadding = 1.dp,
            BigIcon = Icons.Filled.LockOpen,
            BigIconColor = Gold,
            title = "Unlock Threshold",
			modUI = Mod.space(h=2.5.dp),
            endContent = {             
                BasicInput(
                    "${Bar.Dpoints}", 
                    isInt = yes, 
                ) {
                    val input = it.take(5).toIntOrNull() ?: 0
                    if (Bar.funTime>Bar.Dpoints) {
                        if (input<Bar.funTime) {
                            Bar.Dpoints = input
                        } else {
                            Vlog("More points: ${Bar.funTime} < $input ")
                        }
                    } else {
                        Vlog("More points: ${Bar.funTime} < ${Bar.Dpoints} ")
                    }
                }
            }
        )
    }

    @Composable
    fun WebPointTimer() {
        each(1000){
            if (Bar.Dpoints > 0) {
                if (Bar.funTime < 1) {
                    goTo("main")
                    goTo("getPoints")
                } else {
                    Bar.funTime -= 1
                }
            }
        }
    }

    
}

object Header {

    @Composable
    fun Logs(Tag: m_<Str>) {
        LazyInput(Tag, modifier = Mod.h(36).w(120).Hscroll())


		val Logs by remember(Tag.it, Bar.logs) {
			derivedStateOf {
				Bar.logs.filter { it.contains(Tag.it) }
			}
		}

            
        End {
            Icon.Delete {
                Bar.logs.clear()
            }
            Icon.Copy(Logs.joinToString("\n"))
			
        }
    }
    
    @Composable
    fun AppUsage(Time: m_<Int>, Points: m_<Int>, selectedApp: Str) {
        Text("AppUsage")
        
        End {
            Icon.Add {
                check(!isUsageP_Enabled()) { goTo("usagePermission"); return@Add}
                check(Time.it < 1,"Add time") {return@Add}
                check(selectedApp.isEmpty(),"Select app") {return@Add}


                var isAdded = Bar.apps.find { it.name == selectedApp }

                if (isAdded==null){
                    Bar.apps.add {
                        pkg = getAppPkg(selectedApp)
                        name = selectedApp
                        DoneTime = Time.it
                        Worth = Points.it
                    }
                } else {   
                    Bar.apps.edit(isAdded) {
                        pkg = getAppPkg(selectedApp)
                        name = selectedApp
                        DoneTime = Time.it
                        Worth = Points.it
                    }
                }
                goTo("Main")
            }
        }
    }
    
    @Composable
    fun CopyPaste(text: m_<Str>, MaxDone: m_<Int>, DonePts: m_<Int>, LetterPts: m_<Int>, id: Str){
        Text("Copy Paste")
        
        End {
            Icon.Add {
                check(text.it.isEmpty(),"Add text") {return@Add}

                if (!id.isEmpty()) {
                    val tsk = Bar.copyTsk.find { it.id == id }

                    if (tsk!=null){
                        tsk.edit {
                            txt = text.it
                            maxDone = MaxDone.it
                            donePts = DonePts.it
                            letterPts = LetterPts.it
                        }  
                        goTo("Main")
                    }
                    return@Add
                }

                Bar.copyTsk.add {
                    txt = text.it
                    maxDone = MaxDone.it
                    donePts = DonePts.it
                    letterPts = LetterPts.it     
                }
                goTo("Main")
            }
        }
    }


    @Composable
    fun Main(){
        Icon.Menu {
			menu = yes
		}
        // Icon.Chill { goTo("Web") }

        Icon.Reload{
            // RunJs()
        }
        
        move(w = 12)
        
        Text("Points ${Bar.funTime}")
        
        End {
            Icon.Add()
        }
    }
}



@Composable
fun getPoints(){
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
fun usagePermission() {
        LazyPopup(
            m(yes),
            "Need Usage Permission",
            "To function correctly, this app requires access to your app usage data. Granting this permission allows the app to monitor usage statistics and manage app-related tasks efficiently. Without it, this feature won't work.",
            onConfirm = {
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                App.startActivity(intent)
            },
			onClose = {
				navBack()
			}
        )
}

@Composable
fun isSure(show: mBool, msg: Str = "delete this item for ever", Do: Do) {
    LazyPopup(
        show,
        "Delete",
        "Are you certain you want to $msg?",
        onConfirm = {Do()},
    )
}





@Composable
fun selectLocation(show: mBool = m(yes), Do: DoStr ={}) {
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






@Composable
fun selectApp(show: mBool =m(yes), Do: DoStr ={}) {
    var appList by r_m<List<Pair<ResolveInfo, Drawable?>>>(emptyList())
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
				LazzyRow(
					
					Mod.click {
						Do(getAppName(app))
						show.it = no
					}) {
					
					move(10)
					LazyImage(icon)
					move(10)
					Text(getAppName(app))
				}
			}
		}
	}
	
}









