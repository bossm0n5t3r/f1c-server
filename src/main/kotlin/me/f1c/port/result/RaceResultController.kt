package me.f1c.port.result

import me.f1c.domain.ResponseDto
import me.f1c.domain.result.FastestLapResultDto
import me.f1c.domain.result.RankingDto

interface RaceResultController {
    fun getRankings(
        season: Int,
        round: Int,
    ): ResponseDto<RankingDto>

    fun fastestNLaps(
        season: Int,
        round: Int,
        n: Int,
    ): ResponseDto<List<FastestLapResultDto>>
}
