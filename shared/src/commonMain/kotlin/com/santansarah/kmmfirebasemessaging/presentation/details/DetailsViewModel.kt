package com.santansarah.kmmfirebasemessaging.presentation.details

import com.santansarah.kmmfirebasemessaging.data.remote.StoreApiService
import com.santansarah.kmmfirebasemessaging.data.remote.models.Product
import com.santansarah.kmmfirebasemessaging.di.Dispatcher
import com.santansarah.kmmfirebasemessaging.domain.InsertUpdateOrder
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

data class DetailsUIState(
    val selectedProduct: ServiceResult = ServiceResult.Empty
)

class DetailsViewModel(
    private val storeApiService: StoreApiService,
    private val dispatcher: Dispatcher,
    private val productId: Int,
    private val insertUpdateOrder: InsertUpdateOrder
) : ViewModel(), KoinComponent {

    private val _detailsState = MutableStateFlow(DetailsUIState())
    val detailsState = _detailsState.asStateFlow().cStateFlow()

    init {
        getProductById()
    }

    private fun getProductById() {

        _detailsState.update {
            it.copy(selectedProduct = ServiceResult.Loading)
        }

        viewModelScope.launch(dispatcher.io) {
            _detailsState.update {
                it.copy(selectedProduct = storeApiService.getProductById(productId))
            }
        }
    }

    fun insertOrUpdateOrder(orderId: String, isShipped: Boolean) {

        val productId = ((_detailsState.value.selectedProduct
                as ServiceResult.Success<*>).data as Product).id

        viewModelScope.launch {
            insertUpdateOrder.invoke(orderId, productId, isShipped)
        }
    }

}