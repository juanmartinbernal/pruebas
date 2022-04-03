package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class V1(
    //@Json(name = "authCards")
    //val authCards: List<Any>,
    @Json(name = "id")
    val id: Int? = 0,
    @Json(name = "key")
    val key: String = "",
    //@Json(name = "signatures")
    //val signatures: Signatures
) : Parcelable