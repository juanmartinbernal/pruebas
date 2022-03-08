package com.juanmartin.videos.data.remote


import com.juanmartin.videos.data.Resource
import com.juanmartin.videos.data.dto.videos.VideoDataResult
import com.juanmartin.videos.data.error.DEFAULT_ERROR
import com.juanmartin.videos.data.error.NETWORK_ERROR
import com.juanmartin.videos.data.error.NO_INTERNET_CONNECTION
import com.juanmartin.videos.data.remote.service.VideosService
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
    override suspend fun requestVideos(): Resource<VideoDataResult> {
        val videosService = serviceGenerator.createService(VideosService::class.java)
        /* return when (val response = processCall(videosService::fetchVideos)) {
             is List<*> -> {
                 Resource.Success(data = VideoDataResult(response as ArrayList<VideoData>))
             }
             else -> {
                 Resource.DataError(errorCode = response as Int)
             }
         }*/
        return Resource.DataError(errorCode = DEFAULT_ERROR)
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
