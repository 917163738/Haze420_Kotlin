package com.haze420.android.webservice

import com.haze420.android.model.apimodel.ProductListResponse
import com.haze420.android.model.apimodel.ReviewListResponse
import kotlinx.coroutines.Deferred

import retrofit2.http.*

interface HomeService {

    @GET("product")
    fun getProductList(@QueryMap params: Map<String, String>): Deferred<ProductListResponse>

    @GET("review")
    fun getReviewList(@QueryMap params: Map<String, String>): Deferred<ReviewListResponse>
}