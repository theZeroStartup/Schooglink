package com.zero.schooglink.model

import com.google.gson.annotations.SerializedName

//Model to retrieve data from API
data class Dashboard(
    @SerializedName("statusCode")
    val statusCode: Int? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("today_clicks")
    val todayClicks: Int? = null,
    @SerializedName("top_source")
    val topSource: String? = null,
    @SerializedName("top_location")
    val topLocation: String? = null,
    @SerializedName("data")
    val data: Data? = null
)

data class Data(
    @SerializedName("recent_links")
    val recentLinks: List<Links>? = null,
    @SerializedName("top_links")
    val topLinks: List<Links>? = null
)