package com.productivity.wind.Imports.Utils.RunKotlin 

import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

val jsActions = mList<Str>()

fun RunJs(script: Str): Any? {
    val ctx = Context.enter()
    ctx.optimizationLevel = -1
    val scope = ctx.initStandardObjects()
    return ctx.evaluateString(scope, script, "<cmd>", 1, null)
}

fun 

//test: var x=5
//clickbutton
//5h later x=6








