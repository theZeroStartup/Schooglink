package com.zero.schooglink.base.network

/**
 * Wrapper class for passing network calls back to higher levels of the application.
 * All network responses will be wrapped in a ApiResult
 */
sealed class ApiResult <out T> {

    data class Success<out T>(val body: T) : ApiResult<T>()

    data class Error(val message: String?) : ApiResult<Nothing>()
}