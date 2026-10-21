package com.example.globetrotter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class TrotterEntity(
    @PrimaryKey val fact: String,
    val length: Int,
    val agregadoEn: Long = System.currentTimeMillis()
)
