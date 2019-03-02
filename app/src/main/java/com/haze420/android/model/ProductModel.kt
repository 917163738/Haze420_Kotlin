package com.haze420.android.model

import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.enums.THC
import android.os.Parcelable
import android.os.Parcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    val id: Int,
    val name: String,
    val permalink: String = "",
    val description: String = "",
    val categories: List<Categories> = ArrayList<Categories>(),
    val thumbnail_image : String = "",
    val images: List<Images> = ArrayList<Images>(),
    val price: String = "",
    val regular_price: String = "",
    val sale_price: String = "",
    val average_rating: String = "",
    val rating_count: Int = 0,
    val attributes: Attributs? = null
): Parcelable {
    var category: CATEGORY = CATEGORY.Hybrid
    get() {
        if (categories.size > 0 && categories.get(0).name != null){
            when (categories.get(0).name!!){
                CATEGORY.Hybrid.dispName -> return CATEGORY.Hybrid
                CATEGORY.Sativa.dispName -> return CATEGORY.Sativa
                CATEGORY.Indica.dispName -> return CATEGORY.Indica
                else -> return CATEGORY.Sativa
            }
        }else{
            return CATEGORY.Sativa
        }
    }

    var THClevel: THC = THC.LV2
    get() {
        if (attributes != null && attributes.thc_level.size > 0){
            when (attributes.thc_level.get(0)!!){
                "Level1" -> return THC.LV1
                "Level2" -> return THC.LV2
                "Level3" -> return THC.LV3
                else -> return THC.LV2
            }
        }else{
            return THC.LV2
        }
    }
}

@Parcelize
data class Categories(val id: Int,
                        val name: String?,
                        val slug: String?
): Parcelable{}

@Parcelize
data class Images(val thumbnail: String?,
                  val gallery_thumbnail: String?
): Parcelable{}

@Parcelize
data class Attributs(val weight: List<String>,
                  val thc_level: List<String>
): Parcelable{}