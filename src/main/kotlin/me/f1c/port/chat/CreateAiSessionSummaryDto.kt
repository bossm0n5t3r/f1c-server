package me.f1c.port.chat

data class CreateAiSessionSummaryDto(
    val sessionKey: Int,
    val prompt: String,
    val summary: String,
    val revision: Int = 1,
)
