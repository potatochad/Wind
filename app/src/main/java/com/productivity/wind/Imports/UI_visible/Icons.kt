@file:OptIn(ExperimentalFoundationApi::class)

package com.productivity.wind.Imports.UI_visible

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
import androidx.compose.foundation.lazy.*
import com.productivity.wind.Imports.*





object Icon {
    @Composable
    fun Menu(Do: Do) {
        LazyIcon(Icons.Default.Menu) {
            Do()
        }
    }

    @Composable
    fun Reload(Do: Do) {
        LazyIcon(Icons.Default.Refresh) { 
            Do() 
        }
    }

    @Composable
    fun Chill(Do: Do) {
        LazyIcon(Icons.Default.SportsEsports) {
            Do()
        }
    }
    @Composable
    fun Add(Do: Do = { goTo("Challenge") }) {
        LazyIcon(Icons.Default.Add) {     
            Item.enoughPoints{
                Do()
            }
        }
    }

    @Composable
    fun MoreMenu(Do: Do) {
        LazyIcon(Icons.Default.MoreVert) {
            Do()
        }
    }


    @Composable
    fun Edit(
        noPoints: Bool = no,
        Do: Do,
    ) {
        LazyIcon(Icons.Default.Edit) {
            if (!noPoints){
                Item.enoughPoints{
                    Do()
                }
            } else {
                Do()
            }
        }
    }

    @Composable
    fun Delete(Do: Do = {}) {
        var show = r_m(no)
        
        LazyIcon(Icons.Default.Delete){
            show.it = yes
        }
        isSure(show) {
          Do()
        }
    }


    @Composable
    fun Copy(txt: Str) {
        var copied by r_m(no)

        RunOnce(copied) {
            if (copied) {
                delay(1000)
                copied = no
            }
        }

        LazyIcon(if (copied) Icons.Default.Check else Icons.Default.ContentCopy){
            copyToClipboard(txt)
            copied = yes
        }
    }
}










