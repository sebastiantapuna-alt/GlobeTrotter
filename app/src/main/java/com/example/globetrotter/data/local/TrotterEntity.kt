package com.example.globetrotter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class TrotterEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val countryName: String,
    val capital: String,
    val alpha2Code: String,
    val randomHoliday: String,
    val randomTeam: String,
    val agregadoEn: Long = System.currentTimeMillis()
)