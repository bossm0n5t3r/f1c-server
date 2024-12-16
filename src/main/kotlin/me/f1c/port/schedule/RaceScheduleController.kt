package me.f1c.port.schedule

import me.f1c.domain.ResponseDto
import me.f1c.domain.schedule.RaceScheduleDto

interface RaceScheduleController {
    fun findAllByYearAndMonth(
        year: Int,
        month: Int,
    ): ResponseDto<List<RaceScheduleDto>>

    fun findLatest(raceType: String?): ResponseDto<RaceScheduleDto?>

    fun findLatestFinished(raceType: String?): ResponseDto<RaceScheduleDto?>
}
