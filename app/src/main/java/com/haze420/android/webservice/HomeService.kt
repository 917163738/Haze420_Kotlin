package com.haze420.android.webservice

import com.haze420.android.model.BillingAddress
import com.haze420.android.model.DeliveryAddress
import com.haze420.android.model.apimodel.*
import kotlinx.coroutines.Deferred

import retrofit2.http.*

interface HomeService {

    @GET("product")
    fun getProductList(@QueryMap params: Map<String, String>): Deferred<ProductListResponse>

    @GET("review")
    fun getReviewList(@QueryMap params: Map<String, String>): Deferred<ReviewListResponse>

    @GET("user")
    fun getProfile(): Deferred<GetProfileResponse>

    @POST("user/save")
    fun updateProfile(@Body request: ProfileRequest): Deferred<CommonResponse>

    @POST("user/save-delivery-address")
    fun updateDeliveryAddress(@Body request: DeliveryAddress): Deferred<CommonResponse>

    @POST("user/save-billing-address")
    fun updateBillingAddress(@Body request: BillingAddress): Deferred<CommonResponse>


}