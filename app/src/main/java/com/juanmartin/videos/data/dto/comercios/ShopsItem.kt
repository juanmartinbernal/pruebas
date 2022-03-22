package com.juanmartin.videos.data.dto.comercios

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class ShopsItem(
    val __v: Int,
    val accountManager: AccountManager,
    val active: Boolean,
    val address: Address,
    val category: String,
    val clonedFrom: String,
    val config: Config,
    val contact: Contact,
    val contractId: String,
    val contractId_ornot: String,
    val currency: String,
    val deliveroo: Deliveroo,
    val description: String,
    val features: List<Any>,
    val franchiseId: String,
    val franchiseId_old: String,
    val franchises: List<Any>,
    val id: String,
    val ipad: Boolean,
    val ipadPhotos: IpadPhotos,
    val ipadVideo: IpadVideo,
    val latitude: Double,
    val locale: String,
    val logo: Logo?,
    val longitude: Double,
    val minLegalAge: Int,
    val moved: Boolean,
    val name: String,
    val oldContractId: String,
    val oldFranchiseId: String,
    val oldOwnerId: String,
    val oldYoin: Boolean,
    val openingHours: String,
    val ownerId: String,
    val photos: List<Photo>,
    val pointsGroupId: String,
    val polar: Boolean,
    val pos: Boolean,
    val salesPerson: SalesPerson,
    val shortDescription: String?,
    val slug: String,
    val social: Social,
    val startDate: String,
    val stuart: Stuart,
    val surveyRequired: Boolean,
    val tags: List<Any>,
    val timezone: String,
    //val v1: V1,
    val vromo: Vromo,
    val wayletCommerceId: String,
    val whiteLabel: Boolean,
    val whiteLabelGroupId: List<Any>
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }
}