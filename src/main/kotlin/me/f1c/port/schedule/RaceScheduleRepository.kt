package me.f1c.port.schedule

import me.f1c.domain.schedule.RaceScheduleDto

interface RaceScheduleRepository {
    fun findAll(): List<RaceScheduleDto>

    fun findLatest(): RaceScheduleDto?

    fun batchInsert(raceScheduleDtoList: List<RaceScheduleDto>): Int

    fun findAllByRaceType(raceType: String): List<RaceScheduleDto>

    fun findAllByYearAndMonth(
        year: Int,
        month: Int,
    ): List<RaceScheduleDto>
}
