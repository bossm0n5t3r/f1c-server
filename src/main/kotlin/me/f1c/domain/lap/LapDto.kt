package me.f1c.domain.lap

import me.f1c.domain.jolpica.SeasonAndRound

data class LapDto(
    val season: Int,
    val round: Int,
    val raceName: String,
    val circuitId: String,
    val raceDatetime: String,
    val lapNumber: Int,
    val positions: List<PositionDto>,
) {
    fun toSeasonAndRound(): SeasonAndRound = SeasonAndRound(season, round)
}
