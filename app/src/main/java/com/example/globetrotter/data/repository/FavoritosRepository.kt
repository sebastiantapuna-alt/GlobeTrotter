package com.example.globetrotter.data.repository

import com.example.globetrotter.data.local.TrotterDao
import com.example.globetrotter.data.local.TrotterEntity
import com.example.globetrotter.data.local.aEntity
import com.example.globetrotter.data.local.aInfo
import com.example.globetrotter.data.model.GlobeTrotterInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritosRepository(private val dao: TrotterDao){
    fun mirarFavoritos(): Flow<List<GlobeTrotterInfo>> =
        dao.verFavoritos().map { entities -> entities.map { it.aInfo() } }

    suspend fun agregarFavorito(trotterInfo: GlobeTrotterInfo) =
        dao.agregarFavorito(trotterInfo.aEntity())

    suspend fun eliminarFavorito(trotterId: Int) =
        dao.eliminarFavorito(trotterId)

}