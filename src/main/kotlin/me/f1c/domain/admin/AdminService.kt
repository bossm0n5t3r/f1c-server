package me.f1c.domain.admin

import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.driver.DriverService
import me.f1c.domain.position.PositionService
import me.f1c.domain.result.RaceResultService
import me.f1c.domain.schedule.RaceScheduleService
import me.f1c.domain.session.SessionService
import me.f1c.domain.summary.SummaryService
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val sessionService: SessionService,
    private val driverService: DriverService,
    private val positionService: PositionService,
    private val summaryService: SummaryService,
    private val raceScheduleService: RaceScheduleService,
    private val raceResultService: RaceResultService,
) {
    fun upToDate() =
        try {
            sessionService.upToDate()

            // Get All Session Keys
            val allSessionKeys = sessionService.findAll().map { it.sessionKey }.distinct()
            val updatedSessionKeyFromDriver = mutableListOf<Int>()
            val updatedSessionKeyFromPosition = mutableListOf<Int>()
            val updatedSessionKeyFromSummary = mutableListOf<Int>()
            for (sessionKey in allSessionKeys) {
                if (positionService.findAllBySessionKey(sessionKey).isEmpty()) {
                    positionService.upToDate(sessionKey)
                    updatedSessionKeyFromPosition.add(sessionKey)
                }
                if (summaryService.getSessionSummary(sessionKey).isEmpty()) {
                    summaryService.createSessionSummary(sessionKey)
                    updatedSessionKeyFromSummary.add(sessionKey)
                }
            }
            LOGGER.info(
                "${LogResult.SUCCEEDED} upToDate: {}, {}, {}",
                updatedSessionKeyFromDriver.size,
                updatedSessionKeyFromPosition.size,
                updatedSessionKeyFromSummary.size,
            )
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} upToDate: {}, ", e.message, e)
            throw e
        }

    fun upToDateRaceSchedule(): Int = raceScheduleService.upToDate()

    fun upToDateRaceResult(): Int = raceResultService.upToDate()

    fun upToDateDriver(): Int = driverService.upToDate()
}
