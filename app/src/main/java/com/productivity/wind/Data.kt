package com.productivity.wind

import com.productivity.wind.Screens.*
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalConfiguration
import com.productivity.wind.Imports.*
import androidx.core.view.*
import androidx.compose.foundation.lazy.*
import com.productivity.wind.Imports.Data.*

/*! NEVER move bar and lists to another FOLDER, or other file
aka....got some functions in datatools, that though a bit tantrum...
yea....i cant figure out how fix it or rewire it...(kinda lazy--i made it long ago dont remember what did)
*/

fun onNewDay() {  
     apps.forEach{app ->
          apps.edit(app){
				done = false
          }
     }   
}
	

/*
This is a special class
*more info in dataTools.kt

it looks for all vars.
ONLY stores their data:
IF (mutable state)
IF (mutable list)

SKIPS: any private info



*/
class Settings {
    var funTime by m(0)
    var Dpoints by m(0)
    
    


    
    //region COPY PASTE THING Disipline

    var FirstEditText by m(true)
    var targetText by m("ALWAYS BE KIND")
    var LetterToTime by m(1)
    var DoneRetype_to_time by m(60)
    var HowManyDoneRetypes_InDay by m(0)
    var currentInput by m("")
    var highestCorrect by m(0)

    //endregion COPY PASTE Disipline


    var lastDate by m("")

    

    //region ACHIEVEMENTS

    var TotalTypedLetters by m(0)

    //endregion


    // LISTS
    
    var ListApps by m("")
	var CopyPasteTasks by m("")
	var badKeywordsList by m("")

	var TestList = ml<TestData>()

}
data class TestData(
    val id: Str = Id(),
    var name: Str = "",
)

@Composable
fun TestListDemo(testList: MutableList<TestData>) {
    var inputName by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input field + add button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = inputName,
                onValueChange = { inputName = it },
                placeholder = { Text("Enter name") },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (inputName.isNotBlank()) {
                    testList.add(TestData(name = inputName))
                    inputName = ""
                }
            }) {
                Text("Add")
            }
        }

        // Display the list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(testList) { item ->
                var isEditing by remember { mutableStateOf(false) }
                var editName by remember { mutableStateOf(item.name) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isEditing) {
                        TextField(
                            value = editName,
                            onValueChange = { editName = it },
                            modifier = Modifier.weight(1f)
                        )
                        Button(onClick = {
                            if (editName.isNotBlank()) {
                                val index = testList.indexOf(item)
                                testList[index] = item.copy(name = editName)
                                isEditing = false
                            }
                        }) {
                            Text("Save")
                        }
                    } else {
                        Text("id=${item.id}, name=${item.name}", modifier = Modifier.weight(1f))
                        Row {
                            Button(onClick = { isEditing = true }) {
                                Text("Edit")
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Button(onClick = { testList.remove(item) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}






val trackedLists = listOf(
        Dset("Bar.ListApps", "apps"),
	    Dset("Bar.CopyPasteTasks", "copyTasks"),
     	Dset("Bar.badKeywordsList", "badWords"),
)

var copyTasks = ml(CopyTasks())
var apps = ml(DataApps())
var badWords = ml(BlockedKeywords())

data class CopyTasks(
    override val id: Str = Id(),
    var title: Str = "",
    var onMax: Bool = false,
    var MaxTimes: Int = 5,
    var Done_Worth: Int = 10,
    var Letter_Worth: Int = 1,
) : Identifiable, Copyable<CopyTasks> {
    override fun copySelf() = this.copy()
}

data class DataApps(
    override val id: Str = Id(),
    var name: Str = "",
    var done: Bool = false,
    var pkg: Str = "",
    var NowTime: Int = 0,
    var DoneTime: Int = 0,
    var Worth: Int = 0,
) : Identifiable, Copyable<DataApps> {
    override fun copySelf() = this.copy()
}

data class BlockedKeywords(
    override val id: Str = Id(),
    var word: Str = "",
) : Identifiable, Copyable<BlockedKeywords> {
    override fun copySelf() = this.copy()
}








//region OnAppStart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set navigation bar black with white icons
        WindowCompat.setDecorFitsSystemWindows(window, true)

        // Set navigation bar black with white icons
        window.navigationBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false
            show(WindowInsetsCompat.Type.systemBars()) // Force visible
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }

        AppStart_beforeUI(applicationContext)
        setContent {
            LazyTheme {
                Surface(Modifier.maxSize()) {
                    AppStart()
					
                }
            }

        }

    }

    override fun onResume() {
        super.onResume()
        // re-apply nav bar color to prevent flashing
        window.navigationBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false

        OnResume()
    }
}




@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    
    App.ctx = context
    SettingsSaved.init()
    SettingsSaved.Bsave()

    //Background thing! Disabled
    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { context.startForegroundService(Intent(context, WatchdogService::class.java))} else { context.startService(Intent(context, WatchdogService::class.java)) }
}


@Composable
fun MAINStart() {
    LaunchedEffect(Unit) {
        delay(1_000L)
        App.restoringFromFile = false
    }
}

fun OnResume(){
        refreshApps()  
}
@Composable
fun AppStart() {
	App.screenHeight = LocalConfiguration.current.screenHeightDp.dp
    App.screenWidth = LocalConfiguration.current.screenWidthDp.dp
	App.LazyScreenContentHeight = App.screenHeight-100.dp- bottomSystemHeight()
	
    LazyMenu { Menu() }
    
    Popup.Init()
	
    LaunchedEffect(Unit) {
        DayChecker.start()
    }
	
  
    
    ListStorage.SynchAll()
    
    App.navHost = rememberNavController()
    MyNavGraph(navController = App.navHost)
}



//endregion

//region GLOBAL
//* CONTEXT from anywhere!!!
object App {
    /* APP CONTEXT
    Context is weirt:
    there is application, ok for most things
    ?and local
    !which used for popup etc...
    * */
    lateinit var ctx: Context
    lateinit var navHost: NavHostController
	
	var screenHeight by m(0.dp)
	var screenWidth by m(0.dp)
	var LazyScreenContentHeight by m(0.dp)
	
	var Menu by m(false)
	var restoringFromFile by m(false)
	

}
