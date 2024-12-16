package me.f1c.port.schedule

import me.f1c.domain.schedule.RaceScheduleDto
import me.f1c.domain.schedule.RaceType

interface RaceScheduleRepository {
    fun findAll(): List<RaceScheduleDto>

    fun findLatest(raceType: RaceType): RaceScheduleDto?

    fun findLatestFinished(raceType: RaceType): RaceScheduleDto?

    fun batchInsert(raceScheduleDtoList: List<RaceScheduleDto>): Int

    fun findAllByRaceType(raceType: String): List<RaceScheduleDto>

    fun findAllByYearAndMonth(
        year: Int,
        month: Int,
    ): List<RaceScheduleDto>
}
