package me.f1c.domain.chat

data class AiSessionSummaryDto(
    val id: Long,
    val sessionKey: Int,
    val prompt: String,
    val summary: String,
    val revision: Int,
)
