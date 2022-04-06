package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Signatures(
    @Json(name = "apiKey")
    val apiKey: String,
    @Json(name = "signature")
    val signature: String
): Parcelable