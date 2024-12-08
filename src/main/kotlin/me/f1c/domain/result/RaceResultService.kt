package me.f1c.domain.result

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.f1c.adapter.external.JolpicaF1ClientImpl
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.jolpica.JolpicaF1ResponseDto
import me.f1c.domain.jolpica.MRDataWithRaceTable
import me.f1c.domain.jolpica.SeasonAndRound
import me.f1c.domain.schedule.RaceScheduleService
import me.f1c.port.circuit.CircuitRepository
import me.f1c.port.constructor.ConstructorRepository
import me.f1c.port.driver.DriverRepository
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
    private val driverRepository: DriverRepository,
    private val constructorRepository: ConstructorRepository,
    private val circuitRepository: CircuitRepository,
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

    fun getRankings(
        season: Int,
        round: Int,
    ): RankingDto =
        try {
            val raceResults = raceResultRepository.findAllBySeasonAndRound(season, round).sortedBy { it.position }
            require(raceResults.isNotEmpty()) { "RaceResults does not exist" }
            val drivers = driverRepository.findAllBySeason(season)
            require(drivers.isNotEmpty()) { "Drivers does not exist" }
            val constructors = constructorRepository.findAllBySeason(season)
            require(constructors.isNotEmpty()) { "Constructors does not exist" }

            val driverIdToDriver = drivers.associateBy { it.driverId }
            val constructorIdToConstructor = constructors.associateBy { it.constructorId }

            val firstRaceResult = raceResults.first()
            val url = firstRaceResult.url
            val raceName = firstRaceResult.raceName
            val circuitId = firstRaceResult.circuitId

            val circuit = circuitRepository.findBySeasonAndCircuitIdOrNull(season, circuitId) ?: error("Circuit does not exist: $circuitId")

            val raceDateTime = firstRaceResult.raceDatetime
            val rankedDrivers =
                raceResults.map {
                    val driver = driverIdToDriver[it.driverId] ?: error("Driver does not exist: ${it.driverId}")
                    val constructor =
                        constructorIdToConstructor[it.constructorId] ?: error("Constructor does not exist: ${it.constructorId}")
                    RankedDriverDto(
                        driver = driver,
                        position = it.position,
                        constructor = constructor,
                        status = it.status,
                        timeText = it.timeText,
                    )
                }

            RankingDto(
                season = season,
                round = round,
                url = url,
                raceName = raceName,
                circuit = circuit,
                raceDatetime = raceDateTime,
                drivers = rankedDrivers,
            ).also { LOGGER.info("{} getRanking: {}, {}, {}", LogResult.SUCCEEDED, season, round, it) }
        } catch (e: Exception) {
            LOGGER.error("{} getRanking: {}, {}, {}, ", LogResult.FAILED, season, round, e.message, e)
            throw e
        }
}
