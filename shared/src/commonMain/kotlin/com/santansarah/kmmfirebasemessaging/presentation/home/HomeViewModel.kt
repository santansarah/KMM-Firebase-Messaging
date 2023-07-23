package com.santansarah.kmmfirebasemessaging.presentation.home

import co.touchlab.kermit.Logger
import com.santansarah.kmmfirebasemessaging.data.local.AppPreferencesRepository
import com.santansarah.kmmfirebasemessaging.data.remote.StoreApiService
import com.santansarah.kmmfirebasemessaging.utils.Dispatcher
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUIState(
    val isOnboardingComplete: Boolean = false,
    val currentOnboardingScreen: Int = 0,
    val products: ServiceResult = ServiceResult.Empty
)

class HomeViewModel(
    private val storeApiService: StoreApiService,
    private val appPreferencesRepository: AppPreferencesRepository,
    private val dispatcher: Dispatcher
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeUIState())
    val homeUIState = _homeState.asStateFlow().cStateFlow()

    private val _appPreferences = appPreferencesRepository.userPreferencesFlow

    init {
        viewModelScope.launch(dispatcher.io) {
            _appPreferences.collect {appPrefs ->
                _homeState.update { homeUiState ->
                    Logger.d { "AppPrefs: $appPrefs" }
                    val currentScreen = if (appPrefs.lastOnboardingScreen == 0)
                        1 else appPrefs.lastOnboardingScreen

                    homeUiState.copy(
                        isOnboardingComplete = appPrefs.isOnboardingComplete,
                        currentOnboardingScreen = currentScreen
                    ).also {
                        if (it.isOnboardingComplete &&
                            it.products !is ServiceResult.Success<*>
                        )
                            getAllProducts()
                    }
                }
            }
        }
    }

    private fun getAllProducts() {

        _homeState.update {
            it.copy(products = ServiceResult.Loading)
        }

        viewModelScope.launch(dispatcher.io) {
            _homeState.update {
                it.copy(products = storeApiService.getAllProducts())
            }
        }
    }

    fun onOnboardingScreenUpdated(currentScreen: Int) {
        viewModelScope.launch(dispatcher.io) {
            appPreferencesRepository.setLastOnboardingScreen(currentScreen)
        }
    }

}