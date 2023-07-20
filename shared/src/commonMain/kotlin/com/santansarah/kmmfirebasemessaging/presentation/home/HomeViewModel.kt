package com.santansarah.kmmfirebasemessaging.presentation.home

import co.touchlab.kermit.Logger
import com.santansarah.kmmfirebasemessaging.data.remote.StoreApiService
import com.santansarah.kmmfirebasemessaging.data.remote.models.Product
import com.santansarah.kmmfirebasemessaging.utils.Dispatcher
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val storeApiService: StoreApiService,
    private val dispatcher: Dispatcher
): ViewModel() {

    private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())
    val products = _products.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {

        viewModelScope.launch(dispatcher.io) {
            when(val result = storeApiService.getAllProducts()) {
                is ServiceResult.Success -> _products.value = result.data
                is ServiceResult.Error -> Logger.d { "API Error from viewmodel" }
            }
        }

    }

}