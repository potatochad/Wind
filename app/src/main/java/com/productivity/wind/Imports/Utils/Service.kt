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
fun LazyIntent(service: KClass<out Service>) = Intent(App, service)          
fun LazyIntent(ctx: Context, service: KClass<out Service>) = Intent(ctx, service)          
fun start(service: KClass<out Service>) {
    Permission.ignoreOptimizations()
    startService(Intent(App, service.java))
}
fun startForeground(service: KClass<out Service>) {
    Permission.ignoreOptimizations()

    val intent = Intent(App, service.java)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}
fun stop(service: KClass<out Service>) {
    stopService(Intent(App, service.java))
}



//WITHOUT KCLASS
fun start(service: Class<out Service>) {
    Permission.ignoreOptimizations()
    startService(Intent(App, service))
}
fun startForeground(service: Class<out Service>) {
    Permission.ignoreOptimizations()

    val intent = Intent(App, service)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}
fun stop(service: Class<out Service>) {
    stopService(Intent(App, service))
}









