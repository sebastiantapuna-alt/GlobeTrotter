package com.example.globetrotter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TrotterDao {
    @Query("SELECT * FROM favoritos ORDER BY agregadoEn DESC")
    fun verFavoritos(): Flow<List<TrotterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarFavorito(trotterEntity: TrotterEntity)

    @Query("DELETE FROM favoritos WHERE fact = :fact")
    suspend fun eliminarFavorito(fact: String)
}
