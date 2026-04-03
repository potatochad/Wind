package com.productivity.wind.Imports.Utils.RunKotlin 

import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

fun RunJs(script: Str): Any? {
    val ctx = Context.enter()
    ctx.optimizationLevel = -1
    val scope = ctx.initStandardObjects()
    return ctx.evaluateString(scope, script, "<cmd>", 1, null)
}




