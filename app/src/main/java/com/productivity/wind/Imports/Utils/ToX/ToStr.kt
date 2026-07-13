
fun toStr(it: Any?): Str = when (it) {
    is Str -> it
    is UIStr -> it.text
    is LatLng -> "${it.latitude},${it.longitude}"
    is Pair<*, *> -> "${it.first},${it.second}"
    is Double, is Float, is Int, is Long -> it.toString()
    is List<*> -> it.joinToString("\n") { it.toString() }
    null -> ""
    else -> it.toString()
}




private val fieldsCache = mutableMapOf<Class<*>, List<java.lang.reflect.Field>>()

fun toStr(id: Str = "", vars: List<VarInfo<*>>): Str {
    val varsStr = vars.joinToString(", ") { v ->
        "[${v.name}][${v.type}][${ComplexTypeToStr(v.value)}]"
    }

    return "{ id: [$id], vars: $varsStr }"
}
// SLOWWWW
fun ComplexTypeToStr(value: Any?): Str {
    if (value == null) return "null"

    return when (value) {
        is String -> "\"$value\""
        is Number, is Boolean -> value.toString()

        else -> {
            val fields = getFields(value.javaClass)
                .joinToString(", ") { field ->
                    "${field.name}=${ComplexTypeToStr(field.get(value))}"
                }

            "${value.javaClass.simpleName}($fields)"
        }
    }
}

fun getFields(clazz: Class<*>): List<java.lang.reflect.Field> {
    return fieldsCache.getOrPut(clazz) {
        clazz.declaredFields
            .filter { 
                !it.isSynthetic &&
                !java.lang.reflect.Modifier.isStatic(it.modifiers)
            }
            .onEach { it.isAccessible = true }
    }
}




fun toListStr(it: Any?): ListStr = when (it) {      
    is List<*> -> it.map { toStr(it) }              
    is Str -> it.lines()                        
    is UIStr -> it.text.lines()          
    null -> emptyList()
    else -> listOf(toStr(it))
}



