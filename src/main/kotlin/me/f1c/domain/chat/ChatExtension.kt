package me.f1c.domain.chat

import org.springframework.ai.openai.api.OpenAiApi

fun AiSessionSummaryEntity.toDto() =
    AiSessionSummaryDto(
        id.value,
        sessionKey,
        prompt,
        summary,
        revision,
        OpenAiApi.ChatModel.valueOf(chatModel),
    )
