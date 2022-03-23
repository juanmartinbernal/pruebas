package com.juanmartin.videos.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SalesPerson(
    @Json(name = "email")
    val email: String? = "",
    @Json(name = "name")
    val name: String ? = ""
) : Parcelable