@file:OptIn(ExperimentalFoundationApi::class)

package com.productivity.wind.Imports.UI_visible

import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import com.productivity.wind.Imports.Utils.NavControl.*
import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Popup
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.input.pointer.*
import com.productivity.wind.Imports.Utils.*
import android.content.*
import android.provider.*


@Composable
fun DarkBackground(onDismiss: Do = {}){
	Popup(
		onDismissRequest = { onDismiss() },
		alignment = Alignment.Center,
	) {
		Box(
			Mod.maxS().background(faded(Color.Black)).pointerInput(Unit) {
				detectTapGestures(
					onTap = {
						onDismiss()
						Vlog("black click")
					}
				)
			}
		)
	}
}


@Composable
fun IsSure(show: mStateBool, msg: Str = "delete this item for ever", Do: Do) {
    LazyPopup(
        show,
        "Delete",
        "Are you certain you want to $msg?",
        onConfirm = {Do()},
    )
}

@Composable
fun AllowAppUsage() {
    LazyPopup(
        mState(yes),
        "Need Usage Permission",
        "To function correctly, this app requires access to your app usage data. Granting this permission allows the app to monitor usage statistics and manage app-related tasks efficiently. Without it, this feature won't work.",
        onConfirm = {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
            App.startActivity(intent)
        },
		onClose = {
			navBack()
		}
    )
}





















