package me.f1c.domain.chat

import org.springframework.ai.openai.api.OpenAiApi

fun AiRaceResultSummaryEntity.toDto() =
    AiRaceResultSummaryDto(
        season,
        round,
        prompt,
        summary,
        revision,
        OpenAiApi.ChatModel.valueOf(chatModel),
    )
