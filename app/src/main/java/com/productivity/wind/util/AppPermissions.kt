package com.productivity.wind.util

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import com.productivity.wind.data.PermissionType
import com.productivity.wind.data.PermissionType.PERMISSION_IGNORE_BATTERY_OPTIMIZATIONS
import com.productivity.wind.data.PermissionType.PERMISSION_PACKAGE_USAGE_STATS
import com.productivity.wind.data.PermissionType.PERMISSION_POST_NOTIFICATIONS
import com.productivity.wind.data.PermissionType.PERMISSION_SYSTEM_APPLICATION_OVERLAY
import timber.log.Timber

object AppPermissions {

    @SuppressLint("BatteryLife")
    fun requestBatteryOptimizationExclusion(context: Context) {
        Toast.makeText(
            context,
            "Please exclude this app from battery optimization.",
            Toast.LENGTH_SHORT,
        ).show()
        val intent =
            Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:${context.packageName}")
            }
        context.startActivity(intent)
    }

    fun requestOverlayPermission(
        context: Context,
        activityResultLauncher: ActivityResultLauncher<Intent>,
    ) {
        val intent =
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context.packageName}"),
            )
        activityResultLauncher.launch(intent)
    }

    /**
     * Requests the "Usage Access" permission.
     *
     * This navigates the user to the system settings screen where they can grant permission
     * for the app to access information about app usage (e.g., how long apps are used,
     * last time used). This is crucial for the [RecentAppChecker] functionality.
     *
     * @param context The [Context] used to start the settings activity.
     */
    fun requestUsageStatsPermission(context: Context) {
        Timber.d("requestUsageStatsPermission: Requesting usage stats permission")
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        context.startActivity(intent)
    }



    /**
     * Requests the [POST_NOTIFICATIONS] permission using an [ActivityResultLauncher].
     * This is specifically for Android 13 (Tiramisu, API 33) and above, where posting
     * notifications becomes a runtime permission.
     *
     * @param requestPermissionLauncher The [ActivityResultLauncher] for `Array<String>` (multiple permissions)
     *                                  used to request the notification permission.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestPostNotificationPermission(requestPermissionLauncher: ActivityResultLauncher<Array<String>>) {
        Timber.d("requestPostNotificationPermission: Requesting post notification permission")
        requestPermissionLauncher.launch(arrayOf(POST_NOTIFICATIONS))
    }

    /**
     * Central dispatcher for requesting a specific [PermissionType].
     *
     * This function determines the type of permission requested and calls the appropriate
     * specific request method (e.g., [requestPostNotificationPermission], [requestUsageStatsPermission]).
     * It handles API level checks implicitly through the called methods or explicitly where needed.
     *
     * @param context The application [Context].
     * @param activityResultLauncher An [ActivityResultLauncher] for `Intent`, used for permissions
     *                               that require navigating to system settings (e.g., overlay, battery optimization).
     *                               Can be null if the permission type doesn't require it.
     * @param requestPermissionLauncher An [ActivityResultLauncher] for `Array<String>`, used for
     *                                  standard runtime permissions (e.g., post notifications).
     *                                  Can be null if the permission type doesn't require it.
     * @param permissionType The [PermissionType] enum indicating which permission to request.
     */
    @SuppressLint("NewApi") // Suppresses NewApi lint for VERSION_CODES.TIRAMISU check handled by target function
    fun requestPermission(
        context: Context,
        activityResultLauncher: ActivityResultLauncher<Intent>?,
        requestPermissionLauncher: ActivityResultLauncher<Array<String>>?,
        permissionType: PermissionType,
    ) {
        Timber.d("requestPermission: for $permissionType")
        when (permissionType) {
            PERMISSION_POST_NOTIFICATIONS -> {
                // Request for notification permission
                AppPermissions.requestPostNotificationPermission(requestPermissionLauncher!!)
            }

            PERMISSION_PACKAGE_USAGE_STATS -> {
                // Request for usage stats permission
                AppPermissions.requestUsageStatsPermission(context)
            }

            PERMISSION_SYSTEM_APPLICATION_OVERLAY -> {
                // Request for overlay permission
                activityResultLauncher?.let {
                    AppPermissions.requestOverlayPermission(context, it)
                }
            }

            PERMISSION_IGNORE_BATTERY_OPTIMIZATIONS -> {
                // Request for battery optimization exclusion
                AppPermissions.requestBatteryOptimizationExclusion(context)
            }
        }
    }
}
