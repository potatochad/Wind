package com.productivity.wind.Imports.Utils

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import org.mozilla.javascript.Context


fun RunJs(script: Str): Any? {
    val ctx = Context.enter()
    ctx.optimizationLevel = -1
    val scope = ctx.initStandardObjects()
    return ctx.evaluateString(scope, script, "<cmd>", 1, null)
}



@Composable
fun OnceEach(
    s: Any = 1000,
    condition: () -> Bool = { yes },
    action: Wait
) {
	RunOnce {
		while (condition()) {
			action()
			delay(toL(s))
		}
	}
}
fun Each(
	s: Any = 1000,
    condition: () -> Bool = { yes },
    action: Do
) {
	Do {
		while (condition()) {
			action()
			delay(toL(s))
		}
	}
}


@Composable
fun RunOnce(key1: Any? = Unit, key2: Any? = Unit, Do: Wait) {
    LaunchedEffect(key1, key2) {
        Do()
    }
}

fun wait(x: Any = 20, Do: Wait) {
    appScope.launch {
		try {
			wait(x)
			Do()
		} catch (e: Exception) {
			log("<fun wait>: ${e.message}")
		}
    }
}
suspend fun wait(x: Any = 20) { delay(toL(x)) }


fun Do(eLog: Str="", onError: Wait ={}, Do: Wait) {
	var whereCalled by mState("")
	appScope.launch {
		try {
			whereCalled = callerId(2)
			Do()
		} catch (e: CancellationException) {
			throw e
		} catch (e: Exception) {
			Vlog("Do error, $eLog: ${e.message}, $whereCalled")
			onError()
		}
	} 
}
fun NoLag(onError: Str = "noLag error", Do: Wait) {
    appScope.launch(Dispatchers.Default) {
		try {
			Do()
		} catch (e: Exception) {
			Vlog("$onError: ${e.message}")
		}
    }
}


inline fun check(
    condition: Bool,
    msg: Str = "",
    Do: Do = {},
) {
	if (condition) {
		if (msg.isNotEmpty()) Vlog(msg)
		Do()        // safe
	}
}


fun <T> runHeavyTask(
    task: () -> T,         
    onResult: Do_<T>  
) {
    CoroutineScope(Dispatchers.Default).launch { // off UI
        val result = task()                       // run heavy work
        withContext(Dispatchers.Main) {           // back to UI
            onResult(result)                      // update Compose with result
        }
    }
}








