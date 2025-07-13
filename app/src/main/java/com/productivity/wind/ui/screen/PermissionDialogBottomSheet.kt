package com.productivity.wind.ui.screen

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.productivity.wind.data.PermissionType
import com.productivity.wind.data.PermissionType.PERMISSION_IGNORE_BATTERY_OPTIMIZATIONS
import com.productivity.wind.data.PermissionType.PERMISSION_PACKAGE_USAGE_STATS
import com.productivity.wind.data.PermissionType.PERMISSION_POST_NOTIFICATIONS
import com.productivity.wind.data.PermissionType.PERMISSION_SYSTEM_APPLICATION_OVERLAY
import com.productivity.wind.util.AppPermissions
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    showDialog: Boolean,
    title: String,
    description: String,
    onAccept: () -> Unit,
    onCancel: () -> Unit,
) {
    if (showDialog) {
        ModalBottomSheet(
            onDismissRequest = onCancel,
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Spacer(Modifier.height(8.dp))
                Text(text = description)
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = onAccept) {
                        Text("Accept")
                    }
                }
            }
        }
    }
}

@Composable
fun PermissionDialogs(
    context: Context,
    activityResultLauncher: ActivityResultLauncher<Intent>?,
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>?,
    permissionType: PermissionType,
    showDialog: MutableState<Boolean>,
) {
    val (title, description) =
        when (permissionType) {
            PERMISSION_POST_NOTIFICATIONS ->
                "Post Notifications" to "Please grant the notification permission." +
                    "\nThis is essential for notifying you about ongoing watchdog activity. " +
                    "It also allows the app to always run in the background."
            PERMISSION_PACKAGE_USAGE_STATS ->
                "Usage Stats" to "Please grant the usage stats permission." +
                    "\nThe permission allows the app to access usage statistics, " +
                    "which is necessary for knowing if specific apps have been recently used."
            PERMISSION_SYSTEM_APPLICATION_OVERLAY ->
                "Overlay Permission" to "Please grant the overlay permission." +
                    "\nThe permission allows this app to start the apps you configure from the background service."
            PERMISSION_IGNORE_BATTERY_OPTIMIZATIONS ->
                "Battery Optimization" to "Please exclude this app from battery optimization." +
                    "\nThis ensures the app and it's `WatchdogService` can run continuously without being restricted by the system."
            else -> "Unknown" to "Please ignore this permission request."
        }

    BottomSheetDialog(
        showDialog = showDialog.value,
        title = title,
        description = description,
        onAccept = {
            Timber.d("onAccept: for $permissionType")
            showDialog.value = false
            AppPermissions.requestPermission(
                context = context,
                activityResultLauncher = activityResultLauncher,
                requestPermissionLauncher = requestPermissionLauncher,
                permissionType = permissionType,
            )
        },
        onCancel = {
            Timber.d("onCancel: for $permissionType")
            showDialog.value = false
        },
    )
}
