package me.f1c.domain.result

import me.f1c.domain.constructor.ConstructorDto
import me.f1c.domain.driver.DriverDto

data class RankedDriverDto(
    val driver: DriverDto,
    val position: Int,
    val constructor: ConstructorDto,
    val status: String,
    val timeText: String?,
)
