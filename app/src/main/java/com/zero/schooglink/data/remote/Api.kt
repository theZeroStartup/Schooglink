package com.zero.schooglink.data.remote

import com.zero.schooglink.model.Dashboard
import retrofit2.Call
import retrofit2.http.GET

/**
 * Contains all end-points
 */
interface Api {
    @GET("v1/dashboardNew")
    fun getDashboardDetails(): Call<Dashboard>
}