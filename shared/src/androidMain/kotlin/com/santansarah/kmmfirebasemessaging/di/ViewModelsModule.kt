package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual fun getViewModelByPlatform() = module {
    viewModel {
        HomeViewModel(get(), get(), provideDispatcher())
    }
}
