package me.f1c.domain.result

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.f1c.adapter.external.JolpicaF1ClientImpl
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.jolpica.DateTime
import me.f1c.domain.jolpica.JolpicaF1ResponseDto
import me.f1c.domain.jolpica.MRDataWithRaceTable
import me.f1c.domain.jolpica.RaceDto
import me.f1c.domain.jolpica.ResultDto
import me.f1c.domain.jolpica.SeasonAndRound
import me.f1c.domain.jolpica.toRaceDateTimeOrGivenTime
import me.f1c.domain.schedule.RaceScheduleService
import me.f1c.port.external.callGet
import me.f1c.port.result.RaceResultRepository
import me.f1c.util.Constants.END_ROUND
import me.f1c.util.Constants.START_ROUND
import org.springframework.stereotype.Service

@Service
class RaceResultService(
    private val jolpicaF1Client: JolpicaF1ClientImpl,
    private val raceScheduleService: RaceScheduleService,
    private val raceResultRepository: RaceResultRepository,
) {
    fun upToDate(): Int {
        return try {
            val latestFinishedRaceSchedule = raceScheduleService.findLatestFinished()
            val latestFinishedSeasonAndRound = latestFinishedRaceSchedule?.toSeasonAndRound()
            val latestSeasonAndRound = raceResultRepository.findLatest()?.toSeasonAndRound()
            if (latestFinishedSeasonAndRound != null &&
                latestSeasonAndRound != null &&
                latestFinishedSeasonAndRound >= latestSeasonAndRound
            ) {
                LOGGER.info(
                    "{} upToDate: {}, {}, {}",
                    LogResult.SUCCEEDED,
                    "Already up-to-date",
                    latestFinishedSeasonAndRound,
                    latestSeasonAndRound,
                )
                return 0
            }
            val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            val season = now.year
            val startSeasonAndRound = latestFinishedSeasonAndRound ?: SeasonAndRound(season, START_ROUND)
            val endSeasonAndRound = latestSeasonAndRound ?: SeasonAndRound(season, END_ROUND)
            var totalCount = 0
            for (seasonAndRound in startSeasonAndRound..endSeasonAndRound) {
                if (latestFinishedRaceSchedule != null && seasonAndRound == startSeasonAndRound) continue
                totalCount += callResultApiAndBatchInsert(seasonAndRound.season, seasonAndRound.round, now)
            }
            totalCount.also { LOGGER.info("{} upToDate: {}, {}, {}", LogResult.SUCCEEDED, startSeasonAndRound, endSeasonAndRound, it) }
        } catch (e: Exception) {
            LOGGER.error("{} upToDate: {}, ", LogResult.FAILED, e.message, e)
            throw e
        }
    }

    private fun callResultApiAndBatchInsert(
        season: Int,
        round: Int,
        now: LocalDateTime,
    ): Int {
        val resultApi = jolpicaF1Client.getResultApi(season, round)
        val resultResponseDto =
            requireNotNull(
                jolpicaF1Client.callGet<JolpicaF1ResponseDto<MRDataWithRaceTable>>(resultApi),
            ) { "JolpicaF1ResponseDto<MRDataWithRaceTable> does not exist" }
        val raceDto =
            resultResponseDto.mrData.raceTable.races
                .firstOrNull() ?: return 0
        val raceResultDtoList = raceDto.toRaceResultDtoList(now)
        return raceResultRepository.batchInsert(raceResultDtoList)
    }

    private fun RaceDto.toRaceResultDtoList(now: LocalDateTime): List<RaceResultDto> {
        val resultsInRaceDto = this.results
        if (resultsInRaceDto.isNullOrEmpty()) return emptyList()

        val season = this.season.toInt()
        val round = this.round.toInt()
        val url = this.url
        val raceName = this.raceName
        val circuitId = this.circuit.circuitId
        val circuitName = this.circuit.circuitName
        val raceDateTime = DateTime(this.date, this.time).toRaceDateTimeOrGivenTime(now).toString()
        return resultsInRaceDto.map { it.toRaceResultDto(season, round, url, raceName, circuitId, circuitName, raceDateTime) }
    }

    private fun ResultDto.toRaceResultDto(
        season: Int,
        round: Int,
        url: String,
        raceName: String,
        circuitId: String,
        circuitName: String,
        raceDateTime: String,
    ): RaceResultDto =
        RaceResultDto(
            season,
            round,
            url,
            raceName,
            circuitId,
            circuitName,
            raceDateTime,
            this.position.toInt(),
            this.driver.driverId,
            this.constructor.constructorId,
            this.status,
            this.time?.millis?.toLong(),
            this.time?.time,
            this.fastestLap?.rank?.toInt(),
            this.fastestLap?.lap,
            this.fastestLap?.time?.time,
            this.fastestLap?.averageSpeed?.units,
            this.fastestLap?.averageSpeed?.speed,
        )

    fun findAllBySeasonAndRound(
        season: Int,
        round: Int,
    ): List<RaceResultDto> = raceResultRepository.findAllBySeasonAndRound(season, round)
}
