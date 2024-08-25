package me.f1c.adapter.position

import me.f1c.domain.driver.DriverDto
import me.f1c.domain.position.PositionService
import me.f1c.port.position.PositionController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/positions")
class PositionControllerImpl(
    private val positionService: PositionService,
) : PositionController {
    @PutMapping("/{sessionKey}/up-to-date")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun upToDate(
        @PathVariable sessionKey: Int,
    ): Int {
        return positionService.upToDate(sessionKey)
    }

    @GetMapping("/{sessionKey}/rankings")
    override fun rankings(
        @PathVariable sessionKey: Int,
    ): List<DriverDto> {
        return positionService.rankings(sessionKey)
    }
}
