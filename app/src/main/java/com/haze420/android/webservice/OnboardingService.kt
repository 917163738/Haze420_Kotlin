package com.haze420.android.webservice

import com.haze420.android.model.apimodel.*
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OnboardingService {
    @POST("user/login")
    fun login(@Body request: LoginRequest): Deferred<LoginResponse>

    @POST("user/register")
    fun register(@Body request: SignupRequest): Deferred<CommonResponse>

    @POST("user/forgot-password")
    fun forgotPwd(@Body request: ForgotRequest): Deferred<CommonResponse>

}