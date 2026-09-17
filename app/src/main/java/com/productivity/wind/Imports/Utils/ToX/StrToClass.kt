private fun StrToClass(type: Str): Class<*>? {
    return when {
        type == "java.lang.String" -> String::class.java
        type == "java.lang.Integer" -> Integer::class.java
        type == "java.lang.Boolean" -> Boolean::class.java
        type == "java.lang.Long" -> Long::class.java
        type == "java.lang.Double" -> Double::class.java
        type == "java.lang.Float" -> Float::class.java
        type == "null" -> null

        type.startsWith(pkgMyApp) -> {
            Class.forName(type)
        }

        else -> null
    }
}
