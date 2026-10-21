package com.example.globetrotter.data.repository

import com.example.globetrotter.data.local.TrotterDao
import com.example.globetrotter.data.local.aEntity
import com.example.globetrotter.data.local.aFact
import com.example.globetrotter.data.model.CatFact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritosRepository(private val dao: TrotterDao) {
    fun mirarFavoritos(): Flow<List<CatFact>> =
        dao.verFavoritos().map { entities -> entities.map { it.aFact() } }

    suspend fun agregarFavorito(catFact: CatFact) =
        dao.agregarFavorito(catFact.aEntity())

    suspend fun eliminarFavorito(factContenido: String) =
        dao.eliminarFavorito(factContenido)
}
