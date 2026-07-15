package com.productivity.wind.Widget

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


class HelloWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = HelloWidget()
}
class HelloWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            GlanceTheme {
                Vlog("Rendering started")
                Text("Hello")
            }
        }
    }
}
