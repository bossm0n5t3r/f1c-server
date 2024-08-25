package me.f1c.adapter.lap

import me.f1c.domain.lap.LapService
import me.f1c.port.lap.LapController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/laps")
class LapControllerImpl(
    private val lapService: LapService,
) : LapController {
    @PutMapping("/{sessionKey}/up-to-date")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun upToDate(
        @PathVariable sessionKey: Int,
    ): Int = lapService.upToDate(sessionKey)
}
