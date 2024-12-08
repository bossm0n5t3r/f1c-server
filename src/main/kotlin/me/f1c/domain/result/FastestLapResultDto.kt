package me.f1c.domain.result

import me.f1c.domain.driver.DriverDto

data class FastestLapResultDto(
    val driver: DriverDto,
    val lap: Int? = null,
    val time: String? = null,
)
