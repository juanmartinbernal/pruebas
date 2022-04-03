package com.juanmartin.data

import com.juanmartin.data.dto.comercios.Shops
import com.juanmartin.ui.component.shops.entities.ParamFilter
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestShops(params : ParamFilter): Flow<Resource<Shops>>

}