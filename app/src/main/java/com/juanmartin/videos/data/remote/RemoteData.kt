package com.juanmartin.videos.data.remote


import com.juanmartin.videos.data.Resource
import com.juanmartin.videos.data.dto.comercios.Shops
import com.juanmartin.videos.data.dto.comercios.ShopsItem
import com.juanmartin.videos.data.error.DEFAULT_ERROR
import com.juanmartin.videos.data.error.NETWORK_ERROR
import com.juanmartin.videos.data.error.NO_INTERNET_CONNECTION
import com.juanmartin.videos.data.remote.service.VideosService
import com.juanmartin.videos.ui.component.shops.entities.ParamFilter
import com.juanmartin.videos.utils.NetworkConnectivity
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
        val videosService = serviceGenerator.createService(VideosService::class.java)
        return when (val response = processCall(videosService::fetchVideos)) {
            is List<*> -> {
                Resource.Success(data = Shops(response as ArrayList<ShopsItem>))
                /*if(params.category.isNotEmpty()){
                    val result = Shops(response as ArrayList<ShopsItem>)
                    val filter : MutableList<ShopsItem> = ArrayList()

                    result.shopsList.forEach {
                        if(it.category != null && it.category.contains(params.category)){
                            filter.add(it)
                        }
                    }
                    Resource.Success(data = Shops(filter as ArrayList<ShopsItem>))
                }else {
                    Resource.Success(data = Shops(response as ArrayList<ShopsItem>))
                }*/
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
