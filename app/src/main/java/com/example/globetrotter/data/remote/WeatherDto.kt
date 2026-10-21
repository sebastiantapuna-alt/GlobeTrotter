package com.example.globetrotter.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("current") val current: CurrentDto?,
    @SerializedName("current_units") val currentUnits: CurrentUnitsDto?
)

data class CurrentDto(
    @SerializedName("temperature_2m") val temperature: Double?,
    @SerializedName("relative_humidity_2m") val humidity: Int?,
    @SerializedName("wind_speed_10m") val windSpeed: Double?
)

data class CurrentUnitsDto(
    @SerializedName("temperature_2m") val temperatureUnit: String?,
    @SerializedName("relative_humidity_2m") val humidityUnit: String?,
    @SerializedName("wind_speed_10m") val windSpeedUnit: String?
)
