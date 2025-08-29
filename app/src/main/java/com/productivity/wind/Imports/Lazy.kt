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
import com.productivity.wind.Global1
import com.productivity.wind.Imports.*

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
    var delayMs: Long = 40,
    var chunkSize: Int = 1,
	var IntFirst: Int = 0,
)
@Composable
fun <T> LazzyList(
    items: List<T>,
    loadAll: Boolean = true,      
    style: LazzyListStyle = LazzyListStyle(),
    itemContent: @Composable (T) -> Unit,
) {
    val scrollPlace = rememberScrollState()
    val appearedItems = r { mutableStateListOf<T>() }

    // Immediately add initial batch
    val realInitialCount = style.IntFirst.coerceAtMost(items.size)
    if (appearedItems.isEmpty()) {
        if (loadAll) {
            appearedItems.addAll(items)   // load all at once
        } else {
            appearedItems.addAll(items.subList(0, realInitialCount))
        }
    }

    // Gradual loading for the rest (only if not loadAll)
    if (!loadAll) {
        LaunchedEffect(items) {
            var currentIndex = realInitialCount
            while (currentIndex < items.size) {
                val nextIndex = (currentIndex + style.chunkSize).coerceAtMost(items.size)
                appearedItems.addAll(items.subList(currentIndex, nextIndex))
                currentIndex = nextIndex
                if (currentIndex >= items.size) break
                kotlinx.coroutines.delay(style.delayMs)
            }
        }
    }

    Column(
        modifier = Modifier
            .height(style.height)
            .verticalScroll(scrollPlace)
    ) {
        appearedItems.forEach { item ->
            itemContent(item)
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
fun <T> PreloadItems(
    data: List<T>,
    itemContent: @Composable (T) -> Unit
) {
    Box(Modifier.size(1.dp).alpha(0f)) {
        Column {
            data.forEach { element ->
                key(element.hashCode()) {
                    itemContent(element) // prebuild it invisibly
                }
            }
        }
    }
}
fun <T> LazyListScope.noLag(
    data: List<T>,
    preload: Boolean = true,
    itemContent: @Composable (T) -> Unit
) {
    if (preload) {
        item {
            PreloadItems(data, itemContent)
        }
    }
}




  
  
@Composable
fun LazyLine(
    show: Boolean,
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
    content: @Composable () -> Unit,
  ){
    Row(
      modifier = modifier
        .fillMaxWidth()
        .padding(padding.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      content()
	  	}
}
      
@Composable 
fun LazyRuleCard(
	Header: Str,
	content: @Composable () -> Unit,
){
	LazyCard(
		style = LazyCardStyle(roundCorners = 8)
	){
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

data class LazyCardStyle(
	var roundCorners: Int = 16,
)
@Composable
fun LazyCard(
    inputColor: Color = Color(0xFF1A1A1A),
    innerPadding: Int = 16,
    style: LazyCardStyle = LazyCardStyle(),
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(style.roundCorners.dp),
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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyIcon(
    onClick: () -> Unit = {},
    icon: ImageVector? = null,
    BigIcon: ImageVector? = null,
    BigIconColor: Color? = null,
    SquareIcon: Boolean = false,
    BigIconSize: Int = 30,
    OuterPadding: Int = 5,          // outside space
    ButtonSize: Int = 40,           // actual button box (default M3 ~48)
    modifier: Modifier = Modifier,
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
                    tint = Color(0xFFFFD700),
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
    title: String = "Show more",
    initiallyExpanded: Boolean = false,
    content: @Composable () -> Unit
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
    title: String,
    subtitle: String? = null,
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
    titleContent: @Composable () -> Unit,
    onSearchClick: () -> Unit,
    onBackClick: () -> Unit = {},
    showBack: Boolean = true,
    showSearch: Boolean = false,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
	Mheight: Int = 100,
) {
    val ui = rememberSystemUiController()
    var DisableTB_Button by r { m(false) }
    LaunchedEffect(Unit) {
        ui.setStatusBarColor(Color.Black, darkIcons = false)
    }

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )

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
            if (showBack ) {
                IconButton(onClick = { if (DisableTB_Button) { }; if (!DisableTB_Button) { DisableTB_Button = true

                        onBackClick()
                        Global1.navController.popBackStack()
                    }

                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFFFD700)
                    )
                }
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
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFFFFD700)
                    )
                }
            }
        }

        if (showDivider) {
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
	    Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
	
    }
}


@Composable
fun LazyScreen(
    title: @Composable () -> Unit,
    onSearchClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    showBack: Boolean = true,
    showSearch: Boolean = false,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
	MheaderHeight: Int = 44,
    content: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val baseModifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .clipToBounds()
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            focusManager.clearFocus()
        }



    LazyColumn(
        modifier = baseModifier.then(modifier)
    ) {
        item {
            LazyHeader(
                titleContent = title,
                onSearchClick = onSearchClick,
                onBackClick = onBackClick,
                showBack = showBack,
                showSearch = showSearch,
                showDivider = showDivider,
				Mheight = MheaderHeight,
            )
        }
        item {
		Column {
			content()
		}
        }
    }
}



//endregion


//region LAZY POPUP

@Composable
fun LazyPopup(
    show: MutableState<Boolean>,
    onDismiss: (() -> Unit)? = { show.value = false },
    title: String = "Info",
    message: String,
    content: (@Composable () -> Unit)? = null,
    showCancel: Boolean = true,
    showConfirm: Boolean = true,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,

) = NoLagCompose {
	
	 var preloaded by remember(content, message) { mutableStateOf<(@Composable () -> Unit)?>(null) }
    LaunchedEffect(content, message) {
        preloaded = {
            if (content != null) content()
            else Text(message)
        }
    }

    // 🔹 Invisible host – forces Compose to build content early
    Box(modifier = Modifier.size(0.dp)) {
        preloaded?.invoke()
	}
	
	
    if (show.value) { 
		AlertDialog(
			onDismissRequest = {
				onDismiss?.invoke()
			},
			title = { Text(title) },
			text = {
				preparedContent()
			},
			confirmButton = {
				if (showConfirm) {
					TextButton(onClick = {
						onConfirm?.invoke()
						show.value = false
					}) {
						Text("OK")
					}
				}
			},
			dismissButton = if (showCancel) {
				{
					TextButton(onClick = {
						onCancel?.invoke()
						show.value = false
					}) {
						Text("Cancel")
					}
				}
			} else null
     	)
	}
}




@Composable
fun LazyMenu(
    onDismiss: (() -> Unit)? = null,
    content: @Composable () -> Unit,
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











          
