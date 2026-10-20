package com.example.globetrotter.data.remote

import com.google.gson.annotations.SerializedName

data class CountryResponseDto(val data: DataDto?)
data class DataDto(val objects: List<CountryDto>?)
data class CountryDto(
    val names: NamesDto?,
    val codes: CodesDto?,
    val capitals: List<CapitalDto>?
)
data class NamesDto(val common: String?)
data class CodesDto(@SerializedName("alpha_2") val alpha2: String?)
data class CapitalDto(val name: String?)
