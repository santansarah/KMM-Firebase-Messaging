package com.santansarah.kmmfirebasemessaging.android.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FCMService(): FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("test", "new token: $token")
    }

}