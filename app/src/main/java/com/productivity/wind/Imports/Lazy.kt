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
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.activity.compose.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.google.android.gms.location.*
import android.os.*
import kotlin.math.*
import androidx.compose.ui.geometry.*


@Composable
fun LazyTheme(content: ui) {
    val appColorScheme = darkColorScheme(
        background = Color.Black,
        onBackground = Color.White,
        // surface = Color.Black,
        onSurface = Color.White,
        // primary = Color.Black, ✅️
        onPrimary = Color.White,
    )
    MaterialTheme(
        colorScheme = appColorScheme,
        content = content
    )
}




@Composable
fun LazyImage(
    source: Any?,
    modifier: Mod = Mod.s(34).space(5)
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
    modifier: Mod = Mod.h(200),
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
fun LazyInput(
    what: Any,
    isInt: Bool = no,
	modifier: Mod = Mod,
    maxLetters: Int = 20,
    textStyle: TextStyle = TextStyle(
		color = Color.White,
		fontSize = 14.sp,
		textAlign = TextAlign.Start
	),
    onChange: DoStr = {},
) {
    val finalMod = modifier.space(h = 8, w = 4).background(CardColor, shape = RoundedCornerShape(4.dp))
       

	val whatState: m_<Str> = when (what) {
        is m_<*> -> what as m_<Str>
        is Int -> r { m("$what") }
        is Str-> r { m(what) }
        else -> r { m("") }
	}
	
    Input(
        what = whatState,
        isInt = isInt,
        modifier = finalMod,
        textStyle = textStyle,
    ) { input ->
		if (isInt && input.isEmpty()) {
			whatState.it = "0"
		} else {
			whatState.it = input.take(maxLetters)
		}
		onChange(whatState.it)
    }
}






@Composable
fun LazySwitch(isOn: Bool, onToggle: Do_<Bool>) {
    Switch(
      checked = isOn,
      onCheckedChange = onToggle,
      modifier = Mod.s(34), // default ~39dp → minus 5dp
      colors = SwitchDefaults.colors(
          checkedThumbColor = Color(0xFFFFD700),
          uncheckedThumbColor = Color.LightGray,
          checkedTrackColor = Color(0xFFFFF8DC),
          uncheckedTrackColor = Color(0xFFE0E0E0)
      )
    )
}



@Composable
fun LazySlider(
    modifier: Mod = Mod.space(w = 12, h=12),
    min: Float = 1f,
    max: Float = 200_000f,
    linear: Bool = no,
	circeS: Any = 15,
    initialPos: Float = 0f,
    onValueChange: Do_<Float>,
) {
    var sliderPos by r_m(initialPos.coerceIn(0f, 1f))

    Box(
        modifier.pointerInput(Unit) {
            detectDragGestures { change, _ ->
                val newPos = (change.position.x / size.width).coerceIn(0f, 1f)
                sliderPos = newPos
                val value = if (linear) {
                    min + (max - min) * sliderPos
                } else {
                    min * (max / min).pow(sliderPos)
                }
                onValueChange(value)
            }
        }
    ) {
        Canvas(Mod.maxW()) {
            // Track
            drawLine(
                color = Color.Gray,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = 4f
            )
            // Filled portion
            drawLine(
                color = Gold,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width * sliderPos, size.height / 2),
                strokeWidth = 4f
            )
            // Thumb
            drawCircle(
                color = Gold,
                radius = toF(circeS),
                center = Offset(size.width * sliderPos, size.height / 2)
            )
        }
    }
}

  
  
@Composable
fun LazyLine(
    show: Bool = yes,
    color: Color = Color(0xFFFFD700),
    thickness: Dp = 1.dp,
    MoveY: Int = 0,
    spaceH: Dp = 0.dp,
    width: Dp = Dp.Unspecified,
  ) {
    if (!show) return

    Divider(
        color = color,
        thickness = thickness,
        modifier = Mod
            .offset(y = MoveY.dp)
            .space(h=spaceH)
            .then(if (width != Dp.Unspecified) Modifier.width(width) else Modifier)
    )
}
  
@Composable
fun LazzyRow(
    modifier: Mod = Mod,
    space: Int = 0,
    center: Bool = no,
    ui: ui,
) {
    Row(
        modifier.maxW().space(space),
        horizontalArrangement = if (center) Arrangement.Center else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ui()
    }
}

      
@Composable 
fun LazyRuleCard(
    Header: Str,
    ui: ui,
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
		ui()
	}

}


@Composable
fun LazyCard(
    innerPadding: Int = 16,
    corners: Int = 16,
    modifier: Mod = Mod.space(h= 8, w = 10).maxW(),
    ui: ui,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corners.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(CardColor),
    ) {
        Column(
            modifier = Modifier.space(
                start = innerPadding.dp,
                end = innerPadding.dp,
                bottom = innerPadding.dp,
                top = (innerPadding - 3).dp // slight top adjustment
            )
        ) {
            ui()
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
    modifier: Mod = Mod,
    color: Color = Color.White,
	onClick: Do = {},
) {
	
	UI.ComposeCanBeTiny() {
        IconButton(
            onClick = {
				wait(100) {
					onClick()
				}
			},
            modifier = modifier
                .space(OuterPadding) // OUTER padding
                .s(ButtonSize)      // controls inner room around icon
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Mod.s(24)
                )
            }

            if (BigIcon != null && BigIconColor != null) {
                val shape = if (SquareIcon) RoundedCornerShape(6.dp) else CircleShape
                val innerSize = if (SquareIcon) 24.dp else 20.dp
                Box(
                    Mod.s(BigIconSize).clip(shape).background(BigIconColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = BigIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Mod.s(innerSize)
                    )
                }
            }
        }
    }
}



@Stable
@Composable
fun LazyMore(
    title: Str = "Show more",
	initiallyExpanded: Bool = no,
	modifier: Mod = Mod,
    
    ui: ui
) {
    var expanded by r_m(initiallyExpanded)
    val rotation by animateFloatAsState(targetValue = if (expanded) 90f else 0f)

    Column(modifier = modifier) {
        Row(
            modifier = Mod
                .maxW()
                .clickable { expanded = !expanded }
                .space(8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = if (expanded) "Show less" else "Show more",
                modifier = Mod.rotate(rotation),
                tint = Color(0xFFFFD700) // GOLD icon
            )
            Spacer(modifier = Mod.w(8))
            Text(
                text = title,
                modifier = Mod.weight(1f),
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
                Mod
                    .maxW()
                    .space(start = 32, top = 4)
            ) {
                ui()
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
    endContent: ui? = null,
    modifier: Mod = Mod,

    icon: ImageVector? = null,
    BigIcon: ImageVector? = null,
    BigIconColor: Color? = null,

	topPadding: Dp = 7.dp,
	bottomPadding: Dp = 7.dp,
	
	onClick: Do? = null,
) {
	Row(
        Mod
            .maxW()
            .space(
                top = topPadding,
                bottom = bottomPadding,
                start = 7.dp,
                end = 7.dp
            ),
		verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
			modifier
                .maxW()
                .clickable(enabled = onClick != null) { onClick?.invoke() },
			shape = RoundedCornerShape(12.dp),
			colors = CardDefaults.cardColors(containerColor = CardColor),
			elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
		) {
            Row(
				Mod
                    .maxW()
                    .space(5.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				if (icon != null) {
					Icon(
						imageVector = icon,
						contentDescription = null,
						tint = Color.White,
						modifier = Mod
                            .space(end = 10)
                            .s(24)
					)
				}	    

				if (BigIcon != null && BigIconColor != null) {
					Box(
                        modifier = Mod
                            .space(end = 10)
                            .s(30)
                            .clip(CircleShape)
                            .background(BigIconColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = BigIcon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Mod.s(20)
                        )
                    }
				}


				Column(Mod.weight(1f)) {
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
    titleContent: ui,
    onBackClick: Do = {},
    showBack: Bool = yes,
    modifier: Mod = Mod,

    showDivider: Bool = yes,
    DividerPadding: Bool = yes,

    h: Int = 100,
) {
    val ui = rememberSystemUiController()
    var clickedBack by r_m(no)
    RunOnce(Unit) {
        ui.setStatusBarColor(Color.Black, darkIcons = no)
    }

    Column {
        move(h=getStatusBarHeight()/3)

        Row(
            modifier
                .maxW()
                .background(Color.Black)
                .space(w = 12)
                .h(h),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (showBack) {
				move(5)
                LazyIcon(Icons.Default.ArrowBack) {
					if (!clickedBack) {
							clickedBack = yes
							onBackClick()
							
							navBack()
					}
				}
            }

			// Title content
			Box(
                Mod.weight(1f).space(start = if (showBack) 8 else 0),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(Mod.maxW()) {
                    LazzyRow {
                        titleContent()
                    }
				}
				
			}
		}

		if (showDivider){
			LazyLine(color = Color.Gray)		
			if (DividerPadding){
				move(10)
			}
		}
    }
}


@Composable
fun LazyScreen(
	txt: Str = "Hi",
    top: ui = { Text("$txt") },
    onBackClick: Do = {},
    showBack: Bool = yes,
    modifier: Mod = Mod.background(Color.Black),
	
    showDivider: Bool = yes,
    DividerPadding: Bool = yes,

    headerHeight: Int = 44,
    content: ui,
) {

	val header: ui = {
		LazyHeader(
			titleContent = top,
			onBackClick = onBackClick,
			showBack = showBack,
			showDivider = showDivider,
			DividerPadding = DividerPadding,
			h = headerHeight
		)
	}
	val bottom: ui = {
		LazzyRow {move(bottomSystemHeight())}
	}

    Column(modifier) {
        header()
        Column(Mod.h(AppLazyH)) {
			Column(modifier) {
				content()
			}
            bottom()
        }
    }

}



//endregion


//region LAZY POPUP

@Composable
fun LazyBigPopup(
    show: mBool,
	title: Str = "Title",
	onOk: Do = {},
	onCancel: Do = {},
	onDismiss: Do = { show.it = no },
	backhandler: Do = {}, 
	mod: Mod = Mod.w(350).h(560),
    ui: ui,
) {   
    if (!show.it) return

	BackHandler {
        backhandler()
		onDismiss()
	}
	
    Popup {
		Box(Mod.maxS().center().background(faded(Color.Black)).pointerInput(Unit) {
            detectTapGestures {
                onDismiss()
			}
		}){
			Column(
				mod.round(12).background(Color.DarkGray).click(no){},
			) {
				LazzyRow(
					center = yes,
					space = 8
				) {
					Text(title.size(18))
				}
				Box(Mod.weight(1f)) { 
					ui()
				}
				
				LazzyRow {
					UI.End {
						UI.Ctext("CANCEL"){
							onCancel()
							onDismiss()
						}
						move(50)
						UI.Ctext("OK"){
							onOk()
							onDismiss()
						}
						move(10)
					}
				}
			}
		}
    }
}

@Composable
fun LazyPopup(
    show: m_<Bool>,
    title: Str = "Info",
	msg: Str = "",
    showCancel: Bool = yes,
    showConfirm: Bool = yes,
    onConfirm: Do? = null,
    onCancel: Do? = null,
	onDismiss: Do? = { show.it = no },
	onClose: Do = {},
	ui: ui? = null,
) {
    if (!show.it) return
	

    AlertDialog(
        onDismissRequest = {
            onDismiss?.invoke()
			onClose()
        },
        title = { Text(title) },
        text = {
			Column{
				ui?.invoke() ?: Text(msg)
			}
        },
        confirmButton = {
            if (showConfirm) {
				move(15)
                UI.Ctext("OK"){
					wait {
						onConfirm?.invoke()
						hide(show)
					}
                }
            }
        },
        dismissButton = if (showCancel) {
            {
				UI.Ctext("Cancel"){
					wait {
						onCancel?.invoke()
						onClose()
						hide(show)
					}
                }
            }
        } else null
    )
}




var menu by m(no)
@Composable
fun LazyMenu(
    onDismiss: Do? = null,
    ui: ui,
) {
    val visible = r_m(yes)
    val internalVisible = r_m(no)

    // Trigger showing/hiding Popup
    RunOnce(menu) {
        if (menu) {
            visible.it = yes
            delay(16)
            internalVisible.it = yes
        } else {
            internalVisible.it = no
            delay(200)
            visible.it = no
        }
    }

    if (!visible.it) return

    // Slide offset
    val offsetX by animateDpAsState(
        targetValue = if (internalVisible.it) 0.dp else -AppW/2,
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
            menu = no
        }
    ) {
        Box( Mod.maxS()
                .background(faded(Color.Black, backgroundAlpha))
                .clickable(
                    indication = null,
                    interactionSource = r { MutableInteractionSource() }
                ) {
                    onDismiss?.invoke()
                    menu = no
                }
        )

        Box( Mod.offset { IntOffset(offsetX.roundToPx(), 0) }
                .w(AppW / 2 + 30.dp)
                .maxH()
                .background(Color.DarkGray)
        ) {
            ui()
        }
    }
}





@Composable
fun LazyMaps(
    startZoom: Float = 14f,
	id: Int = 1,
) {
	var savedMapType by s(MapType.NORMAL.name, "savedMapType, $id")

	var mapType by synch(
		MapType.valueOf(savedMapType)
	) {
		savedMapType = it.name
	}

	

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(Bar.userLocation, startZoom)
    }

    Box(Mod.maxS()) {

        GoogleMap(
            modifier = Mod.maxS(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = yes,
                mapType = mapType
            ),
			uiSettings = MapUiSettings(
				zoomControlsEnabled = no
			),
        )

        Text(
            "map".bold().darkGray(),
            Mod.align(Alignment.TopEnd)
                .space(top = 12, end = 64)
                .background(faded(Color.White, 0.7f))
                .click {
                    mapType =
                        if (mapType == MapType.NORMAL)
                            MapType.SATELLITE
                        else
                            MapType.NORMAL
                }.space(h = 8, w = 6)
        )
    }
}







          











          
