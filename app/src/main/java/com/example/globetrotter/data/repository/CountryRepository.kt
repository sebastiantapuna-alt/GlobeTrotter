package com.example.globetrotter.data.repository

import com.example.globetrotter.data.mappers.toModel
import com.example.globetrotter.data.model.Country
import com.example.globetrotter.data.remote.CountryApiService
import com.example.globetrotter.data.remote.RetrofitClient

class CountryRepository {
    private val api: CountryApiService = RetrofitClient.apiServiceCountry

    suspend fun search(name: String): Result<Country> {
        return runCatching {
            api.getCountryInfo(name).toModel()
                ?: throw Exception("No se encontró información para el país '$name'")
        }
    }
}
