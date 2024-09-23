package me.f1c.domain.chat

import me.f1c.domain.BaseLongIdTable

object AiSessionSummaries : BaseLongIdTable("ai_session_summaries") {
    val sessionKey = integer("session_key")
    val prompt = text("prompt")
    val summary = text("summary")
    val revision = integer("revision")
    val chatModel = varchar("chat_model", 50)
}
