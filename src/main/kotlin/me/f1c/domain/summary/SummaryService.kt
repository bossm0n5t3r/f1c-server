package me.f1c.domain.summary

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.chat.AiSessionSummaryDto
import me.f1c.domain.chat.AiSummaryService
import me.f1c.domain.session.SessionService
import me.f1c.exception.F1CInternalServerErrorException
import me.f1c.port.driver.DriverRepository
import me.f1c.port.position.PositionRepository
import me.f1c.util.ObjectMapperUtil
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SummaryService(
    private val positionRepository: PositionRepository,
    private val driverRepository: DriverRepository,
    private val aiSummaryService: AiSummaryService,
    private val sessionService: SessionService,
    @Value("\${spring.ai.openai.chat.options.model}") private val chatModel: OpenAiApi.ChatModel,
) {
    fun createSessionSummary(sessionKey: Int): AiSessionSummaryDto =
        try {
            val previousSessionSummary = aiSummaryService.findLatestSessionSummaryBySessionKeyAndChatModel(sessionKey, chatModel)
            require(previousSessionSummary == null) { "Session Summary Already Exists" }

            val sessionSummaryParameters = getSessionSummaryParameters(sessionKey)

            val summary = aiSummaryService.generateSessionSummary(sessionKey, sessionSummaryParameters)

            val result =
                aiSummaryService.createSessionSummary(
                    sessionKey,
                    sessionSummaryParameters,
                    summary,
                )
            LOGGER.info("${LogResult.SUCCEEDED} createSessionSummary: {}", sessionKey)
            result
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} createSessionSummary: {}, {}, ", sessionKey, e.message, e)
            throw F1CInternalServerErrorException(e)
        }

    private fun getSessionSummaryParameters(sessionKey: Int): Map<String, String> {
        val positions =
            positionRepository
                .findAllBySessionKey(sessionKey)
                .takeIf { it.isNotEmpty() }
                ?: error("Not found positions")

        val driverNumberToSortedPositions =
            positions.groupBy { it.driverNumber }.mapValues { (_, positionsByDriver) ->
                positionsByDriver.sortedBy { it.date }.map { it.position }
            }
        val driverNumberToDriver = driverRepository.findAllBySessionKey(sessionKey).associateBy { it.driverNumber }

        val promptData = StringBuilder()
        for ((driverNumber, sortedPositions) in driverNumberToSortedPositions) {
            promptData.append("driver_name: '${driverNumberToDriver[driverNumber]?.fullName}', ")
            promptData.append("positions: $sortedPositions, ")
            promptData.append("ranking: ${sortedPositions.last()}\n ")
        }

        return mapOf("data" to promptData.toString())
    }

    fun getSessionSummary(sessionKey: Int): List<String> =
        try {
            val result =
                aiSummaryService
                    .findLatestSessionSummaryBySessionKeyAndChatModel(sessionKey, chatModel)
                    ?.summary
                    ?.let { ObjectMapperUtil.objectMapper.readValue<List<String>>(it) }
                    ?: emptyList()
            LOGGER.info("${LogResult.SUCCEEDED} getSessionSummary: {}", sessionKey)
            result
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} getSessionSummary: {}, {}, ", sessionKey, e.message, e)
            emptyList()
        }

    fun updateSessionSummary(sessionKey: Int): AiSessionSummaryDto =
        try {
            val previousSessionSummary =
                aiSummaryService.findLatestSessionSummaryBySessionKeyAndChatModel(sessionKey, chatModel)
                    ?: error("Not found latest session summary by session key")

            val sessionSummaryParameters = getSessionSummaryParameters(sessionKey)
            aiSummaryService
                .updateSessionSummary(previousSessionSummary, sessionSummaryParameters)
                .also { LOGGER.info("${LogResult.SUCCEEDED} updateSessionSummary: {}, {}", sessionKey, it.revision) }
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} updateSessionSummary: {}, {}, ", sessionKey, e.message, e)
            throw F1CInternalServerErrorException(e)
        }

    fun validateSessionSummaries() {
        val allSessionKeys = sessionService.findAll().map { it.sessionKey }.distinct()

        var validSessionKeyCount = 0
        var invalidSessionKeyCount = 0
        for (sessionKey in allSessionKeys) {
            if (validateSessionSummary(sessionKey)) {
                validSessionKeyCount++
            } else {
                invalidSessionKeyCount++
            }
        }
        LOGGER.info("${LogResult.SUCCEEDED} validateSessionSummaries: {}, {}", validSessionKeyCount, invalidSessionKeyCount)
    }

    fun validateSessionSummary(sessionKey: Int): Boolean =
        try {
            val result =
                aiSummaryService.findLatestSessionSummaryBySessionKeyAndChatModel(sessionKey, chatModel)
                    ?: error("Not found latest session summary by session key")
            val parsedSummary = ObjectMapperUtil.objectMapper.readValue<List<String>>(result.summary)
            require(parsedSummary.isNotEmpty())
            true
        } catch (e: Exception) {
            LOGGER.warn("${LogResult.FAILED} validateSessionSummary: {}, {}, ", sessionKey, e.message, e)
            false
        }
}
