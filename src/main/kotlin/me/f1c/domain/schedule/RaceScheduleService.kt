package me.f1c.domain.schedule

import me.f1c.port.schedule.RaceScheduleRepository
import org.springframework.stereotype.Service

@Service
class RaceScheduleService(
    private val raceScheduleRepository: RaceScheduleRepository,
) {
    fun findAllByYearAndMonth(
        year: Int,
        month: Int,
    ): List<RaceScheduleDto> {
        require(year in 1950..2024) { "Year should be between 1950 and 2024: $year" }
        require(month in 1..12) { "Month should be between 1 and 12: $month" }
        return raceScheduleRepository.findAllByYearAndMonth(year, month)
    }
}
