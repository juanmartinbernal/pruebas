package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class SalesPerson(
    @Json(name = "email")
    val email: String? = "",
    @Json(name = "name")
    val name: String ? = ""
) : Parcelable