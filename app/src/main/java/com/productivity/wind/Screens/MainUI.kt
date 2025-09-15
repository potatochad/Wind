package com.productivity.wind.Screens

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.productivity.wind.Imports.Bar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.heightIn
import com.productivity.wind.MAINStart
import com.productivity.wind.*
import com.productivity.wind.Imports.*
import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.*
import androidx.compose.runtime.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import com.skydoves.balloon.*
import androidx.compose.ui.viewinterop.*
import android.widget.*
import android.view.*


@Composable
fun Main() {

    MAINStart()

    LazyScreen(title = { Header.Main() }, showBack = false) {

        LazyCard { Disipline() }




        
        LazzyList(
            apps.filter { it.Worth > 0 && it.done == false },
            modifier = Modifier.fillMaxWidth(),
            lazyMode = true,
        ) { app ->



            
            


    val context = LocalContext.current

    val balloon = remember {
        Balloon.Builder(context)
            .setText("App info: ${app.name}")
            .setArrowOrientation(ArrowOrientation.TOP)
            .setAutoDismissDuration(3000L)
            .build()
    }

    Box {
        AndroidView(
            factory = { ctx ->
                FrameLayout(ctx).apply {
                    setOnLongClickListener {
                        balloon.showAlignTop(this) // show balloon on hold
                        true
                    }
                }
            },
            modifier = Modifier
        ) { view ->
            // Render your Compose UI inside the anchor View
            view.setContent {
                Item.AppTaskUI(app)
            }
        }
    }
    

        }
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
        LazzyRow {
            Text("Done: ${Bar.HowManyDoneRetypes_InDay}/5")
            UI.End{
                Icon.Edit()
            }
        }
        UI.move(8)
        Text(
            text = coloredTarget,
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 300.dp)
                .verticalScroll(ScrollText)
        )
        UI.move(h = 20)

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
                .height(150.dp)
                .verticalScroll(ScrollINPUTText),
            placeholder = { Text("Start typing...") }
        )
    }
}



