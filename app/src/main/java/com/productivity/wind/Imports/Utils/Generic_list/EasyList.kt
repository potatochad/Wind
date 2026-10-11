package com.productivity.wind.Imports.Utils.Generic_list

import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.String.*
import kotlin.collections.*
import androidx.compose.runtime.Composable
import com.productivity.wind.Imports.Utils.*


abstract class EasyListExtra<T>(
    items: Iterable<T>
) : Iterable<T> {
    var it = mStateList<T>().apply { addAll(items) }
    
    var onAdd: mList<T>.(T) -> Unit = {}
    var onAddAll: mList<T>.(Iterable<T>) -> Unit = {}
    var onRemove: mList<T>.(T) -> Unit = {}

    val pending = mList<Do>()
    var eachDepth = 0

    fun waitIfEach(Do: Do) {
        if (eachDepth > 0) pending.add(Do)
        else Do()
    }

    

    //‼️‼️ADD and remove can be the only code 
    // THAT ACTUALLY REMOVES THE ITEMS OR ADDS IT   
    //IF want add more: waitIfEach and onX
    //----------------------------------------//
    fun add(item: T) = waitIfEach {
        it.add(item)
        it.onAdd(item)
    }
    fun add(index: Int, item: T) = waitIfEach {
        it.add(index, item)
        it.onAdd(item)
    }
    fun addAll(items: Iterable<T>) = waitIfEach {
       it.addAll(items)
       it.onAddAll(items)
    }

    fun remove(item: T) = waitIfEach {
        val index = it.indexOf(item)

        if (index != -1) {
            it.removeAt(index)
            it.onRemove(item)
        }
    }
    //-----------------------------------------//


    operator fun get(index: Int) = it[index]
    operator fun set(index: Int, value: T){ it[index] = value }
    operator fun plusAssign(item: T) = add(item)
    operator fun minusAssign(item: T) = remove(item)
    
    //‼️ shall crash onRemove
    override fun iterator(): Iterator<T> = it.iterator()
}



//‼️‼️ INSIDE CODE
//ONLY USE function ‼️ MYYY EACH, REMOVE, ADD
class EasyList<T> : EasyListExtra<T> {
    constructor(vararg items: T) : super(items.toList())
    constructor(items: Iterable<T>) : super(items)
    
    val id = Id()

    
    val size get() = it.size
    val notEmpty get() = it.notEmpty
    val empty get() = it.empty


    fun clear() {
        it.toList().forEach { item ->
            remove(item)
        }
    }

    fun removeAt(index: Int) {
        it.getOrNull(index)?.let { item ->
            remove(item)
        }
    }


    fun each(block: (T) -> Unit) {
        each { _, item ->
            block(item)
        }
    }

    fun each(block: (Int, T) -> Unit) {
        eachDepth++
        try {
            for (index in it.indices) {
                block(index, it[index])
            }
        } finally {
            eachDepth--

            if (eachDepth == 0) {
                pending.forEach { it() }
                pending.clear()
            }

        }
    }
    
    
    fun filter(logic: (T) -> Bool): EasyList<T> {
        val result = EasyList<T>()
        
        each { _, item ->
            if (logic(item)) {
                result.add(item)
            }
        }
        return result
    }



}



@Composable
fun <T> rEasyList(vararg items: T) = r { EasyList(*items) }

fun <T, L : EasyListExtra<T>> L.onAdd(
    block: mList<T>.(T) -> Unit
): L {
    onAdd = block
    return this
}

fun <T, L : EasyListExtra<T>> L.onRemove(
    block: mList<T>.(T) -> Unit
): L {
    onRemove = block
    return this
}
fun <T, L : EasyListExtra<T>> L.onAddAll(
    block: mList<T>.(Iterable<T>) -> Unit
): L {
    onAddAll = block
    return this
}



