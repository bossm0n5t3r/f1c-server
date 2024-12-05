package me.f1c.port.driver

import me.f1c.domain.driver.DriverDto

interface DriverRepository {
    fun findAllBySeason(season: Int): List<DriverDto>

    fun batchInsert(driverDtoList: List<DriverDto>): Int
}
