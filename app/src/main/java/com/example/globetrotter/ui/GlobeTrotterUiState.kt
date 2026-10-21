package com.example.globetrotter.ui

import com.example.globetrotter.data.model.GlobeTrotterInfo

data class GlobeTrotterUiState(
    val searchState: SearchState = SearchState.Idle,
    val favoritos: List<GlobeTrotterInfo> = emptyList()
)

sealed interface SearchState {
    object Idle : SearchState
    object Loading : SearchState
    data class Success(val info: GlobeTrotterInfo) : SearchState
    data class Error(val message: String) : SearchState
}
