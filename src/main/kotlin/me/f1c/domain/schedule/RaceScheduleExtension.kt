package me.f1c.domain.schedule

import me.f1c.domain.circuit.CircuitInfo

fun RaceScheduleEntity.toDto(): RaceScheduleDto {
    val circuitInfo = CircuitInfo.findByCircuitIdOrNull(circuitId)
    return RaceScheduleDto(
        season,
        round,
        url,
        raceName,
        circuitId,
        circuitName,
        raceType,
        raceDatetime.toString(),
        circuitInfo?.trackIconUrl,
        circuitInfo?.mapUrl,
    )
}
