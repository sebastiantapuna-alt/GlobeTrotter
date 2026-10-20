package com.example.globetrotter.data.repository

import com.example.globetrotter.data.mappers.toModel
import com.example.globetrotter.data.model.Team
import com.example.globetrotter.data.remote.RetrofitClient
import com.example.globetrotter.data.remote.TeamApiService

class TeamRepository {
    private val api: TeamApiService = RetrofitClient.apiServiceSport

    suspend fun getRandomTeam(countryName: String, sport: String = "Soccer"): Result<Team> {
        return runCatching {
            val formattedCountry = countryName.trim().replaceFirstChar { it.uppercase() }
            val response = api.searchAllTeams(sport = sport, country = formattedCountry)
            response.toModel() ?: throw Exception("No se encontraron equipos para $formattedCountry")
        }
    }
}
