package com.productivity.wind.Imports.UI_visible

// Android & Kotlin
import android.os.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.activity.compose.*
import kotlinx.coroutines.*
import kotlin.math.*
import kotlin.collections.*

// Jetpack Compose Core
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.*

// Compose UI
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import androidx.compose.ui.semantics.*

// Compose Material & Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*

// Compose Animation
import androidx.compose.animation.*
import androidx.compose.animation.core.*

// Accompanist & Maps
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

// Your project imports
import com.productivity.wind.*
import com.productivity.wind.Imports.*
import com.productivity.wind.Imports.Data.*






object AllowedChars {
    val NUMBERS = Regex("[^0-9]")
    val ALPHABETS = Regex("[^a-zA-Z]")
    val ALPHANUMERIC = Regex("[^a-zA-Z0-9]")
    val ALL = null
}

internal enum class LastInputType {
    SUGGESTION,
    TYPING
}

@Composable
fun KwikLoadingView(
    text: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        KwikCircularLoading(
            color = MaterialTheme.colorScheme.primary
        )

        move(h=8)

        KwikText.TitleSmall(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun KwikLinearLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = Color.Transparent
) {
    LinearProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor
    )
}

@Composable
fun KwikCircularLoading(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = Color.Transparent
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor
    )
}

@Composable
@Preview(showBackground = true)
private fun KwikLoadingViewPreview() {
    KwikLoadingView(
        text = "Loading... Please Wait..."
    )
}

@Composable
@Preview(showBackground = true)
private fun KwikLoadingPreview() {
    KwikCircularLoading()
}

/**
 * `Text` component that uses the Material3 typography system.
 * */
object KwikText {

    /**
     * Base `Text` component that uses the Material3 typography system.
     *
     * @param modifier Modifier to be applied to the text
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param style Text style
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * @param onTextLayout Text layout callback
     *
     * @see [Text]
     *
     * [KwikText]'s functions like [DisplayLarge], [DisplayMedium], [BodyMedium] etc... rely on it to display text.
     * */
    @Composable
    fun RenderText(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        textDecoration: TextDecoration = TextDecoration.None,
        style: TextStyle = MaterialTheme.typography.bodyLarge,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
        onTextLayout: (TextLayoutResult) -> Unit = {},
    ) {
        when (text) {
            is String -> Text(
                text = text,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                fontStyle = fontStyle,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow,
                onTextLayout = {
                    onTextLayout(it)
                }
            )
            is Int -> Text(
                text = text.toString(),
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                fontStyle = fontStyle,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow,
                onTextLayout = {
                    onTextLayout(it)
                }
            )
            is AnnotatedString -> Text(
                text = text,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                fontStyle = fontStyle,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow,
                onTextLayout = {
                    onTextLayout(it)
                }
            )
        }
    }

    /**
     * Display Large Text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun DisplayLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        fontStyle: FontStyle? = null,
        textAlign: TextAlign = TextAlign.Start,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.displayLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Medium Text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun DisplayMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.displayMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Small Text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun DisplaySmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.displaySmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Headline large text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun HeadlineLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Headline medium
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun HeadlineMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Headline small text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun HeadlineSmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Title large text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun TitleLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.titleLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Title medium text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun TitleMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.titleMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Title small text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun TitleSmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.titleSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Body large text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun BodyLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Body medium text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun BodyMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Body small text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun BodySmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.bodySmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Label large text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun LabelLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.labelLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Label medium text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun LabelMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.labelMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Label small text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun LabelSmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.labelSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * A quote text component for displaying quotes.
     *
     * @param modifier Modifier to be applied to the text
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param fontStyle Font style
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun Quote(
        modifier: Modifier = Modifier,
        text: Any,
        author: String? = null,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle = FontStyle.Italic,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        Row(
            modifier = modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            RenderText(
                modifier = modifier,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.displaySmall.fontSize)) {
                        append("“")
                        append(" ")
                    }
                    append(text.toString())
                    if(author != null) {
                        append(" ")
                        append("-")
                        append(" ")
                        append(author)
                    }
                },
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                fontStyle = fontStyle,
                style = MaterialTheme.typography.titleSmall,
                maxLines = maxLines,
                overflow = overflow
            )
        }
    }

}


val KwikColorPrimary = Color(0xFF2196F3)
val KwikColorSecondary = Color(0x9A2196F3)
val KwikColorTertiary = Color(0xFFFF5722)
val KwikColorWarning = Color(0xFFFF9800)
val KwikColorYellow = Color(0xFFFFEB3B)
val KwikColorError = Color(0xFFF44336)
val KwikColorSuccess = Color(0xFF4CAF50)
val KwikColorHint = Color(0xFF9B9B9B)
val KwikColorFilledTextFieldFocused = Color(0xFFE1E1E1)
val KwikColorFilledTextFieldUnfocused = Color(0xFFEAEAEA)
val KwikColorFilledTextFieldFocusedDarkMode = Color(0xFF424242)
val KwikColorFilledTextFieldUnfocusedDarkMode = Color(0x19ECEBEB)
val KwikColorFilledTextFieldError = Color(0x34F44336)


/**
 * A versatile filled text field component that can be used to take user input.
 * @param modifier: The modifier for the text field.
 * @param value: The value of the text field.
 * @param onValueChange: The callback that will be called when the value of the text field changes.
 * @param onKeyboardDone: The callback that will be called when the keyboard action is done.
 * @param onActionClick: The callback that will be called when the action icon is clicked.
 * @param onFocusChanged: The callback that will be called when the focus of the text field changes.
 * @param visualTransformation: The visual transformation of the text field. Refer to [VisualTransformation].
 * @param isEditable: If true, the text field is editable. Default is true.
 * @param placeholder: The placeholder text of the text field.
 * @param shape: The shape of the text field. Default is [RoundedCornerShape].
 * @param isError: If true, the text field will display an error state.
 * @param error: The error message to display when isError is true.
 * @param singleLine: If true, the text field will be single line. Default is true.
 * @param maxLength: The maximum length of the text field. Default is 35.
 * @param keyboardType: The keyboard type of the text field. Default is [KeyboardType.Text].
 * @param keyboardActions: The keyboard actions of the text field. Default is [KeyboardActions] with ImeAction.Done.
 * @param maxLines: The maximum number of lines of the text field. Default is 1.
 * @param allowedChars: The allowed characters in the text field. Default is [AllowedChars.ALL].
 * @param imeAction: The IME action of the text field. Default is [ImeAction.Done].
 * @param isValid: If true, the text field will display a valid state.
 * @param isTextCounterShown: If true, the text counter will be shown.
 * @param hint: The hint text of the text field.
 * @param hintVisibleOnError: If true, the hint will be visible only when there is an error.
 * @param leadingIcon: The leading icon of the text field.
 * @param trailingIcon: The trailing icon of the text field.
 * @param showClearTextButton: If true, the clear text button will be shown.
 * @param isLoading: If true, the loading indicator will be shown.
 * @param isBigTextField: If true, the text field will be big.
 * @param enabled: If true, the text field will be enabled. Default is true.
 * @param colors: The colors of the text field. Default is [OutlinedTextFieldDefaults.colors].
 *
 * Example usage:
 *
 * ```
 * val pirateName = rememberSaveable(stateSaver = TextFieldValue.Saver) {
 *    mutableStateOf(TextFieldValue(""))
 * }
 *
 * KwikTextField(
 *    value = pirateName,
 *    onValueChange = {
 *      pirateName.value = it
 *    },
 *    placeholder = "Jack Sparrow",
 *    keyboardType = KeyboardType.Phone,
 *    visualTransformation = VisualTransformation.None,
 *    imeAction = ImeAction.Done,
 * )
 * */
@Composable
fun KwikTextField(
    modifier: Modifier = Modifier,
    value: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
    onKeyboardDone: () -> Unit = {  },
    onActionClick: () -> Unit = {  },
    onFocusChanged: (Boolean) -> Unit = {  },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isEditable: Boolean = true,
    label: String? = null,
    placeholder: String,
    shape: Shape = MaterialTheme.shapes.medium,
    isError: Boolean = false,
    error: String = "Field is required",
    singleLine: Boolean = true,
    maxLength: Int = 65,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions(
        onDone = {
            onKeyboardDone()
        }
    ),
    maxLines: Int = 1,
    allowedChars: Regex? = AllowedChars.ALL,
    imeAction: ImeAction = ImeAction.Done,
    isValid: Boolean = false,
    isTextCounterShown: Boolean = false,
    hint: Any? = null,
    hintVisibleOnError: Boolean = false,
    leadingIcon: Any? = null,
    trailingIcon: Any? = null,
    showClearTextButton: Boolean = false,
    isLoading: Boolean = false,
    isBigTextField: Boolean = false,
    enabled: Boolean = true,
    colors: TextFieldColors = kwikTextFieldColors(isEditable),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    suggestionsModifier: Modifier = Modifier,
    onSuggestionSelected: (String) -> Unit = {},
    suggestions: List<String> = listOf(),
    suggestionsContainerColor: Color = MaterialTheme.colorScheme.surface,
    delay: Boolean = false,
    delayDuration: Long = 500L
) {
    // Determine the autofill content type based on keyboard type
    val contentTypeValue = when(keyboardType) {
        KeyboardType.Password -> ContentType.Password
        KeyboardType.Email -> ContentType.EmailAddress
        KeyboardType.Phone -> ContentType.PhoneNumber
        else -> null
    }

    var suggestionsVisible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    var filteredSuggestions by remember { mutableStateOf(suggestions.take(10)) }
    var textFieldPosition by remember { mutableStateOf<Offset?>(null) }
    var textFieldSize by remember { mutableStateOf<IntSize?>(null) }
    var lastInputType by remember { mutableStateOf(LastInputType.TYPING) }

    fun updateSuggestions(suggestion: String? = null){
        filteredSuggestions = filteredSuggestions.filter { it.lowercase() != (suggestion ?: value.text).lowercase() }
        lastInputType = LastInputType.SUGGESTION
        suggestionsVisible = filteredSuggestions.isNotEmpty()
    }

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .alpha(alpha = if(enabled) 1f else 0.5f)
            .then(modifier)
    ) {
        TextField(
            value = value.value,
            onValueChange = {
                if(!enabled) return@TextField
                if(it.text.length <= maxLength){
                    debounceJob?.cancel()
                    debounceJob = coroutineScope.launch {
                        if(delay && delayDuration >= 1L) {
                            delay(delayDuration)
                        }

                        // filter suggestions based on the query
                        filteredSuggestions = suggestions.filter { suggestion ->
                            suggestion.contains(it.text, ignoreCase = true)
                        }

                        if(lastInputType == LastInputType.TYPING){
                            updateSuggestions()
                        } else {
                            lastInputType = LastInputType.TYPING
                        }

                        if(allowedChars != null) {
                            if (allowedChars.matches(it.text)) {
                                onValueChange(it)
                            }
                        } else onValueChange(it)
                    }
                }
            },
            isError = isError,
            enabled = enabled && isEditable,
            label = if(!label.isNullOrBlank()){
                {
                    KwikText.LabelMedium(
                        modifier = Modifier.fillMaxWidth(),
                        text = label,
                        color = Color.Gray
                    )
                }
            } else null,
            placeholder = {
                KwikText.TitleMedium(
                    text = placeholder,
                    color = if(isError) MaterialTheme.colorScheme.error else Color.Gray,
                    textAlign = TextAlign.Start
                )
            },
            textStyle = textStyle,
            visualTransformation = if(visualTransformation is PasswordVisualTransformation) {
                if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(if(isBigTextField) 150.dp else 45.dp)
                .alpha(if (enabled) 1.0f else 0.5f)
                .then(
                    if (contentTypeValue != null) {
                        Modifier.semantics {
                            contentType = contentTypeValue
                        }
                    } else {
                        Modifier
                    }
                )
                .onFocusChanged { focusState ->
                    suggestionsVisible = focusState.isFocused
                    if(focusState.isFocused){
                        updateSuggestions()
                    }
                    onFocusChanged(focusState.isFocused)
                }
                .onGloballyPositioned { layoutCoordinates ->
                    textFieldPosition = layoutCoordinates.positionInParent()
                    textFieldSize = layoutCoordinates.size
                },
            singleLine = singleLine && !isBigTextField,
            maxLines = if(isBigTextField) 8 else maxLines,
            shape = shape,
            leadingIcon = if(leadingIcon != null){
                {
                    Row(modifier = Modifier
                        .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(leadingIcon is Int){
                            Icon(
                                painter = painterResource(id = leadingIcon),
                                tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        } else if(leadingIcon is ImageVector) {
                            Icon(
                                imageVector = leadingIcon,
                                tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }
            } else {
                null
            },
            trailingIcon = {
                Row(modifier = Modifier
                    .padding(end = 12.dp, start = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(trailingIcon is Int){
                        Icon(
                            painter = painterResource(id = trailingIcon),
                            tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    onActionClick()
                                }
                        )
                    } else if(trailingIcon is ImageVector) {
                        Icon(
                            imageVector = trailingIcon,
                            tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    onActionClick()
                                }
                        )
                    }

                    /*
                    if(visualTransformation is PasswordVisualTransformation) {
                        PasswordToggle(passwordVisible) {
                            passwordVisible = !passwordVisible
                        }
                    }
                    */
                    if(showClearTextButton && value.value.text.isNotEmpty()){
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clickable {
                                    onValueChange(TextFieldValue(""))
                                    filteredSuggestions = suggestions.take(10)
                                    suggestionsVisible = true
                                },
                            contentDescription = "Clear text",
                            tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                        )
                    }
                    if(isLoading){
                        KwikCircularLoading(
                            modifier = Modifier.size(30.dp),
                            color = if(isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                    if(isValid){
                        Icon(
                            modifier = Modifier.size(20.dp),
                            contentDescription = null,
                            imageVector = Icons.Filled.CheckCircle,
                            tint = KwikColorSuccess
                        )
                    }
                    if(isError) {
                        Icon(
                            imageVector = Icons.Filled.Info, "field error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            colors = colors,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction,
                keyboardType = keyboardType,
            ),
            keyboardActions = keyboardActions,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (hint != null && (hintVisibleOnError && isError || !hintVisibleOnError)) {
                KwikText.LabelMedium(
                    text = hint,
                    color = KwikColorHint,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if(isError){
                    KwikText.LabelMedium(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .align(Alignment.BottomStart)
                    )
                }
                if(isTextCounterShown || isBigTextField) {
                    KwikText.LabelMedium(
                        text = "${value.value.text.length}/$maxLength",
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
        if(filteredSuggestions.isNotEmpty() && textFieldPosition != null){
            Popup(
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = {
                    suggestionsVisible = false
                },
                offset = IntOffset(
                    x = textFieldPosition!!.x.toInt(),
                    y = (textFieldPosition!!.y + (textFieldSize!!.height * 1.6)).toInt()
                )
            ) {
                AnimatedVisibility(
                    visible = suggestionsVisible,
                    enter = fadeIn() + slideInVertically { -it },
                    exit = fadeOut() + slideOutVertically { -it }
                ) {
                    Column(
                        modifier = suggestionsModifier
                            .width(textFieldSize!!.width.dp)
                            .background(
                                color = suggestionsContainerColor,
                                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            filteredSuggestions.forEach { suggestion ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .padding(horizontal = 4.dp)
                                        .clickable {
                                            lastInputType = LastInputType.SUGGESTION
                                            onValueChange(value.value.copy(text = suggestion, selection = TextRange(suggestion.length)))
                                            onSuggestionSelected(suggestion)
                                            updateSuggestions(suggestion)
                                            suggestionsVisible = false
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    KwikText.BodyMedium(
                                        text = suggestion
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun kwikFilledColorResolver(): Color {
    return if(isSystemInDarkTheme()) KwikColorFilledTextFieldFocusedDarkMode else KwikColorFilledTextFieldFocused
}

@Composable
fun kwikTextFieldColors(
    isEditable: Boolean = true
): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedTextColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = kwikFilledColorResolver(),
        focusedLabelColor = Color.Gray,
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        unfocusedContainerColor = if(isSystemInDarkTheme()) KwikColorFilledTextFieldUnfocusedDarkMode else KwikColorFilledTextFieldUnfocused,
        unfocusedLabelColor = Color.Gray,
        unfocusedPlaceholderColor = Color.Gray,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        disabledBorderColor = Color.Transparent,
        disabledContainerColor = Color.LightGray,
        disabledTextColor = if(isEditable) Color.Unspecified else Color.Gray,
        errorContainerColor = KwikColorFilledTextFieldError,
        errorBorderColor = Color.Transparent,
        errorLabelColor = MaterialTheme.colorScheme.error,
        errorPlaceholderColor = MaterialTheme.colorScheme.error,
        errorTextColor = MaterialTheme.colorScheme.onSurface,
        errorCursorColor = MaterialTheme.colorScheme.error
    )
}

@Composable
@Preview(showBackground = true)
private fun KwikTextFieldPreview() {
    KwikTextField(
        value = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) },
        onValueChange = {},
        label = "Address",
        placeholder = "Enter address",
        keyboardType = KeyboardType.Phone,
        visualTransformation = VisualTransformation.None,
        imeAction = ImeAction.Done,
    )
}

@Composable
@Preview(showBackground = true)
private fun KwikErrorTextFieldPreview() {
    KwikTextField(
        value = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) },
        onValueChange = {},
        visualTransformation = VisualTransformation.None,
        placeholder = "Jack Sparrow",
        isError = true,
        keyboardType = KeyboardType.Phone,
        imeAction = ImeAction.Done,
    )
}
