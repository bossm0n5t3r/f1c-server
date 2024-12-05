package me.f1c.adapter.driver

import me.f1c.domain.ResponseDto
import me.f1c.domain.driver.DriverDto
import me.f1c.domain.driver.DriverService
import me.f1c.domain.toResponseDto
import me.f1c.port.driver.DriverController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/drivers")
class DriverControllerImpl(
    private val driverService: DriverService,
) : DriverController {
    @GetMapping("/{season}")
    override fun findAllBySeason(
        @PathVariable season: Int,
    ): ResponseDto<List<DriverDto>> = driverService.findAllBySeason(season).toResponseDto()
}
