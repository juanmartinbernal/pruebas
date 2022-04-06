package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deliveroo(
    @Json(name = "active")
    val active: Boolean = false,
    @Json(name = "id")
    val id: String? = ""
) : Parcelable