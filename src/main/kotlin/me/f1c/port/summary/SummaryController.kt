package me.f1c.port.summary

import me.f1c.domain.ResponseDto
import me.f1c.domain.chat.AiSessionSummaryDto

interface SummaryController {
    fun getSessionSummary(sessionKey: Int): ResponseDto<AiSessionSummaryDto?>
}
