package com.santansarah.kmmfirebasemessaging.utils

import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

internal class IosDispatcher: Dispatcher{
    override val io: CoroutineDispatcher
        get() = Dispatchers.Default
}

internal actual fun provideDispatcher(): Dispatcher = IosDispatcher()

actual fun getViewModelByPlatform() = module {
    single {
        HomeViewModel(get(), provideDispatcher())
    }
}

object GetViewModels: KoinComponent {
    fun getHomeViewModel() = get<HomeViewModel>()
}
