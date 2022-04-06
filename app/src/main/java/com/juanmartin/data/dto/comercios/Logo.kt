package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Logo(
    @Json(name = "format")
    val format: String? = "",
    @Json(name = "thumbnails")
    val thumbnails: Thumbnails,
    @Json(name = "url")
    val url: String? = ""
) : Parcelable