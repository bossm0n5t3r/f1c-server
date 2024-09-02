package me.f1c.port.position

import me.f1c.domain.ResponseDto
import me.f1c.domain.driver.DriverDto

interface PositionController {
    fun upToDate(sessionKey: Int): ResponseDto<Int>

    fun rankings(sessionKey: Int): ResponseDto<List<DriverDto>>
}
