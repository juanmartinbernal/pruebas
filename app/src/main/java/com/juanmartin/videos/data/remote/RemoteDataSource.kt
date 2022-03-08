package com.juanmartin.videos.data.remote

import com.juanmartin.videos.data.Resource
import com.juanmartin.videos.data.dto.videos.VideoDataResult


/**
 * Created by Juan Martín Bernal
 */

internal interface RemoteDataSource {
    suspend fun requestVideos(): Resource<VideoDataResult>
}
