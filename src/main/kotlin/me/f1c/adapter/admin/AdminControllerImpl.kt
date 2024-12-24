package me.f1c.adapter.admin

import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.ResponseDto
import me.f1c.domain.admin.AdminService
import me.f1c.domain.chat.AiRaceResultSummaryDto
import me.f1c.domain.toResponseDto
import me.f1c.port.admin.AdminController
import org.springframework.http.HttpMethod
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminControllerImpl(
    private val adminService: AdminService,
) : AdminController {
    @GetMapping("/up-to-date/race/schedule")
    override fun upToDateRaceSchedule(): ResponseDto<Int> = adminService.upToDateRaceSchedule().toResponseDto()

    @GetMapping("/up-to-date/race/result")
    override fun upToDateRaceResult(): ResponseDto<Int> = adminService.upToDateRaceResult().toResponseDto()

    @GetMapping("/up-to-date/driver")
    override fun upToDateDriver(): ResponseDto<Int> = adminService.upToDateDriver().toResponseDto()

    @GetMapping("/up-to-date/constructor")
    override fun upToDateConstructor(): ResponseDto<Int> = adminService.upToDateConstructor().toResponseDto()

    @GetMapping("/up-to-date/circuit")
    override fun upToDateCircuit(): ResponseDto<Int> = adminService.upToDateCircuit().toResponseDto()

    @GetMapping("/up-to-date/laps/all")
    override fun upToDateAllLaps(): ResponseDto<Int> = adminService.upToDateAllLaps().toResponseDto()

    @GetMapping("/up-to-date/laps")
    override fun upToDateLaps(
        @RequestParam season: Int,
        @RequestParam round: Int,
    ): ResponseDto<Int> = adminService.upToDateLaps(season, round).toResponseDto()

    @PostMapping("/summaries/race/result")
    override fun createRaceResultSummary(
        @RequestParam season: Int,
        @RequestParam round: Int,
    ): ResponseDto<AiRaceResultSummaryDto> = adminService.createRaceResultSummary(season, round).toResponseDto()

    @PutMapping("/summaries/race/result")
    override fun updateRaceResultSummary(
        @RequestParam season: Int,
        @RequestParam round: Int,
    ): ResponseDto<AiRaceResultSummaryDto> = adminService.updateRaceResultSummary(season, round).toResponseDto()

    private fun HttpMethod.log(params: Any? = null) {
        LOGGER.info("{} test: {}, {}, {}", LogResult.SUCCEEDED, this, params, SecurityContextHolder.getContext().authentication)
    }

    @GetMapping("/test")
    fun testGet(): ResponseDto<String> {
        HttpMethod.GET.log()
        return "test".toResponseDto()
    }

    @PostMapping("/test")
    fun testPost(
        @RequestBody dummyRequest: Any,
    ): ResponseDto<Any> {
        HttpMethod.POST.log(dummyRequest)
        return dummyRequest.toResponseDto()
    }
}
