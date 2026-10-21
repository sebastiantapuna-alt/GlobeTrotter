package com.example.globetrotter.data.repository

import com.example.globetrotter.data.model.WeatherData
import com.example.globetrotter.data.remote.RetrofitClient
import com.example.globetrotter.data.remote.WeatherApiService
import com.example.globetrotter.data.remote.aWeatherData

class WeatherRepository(
    private val api: WeatherApiService = RetrofitClient.apiServiceWeather
) {
    suspend fun getWeather(): Result<WeatherData>{
        return runCatching {
            api.getWeatherLaPaz().aWeatherData()
        }
    }
}
