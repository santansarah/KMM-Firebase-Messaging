package com.santansarah.kmmfirebasemessaging.android

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import com.santansarah.kmmfirebasemessaging.android.di.androidAppModule
import com.santansarah.kmmfirebasemessaging.android.domain.notifications.notificationChannels
import com.santansarah.kmmfirebasemessaging.android.domain.notifications.notificationGroups
import com.santansarah.kmmfirebasemessaging.di.initKoin
import org.koin.android.ext.koin.androidContext

class SanTanShop: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SanTanShop)
            modules(androidAppModule)
        }
        createNotificationChannel()

    }


    private fun createNotificationChannel() {

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationGroups.forEach {
            notificationManager.createNotificationChannelGroup(
                NotificationChannelGroup(
                    it.groupId,
                    it.groupName
                )
            )
        }

        notificationChannels.forEach {
            val channel = NotificationChannel(
                it.channelId,
                it.name,
                it.importance
            )
            channel.description = it.description
            channel.group = it.channelGroup

            notificationManager.createNotificationChannel(channel)
        }
    }

}

