package com.example.globetrotter.data.local

import com.example.globetrotter.data.model.CatFact

fun TrotterEntity.aFact(): CatFact =
    CatFact(
        fact = fact,
        length = length
    )

fun CatFact.aEntity(): TrotterEntity =
    TrotterEntity(
        fact = fact ?: "",
        length = length ?: 0
    )
