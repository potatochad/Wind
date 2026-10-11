package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.String.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.productivity.wind.Imports.Utils.*


/*

Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
)
*/

@Composable
fun LogTxt(
    text: Str,
    Do: Do_<StrStyle>
) {
    val style = StrStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
    )

    Text(
        text = text.strStyle(style),
        softWrap = false
    )

    Do(style)
}





