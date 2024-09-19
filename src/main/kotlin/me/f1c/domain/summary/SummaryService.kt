package me.f1c.domain.summary

import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.chat.AiSessionSummaryDto
import me.f1c.domain.chat.AiSummaryService
import me.f1c.port.driver.DriverRepository
import me.f1c.port.position.PositionRepository
import org.springframework.stereotype.Service

@Service
class SummaryService(
    private val positionRepository: PositionRepository,
    private val driverRepository: DriverRepository,
    private val aiSummaryService: AiSummaryService,
) {
    fun createSessionSummary(sessionKey: Int): AiSessionSummaryDto =
        try {
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

            val result =
                aiSummaryService.createSessionSummary(
                    sessionKey,
                    "data" to promptData.toString(),
                )
            LOGGER.info("${LogResult.SUCCEEDED} createSessionSummary: {}", sessionKey)
            result
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} createSessionSummary: {}, {}, ", sessionKey, e.message, e)
            throw e
        }

    fun getSessionSummary(sessionKey: Int): AiSessionSummaryDto =
        try {
            val result = requireNotNull(aiSummaryService.findLatestSessionSummaryBySessionKey(sessionKey))
            LOGGER.info("${LogResult.SUCCEEDED} getSessionSummary: {}", sessionKey)
            result
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} getSessionSummary: {}, {}, ", sessionKey, e.message, e)
            throw e
        }
}
