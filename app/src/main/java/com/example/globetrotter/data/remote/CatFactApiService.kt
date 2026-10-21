package com.example.globetrotter.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactApiService {
    @GET("facts")
    suspend fun getCatFacts(
        @Query("limit") limit: Int = 10
    ): DataDto
}
