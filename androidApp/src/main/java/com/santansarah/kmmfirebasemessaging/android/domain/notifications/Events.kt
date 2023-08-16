package com.santansarah.kmmfirebasemessaging.android.domain.notifications

import android.app.NotificationManager

sealed class NotificationEvent(val eventDetails: NotificationEventDetails,
                               val importance: Int) {
    data class OrderPlaced(
        val orderId: String
    ) : NotificationEvent(
        NotificationEventDetails.ORDER_PLACED,
        NotificationManager.IMPORTANCE_DEFAULT)


}
