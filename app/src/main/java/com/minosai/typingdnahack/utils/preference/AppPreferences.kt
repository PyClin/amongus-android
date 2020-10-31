package com.minosai.typingdnahack.utils.preference

import android.content.SharedPreferences

class AppPreferences(private val sharedPreferences: SharedPreferences) {

    private companion object Key {
        const val PREF_ACCESS_TOKEN = "ACCESS_TOKEN"
        const val PREF_REFRESH_TOKEN = "REFRESH_TOKEN"
    }

    fun resetCache() = sharedPreferences.edit().clear().apply()

    var accessToken: String? by sharedPreferences.string(PREF_ACCESS_TOKEN)
    var refreshToken: String? by sharedPreferences.string(PREF_REFRESH_TOKEN)
}