package me.f1c.domain.schedule

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import me.f1c.adapter.external.JolpicaF1ClientImpl
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.jolpica.DateTime
import me.f1c.domain.jolpica.JolpicaF1ResponseDto
import me.f1c.domain.jolpica.MRDataWithRaceTable
import me.f1c.domain.jolpica.Race
import me.f1c.domain.jolpica.toRaceDateTimeOrGivenTime
import me.f1c.exception.F1CBadRequestException
import me.f1c.port.external.callGet
import me.f1c.port.schedule.RaceScheduleRepository
import me.f1c.util.DateTimeUtil.SERVER_TIME_ZONE
import org.springframework.stereotype.Service

@Service
class RaceScheduleService(
    private val jolpicaF1Client: JolpicaF1ClientImpl,
    private val raceScheduleRepository: RaceScheduleRepository,
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
            val raceApi = jolpicaF1Client.getRaceApi(year)
            val raceResponseDto =
                requireNotNull(
                    jolpicaF1Client.callGet<JolpicaF1ResponseDto<MRDataWithRaceTable>>(raceApi),
                ) { "JolpicaF1ResponseDto<MRDataWithRaceTable> does not exist" }
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

    private fun Race.toRaceScheduleDtoList(now: LocalDateTime): List<RaceScheduleDto> =
        listOfNotNull(
            this.firstPractice?.let { this.toRaceScheduleDto("FirstPractice", it, now) },
            this.secondPractice?.let { this.toRaceScheduleDto("SecondPractice", it, now) },
            this.thirdPractice?.let { this.toRaceScheduleDto("ThirdPractice", it, now) },
            this.qualifying?.let { this.toRaceScheduleDto("Qualifying", it, now) },
            this.sprint?.let { this.toRaceScheduleDto("Sprint", it, now) },
            this.sprintQualifying?.let { this.toRaceScheduleDto("SprintQualifying", it, now) },
            this.sprintShootout?.let { this.toRaceScheduleDto("SprintShootout", it, now) },
            this.toRaceScheduleDto("Race", DateTime(this.date, this.time), now),
        )

    private fun Race.toRaceScheduleDto(
        raceType: String,
        raceDateTime: DateTime,
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
            raceDateTime.toRaceDateTimeOrGivenTime(now).toString(),
        )
}
