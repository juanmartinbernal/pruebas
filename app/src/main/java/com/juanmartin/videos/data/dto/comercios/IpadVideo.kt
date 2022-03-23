package com.juanmartin.videos.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IpadVideo(
    @Json(name = "format")
    val format: String? = "",
    @Json(name = "url")
    val url: String? = ""
) :  Parcelable