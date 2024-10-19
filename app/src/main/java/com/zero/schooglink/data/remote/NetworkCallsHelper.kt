package com.zero.schooglink.data.remote

import com.zero.schooglink.base.network.ApiResult
import com.zero.schooglink.base.network.safeApiCall
import com.zero.schooglink.model.Dashboard
import kotlinx.coroutines.Dispatchers

/**
 * Contains executions of all the Apis and categorizes it as Success/Error
 * @see safeApiCall is another helper class which makes the call and validates the http response
 * @param api is injected here which contains all the end-points
 */
open class NetworkCallsHelper(private val api: Api) {

    open suspend fun getDashboardDetails(): Dashboard {
        val result = safeApiCall(Dispatchers.IO) {
            api.getDashboardDetails()
        }
        return when(result){
            is ApiResult.Success -> {
                result.body
            }
            is ApiResult.Error -> {
                val error = Dashboard()
                error.message = result.message
                error
            }
        }
    }
}