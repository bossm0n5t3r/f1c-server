package me.f1c.port.driver

import me.f1c.domain.driver.DriverDto

interface DriverRepository {
    fun findAllBySessionKey(sessionKey: Int): List<DriverDto>

    fun batchInsert(driverDtoList: List<DriverDto>): Int

    fun findAllByDriverNumberOrderBySessionKey(driverNumber: Int): List<DriverDto>
}
