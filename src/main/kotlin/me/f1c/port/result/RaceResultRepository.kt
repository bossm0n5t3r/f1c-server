package me.f1c.port.result

import me.f1c.domain.result.RaceResultDto

interface RaceResultRepository {
    fun batchInsert(raceResultDtoList: List<RaceResultDto>): Int

    fun findAll(): List<RaceResultDto>

    fun findLatest(): RaceResultDto?

    fun findAllBySeasonAndRound(
        season: Int,
        round: Int,
    ): List<RaceResultDto>
}
