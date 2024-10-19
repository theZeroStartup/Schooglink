package com.zero.schooglink.di

import com.zero.schooglink.data.remote.Api
import com.zero.schooglink.data.remote.NetworkCallsHelper
import com.zero.schooglink.data.remote.repository.NetworkCallsRepository
import com.zero.schooglink.data.remote.repository.NetworkCallsRepositoryImpl
import com.zero.schooglink.data.remote.usecase.DashboardUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Module-wise injection logic
 * Following classes in the definition can be injected from within
 * @see NetworkCallsHelper
 * @see NetworkCallsRepositoryImpl
 * @see DashboardUseCase
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkCallImpl(api: Api): NetworkCallsHelper {
        return NetworkCallsHelper(api)
    }

    @Provides
    @Singleton
    fun provideNetworkCallDelegate(networkCallImpl: NetworkCallsHelper): NetworkCallsRepository {
        return NetworkCallsRepositoryImpl(networkCallImpl)
    }

    @Provides
    @Singleton
    fun provideNetworkUseCase(repository: NetworkCallsRepository): DashboardUseCase {
        return DashboardUseCase(repository)
    }
}