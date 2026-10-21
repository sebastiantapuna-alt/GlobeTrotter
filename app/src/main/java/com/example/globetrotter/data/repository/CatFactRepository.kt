package com.example.globetrotter.data.repository

import com.example.globetrotter.data.model.CatFact
import com.example.globetrotter.data.remote.CatFactApiService
import com.example.globetrotter.data.remote.RetrofitClient
import com.example.globetrotter.data.remote.aCatFact

class CatFactRepository(
    private val api: CatFactApiService = RetrofitClient.apiServiceCatFact
) {
    suspend fun getCatFacts(limit: Int = 10): Result<CatFact> {
        return runCatching {
            api.getCatFacts(limit).aCatFact()
                ?: throw Exception("No se encontró")

        }
    }
}
