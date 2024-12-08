package me.f1c.domain.schedule

import me.f1c.domain.jolpica.SeasonAndRound

data class RaceScheduleDto(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    val circuitId: String,
    val circuitName: String,
    val raceType: String,
    val raceDatetime: String,
    val trackIconUrl: String? = null,
    val mapUrl: String? = null,
) {
    fun toSeasonAndRound() = SeasonAndRound(season, round)
}
