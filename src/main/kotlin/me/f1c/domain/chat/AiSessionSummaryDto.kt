package me.f1c.domain.chat

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.util.ObjectMapperUtil

data class AiSessionSummaryDto(
    val id: Long,
    val sessionKey: Int,
    val prompt: String,
    val summary: String,
    val revision: Int,
) {
    val parsedSummary: List<String> by lazy {
        ObjectMapperUtil.objectMapper.readValue<List<String>>(summary)
    }
}
