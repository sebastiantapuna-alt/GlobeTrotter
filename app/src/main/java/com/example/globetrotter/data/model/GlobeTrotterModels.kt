package com.example.globetrotter.data.model

import com.google.gson.annotations.SerializedName

// RestCountries DTO
data class CountryApiResponse(
    @SerializedName("data") val data: CountryData?,
)

data class CountryData(
    @SerializedName("objects") val objects: List<CountryObject>?,
)

data class CountryObject(
    @SerializedName("capital") val capital: Any?,
    @SerializedName("codes") val codes: CountryCodes?,
    @SerializedName("names") val names: CountryNames?,
)

data class CountryCodes(
    @SerializedName("alpha_2") val alpha2: String?,
)

data class CountryNames(
    @SerializedName("common") val common: String?,
    @SerializedName("official") val official: String?,
)

// Public Holiday DTO
data class HolidayDto(
    @SerializedName("date") val date: String?,
    @SerializedName("localName") val localName: String?,
    @SerializedName("name") val name: String?,
)

// Sports DB DTO
data class SportsResponse(
    @SerializedName("teams") val teams: List<SportsTeamDto>?,
)

data class SportsTeamDto(
    @SerializedName("strTeam") val strTeam: String?,
    @SerializedName("strSport") val strSport: String?,
    @SerializedName("strCountry") val strCountry: String?,
)

// Domain Model
data class GlobeTrotterInfo(
    val countryName: String,
    val capital: String,
    val alpha2Code: String,
    val randomHoliday: String,
    val randomTeam: String,
)
