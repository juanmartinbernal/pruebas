package com.juanmartin.data.dto.comercios


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopsItem(
    @Json(name = "accountManager")
    val accountManager: AccountManager? = AccountManager("",""),
    @Json(name = "active")
    val active: Boolean? = false,
    @Json(name = "address")
    val address: Address? = Address("","",""),
    @Json(name = "category")
    val category: String? = "",
    @Json(name = "clonedFrom")
    val clonedFrom: String? = "",
    @Json(name = "config")
    val config: Config? = Config("","",""),
    @Json(name = "contact")
    val contact: Contact? = Contact("","",""),
    @Json(name = "contractId")
    val contractId: String? = "",
    @Json(name = "contractId_ornot")
    val contractIdOrnot: String? = "",
    @Json(name = "currency")
    val currency: String? = "",
    @Json(name = "deliveroo")
    val deliveroo: Deliveroo? = Deliveroo(false, ""),
    @Json(name = "description")
    val description: String? = "",
    //@Json(name = "features")
    //val features: List<Any>,
    @Json(name = "franchiseId")
    val franchiseId: String? = "",
    @Json(name = "franchiseId_old")
    val franchiseIdOld: String? = "",
    //@Json(name = "franchises")
    //val franchises: List<Any>,
    @Json(name = "id")
    val id: String? = "",
    @Json(name = "ipad")
    val ipad: Boolean? = false,
    //@Json(name = "ipadPhotos")
    //val ipadPhotos: IpadPhotos,
    @Json(name = "ipadVideo")
    val ipadVideo: IpadVideo? = IpadVideo("",""),
    @Json(name = "latitude")
    val latitude: Double? = 0.0,
    @Json(name = "locale")
    val locale: String? = "",
    @Json(name = "logo")
    val logo: Logo? = Logo("",Thumbnails("","",""),""),
    @Json(name = "longitude")
    val longitude: Double? = 0.0,
    @Json(name = "minLegalAge")
    val minLegalAge: Int? = 0,
    @Json(name = "moved")
    val moved: Boolean? = false,
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "oldContractId")
    val oldContractId: String? = "",
    @Json(name = "oldFranchiseId")
    val oldFranchiseId: String? = "",
    @Json(name = "oldOwnerId")
    val oldOwnerId: String? = "",
    @Json(name = "oldYoin")
    val oldYoin: Boolean? = false,
    @Json(name = "openingHours")
    val openingHours: String? = "",
    @Json(name = "ownerId")
    val ownerId: String? = "",
    @Json(name = "photos")
    val photos: List<Photo>? = listOf(),
    @Json(name = "pointsGroupId")
    val pointsGroupId: String? = "",
    @Json(name = "polar")
    val polar: Boolean? = false,
    @Json(name = "pos")
    val pos: Boolean? = false,
    @Json(name = "salesPerson")
    val salesPerson: SalesPerson? = SalesPerson("",""),
    @Json(name = "shortDescription")
    val shortDescription: String? = "",
    @Json(name = "slug")
    val slug: String? = "",
    //@Json(name = "social")
    //val social: Social,
    @Json(name = "startDate")
    val startDate: String? = "",
    @Json(name = "stuart")
    val stuart: Stuart? = Stuart(false, "","",false),
    @Json(name = "surveyRequired")
    val surveyRequired: Boolean? = false,
    ///@Json(name = "tags")
    //val tags: List<Any>,
    @Json(name = "timezone")
    val timezone: String? = "",
    @Json(name = "__v")
    val v: Int? = 0,
    @Json(name = "v1")
    val v1: V1? = V1(0,""),
    @Json(name = "vromo")
    val vromo: Vromo? = Vromo(false),
    @Json(name = "wayletCommerceId")
    val wayletCommerceId: String? = "",
    @Json(name = "whiteLabel")
    val whiteLabel: Boolean? = false,
    var distance : Float? = 0.0f,
   // @Json(name = "whiteLabelGroupId")
    //val whiteLabelGroupId: List<Any>
) : Parcelable