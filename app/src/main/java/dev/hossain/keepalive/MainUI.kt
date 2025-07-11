package dev.hossain.keepalive

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import android.content.pm.PackageManager
import androidx.compose.runtime.remember
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.util.UUID


import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import android.app.ActivityManager
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar


import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay


@Composable
fun Main() {


    //FIX THIS LATERRRR
    DrawOnTopPermission()



    val coloredTarget = buildAnnotatedString {
        val correctChars = Bar.targetText.zip(Bar.currentInput).takeWhile { it.first == it.second }.size
        val correctInput = Bar.currentInput.take(correctChars)
        for (i in Bar.targetText.indices) {
            if (i < correctInput.length) {
                withStyle(style = SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold)) {
                    append(Bar.targetText[i])
                }
            } else {
                append(Bar.targetText[i])
            }
        }
    }




    /*
    * THE FULL NORMALL UI
    * TEXT INPUT THING
    * */
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

            /*
        Header
        INFO WILL SLOWLY APPEAR HERE, AS YOU PROGRESS THOUGHT THE APP
        * */
            Row(modifier = Modifier.fillMaxWidth()) {
                MainHeader()
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Fun time: ${Bar.funTime}s", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = coloredTarget,
                modifier = Modifier
                    .height(200.dp)
                    .verticalScroll(rememberScrollState())
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = Bar.currentInput,
                onValueChange = {
                    Bar.currentInput = it
                    log("Bar.currentInput, on value change; ${Bar.currentInput}")
                    log("the itttttt; ${it}")
                    log("initOnce; ${initOnce}")

                    val correctChars = Bar.targetText.zip(Bar.currentInput)
                        .takeWhile { it.first == it.second }.size
                    val correctInput = Bar.currentInput.take(correctChars)


                    val newlyEarned = correctInput.length - Bar.highestCorrect
                    if (newlyEarned > 0) {
                        var oldFunTime = Bar.funTime
                        Bar.funTime += newlyEarned * Bar.LetterToTime; if (oldFunTime === Bar.funTime) {
                            log(
                                "!funTime += newlyEarned * S_Data.int(LetterToTime)- VALUE DID NOT CHANGE, CLUES: ${oldFunTime}-OLDFUNTIME,,,${Bar.funTime}-funTime,,,${newlyEarned}-newlyEarned,,,${Bar.LetterToTime}-LetterToTime,,,",
                                "BAD"
                            )
                        }
                        Bar.highestCorrect = correctInput.length
                    }

                    if (correctInput == Bar.targetText) {
                        Bar.funTime += Bar.DoneRetype_to_time
                        Bar.currentInput = ""  // Reset input when completed
                        Bar.highestCorrect = 0
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .verticalScroll(rememberScrollState()),
                placeholder = { Text("Start typing...") }
            )

            Spacer(modifier = Modifier.height(24.dp))
            ChillTimeButton()
        }

    /*TOP BAR ITEMS
    * IF CLICKED WHAT HAPPENS*/
    Menu()
}


@Composable
fun FunScreen() {
    Bar.GenshinApk = "com.miHoYo.GenshinImpact"
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            Bar.funTime -=1
            if (Bar.funTime <=0) {Global1.navController.navigate("Main")}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp) // remove padding
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { Global1.navController.navigate("Main") }) {
                Text("🔙 Winning")
            }
            Text(
                text = "Fun time: ${Bar.funTime}s",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    loadUrl("https://play.famobi.com/wrapper/rise-up/A1000-10")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // makes it take up remaining space
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun ChillScreen() = NoLagCompose {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (Bar.CheckInstalledApps) {
            context.getAllInstalledApps()
            Bar.CheckInstalledApps = false
            Bar.AppList.sortWith(compareByDescending<Apps> { it.Block }.thenBy { it.name.lowercase() })
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pick Apps to Block") }, modifier = Modifier.fillMaxWidth())
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(Bar.AppList, key = { it.id }) { app ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Checkbox(
                        checked = app.Block,
                        onCheckedChange = { app.Block = it }, modifier = Modifier.padding(start = 8.dp), enabled = true
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(app.name)
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}







