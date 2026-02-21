package com.productivity.wind.Screens
  
import androidx.compose.material3.Text
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.heightIn
import com.productivity.wind.MAINStart
import com.productivity.wind.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.*
import kotlin.system.*
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import androidx.compose.foundation.*
import androidx.compose.ui.focus.*
import com.productivity.wind.Imports.UI_visible.*
import androidx.activity.compose.*


@Composable
fun Main() {
    RunOnce { MAINStart() }

    var searching by r(no)
    var tag = r("")
    var showAll by r(no)

    BtnFloating { goTo("Challenge") }

    LazyScreen(
        top = { TopBar(searching, tag) { searching = it } },
        showBack = no
    ) {
        if (!searching) {
            renderTasks(
                copyTsk = Bar.copyTsk,
                doTsk = Bar.doTsk,
                apps = Bar.apps,
                filter = null
            )
        } else {
            val filter: (Any) -> Boolean = { task ->
                when (task) {
                    is CopyTsk -> task.input.contains(tag.it)
                    is DoTsk -> task.name.contains(tag.it) || task.description.contains(tag.it)
                    is AppTask -> task.name.contains(tag.it)
                    else -> false
                }
            }

            renderTasks(
                copyTsk = Bar.copyTsk,
                doTsk = Bar.doTsk,
                apps = Bar.apps,
                filter = if (showAll) null else filter
            )
        }
    }
}

@Composable
fun TopBar(searching: Boolean, tag: MutableState<String>, onSearchToggle: (Boolean) -> Unit) {
    if (!searching) {
        Icon.Menu { menu = yes }
        Icon.Reload { showOrderNotification(11) }
        move(w = 12)
        Text("Points ${Bar.funTime}")
        End { Icon.Search { onSearchToggle(true) } }
    } else {
        Icon.Back { onSearchToggle(false) }
        TinyInput(tag, Mod.h(40).weight(1f).Hscroll(), isInt = no, maxLetters = 400)
        move(30)
        BackHandler { onSearchToggle(false) }
    }
}

@Composable
fun renderTasks(
    copyTsk: List<CopyTsk>,
    doTsk: List<DoTsk>,
    apps: List<AppTask>,
    filter: ((Any) -> Boolean)? = null
) {
    copyTsk.forEach { if (filter == null || filter(it)) LazyCard { CopyTskUI(it) } }
    doTsk.forEach { 
        if (!it.done() && (filter == null || filter(it)) && taskDueToday(it.schedule)) {
            LazyCard(
                modUI = Mod.space(start = 8),
                modCard = Mod.space(h=8, w=10).maxW().click { goTo("ToDo/${it.id}") }
            ) { DoTskUI(it) }
        }
    }

    apps.forEach { 
        if (!it.done) {
            if (it.NowTime > it.DoneTime - 1 && !it.done) {
                Bar.funTime += it.Worth
                Bar.apps.edit(it) { done = yes }
                Vlog("${it.name} completed")
            }
            if (filter == null || filter(it)) Item.AppTaskUI(it)
        }
    }
}
		



















				
