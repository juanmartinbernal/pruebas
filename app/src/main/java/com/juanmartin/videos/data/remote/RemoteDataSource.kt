package com.juanmartin.videos.data.remote

import com.juanmartin.videos.data.Resource
import com.juanmartin.videos.data.dto.comercios.Shops
import com.juanmartin.videos.ui.component.videos.entities.ParamFilter


/**
 * Created by Juan Martín Bernal
 */

internal interface RemoteDataSource {
    suspend fun requestShops(params : ParamFilter): Resource<Shops>
}
