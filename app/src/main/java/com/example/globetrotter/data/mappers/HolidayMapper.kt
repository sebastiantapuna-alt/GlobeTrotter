package com.example.globetrotter.data.mappers

import com.example.globetrotter.data.model.Holiday
import com.example.globetrotter.data.remote.HolidayDto

fun HolidayDto.toModel(): Holiday = Holiday(
    name = name ?: "Sin nombre"
)