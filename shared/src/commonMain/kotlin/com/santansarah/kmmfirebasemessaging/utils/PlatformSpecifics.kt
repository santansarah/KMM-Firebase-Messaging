package com.santansarah.kmmfirebasemessaging.utils

import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

interface Dispatcher {
    val io: CoroutineDispatcher
}

internal expect fun provideDispatcher(): Dispatcher
internal expect fun getViewModelByPlatform(): Module
