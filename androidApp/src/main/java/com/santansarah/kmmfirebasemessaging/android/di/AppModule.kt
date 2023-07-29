package com.santansarah.kmmfirebasemessaging.android.di

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.santansarah.kmmfirebasemessaging.android.services.AppAnalyticsService
import org.koin.dsl.module

val androidAppModule = module {

    single { Firebase.analytics }
    single { AppAnalyticsService(get()) }

}