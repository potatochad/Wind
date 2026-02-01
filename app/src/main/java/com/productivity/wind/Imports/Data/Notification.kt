// 1. Set text (any type, auto toString)
fun RemoteViews.text(id: Int, value: Any) = setTextViewText(id, value.toString())

// 2. Set image resource
fun RemoteViews.image(id: Int, resId: Int) = setImageViewResource(id, resId)

// 3. Set visibility
fun RemoteViews.visible(id: Int, visible: Bool) =
    setViewVisibility(id, if (visible) View.VISIBLE else View.GONE)

// 4. Set click pending intent
fun RemoteViews.click(id: Int, pendingIntent: PendingIntent) =
    setOnClickPendingIntent(id, pendingIntent)

// 5. Set text color
fun RemoteViews.color(id: Int, color: Int) =
    setTextColor(id, color)
