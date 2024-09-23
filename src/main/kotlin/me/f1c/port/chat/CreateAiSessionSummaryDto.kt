package me.f1c.port.chat

import org.springframework.ai.openai.api.OpenAiApi

data class CreateAiSessionSummaryDto(
    val sessionKey: Int,
    val prompt: String,
    val summary: String,
    val revision: Int,
    val chatModel: OpenAiApi.ChatModel,
)
