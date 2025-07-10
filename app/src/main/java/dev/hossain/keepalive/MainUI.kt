package dev.hossain.keepalive

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.webkit.WebView
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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

// Usage examples


@Composable
fun Main() {
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






val BadApps = DataClass_Manager<Apps>("BlockedApps", Apps::class).apply {
    init()
    DoSave()
}

data class Apps(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var icon: Drawable,
    var packageName: String
) {
    var Block by mutableStateOf(false)
    var Exists by mutableStateOf(true)
    var TimeSpent by mutableStateOf(0f)
}





@SuppressLint("MissingPermission")
@Composable
fun ChillScreen() = NoLagCompose {
    val Apps = remember { mutableStateListOf<Apps>() }


    Box(Modifier.fillMaxSize()) {
        Header("Pick Apps to block")


        LazyColumn(Modifier.fillMaxSize()) { items(Apps, key = { it.id }) { app ->


                val painter = rememberAsyncImagePainter(app.icon)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {



                    Image(painter,app.name,Modifier.size(40.dp)); Spacer(Modifier.width(12.dp))
                    Text(app.name); Spacer(Modifier.weight(1f))
                }
            }
        }







        //not loaded yet
        if (Apps.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}











































































class Settings {
    var funTime by mutableStateOf(0)

    //region COPY PASTE THING
    var targetText by mutableStateOf("I am doing this project to regain freedom in my life. It is most important project ever, but that doesn't mean i need to take it soop seriously. I need to only focus on it, do the pomo. And spend half time improving, half time using the product. Done. I need to keep with it for 100 days for it to bear fruit. Right now it won't work/ the initial mvp is terrible. But that's ok. I will improve it slowly, one tiny feature at a time. All i must do is stick with the idea: type stuff and get time to have fun. Done. That is it!!!!. Goal is consistency, nothing else, nothing else!!")
    var LetterToTime by mutableStateOf(10)
    var DoneRetype_to_time by mutableStateOf(60)
    var currentInput by mutableStateOf("")
    var highestCorrect by mutableStateOf(0)
    //endregion


    //region MISALANIOUS
    var ShowMenu by mutableStateOf(false)
    val AppList = mutableStateListOf<Apps>()


    //endregion
}





