package com.example.globetrotter.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.globetrotter.data.local.GlobeTrotterDatabase
import com.example.globetrotter.data.model.GlobeTrotterInfo
import com.example.globetrotter.data.repository.CountryRepository
import com.example.globetrotter.data.repository.FavoritosRepository
import com.example.globetrotter.data.repository.HolidayRepository
import com.example.globetrotter.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GlobeTrotterViewModel(
    application: Application,
    private val countryRepo: CountryRepository = CountryRepository(),
    private val holidayRepo: HolidayRepository = HolidayRepository(),
    private val teamRepo: TeamRepository = TeamRepository(),
    private val favoritosRepo: FavoritosRepository =
        FavoritosRepository(GlobeTrotterDatabase.obtenerInstancia(application).trotterDao())
) : AndroidViewModel(application) {

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)

    val uiState: StateFlow<GlobeTrotterUiState> = combine(
        _searchState,
        favoritosRepo.mirarFavoritos()
    ) { searchState, favoritos ->
        GlobeTrotterUiState(
            searchState = searchState,
            favoritos = favoritos
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GlobeTrotterUiState()
    )

    fun searchCountry(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            _searchState.value = SearchState.Error("Ingresa el nombre de un país")
            return
        }

        viewModelScope.launch {
            _searchState.value = SearchState.Loading

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
                    _searchState.value = SearchState.Success(info)
                },
                onFailure = { error ->
                    _searchState.value = SearchState.Error(
                        error.localizedMessage ?: "Ocurrió un error inesperado al consultar la información.",
                    )
                },
            )
        }
    }

    fun agregarAFavoritos(info: GlobeTrotterInfo) {
        viewModelScope.launch {
            favoritosRepo.agregarFavorito(info)
        }
    }

    fun eliminarDeFavoritos(trotterId: Int) {
        viewModelScope.launch {
            favoritosRepo.eliminarFavorito(trotterId)
        }
    }
}
