package com.haze420.android.model

import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.enums.THC

data class OrderItem(
    val prductId: String,
    val isSale: Boolean = false,
    val name: String = "AK-27",
    val category: CATEGORY = CATEGORY.Hybrid,
    val weights: List<String> = listOf("1G", "3G", "14G", "128G"),
    val THClevel: THC = THC.LV2,
    val oldPrice: String = "15.00",
    val price: String = "10.00",
    val avg_rating: String = "4.0",
    val description: String = "Pineapple Exptress combines the potent and flavorful forces of parent strains Trainwreck and Hawaiian. The smell is likedned to fred apple and mango, with a taste of pineapple, pine, and cedar. This hard-hitting sativa-dominant hybrid provides a long-lasting energetic buzz perfect for productive afternoons and creative excapes.",
    val review_count: Int = 25,
    val image_URL : String = "https://haze420.co.uk/wp-content/uploads/2018/12/girl-scout-cookies__primary_31a7.jpg",
    val thumb_URL: String = "https://haze420.co.uk/wp-content/uploads/2018/12/girl-scout-cookies__primary_31a7.jpg"
) {
    override fun toString() = name

}