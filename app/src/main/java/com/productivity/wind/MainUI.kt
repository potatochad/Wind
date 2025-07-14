package com.productivity.wind

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch


import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp


@Composable
fun Main() {

    var correctChars by remember { mutableStateOf(0) }

    fun AnnotatedString.Builder.appendAnnotated(text: String, correctUntil: Int) {
        for (i in text.indices) {
            if (i < correctUntil) {
                pushStyle(SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold))
                append(text[i])
                pop()
            } else {
                append(text[i])
            }
        }
    }
    val coloredTarget = remember(Bar.targetText, Bar.currentInput) {
        buildAnnotatedString {
            appendAnnotated(Bar.targetText, correctChars)
        }
    }



    //region MENU CONTROLLER

    Bar.halfWidth = LocalConfiguration.current.screenWidthDp.dp/2+30.dp
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    LaunchedEffect(Bar.ShowMenu) {
        if (Bar.ShowMenu) {
            log("SHOWING MENUUUUU", "BAD")
            scope.launch { drawerState.open() } }
        else {

            log("CLOSING MENUUUU", "BAD")
            scope.launch { drawerState.close() } }
    }
    LaunchedEffect(drawerState) {
        snapshotFlow { drawerState.isOpen }
            .collect { isOpen ->
                if (!isOpen && Bar.ShowMenu) {
                    Bar.ShowMenu = false
                }
            }
    }

    //endregion MENU CONTROLLER

    ModalNavigationDrawer(drawerState = drawerState, gesturesEnabled = true, drawerContent   = { ModalDrawerSheet(modifier = Modifier.width(Bar.halfWidth)) { Menu() } }) {
        SettingsScreen(titleContent = { MainHeader() }, showBack = false, showSearch = false) {
            Card(modifier = Modifier.padding(16.dp).fillMaxWidth(), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = coloredTarget,
                        modifier = Modifier
                            .height(200.dp)
                            .verticalScroll(rememberScrollState())
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = Bar.currentInput,
                        onValueChange = { input ->
                            // Only single‐char changes
                            if (input.length - Bar.currentInput.length <= 1) {
                                // 1) Track typing
                                Bar.TotalTypedLetters++
                                Bar.currentInput = input

                                // 2) Compute how many chars are correct
                                correctChars = Bar.targetText
                                    .zip(input)
                                    .takeWhile { it.first == it.second }
                                    .size

                                // 3) Earn funTime for newly correct letters
                                val newlyEarned = correctChars - Bar.highestCorrect
                                if (newlyEarned > 0) {
                                    Bar.funTime += newlyEarned * Bar.LetterToTime
                                    Bar.highestCorrect = correctChars
                                }

                                // 4) Completed full text?
                                if (correctChars == Bar.targetText.length) {
                                    Bar.funTime += Bar.DoneRetype_to_time
                                    Bar.currentInput = ""
                                    Bar.highestCorrect = 0
                                    correctChars = 0
                                }

                                // 5) Tiny bonus per keystroke
                                Bar.funTime++
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .verticalScroll(rememberScrollState()),
                        placeholder = { Text("Start typing...") }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
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







