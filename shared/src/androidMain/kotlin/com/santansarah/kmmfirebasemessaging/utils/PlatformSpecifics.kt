package com.santansarah.kmmfirebasemessaging.utils

import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal class AndroidDispatcher: Dispatcher{
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}

internal actual fun provideDispatcher(): Dispatcher = AndroidDispatcher()

actual fun getViewModelByPlatform() = module {
    viewModel {
        HomeViewModel(get(), provideDispatcher())
    }
}