package com.santansarah.kmmfirebasemessaging.android.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.santansarah.kmmfirebasemessaging.android.MainActivity
import com.santansarah.kmmfirebasemessaging.android.domain.notifications.notificationChannels
import com.santansarah.kmmfirebasemessaging.domain.INotificationService
import kotlin.random.Random

class NotificationService(
    private val appContext: Context
): INotificationService {

    override suspend fun showOrderPlacedNotification(orderId: String) {

        val orderText = "Your order's been placed. We'll let you know when it's shipped."
        val appNotificationChannel = notificationChannels.find {
            it.channelId == "orderPlaced"
        }

        val notificationManager = appContext
            .getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = notificationManager
            .getNotificationChannel(appNotificationChannel!!.channelId)
        notificationChannel.lightColor = Color.Blue.toArgb()

        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)

        val activityIntent = Intent(appContext, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            appContext,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(appContext,
                    appNotificationChannel.channelId)
                    .setSmallIcon(appNotificationChannel.icon)
                    .setContentTitle(appNotificationChannel.name)
                    .setContentText(orderText)
                    .setContentIntent(activityPendingIntent)

        notificationManager.notify(Random.nextInt(), notification.build())

    }

}