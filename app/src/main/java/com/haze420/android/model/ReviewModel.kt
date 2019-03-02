package com.haze420.android.model


import org.json.JSONObject

data class ReviewModel(
    val id: Int,
    val product_id: Int,
    val date_created: String,
    val status: String,
    val reviewer: String,
    val reviewer_email: String,
    val review: String,
    val rating: Int,
    val reviewer_avatar_urls: Map<String, String>
){

}
