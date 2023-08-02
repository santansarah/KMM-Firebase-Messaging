package com.santansarah.kmmfirebasemessaging.android.di

import android.content.Context
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.santansarah.kmmfirebasemessaging.android.presentation.account.SignInObserver
import com.santansarah.kmmfirebasemessaging.android.services.AppAnalyticsService
import com.santansarah.kmmfirebasemessaging.android.services.SignInService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidAppModule = module {

    single { Firebase.analytics }
    single { AppAnalyticsService(get()) }
    single { FirebaseMessaging.getInstance() }
    single { SignInService(get(), get()) }
    single { (mainActivityContext: Context) ->
        SignInObserver(mainActivityContext, get())
    }

}