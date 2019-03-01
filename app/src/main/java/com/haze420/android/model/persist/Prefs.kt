package com.haze420.android.model.persist

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val PREF_NAME = "Haze420_APP_PREFERENCES"
    private val KEY_TOKEN = "accessToken"
    val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)

    var token: String
        get() = prefs.getString(KEY_TOKEN, "")
        set(value) = prefs.edit().putString(KEY_TOKEN, value).apply()
}