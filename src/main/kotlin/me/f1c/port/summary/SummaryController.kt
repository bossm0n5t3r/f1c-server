package me.f1c.port.summary

import me.f1c.domain.ResponseDto

interface SummaryController {
    fun getSessionSummary(sessionKey: Int): ResponseDto<List<String>>
}
