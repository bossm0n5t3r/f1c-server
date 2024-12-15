package me.f1c.domain.chat

import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.port.chat.AiChat
import me.f1c.port.chat.AiRaceResultSummaryRepository
import me.f1c.port.chat.CreateAiRaceResultSummaryDto
import me.f1c.port.chat.GeneratedSummaryDto
import me.f1c.util.ObjectMapperUtil
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

@Service
class AiSummaryService(
    private val aiRaceResultSummaryRepository: AiRaceResultSummaryRepository,
    @Value("classpath:/race-result-summary-prompt-template.st")
    private val raceResultSummaryPromptTemplate: Resource,
    private val aiChat: AiChat,
    @Value("\${spring.ai.openai.chat.options.model}") private val chatModel: OpenAiApi.ChatModel,
) {
    fun generateRaceResultSummary(parameters: Map<String, String>) =
        aiChat.generateSummaryWithClient(raceResultSummaryPromptTemplate, parameters)

    fun createRaceResultSummary(
        season: Int,
        round: Int,
        parameters: Map<String, String>,
        generatedSummaryDto: GeneratedSummaryDto,
    ): AiRaceResultSummaryDto =
        try {
            var summaryRequest = raceResultSummaryPromptTemplate.getContentAsString(StandardCharsets.UTF_8)
            for ((key, value) in parameters) {
                summaryRequest = summaryRequest.replace("{$key}", value)
            }

            val createAiRaceResultSummaryDto =
                CreateAiRaceResultSummaryDto(
                    season,
                    round,
                    summaryRequest,
                    ObjectMapperUtil.objectMapper.writeValueAsString(generatedSummaryDto.data),
                    1,
                    chatModel,
                )

            val savedId = aiRaceResultSummaryRepository.save(createAiRaceResultSummaryDto)
            val saved = aiRaceResultSummaryRepository.findById(savedId)

            requireNotNull(
                saved,
            ).also { LOGGER.info("{} createRaceResultSummary: {}, {}, {}", LogResult.SUCCEEDED, season, round, it.revision) }
        } catch (e: Exception) {
            LOGGER.error("{} createRaceResultSummary: {}, {}, {}, ", LogResult.FAILED, season, round, e.message, e)
            throw e
        }

    fun updateRaceResultSummary(
        aiRaceResultSummaryDto: AiRaceResultSummaryDto,
        parameters: Map<String, String>,
    ): AiRaceResultSummaryDto =
        try {
            var summaryRequest = raceResultSummaryPromptTemplate.getContentAsString(StandardCharsets.UTF_8)
            for ((key, value) in parameters) {
                summaryRequest = summaryRequest.replace("{$key}", value)
            }
            val prompt =
                if (summaryRequest != aiRaceResultSummaryDto.prompt) {
                    summaryRequest
                } else {
                    aiRaceResultSummaryDto.prompt
                }
            val answer = aiChat.generateSummaryWithClient(prompt)

            val createAiRaceResultSummaryDto =
                CreateAiRaceResultSummaryDto(
                    aiRaceResultSummaryDto.season,
                    aiRaceResultSummaryDto.round,
                    prompt,
                    ObjectMapperUtil.objectMapper.writeValueAsString(answer.data),
                    aiRaceResultSummaryDto.revision + 1,
                    chatModel,
                )

            val savedId = aiRaceResultSummaryRepository.save(createAiRaceResultSummaryDto)
            val saved = aiRaceResultSummaryRepository.findById(savedId)

            requireNotNull(saved).also {
                LOGGER.info(
                    "{} updateRaceResultSummary: {}, {}, {}, {}",
                    LogResult.SUCCEEDED,
                    aiRaceResultSummaryDto.season,
                    aiRaceResultSummaryDto.round,
                    aiRaceResultSummaryDto.revision,
                    it.revision,
                )
            }
        } catch (e: Exception) {
            LOGGER.error(
                "{} updateRaceResultSummary: {}, {}, {}, {}, ",
                LogResult.FAILED,
                aiRaceResultSummaryDto.season,
                aiRaceResultSummaryDto.round,
                aiRaceResultSummaryDto.revision,
                e.message,
                e,
            )
            throw e
        }

    fun findLatestRaceResultSummaryBySeasonAndRoundAndChatModel(
        season: Int,
        round: Int,
        chatModel: OpenAiApi.ChatModel,
    ): AiRaceResultSummaryDto? = aiRaceResultSummaryRepository.findLatestBySeasonAndRoundAndChatModel(season, round, chatModel)
}
