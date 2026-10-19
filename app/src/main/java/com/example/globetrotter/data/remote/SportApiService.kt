package com.example.globetrotter.data.remote

import com.example.globetrotter.data.model.SportsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SportApiService {
    @GET("api/v1/json/{apiKey}/search_all_teams.php")
    suspend fun searchAllTeams(
        @Query("s") sport: String = "Soccer",
        @Query("c") country: String,
        @Path("apiKey") apiKey: String = "123",
    ): SportsResponse
}
