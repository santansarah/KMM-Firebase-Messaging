package com.santansarah.kmmfirebasemessaging.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
import com.santansarah.kmmfirebasemessaging.data.local.dataStoreFileName
import com.santansarah.kmmfirebasemessaging.data.local.getDataStore
import com.santansarah.kmmfirebasemessaging.di.commonModule
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

internal class AndroidDispatcher : Dispatcher {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}

internal actual fun provideDispatcher(): Dispatcher = AndroidDispatcher()

actual fun getViewModelByPlatform() = module {
    viewModel {
        HomeViewModel(get(), provideDispatcher())
    }
}

actual fun getDatastoreModuleByPlatform() = module {

    single {
        getDataStore {
            androidContext().filesDir?.resolve(dataStoreFileName)?.absolutePath
                ?: throw Exception("Couldn't get Android Datastore context.")
        }
    }

    single { AppPreferencesRepository(get()) }

}
