package com.santansarah.kmmfirebasemessaging.utils

/**
 * A generic class that holds a value or error.
 * @param <T>
 */
sealed interface ServiceResult {
    object Loading: ServiceResult
    object Empty: ServiceResult
    data class Success<out T>(val data: T) : ServiceResult
    data class Error(val code: String, val message: String) : ServiceResult
}