package com.zero.schooglink.base.network

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.io.IOException

const val API_CALL_SUCCESS = 200

/**
 * A helper suspend function to safely make an API call that handles the result as well
 * It uses coroutines for asynchronous execution.
 * @param dispatcher Specifies the coroutine dispatcher (default is IO for network operations)
 * @param apiCall The API call to be executed, passed as a lambda function
 * @return ApiResult class with either the subtype Success or Error based on null-check & other conditions
 */
suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Call<T>
) : ApiResult<T & Any> {
    // Run the API call within the provided dispatcher and switch the context accordingly
    return withContext(dispatcher) {
        try {
            val response = apiCall().execute()
            Log.d("TAG", "safeApiCall: ${apiCall().request()}")

            val responseBody = response.body()
            Log.d("TAG", "safeApiCall: ${response.code()} ${responseBody.toString()}")

            if (response.code() == API_CALL_SUCCESS && responseBody != null){
                ApiResult.Success(body = responseBody)
            }else{
                ApiResult.Error(message = response.message())
            }
        }catch (e: IOException){
            Log.d("NETWORK", "Exception : " + e.message)
            ApiResult.Error(message = e.message)
        }catch (e: Exception){
            Log.d("NETWORK", "Exception : " + e.message)
            ApiResult.Error(message = e.message)
        }
    }
}