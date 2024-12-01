package me.f1c.domain.result

fun RaceResultEntity.toDto() =
    RaceResultDto(
        season,
        round,
        url,
        raceName,
        circuitId,
        circuitName,
        raceDatetime.toString(),
        position,
        driverId,
        constructorId,
        status,
        timeMillis,
        timeText,
        fastestLapRank,
        fastestLapLap,
        fastestLapTime,
        fastestLapAverageSpeedUnits,
        fastestLapAverageSpeedSpeed,
    )
