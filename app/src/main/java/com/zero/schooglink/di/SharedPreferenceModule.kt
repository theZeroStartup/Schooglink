package com.zero.schooglink.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.zero.schooglink.data.local.SharedPrefDelegate
import com.zero.schooglink.data.local.SharedPrefImpl
import com.zero.schooglink.data.local.SharedPreferenceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Module-wise injection logic
 * Following classes in the definition can be injected from within
 * @see SharedPreferences
 * @see SharedPreferenceUtils
 * @see SharedPrefImpl
 */
@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceModule {

    //If you need normal shared pref
    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences{
        return app.getSharedPreferences("Shared_Pref", MODE_PRIVATE)
    }

    //If you need encrypted shared pref
    @Provides
    @Singleton
    fun provideSharedPreferenceUtils(app: Application): SharedPreferenceUtils {
        return SharedPreferenceUtils(applicationContext = app)
    }

    @Provides
    @Singleton
    fun provideSharedPrefImpl(sharedPreferenceUtils: SharedPreferenceUtils): SharedPrefDelegate {
        return SharedPrefImpl(sharedPreferenceUtils)
    }
}