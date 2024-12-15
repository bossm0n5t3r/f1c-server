package me.f1c.domain.chat

import me.f1c.domain.BaseLongIdTable

object AiRaceResultSummaries : BaseLongIdTable("ai_race_result_summaries") {
    val season = integer("season")
    val round = integer("round")
    val prompt = text("prompt")
    val summary = text("summary")
    val revision = integer("revision")
    val chatModel = varchar("chat_model", 50)
}
