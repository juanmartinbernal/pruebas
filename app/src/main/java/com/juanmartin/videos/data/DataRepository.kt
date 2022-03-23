package com.juanmartin.videos.data


import com.juanmartin.videos.data.dto.comercios.Shops
import com.juanmartin.videos.data.local.LocalData
import com.juanmartin.videos.data.remote.RemoteData
import com.juanmartin.videos.ui.component.shops.entities.ParamFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by Juan Martin Bernal
 */

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {

    override suspend fun requestShops(params : ParamFilter): Flow<Resource<Shops>> {
        return flow {
            emit(remoteRepository.requestShops(params))
        }.flowOn(ioDispatcher)
    }

}
