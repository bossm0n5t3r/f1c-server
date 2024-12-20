package me.f1c.port.lap

import me.f1c.domain.lap.LapDto

interface LapRepository {
    fun batchInsert(lapDtoList: List<LapDto>): Int

    fun findAllBySeasonAndRound(
        season: Int,
        round: Int,
    ): List<LapDto>

    fun findLatest(season: Int): LapDto?
}
