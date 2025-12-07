fun Mod.w(min: Any?, max: Any? = min) = this.widthIn(max = toDp(max), min = toDp(min))
fun Mod.h(min: Any?, max: Any? = min) = this.heightIn(max = toDp(max), min = toDp(min))
fun Mod.s(value: Any?) = this.size(toDp(value))

fun Modifier.maxS(): Mod= this.fillMaxSize()
fun Mod.maxW(): Mod= this.fillMaxWidth()
fun Mod.maxH(): Mod= this.fillMaxHeight()

