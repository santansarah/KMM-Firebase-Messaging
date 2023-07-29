package com.santansarah.kmmfirebasemessaging.android.services

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.santansarah.kmmfirebasemessaging.domain.IAnalyticsService

// Firebase Analytics Implementation.
class AppAnalyticsService(private val analytics: FirebaseAnalytics): IAnalyticsService {

    override fun completeTutorial() {
        analytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_COMPLETE, null)
    }

}