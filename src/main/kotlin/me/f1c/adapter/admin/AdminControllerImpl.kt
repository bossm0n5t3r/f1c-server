package me.f1c.adapter.admin

import me.f1c.domain.ResponseDto
import me.f1c.domain.admin.AdminService
import me.f1c.domain.chat.AiSessionSummaryDto
import me.f1c.domain.summary.SummaryService
import me.f1c.domain.toResponseDto
import me.f1c.port.admin.AdminController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminControllerImpl(
    private val adminService: AdminService,
    private val summaryService: SummaryService,
) : AdminController {
    @GetMapping("/up-to-date")
    override fun upToDate(): ResponseDto<Unit> = adminService.upToDate().toResponseDto()

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

    @GetMapping("/up-to-date/laps")
    override fun upToDateLaps(
        @RequestParam season: Int,
        @RequestParam round: Int,
    ): ResponseDto<Int> = adminService.upToDateLaps(season, round).toResponseDto()

    @PostMapping("/summaries/sessions/{sessionKey}")
    override fun createSessionSummary(
        @PathVariable sessionKey: Int,
    ): ResponseDto<AiSessionSummaryDto> = summaryService.createSessionSummary(sessionKey).toResponseDto()

    @PutMapping("/summaries/sessions/{sessionKey}")
    override fun updateSessionSummary(
        @PathVariable sessionKey: Int,
    ): ResponseDto<AiSessionSummaryDto> = summaryService.updateSessionSummary(sessionKey).toResponseDto()

    @GetMapping("/summaries/validation/sessions")
    override fun validateAllSessionSummaries(): ResponseDto<Unit> = summaryService.validateSessionSummaries().toResponseDto()

    @GetMapping("/summaries/validation/sessions/{sessionKey}")
    override fun validateSessionSummary(
        @PathVariable sessionKey: Int,
    ): ResponseDto<Boolean> = summaryService.validateSessionSummary(sessionKey).toResponseDto()
}
