package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import com.santansarah.kmmfirebasemessaging.presentation.home.LoginViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

actual fun getViewModelByPlatform() = module {
    single {
        HomeViewModel(get(), get(), provideDispatcher())
    }
    single {
        LoginViewModel()
    }
}

object GetViewModels: KoinComponent {
    fun getHomeViewModel() = get<HomeViewModel>()
    fun getLoginViewModel() = get<LoginViewModel>()
}

