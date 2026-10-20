package com.example.globetrotter.data.remote

import com.google.gson.annotations.SerializedName

data class TeamsResponseDto(
    val teams: List<TeamDto>?
)

data class TeamDto(
    @SerializedName("strTeam") val name: String?
)