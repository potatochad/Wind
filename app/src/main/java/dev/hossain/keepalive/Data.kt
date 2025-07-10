package dev.hossain.keepalive

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import java.util.UUID


class Settings {
    var funTime by mutableStateOf(0)

    //region COPY PASTE THING
    var targetText by mutableStateOf("I am doing this project to regain freedom in my life. It is most important project ever, but that doesn't mean i need to take it soop seriously. I need to only focus on it, do the pomo. And spend half time improving, half time using the product. Done. I need to keep with it for 100 days for it to bear fruit. Right now it won't work/ the initial mvp is terrible. But that's ok. I will improve it slowly, one tiny feature at a time. All i must do is stick with the idea: type stuff and get time to have fun. Done. That is it!!!!. Goal is consistency, nothing else, nothing else!!")
    var LetterToTime by mutableStateOf(10)
    var DoneRetype_to_time by mutableStateOf(60)
    var currentInput by mutableStateOf("")
    var highestCorrect by mutableStateOf(0)
    //endregion


    //region MISALANIOUS
    var ShowMenu by mutableStateOf(false)
    var CheckInstalledApps by mutableStateOf(true)

    var AppList = mutableStateListOf<Apps>()

    //endregion
}


data class Apps(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var packageName: String
) {
    var Block by mutableStateOf(false)
    var Exists by mutableStateOf(true)
    var TimeSpent by mutableStateOf(0f)
}






















//region OnAppStart

@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
    SettingsSaved.init()
    SettingsSaved.Bsave()
}

@Composable
fun AppStart() {
    LaunchedEffect(Unit) {
        Bar.CheckInstalledApps= true
    }
}



//endregion

//region GLOBAL
//* CONTEXT from anywhere!!!
object Global1 {
    lateinit var context: Context
    lateinit var navController: NavHostController
}


//endregion


