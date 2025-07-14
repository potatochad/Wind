package com.productivity.wind

import android.Manifest.permission.PACKAGE_USAGE_STATS
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.productivity.wind.data.AppDataStore
import com.productivity.wind.data.PermissionType
import com.productivity.wind.data.PermissionType.PERMISSION_IGNORE_BATTERY_OPTIMIZATIONS
import com.productivity.wind.data.PermissionType.PERMISSION_PACKAGE_USAGE_STATS
import com.productivity.wind.data.PermissionType.PERMISSION_POST_NOTIFICATIONS
import com.productivity.wind.data.PermissionType.PERMISSION_SYSTEM_APPLICATION_OVERLAY
import com.productivity.wind.ui.theme.KeepAliveTheme
import timber.log.Timber


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppStart_beforeUI(applicationContext)
        setContent {
            KeepAliveTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppStart()

                    Global1.navController = rememberNavController()
                    MyNavGraph(navController = Global1.navController)
                }
            }
        }
    }
}
