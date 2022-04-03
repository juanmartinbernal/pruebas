package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stuart(
    @Json(name = "active")
    val active: Boolean = false,
    @Json(name = "apiKey")
    val apiKey: String? = "",
    @Json(name = "apiSecret")
    val apiSecret: String? = "",
    @Json(name = "customErrorManagement")
    val customErrorManagement: Boolean = false
) : Parcelable