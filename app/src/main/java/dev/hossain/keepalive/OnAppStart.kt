package dev.hossain.keepalive

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable


//region OnAppStart

@RequiresApi(Build.VERSION_CODES.O)
fun AppStart_beforeUI(context: Context) {
    Global1.context = context
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
