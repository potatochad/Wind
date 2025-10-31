@file:OptIn(ExperimentalFoundationApi::class)

package com.productivity.wind.Imports

import android.annotation.SuppressLint
import androidx.compose.ui.draw.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import kotlinx.coroutines.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.collections.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.productivity.wind.*
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.window.*
import androidx.compose.foundation.text.selection.*
import com.productivity.wind.Imports.Data.*
import androidx.compose.ui.focus.*

fun Modifier.clickOrHold(
    hold: Bool = yes,
    action: Do,
): Modifier {
    return if (hold) {
        pointerInput(Unit) {
            detectTapGestures(onLongPress = { action() })
        }
    } else {
        clickable(
            indication = null,
            interactionSource = MutableInteractionSource()
        ) {
            action()
        }
	}
}




fun Modifier.maxS(): Mod= this.fillMaxSize()
fun Modifier.maxW(): Mod= this.fillMaxWidth()
fun Modifier.maxH(): Mod= this.fillMaxHeight()




@Composable
fun LazyMove(
    x: Dp = 0.dp,
    y: Dp = 0.dp,
    content: Content,
) {
    Box(
		Modifier.maxS()
    ) {
        Box(Modifier
            .offset(x = x, y = y)
            .wrapContentSize()
        ){
			content()
		}
    }
}





@Composable
fun LazyTheme(content: Content) {
    val appColorScheme = darkColorScheme(
        background = Color.Black,
        onBackground = Color.White,
        surface = Color.Black,
        onSurface = Color.White,
        primary = Color.Black,
        onPrimary = Color.White
    )

    val blueTextSelectionColors = TextSelectionColors(
        handleColor = Color.Blue,                 
        backgroundColor = Color.Blue.copy(alpha = 0.4f)  
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides blueTextSelectionColors
    ) {
        MaterialTheme(
            colorScheme = appColorScheme,
            content = content
        )
    }
}












@Composable
fun LazyWindow(
    show: m_<Bool>,
    content: Content,
) {
    // Local state to control if Popup is alive
    var popupVisible by r_m(no)

    RunOnce(show.it) {
        if (show.it) {
            popupVisible = yes  // Show immediately
        } else {
            delay(200)           // Wait for fade-out duration
            popupVisible = no // Then remove Popup
        }
    }

    if (popupVisible) {
        Popup(
            properties = PopupProperties(focusable = yes)
        ) {
            Box(
                modifier = Modifier
                    .maxS()
                    .clickOrHold() { set(show, no) },
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = show.it,
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
fun LazyMeasure(
    x: m_<Dp>? = null,
    y: m_<Dp>? = null,
    w: m_<Dp>? = null,
    h: m_<Dp>? = null,
    modifier: Mod = Modifier,
    content: Content
) {
    val density = LocalDensity.current

    Box(
        modifier = modifier.onGloballyPositioned { layoutCoordinates ->
            val position = layoutCoordinates.positionInRoot()
            val size = layoutCoordinates.size

            set(x, with(density) { position.x.toDp() })
            set(y, with(density) { position.y.toDp() })
            set(w, with(density) { size.width.toDp() })
            set(h, with(density) { size.height.toDp() })
        }
    ) {
        content()
    }
}




@SuppressLint("UnusedBoxWithConstraintsScope", "UnrememberedMutableState")
@Composable
fun LazyInfo(
    infoContent: Content,
    hold: Bool = no,
    ChangeY: Dp = 80.dp,
    popupWidth: Dp = 0.dp,
    popupHeight: Dp = 0.dp,
    content: Content,
) {
    var show = r_m(no)
    var x = r_m(0.dp)
    var y = r_m(0.dp)
    var w = r_m(0.dp)
    var h = r_m(0.dp)

    LazyMeasure(
        x, y, w, h,
        modifier = Mod.clickOrHold(hold) { show(show) }
    ) {
        content()
    }

    // Calculate popup position
    val popupW = r_m(0.dp)
    val popupH = r_m(0.dp)

	val top by r_m(y.it - h.it)
	val bottom by r_m(y.it)
	val start by r_m(x.it)
	val end by r_m(x.it + w.it)

    // Compute popup position dynamically
    val popupX = remember(x.it, w.it, popupW.it) {
        if (top > popupH.it + 20.dp) "top" else "bottom"
    }
    val popupY = remember(y.it, h.it, popupH.it) {
        if (end < popupW.it+ 20.dp) "end" else "start"
	}



	

	// Actual position calculations
	val bopupX = remember(popupX, popupH.it) {
		if (popupH.it == 0.dp) {
			0.dp
		} else {
			when (popupX) {
				"top" -> x.it - popupW.it / 2   // Example placement
				"bottom" -> x.it + popupW.it / 2
				else -> 0.dp
			}
		}
	}

	val bopupY = remember(popupY, popupW.it) {
		if (popupW.it == 0.dp) {
			0.dp
		} else {
			when (popupY) {
				"start" -> y.it - popupH.it / 2
				"end" -> y.it + popupH.it / 2
				else -> 0.dp
			}
		}
	}



	
	LazyWindow(show) {
		LazyMove(bopupX, bopupY) {
			// Red dot marking the click point
			BoxWithConstraints {
				set(popupW, maxWidth)
                set(popupH, maxHeight)
                // Red dot marking click point
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Yellow)
                )
			}
		}
	}
	LazyWindow(show) {
		LazyMove(x.it, y.it) {
			// Red dot marking the click point
			BoxWithConstraints {

                // Red dot marking click point
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(Color.Red, CircleShape)
                )
			}
		}
	}
	
	LazyWindow(show) {
		LazyMove(end, top) {
			// Red dot marking the click point
			BoxWithConstraints {
                // Red dot marking click point
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(Color.Magenta, CircleShape)
                )
			}
		}
	}
	
}








//NormalVisual(show, popupX, popupY){}
    
@Composable
fun NormalVisual(
    show: m_<Bool>,
    popupX: Dp,
    popupY: Dp,
    content: Content,
){
	LazyWindow(show) {
        LazyMove(popupX, popupY) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(modifier = Modifier.padding(8.dp)) {
                    content()
                }
            }
        }
	}
}






@Composable
fun LazyImage(
    source: Any?,
    modifier: Mod = Modifier
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


@Composable
fun <T> LazzyList(
    data: List<T>,
    modifier: Mod = Modifier
        .height(200.dp),
    content: @Composable (T, Int) -> Unit,
) {
    var data2 by r_m(data)

    Column(modifier.scroll().maxW()) {
        data2.forEachIndexed { index, item ->
            key(item.hashCode()) {
                content(item, index)
            }
        }
    }
}




@Composable
fun LazyInput (
	what: MutableState<Str>,
	isInt: Bool = no,
	maxLetters: Int = 20,
	modifier: Modifier = Modifier
		.height(34.dp)
		.background(
			Color.Gray.copy(alpha = 0.2f), 
			shape = RoundedCornerShape(4.dp)
		)		
		.padding(
			horizontal = 8.dp, 
			vertical = 4.dp
		)
		.onFocusChanged {
			
		}
		.wrapContentHeight(
			Alignment.CenterVertically
		),
	textStyle: TextStyle = TextStyle(
		color = Color.White,
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
	),
	onChange: (Str) -> Unit = {},
) {
	BasicInput(
		what = what,
		isInt = no,
		modifier = modifier,
		textStyle = textStyle,
	) { 
		if (isInt && it.isEmpty()) {
			what.it = "0"
		} 	
		what.it = it.take(maxLetters)
	}
}





@Composable
fun LazySwitch(isOn: Bool, onToggle: Do_<Bool>) {
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
    show: Bool = yes,
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
    modifier: Mod = Modifier,
    padding: Int = 0,
    center: Bool = no, // Kotlin uses 'Boolean', not 'Bool'
    content: Content,
) {
    Row(
        modifier = modifier
            .maxW()
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
    innerPadding: Int = 16,
    corners: Int = 16,
    modifier: Mod = Mod
	     .padding(horizontal = 8.dp, vertical = 10.dp)
         .maxW(),
    content: Content,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corners.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(CardColor),
    ) {
        Column(
            modifier = Modifier.padding(
                start = innerPadding.dp,
                end = innerPadding.dp,
                bottom = innerPadding.dp,
                top = (innerPadding - 3).dp // slight top adjustment
            )
        ) {
            content()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyIcon(
    icon: ImageVector? = null,
    BigIcon: ImageVector? = null,
    BigIconColor: Color? = null,
    SquareIcon: Bool = no,
    BigIconSize: Int = 30,
    OuterPadding: Int = 5,          // outside space
    ButtonSize: Int = 40,           // actual button box (default M3 ~48)
    modifier: Mod = Modifier,
    color: Color = Color.White,
	onClick: Do = {},
) {
	UI.ComposeCanBeTiny() {
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
                    tint = color,
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
    modifier: Mod = Modifier,
    title: Str = "Show more",
    initiallyExpanded: Bool = no,
    content: Content
) {
    var expanded by r_m(initiallyExpanded)
    val rotation by animateFloatAsState(targetValue = if (expanded) 90f else 0f)

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .maxW()
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
                    .maxW()
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
    endContent: Content? = null,
    modifier: Mod = Modifier,

    icon: ImageVector? = null,
    BigIcon: ImageVector? = null,
    BigIconColor: Color? = null,
	
    topPadding: Dp= 7.dp,
    bottomPadding: Dp = 7.dp,
	
	onClick: Do? = null,
) {
	Row(
        modifier = Modifier
            .maxW()
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
                .maxW()
                .clickable(enabled = onClick != null) { onClick?.invoke() },
			shape = RoundedCornerShape(12.dp),
			colors = CardDefaults.cardColors(containerColor = Color(0xFF121212)),
			elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
		) {
            Row(
				modifier = Modifier
                    .maxW()
                    .padding(5.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				if (icon != null) {
					Icon(
						imageVector = icon,
						contentDescription = null,
						tint = Color.White,
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
    onBackClick: Do = {},
    showBack: Bool = yes,
    modifier: Mod = Modifier,

    showDivider: Bool = yes,
    DividerPadding: Bool = yes,

    height: Int = 100,
) {
    val ui = rememberSystemUiController()
    var clickedBack by r_m(no)
    LaunchedEffect(Unit) {
        ui.setStatusBarColor(Color.Black, darkIcons = no)
    }

    Column {
        UI.move(h=getStatusBarHeight()/3)

        Row(
            modifier = modifier
                .maxW()
                .background(Color.Black)
                .padding(vertical = 12.dp)
                .height(height.dp),
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
                LazyIcon(Icons.Default.ArrowBack) {
					if (!clickedBack) {
							clickedBack = yes
							onBackClick()
							App.navHost.popBackStack()
					}
				}
            }

			// Title content
			Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = if (showBack) 8.dp else 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(Modifier
                    .maxW()
                ) {
                    LazzyRow {
                        titleContent()
                    }
				}
				
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
fun LazyScreen(
    title: Content,
    onBackClick: Do = {},
    showBack: Bool = yes,
    modifier: Mod = Modifier
        .background(Color.Black)
        .maxS(),

    showDivider: Bool = yes,
    DividerPadding: Bool = yes,

    headerHeight: Int = 44,
    content: Content,
) {
	val header: Content = {
		LazyHeader(
			titleContent = title,
			onBackClick = onBackClick,
			showBack = showBack,
			showDivider = showDivider,
			DividerPadding = DividerPadding,
			height = headerHeight
		)
	}
	val bottom: Content = {
		LazzyRow {UI.move(bottomSystemHeight())}
	}

    Column(modifier) {
        header()
        Column(Modifier
            .height(App.LazyScreenContentHeight)) {
            content()
            bottom()
        }
    }

}



//endregion


//region LAZY POPUP

@Composable
fun LazyPopup(
    show: m_<Bool>,
    onDismiss: Do? = { show.value = no },
    title: Str = "Info",
    message: Str,
    content: Content? = null,
    showCancel: Bool = yes,
    showConfirm: Bool = yes,
    onConfirm: Do? = null,
    onCancel: Do? = null,
) {
    if (!show.it) return

	//scope.launch { 
	//	delay(100L)
	//}//Wait 100L before closing
	

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
					LazyDelay {
						onConfirm?.invoke()
						hide(show)
					}
                }
            }
        },
        dismissButton = if (showCancel) {
            {
				UI.Ctext("Cancel"){
					LazyDelay {
						onCancel?.invoke()
						hide(show)
					}
                }
            }
        } else null
    )
}
fun LazyDelay(
    delayMillis: Long = 100L,
    Action: Do,
) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(delayMillis)
        Action()
    }
}





@Composable
fun LazyMenu(
    onDismiss: Do? = null,
    content: Content,
) {
    val visible = r_m(yes)
    val internalVisible = r_m(no)

    // Trigger showing/hiding Popup
    RunOnce(App.Menu) {
        if (App.Menu) {
            set(visible, yes)
            delay(16)
            set(internalVisible, yes)
        } else {
            set(internalVisible, no)
            delay(200) // Wait for animation out
            set(visible, no)
        }
    }

    if (!visible.it) return

    // Slide offset
    val offsetX by animateDpAsState(
        targetValue = if (internalVisible.it) 0.dp else -App.screenWidth/2,
        animationSpec = tween(durationMillis = 200),
        label = "MenuSlide"
    )

    // Background fade
    val backgroundAlpha by animateFloatAsState(
        targetValue = if (internalVisible.it) 0.4f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "Fade"
    )

    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = {
            onDismiss?.invoke()
            App.Menu = no
        }
    ) {
        Box(
            modifier = Modifier
                .maxS()
                .background(Color.Black.copy(alpha = backgroundAlpha))
                .clickable(
                    indication = null,
                    interactionSource = r { MutableInteractionSource() }
                ) {
                    onDismiss?.invoke()
                    App.Menu = no
                }
        )

        Box( Modifier
                .offset { IntOffset(offsetX.roundToPx(), 0) }
                .width(App.screenWidth / 2 + 30.dp)
                .maxH()
                .background(Color.DarkGray)//
        ) {
            content()
        }
    }
}











          
