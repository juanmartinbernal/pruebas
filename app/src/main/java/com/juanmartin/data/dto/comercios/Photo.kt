package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    @Json(name = "format")
    val format: String,
    @Json(name = "_id")
    val id: String? = "",
    @Json(name = "thumbnails")
    val thumbnails: ThumbnailsX,
    @Json(name = "url")
    val url: String
) : Parcelable