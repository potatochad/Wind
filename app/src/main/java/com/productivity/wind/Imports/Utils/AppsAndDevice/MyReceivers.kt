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


class BootReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Intent(context, BackgroundService::class.java).also {
                context.startForegroundService(it)
            }
        }
	}
}














