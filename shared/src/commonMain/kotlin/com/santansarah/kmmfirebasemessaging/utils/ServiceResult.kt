package com.santansarah.kmmfirebasemessaging.utils

/**
 * A generic class that holds a value or error.
 * @param <T>
 */
sealed class ServiceResult<out T> {
    data class Success<out T>(val data: T) : ServiceResult<T>()
    data class Error(val code: String, val message: String) : ServiceResult<Nothing>()
}

/**
 * `true` if [ServiceResult] is [ServiceResult.Success]
 */
val ServiceResult<*>.succeeded
    get() = this is ServiceResult.Success
