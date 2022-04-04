package com.juanmartin.data.remote


import android.location.Location
import com.juanmartin.data.Resource
import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.data.dto.comercios.ShopsItem
import com.juanmartin.data.error.DEFAULT_ERROR
import com.juanmartin.data.error.NETWORK_ERROR
import com.juanmartin.data.error.NO_INTERNET_CONNECTION
import com.juanmartin.data.remote.service.Service
import com.juanmartin.ui.component.shops.entities.ParamFilter
import com.juanmartin.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class RemoteData @Inject
constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) :
    RemoteDataSource {
    override suspend fun requestShops(params : ParamFilter): Resource<Shops> {
        val service = serviceGenerator.createService(Service::class.java)
        return when (val response = processCall(service::fetchShops)) {
            is List<*> -> {
               // Resource.Success(data = Shops(response as ArrayList<ShopsItem>))
                val result = Shops(response as ArrayList<ShopsItem>)
                val filter : MutableList<ShopsItem> = ArrayList()
                val currentLocation = Location("provider")
                currentLocation.latitude = params.latitude
                currentLocation.longitude = params.longitude

                result.shopsList.forEach {
                    if(it.latitude != null && it.longitude != null){
                        val shopLocation = Location("provider")
                        shopLocation.latitude = it.latitude
                        shopLocation.longitude = it.longitude
                        val distance = currentLocation.distanceTo(shopLocation)
                        it.distance = distance / 1000 //km
                        filter.add(it)
                    }
                }
                Resource.Success(data = Shops(filter as ArrayList<ShopsItem>))
            }
            else -> {
                return Resource.DataError(errorCode = DEFAULT_ERROR)
                //Resource.Success(data = Shops(response as ArrayList<ShopsItem>))
                //   Resource.DataError(errorCode = DEFAULT_ERROR/*response as Int*/)
            }
        }
        // return Resource.DataError(errorCode = DEFAULT_ERROR)
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
