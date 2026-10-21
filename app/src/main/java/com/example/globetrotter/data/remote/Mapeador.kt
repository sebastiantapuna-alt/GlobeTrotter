package com.example.globetrotter.data.remote

import com.example.globetrotter.data.model.CatFact
import com.example.globetrotter.data.model.WeatherData

fun DataDto.aCatFact(): CatFact {
    val dto = data.firstOrNull()
    return CatFact(fact = dto?.fact, length = dto?.length)
}

/*TODO pa q funcione el commit*/

fun WeatherResponseDto.aWeatherData(): WeatherData {
    val temp = current?.temperature?.let { "$it ${currentUnits?.temperatureUnit ?: "°C"}" } ?: "N/A"
    val hum = current?.humidity?.let { "$it${currentUnits?.humidityUnit ?: "%"}" } ?: "N/A"
    val wind = current?.windSpeed?.let { "$it ${currentUnits?.windSpeedUnit ?: "km/h"}" } ?: "N/A"
    return WeatherData(
        temperature = temp,
        humidity = hum,
        windSpeed = wind
    )
}
