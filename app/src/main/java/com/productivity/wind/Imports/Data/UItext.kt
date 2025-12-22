






fun UIText(text: UIStr, style: StrStyle = StrStyle()): UIStr {
    return makeUIStr {
        pushStyle(style)
        add(text)
        pop()
    }
}

fun UIStr.getStyle(): StrStyle {
    return this.spanStyles.firstOrNull()?.item ?: StrStyle()
}

fun Any.size(value: TextUnit): UIStr {
    val uiStr = toUIStr(this)
    return UIText(uiStr, uiStr.getStyle().copy(fontSize = value))
}

fun Any.bold(): UIStr {
	val uiStr = toUIStr(this)
	return UIText(uiStr, uiStr.getStyle().copy(fontWeight = FontWeight.Bold))
}

fun Any.color(value: Color): UIStr {
	val uiStr = toUIStr(this)
	return UIText(uiStr, uiStr.getStyle().copy(color = value))
}
fun Any.gold(): UIStr {
	val uiStr = toUIStr(this)
	return UIText(uiStr, uiStr.getStyle().copy(color = Gold))
}






