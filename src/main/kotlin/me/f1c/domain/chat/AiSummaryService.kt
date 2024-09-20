package me.f1c.domain.chat

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
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
        parameters: Map<String, String>,
    ): AiSessionSummaryDto =
        try {
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

            requireNotNull(
                saved,
            ).also { LOGGER.info("${LogResult.SUCCEEDED} createSessionSummary: {}, {}", sessionKey, it.revision) }
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} createSessionSummary: {}, {}, ", sessionKey, e.message, e)
            throw e
        }

    fun updateSessionSummary(
        aiSessionSummaryDto: AiSessionSummaryDto,
        parameters: Map<String, String>,
    ): AiSessionSummaryDto =
        try {
            var summaryRequest = sessionSummaryPromptTemplate.getContentAsString(StandardCharsets.UTF_8)
            for ((key, value) in parameters) {
                summaryRequest = summaryRequest.replace("{$key}", value)
            }
            val prompt =
                if (summaryRequest != aiSessionSummaryDto.prompt) {
                    summaryRequest
                } else {
                    aiSessionSummaryDto.prompt
                }
            val answer = aiChat.chatWithClient(prompt)
            val sessionSummary = ObjectMapperUtil.objectMapper.readValue<ResponseDto<List<String>>>(answer)

            val createAiSessionSummaryDto =
                CreateAiSessionSummaryDto(
                    aiSessionSummaryDto.sessionKey,
                    prompt,
                    ObjectMapperUtil.objectMapper.writeValueAsString(sessionSummary.data),
                    aiSessionSummaryDto.revision + 1,
                )

            val savedId = aiSessionSummaryRepository.save(createAiSessionSummaryDto)
            val saved = aiSessionSummaryRepository.findById(savedId)

            requireNotNull(saved).also {
                LOGGER.info(
                    "${LogResult.SUCCEEDED} updateSessionSummary: {}, {}, {}",
                    aiSessionSummaryDto.sessionKey,
                    aiSessionSummaryDto.revision,
                    it.revision,
                )
            }
        } catch (e: Exception) {
            LOGGER.error(
                "${LogResult.FAILED} updateSessionSummary: {}, {}, {}, ",
                aiSessionSummaryDto.sessionKey,
                aiSessionSummaryDto.revision,
                e.message,
                e,
            )
            throw e
        }

    fun findLatestSessionSummaryBySessionKey(sessionKey: Int): AiSessionSummaryDto? =
        aiSessionSummaryRepository.findLatestBySessionKey(sessionKey)
}
