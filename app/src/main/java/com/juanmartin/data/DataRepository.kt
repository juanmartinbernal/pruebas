package com.juanmartin.data


import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.data.local.LocalData
import com.juanmartin.data.remote.RemoteData
import com.juanmartin.ui.component.shops.entities.ParamFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by Juan Martin Bernal
 */

class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val localRepository: LocalData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource {

    override suspend fun requestShops(params: ParamFilter): Flow<Resource<Shops>> {
        return flow {
            emit(remoteRepository.requestShops(params))
        }.flowOn(ioDispatcher)
    }

}
