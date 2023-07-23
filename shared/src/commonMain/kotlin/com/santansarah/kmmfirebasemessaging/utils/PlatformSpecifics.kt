package com.santansarah.kmmfirebasemessaging.utils

import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

interface Dispatcher {
    val io: CoroutineDispatcher
}

internal expect fun provideDispatcher(): Dispatcher
internal expect fun getViewModelByPlatform(): Module
internal expect fun getDatastoreModuleByPlatform(): Module