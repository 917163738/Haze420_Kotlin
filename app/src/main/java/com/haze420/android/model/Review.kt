package com.haze420.android.model

import com.haze420.android.model.enums.CATEGORY
import com.haze420.android.model.enums.THC
import android.os.Parcelable
import android.os.Parcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    val id: String,
    val content: String = "Like it!"
): Parcelable {


}