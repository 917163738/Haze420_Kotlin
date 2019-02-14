package com.haze420.android.model

import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.enums.THC
import android.os.Parcelable
import android.os.Parcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val prductId: String,
    val isSale: Boolean = false,
    val name: String = "AK-27",
    val category: CATEGORY = CATEGORY.Hybrid,
    val weights: List<String> = listOf("1G", "3G", "14G", "128G"),
    val THClevel: THC = THC.LV2,
    val oldPrice: String = "15.00",
    val price: String = "10.00",
    val avg_rating: String = "4.0",
    val description: String = "<p>Gorilla Glue #4 (also known as GG4) created by GG strains is a Sativa-dominant hybrid, a multiple award-winning strain, famous for its high THC content. It averages 18 to 25 percent THC, but you may find it as high as 32 percent at some dispensaries. The CBD is very low, ranging between 0.05 and 0.1 percent.</p>\n<p>This strain gets its name because of the resin that collects on the scissors when trimming. Gorilla Glue #4 has several parents due to extensive backcrossing – Sour Diesel, Sour Dubb, Chem’s Sister, and Chocolate Diesel.</p>\n<p>The hybrid strain has won awards at the 2014 Cannabis Cup in both Los Angeles and Michigan. It has been described as a bunch of trichromes with a few buds, leaves, and stalks sticking out.</p>\n<p>The aroma is very strong and is mostly diesel with notes of chocolate and coffee, although sweet and earthy is also used to describe it. You’ll be going back for seconds due to the full-bodied and flavorful smoke. The buds are green with orange hairs. It is very dense, spongy, resinous, and covered in trichromes.</p>\n<p>Gorilla Glue #4 makes you chill, medical and recreational users, both. It gives a full body melt, despite the Sativa-dominance. The body effect may be a result of the THC/CBD ratio, the terpene profile, and/or the synergistic effects of the cannabinoids. Along with the body buzz, expect uplifting, cerebral effects. Not exactly a strain to use in the morning, Gorilla Glue #4 is more suited to a day off from work or to end the day.</p>\n<p>Recreational users will find that the buzz goes straight to the head. The effect is heavy-handed as this is a couch-locking strain. You will feel the effect immediately and it will “glue” you to the couch and deliver relaxation and euphoria.</p>\n",
    val review_count: Int = 25,
    val image_URL : String = "https://haze420.co.uk/wp-content/uploads/2018/12/girl-scout-cookies__primary_31a7-100x100.jpg",
    val thumb_URL: String = "https://haze420.co.uk/wp-content/uploads/2018/12/girl-scout-cookies__primary_31a7-100x100.jpg"
): Parcelable {
    override fun toString() = name

}