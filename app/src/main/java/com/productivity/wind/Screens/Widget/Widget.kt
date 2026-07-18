package com.productivity.wind.Screens.Widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.productivity.wind.Imports.Utils.AppUI
import com.productivity.wind.R

class HelloWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        try {

            appWidgetIds.forEach { widgetId ->

                val views = RemoteViews(
                    context.packageName,
                    R.layout.widget_hello
                )

                val intent = Intent(
                    context,
                    AppUI::class.java
                )

                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                views.setOnClickPendingIntent(
                    R.id.widget_root,
                    pendingIntent
                )

                appWidgetManager.updateAppWidget(
                    widgetId,
                    views
                )
            }

        } catch (e: Exception) {
            Log.e("HelloWidgetProvider", "Widget update failed", e)
        }
    }
}
