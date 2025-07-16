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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext




@Composable
fun Main() {
    //Bar.targetText = "I am doing this project to regain freedom in my life. It is most important project ever, I NEED TO TAKE THIS WEEK UNTIL FRIDAY SUPER SERIOUSLY, NOT GETTING THE APP TO THE PLAY STORE UNTIL THEN means a 100x difference: NO PROGRAMMING, PROGRESS FOR A MONTH, MULTIPLE DISTRACTIONS, NO ME WITH SELF CONTROL, ETC.... I need to only focus on it,and how I programm, all logic MUST BE WRITTEN BY ME, IT MUST BEEEE, otherwise will spend many hours and thus resulting a catastrophic outcome, of nothing achieved, like those 5 months!!! I need to keep with it, AND GET IT TO BEAR FRUIT AS FAST AS possible, but making sure logic IS REUSABLE AND UNIVERSAL. All i must do is stick with the idea: type stuff and get time to have fun. Done, I MUST FOCUS ON ONE IDEA, ONE ONLYYY. Goal is consistency, nothing else, nothing else!!"
    val halfWidth = LocalConfiguration.current.screenWidthDp.dp/2+30.dp; Bar.halfWidth = halfWidth
    val halfHeight = LocalConfiguration.current.screenHeightDp.dp/2; Bar.halfHeight = halfHeight
    LazyMenu { Menu();  }
    if (Bar.NewDay == true) { Bar.HowManyDoneRetypes_InDay = 0}

    NewDayWaterDo()
    SettingsScreen(titleContent = { MainHeader() }, showBack = false, showSearch = false) {
        Card(modifier = Modifier.padding(16.dp).fillMaxWidth(), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Disipline()
                German()
            }
        }
    }
}

@Composable
fun Disipline() {
    //Bar.targetText = "I am doing this project to regain freedom in my life. It is most important project ever, I NEED TO TAKE THIS WEEK UNTIL FRIDAY SUPER SERIOUSLY, NOT GETTING THE APP TO THE PLAY STORE UNTIL THEN means a 100x difference: NO PROGRAMMING, PROGRESS FOR A MONTH, MULTIPLE DISTRACTIONS, NO ME WITH SELF CONTROL, ETC.... I need to only focus on it,and how I programm, all logic MUST BE WRITTEN BY ME, IT MUST BEEEE, otherwise will spend many hours and thus resulting a catastrophic outcome, of nothing achieved, like those 5 months!!! I need to keep with it, AND GET IT TO BEAR FRUIT AS FAST AS possible, but making sure logic IS REUSABLE AND UNIVERSAL. All i must do is stick with the idea: type stuff and get time to have fun. Done, I MUST FOCUS ON ONE IDEA, ONE ONLYYY. Goal is consistency, nothing else, nothing else!!"

    fun AnnotatedString.Builder.appendAnnotated(text: String, correctUntil: Int) {
        for (i in text.indices) {
            if (i < correctUntil) {
                pushStyle(SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold))
                append(text[i]); pop()
            } else {
                append(text[i])
            }
        }
    }
    val coloredTarget by produceState(initialValue = AnnotatedString(""), Bar.targetText, Bar.currentInput) {
        withContext(Dispatchers.Default) {
            val correctChars = Bar.targetText.zip(Bar.currentInput)
                .takeWhile { it.first == it.second }
                .size
            value = buildAnnotatedString {
                appendAnnotated(Bar.targetText, correctChars)
            }
        }
    }

    if (Bar.HowManyDoneRetypes_InDay == 5) { }
    else {
        Text(
            text = coloredTarget,
            modifier = Modifier
                .height(200.dp)
                .verticalScroll(rememberScrollState())
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = Bar.currentInput,
            onValueChange = {
                if (it.length - Bar.currentInput.length <= 5) {
                    Bar.TotalTypedLetters += 1
                    Bar.currentInput = it

                    val correctChars = Bar.targetText.zip(Bar.currentInput)
                        .takeWhile { it.first == it.second }.size
                    val correctInput = Bar.currentInput.take(correctChars)


                    val newlyEarned = correctInput.length - Bar.highestCorrect
                    if (newlyEarned > 0) {
                        var oldFunTime = Bar.funTime
                        Bar.funTime += newlyEarned * Bar.LetterToTime; if (oldFunTime === Bar.funTime) {

                        }
                        Bar.highestCorrect = correctInput.length
                    }

                    if (correctInput == Bar.targetText) {
                        Bar.funTime += Bar.DoneRetype_to_time
                        Bar.HowManyDoneRetypes_InDay +=1
                        Bar.currentInput = ""  // Reset input when completed
                        Bar.highestCorrect = 0
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .verticalScroll(rememberScrollState()),
            placeholder = { Text("Start typing...") }
        )
    }


}

@Composable
fun German() {

    fun AnnotatedString.Builder.appendAnnotated(text: String, correctUntil: Int) {
        for (i in text.indices) {
            if (i < correctUntil) {
                pushStyle(SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold))
                append(text[i]); pop()
            } else {
                append(text[i])
            }
        }
    }
    val coloredTarget by produceState(initialValue = AnnotatedString(""), Bar.G_targetText, Bar.G_currentInput) {
        withContext(Dispatchers.Default) {
            val correctChars = Bar.G_targetText.zip(Bar.G_currentInput)
                .takeWhile { it.first == it.second }
                .size
            value = buildAnnotatedString {
                appendAnnotated(Bar.G_targetText, correctChars)
            }
        }
    }

    G_EditIcon()
    Text(
        text = coloredTarget,
        modifier = Modifier
            .height(200.dp)
            .verticalScroll(rememberScrollState())
    )
    Spacer(modifier = Modifier.height(20.dp))

    OutlinedTextField(
        value = Bar.G_currentInput,
        onValueChange = {
            if (it.length - Bar.G_currentInput.length <= 5) {
                Bar.TotalTypedLetters += 1
                Bar.G_currentInput = it

                val correctChars = Bar.G_targetText.zip(Bar.G_currentInput)
                    .takeWhile { it.first == it.second }.size
                val correctInput = Bar.G_currentInput.take(correctChars)


                val newlyEarned = correctInput.length - Bar.G_highestCorrect
                if (newlyEarned > 0) {
                    var oldFunTime = Bar.funTime
                    Bar.funTime += newlyEarned * Bar.G_LetterToTime; if (oldFunTime === Bar.funTime) { log("TIME HAS NOT INCREASE", "BAD") }
                    Bar.G_highestCorrect = correctInput.length
                }

                if (correctInput == Bar.G_targetText) {
                    Bar.funTime += Bar.DoneRetype_to_time
                    Bar.G_currentInput = ""
                    Bar.G_highestCorrect = 0
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .verticalScroll(rememberScrollState()),
        placeholder = { Text("Start typing...") }
    )

}






