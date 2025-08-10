package com.productivity.wind.Screens

import androidx.compose.foundation.interaction.*
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import android.os.*
import java.io.*
import android.annotation.*
import android.content.*
import android.util.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.navigation.NavController
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.isAccessible
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.reflect.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import android.widget.ScrollView
import com.productivity.wind.*
import java.util.UUID
import java.lang.reflect.Type

@Composable
fun Web() {
    SettingsScreen(
        titleContent = {
            UI.SimpleRow(
                content = {
                    Text("Web")

                    // WORK WEB
                    UI.SimpleIconButton(
                        onClick = { Global1.navController.navigate("WorkWeb") },
                        BigIcon = Icons.Default.Work,
                        BigIconColor = Color(0xFF4CAF50), // Green
                        OuterPadding = 0,
                    )

                    UI.SimpleIconButton(
                        onClick = { Global1.navController.navigate("FunWeb") },
                        BigIcon = Icons.Default.SentimentVerySatisfied,
                        BigIconColor = Color(0xFFFFC107), // Amber
                        OuterPadding = 0,
                    )


                },
            )


        },
    ) {
        UI.EmptyBox(text = "TO DO")

    }



}


@Composable
fun FunWeb() {
    SettingsScreen(titleContent = { Text("Fun Web") }) {
        UI.EmptyBox(text = "TO DO")

    }




}


@Composable
fun WorkWeb() {
    SettingsScreen(titleContent = {Text("Work Web")}) {
        UI.EmptyBox(text = "TO DO")
    }




}
