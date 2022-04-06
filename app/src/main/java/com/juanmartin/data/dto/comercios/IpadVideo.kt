package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class IpadVideo(
    @Json(name = "format")
    val format: String? = "",
    @Json(name = "url")
    val url: String? = ""
) :  Parcelable