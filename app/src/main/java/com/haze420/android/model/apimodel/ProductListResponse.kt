package com.haze420.android.model.apimodel

import com.haze420.android.model.ProductModel

data class ProductListResponse(val success: Boolean,
                         val data: List<ProductModel>,
                         val error: ErrorModel?) {

}