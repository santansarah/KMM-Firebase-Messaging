package com.santansarah.kmmfirebasemessaging.android.di

import android.content.Context
import androidx.activity.ComponentActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.santansarah.kmmfirebasemessaging.android.SignInObserver
import com.santansarah.kmmfirebasemessaging.android.services.AppAnalyticsService
import com.santansarah.kmmfirebasemessaging.android.services.NotificationService
import com.santansarah.kmmfirebasemessaging.android.services.PermissionManager
import com.santansarah.kmmfirebasemessaging.android.services.SignInService
import com.santansarah.kmmfirebasemessaging.di.provideDispatcher
import com.santansarah.kmmfirebasemessaging.domain.INotificationService
import com.santansarah.kmmfirebasemessaging.domain.InsertUpdateOrder
import com.santansarah.kmmfirebasemessaging.presentation.details.DetailsViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidAppModule = module {

    single { Firebase.analytics }
    single { AppAnalyticsService(get()) }
    single { FirebaseMessaging.getInstance() }
    single { AuthUI.getInstance() }
    single { SignInService(get(), get(), get()) }
    factory { (mainActivityContext: Context) ->
        SignInObserver(mainActivityContext, get(), get())
    }
    factory { PermissionManager(get(), CoroutineScope(provideDispatcher().io)) }
    single<INotificationService> { NotificationService(androidApplication()) }
    single { InsertUpdateOrder(get(), get(), get()) }
    viewModel { (productId: Int) ->
        DetailsViewModel(get(), provideDispatcher(), productId, get())
    }
}