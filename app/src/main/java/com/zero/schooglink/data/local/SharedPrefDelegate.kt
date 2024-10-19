package com.zero.schooglink.data.local

interface SharedPrefDelegate {

    fun saveAuthToken(token: String)

    val token: String
}