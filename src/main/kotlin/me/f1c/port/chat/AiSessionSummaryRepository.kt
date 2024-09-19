package me.f1c.port.chat

import me.f1c.domain.chat.AiSessionSummaryDto

interface AiSessionSummaryRepository {
    fun save(createAiSessionSummaryDto: CreateAiSessionSummaryDto): Long

    fun findLatestBySessionKey(sessionKey: Int): AiSessionSummaryDto?

    fun findById(id: Long): AiSessionSummaryDto?
}
