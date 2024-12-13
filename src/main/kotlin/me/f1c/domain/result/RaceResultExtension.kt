package me.f1c.domain.result

import kotlinx.datetime.LocalDateTime
import me.f1c.domain.jolpica.DateTime
import me.f1c.domain.jolpica.Race
import me.f1c.domain.jolpica.Result
import me.f1c.domain.jolpica.toRaceDateTimeOrGivenTime

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

fun Race.toRaceResultDtoList(now: LocalDateTime): List<RaceResultDto> {
    val resultsInRaceDto = this.results
    if (resultsInRaceDto.isNullOrEmpty()) return emptyList()

    val season = this.season.toInt()
    val round = this.round.toInt()
    val url = this.url
    val raceName = this.raceName
    val circuitId = this.circuit.circuitId
    val circuitName = this.circuit.circuitName
    val raceDateTime = DateTime(this.date, this.time).toRaceDateTimeOrGivenTime(now).toString()
    return resultsInRaceDto.map { it.toRaceResultDto(season, round, url, raceName, circuitId, circuitName, raceDateTime) }
}

private fun Result.toRaceResultDto(
    season: Int,
    round: Int,
    url: String,
    raceName: String,
    circuitId: String,
    circuitName: String,
    raceDateTime: String,
): RaceResultDto =
    RaceResultDto(
        season,
        round,
        url,
        raceName,
        circuitId,
        circuitName,
        raceDateTime,
        this.position.toInt(),
        this.driver.driverId,
        this.constructor.constructorId,
        this.status,
        this.time?.millis?.toLong(),
        this.time?.time,
        this.fastestLap?.rank?.toInt(),
        this.fastestLap?.lap,
        this.fastestLap?.time?.time,
        this.fastestLap?.averageSpeed?.units,
        this.fastestLap?.averageSpeed?.speed,
    )
