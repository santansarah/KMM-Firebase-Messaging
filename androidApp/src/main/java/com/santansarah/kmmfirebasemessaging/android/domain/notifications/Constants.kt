package com.santansarah.kmmfirebasemessaging.android.domain.notifications

import android.app.NotificationManager.IMPORTANCE_DEFAULT
import com.santansarah.kmmfirebasemessaging.SharedRes

enum class NotifyChannelTypes(val channelId: String) {
    ORDERS("orderStatus"),
    DEALS("deals"),
    REMINDERS("reminders")
}

enum class NotificationEventDetails(
    val notifyChannelType: NotifyChannelTypes,
    val icon: Int,
    val title: String
) {
    ORDER_PLACED(NotifyChannelTypes.ORDERS, SharedRes.images.flower.drawableResId, "Order placed"),
    ORDER_SHIPPED(NotifyChannelTypes.ORDERS, SharedRes.images.flower.drawableResId, "Order shipped"),
    ORDER_DELIVERED(NotifyChannelTypes.ORDERS, SharedRes.images.flower.drawableResId, "Order delivered")
}

data class AppNotificationGroup(
    val groupId: String,
    val groupName: String
)

data class AppNotificationChannel(
    val channelGroup: String,
    val channelId: String,
    val name: String,
    val description: String,
    val importance: Int,
    val icon: Int
)

val notificationGroups = listOf(
    AppNotificationGroup("orders", "Your Orders"),
    AppNotificationGroup("deals", "Discounts & Deals")
)

val notificationChannels = listOf(
    AppNotificationChannel(
        "orders", "orderPlaced",
        "Order Placed",
        "We've received your order.", IMPORTANCE_DEFAULT, SharedRes.images.flower.drawableResId
    ),
    AppNotificationChannel("orders", "orderShipped",
        "Order Shipped", "Your order's been shipped.",
        IMPORTANCE_DEFAULT, SharedRes.images.flower.drawableResId)
)