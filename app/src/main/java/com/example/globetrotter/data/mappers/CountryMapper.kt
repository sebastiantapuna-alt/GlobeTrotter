package com.example.globetrotter.data.mappers

import com.example.globetrotter.data.model.Country
import com.example.globetrotter.data.remote.CountryResponseDto

fun CountryResponseDto.toModel(): Country? {
    val dto = data?.objects?.firstOrNull() ?: return null
    return Country(
        name = dto.names?.common ?: "Desconocido",
        alpha2 = dto.codes?.alpha2 ?: "",
        capital = dto.capitals?.firstOrNull()?.name ?: ""
    )
}