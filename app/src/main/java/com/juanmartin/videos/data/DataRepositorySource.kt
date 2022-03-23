package com.juanmartin.videos.data

import com.juanmartin.videos.data.dto.comercios.Shops
import com.juanmartin.videos.ui.component.shops.entities.ParamFilter
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestShops(params : ParamFilter): Flow<Resource<Shops>>

}