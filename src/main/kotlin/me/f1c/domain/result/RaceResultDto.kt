package me.f1c.domain.result

import me.f1c.domain.jolpica.SeasonAndRound

data class RaceResultDto(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    val circuitId: String,
    val circuitName: String,
    val raceDatetime: String,
    val position: Int,
    val driverId: String,
    val constructorId: String,
    val status: String,
    val timeMillis: Long? = null,
    val timeText: String? = null,
    val fastestLapRank: Int? = null,
    val fastestLapLap: String? = null,
    val fastestLapTime: String? = null,
    val fastestLapAverageSpeedUnits: String? = null,
    val fastestLapAverageSpeedSpeed: String? = null,
) {
    fun toSeasonAndRound() = SeasonAndRound(season, round)
}
