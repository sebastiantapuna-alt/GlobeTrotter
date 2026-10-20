package com.example.globetrotter.data.repository

import com.example.globetrotter.data.mappers.toModel
import com.example.globetrotter.data.model.Holiday
import com.example.globetrotter.data.remote.HolidayApiService
import com.example.globetrotter.data.remote.RetrofitClient

class HolidayRepository {
    private val api: HolidayApiService = RetrofitClient.apiServiceHoliday

    suspend fun getRandomHoliday(countryCode: String, year: Int = 2026): Result<Holiday> {
        return runCatching {
            val list = api.getPublicHolidays(year, countryCode)
            if (list.isEmpty()) {
                throw Exception("No hay feriados para $countryCode")
            }
            list.random().toModel()
        }
    }
}
