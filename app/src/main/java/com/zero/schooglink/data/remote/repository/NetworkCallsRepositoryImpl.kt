package com.zero.schooglink.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.zero.schooglink.data.remote.NetworkCallsHelper
import com.zero.schooglink.model.Dashboard

/**
 * Contains implementations of all the Api Calls in the Repository, made using helper class
 * @param networkCallsHelper is the helper class injected here to make safe Api calls that handles edge-cases
 */
class NetworkCallsRepositoryImpl(
    private val networkCallsHelper: NetworkCallsHelper
) : NetworkCallsRepository {

    override fun getDashboardDetails(): LiveData<Dashboard> = liveData {
        emit(networkCallsHelper.getDashboardDetails())
    }
}