package Screens

import android.content.Intent
import android.os.Build
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material3.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.productivity.wind.Bar
import com.productivity.wind.Global1
import com.productivity.wind.Global1.context
import com.productivity.wind.LazyMenu
import com.productivity.wind.LazyPopup
import com.productivity.wind.R
import com.productivity.wind.SettingItem
import com.productivity.wind.SettingsScreen
import com.productivity.wind.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext




@Composable
fun Main() {
    LazyMenu { Menu();  }
    if (Bar.NewDay == true) { Bar.HowManyDoneRetypes_InDay = 0}

    NewDayWaterDo()
    Screen_Layout(showBack = false){
        Disipline()
        German()
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



//region UI ELEMENTS

@Composable
fun Screen_Layout(showBack: Boolean=true, content: @Composable () -> Unit) {
    SettingsScreen(
        titleContent = { MainHeader() },
        showBack = showBack,
        showSearch = false
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun MainHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuIcon()
        EditIcon()
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "Points: ${Bar.funTime}", fontSize = 18.sp)
        Text(text = "WaterDo: ${Bar.WaterDOtime_spent}", fontSize = 18.sp)


        Spacer(modifier = Modifier.weight(1f))

        ConfigureIcon()
    }

}

//region MENU

@Composable
fun MenuHeader(){
    val safeStartPadding = max(0.dp, Bar.halfWidth / 2 - 50.dp)
    Column(modifier = Modifier.padding(start = safeStartPadding), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.baseline_radar_24),
            contentDescription = "Radar Icon",
            tint = Color(0xFFFFD700),
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Wind",
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

}
@Composable
fun Menu() {

    SettingsScreen(
        titleContent = { MenuHeader() },
        onSearchClick = { },
        showBack = false,
        showSearch = false,
        showDivider = false
    ) {
        SettingItem(
            icon = Icons.Outlined.Chat,
            title = "Contact Support",
            onClick = { SupportEmail(); Bar.ShowMenu = false }
        )
        /*
    SettingItem(
        icon = Icons.Outlined.Landscape,
        title = "Premium",
        onClick = { Global1.navController.navigate("")  }
    )
    */
        SettingItem(
            icon = Icons.Outlined.Landscape,
            title = "Settings",
            onClick = { Global1.navController.navigate("SettingsScreen"); Bar.ShowMenu = false }
        )
//        SettingItem(
//            icon = Icons.Outlined.QueryStats,
//            title = "Achievements",
//            onClick = { Global1.navController.navigate("Achievements"); Bar.ShowMenu = false }
//        )
    }
}
fun SupportEmail() {
    val subject = "Support Request – App Issue"

    val body = buildString {
        appendLine()
        appendLine("Phone Info:")
        appendLine("• Manufacturer: ${Build.MANUFACTURER}")
        appendLine("• Model: ${Build.MODEL}")
        appendLine("• Android Version: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
        appendLine()
        appendLine("I'm experiencing the following issue with the app:")
        appendLine()

    }

    val email = arrayOf("productivitywind@gmail.com")

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, email)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    val chooser = Intent.createChooser(intent, "Send Email").apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    context.startActivity(chooser)
}

//endregion MENU


@Composable
fun EditPopUp(show: MutableState<Boolean>) {
    var TemporaryTargetText by remember { mutableStateOf("") }
    TemporaryTargetText = Bar.targetText
    LazyPopup(
        show = show,
        onDismiss = { TemporaryTargetText = Bar.targetText },
        title = "Edit Text",
        message = "",
        content = {
            OutlinedTextField(
                value = TemporaryTargetText,
                onValueChange = { TemporaryTargetText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        showCancel = true,
        onConfirm = { Bar.targetText = TemporaryTargetText; Bar.FirstEditText = false },
        onCancel = { TemporaryTargetText = Bar.targetText }
    )
}


//endregion UI ELEMENTS



