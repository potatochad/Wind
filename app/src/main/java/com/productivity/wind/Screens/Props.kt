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
        top = { UI.MenuHeader() },
        showBack = no,
        showDivider = no,
        headerHeight = 190,
    ) {
        LazyItem(
            icon = Icons.Outlined.Chat,
            title = "Contact Support",
            onClick = { UI.SendEmail(); menu = no }
        )
        LazyItem(
            icon = Icons.Outlined.Landscape,
            title = "Settings",
            onClick = { goTo("SettingsScreen"); menu = no }
        )
        LazyItem(
            icon = Icons.Outlined.QueryStats,
            title = "Achievements",
            onClick = { goTo("Achievements"); menu = no }
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
                    UI.ProgressIcon(icon, progress)
                }){
                    openApp(app.pkg)
                }


                move(12)
                Text("Points ${app.Worth}")

                UI.End {
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

            
        UI.End {
            Icon.Delete {
                Bar.logs.clear()
            }
            Icon.Copy(Logs.joinToString("\n"))
			
        }
    }
    
    @Composable
    fun AppUsage(Time: m_<Int>, Points: m_<Int>, selectedApp: Str) {
        Text("AppUsage")
        
        UI.End {
            Icon.Add {
                UI.check(!isUsageP_Enabled()) { goTo("usagePermission"); return@Add}
                UI.check(Time.it < 1,"Add time") {return@Add}
                UI.check(selectedApp.isEmpty(),"Select app") {return@Add}


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
    fun CopyPaste(text: m_<Str>, dailyMax: m_<Int>, doneWorth: m_<Int>, letterWorth: m_<Int>, id: Str){
        Text("Copy Paste")
        
        UI.End {
            Icon.Add {
                UI.check(text.it.isEmpty(),"Add text") {return@Add}

                if (!id.isEmpty()) {
                    val tsk = Bar.copyTsk.find { it.id == id }

                    if (tsk!=null){
                        Bar.copyTsk.edit(tsk) {
                            txt = text.it
                            DailyMax = dailyMax.it
                            Done_Worth = doneWorth.it
                            Letter_Worth = letterWorth.it
                        }  
                        goTo("Main")
                    }
                    return@Add
                }

                Bar.copyTsk.add {
                    txt = text.it
                    DailyMax = dailyMax.it
                    Done_Worth = doneWorth.it
                    Letter_Worth = letterWorth.it     
                }
                goTo("Main")
            }
        }
    }


    @Composable
    fun Main(){
        Icon.Menu()
        Icon.Chill()

        Icon.Reload{
            // RunJs()
        }
        
        move(w = 12)
        
        Text("Points ${Bar.funTime}")
        
        UI.End {
            Icon.Add()
        }
    }

    
}


object Icon {
    @Composable
    fun Menu() {
        LazyIcon(Icons.Default.Menu) {
            menu = yes
        }
    }

    @Composable
    fun Reload(Do: Do) {
        LazyIcon(Icons.Default.Refresh) { 
            Do() 
        }
    }

    @Composable
    fun Chill() {
        LazyIcon(Icons.Default.SportsEsports) {
            goTo("Web")
        }
    }

    @Composable
    fun Add(Do: Do = { goTo("Challenge") }) {
        LazyIcon(Icons.Default.Add) {     
            Item.enoughPoints{
                Do()
            }
        }
    }

    @Composable
    fun MoreMenu(Do: Do) {
        LazyIcon(Icons.Default.MoreVert) {
            Do()
        }
    }


    @Composable
    fun Edit(
        noPoints: Bool = no,
        Do: Do,
    ) {
        LazyIcon(Icons.Default.Edit) {
            if (!noPoints){
                Item.enoughPoints{
                    Do()
                }
            } else {
                Do()
            }
        }
    }

    @Composable
    fun Delete(Do: Do = {}) {
        var show = r_m(no)
        
        LazyIcon(Icons.Default.Delete){
            show.it = yes
        }
		isSure(show) {
			Do()
		}
    }


    @Composable
    fun Copy(txt: Str) {
        var copied by r_m(no)

        RunOnce(copied) {
            if (copied) {
                delay(1000)
                copied = no
            }
        }

        LazyIcon(if (copied) Icons.Default.Check else Icons.Default.ContentCopy){
            UI.copyToClipboard(txt)
            copied = yes
        }
    }


    //ICONS!!!!!!-------------------------///
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
	var slider by r_m(1f)
	var center by remember { mutableStateOf(Bar.userLocation) }
	
    LazyBigPopup(
        show,
		"Choose Locations",
		mod = Mod.w(360).h(600),
    ){
		Column {
			
			Box(Mod.weight(1f)) {
				LazyMaps(
					mapClick = { 
						// LatLng
					},
				){
					
    Circle(
        center = center,
        radius = toD(slider),
        strokeColor = Gold,
        fillColor = faded(Gold, 0.6f)
    )


	
	val context = LocalContext.current
val bitmap = r {
    val drawable = AppCompatResources.getDrawable(context, R.drawable.incognito)!!

    // Desired size in pixels
    val width = 60
    val height = 60

    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    drawable.setBounds(0, 0, width, height)
    drawable.draw(canvas)
    bmp
}

Marker(
    state = rememberMarkerState(position = center),
    icon = BitmapDescriptorFactory.fromBitmap(bitmap),
    title = "Incognito Spot"
)




					
					
				}
			}

			LazySlider(
				min = 10f,
				max = 100_000f
			){
				slider = it
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









