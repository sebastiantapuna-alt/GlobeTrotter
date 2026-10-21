package com.example.globetrotter.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getWeatherLaPaz(
        @Query("latitude") latitude: Double = -16.5,
        @Query("longitude") longitude: Double = -68.15,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,wind_speed_10m",
        @Query("timezone") timezone: String = "America/La_Paz"
    ): WeatherResponseDto
}
