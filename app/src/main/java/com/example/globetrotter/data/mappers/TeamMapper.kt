package com.example.globetrotter.data.mappers

import com.example.globetrotter.data.model.Team
import com.example.globetrotter.data.remote.TeamsResponseDto

fun TeamsResponseDto.toModel(): Team? {
    val randomTeam = teams?.randomOrNull() ?: return null
    return Team(
        name = randomTeam.name ?: "Sin nombre"
    )
}
