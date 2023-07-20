package com.santansarah.kmmfirebasemessaging.data.remote

import co.touchlab.kermit.Logger
import co.touchlab.kermit.NoTagFormatter
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.santansarah.kmmfirebasemessaging.data.remote.models.Product
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import io.ktor.client.call.body
import io.ktor.client.request.get

class StoreApiService : KtorApi() {

    private val storeLogger = Logger(
        loggerConfigInit(platformLogWriter(NoTagFormatter)),
        "StoreApi"
    )


    companion object {
        const val PRODUCTS = "$BASE_URL/products"
    }

    suspend fun getAllProducts(): ServiceResult<List<Product>> {

        return try {
            val productResponse: List<Product> = client.get(PRODUCTS).body()
            ServiceResult.Success(productResponse)
        } catch (apiError: Exception) {
            storeLogger.d { apiError.message.toString() }
            ServiceResult.Error("", apiError.message ?: "API Error")
        }
    }
}
