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

/*
        {
            "id": 51,
            "product_id": 1388,
            "date_created": "2019-02-21T23:12:37",
            "status": "approved",
            "reviewer": "",
            "reviewer_email": "testaccount3@gmail.com",
            "review": "<p>Niceas12312315464456456 5623!</p>\n",
            "rating": 5,
            "reviewer_avatar_urls": {
                "24": "https://secure.gravatar.com/avatar/de7ce1e196d811a19588d3d3fcf583d4?s=24&d=mm&r=g",
                "48": "https://secure.gravatar.com/avatar/de7ce1e196d811a19588d3d3fcf583d4?s=48&d=mm&r=g",
                "96": "https://secure.gravatar.com/avatar/de7ce1e196d811a19588d3d3fcf583d4?s=96&d=mm&r=g"
            }
        }
 */
