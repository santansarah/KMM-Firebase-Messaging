package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
import com.santansarah.kmmfirebasemessaging.data.local.dataStoreFileName
import com.santansarah.kmmfirebasemessaging.data.local.getDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun getDatastoreModuleByPlatform() = module {

    single {
        getDataStore {
            androidContext().filesDir?.resolve(dataStoreFileName)?.absolutePath
                ?: throw Exception("Couldn't get Android Datastore context.")
        }
    }

    single { AppPreferencesRepository(get()) }

}