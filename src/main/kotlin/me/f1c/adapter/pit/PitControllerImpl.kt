package me.f1c.adapter.pit

import me.f1c.domain.pit.PitService
import me.f1c.port.pit.PitController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pits")
class PitControllerImpl(
    private val pitService: PitService,
) : PitController {
    @PutMapping("/{sessionKey}/up-to-date")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun upToDate(
        @PathVariable sessionKey: Int,
    ): Int = pitService.upToDate(sessionKey)
}
