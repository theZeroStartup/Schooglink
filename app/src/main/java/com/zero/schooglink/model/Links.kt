package com.zero.schooglink.model

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("title")
    val title: String,
    @SerializedName("created_at")
    val date: String,
    @SerializedName("total_clicks")
    val clicks: Int,
    @SerializedName("web_link")
    val link: String,
    @SerializedName("original_image")
    val image: String
)
