package com.juanmartin.videos.data

import com.juanmartin.videos.data.dto.videos.VideoDataResult
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestVideos(): Flow<Resource<VideoDataResult>>

}