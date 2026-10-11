package com.productivity.wind.Imports.Utils.VarVal

import androidx.compose.runtime.*
import kotlin.collections.*
import androidx.compose.runtime.getValue
import com.productivity.wind.Imports.Utils.*


class By<T>(value: T){
	var it by mState(value)
	var delegateValue = mState(value)
	var id by mState("")

	val builds = mList<Do3_<ValVar, Str, mState_<T>>>()
    val gets = mList<Do_<ValVar>>()
    val sets = mList<Do3_<ValVar, Str, T>>()

    operator fun provideDelegate(thisRef: Any?, property: ValVar): By<T> {
		id = property.name
		
		builds.forEach { it(property, id, delegateValue) }

		it = delegateValue.it 
        return this
    }
    operator fun getValue(thisRef: Any?, property: ValVar): T {
		gets.forEach { it(property) }
		
		it = delegateValue.it 
        return it
    }
    operator fun setValue(thisRef: Any?, property: ValVar, newValue: T) {
        it = newValue
		delegateValue.it = it
		sets.forEach { it(property, id, newValue) }
    }
}



fun <T> By<T>.onBuild(
    x: Do3_<ValVar, Str, mState_<T>>
) = apply {
    builds += x
}

fun <T> By<T>.onGet(
    x: Do_<ValVar>
) = apply {
    gets += x
}

fun <T> By<T>.onSet(
    x: Do3_<ValVar, Str, T>
) = apply {
    sets += x
}

private val firstGetOrSet = mutableMapOf<By<*>, Bool>()

fun <T> By<T>.onFirstGetOrSet(
    x: Do3_<ValVar, Str, mState_<T>>
) = apply {
    fun first(property: ValVar) {
        if (firstGetOrSet[this] != true) {
            firstGetOrSet[this] = true
            x(property, id, delegateValue)
        }
    }

    onGet { property -> first(property) }
    onSet { property, _, _ -> first(property) }
}
