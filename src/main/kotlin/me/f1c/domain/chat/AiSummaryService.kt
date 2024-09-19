package me.f1c.domain.chat

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.domain.ResponseDto
import me.f1c.port.chat.AiChat
import me.f1c.port.chat.AiSessionSummaryRepository
import me.f1c.port.chat.CreateAiSessionSummaryDto
import me.f1c.util.ObjectMapperUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class AiSummaryService(
    private val aiSessionSummaryRepository: AiSessionSummaryRepository,
    @Value("classpath:/session-summary-prompt-template.st")
    private val sessionSummaryPromptTemplate: Resource,
    private val aiChat: AiChat,
) {
    fun createSessionSummary(
        sessionKey: Int,
        vararg parameters: Pair<String, String>,
    ): AiSessionSummaryDto = createSessionSummary(sessionKey, parameters.toMap())

    fun createSessionSummary(
        sessionKey: Int,
        parameters: Map<String, String>,
    ): AiSessionSummaryDto {
        val answer = aiChat.chatWithClient(sessionSummaryPromptTemplate, parameters)
        val sessionSummary = ObjectMapperUtil.objectMapper.readValue<ResponseDto<List<String>>>(answer)
        var summaryRequest = sessionSummaryPromptTemplate.getContentAsString(StandardCharsets.UTF_8)
        for ((key, value) in parameters) {
            summaryRequest = summaryRequest.replace("{$key}", value)
        }

        val createAiSessionSummaryDto =
            CreateAiSessionSummaryDto(
                sessionKey,
                summaryRequest,
                ObjectMapperUtil.objectMapper.writeValueAsString(sessionSummary.data),
            )

        val savedId = aiSessionSummaryRepository.save(createAiSessionSummaryDto)
        val saved = aiSessionSummaryRepository.findById(savedId)

        return requireNotNull(saved)
    }

    fun findLatestSessionSummaryBySessionKey(sessionKey: Int): AiSessionSummaryDto? =
        aiSessionSummaryRepository.findLatestBySessionKey(sessionKey)
}
