package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import android.app.*
import androidx.core.app.*
import android.os.*
import android.content.*
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import android.content.Intent
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.UI_visible.*
import kotlin.reflect.KClass


//KCLAAS WITH
fun LazyIntent(service: KClass<out Service>) = Intent(App, service.java)                       
fun LazyIntent(ctx: Context, service: KClass<out Service>) = Intent(ctx, service.java)
fun start(service: KClass<out Service>) {
    Permission.ignoreOptimizations()
    App.startService(Intent(App, service.java))
}
fun startForeground(service: KClass<out Service>) {
    Permission.ignoreOptimizations()
    Vlog("START FOREGROUND CALLED")

    val intent = Intent(App, service.java)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        App.startForegroundService(intent)
        Vlog("ACTIVATING: App.startForegroundService(intent)")
    } else {
        App.startService(intent)
    }
}
fun stop(service: KClass<out Service>) {
    App.stopService(Intent(App, service.java))
}



//WITHOUT KCLASS
fun start(service: Class<out Service>) {
    Permission.ignoreOptimizations()
    App.startService(Intent(App, service))
}
fun startForeground(service: Class<out Service>) {
    Permission.ignoreOptimizations()

    val intent = Intent(App, service)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        App.startForegroundService(intent)
    } else {
        App.startService(intent)
    }
}
fun stop(service: Class<out Service>) {
    App.stopService(Intent(App, service))
}









