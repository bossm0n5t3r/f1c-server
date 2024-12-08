package me.f1c.adapter.result

import me.f1c.domain.ResponseDto
import me.f1c.domain.result.RaceResultService
import me.f1c.domain.result.RankingDto
import me.f1c.domain.toResponseDto
import me.f1c.port.result.RaceResultController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/results")
class RaceResultControllerImpl(
    private val raceResultService: RaceResultService,
) : RaceResultController {
    @GetMapping("/{season}/{round}/rankings")
    override fun getRankings(
        @PathVariable season: Int,
        @PathVariable round: Int,
    ): ResponseDto<RankingDto> = raceResultService.getRankings(season, round).toResponseDto()
}
