package com.example.globetrotter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.globetrotter.data.repository.GlobeTrotterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GlobeTrotterViewModel(
    private val repository: GlobeTrotterRepository = GlobeTrotterRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<GlobeTrotterUiState>(GlobeTrotterUiState.Idle)
    val uiState: StateFlow<GlobeTrotterUiState> = _uiState.asStateFlow()

    fun searchCountry(countryName: String) {
        val trimmed = countryName.trim()
        if (trimmed.isEmpty()) {
            _uiState.value = GlobeTrotterUiState.Error("Ingresa el nombre de un país")
            return
        }

        viewModelScope.launch {
            _uiState.value = GlobeTrotterUiState.Loading
            val result = repository.getGlobeTrotterInfo(trimmed)
            result.onSuccess { info ->
                _uiState.value = GlobeTrotterUiState.Success(info)
            }.onFailure { exception ->
                _uiState.value = GlobeTrotterUiState.Error(
                    exception.localizedMessage ?: "Ocurrió un error al obtener la información."
                )
            }
        }
    }
}
