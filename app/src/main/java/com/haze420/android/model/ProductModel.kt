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

/*
        {
            "id": 1388,
            "categories": [
                {
                    "id": 35,
                    "name": "Sativa",
                    "slug": "sativa"
                }
            ],
            "name": "Sour Diesel",
            "slug": "sour-diesel",
            "permalink": "https://www.haze420.co.uk/product/sour-diesel/",
            "description": "<p>Sour Diesel, sometimes called Sour D, is an invigorating sativa-dominant strain named after its pungent, diesel-like aroma. This fast-acting strain delivers energising, dreamy cerebral effects that have pushed Sour Diesel to its legendary status. Stress, pain, and depression fade away in long-lasting relief that makes Sour Diesel a top choice among medical patients. This strain took root in the early 90's, and it is believed to have descended from Chemdawg 91 and Super Skunk.</p>\n",
            "thumbnail_image": "https://www.haze420.co.uk/wp-content/uploads/2018/12/sour-diesel__primary_9bc6-100x100.jpg",
            "images": [
                {
                    "thumbnail": "https://www.haze420.co.uk/wp-content/uploads/2018/12/sour-diesel__primary_9bc6-500x500.jpg",
                    "gallery_thumbnail": "https://www.haze420.co.uk/wp-content/uploads/2018/12/sour-diesel__primary_9bc6-100x100.jpg"
                },
                {
                    "thumbnail": "https://www.haze420.co.uk/wp-content/uploads/2018/12/sour-diesel__primary_fb56-500x500.jpg",
                    "gallery_thumbnail": "https://www.haze420.co.uk/wp-content/uploads/2018/12/sour-diesel__primary_fb56-100x100.jpg"
                },
                {
                    "thumbnail": "https://www.haze420.co.uk/wp-content/uploads/2018/12/Sour-Diesel-500x500.png",
                    "gallery_thumbnail": "https://www.haze420.co.uk/wp-content/uploads/2018/12/Sour-Diesel-100x100.png"
                }
            ],
            "weight": "",
            "price": "11.25",
            "regular_price": "",
            "sale_price": "",
            "average_rating": "4.69",
            "rating_count": 13,
            "attributes": {
                "weight": [
                    "1G",
                    "3G",
                    "7G",
                    "14G",
                    "28G"
                ],
                "thc_level": [
                    "Level2"
                ]
            }
        }
 */