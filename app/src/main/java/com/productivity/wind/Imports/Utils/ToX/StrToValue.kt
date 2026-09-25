class StrToValue {

    fun convert(type: Str, raw: Str): Any? {
        val clazz = StrToClass(type) ?: return null

        return when {
            isString(clazz) -> raw.removeSurrounding("\"")
            isInteger(clazz) -> raw.toIntOrNull()
            isBoolean(clazz) -> raw.toBooleanStrictOrNull()
            isLong(clazz) -> raw.toLongOrNull()
            isDouble(clazz) -> raw.toDoubleOrNull()
            isFloat(clazz) -> raw.toFloatOrNull()
            isEnum(clazz) -> getEnumValue(clazz, raw)
            else -> null
        }
    }
}
