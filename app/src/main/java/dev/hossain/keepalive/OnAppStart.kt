package dev.hossain.keepalive

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import kotlinx.coroutines.delay


//region OnAppStart

@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
    SettingsSaved.init()
    SettingsSaved.Bsave()
}

@Composable
fun AppStart() {

}

//endregion

//region GLOBAL CONTEXT
//* CONTEXT from anywhere!!!
object Global1 {
    lateinit var context: Context
}


//endregion
