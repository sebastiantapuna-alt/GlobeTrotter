package com.example.globetrotter.data.local

import com.example.globetrotter.data.model.GlobeTrotterInfo

fun TrotterEntity.aInfo(): GlobeTrotterInfo =
    GlobeTrotterInfo(
        id = id,
        countryName = countryName,
        capital = capital,
        alpha2Code = alpha2Code,
        randomHoliday = randomHoliday,
        randomTeam = randomTeam
    )

fun GlobeTrotterInfo.aEntity(): TrotterEntity =
    TrotterEntity(
        id = id,
        countryName = countryName,
        capital = capital,
        alpha2Code = alpha2Code,
        randomHoliday = randomHoliday,
        randomTeam = randomTeam
    )
