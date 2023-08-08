package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.data.local.UserRepository
import com.santansarah.kmmfirebasemessaging.data.remote.StoreApiService
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module


val commonModule = module {
    single { StoreApiService() }
    single { Firebase.firestore }
    single { Firebase.auth }
    single { UserRepository(get(), get()) }
}