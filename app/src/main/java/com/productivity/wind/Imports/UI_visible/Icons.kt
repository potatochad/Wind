@file:OptIn(ExperimentalFoundationApi::class)

package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.ToX.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.*
import androidx.compose.foundation.background
import com.productivity.wind.Imports.Utils.*
import androidx.compose.ui.res.*
import com.productivity.wind.R


typealias icon = ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Icon(
    icon: icon,
    size: Any = 25,        
    mod: Mod = Mod,
    color: Color = white,
	onClick: Do,
) {
	ComposeCanBeTiny() {
        IconButton(
            onClick = {
				wait(100) {
					onClick()
				}
			},
            modifier = Mod.space(5).s(toF(size)*1.7)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = mod.s(size)
            )
        }
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Icon(
    icon: icon,
    size: Any = 25,        
    mod: Mod = Mod,
    color: Color = white,
) {
	ComposeCanBeTiny() {
		Icon(
			imageVector = icon,
			contentDescription = null,
			tint = color,
			modifier = mod.s(size)
		)
	}
}

@Composable
fun Icon(
    iconRes: Int,
    color: Color = white,
    size: Any = 24,
    mod: Mod = Mod,
) {
    Icon(
        painter = painterResource(id = iconRes),
        tint = color,
		contentDescription = null,
        modifier = mod.s(size)
    )
}


@Composable
fun Icon(
    iconRes: Int,
    color: Color = white,
    size: Any = 24,
    mod: Mod = Mod,
	Do: Do,
) {
	ComposeCanBeTiny() {
        IconButton(
            onClick = {
				wait(100) {
					Do()
				}
			},
            modifier = Mod.space(5).s(toF(size)*1.7)
        ) {
			Icon(iconRes, color, size, mod)
        }
	}
}




@Composable
fun BigIcon(
    icon: icon,
	color: Color,
    mod: Mod = Mod,
	size: Any = 20,
	Do: Do,
) {
	Icon(
		icon,
		size = size,
		mod = mod.round(toF(size)*5).background(color).space(5),
	){
		Do()
	}
}
@Composable
fun BigIcon(
    icon: icon,
	color: Color,
    mod: Mod = Mod,
	size: Any = 20,
) {
	Icon(
		icon,
		size = size,
		mod = mod.round(toF(size)*5).background(color).space(5),
	)
}

val myAppRes: Int = R.drawable.baseline_radar_24



object Icon {
	@Composable
	fun IconShortcut(icon: icon, mod: Mod = Mod, color: Color = white, Do: Do? = null) {
		if (Do != null) {
			Icon(icon, mod = mod, color = color) { Do() }
		} else {
			Icon(icon, mod = mod, color = color)
		}
	}

	
	@Composable
	fun Menu(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Menu, mod, color, Do)
	
	@Composable
	fun Reload(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Refresh, mod, color, Do)
	
	@Composable
	fun Chill(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.SportsEsports, mod, color, Do)
	
	@Composable
	fun Add(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Add, mod, color, Do)
	
	@Composable
	fun MoreMenu(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.MoreVert, mod, color, Do)
	
	@Composable
	fun Edit(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Edit, mod, color, Do)
	
	@Composable
	fun Delete(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Delete, mod, color, Do)    

	@Composable
	fun Search(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Search, mod, color, Do)

	@Composable
	fun Back(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.ArrowBack, mod, color, Do)

	@Composable
	fun Lock(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Lock, mod, color, Do)

	@Composable
	fun Whatshot(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Whatshot, mod, color, Do)

	@Composable
	fun Chat(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Chat, mod, color, Do)

	@Composable
	fun Landscape(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.Landscape, mod, color, Do)

	@Composable
	fun QueryStats(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.QueryStats, mod, color, Do)

	@Composable
	fun AccessTime(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.AccessTime, mod, color, Do)

	@Composable
	fun ContentPaste(mod: Mod = Mod, color: Color = white, Do: Do? = null) = IconShortcut(Icons.Default.ContentPaste, mod, color, Do)




    @Composable
    fun Copy(txt: Str) {
        var copied by r(no)

        RunOnce(copied) {
            if (copied) {
                delay(1000)
                copied = no
            }
        }

        Icon(if (copied) Icons.Default.Check else Icons.Default.ContentCopy){
            CopyToClipboard(txt)
            copied = yes
        }
    }
	@Composable
	fun Lock(Init: Bool, Do: Do_<Bool>) {
		var locked by r(Init)
		Icon(
			if (locked) Icons.Default.Lock else Icons.Default.LockOpen,
			color = if (locked) gold else gray,
		){
			locked = !locked
			Do(locked)
		}
	}
	@Composable
	fun Lock(locked: mStateBool, Do: Do_<Bool> = {}) {
		Icon(
			if (locked.it) Icons.Default.Lock else Icons.Default.LockOpen,
			color = if (locked.it) gold else gray,
		){
			locked.it = !locked.it
			Do(locked.it)
		}
	}
	@Composable
	fun Timer(
		on: Bool,
		Do: DoBool
	) {
		BigIcon( if (on) Icons.Default.Pause else Icons.Default.PlayArrow, if (on) gold else gray) {
			Do(on)
		}
	}

	
}


object BigIcon {
	@Composable
	fun IconShortcut(icon: icon, mod: Mod, color: Color, Do: Do?) {
		if (Do != null) {
			BigIcon(icon, mod = mod, color = color) { Do() }
		} else {
			BigIcon(icon, mod = mod, color = color)
		}
	}

	
	@Composable
	fun Menu(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Menu, mod, toColor(color), Do)
	
	@Composable
	fun Reload(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Refresh, mod, toColor(color), Do)
	
	@Composable
	fun Chill(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.SportsEsports, mod, toColor(color), Do)
	
	@Composable
	fun Add(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Add, mod, toColor(color), Do)
	
	@Composable
	fun MoreMenu(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.MoreVert, mod, toColor(color), Do)
	
	@Composable
	fun Edit(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Edit, mod, toColor(color), Do)
	
	@Composable
	fun Delete(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Delete, mod, toColor(color), Do)    

	@Composable
	fun Search(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Search, mod, toColor(color), Do)

	@Composable
	fun Back(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.ArrowBack, mod, toColor(color), Do)

	@Composable
	fun Lock(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Lock, mod, toColor(color), Do)

	@Composable
	fun Restore(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Restore, mod, toColor(color), Do)

	@Composable
	fun Backup(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Backup, mod, toColor(color), Do)
	
	@Composable
	fun Tune(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Tune, mod, toColor(color), Do)

	@Composable
	fun VisibilityOff(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.VisibilityOff, mod, toColor(color), Do)

	@Composable
	fun Extension(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Extension, mod, toColor(color), Do)

	@Composable
	fun ListAlt(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.ListAlt, mod, toColor(color), Do)

	@Composable
	fun Whatshot(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.Whatshot, mod, toColor(color), Do)

	@Composable
	fun LockOpen(color: Any, mod: Mod = Mod, Do: Do? = null) = IconShortcut(Icons.Filled.LockOpen, mod, toColor(color), Do)

	
	

	
}














