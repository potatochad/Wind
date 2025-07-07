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


@Composable
fun TrueMain(navController: NavController) {
    var OtherM = ItemManager("OtherSettings")
    var OtherSettings = OtherM.createOrUpdate(defaults =
        mapOf("showWorkAlert" to false)
    )
    val S_manager = ItemManager("trueMain_settings")
    var Settings = S_manager.createOrUpdate(defaults = mapOf(
        "targetText" to "I am doing this project to regain freedom in my life. It is most important project ever, but that doesn't mean i need to take it soop seriously. I need to only focus on it, do the pomo. And spend half time improving, half time using the product. Done. I need to keep with it for 100 days for it to bear fruit. Right now it won't work/ the initial mvp is terrible. But that's ok. I will improve it slowly, one tiny feature at a time. All i must do is stick with the idea: type stuff and get time to have fun. Done. That is it!!!!. Goal is consistency, nothing else, nothing else!!",
        "LetterToTime" to 5,
        "DoneRetype_to_time" to 60,
        "funTime" to 0,
        "currentInput" to "",
        "highestCorrect" to "",
    ))

    val targetText = Settings.string("targetText")
    val DoneRetype_to_time = Settings.int("DoneRetype_to_time")
    var funTime = Settings.int("DoneRetype_to_time")
    var input = Settings.string("currentInput")

    var correctInput by Synched { "" }
    var highestCorrect = Settings.string("highestCorrect")




    val coloredTarget = buildAnnotatedString {
        val correctChars = targetText.zip(input).takeWhile { it.first == it.second }.size
        correctInput = input.take(correctChars)
        for (i in targetText.indices) {
            if (i < correctInput.length) {
                withStyle(style = SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold)) {
                    append(targetText[i])
                }
            } else {
                append(targetText[i])
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text(text = "Header", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Fun time: ${funTime}s", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = coloredTarget)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it

                val newlyEarned = correctInput.length - highestCorrect
                if (newlyEarned > 0) {
                    funTime += newlyEarned * Settings.int("LetterToTime")
                    highestCorrect = correctInput.length.toString()
                }

                if (correctInput == targetText) {
                    funTime += DoneRetype_to_time
                    input = ""
                    highestCorrect = 0
                }
            },
            modifier = Modifier.fillMaxWidth().height(200.dp).verticalScroll(rememberScrollState()),
            placeholder = { Text("Start typing...") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (funTime > 0) {
                    navController.navigate("FunScreen")
                }
                else {

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Chill Time 🌴")
        }
    }
}


@Composable
fun FunScreen(navController: NavHostController) {
    var funTime by Synched { startTime }
    var running by Synched { true }

    LaunchedEffect(Unit) {
        while (running && funTime > 0) {
            delay(1000)
            funTime--
        }
        if (funTime <= 0) {
            running = false
            onBack()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                running = false
                onBack()
            }) {
                Text("🔙 Back to Winning")
            }

            Text("Fun time: ${funTime}s", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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

