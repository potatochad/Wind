package com.productivity.wind.Screens.Widget

//‼️IMPORTS NEED BE CLOSELY CONTROLLED
//BECAUSE: import androidx.glance.text.Text ‼️= import androidx.compose.material3.Text

import android.util.Log
import androidx.glance.GlanceTheme
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import android.content.Context
import androidx.glance.text.Text
import com.productivity.wind.Imports.Utils.Vlog
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.GlanceModifier
import com.productivity.wind.Imports.Utils.AppUI
import android.content.Intent
import com.productivity.wind.R
import androidx.glance.unit.ColorProvider
import androidx.glance.ColorFilter
import androidx.glance.Image
import androidx.glance.ImageProvider
import android.graphics.Color
import androidx.glance.background
import androidx.glance.layout.padding
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.Column
import androidx.glance.layout.Alignment
import androidx.glance.text.TextStyle
import kotlinx.coroutines.CancellationException


class HelloWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = HelloWidget()
}
class HelloWidget : GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        try {
            provideContent {
                GlanceTheme {
                    Box(
                        modifier = GlanceModifier
                            .fillMaxSize()
                            .background(ColorProvider(Color.BLACK))
                            .cornerRadius(20.dp)
                            .padding(20.dp)
                            .clickable(
                                actionStartActivity(
                                    Intent(context, AppUI::class.java)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                provider = ImageProvider(R.drawable.ic_sports_esports),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(
                                    ColorProvider(Color.rgb(255, 215, 0))
                                )
                            )
                            
                            Text(
                                "Wind",
                                style = TextStyle(
                                    color = ColorProvider(Color.WHITE)
                                )
                            )
                        }
                    }
                }
            }
        } catch (e: CancellationException) {
            Log.e("HelloWidget", "CancellationException", e)
            throw e
        } catch (e: Exception) {
            Log.e("HelloWidget", "Widget failed", e)
        }
    }
}






