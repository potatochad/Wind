@file:OptIn(ExperimentalFoundationApi::class)

package com.productivity.wind.Imports

import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.Imports.*
import com.productivity.wind.*
import kotlin.random.*
import androidx.compose.ui.*
import android.webkit.*
import androidx.compose.ui.viewinterop.*
import org.mozilla.geckoview.*
import android.content.*
import androidx.compose.runtime.snapshots.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import kotlin.math.*
import androidx.compose.ui.unit.*

// needs fixing
@Composable
fun LazySearch(
    searchQuery: Str,
    onChange: (Str) -> Unit
) {
    Box(
		modifier = Modifier
			.fillMaxWidth()
			.border(
				width = 1.dp,
				color = Color.Black,
				shape = RoundedCornerShape(6.dp)
			)
			.background(Color.Transparent, RoundedCornerShape(6.dp))
			.padding(8.dp)
	) {
        LazzyRow{
            LazyIcon(
                icon = Icons.Default.Search,
				look = LazyIconLook( Color = Color.Black),
            )
            UI.move(w=8)
            UI.Input(searchQuery) { 
                onChange(it)
            }
        }
    }
}







fun Modifier.clickOrHold(
    hold: Bool = false,
	animation: Bool = true,
    action: Do,
): Modifier {
    return if (hold) {
        pointerInput(Unit) {
            detectTapGestures(onLongPress = { action() })
        }
    } else {
        clickable(
            indication = if (animation) null else null,
            interactionSource = MutableInteractionSource()
        ) {
            action()
        }
	}
}
fun Modifier.location(
    onBoundsChanged: (Rect) -> Unit
): Modifier = this.then(
    Modifier.onGloballyPositioned { coordinates ->
        val bounds = coordinates.boundsInWindow()
        onBoundsChanged(bounds)
    }
)





@Composable
fun LazyPopup(
    show: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    // Local state to control if Popup is alive
    var popupVisible by remember { mutableStateOf(false) }

    // Sync popupVisible with show.value
    LaunchedEffect(show.value) {
        if (show.value) {
            popupVisible = true  // Show immediately
        } else {
            delay(200)           // Wait for fade-out duration
            popupVisible = false // Then remove Popup
        }
    }

    if (popupVisible) {
        Popup(
            properties = PopupProperties(focusable = true)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickOrHold(animation = false) { show.value = false },
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = show.value,
                    enter = fadeIn(animationSpec = tween(200)),
                    exit = fadeOut(animationSpec = tween(200))
                ) {
                    content()
                }
            }
        }
    }
}



@Composable
fun LazyInfo(
    info: String = "",
    hold: Boolean = false,
    content: @Composable () -> Unit
) {
    var show = remember { mutableStateOf(false) }
    var triggerBounds by remember { mutableStateOf<Rect?>(null) }

    Box(
        modifier = Modifier
            .clickOrHold(hold) { show.value = true }
            .location { rect -> triggerBounds = rect } // Capture trigger position
    ) {
        content()
    }

    LazyPopup(show = show) {
        triggerBounds?.let { bounds ->
            Box(
                modifier = Modifier
                    .offset { IntOffset(bounds.left.toInt(), bounds.bottom.toInt()) }
                    .wrapContentSize()
                    .border(
                        width = 2.dp,
                        color = Color.Black.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                LazyCard(
                    corners = 8,
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(info)
                }
            }
        }
    }
}













@Composable
fun LazyImage(
    source: Any?,
    modifier: Modifier = Modifier
        .size(34.dp)
		.padding(5.dp)
) {
    val contentDescription = "boring"
    when (source) {
        is Painter -> Image(painter = source, contentDescription = contentDescription, modifier = modifier)
        is Drawable -> Image(painter = rememberDrawablePainter(source), contentDescription = contentDescription, modifier = modifier)
        is Bitmap -> Image(painter = BitmapPainter(source.asImageBitmap()), contentDescription = contentDescription, modifier = modifier)
        is ImageBitmap -> Image(painter = BitmapPainter(source), contentDescription = contentDescription, modifier = modifier)
        else -> { /* do nothing if null/unsupported */ }
    }
}




data class LazzyListStyle(
    var height: Dp = 200.dp,
    var delayMs: Long = 40L,
    var chunkSize: Int = 1,
    var IntFirst: Int = 0,
	var wrapContent: Bool = false,
)

data class LazyLoaderState<T>(
    val scrollState: ScrollState,
    val appearedItems: SnapshotStateList<T>,
)

@Composable
fun <T> rememberLazyLoaderState(
    items: List<T>,
    style: LazzyListStyle,         // your config: IntFirst, chunkSize, delayMs
    loadAll: Bool = false
): LazyLoaderState<T> {
    val scrollState = rememberScrollState()
    val appearedItems = r { mutableStateListOf<T>() }

    // Run once when items or loadAll change
    LaunchedEffect(items, loadAll) {
        appearedItems.clear()

        if (loadAll) {
            appearedItems.addAll(items)
            return@LaunchedEffect
        }

        // initial batch
        val realInitialCount = style.IntFirst.coerceAtMost(items.size)
        if (realInitialCount > 0) {
            appearedItems.addAll(items.subList(0, realInitialCount))
        }

        // gradual load remainder
        var currentIndex = realInitialCount
        while (currentIndex < items.size) {
            val nextIndex = (currentIndex + style.chunkSize).coerceAtMost(items.size)
            appearedItems.addAll(items.subList(currentIndex, nextIndex))
            currentIndex = nextIndex
            if (currentIndex >= items.size) break
            delay(style.delayMs)
        }
    }

    return LazyLoaderState(scrollState, appearedItems)
}

@Composable
fun <T> LazzyList(
    items: List<T>,
    loadAll: Bool = false,
    style: LazzyListStyle = LazzyListStyle(),
    key: ((T) -> Any)? = null,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
) {
    val loader = rememberLazyLoaderState(items, style, loadAll)

    // Apply style.height only if caller did not override height manually
    val finalModifier = modifier
		.then(
			if (style.wrapContent) Modifier else Modifier.height(style.height)
		)
		.fillMaxWidth()
		.verticalScroll(loader.scrollState)

    Column(modifier = finalModifier) {
        loader.appearedItems.forEach { item ->
            if (key != null) {
                androidx.compose.runtime.key(key(item)) {
                    itemContent(item)
                }
            } else {
                itemContent(item)
            }
        }
    }
}







@Composable
fun LazySwitch(isOn: Boolean, onToggle: (Boolean) -> Unit) {
    Switch(
      checked = isOn,
      onCheckedChange = onToggle,
      modifier = Modifier.size(34.dp), // default ~39dp → minus 5dp
      colors = SwitchDefaults.colors(
          checkedThumbColor = Color(0xFFFFD700),
          uncheckedThumbColor = Color.LightGray,
          checkedTrackColor = Color(0xFFFFF8DC),
          uncheckedTrackColor = Color(0xFFE0E0E0)
      )
    )
}




  
  
@Composable
fun LazyLine(
    show: Bool = true,
    color: Color = Color(0xFFFFD700),
    thickness: Dp = 1.dp,
    MoveY: Int = 0,
    paddingHorizontal: Dp = 0.dp,
    width: Dp = Dp.Unspecified,
  ) {
    if (!show) return

    Divider(
        color = color,
        thickness = thickness,
        modifier = Modifier
            .offset(y = MoveY.dp)
            .padding(horizontal = paddingHorizontal)
            .then(if (width != Dp.Unspecified) Modifier.width(width) else Modifier)
    )
}
  
@Composable
fun LazzyRow(
    modifier: Modifier = Modifier,
    padding: Int = 0,
    center: Bool = false, // Kotlin uses 'Boolean', not 'Bool'
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding.dp),
        horizontalArrangement = if (center) Arrangement.Center else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

      
@Composable 
fun LazyRuleCard(
	Header: Str,
	content: Content,
){
	LazyCard(corners = 8){
		LazzyRow {
			Text(
				text = Header,
				fontSize = 17.sp,        
				color = Color.White,        
				fontWeight = FontWeight.Bold,
			)
		}
		content()
	}

}


@Composable
fun LazyCard(
    inputColor: Color = Color(0xFF1A1A1A),
    innerPadding: Int = 16,
    corners: Int = 16,
	modifier: Modifier = Modifier
	     .padding(horizontal = 8.dp, vertical = 10.dp)
         .fillMaxWidth(),
    content: Content,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corners.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = inputColor)
    ) {
        Column(
            modifier = Modifier.padding(
                start = innerPadding.dp,
                end = innerPadding.dp,
                bottom = innerPadding.dp,
                top = (innerPadding - 2).dp // slight top adjustment
            )
        ) {
            content()
        }
    }
}



data class LazyIconLook(
    val Color: Color = Color(0xFFFFD700) // gild
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyIcon(
    onClick: () -> Unit = {},
    icon: ImageVector? = null,
    BigIcon: ImageVector? = null,
    BigIconColor: Color? = null,
    SquareIcon: Bool = false,
    BigIconSize: Int = 30,
    OuterPadding: Int = 5,          // outside space
    ButtonSize: Int = 40,           // actual button box (default M3 ~48)
    modifier: Modifier = Modifier,
	look: LazyIconLook = LazyIconLook(),
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        IconButton(
            onClick = onClick,
            modifier = modifier
                .padding(OuterPadding.dp) // OUTER padding
                .size(ButtonSize.dp)      // controls inner room around icon
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = look.Color,
                    modifier = Modifier.size(24.dp)
                )
            }

            if (BigIcon != null && BigIconColor != null) {
                val shape = if (SquareIcon) RoundedCornerShape(6.dp) else CircleShape
                val innerSize = if (SquareIcon) 24.dp else 20.dp
                Box(
                    modifier = Modifier
                        .size(BigIconSize.dp)
                        .clip(shape)
                        .background(BigIconColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = BigIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(innerSize)
                    )
                }
            }
        }
    }
}



@Stable
@Composable
fun LazyMore(
    modifier: Modifier = Modifier,
    title: Str = "Show more",
    initiallyExpanded: Bool = false,
    content: Content
) {
    var expanded by r { m(initiallyExpanded) }
    val rotation by animateFloatAsState(targetValue = if (expanded) 90f else 0f)

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = if (expanded) "Show less" else "Show more",
                modifier = Modifier.rotate(rotation),
                tint = Color(0xFFFFD700) // GOLD icon
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                color = Color(0xFFFFD700) // GOLD text
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(
                animationSpec = tween(durationMillis = 400),
                expandFrom = Alignment.Top
            ),
            exit = shrinkVertically(
                animationSpec = tween(durationMillis = 300),
                shrinkTowards = Alignment.Top
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, top = 4.dp) // indent content nicely
            ) {
                content()
            }
        }
    }
}

//endregion


//region SCREENS


@Composable
fun LazyItem(
    title: Str,
    subtitle: Str? = null,
    endContent: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
	
    icon: ImageVector? = null,
    BigIcon: ImageVector? = null,
    BigIconColor: Color? = null,
	
    onClick: (() -> Unit)? = null,

    topPadding: Dp= 7.dp,
    bottomPadding: Dp = 7.dp,
) {
	Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
		    top = topPadding,
		    bottom = bottomPadding,
		    start = 7.dp,
		    end = 7.dp
	    ),
		verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
			modifier = modifier
				.fillMaxWidth()
				.clickable(enabled = onClick != null) { onClick?.invoke() },
			shape = RoundedCornerShape(12.dp),
			colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
			elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
		) {
            Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(5.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				if (icon != null) {
					Icon(
						imageVector = icon,
						contentDescription = null,
						tint = Color(0xFFFFD700),
						modifier = Modifier
							.padding(end = 10.dp)
							.size(24.dp)
					)
				}	    

				if (BigIcon != null && BigIconColor != null) {
					Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(BigIconColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = BigIcon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
				}


				Column(modifier = Modifier.weight(1f)) {
					Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
					subtitle?.let {
						Text(text = it, color = Color.Gray, fontSize = 12.sp)
					}
				}
				endContent?.invoke()
			}
		}
	}
}

@Composable
fun LazyHeader(
    titleContent: Content,
    onSearchClick: Do ={},
    onBackClick: Do = {},
    showBack: Bool = true,
    showSearch: Bool = false,
    modifier: Modifier = Modifier,
	
    showDivider: Bool = true,
	DividerPadding: Bool = true,
	
	Mheight: Int = 100,
) {
    val ui = rememberSystemUiController()
    var DisableTB_Button by r { m(false) }
    LaunchedEffect(Unit) {
        ui.setStatusBarColor(Color.Black, darkIcons = false)
    }

    Column {
        UI.move(h=getStatusBarHeight()/3)

        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Black)
				.padding(vertical = 12.dp)
			.heightIn(max = Mheight.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            /* 🔒 Btn Cooldown
             *
             * ⚠️ Problem:
             * Rapid back clicks (especially after popupBackStack or screen transitions)
             * sometimes cause a full black screen in Compose — likely due to
             * navigation state confusion or overlapping recompositions.
             *
             * ✅ Fix:
             * We temporarily disable this button after it's clicked once,
             * using the `DisableTB_Button` flag, to prevent double-taps.
             *
             * This protects Compose from crashing or losing its state tree.
             */
            if (showBack) {
				UI.move(5)
                LazyIcon(
					onClick = { 
						if (DisableTB_Button) {}
						if (!DisableTB_Button) { 
							DisableTB_Button = true
							onBackClick()
							App.navHost.popBackStack()
						}
					},
					icon = Icons.Default.ArrowBack,
                    )
                }

			// Title content
			Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = if (showBack) 8.dp else 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
				LazzyRow(){
						titleContent()
				}
				
			}
			// Search icon
			if (showSearch) {
				LazyIcon(
					onClick = onSearchClick,
					icon = Icons.Default.Search,
				)
			}
		}

		if (showDivider){
			LazyLine(color = Color.Gray)		
			if (DividerPadding){
				UI.move(10)
			}
		}
    }
}

@Composable
fun ScreenModifier(
    focusManager: FocusManager,
    backgroundColor: Color = Color.Black,
    onClick: (() -> Unit)? = null
): Modifier {
    var modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
        .clipToBounds()

    // Add clickable only if onClick is provided
    if (onClick != null) {
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    }

    return modifier
}


@Composable
fun LazyScreen(
    title: Content,
    onSearchClick: Do = {},
    onBackClick: Do = {},
    showBack: Bool = true,
    showSearch: Bool = false,
    modifier: Modifier = Modifier,
	
    showDivider: Bool = true,
	DividerPadding: Bool = true,
	
	MheaderHeight: Int = 44,
	Scrollable: Bool = false,
    content: Content,
) {
    val focusManager = LocalFocusManager.current
    val baseModifier = ScreenModifier(focusManager) {
		focusManager.clearFocus()
	}



	val header: Content = {
		LazyHeader(
			titleContent = title,
			onSearchClick = onSearchClick,
			onBackClick = onBackClick,
			showBack = showBack,
			showSearch = showSearch,
			showDivider = showDivider,
			DividerPadding = DividerPadding,
			Mheight = MheaderHeight
		)
	}
	
	if (Scrollable) {
		LazyColumn(modifier = baseModifier.then(modifier)) {
			stickyHeader { header() }
			item { Column { content() } }
			item { Spacer(modifier = Modifier.height(bottomSystemHeight())) }
		}
	} else {
		Column(modifier = baseModifier.then(modifier)) {
			header()
			content()
		}
	}
}



//endregion


//region LAZY POPUP

@Composable
fun LazyPopup(
    show: MutableState<Boolean>,
    onDismiss: (() -> Unit)? = { show.value = false },
    title: Str = "Info",
    message: Str,
    content: (@Composable () -> Unit)? = null,
    showCancel: Bool = true,
    showConfirm: Bool = true,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
) {
    if (!show.value) return
	

    AlertDialog(
        onDismissRequest = {
            onDismiss?.invoke()
        },
        title = { Text(title) },
        text = {
			Column{
				content?.invoke() ?: Text(message)
			}
        },
        confirmButton = {
            if (showConfirm) {
				UI.move(15)
                UI.Ctext("OK"){
                    onConfirm?.invoke()
                    show.value = false
                }
            }
        },
        dismissButton = if (showCancel) {
            {
				UI.Ctext("Cancel"){
                    onCancel?.invoke()
                    show.value = false
                }
            }
        } else null
    )
}




@Composable
fun LazyMenu(
    onDismiss: (() -> Unit)? = null,
    content: Content,
) {
    val visible = r { m(false) }
    val internalVisible = r { m(false) }

    // Trigger showing/hiding Popup
    LaunchedEffect(Bar.ShowMenu) {
        if (Bar.ShowMenu) {
            visible.value = true
            delay(16)
            internalVisible.value = true
        } else {
            internalVisible.value = false
            delay(200) // Wait for animation out
            visible.value = false
        }
    }

    if (!visible.value) return

    // Slide offset
    val offsetX by animateDpAsState(
        targetValue = if (internalVisible.value) 0.dp else -Bar.halfWidth,
        animationSpec = tween(durationMillis = 200),
        label = "MenuSlide"
    )

    // Background fade
    val backgroundAlpha by animateFloatAsState(
        targetValue = if (internalVisible.value) 0.4f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "Fade"
    )

    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = {
            onDismiss?.invoke()
            Bar.ShowMenu = false
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = backgroundAlpha))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                onDismiss?.invoke()
                    Bar.ShowMenu = false
                }
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToPx(), 0) }
                .width(Bar.halfWidth)
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .background(Color.DarkGray)
        ) {
            content()
        }
    }
}











          
