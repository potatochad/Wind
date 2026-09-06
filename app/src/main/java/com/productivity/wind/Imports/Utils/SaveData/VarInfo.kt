class MapVarInfo {
    private val map = mutableMapOf<String, VarInfo<*>>()

    operator fun get(name: String) = map[name]

    operator fun set(name: String, info: VarInfo<*>) {
        map[name] = info
    }

    fun remove(name: String) = map.remove(name)

    fun clear() = map.clear()

    fun contains(name: String) = name in map
}
