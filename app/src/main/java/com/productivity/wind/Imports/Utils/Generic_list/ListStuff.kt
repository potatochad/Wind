package com.productivity.wind.Imports.Utils.Generic_list

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.String.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.*
import kotlin.collections.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import kotlinx.serialization.*
import com.productivity.wind.Imports.Utils.*


inline fun <T, R : Comparable<R>> Iterable<T>.max(selector: (T) -> R): R? =
    maxOfOrNull(selector)

inline fun <T, R : Comparable<R>> Iterable<T>.min(selector: (T) -> R): R? =
    minOfOrNull(selector)


fun <T> MutableList<T>.keep(max: Int) {
    if (size > max) {
        subList(0, size - max).clear()
    }
}

    
@Composable
fun <T> List<T>.onDelete(
    onDelete: (List<T>) -> Unit
): List<T> {
    var previous by r(emptyList<T>())
    var deleted by r(emptyList<T>())

    LaunchedEffect(this) {
        snapshotFlow { this@onDelete.toList() }
            .collect { now ->
                val removed =
                    if (now.size < previous.size) {
                        previous - now.toSet()
                    } else emptyList()

                if (removed.notEmpty) {
                    onDelete(removed)
                    deleted = removed
                }

                previous = now
            }
    }

    return deleted
}


@Composable
fun <T> List<T>.rNewItems(): List<T> {
    var previous by r(emptyList<T>())
    var result by r(emptyList<T>())

    RunOnce(this.toList()) {
        snapshotFlow { this@rNewItems.toList() }
            .collect { now ->
                val newItems =
                    if (now.size < previous.size) {
                        now // reset
                    } else {
                        now.drop(previous.size)
                    }
                result = newItems
                previous = now
            }
    }

    return result
}


fun Any?.isArray(): Bool =
    this?.javaClass?.isArray == true


val List<*>.isRecomposable: Bool
    get() = this is SnapshotStateList<*>

inline fun <reified T> isSerializable(): Bool {
    return try {
        serializer<T>()
        true
    } catch (_: Exception) {
        false
    }
}
    
val Any?.tempId: Str
    get() = "${this?.javaClass?.simpleName ?: "null"}@${System.identityHashCode(this)}"

val List<*>.sameType: Bool
    get() {
        if (size <= 1) return true
        val type = firstOrNull()?.javaClass
        return all { it?.javaClass == type }
    }

val List<*>.itemKType: KClass<*>?
    get() = if (sameType) firstOrNull()?.let { it::class } else null

val KClass<*>.isDataClass: Bool
    get() = this.isData

val KClass<*>.isSimpleClass: Bool
    get() =
        !isData &&
        !isSealed &&
        !java.isInterface &&
        !java.isEnum

fun KClass<*>.hasId(): Bool {
    if (!this.isData) return no
    return this.props.any { it.name == "id" }
}
fun KClass<*>.hasProp(name: Str): Bool =
    props.any { it.name == name }
fun KClass<*>.hasProps(vararg names: Str): Bool =
    names.all { name ->
        props.any { it.name == name }
    }
    
val KClass<*>.props
    get() = this.memberProperties

fun <T : Any> KClass<T>.getProp(name: Str) =
    props.firstOrNull { it.name == name }
        .also { if (it == null) Vlog("Cant find prop ($name) in instance $this") }
    
fun <T : Any> KClass<T>.getPropValue(
    instance: T,   // User(1, "A")
    name: Str
): Any? {
    val prop = this.getProp(name)

    @Suppress("UNCHECKED_CAST")
    return (prop as? KProperty1<T, *>)?.get(instance)
}

val KProperty1<*, *>.isMutable: Bool
    get() = this is KMutableProperty1<*, *>

fun <T : Any> KClass<T>.setProp(
    instance: T,   // User(1, "A")
    name: Str,
    value: Any?
) {
    val prop = this.getProp(name) 
    val mutableProp = prop as? KMutableProperty1<T, Any?> ?: run {
        Vlog("Cant change val prop ($name) in instance $instance")
        return
    }

    mutableProp.set(instance, value)
}

val Any.className: Str
    get() = this::class.simpleName ?: "Unknown"
