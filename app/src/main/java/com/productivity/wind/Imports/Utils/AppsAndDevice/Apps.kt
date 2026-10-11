package com.productivity.wind.Imports.Utils.AppsAndDevice            

import com.productivity.wind.Imports.Utils.Log.*
import android.app.usage.UsageStatsManager
import android.content.*
import kotlin.collections.*
import android.content.Intent
import android.graphics.drawable.Drawable
import android.content.pm.*
import java.util.*
import com.productivity.wind.Imports.Utils.*
import android.app.usage.UsageEvents


fun TodayAppUsage(packageName: Str): Int {
    val end = System.currentTimeMillis()
    val cal = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val start = cal.timeInMillis

    val usm = App.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Use INTERVAL_BEST and filter manually
    val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, start, end)
    val todayUsage = stats
        .filter { it.packageName == packageName && it.lastTimeUsed >= start }
        .sumOf { it.totalTimeInForeground }

    return (todayUsage / 1000L).toInt().coerceAtLeast(0)
}


fun AppInfo.pkg(): Str {
    return this.activityInfo?.packageName ?: ""
}


fun LastUsedApp(context: Context = App, lookBackMs: Long = 10_000): Str? {
    val usm = context.getSystemService(UsageStatsManager::class.java)

    val end = System.currentTimeMillis()
    val start = end - lookBackMs

    val events = usm.queryEvents(start, end)
    val event = UsageEvents.Event()

    var lastApp: String? = null

    while (events.hasNextEvent()) {
        events.getNextEvent(event)

        if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
            lastApp = event.packageName
        }
    }

    return lastApp
}


fun Str.pkg(): Str {
    val pm = App.packageManager
    return pm.getInstalledApplications(0)
        .firstOrNull { pm.getApplicationLabel(it).toString().equals(this, ignoreCase = yes) }
        ?.packageName ?: this
}

fun OpenApp(pkg: Str) {
    val pm: PackageManager = App.packageManager
    val launchIntent: Intent? = pm.getLaunchIntentForPackage(pkg)
    if (launchIntent != null) {
        App.startActivity(launchIntent)
    } else {
        Vlog("App not installed")
    }
}

val ResolveInfo.name: Str
    get() {
        val pkg = this.activityInfo.packageName
        return this.loadLabel(App.packageManager)?.toString() ?: pkg
	}
	
fun AppIcon(packageName: Str?): Drawable? {
    if (packageName.isNullOrBlank()) {
        return App.getDrawable(android.R.drawable.sym_def_app_icon)
    }

    val pm = App.packageManager
    return try {
        val info = pm.getApplicationInfo(packageName, 0)
        info.loadIcon(pm)
    } catch (e: Exception) {
        log("Bad package: $packageName")
        App.getDrawable(android.R.drawable.sym_def_app_icon)
    }
}

