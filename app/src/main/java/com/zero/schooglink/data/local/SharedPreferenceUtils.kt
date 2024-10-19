package com.zero.schooglink.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

private val defaultPrefKeyEncryptionScheme: EncryptedSharedPreferences.PrefKeyEncryptionScheme =
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
private val defaultPrefValueEncryptionScheme: EncryptedSharedPreferences.PrefValueEncryptionScheme =
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

/**
 * Helper class for Encrypted Shared Prefs to store simple Key-value pair data locally
 * This is then used by SharedPrefImpl which are then used in higher-levels of code
 */
class SharedPreferenceUtils(
    fileName: String = "Schooglink",
    applicationContext: Context,
    prefKeyEncryptionScheme: EncryptedSharedPreferences.PrefKeyEncryptionScheme = defaultPrefKeyEncryptionScheme,
    prefValueEncryptionScheme: EncryptedSharedPreferences.PrefValueEncryptionScheme = defaultPrefValueEncryptionScheme
) {
    private var masterKey = MasterKey.Builder(
        applicationContext,
        MasterKey.DEFAULT_MASTER_KEY_ALIAS
    ).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        fileName, masterKey, prefKeyEncryptionScheme, prefValueEncryptionScheme
    )

    fun containsKey(key: String): Boolean = sharedPreferences.contains(key)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean = sharedPreferences.getBoolean(key, defaultValue)
    fun getInt(key: String): Int = sharedPreferences.getInt(key, 0)
    fun getFloat(key: String): Float = sharedPreferences.getFloat(key, 0F)
    fun getLong(key: String): Long = sharedPreferences.getLong(key, 0L)
    fun getString(key: String): String = sharedPreferences.getString(key, "") ?: ""

    fun saveBoolean(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()
    fun saveInt(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).apply()
    fun saveLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()
    fun saveFloat(key: String, value: Float) = sharedPreferences.edit().putFloat(key, value).apply()
    fun saveString(key: String, value: String) = sharedPreferences.edit().putString(key, value).apply()

    fun deleteValue(key: String) = sharedPreferences.edit().remove(key).apply()

    fun clear() = sharedPreferences.edit().clear().apply()

}