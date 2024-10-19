package com.zero.schooglink.data.remote.usecase

import androidx.lifecycle.LiveData
import com.zero.schooglink.data.remote.repository.NetworkCallsRepository
import com.zero.schooglink.model.Dashboard

/**
 * Contains the API calls specific to the Dashboard
 * @param repository Contains all APIs out of which, only dashboard-related calls will be made from here
 */
class DashboardUseCase(private val repository: NetworkCallsRepository) {
    fun getDashboardDetails(): LiveData<Dashboard> {
        return repository.getDashboardDetails()
    }
}