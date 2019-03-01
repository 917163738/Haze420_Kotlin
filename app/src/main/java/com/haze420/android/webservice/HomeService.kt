package com.haze420.android.webservice

import com.haze420.android.model.apimodel.ProductListResponse
import kotlinx.coroutines.Deferred

import retrofit2.http.*

interface HomeService {

    @GET("product")
    fun loadProducts(@QueryMap params: Map<String, String>): Deferred<ProductListResponse>
}