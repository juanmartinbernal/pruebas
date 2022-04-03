package com.juanmartin.data.remote.service

import com.juanmartin.data.dto.comercios.ShopsItem
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Juan Martín Bernal
 */

interface Service {
    @GET("commerces/public")
    suspend fun fetchShops(): Response<ArrayList<ShopsItem>>
}
