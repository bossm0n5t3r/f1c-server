package me.f1c.adapter.summary

import me.f1c.domain.ResponseDto
import me.f1c.domain.summary.SummaryService
import me.f1c.domain.toResponseDto
import me.f1c.port.summary.SummaryController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/summaries")
class SummaryControllerImpl(
    private val summaryService: SummaryService,
) : SummaryController {
    @GetMapping("/race/result")
    override fun getRaceResultSummary(
        @RequestParam season: Int,
        @RequestParam round: Int,
    ): ResponseDto<List<String>> = summaryService.getRaceResultSummary(season, round).toResponseDto()
}
