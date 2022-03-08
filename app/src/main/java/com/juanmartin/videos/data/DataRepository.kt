package com.juanmartin.videos.data


import com.juanmartin.videos.data.dto.videos.VideoDataResult
import com.juanmartin.videos.data.local.LocalData
import com.juanmartin.videos.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by Juan Martin Bernal
 */

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {

    override suspend fun requestVideos(): Flow<Resource<VideoDataResult>> {
        return flow {
            emit(remoteRepository.requestVideos())
        }.flowOn(ioDispatcher)
    }

}
