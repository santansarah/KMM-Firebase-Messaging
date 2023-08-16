package com.santansarah.kmmfirebasemessaging.domain

interface INotificationService {

    suspend fun showOrderPlacedNotification(orderId: String)

}