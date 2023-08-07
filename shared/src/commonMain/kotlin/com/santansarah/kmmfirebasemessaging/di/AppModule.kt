package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.data.remote.StoreApiService
import org.koin.dsl.module


val commonModule = module {
    single { StoreApiService() }
    //single { Firebase.firestore }
    //single { UserRepository(get()) }
}