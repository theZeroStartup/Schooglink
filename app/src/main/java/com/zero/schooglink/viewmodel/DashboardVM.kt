package com.zero.schooglink.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.zero.schooglink.base.viewmodel.BaseViewModel
import com.zero.schooglink.base.viewmodel.SingleLiveEvent
import com.zero.schooglink.data.local.SharedPrefDelegate
import com.zero.schooglink.data.remote.usecase.DashboardUseCase
import com.zero.schooglink.model.Dashboard
import com.zero.schooglink.model.Links
import com.zero.schooglink.view.adapter.LinksAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

/**
 * Hilt view model which is injected in the UI
 * Additionally, dashboard use-case and local db are injected to access data
 */
@HiltViewModel
class DashboardVM @Inject constructor(app: Application, private val dashboardUseCase: DashboardUseCase,
                                      private val localDataSource: SharedPrefDelegate) : BaseViewModel(app) {

    companion object {
        //Types of actions/clicks performed in recyclerview
        const val TYPE_COPY = "copy"
        const val TYPE_OPEN_DETAILS = "details"
    }

    private var linksAdapter: LinksAdapter? = null

    //Contains filtered list of links based on search query
    val filteredLinks = SingleLiveEvent<List<Links>?>()
    val selectedLink = SingleLiveEvent<Links>()
    val copyLink = SingleLiveEvent<String>()

    //Toggle this to 'true' to make the API call
    val requestDashboardDetails = SingleLiveEvent<Boolean>()

    //Contains the api result data
    val dashboardDetailsResponse: LiveData<Dashboard> = requestDashboardDetails.switchMap {
        dashboardUseCase.getDashboardDetails()
    }

    //Contains only recent links data
    private val _recentLinksData = MutableLiveData<List<Links>>()
    val recentLinksData: LiveData<List<Links>> get() = _recentLinksData

    fun setRecentLinksData(value: List<Links>) {
        _recentLinksData.value = value
    }
    //Contains only top links data
    private val _topLinksData = MutableLiveData<List<Links>>()
    val topLinksData: LiveData<List<Links>> get() = _topLinksData

    fun setTopLinksData(value: List<Links>) {
        _topLinksData.value = value
    }

    /**
     * Show greetings based on time of the day
     * @return Personalized greeting message for the user
     */
    fun getGreetingMessage(): String {
        // Get the current hour of the day
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        // Determine the greeting based on the hour
        val greeting = when (hour) {
            in 0..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else -> "Good Evening"
        }

        return greeting
    }

    /**
     * Perform search in recyclerview and notify ui if match found. Otherwise, display all data
     * @param query The search regex from ui
     * @param links The list of all data on which filtering will be performed
     */
    fun filter(query: String?, links: List<Links>?) {
        val filteredListDeferred = viewModelScope.async(Dispatchers.IO) {
            if (!query.isNullOrEmpty()) {
                links?.filter { it.title.contains(query, ignoreCase = true) }
            } else {
                links
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            val filteredList = filteredListDeferred.await()
            if (filteredList != null) { //Notify UI about the filtered data
                filteredLinks.postValue(filteredList)
            }
        }
    }

    //Initialize the adapter for recycler view to display the retrieved link details
    fun getLinksAdapter(context: Context, links: List<Links>? = null): LinksAdapter {
        return linksAdapter ?: LinksAdapter(context, links ?: mutableListOf()) { type, link ->
            if (type == TYPE_COPY) copyLink.postValue(link.link)
            else if (type == TYPE_OPEN_DETAILS) selectedLink.postValue(link)
        }.also { linksAdapter = it }
    }
}