package com.example.globetrotter.data.repository

import com.example.globetrotter.data.model.GlobeTrotterInfo
import com.example.globetrotter.data.remote.CountryApiService
import com.example.globetrotter.data.remote.HolidayApiService
import com.example.globetrotter.data.remote.RetrofitClient
import com.example.globetrotter.data.remote.SportApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GlobeTrotterRepository(
    private val countryApi: CountryApiService = RetrofitClient.apiServiceCountry,
    private val holidayApi: HolidayApiService = RetrofitClient.apiServiceHoliday,
    private val sportApi: SportApiService = RetrofitClient.apiServiceSport,
) {
    suspend fun getGlobeTrotterInfo(countryInput: String): Result<GlobeTrotterInfo> =
        withContext(Dispatchers.IO) {
            runCatching {
                // 1. Obtener información del país desde RestCountries API
                val countryResponse = countryApi.getCountryInfo(query = countryInput)
                val countryObj = countryResponse.data?.objects?.firstOrNull()
                    ?: throw Exception("No se encontró información para el país '$countryInput'")

                // Parsear capital (puede venir como lista o texto)
                val capitalText = when (val cap = countryObj.capital) {
                    is List<*> -> cap.firstOrNull()?.toString() ?: "Sin Capital"
                    is String -> cap
                    else -> "Sin Capital"
                }

                // Extraer el código Alpha-2
                val alpha2 = countryObj.codes?.alpha2
                    ?: throw Exception("No se encontró el código alpha_2 para el país '$countryInput'")

                val countryDisplayName = countryObj.names?.common
                    ?: countryObj.names?.official
                    ?: countryInput

                // 2. Obtener feriados públicos desde Nager.Date API
                val holidays = runCatching {
                    holidayApi.getPublicHolidays(year = 2026, countryCode = alpha2)
                }.getOrNull() ?: emptyList()

                val randomHolidayText = if (holidays.isNotEmpty()) {
                    val picked = holidays.random()
                    val hName = picked.name ?: picked.localName ?: "Feriado"
                    val hDate = picked.date ?: ""
                    if (hDate.isNotBlank()) "$hName ($hDate)" else hName
                } else {
                    "No hay feriados públicos registrados para este código ($alpha2)."
                }

                // 3. Obtener equipos de fútbol desde TheSportsDB API
                val sportsResponse = runCatching {
                    sportApi.searchAllTeams(sport = "Soccer", country = countryInput)
                }.getOrNull()

                val teams = sportsResponse?.teams ?: emptyList()
                val randomTeamText = if (teams.isNotEmpty()) {
                    teams.random().strTeam ?: "Equipo de fútbol desconocido"
                } else {
                    "No se encontraron equipos de fútbol registrados para '$countryInput'."
                }

                GlobeTrotterInfo(
                    countryName = countryDisplayName,
                    capital = capitalText,
                    alpha2Code = alpha2,
                    randomHoliday = randomHolidayText,
                    randomTeam = randomTeamText,
                )
            }
        }
}
