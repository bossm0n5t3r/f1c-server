package me.f1c.domain.lap

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.f1c.domain.jolpica.DateTime
import me.f1c.domain.jolpica.LapTiming
import me.f1c.domain.jolpica.Race
import me.f1c.domain.jolpica.toRaceDateTimeOrGivenTime

fun LapEntity.toDto() =
    LapDto(
        season,
        round,
        raceName,
        circuitId,
        raceDatetime.toString(),
        lapNumber,
        positions,
    )

private fun LapTiming.toPositionDto() =
    PositionDto(
        this.driverId,
        this.position,
        this.time,
    )

fun Race.toLapDto(): LapDto {
    val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val lap = this.laps?.first()
    requireNotNull(lap) { "Lap does not exist" }

    return LapDto(
        this.season.toInt(),
        this.round.toInt(),
        this.raceName,
        this.circuit.circuitId,
        DateTime(this.date, this.time).toRaceDateTimeOrGivenTime(now).toString(),
        lap.number.toInt(),
        lap.timings.map { it.toPositionDto() },
    )
}
