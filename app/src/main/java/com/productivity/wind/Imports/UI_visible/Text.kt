@Composable
fun LogTxt(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        softWrap = false,
        fontFamily = FontFamily.Monospace
    )
}
