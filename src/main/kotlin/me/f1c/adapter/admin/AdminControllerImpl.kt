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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminControllerImpl(
    private val adminService: AdminService,
    private val summaryService: SummaryService,
) : AdminController {
    @GetMapping("/up-to-date")
    override fun upToDate(): ResponseDto<Unit> = adminService.upToDate().toResponseDto()

    @PostMapping("/sessions/{sessionKey}")
    override fun createSessionSummary(
        @PathVariable sessionKey: Int,
    ): ResponseDto<AiSessionSummaryDto> = summaryService.createSessionSummary(sessionKey).toResponseDto()
}
