package com.juanmartin.videos.data

import com.juanmartin.videos.data.dto.comercios.Shops
import com.juanmartin.videos.ui.component.videos.entities.ParamFilter
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestVideos(params : ParamFilter): Flow<Resource<Shops>>

}