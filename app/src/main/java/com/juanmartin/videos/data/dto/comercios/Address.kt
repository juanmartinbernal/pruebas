package com.juanmartin.videos.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @Json(name = "city")
    val city: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "street")
    val street: String,
    //@Json(name = "zip")
    //val zip: Int
): Parcelable