package me.f1c.domain.chat

fun AiSessionSummaryEntity.toDto() =
    AiSessionSummaryDto(
        id.value,
        sessionKey,
        prompt,
        summary,
        revision,
    )
