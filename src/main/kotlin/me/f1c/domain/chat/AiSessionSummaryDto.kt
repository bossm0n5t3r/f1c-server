package me.f1c.domain.chat

import org.springframework.ai.openai.api.OpenAiApi

data class AiSessionSummaryDto(
    val id: Long,
    val sessionKey: Int,
    val prompt: String,
    val summary: String,
    val revision: Int,
    val chatModel: OpenAiApi.ChatModel,
)
