package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    @Json(name = "email")
    val email: String? = "",
    @Json(name = "phone")
    val phone: String? = "",
    @Json(name = "web")
    val web: String? = ""
): Parcelable