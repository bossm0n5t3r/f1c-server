package me.f1c.domain.schedule

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.JolpicaF1API
import me.f1c.exception.F1CBadRequestException
import me.f1c.port.schedule.RaceScheduleRepository
import me.f1c.util.DateTimeUtil.SERVER_TIME_ZONE
import me.f1c.util.DateTimeUtil.toKotlinLocalDateTime
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class RaceScheduleService(
    private val restClient: RestClient,
    private val raceScheduleRepository: RaceScheduleRepository,
    private val objectMapper: ObjectMapper,
) {
    fun findAllByYearAndMonth(
        year: Int,
        month: Int,
    ): List<RaceScheduleDto> =
        try {
            require(year in 1950..2024) { "Year should be between 1950 and 2024: $year" }
            require(month in 1..12) { "Month should be between 1 and 12: $month" }
            raceScheduleRepository
                .findAllByYearAndMonth(year, month)
                .also { LOGGER.info("{} findAllByYearAndMonth: {}, {}, {}", LogResult.SUCCEEDED, year, month, it.size) }
        } catch (e: Exception) {
            LOGGER.error("{} findAllByYearAndMonth: {}, {}, {}", LogResult.FAILED, year, month, e.message)
            when (e) {
                is IllegalArgumentException -> throw F1CBadRequestException(e)
                else -> throw e
            }
        }

    fun findLatest(): RaceScheduleDto? =
        try {
            raceScheduleRepository
                .findLatest()
                .also { LOGGER.info("{} findLatest", LogResult.SUCCEEDED) }
        } catch (e: Exception) {
            LOGGER.error("{} findLatest: {}", LogResult.FAILED, e.message)
            throw e
        }

    fun findLatestFinished(): RaceScheduleDto? =
        try {
            raceScheduleRepository
                .findLatestFinished()
                .also { LOGGER.info("{} findLatestFinished", LogResult.SUCCEEDED) }
        } catch (e: Exception) {
            LOGGER.error("{} findLatestFinished: {}", LogResult.FAILED, e.message)
            throw e
        }

    fun upToDate(): Int =
        try {
            val now =
                Clock.System
                    .now()
                    .toLocalDateTime(SERVER_TIME_ZONE)
            val year = now.year
            val raceApi = JolpicaF1API.getRaceApi(year)
            val rawResponse =
                restClient
                    .get()
                    .uri(raceApi)
                    .retrieve()
                    .toEntity(String::class.java)
            val bodyString = rawResponse.body ?: error("Body does not exist")
            val raceResponseDto = objectMapper.readValue<JolpicaF1RaceResponseDto>(bodyString)
            val raceScheduleDtoList =
                raceResponseDto
                    .mrData
                    .raceTable
                    .races
                    .flatMap { it.toRaceScheduleDtoList(now) }
            raceScheduleRepository
                .batchInsert(raceScheduleDtoList)
                .also { LOGGER.info("{} upToDate: {}", LogResult.SUCCEEDED, it) }
        } catch (e: Exception) {
            LOGGER.error("{} upToDate: {}, ", LogResult.FAILED, e.message, e)
            throw e
        }

    private fun JolpicaF1RaceDto.toRaceScheduleDtoList(now: LocalDateTime): List<RaceScheduleDto> =
        listOfNotNull(
            this.firstPractice?.let { this.toRaceScheduleDto("FirstPractice", it, now) },
            this.secondPractice?.let { this.toRaceScheduleDto("SecondPractice", it, now) },
            this.thirdPractice?.let { this.toRaceScheduleDto("ThirdPractice", it, now) },
            this.qualifying?.let { this.toRaceScheduleDto("Qualifying", it, now) },
            this.sprint?.let { this.toRaceScheduleDto("Sprint", it, now) },
            this.sprintQualifying?.let { this.toRaceScheduleDto("SprintQualifying", it, now) },
            this.sprintShootout?.let { this.toRaceScheduleDto("SprintShootout", it, now) },
            this.toRaceScheduleDto("Race", JolpicaF1RaceDto.DateTime(this.date, this.time), now),
        )

    private fun JolpicaF1RaceDto.toRaceScheduleDto(
        raceType: String,
        raceDateTime: JolpicaF1RaceDto.DateTime,
        now: LocalDateTime,
    ): RaceScheduleDto =
        RaceScheduleDto(
            this.season.toInt(),
            this.round.toInt(),
            this.url,
            this.raceName,
            this.circuit.circuitId,
            this.circuit.circuitName,
            raceType,
            (raceDateTime.toRaceDatetime() ?: now).toString(),
        )

    private fun JolpicaF1RaceDto.DateTime.toRaceDatetime(): LocalDateTime? =
        try {
            "${this.date}T${this.time}".toKotlinLocalDateTime()
        } catch (e: Exception) {
            LOGGER.warn("Failed JolpicaF1RaceDto.DateTime.toRaceDatetime: {}, {}", this.date, this.time)
            null
        }
}
