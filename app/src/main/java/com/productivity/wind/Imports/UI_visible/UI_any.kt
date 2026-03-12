package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.*
import android.app.usage.UsageStatsManager
import android.app.*
import androidx.core.app.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.os.*
import android.content.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import android.net.Uri
import android.widget.Toast
import com.productivity.wind.*
import kotlin.collections.*
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.painterResource
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.productivity.wind.Screens.*
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.rememberTextMeasurer
import android.graphics.drawable.Drawable
import android.content.pm.*
import java.util.*
import com.productivity.wind.R
import com.productivity.wind.Imports.UI_visible.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.*
import java.time.*
import kotlin.concurrent.schedule
import java.io.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.*
import android.text.*
import android.text.style.*
import androidx.compose.ui.viewinterop.*
import android.widget.*
import android.text.method.*
import androidx.compose.ui.unit.*
import com.productivity.wind.Imports.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController



/*
import androidx.annotation.FloatRange
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.internal.FloatProducer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.IndicatorBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.LoadingIndicator
import androidx.compose.material3.tokens.ElevationTokens
import androidx.compose.material3.tokens.MotionSchemeKeyTokens
import androidx.compose.material3.value
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScrollModifierNode
import androidx.compose.ui.layout.layout
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DelegatingNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.requireDensity
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.js.JsName
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlinx.coroutines.launch
*/

@Composable 
fun RuleCard(
    txt: Str,
    ui: ui,
){
   LazyCard(corners = 8){
      Text(txt.size(17).bold())
      ui()
   }
}

@Composable
fun BlackStatusBar() {
    val ui = rememberSystemUiController()

    RunOnce {
        ui.setStatusBarColor(Color.Black, darkIcons = no)
    }
}



@Composable
fun End(mod: Mod = Mod, ui: ui) {
	Row(
		mod.maxW(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.End
	){
		ui()
		move(10)
	}
}

@Composable
fun EmptyBox(
	text: Str = "No Items",
	icon: icon = Icons.Default.Block,
	iconSize: Dp = 70.dp,
	textSize: Int = 18,
	color: Color = Color.Gray,
) {
	Column(
		Mod.maxS(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Icon(icon, iconSize, color = color)
	
		move(8)
		Text(text.size(textSize).color(color))
	}
}

@Composable
fun MenuHeader(
	title: Str = "Wind",
	iconRes: Int = myAppRes,
	iconSize: Dp = 60.dp,
	iconTint: Color = Color(0xFFFFD700),
	titleSize: Int = 28,
	topPadding: Dp = 8.dp,
	bottomPadding: Dp = 20.dp,
	StartPaddingRemove: Int = 40,
) {
	val safeStartPadding = max(0.dp, (AppW+60.dp) / 4 - StartPaddingRemove.dp)

	Column(
		Modifier.space(start = safeStartPadding),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		move(h = topPadding)

		Icon(
			iconRes,
			color = iconTint,
			size = iconSize,
		)
		move(4)
		Text(title.size(titleSize))
        move(h = bottomPadding)
	}
}

@Composable
fun ProgressIcon(
	icon: Drawable?,              
	progress: Float,
) {
	val ringColor = ProgressColor(progress)

	Ring(
		color = ringColor,
		progress = progress,
		ContentPadding = -3,
	) {
		Box(contentAlignment = Alignment.Center) {
			LazyImage(icon)

			Canvas(
				modifier = Mod
					.matchParentSize()
					.space(4)
			) {
				drawArc(
					color = Color(0xFF171717),   // ~10% darker overlay
					startAngle = 0f,
					sweepAngle = 360f,
					useCenter = false,
					style = Stroke(width = 2.dp.toPx())
				)
			}
		}
	}
}

@Composable
fun Ring(
	color: Color,
	strokeWidth: Int = 3,
	progress: Float = 1f,
	ContentPadding: Int = 1,
	strokeCap: StrokeCap = StrokeCap.Butt,
	content: @Composable BoxScope.() -> Unit
) {
	Box(
		contentAlignment = Alignment.Center,
	) {
		Canvas(modifier = Mod.matchParentSize()) {
			val stroke = Stroke(width = strokeWidth.dp.toPx(), cap = strokeCap)
			val radius = size.minDimension / 2
			val topLeft = Offset(center.x - radius, center.y - radius)

			drawCircle(             
				color = Color.Black.copy(alpha = 0.1f), // faint dark circle
				radius = radius,                         // radius of the circle
			)
			drawArc(
				color = color,
				startAngle = -90f,
				sweepAngle = -360f * progress,
				useCenter = no,
				topLeft = topLeft,
				size = Size(radius * 2, radius * 2),
				style = stroke,
			)
		}
		Box(Mod.space(strokeWidth+ContentPadding)) {
			content()
		}
	}
}






































/*
@Composable
fun PullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    contentAlignment: Alignment = Alignment.TopStart,
    indicator: @Composable BoxScope.() -> Unit = {
        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            state = state,
        )
    },
    enabled: Boolean = true,
    threshold: Dp = PullToRefreshDefaults.PositionalThreshold,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier.pullToRefresh(
            state = state,
            isRefreshing = isRefreshing,
            enabled = enabled,
            threshold = threshold,
            onRefresh = onRefresh,
        ),
        contentAlignment = contentAlignment,
    ) {
        content()
        indicator()
    }
}

@Deprecated(
    message = "Use the PullToRefreshBox that takes enabled and threshold parameters",
    level = DeprecationLevel.HIDDEN,
)
@Composable
fun PullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    contentAlignment: Alignment = Alignment.TopStart,
    indicator: @Composable BoxScope.() -> Unit = {
        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            state = state,
        )
    },
    content: @Composable BoxScope.() -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        contentAlignment = contentAlignment,
        indicator = indicator,
        enabled = true,
        threshold = PullToRefreshDefaults.PositionalThreshold,
        content = content,
    )
}

fun Modifier.pullToRefresh(
    isRefreshing: Boolean,
    state: PullToRefreshState,
    enabled: Boolean = true,
    threshold: Dp = PullToRefreshDefaults.PositionalThreshold,
    onRefresh: () -> Unit,
): Modifier =
    this then
        PullToRefreshElement(
            state = state,
            isRefreshing = isRefreshing,
            enabled = enabled,
            onRefresh = onRefresh,
            threshold = threshold,
        )

internal class PullToRefreshElement(
    val isRefreshing: Boolean,
    val onRefresh: () -> Unit,
    val enabled: Boolean,
    val state: PullToRefreshState,
    val threshold: Dp,
) : ModifierNodeElement<PullToRefreshModifierNode>() {
    override fun create() =
        PullToRefreshModifierNode(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            enabled = enabled,
            state = state,
            threshold = threshold,
        )

    override fun update(node: PullToRefreshModifierNode) {
        node.onRefresh = onRefresh
        node.enabled = enabled
        node.state = state
        node.threshold = threshold
        if (node.isRefreshing != isRefreshing) {
            node.isRefreshing = isRefreshing
            node.update()
        }
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "PullToRefreshModifierNode"
        properties["isRefreshing"] = isRefreshing
        properties["onRefresh"] = onRefresh
        properties["enabled"] = enabled
        properties["state"] = state
        properties["threshold"] = threshold
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PullToRefreshElement) return false

        if (isRefreshing != other.isRefreshing) return false
        if (enabled != other.enabled) return false
        if (onRefresh !== other.onRefresh) return false
        if (state != other.state) return false
        if (threshold != other.threshold) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isRefreshing.hashCode()
        result = 31 * result + enabled.hashCode()
        result = 31 * result + onRefresh.hashCode()
        result = 31 * result + state.hashCode()
        result = 31 * result + threshold.hashCode()
        return result
    }
}

internal class PullToRefreshModifierNode(
    var isRefreshing: Boolean,
    var onRefresh: () -> Unit,
    var enabled: Boolean,
    var state: PullToRefreshState,
    var threshold: Dp,
) : DelegatingNode(), NestedScrollConnection {

    override val shouldAutoInvalidate: Boolean
        get() = false

    private var nestedScrollNode: DelegatableNode =
        nestedScrollModifierNode(connection = this, dispatcher = null)

    private var verticalOffset by mutableFloatStateOf(0f)
    private var distancePulled by mutableFloatStateOf(0f)

    private val adjustedDistancePulled: Float
        get() = distancePulled * DragMultiplier

    private val thresholdPx
        get() = with(requireDensity()) { threshold.roundToPx() }

    private val progress
        get() = adjustedDistancePulled / thresholdPx

    override fun onAttach() {
        delegate(nestedScrollNode)
        coroutineScope.launch { state.snapTo(if (isRefreshing) 1f else 0f) }
        verticalOffset = if (isRefreshing) thresholdPx.toFloat() else 0f
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset =
        when {
            state.isAnimating -> Offset.Zero
            !enabled -> Offset.Zero
			
            source == NestedScrollSource.UserInput && available.y < 0 -> {
                consumeAvailableOffset(available)
            }
            else -> Offset.Zero
        }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
    ): Offset =
        when {
            state.isAnimating -> Offset.Zero
            !enabled -> Offset.Zero
            source == NestedScrollSource.UserInput -> {
                val newOffset = consumeAvailableOffset(available)
                coroutineScope.launch {
                    if (!state.isAnimating) {
                        state.snapTo(verticalOffset / thresholdPx)
                    }
                }

                newOffset
            }
            else -> Offset.Zero
        }

    override suspend fun onPreFling(available: Velocity): Velocity {
        return Velocity(0f, onRelease(available.y))
    }

    fun update() {
        coroutineScope.launch {
            if (!isRefreshing) {
                animateToHidden()
            } else {
                animateToThreshold()
            }
        }
    }

    private fun consumeAvailableOffset(available: Offset): Offset {
        val y =
            if (isRefreshing) 0f
            else {
                val newOffset = (distancePulled + available.y).coerceAtLeast(0f)
                val dragConsumed = newOffset - distancePulled
                distancePulled = newOffset
                verticalOffset = calculateVerticalOffset()
                dragConsumed
            }
        return Offset(0f, y)
    }

    private suspend fun onRelease(velocity: Float): Float {
        if (isRefreshing) return 0f // Already refreshing, do nothing
        // Trigger refresh
        if (adjustedDistancePulled > thresholdPx) {
            onRefresh()
        }

        val consumed =
            when {
                distancePulled == 0f -> 0f
                velocity < 0f -> 0f
                else -> velocity
            }

        animateToHidden()

        distancePulled = 0f

        return consumed
    }

    private fun calculateVerticalOffset(): Float =
        when {
            // If drag hasn't gone past the threshold, the position is the adjustedDistancePulled.
            adjustedDistancePulled <= thresholdPx -> adjustedDistancePulled
            else -> {
                // How far beyond the threshold pull has gone, as a percentage of the threshold.
                val overshootPercent = abs(progress) - 1.0f
                // Limit the overshoot to 200%. Linear between 0 and 200.
                val linearTension = overshootPercent.coerceIn(0f, 2f)
                // Non-linear tension. Increases with linearTension, but at a decreasing rate.
                val tensionPercent = linearTension - linearTension.pow(2) / 4
                // The additional offset beyond the threshold.
                val extraOffset = thresholdPx * tensionPercent
                thresholdPx + extraOffset
            }
        }

    private suspend fun animateToThreshold() {
        try {
            state.animateToThreshold()
        } finally {
            if (isAttached) {
                distancePulled = thresholdPx.toFloat()
                verticalOffset = thresholdPx.toFloat()
            }
        }
    }

    private suspend fun animateToHidden() {
        try {
            state.animateToHidden()
        } finally {
            distancePulled = 0f
            verticalOffset = 0f
        }
    }
}

object PullToRefreshDefaults {
    @Deprecated("Use indicatorShape instead", ReplaceWith("indicatorShape"))
    @ExperimentalMaterial3Api
    val shape: Shape = CircleShape

    val indicatorShape: Shape = CircleShape

    @Deprecated("Use indicatorContainerColor instead", ReplaceWith("indicatorContainerColor"))
    @ExperimentalMaterial3Api
    val containerColor: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceContainerHigh

    val indicatorContainerColor: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceContainerHigh

    @ExperimentalMaterial3ExpressiveApi
    val loadingIndicatorContainerColor: Color
        @Composable get() = LoadingIndicatorDefaults.containedContainerColor

    val indicatorColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    @ExperimentalMaterial3ExpressiveApi
    val loadingIndicatorColor: Color
        @Composable get() = LoadingIndicatorDefaults.containedIndicatorColor

    val PositionalThreshold = 80.dp
    val IndicatorMaxDistance = PositionalThreshold

    val Elevation = ElevationTokens.Level2

    val LoadingIndicatorElevation = ElevationTokens.Level0

    @Composable
    fun IndicatorBox(
        state: PullToRefreshState,
        isRefreshing: Boolean,
        modifier: Modifier = Modifier,
        maxDistance: Dp = IndicatorMaxDistance,
        shape: Shape = indicatorShape,
        containerColor: Color = Color.Unspecified,
        elevation: Dp = Elevation,
        content: @Composable BoxScope.() -> Unit,
    ) {
        Box(
            modifier =
                modifier
                    .size(SpinnerContainerSize)
                    .drawWithContent {
                        clipRect(
                            top = 0f,
                            left = -Float.MAX_VALUE,
                            right = Float.MAX_VALUE,
                            bottom = Float.MAX_VALUE,
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    }
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            placeable.placeWithLayer(
                                0,
                                0,
                                layerBlock = {
                                    val showElevation = state.distanceFraction > 0f || isRefreshing
                                    translationY =
                                        state.distanceFraction * maxDistance.roundToPx() -
                                            size.height
                                    shadowElevation = if (showElevation) elevation.toPx() else 0f
                                    this.shape = shape
                                    clip = true
                                },
                            )
                        }
                    }
                    .background(color = containerColor, shape = shape),
            contentAlignment = Alignment.Center,
            content = content,
        )
    }

    @Composable
    fun Indicator(
        state: PullToRefreshState,
        isRefreshing: Boolean,
        modifier: Modifier = Modifier,
        containerColor: Color = this.indicatorContainerColor,
        color: Color = this.indicatorColor,
        maxDistance: Dp = IndicatorMaxDistance,
    ) {
        IndicatorBox(
            modifier = modifier,
            state = state,
            isRefreshing = isRefreshing,
            containerColor = containerColor,
            maxDistance = maxDistance,
        ) {
            Crossfade(
                targetState = isRefreshing,
                animationSpec = MotionSchemeKeyTokens.DefaultEffects.value(),
            ) { refreshing ->
                if (refreshing) {
                    CircularProgressIndicator(
                        strokeWidth = StrokeWidth,
                        color = color,
                        modifier = Modifier.size(SpinnerSize),
                    )
                } else {
                    CircularArrowProgressIndicator(
                        progress = { state.distanceFraction },
                        color = color,
                    )
                }
            }
        }
    }

    @ExperimentalMaterial3ExpressiveApi
    @Composable
    fun LoadingIndicator(
        state: PullToRefreshState,
        isRefreshing: Boolean,
        modifier: Modifier = Modifier,
        containerColor: Color = this.loadingIndicatorContainerColor,
        color: Color = this.loadingIndicatorColor,
        elevation: Dp = LoadingIndicatorElevation,
        maxDistance: Dp = IndicatorMaxDistance,
    ) {
        IndicatorBox(
            modifier = modifier.size(width = LoaderIndicatorWidth, height = LoaderIndicatorHeight),
            state = state,
            isRefreshing = isRefreshing,
            containerColor = containerColor,
            elevation = elevation,
            maxDistance = maxDistance,
        ) {
            Crossfade(
                targetState = isRefreshing,
                animationSpec = MotionSchemeKeyTokens.DefaultEffects.value(),
            ) { refreshing ->
                if (refreshing) {
                    ContainedLoadingIndicator(
                        modifier =
                            Modifier.requiredSize(
                                width = LoaderIndicatorWidth,
                                height = LoaderIndicatorHeight,
                            ),
                        containerColor = containerColor,
                        indicatorColor = color,
                    )
                } else {
                    ContainedLoadingIndicator(
                        progress = { state.distanceFraction },
                        modifier =
                            Mod.requiredSize(
                                    width = LoaderIndicatorWidth,
                                    height = LoaderIndicatorHeight,
                                )
                                .drawWithContent {
                                    val progress = state.distanceFraction
                                    if (progress > 1f) {
                                        rotate(-(progress - 1) * 180) {
                                            this@drawWithContent.drawContent()
                                        }
                                    } else {
                                        drawContent()
                                    }
                                },
                        containerColor = containerColor,
                        indicatorColor = color,
                    )
                }
            }
        }
    }
}


@Stable
interface PullToRefreshState {
    @get:FloatRange(from = 0.0) val distanceFraction: Float
    val isAnimating: Boolean
    suspend fun animateToThreshold()
    suspend fun animateToHidden()
    suspend fun snapTo(@FloatRange(from = 0.0) targetValue: Float)
}

@Composable
fun rememberPullToRefreshState(): PullToRefreshState {
    return rememberSaveable(saver = PullToRefreshStateImpl.Saver) { PullToRefreshStateImpl() }
}


@JsName("funPullToRefreshState")
fun PullToRefreshState(): PullToRefreshState = PullToRefreshStateImpl()

internal class PullToRefreshStateImpl
private constructor(private val anim: Animatable<Float, AnimationVector1D>) : PullToRefreshState {
    constructor() : this(Animatable(0f, Float.VectorConverter))

    override val distanceFraction
        get() = anim.value

    override val isAnimating: Boolean
        get() = anim.isRunning

    override suspend fun animateToThreshold() {
        anim.animateTo(1f)
    }

    override suspend fun animateToHidden() {
        anim.animateTo(0f)
    }

    override suspend fun snapTo(targetValue: Float) {
        anim.snapTo(targetValue)
    }

    companion object {
        val Saver =
            Saver<PullToRefreshStateImpl, Float>(
                save = { it.anim.value },
                restore = { PullToRefreshStateImpl(Animatable(it, Float.VectorConverter)) },
            )
    }
}

/** The default pull indicator for [PullToRefreshBox] */
@Composable
private fun CircularArrowProgressIndicator(progress: FloatProducer, color: Color) {
    val path = remember { Path().apply { fillType = PathFillType.EvenOdd } }
    val targetAlpha by remember { derivedStateOf { if (progress() >= 1f) MaxAlpha else MinAlpha } }
    val alphaState =
        animateFloatAsState(
            targetValue = targetAlpha,
            animationSpec = MotionSchemeKeyTokens.DefaultEffects.value(),
        )

    Canvas(
        modifier =
            Modifier.clearAndSetSemantics {
                    if (progress() > 0f) {
                        progressBarRangeInfo = ProgressBarRangeInfo(progress(), 0f..1f, 0)
                    }
                }
                .size(SpinnerSize)
    ) {
        val values = ArrowValues(progress())
        val alpha = alphaState.value
        rotate(degrees = values.rotation) {
            val arcRadius = ArcRadius.toPx() + StrokeWidth.toPx() / 2f
            val arcBounds = Rect(center = size.center, radius = arcRadius)
            drawCircularIndicator(color, alpha, values, arcBounds, StrokeWidth)
            drawArrow(path, arcBounds, color, alpha, values, StrokeWidth)
        }
    }
}

private fun DrawScope.drawCircularIndicator(
    color: Color,
    alpha: Float,
    values: ArrowValues,
    arcBounds: Rect,
    strokeWidth: Dp,
) {
    drawArc(
        color = color,
        alpha = alpha,
        startAngle = values.startAngle,
        sweepAngle = values.endAngle - values.startAngle,
        useCenter = false,
        topLeft = arcBounds.topLeft,
        size = arcBounds.size,
        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt),
    )
}

@Immutable
private class ArrowValues(
    val rotation: Float,
    val startAngle: Float,
    val endAngle: Float,
    val scale: Float,
)

private fun ArrowValues(progress: Float): ArrowValues {
    // Discard first 40% of progress. Scale remaining progress to full range between 0 and 100%.
    val adjustedPercent = max(min(1f, progress) - 0.4f, 0f) * 5 / 3
    // How far beyond the threshold pull has gone, as a percentage of the threshold.
    val overshootPercent = abs(progress) - 1.0f
    // Limit the overshoot to 200%. Linear between 0 and 200.
    val linearTension = overshootPercent.coerceIn(0f, 2f)
    // Non-linear tension. Increases with linearTension, but at a decreasing rate.
    val tensionPercent = linearTension - linearTension.pow(2) / 4

    // Calculations based on SwipeRefreshLayout specification.
    val endTrim = adjustedPercent * MaxProgressArc
    val rotation = (-0.25f + 0.4f * adjustedPercent + tensionPercent) * 0.5f
    val startAngle = rotation * 360
    val endAngle = (rotation + endTrim) * 360
    val scale = min(1f, adjustedPercent)

    return ArrowValues(rotation, startAngle, endAngle, scale)
}

private fun DrawScope.drawArrow(
    arrow: Path,
    bounds: Rect,
    color: Color,
    alpha: Float,
    values: ArrowValues,
    strokeWidth: Dp,
) {
    arrow.reset()
    arrow.moveTo(0f, 0f)
	
    arrow.lineTo(x = ArrowWidth.toPx() * values.scale / 2, y = ArrowHeight.toPx() * values.scale)
    arrow.lineTo(x = ArrowWidth.toPx() * values.scale, y = 0f) // Line to right corner

    val radius = min(bounds.width, bounds.height) / 2f
    val inset = ArrowWidth.toPx() * values.scale / 2f
    arrow.translate(
        Offset(x = radius + bounds.center.x - inset, y = bounds.center.y - strokeWidth.toPx())
    )
    rotate(degrees = values.endAngle - strokeWidth.toPx()) {
        drawPath(path = arrow, color = color, alpha = alpha, style = Stroke(strokeWidth.toPx()))
    }
}

private const val MaxProgressArc = 0.8f

/** The default stroke width for [Indicator] */
private val StrokeWidth = 2.5.dp
private val ArcRadius = 5.5.dp
internal val SpinnerSize = 16.dp // (ArcRadius + PullRefreshIndicatorDefaults.StrokeWidth).times(2)
internal val SpinnerContainerSize = 40.dp
private val ArrowWidth = 10.dp
private val ArrowHeight = 5.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
internal val LoaderIndicatorHeight = LoadingIndicatorDefaults.ContainerHeight
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
internal val LoaderIndicatorWidth = LoadingIndicatorDefaults.ContainerWidth

// Values taken from SwipeRefreshLayout
private const val MinAlpha = 0.3f
private const val MaxAlpha = 1f

private const val DragMultiplier = 0.5f




*/







