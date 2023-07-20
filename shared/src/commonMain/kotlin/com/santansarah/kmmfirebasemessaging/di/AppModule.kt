package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.data.remote.StoreApiService
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module
import org.koin.dsl.module


val commonModule = module {
    single { StoreApiService() }
}