package com.productivity.wind.ui.screen

import android.content.Context
import android.content.pm.PackageManager
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppViewModel(private val dataStore: DataStore<List<AppInfo>>) : ViewModel() {

    val appList: LiveData<List<AppInfo>> = dataStore.data.map { it }.asLiveData()
    fun addApp(appInfo: AppInfo) {
        viewModelScope.launch {
            val currentList = appList.value ?: emptyList()
            dataStore.updateData { currentList + appInfo }
        }
    }

    fun getInstalledApps(context: Context): List<AppInfo> {
        val alreadyAddedApps: List<AppInfo> = appList.value ?: emptyList()
        val pm = context.packageManager
        val thisAppPackageName = context.packageName

        return pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { app ->
                // Check if the app has a launchable activity
                val hasLaunchableActivity = pm.getLaunchIntentForPackage(app.packageName) != null

                // Allow apps with launchable activities, exclude service-only apps
                hasLaunchableActivity &&
                    (app.packageName != thisAppPackageName) &&
                    !alreadyAddedApps.any { it.packageName == app.packageName }
            }
            .map { app -> AppInfo(app.packageName, app.loadLabel(pm).toString()) }
            .distinctBy { it.packageName }
            .sortedBy { it.appName }
    }
}
