package com.zero.schooglink.data.local

/**
 * Local storage for simple key-value pair
 * @param sharedPrefs Util class that initializes Encrypted Shared Prefs & has other helper methods
 */
class SharedPrefImpl(private val sharedPrefs: SharedPreferenceUtils) : SharedPrefDelegate {

    companion object {
        private const val TOKEN = "token"
    }

    override fun saveAuthToken(token: String) {
        sharedPrefs.saveString(TOKEN, token)
    }
                                        //authSharedPrefs.getString(TOKEN)
    override val token: String get() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
            "eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
}