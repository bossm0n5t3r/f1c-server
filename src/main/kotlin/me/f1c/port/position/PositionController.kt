package me.f1c.port.position

import me.f1c.domain.driver.DriverDto

interface PositionController {
    fun upToDate(sessionKey: Int): Int

    fun rankings(sessionKey: Int): List<DriverDto>
}
