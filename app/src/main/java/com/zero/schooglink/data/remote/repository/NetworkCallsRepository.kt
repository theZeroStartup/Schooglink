package com.zero.schooglink.data.remote.repository

import androidx.lifecycle.LiveData
import com.zero.schooglink.model.Dashboard

/**
 * Contains all Api calls to be made within this module
 * @see NetworkCallsRepositoryImpl for implementation of this interface
 */
interface NetworkCallsRepository {
    fun getDashboardDetails(): LiveData<Dashboard>
}