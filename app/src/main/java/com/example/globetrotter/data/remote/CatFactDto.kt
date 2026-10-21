package com.example.globetrotter.data.remote

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("data") val data: List<CatFactDto>
)

data class CatFactDto(
    @SerializedName("fact") val fact: String?,
    @SerializedName("length") val length: Int?
)
