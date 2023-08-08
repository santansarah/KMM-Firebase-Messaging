package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.data.local.UserRepository
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

actual fun getViewModelByPlatform() = module {
    single {
        HomeViewModel(get(), get(), provideDispatcher())
    }
}

object KoinHelper: KoinComponent {
    fun getHomeViewModel() = get<HomeViewModel>()
    fun getUserRepo() = get<UserRepository>()
}

