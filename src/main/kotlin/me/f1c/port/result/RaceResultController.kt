package me.f1c.port.result

import me.f1c.domain.ResponseDto
import me.f1c.domain.result.RankingDto

interface RaceResultController {
    fun getRankings(
        season: Int,
        round: Int,
    ): ResponseDto<RankingDto>
}
