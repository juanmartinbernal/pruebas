package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vromo(
    @Json(name = "active")
    val active: Boolean
):Parcelable