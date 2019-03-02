package com.haze420.android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    val ID: Int,
    val user_email: String?,
    val first_name: String?,
    val last_name: String?,
    val birthday: String?,
    val phone : String?,
    val avatar: String?,
    val billing_address: BillingAddress?,
    val deliveryAddress: DeliveryAddress?
): Parcelable {
}

@Parcelize
data class BillingAddress(
    val first_name: String?,
    val last_name: String?,
    val address_1: String?,
    val address_2: String?,
    val city: String?,
    val postcode: String?,
    val country: String?,
    val state: String?,
    val phone: String?
): Parcelable{}

@Parcelize
data class DeliveryAddress(
    val address_1: String?,
    val address_2: String?,
    val city: String?,
    val postcode: String?,
    val country: String?,
    val state: String?,
    val additional_instruction: String?
): Parcelable{}

/*
    {
        "ID": 93,
        "user_email": "testaccount3@gmail.com",
        "first_name": "Test Name",
        "last_name": "Test last name",
        "birthday": "19/10/1986",
        "phone": "+37498253510",
        "avatar": "https://secure.gravatar.com/avatar/de7ce1e196d811a19588d3d3fcf583d4?d=mm",
        "billing_address": {
            "first_name": "Billing FName",
            "last_name": "Billing Last name",
            "address_1": "Yerevan, Armenia",
            "address_2": "Jrvej, Kotayk",
            "city": "Yerevan",
            "postcode": "2227",
            "country": "Armenia",
            "state": "California",
            "phone": "+3333333333"
        },
        "delivery_address": {
            "address_1": "Yerevan, Armenia",
            "address_2": "Jrvej, Kotayk",
            "city": "Yerevan",
            "postcode": "2227",
            "country": "Armenia",
            "state": "California shipping",
            "additional_instruction": "bla bla bla"
        }
    }
     */
