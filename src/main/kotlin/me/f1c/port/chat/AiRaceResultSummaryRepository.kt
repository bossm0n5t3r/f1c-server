package me.f1c.port.chat

import me.f1c.domain.chat.AiRaceResultSummaryDto
import org.springframework.ai.openai.api.OpenAiApi

interface AiRaceResultSummaryRepository {
    fun save(createAiRaceResultSummaryDto: CreateAiRaceResultSummaryDto): Long

    fun findLatestBySeasonAndRoundAndChatModel(
        season: Int,
        round: Int,
        chatModel: OpenAiApi.ChatModel,
    ): AiRaceResultSummaryDto?

    fun findById(id: Long): AiRaceResultSummaryDto?
}
