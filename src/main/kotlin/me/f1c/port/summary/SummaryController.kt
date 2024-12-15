package me.f1c.port.summary

import me.f1c.domain.ResponseDto

interface SummaryController {
    fun getRaceResultSummary(
        season: Int,
        round: Int,
    ): ResponseDto<List<String>>
}
