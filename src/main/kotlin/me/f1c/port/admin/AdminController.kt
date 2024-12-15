package me.f1c.port.admin

import me.f1c.domain.ResponseDto
import me.f1c.domain.chat.AiRaceResultSummaryDto

interface AdminController {
    fun upToDateRaceSchedule(): ResponseDto<Int>

    fun upToDateRaceResult(): ResponseDto<Int>

    fun upToDateDriver(): ResponseDto<Int>

    fun upToDateConstructor(): ResponseDto<Int>

    fun upToDateCircuit(): ResponseDto<Int>

    fun upToDateAllLaps(): ResponseDto<Int>

    fun upToDateLaps(
        season: Int,
        round: Int,
    ): ResponseDto<Int>

    fun createRaceResultSummary(
        season: Int,
        round: Int,
    ): ResponseDto<AiRaceResultSummaryDto>

    fun updateRaceResultSummary(
        season: Int,
        round: Int,
    ): ResponseDto<AiRaceResultSummaryDto>
}
