package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Config(
    @Json(name = "currency")
    val currency: String,
    @Json(name = "locale")
    val locale: String,
    @Json(name = "timezone")
    val timezone: String
) : Parcelable