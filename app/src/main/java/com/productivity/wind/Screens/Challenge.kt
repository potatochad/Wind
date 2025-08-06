package com.productivity.wind.Screens

import android.app.*
import android.content.*
import android.net.*
import android.os.*
import android.util.*
import android.widget.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier // ✅ Only this one — no conflict
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import androidx.core.app.NotificationManagerCompat // ✅ Fixes "Unresolved reference"
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.*
import com.google.gson.*
import com.google.gson.reflect.*
import com.productivity.wind.*
import kotlinx.coroutines.*
import java.io.*
import java.lang.reflect.*
import java.util.*
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*



@Composable
fun Challenge() {
  SettingsScreen(titleContent = { Text("Challenge") }) {
    SettingItem(
            icon = Icons.Outlined.Info,
            title = "Reminder",
            endContent = {
            }
        )
    
   }
}


























