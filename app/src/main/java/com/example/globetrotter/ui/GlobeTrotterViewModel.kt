package com.example.globetrotter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.globetrotter.data.model.GlobeTrotterInfo
import com.example.globetrotter.data.repository.CountryRepository
import com.example.globetrotter.data.repository.HolidayRepository
import com.example.globetrotter.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GlobeTrotterViewModel(
    private val countryRepo: CountryRepository = CountryRepository(),
    private val holidayRepo: HolidayRepository = HolidayRepository(),
    private val teamRepo: TeamRepository = TeamRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<GlobeTrotterUiState>(GlobeTrotterUiState.Idle)
    val uiState: StateFlow<GlobeTrotterUiState> = _uiState.asStateFlow()

    fun searchCountry(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            _uiState.value = GlobeTrotterUiState.Error("Ingresa el nombre de un país")
            return
        }

        viewModelScope.launch {
            _uiState.value = GlobeTrotterUiState.Loading

            val countryResult = countryRepo.search(trimmed)
            countryResult.fold(
                onSuccess = { country ->
                    val holidayText = if (country.alpha2.isNotBlank()) {
                        holidayRepo.getRandomHoliday(country.alpha2).getOrNull()?.name
                            ?: "No hay feriados para el código ${country.alpha2}"
                    } else {
                        "Sin código de país para consultar feriados"
                    }

                    val teamText = teamRepo.getRandomTeam(trimmed).getOrNull()?.name
                        ?: "No se encontraron equipos para ${country.name}"

                    val info = GlobeTrotterInfo(
                        countryName = country.name,
                        capital = country.capital.ifBlank { "Sin Capital" },
                        alpha2Code = country.alpha2,
                        randomHoliday = holidayText,
                        randomTeam = teamText,
                    )
                    _uiState.value = GlobeTrotterUiState.Success(info)
                },
                onFailure = { error ->
                    _uiState.value = GlobeTrotterUiState.Error(
                        error.localizedMessage ?: "Ocurrió un error inesperado al consultar la información.",
                    )
                },
            )
        }
    }
}
