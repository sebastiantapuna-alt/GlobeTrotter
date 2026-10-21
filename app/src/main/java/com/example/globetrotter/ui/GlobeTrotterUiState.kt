package com.example.globetrotter.ui

import com.example.globetrotter.data.model.CatFact
import com.example.globetrotter.data.model.WeatherData

data class GlobeTrotterUiState(
    val weatherState: WeatherUiState = WeatherUiState.Idle,
    val catFactsState: CatFactsUiState = CatFactsUiState.Idle,
    val favoritos: List<CatFact> = emptyList()
)

sealed interface WeatherUiState {
    object Idle : WeatherUiState
    object Loading : WeatherUiState
    data class Success(val weather: WeatherData) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

sealed interface CatFactsUiState {
    object Idle : CatFactsUiState
    object Loading : CatFactsUiState
    data class Success(val facts: CatFact) : CatFactsUiState
    data class Error(val message: String) : CatFactsUiState
}
