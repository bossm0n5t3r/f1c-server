package me.f1c.domain.schedule

fun RaceScheduleEntity.toDto() =
    RaceScheduleDto(
        season,
        round,
        url,
        raceName,
        circuitId,
        circuitName,
        raceType,
        raceDatetime.toString(),
    )
