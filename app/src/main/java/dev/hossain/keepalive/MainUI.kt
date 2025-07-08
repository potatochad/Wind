package dev.hossain.keepalive

import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow


// Usage examples
@Composable
fun TrueMain(navController: NavController) {

    log("targetText; ${Bar.targetText}, LetterToTime;${Bar.LetterToTime}, DoneRetype_to_time;${Bar.DoneRetype_to_time}, currentInput;${Bar.currentInput}, highestCorrect;${Bar.highestCorrect}")

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

    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text(text = "Header", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Fun time: ${Bar.funTime}s", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = coloredTarget, modifier = Modifier.height(200.dp).verticalScroll(rememberScrollState()))
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = Bar.currentInput,
            onValueChange = {
                Bar.currentInput = it
                log("Bar.currentInput, on value change; ${Bar.currentInput}")
                log("the itttttt; ${it}")
                log("initOnce; ${initOnce}")

                val correctChars = Bar.targetText.zip(Bar.currentInput).takeWhile { it.first == it.second }.size
                val correctInput = Bar.currentInput.take(correctChars)


                val newlyEarned = correctInput.length - Bar.highestCorrect
                if (newlyEarned > 0) {
                    var oldFunTime = Bar.funTime
                    Bar.funTime += newlyEarned * Bar.LetterToTime; if (oldFunTime===Bar.funTime){ log("!funTime += newlyEarned * S_Data.int(LetterToTime)- VALUE DID NOT CHANGE, CLUES: ${oldFunTime}-OLDFUNTIME,,,${Bar.funTime}-funTime,,,${newlyEarned}-newlyEarned,,,${Bar.LetterToTime}-LetterToTime,,,", "BAD")}
                    Bar.highestCorrect = correctInput.length
                }

                if (correctInput == Bar.targetText) {
                    Bar.funTime += Bar.DoneRetype_to_time
                    Bar.currentInput = ""  // Reset input when completed
                    Bar.highestCorrect = 0
                }

            },
            modifier = Modifier.fillMaxWidth().height(200.dp).verticalScroll(rememberScrollState()),
            placeholder = { Text("Start typing...") }
        )

        Spacer(modifier = Modifier.height(24.dp))
        ChillTimeButton(navController)
    }
}


@Composable
fun FunScreen(navController: NavHostController) {
    var running by Synched { true }


    LaunchedEffect(Unit) {
        while (running && Bar.funTime > 0) {
            delay(1000)
            Bar.funTime--
        }
        if (Bar.funTime <= 0) {
            running = false
            navController.navigate("TrueMain")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                running = false
                navController.navigate("TrueMain")
            }) {
                Text("🔙 Back to Winning")
            }

            Text("Fun time: ${Bar.funTime}s", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    loadUrl("https://play.famobi.com/wrapper/rise-up/A1000-10")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        )
    }
}

