package com.santansarah.kmmfirebasemessaging.data.remote

import co.touchlab.kermit.Logger
import co.touchlab.kermit.NoTagFormatter
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.santansarah.kmmfirebasemessaging.data.remote.models.Product
import com.santansarah.kmmfirebasemessaging.utils.ServiceResult
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class StoreApiService : KtorApi() {

    private val storeLogger = Logger(
        loggerConfigInit(platformLogWriter(NoTagFormatter)),
        "StoreApi"
    )

    companion object {
        const val PRODUCTS = "/products"
    }

    suspend fun getAllProducts(): ServiceResult {

        return try {
            val productResponse: List<Product> = client.get(BASE_URL + PRODUCTS).body()
            ServiceResult.Success(productResponse)
        } catch (apiError: Exception) {
            storeLogger.d { apiError.message.toString() }
            ServiceResult.Error("", apiError.message ?: "API Error")
        }
    }

    suspend fun getProductById(productId: Int): ServiceResult {

        return try {
            val productResponse: Product = client.get(BASE_URL) {
                url {
                    appendPathSegments("products", productId.toString())
                }
            }.body()
            ServiceResult.Success(productResponse)
        } catch (apiError: Exception) {
            storeLogger.d { apiError.message.toString() }
            ServiceResult.Error("", apiError.message ?: "API Error")
        }
    }
}
