package com.haze420.android.model.persist

import android.content.Context
import android.content.SharedPreferences
import com.haze420.android.model.UserProfile
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Prefs(context: Context) {
    private val PREF_NAME = "Haze420_APP_PREFERENCES"
    private val KEY_TOKEN = "accessToken"
    private val KEY_EMAIL = "key_email"
    private val KEY_PASSWORD = "key_passwrod"
    private val KEY_USERPROFILE = "userProfile"
    val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)

    // Save and read token
    var token: String
        get() = prefs.getString(KEY_TOKEN, "")
        set(value) = prefs.edit().putString(KEY_TOKEN, value).apply()

    // Save and read email
    var email: String
        get() = prefs.getString(KEY_EMAIL, "")
        set(value) = prefs.edit().putString(KEY_EMAIL, value).apply()

    // Save and read password
    var password: String
        get() = prefs.getString(KEY_PASSWORD, "")
        set(value) = prefs.edit().putString(KEY_PASSWORD, value).apply()

    // Save and read user profile
    var userProfile: UserProfile?
    get() {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(UserProfile::class.java)
        val stringValue = prefs.getString(KEY_USERPROFILE, "")
        return jsonAdapter.fromJson(stringValue)
    }
    set(value) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(UserProfile::class.java)
        val stringValue = jsonAdapter.toJson(value)
        prefs.edit().putString(KEY_USERPROFILE, stringValue).apply()
    }



}