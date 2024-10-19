package com.zero.schooglink.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zero.schooglink.base.network.NetworkClient
import com.zero.schooglink.data.local.SharedPrefDelegate
import com.zero.schooglink.data.remote.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt Module-wise injection logic
 * Following classes in the definition can be injected from within
 * @see Api
 * @see Retrofit
 */
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(sharedPrefDelegate: SharedPrefDelegate): Retrofit {
        //If need you can add network client helper by providing parameter
        val networkClient = NetworkClient.SafeApiClient(
            60L,
            60L,
            sharedPrefDelegate
        )
        return Retrofit.Builder()
            .baseUrl("https://api.inopenapp.com/api/") //BuildConfig.USER_BASE_URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(networkClient.getOkHttpClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : Api {
        return retrofit.create(Api::class.java)
    }
}