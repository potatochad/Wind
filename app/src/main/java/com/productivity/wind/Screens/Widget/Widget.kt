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
                Box(
                    GlanceModifier
                        .fillMaxSize()
                        .clickable(
                            actionStartActivity(
                                Intent(context, AppUI::class.java)
                            )
                        )
                ) {
                    Text("Hello")
                }
            }
        }
    }
}






