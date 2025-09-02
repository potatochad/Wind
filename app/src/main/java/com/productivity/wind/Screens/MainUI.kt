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




@Composable
fun Ring(
    color: Color,
    strokeWidth: Int = 3,
    progress: Float = 1f,
    ContentPadding: Int = 1,
    strokeCap: StrokeCap = StrokeCap.Butt,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val stroke = Stroke(width = strokeWidth.dp.toPx(), cap = strokeCap)
            val radius = size.minDimension / 2
            val topLeft = Offset(center.x - radius, center.y - radius)

            drawCircle(             
                color = Color.Black.copy(alpha = 0.1f), // faint dark circle
                radius = radius,                         // radius of the circle
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * progress.coerceIn(0f, 1f),
                useCenter = false,
                topLeft = topLeft,
                size = Size(radius * 2, radius * 2),
                style = stroke,
            )
        }
        Box(modifier = Modifier.padding((strokeWidth+ContentPadding).dp)) {
            content()
        }
    }
}

@Composable
fun Main() {
    
    MAINStart()
    
    LazyScreen( title ={ Header.Main() }, showBack = false) {
        
                LazyCard{ Disipline() } 







                                
            
        
                LazzyList(
                    apps.filter { it.Worth > 0 },
                    key = { it.id },
                ) { app ->
                    val icon = getAppIcon(app.pkg)
                    val progress = (app.NowTime.toFloat() / app.DoneTime.toFloat()).coerceIn(0f, 1f)

                    
                    LazyCard {
                        LazzyRow {
                            UI.move(10)

                            // Icon with circular progress ring
                            val ringColor = lerp(Color.Red, Color.Green, progress)
                            
                            Ring(
                                color = ringColor,
                                progress = progress,
                                ContentPadding= -3,
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                ) {
                                    LazyImage(icon) // your image

                                    // Overlay circle on top
                                    Canvas(modifier = Modifier
                                            .matchParentSize()
                                            .padding(3.dp)
                                    ) {
                                        drawArc(
                                            color = Color(0xFF171717),//10%darker
                                            startAngle = 0f,                         // where the arc starts
                                            sweepAngle = 360f,                        // full circle
                                            useCenter = false,                        // don’t draw lines to the center
                                            style = Stroke(width = 5.dp.toPx())       // stroke width, convert dp to pixels
                                        )
                                    }

                                }

                            }
                            UI.move(10)
                            // Priority star
                            Text(
                                "★",
                                fontSize = 12.sp,
                                color = if (app.Worth > 10) Color.Yellow else Color.Gray,
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                            )
                            
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
        Text(
            text = coloredTarget,
            modifier = Modifier
                .heightIn(max = 200.dp)
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
                .height(200.dp)
                .verticalScroll(ScrollINPUTText),
            placeholder = { Text("Start typing...") }
        )
    }
}



