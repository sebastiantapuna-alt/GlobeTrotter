package com.example.globetrotter.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.globetrotter.data.local.GlobeTrotterDatabase
import com.example.globetrotter.data.model.CatFact
import com.example.globetrotter.data.repository.CatFactRepository
import com.example.globetrotter.data.repository.FavoritosRepository
import com.example.globetrotter.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GlobeTrotterViewModel @JvmOverloads constructor(
    application: Application,
    private val weatherRepo: WeatherRepository = WeatherRepository(),
    private val catFactRepo: CatFactRepository = CatFactRepository(),
    private val favoritosRepo: FavoritosRepository =
        FavoritosRepository(GlobeTrotterDatabase.obtenerInstancia(application).trotterDao())
) : AndroidViewModel(application) {

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    private val _catFactsState = MutableStateFlow<CatFactsUiState>(CatFactsUiState.Idle)

    val uiState: StateFlow<GlobeTrotterUiState> = combine(
        _weatherState,
        _catFactsState,
        favoritosRepo.mirarFavoritos()
    ) { weather, catFacts, favoritos ->
        GlobeTrotterUiState(
            weatherState = weather,
            catFactsState = catFacts,
            favoritos = favoritos
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GlobeTrotterUiState()
    )

    init {
        fetchWeatherLaPaz()
        fetchCatFacts()
    }

    fun fetchWeatherLaPaz() {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            weatherRepo.getWeather().fold(
                onSuccess = { weather ->
                    _weatherState.value = WeatherUiState.Success(weather)
                },
                onFailure = { error ->
                    _weatherState.value = WeatherUiState.Error(
                        error.localizedMessage ?: "Error al obtener el clima de La Paz"
                    )
                }
            )
        }
    }

    fun fetchCatFacts(limit: Int = 10) {
        viewModelScope.launch {
            _catFactsState.value = CatFactsUiState.Loading
            catFactRepo.getCatFacts(limit).fold(
                onSuccess = { facts ->
                    _catFactsState.value = CatFactsUiState.Success(facts)
                },
                onFailure = { error ->
                    _catFactsState.value = CatFactsUiState.Error(
                        error.localizedMessage ?: "Error al obtener datos de gatos"
                    )
                }
            )
        }
    }

    fun agregarAFavoritos(fact: CatFact) {
        viewModelScope.launch {
            favoritosRepo.agregarFavorito(fact)
        }
    }

    fun eliminarDeFavoritos(factContenido: String) {
        viewModelScope.launch {
            favoritosRepo.eliminarFavorito(factContenido)
        }
    }
}
