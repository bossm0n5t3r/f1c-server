package me.f1c.port.admin

import me.f1c.domain.ResponseDto
import me.f1c.domain.chat.AiSessionSummaryDto

interface AdminController {
    fun upToDate(): ResponseDto<Unit>

    fun upToDateRaceSchedule(): ResponseDto<Unit>

    fun createSessionSummary(sessionKey: Int): ResponseDto<AiSessionSummaryDto>

    fun updateSessionSummary(sessionKey: Int): ResponseDto<AiSessionSummaryDto>

    fun validateAllSessionSummaries(): ResponseDto<Unit>

    fun validateSessionSummary(sessionKey: Int): ResponseDto<Boolean>
}
