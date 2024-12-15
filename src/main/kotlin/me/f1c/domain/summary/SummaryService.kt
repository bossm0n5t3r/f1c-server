package me.f1c.domain.summary

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.chat.AiRaceResultSummaryDto
import me.f1c.domain.chat.AiSummaryService
import me.f1c.domain.driver.DriverInfo
import me.f1c.exception.F1CInternalServerErrorException
import me.f1c.port.lap.LapRepository
import me.f1c.port.result.RaceResultRepository
import me.f1c.util.ObjectMapperUtil
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SummaryService(
    private val lapRepository: LapRepository,
    private val raceResultRepository: RaceResultRepository,
    private val aiSummaryService: AiSummaryService,
    @Value("\${spring.ai.openai.chat.options.model}") private val chatModel: OpenAiApi.ChatModel,
) {
    fun createRaceResultSummary(
        season: Int,
        round: Int,
    ): AiRaceResultSummaryDto =
        try {
            val previousRaceResultSummary =
                aiSummaryService.findLatestRaceResultSummaryBySeasonAndRoundAndChatModel(
                    season,
                    round,
                    chatModel,
                )
            require(previousRaceResultSummary == null) { "Session Summary Already Exists" }

            val raceResultSummaryParameters = getRaceResultSummaryParameters(season, round)

            val summary = aiSummaryService.generateRaceResultSummary(raceResultSummaryParameters)

            val result =
                aiSummaryService.createRaceResultSummary(
                    season,
                    round,
                    raceResultSummaryParameters,
                    summary,
                )
            LOGGER.info("{} createRaceResultSummary: {}, {}", LogResult.SUCCEEDED, season, round)
            result
        } catch (e: Exception) {
            LOGGER.error("{} createRaceResultSummary: {}, {}, {}, ", LogResult.FAILED, season, round, e.message, e)
            throw F1CInternalServerErrorException(e)
        }

    private fun getRaceResultSummaryParameters(
        season: Int,
        round: Int,
    ): Map<String, String> {
        val positions =
            lapRepository
                .findAllBySeasonAndRound(season, round)
                .sortedBy { it.lapNumber }
                .map {
                    "lap ${it.lapNumber}: ${it.positions.joinToString { (driverId, position, time) ->
                        "$position - ${DriverInfo.findByDriverIdOrNull(driverId)?.koreanDriverName} ($time)"
                    }}"
                }

        val ranking =
            raceResultRepository
                .findAllBySeasonAndRound(season, round)
                .sortedBy { it.position }
                .joinToString {
                    "${it.position} - ${DriverInfo.findByDriverIdOrNull(it.driverId)?.koreanDriverName}"
                }

        return mapOf(
            "positions" to positions.joinToString("\n"),
            "ranking" to ranking,
        )
    }

    fun getRaceResultSummary(
        season: Int,
        round: Int,
    ): List<String> =
        try {
            val result =
                aiSummaryService
                    .findLatestRaceResultSummaryBySeasonAndRoundAndChatModel(season, round, chatModel)
                    ?.summary
                    ?.let { ObjectMapperUtil.objectMapper.readValue<List<String>>(it) }
                    ?: emptyList()
            LOGGER.info("{} getRaceResultSummary: {}, {}", LogResult.SUCCEEDED, season, round)
            result
        } catch (e: Exception) {
            LOGGER.error("{} getRaceResultSummary: {}, {}, {}, ", LogResult.FAILED, season, round, e.message, e)
            emptyList()
        }

    fun updateRaceResultSummary(
        season: Int,
        round: Int,
    ): AiRaceResultSummaryDto =
        try {
            val previousRaceResultSummary =
                aiSummaryService.findLatestRaceResultSummaryBySeasonAndRoundAndChatModel(season, round, chatModel)
                    ?: error("Not found latest session summary by session key")

            val raceResultSummaryParameters = getRaceResultSummaryParameters(season, round)
            aiSummaryService
                .updateRaceResultSummary(previousRaceResultSummary, raceResultSummaryParameters)
                .also { LOGGER.info("{} updateRaceResultSummary: {}, {}, {}", LogResult.SUCCEEDED, season, round, it.revision) }
        } catch (e: Exception) {
            LOGGER.error("{} updateRaceResultSummary: {}, {}, {}, ", LogResult.FAILED, season, round, e.message, e)
            throw F1CInternalServerErrorException(e)
        }
}
