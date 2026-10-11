package com.productivity.wind.Screens.Widget

import android.util.*
import kotlin.collections.*
import com.productivity.wind.R
import com.productivity.wind.Imports.Utils.*
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews


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

                views.onClick(
                    R.id.widget_root,
                    goTo("wind://web", context)
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
