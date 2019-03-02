package com.haze420.android.model.apimodel

import com.haze420.android.model.UserProfile

data class GetProfileResponse(val success: Boolean,
                         val data: UserProfile?,
                         val error: ErrorModel?) {

}