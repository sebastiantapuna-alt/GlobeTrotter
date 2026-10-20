package com.example.globetrotter.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamApiService {
    @GET("api/v1/json/{apiKey}/search_all_teams.php")
suspend fun searchAllTeams(
    @Path("apiKey") apiKey: String = "123",
    @Query("s") sport: String = "Soccer",
    @Query("c") country: String,
): TeamsResponseDto
}
