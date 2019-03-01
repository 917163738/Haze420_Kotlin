package com.haze420.android.model.apimodel

data class LoginResponse(val success: Boolean,
                         val data: LoginResponseData?,
                         val error: ErrorModel?) {

}

data class LoginResponseData(val jwt_token: String?) {

}