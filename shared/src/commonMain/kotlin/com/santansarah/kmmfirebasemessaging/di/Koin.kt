package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.utils.getViewModelByPlatform
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, getViewModelByPlatform())
}

// called by iOS etc
fun initKoin() = initKoin{}