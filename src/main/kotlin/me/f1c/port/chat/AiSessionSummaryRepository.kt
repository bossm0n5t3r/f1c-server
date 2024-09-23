package me.f1c.port.chat

import me.f1c.domain.chat.AiSessionSummaryDto
import org.springframework.ai.openai.api.OpenAiApi

interface AiSessionSummaryRepository {
    fun save(createAiSessionSummaryDto: CreateAiSessionSummaryDto): Long

    fun findLatestBySessionKeyAndChatModel(
        sessionKey: Int,
        chatModel: OpenAiApi.ChatModel,
    ): AiSessionSummaryDto?

    fun findById(id: Long): AiSessionSummaryDto?
}
