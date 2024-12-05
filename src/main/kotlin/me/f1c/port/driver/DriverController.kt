package me.f1c.port.driver

import me.f1c.domain.ResponseDto
import me.f1c.domain.driver.DriverDto

interface DriverController {
    fun findAllBySeason(season: Int): ResponseDto<List<DriverDto>>
}
