package com.zero.schooglink.base.network

import com.zero.schooglink.data.local.SharedPrefDelegate
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

sealed class NetworkClient(
    private val connectionTimeoutSeconds: Long,
    private val readTimeoutSeconds: Long,
    sharedPrefDelegate: SharedPrefDelegate) {

    class SafeApiClient(connectionTimeoutSeconds: Long, readTimeoutSeconds: Long, sharedPrefDelegate: SharedPrefDelegate)
        : NetworkClient(connectionTimeoutSeconds, readTimeoutSeconds, sharedPrefDelegate)

    //Auth token retrieved from storage
    private val token = sharedPrefDelegate.token

    //Init Http client with desired timeout, interceptors, etc.
    private val httpClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(connectionTimeoutSeconds, TimeUnit.SECONDS)
            readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(loggingInterceptor)
            addInterceptor(networkInterceptor)
        }
    }

    //For capturing and logging HTTP request/response details. Helps debugging
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
        redactHeader("Authorization")
        redactHeader("Cache")
    }

    //To add dynamic headers (like Content-Type and Authorization) to outgoing requests, in runtime.
    private val networkInterceptor: Interceptor = Interceptor { chain ->
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.addHeader("Connection","close")
        requestBuilder.addHeader("Authorization", "Bearer $token")
        chain.proceed(requestBuilder.build())
    }

    fun getOkHttpClient(): OkHttpClient {
        return httpClientBuilder.build()
    }

}

