package com.santansarah.kmmfirebasemessaging.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
import com.santansarah.kmmfirebasemessaging.data.local.dataStoreFileName
import com.santansarah.kmmfirebasemessaging.data.local.getDataStore
import com.santansarah.kmmfirebasemessaging.presentation.home.HomeViewModel
import com.santansarah.kmmfirebasemessaging.presentation.home.LoginViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal class IosDispatcher: Dispatcher{
    override val io: CoroutineDispatcher
        get() = Dispatchers.Default
}

internal actual fun provideDispatcher(): Dispatcher = IosDispatcher()

actual fun getViewModelByPlatform() = module {
    single {
        HomeViewModel(get(), provideDispatcher())
    }
    single {
        LoginViewModel()
    }
}

object GetViewModels: KoinComponent {
    fun getHomeViewModel() = get<HomeViewModel>()
    fun getLoginViewModel() = get<LoginViewModel>()
}

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
