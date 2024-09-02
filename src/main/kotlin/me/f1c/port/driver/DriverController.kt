package me.f1c.port.driver

import me.f1c.domain.ResponseDto
import me.f1c.domain.driver.DriverDto

interface DriverController {
    fun findAll(sessionKey: Int): ResponseDto<List<DriverDto>>

    fun upToDate(sessionKey: Int): ResponseDto<Int>
}
