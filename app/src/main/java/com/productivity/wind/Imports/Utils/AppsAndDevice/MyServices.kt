package com.productivity.wind.Imports.Utils.AppsAndDevice

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


class BackgroundService : Service() {
    val notifi = LazyNotification(1, "Background tasks", "Loading...")
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var job: Job? = null
    

    override fun onCreate() {
        super.onCreate()

		Permission.notification()

        startForeground(notifi)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (job?.isActive != true) {
            job = serviceScope.launch {
				Permission.appearOnTop()
				while (appearOnTop){
					
					//for better accuracy need accesibility permission
					var lastUsed = LastUsedApp()

					if (!Permission.hasAccessibility("com.teqtic.lockmeout")) {
						DeviceGray()
					} else {
						DeviceGray(no)
					}

					notifi.text = "$lastUsed"
					delay(1000)
				}
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        job?.cancel()
        serviceScope.cancel()

        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null
}

















