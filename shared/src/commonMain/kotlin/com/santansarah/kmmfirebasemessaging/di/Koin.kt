package com.santansarah.kmmfirebasemessaging.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, getViewModelByPlatform(), getDatastoreModuleByPlatform())
}

// called by iOS etc
fun initKoin() = initKoin{}