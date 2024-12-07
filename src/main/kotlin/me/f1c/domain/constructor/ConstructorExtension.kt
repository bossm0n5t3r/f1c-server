package me.f1c.domain.constructor

import me.f1c.domain.jolpica.Constructor

fun ConstructorEntity.toDto() =
    ConstructorDto(
        season,
        constructorId,
        url,
        name,
        nationality,
        ConstructorInfo.findByConstructorIdOrNull(constructorId)?.hexColor,
    )

fun Constructor.toConstructorDto(season: Int) =
    ConstructorDto(
        season,
        constructorId,
        url,
        name,
        nationality,
    )
