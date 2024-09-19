package me.f1c.adapter.summary

import me.f1c.domain.ResponseDto
import me.f1c.domain.chat.AiSessionSummaryDto
import me.f1c.domain.summary.SummaryService
import me.f1c.domain.toResponseDto
import me.f1c.port.summary.SummaryController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/summaries")
class SummaryControllerImpl(
    private val summaryService: SummaryService,
) : SummaryController {
    @GetMapping("/sessions/{sessionKey}")
    override fun getSessionSummary(
        @PathVariable sessionKey: Int,
    ): ResponseDto<AiSessionSummaryDto> = summaryService.getSessionSummary(sessionKey).toResponseDto()
}
