package me.f1c.domain.admin

import me.f1c.domain.chat.AiRaceResultSummaryDto
import me.f1c.domain.circuit.CircuitService
import me.f1c.domain.constructor.ConstructorService
import me.f1c.domain.driver.DriverService
import me.f1c.domain.lap.LapService
import me.f1c.domain.result.RaceResultService
import me.f1c.domain.schedule.RaceScheduleService
import me.f1c.domain.summary.SummaryService
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val driverService: DriverService,
    private val raceScheduleService: RaceScheduleService,
    private val raceResultService: RaceResultService,
    private val constructorService: ConstructorService,
    private val circuitService: CircuitService,
    private val lapService: LapService,
    private val summaryService: SummaryService,
) {
    fun upToDateRaceSchedule(): Int = raceScheduleService.upToDate()

    fun upToDateRaceResult(): Int = raceResultService.upToDate()

    fun upToDateDriver(): Int = driverService.upToDate()

    fun upToDateConstructor(): Int = constructorService.upToDate()

    fun upToDateCircuit(): Int = circuitService.upToDate()

    fun upToDateAllLaps() = lapService.upToDateAll()

    fun upToDateLaps(
        season: Int,
        round: Int,
    ): Int = lapService.upToDate(season, round)

    fun createRaceResultSummary(
        season: Int,
        round: Int,
    ): AiRaceResultSummaryDto = summaryService.createRaceResultSummary(season, round)

    fun updateRaceResultSummary(
        season: Int,
        round: Int,
    ): AiRaceResultSummaryDto = summaryService.updateRaceResultSummary(season, round)
}
