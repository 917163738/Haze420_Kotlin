package com.haze420.android.model.apimodel

data class ErrorResponse(val success: Boolean,
                         val error: ErrorModel?) {

}

data class ErrorModel(val code:Int?, val message: String?) {

}