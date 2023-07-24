package com.santansarah.kmmfirebasemessaging.di

import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
import com.santansarah.kmmfirebasemessaging.data.local.dataStoreFileName
import com.santansarah.kmmfirebasemessaging.data.local.getDataStore
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


actual fun getDatastoreModuleByPlatform() = module {

    single {
        getDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$dataStoreFileName"
        }
    }

    single { AppPreferencesRepository(get()) }

}