package com.juanmartin.videos.data.remote.service

import com.juanmartin.videos.data.dto.comercios.ShopsItem
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Juan Martín Bernal
 */

interface VideosService {
    @GET("commerces/public")
    suspend fun fetchVideos(): Response<ArrayList<ShopsItem>>
}
