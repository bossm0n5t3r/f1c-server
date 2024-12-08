package me.f1c.port.schedule

import me.f1c.domain.ResponseDto
import me.f1c.domain.schedule.RaceScheduleDto

interface RaceScheduleController {
    fun findAllByYearAndMonth(
        year: Int,
        month: Int,
    ): ResponseDto<List<RaceScheduleDto>>

    fun findLatest(): ResponseDto<RaceScheduleDto?>

    fun findLatestFinished(): ResponseDto<RaceScheduleDto?>
}
