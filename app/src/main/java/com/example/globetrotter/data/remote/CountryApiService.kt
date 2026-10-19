package com.example.globetrotter.data.remote

import com.example.globetrotter.data.model.CountryApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApiService {
    @GET("countries/v5")
    suspend fun getCountryInfo(
        @Query("q") query: String,
        @Query("api-key") apiKey: String = "rc_live_1352582c923f4b6f94ff520897111148",
        @Query("pretty") pretty: Int = 1,
    ): CountryApiResponse
}
