package com.santansarah.kmmfirebasemessaging.android.di

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.santansarah.kmmfirebasemessaging.android.SignInObserver
import com.santansarah.kmmfirebasemessaging.android.services.AppAnalyticsService
import com.santansarah.kmmfirebasemessaging.android.services.SignInService
import org.koin.dsl.module

val androidAppModule = module {

    single { Firebase.analytics }
    single { AppAnalyticsService(get()) }
    single { FirebaseMessaging.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { AuthUI.getInstance() }
    single { SignInService(get(), get(), get(), get()) }
    single { (mainActivityContext: Context) ->
        SignInObserver(mainActivityContext, get())
    }

}