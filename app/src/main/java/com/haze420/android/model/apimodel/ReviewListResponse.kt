package com.haze420.android.model.apimodel

import com.haze420.android.model.ProductModel
import com.haze420.android.model.ReviewModel

data class ReviewListResponse(val success: Boolean,
                              val data: List<ReviewModel>,
                              val error: ErrorModel?) {

}