package com.haze420.android.webservice

import com.haze420.android.model.apimodel.LoginRequest
import com.haze420.android.model.apimodel.LoginResponse
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface HomeService {
    @POST("user/login")
    fun login(@Body request: LoginRequest): Deferred<LoginResponse>
}