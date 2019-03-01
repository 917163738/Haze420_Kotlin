package com.haze420.android.model.apimodel

data class SignupRequest(val email: String,
                         val password: String,
                         val phone: String,
                         val birthday: String) {
}