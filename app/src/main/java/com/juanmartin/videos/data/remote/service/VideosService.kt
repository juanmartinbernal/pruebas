package com.juanmartin.videos.data.remote.service

import com.juanmartin.videos.data.dto.videos.VideoData
import com.juanmartin.videos.data.dto.videos.VideoDataResult
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Juan Martín Bernal
 */

interface VideosService {
    @GET("uc?id=1ldaCVV28Xtk-EVXhv7xh8ebR9kzfha0v")
    suspend fun fetchVideos(): Response<VideoData>
}
