data class VarInfo<T>(
	val name: Str,
    val value: T,
    val type: Class<*>? = value?.let { it::class.java },
	val typeStr: Str = type?.name ?: "null",
)
