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
fun Context.start(service: KClass<out Service>) {
    Permission.ignoreOptimizations()
    startService(Intent(this, service.java))
}
fun Context.startForeground(service: KClass<out Service>) {
    Permission.ignoreOptimizations()

    val intent = Intent(this, service.java)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}
fun Context.stop(service: KClass<out Service>) {
    stopService(Intent(this, service.java))
}



//WITHOUT KCLASS
fun Context.start(service: Class<out Service>) {
    Permission.ignoreOptimizations()
    startService(Intent(this, service))
}
fun Context.startForeground(service: Class<out Service>) {
    Permission.ignoreOptimizations()

    val intent = Intent(this, service)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}
fun Context.stop(service: Class<out Service>) {
    stopService(Intent(this, service))
}









