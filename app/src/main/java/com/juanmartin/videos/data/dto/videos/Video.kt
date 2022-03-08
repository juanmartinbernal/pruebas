package com.juanmartin.videos.data.dto.videos

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class Video(
    @Json(name = "categoryId")
    val categoryId: Int = 0,
    @Json(name = "id")
    val id: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "thumb")
    val thumb: String = "",
    @Json(name = "videoUrl")
    val videoUrl: String = ""
) : Parcelable