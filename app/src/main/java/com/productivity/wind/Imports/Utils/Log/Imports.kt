//‼️‼️THIS IS VERY SENSITIVE CODE AND CAN EASILY NOT WORK 
//‼️‼️‼️DONT USE MY OUTSIDE FUNCTIONS‼️‼️

package com.productivity.wind.Imports.Utils.Log

import com.productivity.wind.Bar
import com.productivity.wind.Imports.Utils.appScope
import com.productivity.wind.Imports.Utils.App

import android.os.*
import android.content.*
import android.util.*
import android.widget.Toast
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException


typealias Str = String
typealias Bool = Boolean
typealias Do = () -> Unit
typealias Wait = suspend () -> Unit
typealias mList<T> = MutableList<T>

typealias ui = @Composable () -> Unit
typealias ui_<T> = @Composable (T) -> Unit


val Str.size: Int
    get() = length
fun Str.last(n: Int): Str = takeLast(n)
fun Str.fromTo(start: Int, end: Int = this.size) = substring(start, end)
fun <T> mList<T>.fromTo(start: Int, end: Int = size): mList<T> = subList(start, end)

fun <T> mList<T>.keep(max: Int) {
    if (size > max) {
        fromTo(0, size - max).clear()
    }
}

@Composable
fun <T> r(x: () -> T) = remember { x() }
fun <T> m(value: T) = mutableStateOf(value)
@Composable
fun <T> r(x: T) = r { m(x) }
@Composable
inline fun <T> r(vararg keys: Any?, crossinline calc: () -> T): T = remember(*keys, calculation = calc)


fun <T> mList() = mutableStateListOf<T>()


fun Do(eLog: Str="", onError: Wait ={}, Do: Wait) {
	var whereCalled by m("")
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
fun callerId(depth: Int = 0): Str {
    val stack = Throwable().stackTrace

    if (depth !in stack.indices) {
        return "invalid-depth"
    }

    val it = stack[depth]

    return "${it.fileName}-${it.className.substringAfterLast('.')}-${it.methodName}"
}





