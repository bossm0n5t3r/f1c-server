package me.f1c.adapter.schedule

import me.f1c.domain.ResponseDto
import me.f1c.domain.schedule.RaceScheduleDto
import me.f1c.domain.schedule.RaceScheduleService
import me.f1c.domain.toResponseDto
import me.f1c.port.schedule.RaceScheduleController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/schedules")
class RaceScheduleControllerImpl(
    private val raceScheduleService: RaceScheduleService,
) : RaceScheduleController {
    @GetMapping
    override fun findAllByYearAndMonth(
        @RequestParam year: Int,
        @RequestParam month: Int,
    ): ResponseDto<List<RaceScheduleDto>> = raceScheduleService.findAllByYearAndMonth(year, month).toResponseDto()

    @GetMapping("/latest")
    override fun findLatest(): ResponseDto<RaceScheduleDto?> = raceScheduleService.findLatest().toResponseDto()
}
