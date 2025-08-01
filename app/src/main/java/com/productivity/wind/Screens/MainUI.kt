package com.productivity.wind.Screens

import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalConfiguration
import com.productivity.wind.Bar
import com.productivity.wind.LazyMenu
import com.productivity.wind.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.productivity.wind.SettingsScreen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.foundation.ScrollState
import kotlinx.coroutines.launch
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.heightIn
import com.productivity.wind.MAINStart
import com.productivity.wind.UI

@Composable
fun Main() {
    LazyMenu { Menu() }
    if (Bar.NewDay == true) { Bar.HowManyDoneRetypes_InDay = 0}
    Bar.NewDay = false
    MAINStart()
    
    SettingsScreen(titleContent = { MainHeader() }, showBack = false) {
        
                UI.LazyCard( content = { Disipline() } )
                UI.LazyCard( content = { German() } )
                UI.LazyCard( content = { Text( text = "WaterDo: ${Bar.WaterDOtime_spent}") } )
                
    }
}

@Composable
fun Disipline() {
    val ScrollText = rememberScrollState()

    LaunchedEffect(Bar.highestCorrect) {
        if (Bar.highestCorrect > 20) {
            ScrollText.animateScrollBy(1f)
        }
    }

    val ScrollINPUTText = rememberScrollState()

    LaunchedEffect(Bar.highestCorrect) {
        if (Bar.highestCorrect > 20) {
            ScrollINPUTText.animateScrollBy(15f)
        }
    }

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
        Text("Done: ${Bar.HowManyDoneRetypes_InDay}/5")
        Icon.Edit()
        Text(
            text = coloredTarget,
            modifier = Modifier
                .heightIn(max = 200.dp)
                .verticalScroll(ScrollText)
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = Bar.currentInput,
            onValueChange = {
                if (it.length - Bar.currentInput.length <= 5) {
                    if (it.length > Bar.currentInput.length){
                        Bar.TotalTypedLetters += 1
                    }
                    
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
                .verticalScroll(ScrollINPUTText),
            placeholder = { Text("Start typing...") }
        )
    }
}

@Composable
fun German() {
    val ScrollText = rememberScrollState()

    LaunchedEffect(Bar.G_highestCorrect) {
        if (Bar.G_highestCorrect > 20) {
            ScrollText.animateScrollBy(1f)
        }
    }
    
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

    Icon.G_Edit()
    Text(
        text = coloredTarget,
        modifier = Modifier
            .heightIn(max = 200.dp)
            .verticalScroll(ScrollText)
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






