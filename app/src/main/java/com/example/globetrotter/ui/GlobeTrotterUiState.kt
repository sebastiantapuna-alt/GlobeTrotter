package com.example.globetrotter.ui

import com.example.globetrotter.data.model.GlobeTrotterInfo

sealed interface GlobeTrotterUiState {
    object Idle : GlobeTrotterUiState
    object Loading : GlobeTrotterUiState
    data class Success(val info: GlobeTrotterInfo) : GlobeTrotterUiState
    data class Error(val message: String) : GlobeTrotterUiState
}
